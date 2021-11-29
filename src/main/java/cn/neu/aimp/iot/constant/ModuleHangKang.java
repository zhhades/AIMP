package cn.neu.aimp.iot.constant;

import cn.neu.aimp.iot.entity.AimpDeviceConfig;
import cn.neu.aimp.iot.entity.AimpRealplayConfig;
import com.sun.jna.Pointer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;

/**
 * 海康接口实现
 * 主要有 ：初始化、登陆、登出、实时流获取和云台控制等功能
 *
 * @author wangjiawen
 */
public class ModuleHangKang {
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private AimpDeviceConfig aimpDeviceConfig;
    //ai处理流
    public PipedOutputStream out2ai;
    //原始视频显示流
    public PipedOutputStream out2rtsp;
    FileOutputStream fileout;
    //设备信息
    HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;
    //IP参数
    HCNetSDK.NET_DVR_IPPARACFG m_strIpparaCfg;
    //用户参数
    HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;
    //是否在预览.
    public boolean bRealPlay;
    //已登录设备的IP地址
    String m_sDeviceIP;
    //用户句柄
    int lUserID = -1;
    //预览句柄
    int lPreviewHandle = -1;
    //预览回调函数实现
    FRealDataCallBack fRealDataCallBack;

    public ModuleHangKang(AimpDeviceConfig aimpDeviceConfig) throws IOException {
        //待完成 从数据库中读取配置信息
        if (hCNetSDK.NET_DVR_Init()) {
            System.out.println("初始化成功！");
        }
        hCNetSDK.NET_DVR_SetConnectTime(aimpDeviceConfig.getWaitTime(), aimpDeviceConfig.getTryTimes());
        hCNetSDK.NET_DVR_SetReconnect(aimpDeviceConfig.getReconnectTime(), true);
        fRealDataCallBack = new FRealDataCallBack();
    }

    /**
     * 登录模块
     *
     * @param ip
     * @param port
     * @param userName
     * @param password
     * @return
     */
    public int login(String ip, int port, String userName, String password) {
        HCNetSDK.NET_DVR_USER_LOGIN_INFO struLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
        byte[] sDeviceIP = ip.getBytes();
        byte[] sUserName = userName.getBytes();
        byte[] sPwd = password.getBytes();
        lUserID = -1;
        for (int i = 0; i < sDeviceIP.length; i++) {
            struLoginInfo.sDeviceAddress[i] = sDeviceIP[i];
        }
        for (int i = 0; i < sPwd.length; i++) {
            struLoginInfo.sPassword[i] = sPwd[i];
        }
        for (int i = 0; i < sUserName.length; i++) {
            struLoginInfo.sUserName[i] = sUserName[i];
        }
        struLoginInfo.wPort = (short) port;

        //设备信息，登录接口返回参数，输出参数
        HCNetSDK.NET_DVR_DEVICEINFO_V40 struDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();

        //登录设备
        lUserID = hCNetSDK.NET_DVR_Login_V40(struLoginInfo, struDeviceInfo);
        if (lUserID == -1) {
            System.out.println("登录失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            System.out.println("登录成功！");
        }
        return lUserID;
    }

    /**
     * 注销模块
     */
    public boolean logout() {
        if (lPreviewHandle > -1) {
            hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);
        }
        //如果已经注册,注销
        if (lUserID > -1) {
            hCNetSDK.NET_DVR_Logout_V30(lUserID);
        }
        //cleanup SDK
        hCNetSDK.NET_DVR_Cleanup();
        return true;
    }

    /**
     * 实时流获取与处理模块：将获取的流调用回调函数处理
     *
     * @param aimpRealplayConfig 实时流获取的配置文件
     * @return
     */
    public boolean realPlay(AimpRealplayConfig aimpRealplayConfig) throws FileNotFoundException {
        if (lUserID == -1) {
            return false;
        }
        if(aimpRealplayConfig.getIsSaveFile()==1){
            fileout = new FileOutputStream(aimpRealplayConfig.getFilePath());
        }
        if(aimpRealplayConfig.getIsForward()==1){
            out2rtsp = new PipedOutputStream();
        }
        if(aimpRealplayConfig.getIsImgStream()==1){
            out2ai = new PipedOutputStream();
        }
        HCNetSDK.NET_DVR_PREVIEWINFO m_strPreviewInfo = new HCNetSDK.NET_DVR_PREVIEWINFO();
        m_strPreviewInfo.hPlayWnd = null;
        m_strPreviewInfo.lChannel = aimpRealplayConfig.getChannelNum();
        m_strPreviewInfo.dwStreamType = aimpRealplayConfig.getDwStreamType();
        m_strPreviewInfo.dwLinkMode = aimpRealplayConfig.getDwLinkMode();
        m_strPreviewInfo.bBlocked = aimpRealplayConfig.getBBlocked();
        m_strPreviewInfo.write();

        if (fRealDataCallBack == null) {
            fRealDataCallBack = new FRealDataCallBack();
        }
        if(aimpRealplayConfig.getIsSaveFile()==1){
            fileout = new FileOutputStream(aimpRealplayConfig.getFilePath());
        }

        //开始预览，设置回调
        lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V40(lUserID, m_strPreviewInfo, fRealDataCallBack, null);
        if (lPreviewHandle == -1) {
            System.out.println("开始预览失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
            return false;
        }
        System.out.println("开始预览成功");
        return true;
    }


    /**
     * 停止预览模块
     * @return
     */
    public boolean stopRealPlay() {
        return hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);
    }

    /**
     * 云台控制模块
     *
     * @param iPTZCommand
     * @param iStop
     * @param iSpeed
     * @return
     */
    public boolean pztControl(int iPTZCommand, int iStop, int iSpeed) {
        boolean ret = false;
        if (lPreviewHandle >= 0) {
            //有速度的ptz
            if (iSpeed >= 1) {
                ret = hCNetSDK.NET_DVR_PTZControlWithSpeed(lPreviewHandle, iPTZCommand, iStop, iSpeed);
            }
            //速度为默认时
            else {
                ret = hCNetSDK.NET_DVR_PTZControl(lPreviewHandle, iPTZCommand, iStop);
            }
        }
        return ret;
    }

    public PipedOutputStream getOut2ai() {
        return out2ai;
    }

    public void setOut2ai(PipedOutputStream out2ai) {
        this.out2ai = out2ai;
    }

    public PipedOutputStream getOut2rtsp() {
        return out2rtsp;
    }

    public void setOut2rtsp(PipedOutputStream out2rtsp) {
        this.out2rtsp = out2rtsp;
    }

    /******************************************************************************
     *内部类:   FRealDataCallBack
     *          实现预览回调数据
     ******************************************************************************/
    class FRealDataCallBack implements HCNetSDK.FRealDataCallBack_V30 {

        @Override
        public void invoke(int lRealHandle, int dwDataType, Pointer pBuffer, int dwBufSize, Pointer pUser) {
            if (dwBufSize > 0) {
                long offset = 0;
                ByteBuffer buffers = pBuffer.getByteBuffer(offset, dwBufSize);
                byte[] bytes = new byte[dwBufSize];
                buffers.rewind();
                buffers.get(bytes);
                try {
                    if(out2ai!=null){
                        out2ai.write(bytes);
                    }
                    if(out2rtsp!=null){
                        out2rtsp.write(bytes);
                    }
                    if(fileout!=null){
                        fileout.write(bytes);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}




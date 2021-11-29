package cn.neu.aimp.iot.constant;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;
import cn.neu.aimp.iot.constant.lib.NetSDKLib.LLong;
import cn.neu.aimp.iot.constant.lib.NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY;
import cn.neu.aimp.iot.constant.lib.NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY;
import cn.neu.aimp.iot.constant.lib.ToolKits;
import cn.neu.aimp.iot.entity.AimpDeviceConfig;
import cn.neu.aimp.iot.entity.AimpRealplayConfig;
import com.sun.jna.Pointer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;

/**
 * 大华接口实现
 * 主要有 ：初始化、登陆、登出、实时流获取等功能
 *
 * @author wangjiawen
 */
public class ModuleDaHua {

    static NetSDKLib netsdk = NetSDKLib.NETSDK_INSTANCE;
    static NetSDKLib configsdk = NetSDKLib.CONFIG_INSTANCE;
    private AimpDeviceConfig aimpDeviceConfig;
    // 设备断线通知回调
    private static DisConnect disConnect = new DisConnect();
    // 网络连接恢复
    private static HaveReConnect haveReConnect = new HaveReConnect();

    //ai处理流
    public PipedOutputStream out2ai;
    //原始视频显示流
    public PipedOutputStream out2rtsp;
    //本地视频存储
    FileOutputStream fileout;
    // 登陆句柄
    public LLong m_hLoginHandle = new LLong(0);
    // 预览句柄
    public LLong m_hPlayHandle = new LLong(0);
    // 设备信息
    NetSDKLib.NET_DEVICEINFO_Ex m_stDeviceInfo = new NetSDKLib.NET_DEVICEINFO_Ex();

    //是否初始化
    private boolean bInit = false;
    private final boolean bLogopen = false;
    //回调信息
    private DataCallBackEx callBackEx = new DataCallBackEx();

    public ModuleDaHua(AimpDeviceConfig aimpDeviceConfig) throws FileNotFoundException {
        this.aimpDeviceConfig = aimpDeviceConfig;
        init(disConnect, haveReConnect);
    }

    /**
     * \if ENGLISH_LANG
     * Init
     * \else
     * 初始化
     * \endif
     */
    public boolean init(NetSDKLib.fDisConnect disConnect, NetSDKLib.fHaveReConnect haveReConnect) throws FileNotFoundException {
        bInit = netsdk.CLIENT_Init(disConnect, null);
        if (!bInit) {
            System.out.println("Initialize SDK failed");
            return false;
        }

        // 设置断线重连回调接口，设置过断线重连成功回调函数后，当设备出现断线情况，SDK内部会自动进行重连操作
        // 此操作为可选操作，但建议用户进行设置
        netsdk.CLIENT_SetAutoReconnect(haveReConnect, null);

        //设置登录超时时间和尝试次数，可选
        //登录请求响应超时时间设置为5S
        int waitTime = aimpDeviceConfig.getWaitTime();
        //登录时尝试建立链接1次
        int tryTimes = aimpDeviceConfig.getTryTimes();
        netsdk.CLIENT_SetConnectTime(waitTime, tryTimes);

        // 设置更多网络参数，NET_PARAM的nWaittime，nConnectTryNum成员与CLIENT_SetConnectTime
        // 接口设置的登录设备超时时间和尝试次数意义相同,可选
        NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
        netsdk.CLIENT_SetNetworkParam(netParam);

        return true;
    }

    /**
     * \if ENGLISH_LANG
     * CleanUp
     * \else
     * 清除环境
     * \endif
     */
    public void cleanup() {
        if (bLogopen) {
            netsdk.CLIENT_LogClose();
        }

        if (bInit) {
            netsdk.CLIENT_Cleanup();
        }
    }

    /**
     * \if ENGLISH_LANG
     * Login Device
     * \else
     * 登录设备
     * \endif
     */
    public boolean login(String m_strIp, int m_nPort, String m_strUser, String m_strPassword) {
        //IntByReference nError = new IntByReference(0);
        //入参
        NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY pstInParam = new NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY();
        pstInParam.nPort = m_nPort;
        pstInParam.szIP = m_strIp.getBytes();
        pstInParam.szPassword = m_strPassword.getBytes();
        pstInParam.szUserName = m_strUser.getBytes();
        //出参
        NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY pstOutParam = new NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY();
        pstOutParam.stuDeviceInfo = m_stDeviceInfo;
        m_hLoginHandle = netsdk.CLIENT_LoginWithHighLevelSecurity(pstInParam, pstOutParam);
        if (m_hLoginHandle.longValue() == 0) {
            System.err.printf("Login Device[%s] Port[%d]Failed. %s\n", m_strIp, m_nPort, ToolKits.getErrorCodePrint());
        } else {
            System.out.println("Login Success [ " + m_strIp + " ]");
        }

        return m_hLoginHandle.longValue() != 0;
    }

    /**
     * \if ENGLISH_LANG
     * Start RealPlay
     * \else
     * 停止预览
     * \endif
     * @return
     */
    public boolean stopRealPlay() {
        if(m_hPlayHandle.longValue() == 0) {
            return false;
        }
        boolean bRet = netsdk.CLIENT_StopRealPlayEx(m_hPlayHandle);
        if(bRet) {
            m_hPlayHandle.setValue(0);
        }
        return bRet;
    }

    /**
     * \if ENGLISH_LANG
     * Logout Device
     * \else
     * 登出设备
     * \endif
     */
    public boolean logout() {
        if (m_hLoginHandle.longValue() == 0) {
            return false;
        }

        boolean bRet = netsdk.CLIENT_Logout(m_hLoginHandle);
        if (bRet) {
            m_hLoginHandle.setValue(0);
        }

        return bRet;
    }

    /**
     * 设置回调函数，主要实现将流如何处理
     */
    class DataCallBackEx implements NetSDKLib.fRealDataCallBackEx {

        @Override
        public void invoke(LLong lRealHandle, int dwDataType, Pointer pBuffer,
                           int dwBufSize, int param, Pointer dwUser) {
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

    public boolean realPay(AimpRealplayConfig aimpRealplayConfig) throws FileNotFoundException {
        if(aimpRealplayConfig.getIsForward()==1){
            out2rtsp = new PipedOutputStream();
        }
        if(aimpRealplayConfig.getIsSaveFile()==1){
            fileout = new FileOutputStream(aimpRealplayConfig.getFilePath());
        }
        if(aimpRealplayConfig.getIsImgStream()==1){
            out2ai = new PipedOutputStream();
        }
        //实时播放函数
        m_hPlayHandle = netsdk.CLIENT_RealPlayEx(m_hLoginHandle,
                aimpRealplayConfig.getChannelNum(), null, aimpRealplayConfig.getDwStreamType());
        //回调函数设置
        netsdk.CLIENT_SetRealDataCallBackEx(m_hLoginHandle, callBackEx, null, 0x00000001);
        if(m_hPlayHandle.longValue()==0){
            System.out.println("错误码"+ToolKits.getErrorCodePrint());
        }
        return m_hPlayHandle.longValue() != 0;
    }

    public boolean ptzControl(int channel, int controlType, int lParam1,
                              int lParam2, int lParam3, int stopFlag){
        return netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channel,
                controlType, lParam1, lParam2, lParam3, stopFlag);
    }

    private static class DisConnect implements NetSDKLib.fDisConnect {
        @Override
        public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);
        }
    }

    private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
        @Override
        public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);
        }
    }

}



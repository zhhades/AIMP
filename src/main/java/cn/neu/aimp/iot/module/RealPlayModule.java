package cn.neu.aimp.iot.module;

import cn.neu.aimp.iot.constant.ModuleDaHua;
import cn.neu.aimp.iot.constant.ModuleHangKang;
import cn.neu.aimp.iot.entity.*;
import cn.neu.aimp.iot.processor.FFmpegPusher;
import cn.neu.aimp.iot.processor.PtzProcess;
import cn.neu.aimp.iot.processor.streamProcess;
import cn.neu.aimp.iot.service.impl.*;
import cn.neu.aimp.iot.storage.ImgStorage;
import cn.neu.aimp.iot.storage.PtzStorage;
import cn.neu.aimp.iot.util.Tool;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.util.HashMap;

/**
 * @author wangjiawen
 * 实时播放模块，主要完成将实时流传输到各个处理模块。
 */
public class RealPlayModule {
    /**
     * 每个设备的存储仓库
     * 主要存储待处理和已经处理的图片流和需要处理的云台控制指令
     */
    public static HashMap<String, Object[]> storages = new HashMap<>();

    /**
     * 实时流播放接口
     * @param id 设备id
     * @param aimpRealplayConfig 播放相关参数
     * @return
     */
    public static boolean startRealPlay(String id, AimpRealplayConfig aimpRealplayConfig) throws FileNotFoundException {
        if(id.startsWith("dh")){
            return LoginAndOutModule.devices.get(id).getDaHua().realPay(aimpRealplayConfig);
        }
        else if(id.startsWith("hk")){
            ModuleHangKang hangKang = LoginAndOutModule.devices.get(id).getHangKang();
            return hangKang.realPlay(aimpRealplayConfig);
        }else {
            return false;
        }
    }

    /**
     * 视频流实时播放和转发
     * 适用于本地测试
     * @param id 设备id
     * @throws IOException
     */
    public void realPlayLocal(String id, AimpRealplayConfig aimpRealplayConfig,
                              AimpForwardConfig aimpForwardConfig, AimpPtzcontrolConfig aimpPtzcontrolConfig) throws IOException {
        if(aimpRealplayConfig ==null){
            aimpRealplayConfig = getDefaultRealPlayConfig(id);
        }
        if(aimpForwardConfig ==null){
            aimpForwardConfig = getDefaultForwardConfig(id);
        }
        if(aimpPtzcontrolConfig == null){
            aimpPtzcontrolConfig = getDefaultPtzControlConfig(id);
        }
        ThreadWrite threadWrite = new ThreadWrite(id, aimpRealplayConfig);


        if(aimpRealplayConfig.getIsPtzControl()==1){

        }
        PtzStorage ptzStorage = new PtzStorage(id, aimpPtzcontrolConfig.getPtzStorageMaxSize());
        ImgStorage imgStorage = new ImgStorage(aimpRealplayConfig.getPendingListMaxSize(),
                aimpRealplayConfig.getProcessedListMaxSize());
        streamProcess streamProcess = new streamProcess(imgStorage, ptzStorage);
        PtzProcess pz = new PtzProcess(ptzStorage, id, aimpPtzcontrolConfig);

        PipedInputStream input2ai = new PipedInputStream();
        PipedInputStream input2rtsp = new PipedInputStream();


        ThreadRead threadRead = new ThreadRead(streamProcess, input2ai);
        ThreadPtzRead threadPTZRead = new ThreadPtzRead(pz);
        String ip = LoginAndOutModule.devices.get(id).getIp();
        RtspWriter rtspWriter = new RtspWriter(input2rtsp,
                "rtsp://172.22.104.255/"+ip+"withoutAI", aimpForwardConfig);
        if(id.startsWith("dh")){
            ModuleDaHua daHua = LoginAndOutModule.devices.get(id).getDaHua();
            daHua.out2ai.connect(input2ai);
            daHua.out2rtsp.connect(input2rtsp);
        }
        else if(id.startsWith("hk")) {
            ModuleHangKang hangKang = LoginAndOutModule.devices.get(id).getHangKang();
            hangKang.out2ai.connect(input2ai);
            hangKang.out2rtsp.connect(input2rtsp);

        }
        threadWrite.start();
        threadPTZRead.start();
        threadRead.start();
        rtspWriter.start();
    }

    /**
     * 关闭实时流播放
     * @param id 设备id
     * @return
     */
    public boolean stopRealPlay(String id){
        if(id.startsWith("dh") && LoginAndOutModule.devices.get(id).getDaHua().m_hPlayHandle.longValue()!=0){
            return LoginAndOutModule.devices.get(id).getDaHua().stopRealPlay();
        }
        else if(id.startsWith("hk") && LoginAndOutModule.devices.get(id).getHangKang().bRealPlay){
            ModuleHangKang hangKang = LoginAndOutModule.devices.get(id).getHangKang();
            return hangKang.stopRealPlay();
        }else {
            return false;
        }
    }

    /**
     * 视频流实时播放接口
     * 调用本接口开启实时播放
     * @param id 设备id
     * @return
     * @throws IOException
     */
    public Object[] realPlay(String id, AimpRealplayConfig aimpRealplayConfig,
                             AimpForwardConfig aimpForwardConfig, AimpPtzcontrolConfig aimpPtzcontrolConfig) throws IOException, InterruptedException {
        if(LoginAndOutModule.devices.get(id)==null){
            System.out.println("当前设备未登录，请先登陆。");
            return null;
        }
        if(id.startsWith("dh") && LoginAndOutModule.devices.get(id).getDaHua().m_hPlayHandle.longValue()>0){
            return storages.get(id);
        }else if(id.startsWith("hk") && LoginAndOutModule.devices.get(id).getHangKang().bRealPlay){
            return storages.get(id);
        }
        if(aimpRealplayConfig ==null){
            aimpRealplayConfig = getDefaultRealPlayConfig(id);
        }
        if(aimpRealplayConfig.getIsForward()==1 && aimpForwardConfig ==null){
            aimpForwardConfig = getDefaultForwardConfig(id);

        }
        if(aimpRealplayConfig.getIsPtzControl()==1 && aimpPtzcontrolConfig ==null){
            aimpPtzcontrolConfig = getDefaultPtzControlConfig(id);
        }

        //开启实时流
        ThreadWrite threadWrite = new ThreadWrite(id, aimpRealplayConfig);
        threadWrite.start();

        Object[] objects = new Object[2];

        if(aimpRealplayConfig.getIsImgStream()==1){
            ImgStorage imgStorage = new ImgStorage(aimpRealplayConfig.getPendingListMaxSize(),
                    aimpRealplayConfig.getProcessedListMaxSize());
            streamProcess streamProcess = new streamProcess(imgStorage);
            PipedInputStream input2ai = new PipedInputStream();
            if(id.startsWith("dh")){
                ModuleDaHua daHua = LoginAndOutModule.devices.get(id).getDaHua();
                daHua.out2ai.connect(input2ai);
            }
            else if(id.startsWith("hk")) {
                ModuleHangKang hangKang = LoginAndOutModule.devices.get(id).getHangKang();
                hangKang.out2ai.connect(input2ai);
            }
            ThreadRead threadRead = new ThreadRead(streamProcess, input2ai);
            threadRead.start();
            objects[0] = imgStorage;
        }

        //开启实时流转发到流媒体服务器
        if(aimpRealplayConfig.getIsForward()==1){
            PipedInputStream input2rtsp = new PipedInputStream();
            //传输到流媒体服务器的连接地址应为:"rtsp://172.22.104.255/"+ip地址+"withoutAI"
            RtspWriter rtspWriter = new RtspWriter(input2rtsp,
                    aimpRealplayConfig.getStreamMediaIp(), aimpForwardConfig);
            if(id.startsWith("dh")){
                ModuleDaHua daHua = LoginAndOutModule.devices.get(id).getDaHua();
                daHua.out2rtsp.connect(input2rtsp);
            }
            else if(id.startsWith("hk")) {
                ModuleHangKang hangKang = LoginAndOutModule.devices.get(id).getHangKang();
                hangKang.out2rtsp.connect(input2rtsp);
            }
            rtspWriter.start();
        }

        //开启云台控制
        if(aimpRealplayConfig.getIsPtzControl()==1){
            PtzStorage ptzStorage = new PtzStorage(id, aimpPtzcontrolConfig.getPtzStorageMaxSize());
            PtzProcess pz = new PtzProcess(ptzStorage, id, aimpPtzcontrolConfig);
            ThreadPtzRead threadPTZRead = new ThreadPtzRead(pz);
            threadPTZRead.start();
            objects[1] = ptzStorage;
        }

        //数据临时存取仓库
        storages.put(id, objects);
        return objects;
    }

    /**
     * 视频流实时播放使用默认配置
     * @param id 设备id
     * @throws IOException
     */
    public void realPlayWithDefaultConfig(String id) throws IOException, InterruptedException {
        realPlay(id,null, null, null);
    }


    /**
     * 获取设备默认实时流播放配置
     * @param id 设备id
     * @return
     */
    public AimpRealplayConfig getDefaultRealPlayConfig(String id){
        AimpDevice aimpDevice = LoginAndOutModule.devices.get(id);
        AimpRealplayConfig aimpRealplayConfig = Tool.realplayConfigService.queryById(aimpDevice.getIp());
        if(aimpRealplayConfig ==null){
            return  Tool.realplayConfigService.queryById("默认");
        }else {
            return aimpRealplayConfig;
        }
    }

    /**
     * 获取设备默认转发到流媒体配置
     * @param id 设备id
     * @return
     */
    public AimpForwardConfig getDefaultForwardConfig(String id){
        AimpDevice aimpDevice = LoginAndOutModule.devices.get(id);
        AimpForwardConfig aimpForwardConfig = Tool.forwardConfigService.queryById(aimpDevice.getIp());
        if(aimpForwardConfig ==null){
            return  Tool.forwardConfigService.queryById("默认");
        }else {
            return aimpForwardConfig;
        }
    }

    /**
     * 获取设备默认云台控制配置
     * @param id 设备id
     * @return
     */
    public AimpPtzcontrolConfig getDefaultPtzControlConfig(String id){
        AimpDevice aimpDevice = LoginAndOutModule.devices.get(id);
        AimpPtzcontrolConfig aimpPtzcontrolConfig = Tool.ptzcontrolConfigService.queryById(aimpDevice.getIp());
        if(aimpPtzcontrolConfig ==null){
            return  Tool.ptzcontrolConfigService.queryById("默认");
        }else {
            return aimpPtzcontrolConfig;
        }
    }

    /**
     * 读取实时流数据的线程类
     */
    static class ThreadRead extends Thread {
        private streamProcess streamProcess;
        private PipedInputStream inputStream;
        public ThreadRead(streamProcess streamProcess, PipedInputStream inputStream) {
            this.streamProcess = streamProcess;
            this.inputStream = inputStream;
        }
        @Override
        public void run() {
            try {
                if(streamProcess.ptzStorage!=null){
                    streamProcess.streamLocalDetector(inputStream);
                }else {
                    streamProcess.getImageStream(inputStream);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入实时流数据的线程类
     */
    static class ThreadWrite extends Thread {
        private String id;
        private AimpRealplayConfig aimpRealplayConfig;
        public ThreadWrite(String deviceId, AimpRealplayConfig aimpRealplayConfig) {
            this.id = deviceId;
            this.aimpRealplayConfig = aimpRealplayConfig;
        }

        @SneakyThrows
        @Override
        public void run() {
            System.out.println(RealPlayModule.startRealPlay(id, aimpRealplayConfig));
        }
    }

    static class ThreadPtzRead extends Thread {
        PtzProcess ptz;
        public ThreadPtzRead(PtzProcess p){
            this.ptz = p;
        }

        @Override
        public void run() {
            while (true){
                ptz.ptzControl();
            }

        }
    }

    /**
     * 将实时流推送到流媒体服务器的线程类
     */
    static class RtspWriter extends Thread {
        private PipedInputStream pipedInputStream;
        private String stmpURL;
        private AimpForwardConfig aimpForwardConfig;

        public RtspWriter(PipedInputStream pipedInputStream,
                          String stmpURL, AimpForwardConfig aimpForwardConfig){
            this.pipedInputStream = pipedInputStream;
            this.stmpURL = stmpURL;
            this.aimpForwardConfig = aimpForwardConfig;
        }

        @SneakyThrows
        @Override
        public void run() {
            FFmpegPusher pusher=new FFmpegPusher(stmpURL);
                pusher.imgToRtsp(pipedInputStream, aimpForwardConfig);
//                pusher.pushWithFile();
        }
    }
}

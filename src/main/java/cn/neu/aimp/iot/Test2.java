package cn.neu.aimp.iot;

import cn.neu.aimp.iot.constant.ModuleDaHua;
import cn.neu.aimp.iot.entity.*;
import cn.neu.aimp.iot.util.Tool;

import java.io.IOException;
import java.util.List;

public class Test2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        AimpForwardConfig forwardConfig = new AimpForwardConfig();
        forwardConfig.setHeight(1080);
        forwardConfig.setWidth(1920);
        forwardConfig.setVideoCodec(0);
        forwardConfig.setVideoFormat("mp4");

    }

}
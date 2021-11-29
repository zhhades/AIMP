package cn.neu.aimp.iot;

import cn.neu.aimp.iot.entity.AimpDevice;
import cn.neu.aimp.iot.module.RealPlayModule;
import cn.neu.aimp.iot.module.LoginAndOutModule;
import cn.neu.aimp.iot.storage.ImgStorage;
import cn.neu.aimp.iot.storage.PtzStorage;
import com.github.chen0040.objdetect.models.DetectedObj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        //实例化登录模块
        LoginAndOutModule loginAndOutModule = new LoginAndOutModule();
        //设备登录，获取设备实例
        AimpDevice haikang = loginAndOutModule.login("haikang", "192.168.1.61", 8000, "admin", "abc123..");
        //实例化实时播放模块
        RealPlayModule realPlayModule = new RealPlayModule();
        //调用实时播放函数，开始实时播放。主要输入是设备实例获得的设备ID，其余包括实时播放配置、转发流媒体配置、云台控制配置都是可选。
        Object[] objects = realPlayModule.realPlay(haikang.getDeviceId(), null, null, null);
        //实时播放函数的返回值是两项，一项是图片存储仓库，即为一帧一帧的数据转化为的图片，另一项是控制指令仓库，主要是将处理后的目标位置和标签返回，一遍自动化云台控制。
        //注意当在实时播放配置中设置不自动进行云台控制，则返回值当中的控制指令仓库为NULL。
        ImgStorage imgStorage = (ImgStorage) objects[0];
        PtzStorage ptzStorage = (PtzStorage) objects[1];
        int i = 0;
        while (true){
            //获取图片
            BufferedImage bufferedImage = imgStorage.consumePendingImg();
            //处理图片，eg:存储，检测等
            ImageIO.write(bufferedImage, "jpg", new File("E:/test/" + i + ".jpg"));
            //处理完图片以后，将图片返回给图片仓库，存入已处理图片仓库。
            imgStorage.produceProcessedImg(bufferedImage);
            //可选 如果开启自动云台控制 则将处理后的检测信息封装为DetectedObj链表范围给控制指令仓库。
            ptzStorage.produce(new LinkedList<DetectedObj>());
        }
    }
}

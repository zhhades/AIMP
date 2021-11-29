package cn.neu.aimp.iot.processor;

import cn.neu.aimp.iot.storage.ImgStorage;
import cn.neu.aimp.iot.storage.PtzStorage;
import com.github.chen0040.objdetect.ObjectDetector;
import com.github.chen0040.objdetect.models.DetectedObj;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @author wangjiawen
 * 实时流处理模块
 */
public class streamProcess {

    /**
     * 当前帧编号
     */
    int i = 0;

    /**
     * 上次检测出目标帧号
     */
    int lastProcessI = 0;

    /**
     * 设置当前帧和上次检出目标帧之间间隔，以便用于恢复自动巡航
     */
    int interval = 100;

    /**
     * 每隔detectRate个帧进行目标检测和云台控制
     */
    int detectRate = 20;

    /**
     * 每隔imgRate帧将数据转为图片，适用于本地测试
     */
    int imgRate = 10;

    ObjectDetector detector = new ObjectDetector();
    public PtzStorage ptzStorage;
    public ImgStorage imgStorage;

    public streamProcess(ImgStorage imgStorage, PtzStorage ptzStorage){
        //初始化化相关参数 待完成读取配置表信息
        this.ptzStorage = ptzStorage;
        this.imgStorage = imgStorage;
    }

    public streamProcess(ImgStorage imgStorage){
        //初始化化相关参数 待完成读取配置表信息
        this.imgStorage = imgStorage;
    }

    /**
     * 视频流本地检测处理，主要完成将获取到的视频流转为图片
     * 调用相关模型检测，将检测结果传入鱼台控制模块，方便进行云台控制。
     * @param inputStream 接受传入的视频流
     * @throws Exception
     */
    public void streamLocalDetector(PipedInputStream inputStream) throws Exception {
        detector.loadModel();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputStream);
        frameGrabber.start();
        Frame frame = frameGrabber.grab();
        Java2DFrameConverter converter = new Java2DFrameConverter();
        while (frame != null ) {
            if(i % imgRate==0) {
                BufferedImage bufferedImage = converter.convert(frame);
                if (i % detectRate == 0) {
                    List<DetectedObj> result = detector.detectObjects(bufferedImage);
                    for (int j = 0; j < result.size(); j++) {
                        if (!"person".equals(result.get(j).getLabel())) {
                            result.remove(j);
                        }
                    }
                    System.out.println("There are " + result.size() + " objects detected");
                    if (result.size() > 0) {
                        ptzStorage.produce(result);
                        lastProcessI = i;
                    }
                    else if(i-lastProcessI>interval){
                        ptzStorage.produce(result);
                    }
                }
            }
            frame = frameGrabber.grab();
            i++;
        }
    }

    /**
     * 视频流图片提供模块
     * @param inputStream
     * @throws Exception
     */
    public void getImageStream(PipedInputStream inputStream) throws Exception {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputStream);
        frameGrabber.start();
        Frame frame = frameGrabber.grab();
        Java2DFrameConverter converter = new Java2DFrameConverter();
        while (frame != null ) {
            BufferedImage bufferedImage = converter.convert(frame);
            imgStorage.producePendingImg(bufferedImage);
            frame = frameGrabber.grab();
            i++;
        }
    }


    public void imgToRtsp(String id) throws FFmpegFrameRecorder.Exception {
        //视频宽高最好是按照常见的视频的宽高  16：9  或者 9：16
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtsp://172.22.104.255/"+id+"withAI",0,0,1);
        //设置视频编码层模式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        //设置视频为1帧每秒
        recorder.setFrameRate(15);
        //设置视频图像数据格式
//        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFormat("mp4");

        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();
            while (true) {
                BufferedImage read = imgStorage.consumeProcessedImg();
                recorder.record(converter.getFrame(read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recorder.stop();
            recorder.release();
        }
    }
}
package cn.neu.aimp.iot.processor;

import cn.neu.aimp.iot.constant.BStreamer;
import cn.neu.aimp.iot.entity.AimpForwardConfig;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedInputStream;


/**
 * @author wangjiawen
 * 推流模块
 */
public class FFmpegPusher extends BStreamer {
    public FFmpegPusher(String url) {
        super(url);
    }

    public FFmpegPusher(String url, int w, int h) {
        super(url, w, h);
    }
    private Process process;

    /**
     * 通过文件形式推流。
     * 需要配置ffmpeg启动路径和传输的媒体文件位置
     * 主要通过ffmpeg的cmd命令完成推流，详情命名可以参考ffmpeg文档
     * 这一部分主要用于本地测试
     */
    public void pushWithFile(){
        String basePath="F:\\ffmpeg-4.2.2\\ffmpeg-4.2.2-win64-shared\\bin";
        String videoPath="F:/星际迷航3：超越星辰.mp4";
        String ffmpegPath=String.format("%s\\ffmpeg",basePath);
        try {
            // 视频切换时，先销毁进程，全局变量Process process，方便进程销毁重启，即切换推流视频
            if(process != null){
                process.destroy();
                System.out.println(">>>>>>>>>>推流视频切换<<<<<<<<<<");
            }
            // ffmpeg开头，-re表明按照帧率发送，在推流时必须有
            // 指定要推送的视频
            // 指定推送服务器，-f：指定格式
            String command =String.format("%s -re -i %s -f rtsp -rtsp_transport tcp %s",
                    ffmpegPath, videoPath, getUrl());
            System.out.println("ffmpeg推流命令：" + command);
            // 运行cmd命令，获取其进程
            process = Runtime.getRuntime().exec(command);
            // 输出ffmpeg推流日志
            BufferedReader br= new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println("视频推流信息[" + line + "]");
            }
            process.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * rtsp推流，主要通过管道获得视频流，然后将其通过rtsp推流
     * @param pipedInputStream 管道传入的视频流
     */
    public void pushWithStream(PipedInputStream pipedInputStream, AimpForwardConfig aimpForwardConfig) throws FFmpegFrameGrabber.Exception, FFmpegFrameRecorder.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(pipedInputStream);
        // rtsp格式一般添加TCP配置，否则丢帧会比较严重
        // Brick在测试过程发现，该参数改成udp可以解决部分电脑出现的下列报警，但是丢帧比较严重
        // av_interleaved_write_frame() error -22 while writing interleaved video packet.
        grabber.start();
        // 最后一个参数是AudioChannels，建议通过grabber获取
        System.out.println(super.getUrl());
        final FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(super.getUrl(), aimpForwardConfig.getWidth(), aimpForwardConfig.getHeight(), 1);
        recorder.setInterleaved(true);
        // 降低编码延时
        recorder.setVideoOption("tune", "zerolatency");
        // 提升编码速度
        recorder.setVideoOption("preset", "ultrafast");
        // 视频质量参数(详见 https://trac.ffmpeg.org/wiki/Encode/H.264)
        recorder.setVideoOption("crf", "28");
        // 分辨率
//        recorder.setVideoBitrate(2000000);
         //视频编码格式
        if(aimpForwardConfig.getVideoCodec()==0){
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        }else if(aimpForwardConfig.getVideoCodec()==1){
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
        }
        // 视频格式
        recorder.setFormat(aimpForwardConfig.getVideoFormat());
        // 视频帧率
//        recorder.setFrameRate(15);
//        recorder.setGopSize(60);
////        recorder.setAudioOption("crf", "0");
//        recorder.setAudioQuality(0);
//        recorder.setAudioBitrate(192000);
//        recorder.setSampleRate(44100);
        // 建议从grabber获取AudioChannels
//        recorder.setAudioChannels(1);
//        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.start();
    }

    public void imgToRtsp(PipedInputStream pipedInputStream, AimpForwardConfig aimpForwardConfig) throws FFmpegFrameRecorder.Exception {
        //视频宽高最好是按照常见的视频的宽高  16：9  或者 9：16
        System.out.println(getUrl());
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(getUrl(),
                aimpForwardConfig.getWidth(), aimpForwardConfig.getHeight(),0);
        //设置视频编码层模式
        if(aimpForwardConfig.getVideoCodec()==0){
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        }else if(aimpForwardConfig.getVideoCodec()==1){
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
        }
        //设置视频为1帧每秒
        //设置视频图像数据格式
//        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFormat("flv");

        try {
            recorder.start();
            FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(pipedInputStream);
            frameGrabber.start();
            Frame frame = frameGrabber.grab();
            while (frame != null ) {
                recorder.record(frame);
                frame = frameGrabber.grab();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recorder.stop();
            recorder.release();
        }
    }
}
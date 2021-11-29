package cn.neu.aimp.iot;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class CameraHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CameraHandler.class);

    private final String LOCK = "LOCK";

    private Socket cameraSocket;
    private static BufferedImage bufferedImage;

    CameraHandler(Socket socket) throws IOException {
        this.cameraSocket = socket;
    }

    String getLock() {
        return LOCK;
    }

    @Override
    public void run() {
        try {
            InputStream cameraStream = cameraSocket.getInputStream();

            FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(cameraStream);

            frameGrabber.setFrameRate(25);
            frameGrabber.setFormat("h264");
            frameGrabber.setVideoBitrate(3000000);
            frameGrabber.setVideoOption("preset", "ultrafast");
            frameGrabber.setNumBuffers(25000000);

            frameGrabber.start();

            Frame frame = frameGrabber.grab();

            Java2DFrameConverter converter = new Java2DFrameConverter();

            while (frame != null) {
                BufferedImage bufferedImage = converter.convert(frame);

                setBufferedImage(bufferedImage);

                synchronized (LOCK) {
                    LOCK.notifyAll();
                }

                frame = frameGrabber.grab();
            }
        } catch (IOException e) {
            logger.info("Video handle error, exit ...");
            logger.info(e.getMessage());
        }
    }

    private void setBufferedImage(BufferedImage image) {
        bufferedImage = image;
    }

    BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}


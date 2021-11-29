package cn.neu.aimp.iot.processor;

import com.github.chen0040.objdetect.models.Box;
import com.github.chen0040.objdetect.models.DetectedObj;
import cn.neu.aimp.iot.entity.AimpPtzcontrolConfig;
import cn.neu.aimp.iot.storage.PtzStorage;
import cn.neu.aimp.iot.module.PtzControlModule;
import cn.neu.aimp.iot.constant.HCNetSDK;

import java.util.List;

/**
 * @author wangjiawen
 * 云台控制指令实际实现
 */
public class PtzProcess {
    private String deviceId;
    /**
     * 控制时间，云台每次调整的时间
     */
    private int ctrlTime = 120;

    /**
     * 小目标检测的阈值，目标大小所占图像大小比例低于该阈值视为小目标
     */
    private double smallObjectSize = 0.3;

    /**
     * 上一次是否处于放大中，便于确认本次是否焦距回调
     */
    private boolean lastZoomIn = false;

    /**
     * 目标是否在中间，当目标在图像中间时才能焦距回调
     */
    private  boolean isInCenter = false;

    /**
     * 等待时间，当摄像头启动或者收缩以后需要等待一定时间才能进行缩放
     */
    private int sleep = 6;
    PtzStorage storage;

    public PtzProcess(PtzStorage storage, String id, AimpPtzcontrolConfig aimpPtzcontrolConfig){
        //初始化参数 待完成读取参数完成配置
        this.storage = storage;
        this.deviceId = id;
        this.ctrlTime = aimpPtzcontrolConfig.getSleepTime();
        this.sleep = aimpPtzcontrolConfig.getCoolingTime();
        this.smallObjectSize = aimpPtzcontrolConfig.getSmallObject();
    }

    /**
     * 云台控制，主要完成云台上下左右控制和焦距调节
     */
    public void ptzControl() {
        try {
            List<DetectedObj> list = storage.consume();
            //如果传入的list为空，说明很久没有检测到目标，即将进行自动巡航。
            if(list.size()==0){
                PtzControlModule.ptzControl(deviceId, HCNetSDK.PAN_AUTO, 0, 1);
                return;
            }
            //如果传入的不为空，那应该先关闭自动巡航
            PtzControlModule.ptzControl(deviceId, HCNetSDK.PAN_AUTO, 1, 1);

            //接下来根据目标位置控制摄像头移动
            Box box = boxsListProcess(list);
            double left = box.getLeft();
            double top = box.getTop();
            double width = box.getWidth() / 2;
            double height = box.getHeight() / 2;
            //云台方向控制
            if (top + height < 0.5) {
                ptzMove(deviceId, HCNetSDK.TILT_UP,  (int) (0.5 - top - height) * 10);
            } else {
                ptzMove(deviceId, HCNetSDK.TILT_DOWN,  (int) (top + height - 0.5) * 10);
            }
            if (left + width < 0.5) {
                ptzMove(deviceId, HCNetSDK.PAN_LEFT, (int) (0.5 - left - width) * 10);
            } else {
                ptzMove(deviceId, HCNetSDK.PAN_RIGHT,  (int) (left + width - 0.5) * 10);
            }
            if(top + height < 0.6 && top + height > 0.4 && left + width < 0.6 && left + width > 0.4){
                isInCenter = true;
            }
            //云台伸缩控制
            if(--sleep>0){
                return;
            }
            double size = Math.max(height, width);
            if (size < smallObjectSize) {
                ptzMove(deviceId, HCNetSDK.ZOOM_IN,  (int) (smallObjectSize - size) * 10);
            }else if(lastZoomIn && isInCenter){
                //确定回调焦距前需要等待几秒进行抓拍
                Thread.sleep(ctrlTime*20);
                storage.consumeAll();
                ptzMove(deviceId, HCNetSDK.ZOOM_OUT, (int) (size - smallObjectSize) * 50);
                sleep = 6;
            }else {
                lastZoomIn = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多目标位置信息处理，主要讲多个目标的位置转化为一个Box
     * 确定多目标中左上角和右下角位置，这里转为左上角和长宽信息
     * @param list
     * @return
     */
    public Box boxsListProcess(List<DetectedObj> list) {
        float left = 1;
        float top = 1;
        float width = 0;
        float height = 0;
        for (DetectedObj obj : list) {
            left = Math.min(obj.getBox().getLeft(), left);
            top = Math.min(obj.getBox().getTop(), top);
            width = Math.max(obj.getBox().getLeft() - left + obj.getBox().getWidth(), width);
            height = Math.max(obj.getBox().getTop() - left + obj.getBox().getHeight(), height);
        }
        Box box = new Box();
        box.setLeft(left);
        box.setTop(top);
        box.setHeight(height);
        box.setWidth(width);
        return box;
    }

    /**
     * 封装控制信息，完成对一个设备的开始控制和结束控制。
     * @param deviceId 设备ID
     * @param controlType 云台控制类别
     * @param values 相关参数
     * @throws InterruptedException
     */
    public void ptzMove(String deviceId, int controlType, int... values) throws InterruptedException {
        PtzControlModule.ptzControl(deviceId, controlType, 0, values);
        Thread.sleep(ctrlTime);
        PtzControlModule.ptzControl(deviceId, controlType, 1, values);
    }
}

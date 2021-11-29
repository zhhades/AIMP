package cn.neu.aimp.iot.module;

/**
 * @author JiawenWang
 * 云台控制模块
 * 提供云台控制接口
 */
public class PtzControlModule {

    /**
     * 云台控制通用接口
     * @param id 设备id
     * @param controlType 控制指令（参考文档）
     * @param isStop 开始/结束控制（0-开始，1-结束）
     * @param values 相关参数。对于海康设备来说输入控制速度。
     * @return
     */
    public static boolean ptzControl(String id, int controlType, int isStop, int... values){
        if(id.startsWith("dh")){
            //当前大华设备不支持云台控制，所有该项方法的详细参数参考文档。
            return LoginAndOutModule.devices.get(id).getDaHua().ptzControl(values[0],
                    controlType, values[1], values[2],values[3], isStop );
        }
        else if(id.startsWith("hk")){
            return LoginAndOutModule.devices.get(id).getHangKang().pztControl(controlType, isStop, values[0]);
        }else {
            return false;
        }
    }


}

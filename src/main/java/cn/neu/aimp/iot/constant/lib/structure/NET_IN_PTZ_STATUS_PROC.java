package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

/**
 * @author 251823
 * @description 订阅云台元数据接口输入参数
 * @date 2021/02/26
 */
public class NET_IN_PTZ_STATUS_PROC extends NetSDKLib.SdkStructure {
	 /**
     * dwSize;
     */
    public int dwSize;
    /**
     *  云台通道
     */
    public int nChannel;

    /**
     *  状态回调函数
     */
    public NetSDKLib.fPTZStatusProcCallBack cbPTZStatusProc;

    /**
     *  用户数据
     */
    public long dwUser;

    public NET_IN_PTZ_STATUS_PROC(){
        this.dwSize = this.size();
    }

}

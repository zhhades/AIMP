package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

public class NET_OUT_SET_PARK_CONTROL_INFO  extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
	public int					dwSize;					 

	public NET_OUT_SET_PARK_CONTROL_INFO() {
        this.dwSize = this.size();
    }
}

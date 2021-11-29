package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;
import cn.neu.aimp.iot.constant.lib.enumeration.NET_VOLUME_TYPE;

/**
 * NET_QUERY_DEV_STORAGE_NAMES 类型接口 输入参数
 * @author 29779
 */
public class NET_IN_STORAGE_DEV_INFOS extends NetSDKLib.SdkStructure {
	public NET_IN_STORAGE_DEV_INFOS() {
		this.dwSize = this.size();
	}
	
	public int dwSize;
	
	/**
	 * 要获取的卷类型
	 * {@link NET_VOLUME_TYPE }
	 */
	public int emVolumeType; 
}

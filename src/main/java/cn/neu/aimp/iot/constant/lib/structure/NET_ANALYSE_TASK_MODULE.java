package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

/**
 * @description 任务模块配置
 * @author 119178
 * @date 2021/3/16
 */
public class NET_ANALYSE_TASK_MODULE extends NetSDKLib.SdkStructure {
	/**
	 * 规则特定的尺寸过滤器
	 */
	public NET_CFG_SIZEFILTER_INFO stuSizeFileter;
	/**
	 * 排除区域
	 */
	public	NetSDKLib.NET_POLY_POINTS[]						stuExcludeRegion=(NetSDKLib.NET_POLY_POINTS[])new NetSDKLib.NET_POLY_POINTS().toArray(10);
	/**
	 * 排除区域数
	 */
	public	int										nExcludeRegionNum;	
	/**
	 * 保留字节
	 */
	public	byte[]									byReserved = new byte[1020];									
}

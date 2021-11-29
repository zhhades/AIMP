package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

/**
 * @description 标定区域
 * @author 119178
 * @date 2021/3/16
 */
public class NET_ANALYSE_TASK_GLOBAL_CALIBRATEAREA extends NetSDKLib.SdkStructure {
	/**
	 * 标尺线  
	 */
	public NET_STAFF_INFO stuStaffs;
	/**
	 * 标定多边形区域
	 */
	public	NetSDKLib.DH_POINT[]					stuCalibratePloygonArea=(NetSDKLib.DH_POINT[])new NetSDKLib.DH_POINT().toArray(20);
	/**
	 * 标定多边形区域顶点个数
	 */
	public	int							nCalibratePloygonAreaNum;  
	/**
	 * 保留字节
	 */
	public	byte[]                        byReserved = new byte[1020];												
}

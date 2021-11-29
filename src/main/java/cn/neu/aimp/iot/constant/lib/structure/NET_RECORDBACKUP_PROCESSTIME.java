package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

public class NET_RECORDBACKUP_PROCESSTIME  extends NetSDKLib.SdkStructure {
	public NET_TIME_EX1 stuStartTime;            // 开始时间
	public NET_TIME_EX1 stuEndTime;              // 结束时间
	public byte[]                  bReserved=new byte[64];          // 保留字段
}

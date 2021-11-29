package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

public class NET_BROADCAST_INFO  extends NetSDKLib.SdkStructure {
	/**
	 * 语音文本
	 */
	public byte[]					szText = new byte[256];			
	/**
	 * 文本类型EM_BROADCAST_TEXT_TYPE
	 */
	public int	emTextType;				
	/**
	 * 保留字节
	 */
	public byte[]					byReserved = new byte[252];		 
}

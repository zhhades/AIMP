package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

/**
 * 电视墙使能信息
 *
 * @author ： 47040
 * @since ： Created in 2020/10/19 11:17
 */
public class NET_MONITORWALL_ENABLE_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int dwSize;
    /**
     * 使能
     */
    public int bEnable;
    /**
     * 电视墙名称
     */
    public byte[] szName = new byte[NetSDKLib.NET_COMMON_STRING_128];

    public NET_MONITORWALL_ENABLE_INFO() {
        dwSize = this.size();
    }
}

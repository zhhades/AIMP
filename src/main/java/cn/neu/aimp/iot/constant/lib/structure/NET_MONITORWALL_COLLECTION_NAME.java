package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

/**
 * 电视墙预案名称 拆分自{@link NET_IN_MONITORWALL_GET_COLL_SCHD}
 *
 * @author ： 47040
 * @since ： Created in 2020/10/19 9:28
 */
public class NET_MONITORWALL_COLLECTION_NAME extends NetSDKLib.SdkStructure {
    /**
     * 电视墙预案名称
     */
    public byte[] collectionName = new byte[NetSDKLib.NET_DEVICE_NAME_LEN];

}

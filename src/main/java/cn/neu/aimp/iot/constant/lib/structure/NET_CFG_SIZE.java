package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

/**
 * 物体尺寸
 */
public class NET_CFG_SIZE extends NetSDKLib.SdkStructure {
    /**
     * 针对使用的不同,该字段有两种含义:宽,面积
     * 使用哪种含义由配置的是宽高还是面积决定
     */
    public float nWide;
    /**
     * 高
     */
    public float             nHeight;
}

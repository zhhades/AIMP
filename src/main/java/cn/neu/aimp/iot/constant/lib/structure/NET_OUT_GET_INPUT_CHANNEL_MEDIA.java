package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;
import cn.neu.aimp.iot.constant.lib.enumeration.NET_ENUM_INPUT_CHANNEL_MEDIA;

/**
 * 获取录播主机通道输入媒体介质出参 ，对应接口{@link NetSDKLib#CLIENT_GetInputChannelMedia}
 *
 * @author ： 47040
 * @since ： Created in 2020/9/28 16:03
 */
public class NET_OUT_GET_INPUT_CHANNEL_MEDIA extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int dwSize;
    /**
     * 通道数量
     */
    public int nChannelNum;
    /**
     * 输入媒体介质 {@link NET_ENUM_INPUT_CHANNEL_MEDIA}
     */
    public int[] emInputMedia = new int[NetSDKLib.MAX_PREVIEW_CHANNEL_NUM];

    public NET_OUT_GET_INPUT_CHANNEL_MEDIA() {
        dwSize = this.size();
    }
}

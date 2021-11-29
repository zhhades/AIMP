package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

/**
 * className：NET_FAN_INFO
 * description：风扇信息
 * author：251589
 * createTime：2021/2/25 13:40
 *
 * @version v1.0
 */

public class NET_FAN_INFO extends NetSDKLib.SdkStructure {
    /**
     * dwSize;
     */
    public int dwSize;
    /**
     *  名称
     */
    public byte[] szName = new byte[NetSDKLib.NET_DEVICE_NAME_LEN];

    /**
     *  速度
     */
    public int nSpeed;

    public NET_FAN_INFO(){
        this.dwSize = this.size();
    }
}

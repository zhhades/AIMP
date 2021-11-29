package cn.neu.aimp.iot.constant.lib.structure;/**
 * @author 47081
 * @descriptio
 * @date 2020/11/9
 * @version 1.0
 */

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

import static cn.neu.aimp.iot.constant.lib.constant.SDKStructureFieldLenth.CFG_COMMON_STRING_32;

/**
 * @author 47081
 * @version 1.0
 * @description
 * @date 2020/11/9
 */
public class Auxs extends NetSDKLib.SdkStructure {
    public byte[] auxs=new byte[CFG_COMMON_STRING_32];
}

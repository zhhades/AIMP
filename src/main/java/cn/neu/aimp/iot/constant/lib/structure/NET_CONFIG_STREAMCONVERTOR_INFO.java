package cn.neu.aimp.iot.constant.lib.structure;

import cn.neu.aimp.iot.constant.lib.NetSDKLib;

/**
 * @author 47081
 * @version 1.0
 * @description StreamConvertor转码库信息配置
 * @date 2021/3/2
 */
public class NET_CONFIG_STREAMCONVERTOR_INFO extends NetSDKLib.SdkStructure {
  /**
   * StreamConvertor转码库 Windows: 为StreamConvertor.dll 库全路径，当路径为空时默认从当前NetSDK.dll路径下查找加载 Linux:
   * 为libStreamConvertor.so 库全路径，当路径为空时默认从系统路径下查找加载 Mac OS:
   * 为libStreamConvertor.so/libStreamConvertor.dylib 库全路径，当路径为空时默认从系统路径下查找加载 Mac
   * OS中先默认转码库是libStreamConvertor.so，尝试加载失败时候，则认为转码库是libStreamConvertor.dylib
   */
  public byte[] szStreamConvertor = new byte[NetSDKLib.MAX_PATH];
  /** 保留字节 */
  public byte[] bReserved = new byte[764];
}

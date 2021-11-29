package cn.neu.aimp.iot.entity;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @author wangjiawen
 * 主要配置设备实施预览相关参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AimpRealplayConfig {

    /**
     * 设备ID
     */
    String ip;

    /**
     * 设备播放通道
     */
    int channelNum;

    /**
     * 码流类型，0-主码流，1-子码流
     */
    Integer dwStreamType;

    /**
     * 连接模式，0：TCP方式,1：UDP方式
     */
    Integer dwLinkMode;

    /**
     * 阻塞模式，0-非阻塞取流, 1-阻塞取流
     * 如果阻塞SDK内部connect失败将会有5s的超时才能够返回,不适合于轮询取流操作.
     */
    Integer bBlocked;

    /**
     * 是否进行图片获取
     */
    Integer isImgStream;

    /**
     * 待处理图片仓库最大存储大小
     */
    Integer pendingListMaxSize;

    /**
     * 已处理图片仓库最大存储大小
     */
    Integer processedListMaxSize;

    /**
     * 是否存储为本地文件,0-否，1-是
     */
    Integer isSaveFile;

    /**
     * 存储路径，将实时流存储为本地文件的路径
     */
    String filePath;

    /**
     * 是否转发到流媒体服务器
     */
    Integer isForward;

    /**
     *  流媒体服务器地址
     *  例如："rtsp://172.22.104.255/"
     */
    String streamMediaIp;

    /**
     * 是否使用自动设备云台控制
     */
    Integer isPtzControl;

    /**
     * 备注
     */
    String remark;
}

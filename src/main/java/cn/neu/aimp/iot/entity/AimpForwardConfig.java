package cn.neu.aimp.iot.entity;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @author wangjiawen
 * 配置实施流的转发和存储相关配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AimpForwardConfig {

    /**
     * 设备ID
     */
    String ip;

    /**
     * 视频高度
     */
    int height;

    /**
     * 视频宽度
     */
    int width;

    /**
     * 编码方式，0-H264,1-MPEG4
     */
    int videoCodec;

    /**
     * 封装格式
     */
    String videoFormat;

    /**
     * 备注
     */
    String remark;
}

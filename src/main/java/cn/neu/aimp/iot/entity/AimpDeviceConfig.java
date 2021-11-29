package cn.neu.aimp.iot.entity;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @author wangjiawen
 * 设备连接配置相关信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AimpDeviceConfig {
    /**
     * 设备ID
     */
    String ip;

    /**
     * 连接等待时间
     */
    Integer waitTime;

    /**
     * 尝试连接次数
     */
    Integer tryTimes;

    /**
     * 重连接等待时间
     */
    Integer reconnectTime;

    /**
     * 备注
     */
    String remark;
}

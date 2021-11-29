package cn.neu.aimp.iot.entity;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @author wangjiawen
 * 云台控制相关配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AimpPtzcontrolConfig {
    /**
     * 设备ID
     */
    String ip;

    /**
     * 云台控制指令仓库最大存储空间
     */
    int ptzStorageMaxSize;

    /**
     * 指令控制时长，一条指令执行的开始和结束时间。
     */
    int sleepTime;

    /**
     * 小目标识别阈值，低于阈值将识别为小目标
     * 建议阈值：0.2-0.4
     */
    double smallObject;

    /**
     * 冷却时间
     * 设备启动和镜头缩回后进行冷却时间，防止设备再次进行镜头拉近
     */
    int coolingTime;

    /**
     * 备注
     */
    String remark;
}

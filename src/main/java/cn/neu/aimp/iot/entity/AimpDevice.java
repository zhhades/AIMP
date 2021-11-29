package cn.neu.aimp.iot.entity;

import cn.neu.aimp.iot.constant.ModuleDaHua;
import cn.neu.aimp.iot.constant.ModuleHangKang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 设备信息
 * @author wangjiawen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AimpDevice {
    private String deviceId;

    private String ip;

    private Integer port;

    private String userName;

    private String password;

    private String deviceName;

    private String deviceType;

    private String firstDir;

    private String secondDir;

    private String location;

    private Date lastLoginTime;

    private String currentState;

    private ModuleHangKang hangKang;

    private ModuleDaHua daHua;

    public AimpDevice(String userName, String deviceId, String password, String ip, int port) {
        this.userName = userName;
        this.deviceId = deviceId;
        this.password = password;
        this.ip = ip;
        this.port = port;
        this.lastLoginTime = new Date(System.currentTimeMillis());
        this.currentState = "在线";
    }
}

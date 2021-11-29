package cn.neu.aimp.iot.module;

import cn.neu.aimp.iot.entity.AimpDevice;
import cn.neu.aimp.iot.entity.AimpDeviceConfig;
import cn.neu.aimp.iot.constant.ModuleDaHua;
import cn.neu.aimp.iot.constant.ModuleHangKang;
import cn.neu.aimp.iot.util.Tool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangjiawen
 * 主要集成多厂商登录、注销功能
 */
public class LoginAndOutModule {
    /**
     * 当前登录设备列表，key是设备id，value是设备对象。
     */
    public static HashMap<String, AimpDevice> devices = new HashMap<>();

    /**
     * 登录模块 单独登录注册一个设备 需要输入配置信息
     * @param deviceType 海康或者大华等设备名称
     * @param ip 设备所在ip
     * @param port 设备端口
     * @param userName 登录用户名称
     * @param password 登录用户密码
     * @param aimpDeviceConfig 设备登录配置信息
     * @return Device
     */
    public AimpDevice login(String deviceType, String ip, int port, String userName,
                            String password, AimpDeviceConfig aimpDeviceConfig) throws IOException {
        AimpDevice aimpDevice = null;
        switch (deviceType) {
            case "dahua":
                ModuleDaHua daHua = new ModuleDaHua(aimpDeviceConfig);
                boolean dahuaLoginId = daHua.login(ip, port, userName, password);
                if (dahuaLoginId) {
                    aimpDevice = new AimpDevice(userName, "dh"+daHua.m_hLoginHandle.longValue(), password, ip, port);
                    aimpDevice.setDaHua(daHua);
                    devices.put("dh"+daHua.m_hLoginHandle.longValue(), aimpDevice);
                }
                break;
            case "haikang":
                ModuleHangKang hangKang = new ModuleHangKang(aimpDeviceConfig);
                int haikangLoginId = hangKang.login(ip, port, userName, password);
                if(haikangLoginId>-1){
                    aimpDevice = new AimpDevice(userName,"hk"+haikangLoginId, password, ip, port);
                    aimpDevice.setHangKang(hangKang);
                    devices.put("hk"+haikangLoginId, aimpDevice);
                }
                break;
            default:
                break;
        }
        if(aimpDevice !=null){
            Tool.deviceService.insert(aimpDevice);
        }
        return aimpDevice;
    }

    /**
     *登录模块 单独登录注册一个设备 使用默认配置信息
     * @param deviceName 设备名称
     * @param ip 设备ip
     * @param port 设备端口号
     * @param userName 用户名
     * @param password 密码
     * @return Device 设备信息
     * @throws IOException
     */
    public AimpDevice login(String deviceName, String ip, int port, String userName, String password) throws IOException {
        AimpDeviceConfig aimpDeviceConfig = Tool.deviceConfigService.queryById(ip);
        if(aimpDeviceConfig ==null){
            aimpDeviceConfig = Tool.deviceConfigService.queryById("默认");
        }
        return login(deviceName, ip, port, userName, password, aimpDeviceConfig);
    }

    /**
     * 初始化登录设备 从数据库当中批量增加设备
     *
     * @throws IOException
     */
    public void loginInit() throws IOException {
        List<AimpDevice> devicesList = Tool.deviceService.queryAllByLimit(0,10);
        for(AimpDevice aimpDevice : devicesList){
            login(aimpDevice.getUserName(), aimpDevice.getIp(), aimpDevice.getPort(),
                    aimpDevice.getUserName(), aimpDevice.getPassword());
        }
    }

    /**
     * 注销模块
     * @param id 设备ID
     * @return
     */
    public boolean logout(String id){
        boolean logoutFlag;
        if(id.startsWith("dh") && devices.get(id).getDaHua().logout()){
            Tool.deviceService.deleteById(id);
            logoutFlag = true;
        }
        else if(id.startsWith("hk") && devices.get(id).getHangKang().logout()){
            Tool.deviceService.deleteById(id);
            logoutFlag = true;
        }
        else{
            logoutFlag = false;
        }
        devices.remove(id);
        return logoutFlag;
    }









}

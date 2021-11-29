package cn.neu.aimp.iot.util;

import cn.neu.aimp.iot.dao.AimpDeviceConfigDao;
import cn.neu.aimp.iot.service.impl.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangjiawen
 */
public class Tool {
    static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    public static AimpRealplayConfigServiceImpl realplayConfigService= context.getBean(AimpRealplayConfigServiceImpl.class);

    public static AimpForwardConfigServiceImpl forwardConfigService= context.getBean(AimpForwardConfigServiceImpl.class);

    public static AimpPtzcontrolConfigServiceImpl ptzcontrolConfigService= context.getBean(AimpPtzcontrolConfigServiceImpl.class);

    public static AimpDeviceConfigServiceImpl deviceConfigService = context.getBean(AimpDeviceConfigServiceImpl.class);

    public static AimpDeviceServiceImpl deviceService = context.getBean(AimpDeviceServiceImpl.class);


}

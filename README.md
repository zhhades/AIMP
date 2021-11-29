# AIMP（AI中台）——Iot模块
## 项目说明
本项目主要实现AIMP模块的设备管理模块，包括设备连接和登录，视频流的编码和解码，视频流帧图像获取，设备云台控制，数据推流等功能。
### 设备登录连接
主要实现海康威视和浙江大华两个品牌网络摄像头的登录连接，后续品牌摄像头也可以添加。
### 视频流的编码和解码
主要对视频流获取到的裸码流进行解码和编码，主要码流是H264和MPEG-4，而大部分品牌设备也会支持这两个码流格式，本项目主要应用ffmpeg的Java API实现相关功能。
### 帧的图片化流
为了AI中台处理更加便捷，本项目提供视频流中的每一帧将其转化为图片流，以便更快的进行图像处理和分析。
### 设备云台控制
本项目提供设备主动巡航，设备自动追踪目标，设备抓拍，镜头拉伸，夜间红外等功能。
### 数据推流
这里主要完成将设备的视频流推送到流媒体服务器，AI中台处理完毕的图片流转化为为视频流推送的流媒体服务器。

## 快速开始
    //实例化登录模块
    LoginAndOutModule loginAndOutModule = new LoginAndOutModule();
    //设备登录，获取设备实例
    AimpDevice haikang = loginAndOutModule.login("haikang", "192.168.1.61", 8000, "admin", "abc123..");
    //实例化实时播放模块
    RealPlayModule realPlayModule = new RealPlayModule();
    //调用实时播放函数，开始实时播放。主要输入是设备实例获得的设备ID，其余包括实时播放配置、转发流媒体配置、云台控制配置都是可选。
    Object[] objects = realPlayModule.realPlay(haikang.getDeviceId(), null, null, null);
    //实时播放函数的返回值是两项，一项是图片存储仓库，即为一帧一帧的数据转化为的图片，另一项是控制指令仓库，主要是将处理后的目标位置和标签返回，一遍自动化云台控制。
    //注意当在实时播放配置中设置不自动进行云台控制，则返回值当中的控制指令仓库为NULL。
    ImgStorage imgStorage = (ImgStorage) objects[0];
    PtzStorage ptzStorage = (PtzStorage) objects[1];
    int i = 0;
    while (true){
    	//获取图片
    	BufferedImage bufferedImage = imgStorage.consumePendingImg();
    	//处理图片，eg:存储，检测等
    	ImageIO.write(bufferedImage, "jpg", new File("E:/test/" + i + ".jpg"));
    	//处理完图片以后，将图片返回给图片仓库，存入已处理图片仓库。
        imgStorage.produceProcessedImg(bufferedImage);c
    	//可选 如果开启自动云台控制 则将处理后的检测信息封装为DetectedObj链表范围给控制指令仓库。
    	ptzStorage.produce(new LinkedList<DetectedObj>());
    }
## 配置介绍
关于设备的配置主要包括四个部分，设备连接配置，实时播放配置，云台控制配置，转发流媒体配置。
### 设备连接配置
设备连接配置主要是配置设备连接超时时间、设备重连接次数、设备重连接时间等连接参数。
使用方式：

    AimpDeviceConfig deviceConfig = new AimpDeviceConfig("",10000, 1, 2000, "");
    
实例化设备配置以后可以当做参数传入到登录函数中，实现设备连接配置，若不传或者传入参数为空，则使用默认配置。        
### 实时播放配置
设备连接配置主要是配置设备当前播放通道、码流类型、连接模式、阻塞模式、是否获取图片流、待处理图片仓库最大存储大小、已处理图片仓库最大存储大小、是否存储为本地文件、存储路径、是否转发到流媒体服务器、流媒体服务器地址、是否使用自动设备云台控制等。
使用方式：

    AimpRealplayConfig aimpRealplayConfig = new AimpRealplayConfig();
    aimpRealplayConfig.setIsPtzControl(0);
    aimpRealplayConfig.setIsForward(0);
    aimpRealplayConfig.setBBlocked(0);
    aimpRealplayConfig.setChannelNum(1);
    aimpRealplayConfig.setDwLinkMode(0);
    aimpRealplayConfig.setIsImgStream(1);
    aimpRealplayConfig.setIsSaveFile(0);
    aimpRealplayConfig.setIp(haikang.getIp());
    aimpRealplayConfig.setDwStreamType(0);
    aimpRealplayConfig.setPendingListMaxSize(30);
    aimpRealplayConfig.setProcessedListMaxSize(30);
    
实例化实时播放配置以后可以当做参数传入到实时播放函数中，实现实时播放配置，若不传或者传入参数为空，则使用默认配置。
### 云台控制配置
云台控制配置主要是配置云台控制指令仓库最大存储空间、指令控制时长（一条指令执行的开始和结束时间）、小目标识别阈值（低于阈值将识别为小目标）、冷却时间（设备启动和镜头缩回后进行冷却时间，防止设备再次进行镜头拉近）等
使用方式：

    AimpPtzcontrolConfig ptzcontrolConfig = new AimpPtzcontrolConfig();
    ptzcontrolConfig.setCoolingTime(6);
    ptzcontrolConfig.setPtzStorageMaxSize(30);
    ptzcontrolConfig.setSleepTime(150);
    ptzcontrolConfig.setSmallObject(0.3);

实例化云台配置以后可以当做参数传入到实时播放函数中，实现设备云台控制，若不传或者传入参数为空，则使用默认配置。
### 转发流媒体配置
转发流媒体配置主要是配置传输的视频高度、视频宽度、编码方式（如H264和MPEG4等）、视频封格式等。
使用方式：

    AimpForwardConfig forwardConfig = new AimpForwardConfig();
    forwardConfig.setHeight(1080);
    forwardConfig.setWidth(1920);
    forwardConfig.setVideoCodec(0);
    forwardConfig.setVideoFormat("mp4");

实例化流媒体配置以后可以当做参数传入到实时播放函数中，实现设备云台控制，若不传或者传入参数为空，则使用默认配置。
## 模块介绍

## BUG反馈
当前项目处于初步开发，在使用过程中会遇到很多问题，还请在在使用过程中遇到的bug及时反馈给作者，邮箱:keon.wangjw@outlook.com


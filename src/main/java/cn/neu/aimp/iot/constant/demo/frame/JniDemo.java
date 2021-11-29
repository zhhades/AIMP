package cn.neu.aimp.iot.constant.demo.frame;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

import cn.neu.aimp.iot.constant.common.CaseMenu;
import cn.neu.aimp.iot.constant.lib.NetSDKLib;
import cn.neu.aimp.iot.constant.lib.ToolKits;
import com.company.NetSDK.*;
import cn.neu.aimp.iot.constant.lib.LibraryLoad;
import com.sun.jna.ptr.IntByReference;

public class JniDemo {

	static{ 		 
		System.load(LibraryLoad.getLoadLibrary("dhnetsdk"));
		System.load(LibraryLoad.getLoadLibrary("dhconfigsdk"));
		System.load(LibraryLoad.getLoadLibrary("jninetsdk"));}
	
	private static BufferedImage globalBufferedImage = null;

	private static BufferedImage personBufferedImage = null;

	// 用于人脸检测
	private static int groupId = 0; 
	
	// private static BufferedImage globalBufferedImage = null;
	/* public static final NetSDKLib netSdk = NetSDKLib.NETSDK_INSTANCE; */
	// 登陆句柄
	private static long loginHandle = 0;
	private static long result=0;
	private static  int type=EM_FILE_QUERY_TYPE.SDK_FILE_QUERY_FACE;
	// 设备信息扩展
	private NET_DEVICEINFO_Ex deviceInfo = new NET_DEVICEINFO_Ex();

	/**
	 * 获取接口错误码
	 * @return
	 */
	public static String getErrorCode() {
		return " { error code: ( 0x80000000|" + (-2147483646 & 0x7fffffff) +" ). 参考  LastError.java }";
	}
	
	public void InitTest(String m_strIp, int m_nPort, String m_strUser, String m_strPassword) {
		// 初始化SDK库
		INetSDK.Init(DisConnectCallBack.getInstance());/* (DisConnectCallBack.getInstance()); */

		// 设置断线重连成功回调函数
		INetSDK.SetAutoReconnect(HaveReConnectCallBack.getInstance());/* (HaveReConnectCallBack.getInstance(), null); */

		// 打开日志，可选
		LOG_SET_PRINT_INFO setLog = new LOG_SET_PRINT_INFO();
		String logPath = new File(".").getAbsoluteFile().getParent() + File.separator + "sdk_log" + File.separator
				+ "sdk.log";
		setLog.bSetFilePath = true;
		System.arraycopy(logPath.getBytes(), 0, setLog.szLogFilePath, 0, logPath.getBytes().length);
		setLog.bSetPrintStrategy = true;
		setLog.nPrintStrategy = 0;
		if (!INetSDK.LogOpen(setLog)/* (setLog) */) {
			System.err.println("Open SDK Log Failed!!!");
		}

		Login(m_strIp,m_nPort,m_strUser,m_strPassword);
	}

	public void Login(String m_strIp, int m_nPort, String m_strUser, String m_strPassword) {

		// 登陆设备
		// int nSpecCap = NetSDKLib.EM_LOGIN_SPAC_CAP_TYPE.EM_LOGIN_SPEC_CAP_TCP; //
		// TCP登入
		IntByReference nError = new IntByReference(0);
		//for(int i=0 ;i<4 ;i++) {		
		loginHandle = INetSDK.LoginEx2(m_strIp, m_nPort, m_strUser, m_strPassword, 0, null, deviceInfo, 0);
		if (loginHandle != 0) {
			System.out.printf("Login Device[%s] Success!\n", m_strIp);
			//FindFileEx();
			//FindNextFileEx();
		} else {
			System.err.printf("Login Device[%s] Fail.Error[%s]\n", m_strIp, getErrorCode());
			LoginOut();
		//}
		}
	}

	public void LoginOut() {
		System.out.println("End Test");
		if (loginHandle != 0) {
			INetSDK.Logout(loginHandle)/* (loginHandle) */;
		}
		System.out.println("See You...");

		INetSDK.Cleanup();
		System.exit(0);
	}

////////////////////////////////////////////////////////////////
/*	private String m_strIp = "172.23.12.138";
	private int m_nPort = 37777;
	private String m_strUser = "admin";
	private String m_strPassword = "admin123";*/
////////////////////////////////////////////////////////////////

	public void findRecognationInfo () {
		Long time = System.currentTimeMillis();
		MEDIAFILE_FACERECOGNITION_PARAM file =new MEDIAFILE_FACERECOGNITION_PARAM();
		MEDIAFILE_FACERECOGNITION_INFO []  faceRecognitionInfo = new MEDIAFILE_FACERECOGNITION_INFO[10];
		for (int i = 0; i < faceRecognitionInfo.length; ++i) {
			faceRecognitionInfo[i] = new MEDIAFILE_FACERECOGNITION_INFO();
			faceRecognitionInfo[i].bUseCandidatesEx = true;
		}
		// 开始时间
		file.stStartTime.dwYear = 2021;
		file.stStartTime.dwMonth = 5;
		file.stStartTime.dwDay = 1;
		file.stStartTime.dwHour = 13;
		file.stStartTime.dwMinute = 0;
		file.stStartTime.dwSecond = 0;
		
		// 结束时间
		file.stEndTime.dwYear = 2021;
		file.stEndTime.dwMonth = 5;
		file.stEndTime.dwDay = 8;
		file.stEndTime.dwHour = 13;
		file.stEndTime.dwMinute = 0;
		file.stEndTime.dwSecond = 0;
		file.nAlarmType = 1;
		// 通道号
		file.nChannelId = 5;

		/*// 人员组数
		file.nGroupIdNum = 1;

		// 人员组ID(人脸库ID)
		String groupId = "1";
		System.arraycopy(groupId.getBytes(), 0, file.szGroupId[0], 0, groupId.getBytes().length);*/
		long fileLogin = INetSDK.FindFileEx(loginHandle,3, file, 3000);
        if (fileLogin == 0) {
            System.err.println("FindFile(FaceRecognition) Failed!");
            return;
        }
        System.out.println("检索指令下发成功，检索句柄：" + fileLogin);
        
		//循环查询
		int nCurCount = 0;
		int nFindCount = 0;
		while(true) {
        int nRetCount = INetSDK.FindNextFileEx(fileLogin, 3, faceRecognitionInfo, 3000);
        if (nRetCount <= 0) {
        	System.err.printf("FindNextFileEx failed! Fail.Error[%s]\n", getErrorCode());
            break;
		} 

		for (int i = 0; i < nRetCount; i++) {
			nFindCount = i + nCurCount * 10;
			System.out.println("[" + nFindCount + "]通道号 :" + faceRecognitionInfo[i].nChannelId);
			System.out.println("[" + nFindCount + "]报警发生时间 :" + faceRecognitionInfo[i].stTime.toString());

			// 人脸图
			System.out.println("[" + nFindCount + "]人脸图路径 :" + new String(faceRecognitionInfo[i].stObjectPic.szFilePath).trim());
			
			// 对比图
			System.out.println("[" + nFindCount + "]匹配到的候选对象数量 :" + faceRecognitionInfo[i].nCandidateNum);
			for(int j = 0; j < faceRecognitionInfo[i].nCandidateNum; j++) {  
				for(int k = 0; k < faceRecognitionInfo[i].stuCandidatesPic[j].nFileCount; k++) {
					System.out.println("[" + nFindCount + "]对比图路径 :" + new String(faceRecognitionInfo[i].stuCandidatesPic[j].stFiles[k].szFilePath).trim());
				}	
			}	

			// 对比信息   
			System.out.println("[" + nFindCount + "]匹配到的候选对象数量 :" + faceRecognitionInfo[i].nCandidateExNum);
			for(int j = 0; j < faceRecognitionInfo[i].nCandidateExNum; j++) {  
				System.out.println("[" + nFindCount + "]人员唯一标识符 :" + new String(faceRecognitionInfo[i].stuCandidatesEx[j].stPersonInfo.szUID).trim());
				
				// 以下参数，设备有些功能没有解析，如果想要知道   对比图的人员信息，可以根据上面获取的 szUID，用 findFaceRecognitionDB() 来查询人员信息。
				// findFaceRecognitionDB() 此示例的方法是根据 GroupId来查询的，这里的查询，GroupId不填，根据 szUID 来查询
				System.out.println("[" + nFindCount + "]姓名 :" + new String(faceRecognitionInfo[i].stuCandidatesEx[j].stPersonInfo.szPersonName).trim());
				System.out.println("[" + nFindCount + "]相似度 :" + faceRecognitionInfo[i].stuCandidatesEx[j].bySimilarity);
				System.out.println("[" + nFindCount + "]年龄 :" + faceRecognitionInfo[i].stuCandidatesEx[j].stPersonInfo.byAge);
				System.out.println("[" + nFindCount + "]人脸库名称 :" + new String(faceRecognitionInfo[i].stuCandidatesEx[j].stPersonInfo.szGroupName).trim());
				System.out.println("[" + nFindCount + "]人脸库ID :" + new String(faceRecognitionInfo[i].stuCandidatesEx[j].stPersonInfo.szGroupID).trim());
			}
			
			System.out.println();
		}
		
		if(nRetCount < 10) {
			break;
		} else {
			nCurCount++;
		}
	}
		System.out.println(nCurCount);
		INetSDK.FindCloseEx(fileLogin);	
		Long time1 = System.currentTimeMillis();
		System.out.println("------------------------------"+(time1-time)+"------------------------------------");
}
	
	
	public void RealloadPic() {
		long result = INetSDK.RealLoadPictureEx(loginHandle, 0, FinalVar.EVENT_IVS_ALL, true,
				fAnalyzerDataCallBack.getInstance(), null);
		if (result != 0) {
			System.out.println("RealloadPic success");
		} else {
			System.out.println("RealloadPic field");
		}
	}

	public void StartListenEx() {
		INetSDK.SetDVRMessCallBack(fmessDataCallBack.getInstance());
		boolean result = INetSDK.StartListenEx(loginHandle);
		if (result) {
			System.out.println("RealloadPic success");
		} else {
			System.out.println("RealloadPic field");
		}
	}
	

	public void MonitorWall() {	
		Long time = System.currentTimeMillis();
		String command =FinalVar.CFG_CMD_MONITORWALL;
		
		int ruleCount = 10;  // 事件规则个数
		
		AV_CFG_MonitorWall[] monitorWall = new AV_CFG_MonitorWall[ruleCount];
   		for(int i =0;i<ruleCount ;i++) {
   			monitorWall[i] = new AV_CFG_MonitorWall();
   		}
		Integer error = new Integer(0);
		Integer restart = new Integer(0);
		int nBufferLen = 1024 * 1024 * 2;
		char[] szBuffer =new char[nBufferLen];
		int bBufferLen = 1024 * 1024 * 2;
		
		if(INetSDK.GetNewDevConfig( loginHandle, command , 0, szBuffer,nBufferLen,error,10000)) {
			//System.out.println(new String (szBuffer).trim());
			 if(!INetSDK.ParseData(command ,szBuffer , monitorWall , null ) )
	            {
	                return;
	            }
			 for(int i = 0 ;i<monitorWall.length ; i++) {
				 System.out.println("电视墙禁用使能"+monitorWall[i].bDisable 
						            + "网格列数"+monitorWall[i].nColumn
						            +"电视墙信息"+ new String(monitorWall[i].szDesc));
				 monitorWall[i].bDisable = true;
			 }
			 
			// 设置	
				if(INetSDK.PacketData(command, monitorWall, szBuffer, bBufferLen))
		        {
		            if( INetSDK.SetNewDevConfig(loginHandle,command , 0 , szBuffer, nBufferLen, error, restart, 10000))
		            {
		            	System.out.println("设置使能成功!");
		            }
		            else
		            {	                
		                System.err.println("Set Config Failed!");
		            }
		        }
		        else
		        {
		            System.err.println("Packet Config Failed!");
		        }
		}else {
			System.err.println("GetNewDevConfig Failed!");
		}
		Long time1 = System.currentTimeMillis();
		System.out.println("------------------------------"+(time1-time)+"------------------------------------");
	}
	
	public void SetFaceDetectEnable() {
		
		  Scanner scanner = new Scanner(System.in);
		  
		  String line = ""; 
		  while(true) { 
			  System.out.println("请输入通道号："); 
			  line =scanner.nextLine(); 
			  if(!line.equals("")) 
			  break; 
			  } 
		  scanner.close();		 
		long time = System.currentTimeMillis();
		int channel = Integer.parseInt(line); // 通道号
		String command =FinalVar.CFG_CMD_ANALYSERULE;
		
		int ruleCount = 10;  // 事件规则个数
		
		CFG_ANALYSERULES_INFO analyse = new CFG_ANALYSERULES_INFO(ruleCount); 			   		
		Integer error = new Integer(0);
		Integer restart = new Integer(0);
		int nBufferLen = 1024 * 1024 * 2;
		char[] szBuffer =new char[nBufferLen];
		int bBufferLen = 1024 * 1024 * 2;
		// 获取
		if(INetSDK.GetNewDevConfig( loginHandle, command , channel, szBuffer,nBufferLen,error,10000)) {
			//System.out.println(new String (szBuffer).trim());
			 if(!INetSDK.ParseData(command ,szBuffer , analyse , null ) )
	            {
	                return;
	            }			
			System.out.println("设备返回的事件规则个数:" + analyse.nRuleCount);
			
			int count = analyse.nRuleCount < ruleCount? analyse.nRuleCount : ruleCount;
			
			for(int i = 0; i < count; i++) {

				switch (analyse.pRuleBuf[i].dwRuleType) {
					case NetSDKLib.EVENT_IVS_FACERECOGNITION:   // 人脸识别
					{
						CFG_FACERECOGNITION_INFO msg = (CFG_FACERECOGNITION_INFO)analyse.pIvsRuleBuf[i];											
						
						System.out.println("规则名称：" + new String(msg.szRuleName).trim());
						System.out.println("使能：" + msg.bRuleEnable);											
						
						// 设置使能开
						// 设置使能开
						msg.bRuleEnable = true;				
						analyse.pIvsRuleBuf[i] = msg;
						
						break;
					}
					case NetSDKLib.EVENT_IVS_FACEDETECT:    // 人脸检测
					{
						CFG_FACEDETECT_INFO msg = (CFG_FACEDETECT_INFO)analyse.pIvsRuleBuf[i];
						
						System.out.println("规则名称：" + new String(msg.szRuleName).trim());
						System.out.println("使能：" + msg.bRuleEnable);
						
	
						// 设置使能开
						msg.bRuleEnable = false;				
						analyse.pIvsRuleBuf[i] = msg;
						
						break;
					}
					case NetSDKLib.EVENT_IVSS_FACECOMPARE: 		// IVSS人脸识别
					{
						CFG_FACECOMPARE_INFO msg = (CFG_FACECOMPARE_INFO)analyse.pIvsRuleBuf[i];			
						
						System.out.println("规则名称：" + new String(msg.szRuleName).trim());
						System.out.println("使能：" + msg.bRuleEnable);
						
						// 设置使能开
						msg.bRuleEnable = false;				
						analyse.pIvsRuleBuf[i] = msg;
						break;
					}
					/*case NetSDKLib.EVENT_IVS_FACEANALYSIS: 	// IVSS人脸分析
					{
						CFG_FACEANALYSIS_INFO msg = new CFG_FACEANALYSIS_INFO();
						ToolKits.GetPointerDataToStruct(analyse.pRuleBuf, offset, msg);
						
						System.out.println("规则名称：" + new String(msg.szRuleName).trim());
						System.out.println("使能：" + msg.bRuleEnable);
						
						// 设置使能开
						msg.bRuleEnable = 1;	
						msg.stuStrangerMode.stuEventHandler.abBeepEnable=1;
						msg.stuStrangerMode.stuEventHandler.bBeepEnable=1;
						msg.stuStrangerMode.stuEventHandler.abVoiceEnable=1;
						msg.stuStrangerMode.stuEventHandler.bVoiceEnable=0;
						ToolKits.SetStructDataToPointer(msg, analyse.pRuleBuf, offset);
						
						break;
					}*/
					/*case NetSDKLib.EVENT_IVSS_FACECOMPARE: 		// IVSS人脸识别
					{
						CFG_FACECOMPARE_INFO msg = new CFG_FACECOMPARE_INFO();
						ToolKits.GetPointerDataToStruct(analyse.pRuleBuf, offset, msg);
						
						System.out.println("规则名称：" + new String(msg.szRuleName).trim());
						System.out.println("使能：" + msg.bRuleEnable);
						
						// 设置使能开
						msg.bRuleEnable = 1;
						msg.stuStrangerMode.stuEventHandler.abBeepEnable=1;
						msg.stuStrangerMode.stuEventHandler.bBeepEnable=1;
						//msg.write();
						ToolKits.SetStructDataToPointer(msg, analyse.pRuleBuf, offset);
						
						break;
					}*/
					case NetSDKLib.EVENT_IVS_CROSSLINEDETECTION :
					{
						System.out.println("警戒线");
						break;
					}
					case NetSDKLib.EVENT_IVS_CROSSREGIONDETECTION :
					{
						System.out.println("警戒区");
						break;
					}
					default:
						break;
				}
			}

			for(int i=0; i<nBufferLen; i++)szBuffer[i]=0;
			// 设置	
			if(INetSDK.PacketData(command, analyse, szBuffer, bBufferLen))
	        {
	            if( INetSDK.SetNewDevConfig(loginHandle,command , channel , szBuffer, nBufferLen, error, restart, 10000))
	            {
	            	System.out.println("设置使能成功!");
	            }
	            else
	            {	                
	                System.err.println("Set Config Failed!" + ToolKits.getErrorCodePrint());
	            }
	        }
	        else
	        {
	            System.err.println("Packet Config Failed!" + ToolKits.getErrorCodePrint());
	        }	       
	    }else {
			System.err.println("获取使能失败!" + ToolKits.getErrorCodePrint());
		}
		long time1 = System.currentTimeMillis();
		System.out.println(time1-time);
	}
	
	
	public void FindFileEx() {
		
		MEDIAFILE_FACERECOGNITION_PARAM stQueryPar =new MEDIAFILE_FACERECOGNITION_PARAM(); 
			// 历史库开始时间->"StartTime"
		 NET_TIME startTime = new NET_TIME();
		 startTime.dwDay=11;
		 startTime.dwHour=0;
		 startTime.dwMinute=0;
		 startTime.dwMonth=5;
		 startTime.dwSecond=0;
		 startTime.dwYear=2020;
		 
			// 历史库结束时间->"EndTime"
		 NET_TIME EndTime = new NET_TIME();
		 EndTime.dwDay=11;
		 EndTime.dwHour=23;
		 EndTime.dwMinute=5;
		 EndTime.dwMonth=59;
		 EndTime.dwSecond=59;
		 EndTime.dwYear=2020;
			// AI历史库来源通道号
			    		stQueryPar.nChannelId=2;
			    		stQueryPar.stEndTime=EndTime;
			    		stQueryPar.stStartTime=startTime;
			    		stQueryPar.nAlarmType = 1;
			    		stQueryPar.abPersonInfo=  true;
			    		stQueryPar.stPersonInfoEx.bAgeEnable = true;

			// 设置检测相似度区间 80 至 100
			    		stQueryPar.stPersonInfoEx.nAgeRange[0] =1;
			    		stQueryPar.stPersonInfoEx.nAgeRange[1] =30;
			    		stQueryPar.stPersonInfoEx.bySex=1;
			    		stQueryPar.bSimilaryRangeEnable=true;
			    		stQueryPar.nSimilaryRange[0] =1;
			    		stQueryPar.nSimilaryRange[1] =100;

		/*
		 * stQueryPar.bSimilaryRangeEnable=true; stQueryPar.nSimilaryRange[0] =80;
		 * stQueryPar.nSimilaryRange[1] =100;
		 */

		result = INetSDK.FindFileEx(loginHandle, type, stQueryPar, 5000);
		if(result!=0) {
			System.out.println("FindFileEx success" );
		}else {
			System.err.printf("FindFileEx fail, ErrCode=%x\n" , INetSDK.GetLastError());
		}
	}
	

   public void FindNextFileEx() {
	   MEDIAFILE_FACERECOGNITION_INFO stQueryFile[] =new MEDIAFILE_FACERECOGNITION_INFO[2];
	   for(int i=0 ;i<2 ;i++) {
		   stQueryFile[i]=new MEDIAFILE_FACERECOGNITION_INFO();
	   }

	int filecount = INetSDK.FindNextFileEx(result, type, stQueryFile, 5000);
	if(filecount>0) {
		for(int l=0 ;l<filecount ;l++) {
			System.out.println(stQueryFile[l].nChannelId);
		}
	}
    }
	static int i = 0;
	static int j = 0;
	private static class fAnalyzerDataCallBack implements CB_fAnalyzerDataCallBack {

		private static class CallBackHolder {
			private static fAnalyzerDataCallBack instance = new fAnalyzerDataCallBack();
		}

		public static fAnalyzerDataCallBack getInstance() {
			return CallBackHolder.instance;
		}

		@Override
		public void invoke(long arg0, int arg1, Object arg2, byte[] arg3, int arg4, int arg5, int arg6) {
			// TODO Auto-generated method stub
			System.out.println("common" + arg1);

			/*
			 * File path = new File("./EventPicture/"); if (!path.exists()) { path.mkdir();
			 * }
			 */

			switch (arg1) {
			case FinalVar.EVENT_IVS_FACERECOGNITION:{
				i++;
				DEV_EVENT_FACERECOGNITION_INFO facerecognitionInfo = (DEV_EVENT_FACERECOGNITION_INFO) arg2;
			
				System.out.println("第"+i+"人脸识别，时间：");
				///////////////////////////////////////// 全景图
				///////////////////////////////////////// ////////////////////////////////////
				File path = new File("./EventPicture/");
	            if (!path.exists()) {
	                path.mkdir();
	            }
				String strFileName = "";
				if (facerecognitionInfo.bGlobalScenePic) {
					/* if (ToolKits.createFile("/mnt/sdcard/NetSDK/", "全景图.jpg")) { */
					strFileName = "./EventPicture/"+System.currentTimeMillis()+"全景图.jpg";
					/* } */
					if ("" != strFileName) {
						FileOutputStream fileStream;
						try {
							fileStream = new FileOutputStream(strFileName, true);
							fileStream.write(arg3, facerecognitionInfo.stuGlobalScenePicInfo.dwOffSet,
									facerecognitionInfo.stuGlobalScenePicInfo.dwFileLenth);
							fileStream.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				/////////////////////////////////////////////// 人脸信息
				///////////////////////////////////
				if (facerecognitionInfo.stuObject.stPicInfo != null) {

					/* if (ToolKits.createFile("/mnt/sdcard/NetSDK/", "人脸图.jpg")) { */
						//strFileName = "./EventPicture/人脸图.jpg";
					/* } */

					strFileName = "./EventPicture/"+System.currentTimeMillis()+"人脸图.jpg";
					if ("" != strFileName) {
						FileOutputStream fileStream;
						try {
							fileStream = new FileOutputStream(strFileName, true);
							fileStream.write(arg3, facerecognitionInfo.stuObject.stPicInfo.dwOffSet,
									facerecognitionInfo.stuObject.stPicInfo.dwFileLenth);
							fileStream.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			case FinalVar.EVENT_IVS_FACEDETECT:{
				j++;
				DEV_EVENT_FACEDETECT_INFO faceDetectInfo = new DEV_EVENT_FACEDETECT_INFO();
				System.out.println("第"+j+"人脸识别，时间：");
				
				
				File path = new File("./FaceDetection/");
		        if (!path.exists()) {
		            path.mkdir();
		        }

		        if (arg3 == null || arg3.length <= 0) {
		        	return;
		        }	   
		        
		        // 小图的 stuObject.nRelativeID 来匹配大图的 stuObject.nObjectID，来判断是不是 一起的图片
		        if(groupId != faceDetectInfo.stuObject.nRelativeID) {   ///->保存全景图 
		        	personBufferedImage = null;
		        	groupId = faceDetectInfo.stuObject.nObjectID;
		        				
					String strGlobalPicPathName = path + "\\" + System.currentTimeMillis() + "_FaceDetection_Global.jpg"; 
			    	byte[] bufferGlobal = arg3;
					ByteArrayInputStream byteArrInputGlobal = new ByteArrayInputStream(bufferGlobal);
					
					try {
						globalBufferedImage = ImageIO.read(byteArrInputGlobal);
						if(globalBufferedImage != null) {
							File globalFile = new File(strGlobalPicPathName);
							if(globalFile != null) {
								ImageIO.write(globalBufferedImage, "jpg", globalFile);
							}
						}				
					} catch (IOException e2) {
						e2.printStackTrace();
					}
		        } else if(groupId == faceDetectInfo.stuObject.nRelativeID){   ///->保存人脸图
			        if(faceDetectInfo.stuObject.stPicInfo != null) {
			        	String strPersonPicPathName = path + "\\" + System.currentTimeMillis() + "_FaceDetection_Person.jpg"; 
				    	byte[] bufferPerson = arg3;
						ByteArrayInputStream byteArrInputPerson = new ByteArrayInputStream(bufferPerson);
						
						try {
							personBufferedImage = ImageIO.read(byteArrInputPerson);
							if(personBufferedImage != null) {						
								File personFile = new File(strPersonPicPathName);
								if(personFile != null) {
									ImageIO.write(personBufferedImage, "jpg", personFile);
								}	
							}			
						} catch (IOException e2) {
							e2.printStackTrace();
						}
			        }
		        }			
			}
				break;
			}
		}

	}

	private static class fmessDataCallBack implements CB_fMessageCallBack

	{

		private fmessDataCallBack() {
		}

		private static class CallBackHolder {
			private static fmessDataCallBack instance = new fmessDataCallBack();
		}

		public static fmessDataCallBack getInstance() {
			return CallBackHolder.instance;
		}

		@Override
		public boolean invoke(int arg0, long arg1, Object arg2, String arg3, int arg4) {
			// TODO Auto-generated method stub
			switch (arg0) {
				case FinalVar.SDK_ALARM_WIFI_VIRTUALINFO_SEARCH:{
					ALARM_WIFI_VIRTUALINFO_SEARCH_INFO msg = (ALARM_WIFI_VIRTUALINFO_SEARCH_INFO)arg2;
					for (int i = 0; i < msg.nVirtualInfoNum; i++) {
                        System.out.println("访问时间:" + msg.stuVirtualInfo[i].stuAccessTime.toStringTime());
                        System.out.println("虚拟信息的来源MAC:" + new String(msg.stuVirtualInfo[i].szSrcMac).trim());
                        System.out.println("虚拟信息的目标MAC:" + new String(msg.stuVirtualInfo[i].szDstMac).trim());
                        System.out.println("采集设备编号:" + new String(msg.stuVirtualInfo[i].szDevNum).trim());
                        System.out.println("虚拟用户ID:" + new String(msg.stuVirtualInfo[i].szUserID).trim());
                        System.out.println("协议代号:" + msg.stuVirtualInfo[i].nProtocal);
                    }
					break;
				}
					
				case FinalVar.SDK_ALARM_WIFI_SEARCH: {
					ALARM_WIFI_SEARCH_INFO msg =(ALARM_WIFI_SEARCH_INFO)arg2;
					NET_WIFI_DEV_INFO[] nwdi=new NET_WIFI_DEV_INFO[msg.nWifiNum];
		  			System.out.println("通道号:" + msg.nChannel);		  			
		  			// 周围Wifi设备的信息
		  			for(int i = 0; i < msg.nWifiNum; i++) {
		  				nwdi[i]=new NET_WIFI_DEV_INFO();		  				
		  				System.out.println("Wifi设备的Mac地址:" + new String(nwdi[i].szMac).trim());
		  				System.out.println("接入热点Mac的Mac地址:" + new String(nwdi[i].szAPMac).trim());
		  				System.out.println("接入热点SSID:" + new String(nwdi[i].szAPSSID).trim());
		  				System.out.println("设备类型:" + nwdi[i].emDevType);		  					  				
		  			}

	                    // Wifi事件上报基础信息
		  			System.out.println("本周期上报的wifi总数:" + msg.stuWifiBasiInfo.nDeviceSum);
		  			System.out.println("本次事件上报的Wifi设备数量:" + msg.stuWifiBasiInfo.nCurDeviceCount);
					break;
				}
					
				
			default:
				break;
			}
			return false;
		}		
	}

	
	
	/**
	 * 设备断线回调
	 */
	private static class DisConnectCallBack implements CB_fDisConnect

	{

		private DisConnectCallBack() {
		}

		private static class CallBackHolder {
			private static DisConnectCallBack instance = new DisConnectCallBack();
		}

		public static DisConnectCallBack getInstance() {
			return CallBackHolder.instance;
		}

		@Override
		public void invoke(long arg0, String arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * 设备重连回调
	 */
	private static class HaveReConnectCallBack implements CB_fHaveReConnect

	{
		private HaveReConnectCallBack() {
		}

		private static class CallBackHolder {
			private static HaveReConnectCallBack instance = new HaveReConnectCallBack();
		}

		public static HaveReConnectCallBack getInstance() {
			return CallBackHolder.instance;
		}

		@Override
		public void invoke(long arg0, String arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}

	public void RunTest() {
		System.out.println("Run Test");
		CaseMenu menu = new CaseMenu();
		menu.addItem(new CaseMenu.Item(this, "RealloadPic", "RealloadPic"));
		menu.addItem(new CaseMenu.Item(this, "StartListenEx", "StartListenEx"));
		menu.addItem(new CaseMenu.Item(this , "SetFaceDetectEnable" , "SetFaceDetectEnable"));		
		menu.addItem(new CaseMenu.Item(this , "findRecognationInfo" , "findRecognationInfo"));
		menu.addItem(new CaseMenu.Item(this , "MonitorWall" , "MonitorWall"));		
		menu.run();
	}

	public static void main(String[] args) {
		JniDemo demo = new JniDemo();
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入ip");
		String m_strIp = scanner.next();
		System.out.println("请输入port");
		int m_nPort = scanner.nextInt();
		System.out.println("请输入user");
		String m_strUser = scanner.next();
		System.out.println("请输入password");
		String m_strPassword = scanner.next();
		demo.InitTest(m_strIp,m_nPort,m_strUser,m_strPassword);
		demo.RunTest();
		demo.LoginOut();
	}
}

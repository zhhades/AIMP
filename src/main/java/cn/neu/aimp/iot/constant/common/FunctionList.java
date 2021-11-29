package cn.neu.aimp.iot.constant.common;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import cn.neu.aimp.iot.constant.demo.frame.*;

/**
 * 功能列表界面
 */
public class FunctionList extends JFrame {
	private static final long serialVersionUID = 1L;

	public FunctionList() {
		setTitle(Res.string().getFunctionList());
		setLayout(new BorderLayout());
		pack();
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);

		add(new FunctionPanel(), BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}

	public class FunctionPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public FunctionPanel() {
			setLayout(new GridLayout(9, 2));

			setBorder(new EmptyBorder(30, 50, 0, 50));

			faceRecognitionBtn = new JButton(Res.string().getFaceRecognition());
			gateBtn = new JButton(Res.string().getGate());
			capturePictureBtn = new JButton(Res.string().getCapturePicture());
			realPlayBtn = new JButton(Res.string().getRealplay());
			itsEventBtn = new JButton(Res.string().getITSEvent());
			downloadBtn = new JButton(Res.string().getDownloadRecord());
			talkBtn = new JButton(Res.string().getTalk());
			deviceSearchAndInitBtn = new JButton(Res.string().getDeviceSearchAndInit());
			ptzBtn = new JButton(Res.string().getPTZ());
			deviceCtlBtn = new JButton(Res.string().getDeviceControl());
			alarmListenBtn = new JButton(Res.string().getAlarmListen());
			autoRegisterBtn = new JButton(Res.string().getAutoRegister());
			attendanceBtn = new JButton(Res.string().getAttendance());
			thermalCameraBtn = new JButton(Res.string().getThermalCamera());
			matrixScreenBtn = new JButton(Res.string().getmatrixScreen());
			humanNumberStatisticBtn = new JButton(Res.string().getHumanNumberStatistic());
			vtoBtn = new JButton(Res.string().getVTO());

			SCADABtn = new JButton(Res.string().getSCADA());


			JNIdemo = new JButton("JNIdemo");

			add(gateBtn);
			add(faceRecognitionBtn);
			add(deviceSearchAndInitBtn);
			add(ptzBtn);
			add(realPlayBtn);
			add(capturePictureBtn);
			add(talkBtn);
			add(itsEventBtn);
			add(downloadBtn);
			add(deviceCtlBtn);
			add(alarmListenBtn);
			add(autoRegisterBtn);
			//add(attendanceBtn);
			add(thermalCameraBtn);
			add(matrixScreenBtn);
			add(humanNumberStatisticBtn);
			add(vtoBtn);
			add(SCADABtn);
			add(JNIdemo);
			gateBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();
						}
					});
				}
			});



			capturePictureBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();
						}
					});
				}
			});

			realPlayBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			downloadBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			talkBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();
						}
					});
				}
			});

			itsEventBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {

						}
					});
				}
			});

			deviceSearchAndInitBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			ptzBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			deviceCtlBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			alarmListenBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			autoRegisterBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			attendanceBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			thermalCameraBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dispose();

						}
					});
				}
			});

			
			  matrixScreenBtn.addActionListener(new ActionListener() {
				  @Override public void actionPerformed(ActionEvent e) {
					  SwingUtilities.invokeLater(new Runnable() { 
						  public void run() 
						  { 
							   dispose();

					           } 
						  });
					  }
					  });			  
			  
			  
			  humanNumberStatisticBtn.addActionListener(new ActionListener() {
			  
			  @Override public void actionPerformed(ActionEvent e) {
			  SwingUtilities.invokeLater(new Runnable() { 
				  public void run() 
				  { 
					   dispose();			           

			           } 
				  });
			  }
			  });
			  vtoBtn.addActionListener(new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent e) {
					  SwingUtilities.invokeLater(new Runnable() {
						  @Override
						  public void run() {
							  dispose();

						  }
					  });
				  }
			  });

			SCADABtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							dispose();

						}
					});
				}
			});
			 
			  JNIdemo.addActionListener(new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent e) {
					  SwingUtilities.invokeLater(new Runnable() {
						  @Override
						  public void run() {
							  dispose();
							  JniDemo.main(null);
						  }
					  });
				  }
			  });
		}

		/*
		 * 功能列表组件
		 */
		private JButton faceRecognitionBtn;
		private JButton capturePictureBtn;
		private JButton realPlayBtn;
		private JButton downloadBtn;
		private JButton itsEventBtn;
		private JButton talkBtn;
		private JButton deviceSearchAndInitBtn;
		private JButton ptzBtn;
		private JButton deviceCtlBtn;
		private JButton alarmListenBtn;
		private JButton autoRegisterBtn;
		private JButton attendanceBtn;
		private JButton gateBtn;
		private JButton thermalCameraBtn;
		private JButton matrixScreenBtn;
		private JButton humanNumberStatisticBtn;
		private JButton vtoBtn;

		/**
		 * 动环主机按钮
		 */
		private JButton SCADABtn;

		private JButton JNIdemo;

	}
}

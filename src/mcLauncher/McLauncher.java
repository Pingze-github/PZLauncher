package mcLauncher;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

/*功能拓展：
 * 设置可以保存在.cfg （完成）
 * 有Forge的情况考虑,版本的选择(完成)
 * 程序自动检索json(完成)
 * 界面可拖动(完成)
 * 程序自动检索java(完成)
 * 未选择必选项目的警告和处理（完成）
 * 错误报告界面优化
 * 支持1.8+
 * 版本号自动显示(完成)
 * UI设计(完成)
 * UI进步设计
 * warning的检查	（完成）
 */

public class McLauncher extends JFrame{
//版本号
	private static final long serialVersionUID = 1L;
//游戏路径
	protected static String gamePath=null;
//传递变量
	protected static String[] arguments=new String[5];//"javapath","maxmemory","username","version","Extra instructions"
	protected static ArrayList<String> ArrayJsonFilePath=new ArrayList<String>();
//框体
	private JPanel MainPanel,TitlePanel;
	private JLabel MainLabel,SetLabel,TNameLabel,TMaxMemLabel,TJavaPathLabel,TExtraLabel,TitleLabel,minecraftLabel;
	private JTextField TName,TMaxMem,TJavaPath,TExtra,TVersion;
	private JButton BStart,BExit,BSet,BSetExit,BSetEnter;
	private Choice CVersion;
//构造器	
	public McLauncher(){
		
		//版本号
		final String versionInfo="1.0.0.0205_alpha";
		//图标导入
		ImageIcon bgMain =new ImageIcon(McLauncher.class.getResource("/images/bgMain.png"));
		ImageIcon bgSet=new ImageIcon(McLauncher.class.getResource("/images/bgSet.png"));
		ImageIcon minecraft=new ImageIcon(McLauncher.class.getResource("/images/minecraft.png"));
		ImageIcon bStart=new ImageIcon(McLauncher.class.getResource("/images/bStart.png"));
		ImageIcon bStartHighlight=new ImageIcon(McLauncher.class.getResource("/images/bStartHighlight.png"));
		ImageIcon bExit=new ImageIcon(McLauncher.class.getResource("/images/bExit.png"));
		ImageIcon bExitHighlight=new ImageIcon(McLauncher.class.getResource("/images/bExitHighlight.png"));
		ImageIcon bSet=new ImageIcon(McLauncher.class.getResource("/images/bSet.png"));
		ImageIcon bSetHighlight=new ImageIcon(McLauncher.class.getResource("/images/bSetHighlight.png"));
		ImageIcon bSetExit=new ImageIcon(McLauncher.class.getResource("/images/bSetExit.png"));
		ImageIcon bSetExitHighlight=new ImageIcon(McLauncher.class.getResource("/images/bSetExitHighlight.png"));
		ImageIcon bSetEnter=new ImageIcon(McLauncher.class.getResource("/images/bSetEnter.png"));
		ImageIcon bSetEnterHighlight=new ImageIcon(McLauncher.class.getResource("/images/bSetEnterHighlight.png"));
		ImageIcon textLabel=new ImageIcon(McLauncher.class.getResource("/images/textLabel.png"));
		ImageIcon titleLabel=new ImageIcon(McLauncher.class.getResource("/images/titleLabel.png"));
		int WidthOfMainPanel=bgMain.getIconWidth();
		int HeightOfMainPanel=bgMain.getIconHeight();
		//自定义背景
		try{
			File bgFolder=new File(gamePath+"\\bgImage");
			if(!bgFolder.exists()&&!bgFolder.isDirectory()){
				bgFolder.mkdirs();
				System.out.println("成功创建背景图片文件夹");
			}
		}catch(Exception e){
			System.out.println("创建背景图片文件夹失败");
		}
		File bgFile=new File(gamePath+"\\bgImage\\bg.png");
		if(bgFile.exists()){
			bgMain =new ImageIcon(gamePath+"\\bgImage\\bg.png");
			System.out.println("成功替换背景");
		}
		//字体导入
		final Font font=new Font("黑体",Font.PLAIN,18);
		final Font font2=new Font("Calibri",Font.PLAIN,12);
		//属性
		this.setUndecorated(true);
		this.setVisible(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setBounds(0,0,WidthOfMainPanel,HeightOfMainPanel+titleLabel.getIconHeight());
		this.setLocationRelativeTo(null);
		Container contentPane=getContentPane();
		
		//面板
		MainPanel=new JPanel();
		contentPane.add(MainPanel);
		CardLayout cardLayout=new CardLayout();
		MainPanel.setLayout(cardLayout);
		MainPanel.setBounds(0,titleLabel.getIconHeight(),WidthOfMainPanel,HeightOfMainPanel);
		
		TitlePanel=new JPanel();
		contentPane.add(TitlePanel);
		TitlePanel.setLayout(null);
		TitlePanel.setBounds(0,0,titleLabel.getIconWidth(),titleLabel.getIconHeight());
		
		//标签
		MainLabel=new JLabel(bgMain);
		MainPanel.add(MainLabel,"MainLabel");
		MainLabel.setLayout(null);
		MainLabel.setBounds(0,0,WidthOfMainPanel,HeightOfMainPanel);
		
		SetLabel=new JLabel(bgSet);
		MainPanel.add(SetLabel,"SetLabel");
		SetLabel.setLayout(null);
		SetLabel.setBounds(0,0,WidthOfMainPanel,HeightOfMainPanel);
		
		TitleLabel=new JLabel(titleLabel);
		TitlePanel.add(TitleLabel);
		TitleLabel.setBounds(0,0,titleLabel.getIconWidth(),titleLabel.getIconHeight());
		MouseEventListener mouseListener = new MouseEventListener(this);
	    TitleLabel.addMouseListener(mouseListener);
	    TitleLabel.addMouseMotionListener(mouseListener);
		
	    minecraftLabel=new JLabel(minecraft);
	    MainLabel.add(minecraftLabel);
	    minecraftLabel.setBounds(9,60,minecraft.getIconWidth(),minecraft.getIconHeight());
	    
	    
		TNameLabel= new JLabel(textLabel);
		SetLabel.add(TNameLabel);
		TNameLabel.setLayout(null);
		TNameLabel.setBounds(40,110,textLabel.getIconWidth(),textLabel.getIconHeight());
		
		TMaxMemLabel= new JLabel(textLabel);
		SetLabel.add(TMaxMemLabel);
		TMaxMemLabel.setLayout(null);
		TMaxMemLabel.setBounds(40,186,textLabel.getIconWidth(),textLabel.getIconHeight());
		
		TJavaPathLabel= new JLabel(textLabel);
		SetLabel.add(TJavaPathLabel);
		TJavaPathLabel.setLayout(null);
		TJavaPathLabel.setBounds(40,262,textLabel.getIconWidth(),textLabel.getIconHeight());
		
		TExtraLabel= new JLabel(textLabel);
		SetLabel.add(TExtraLabel);
		TExtraLabel.setLayout(null);
		TExtraLabel.setBounds(40,340,textLabel.getIconWidth(),textLabel.getIconHeight());

		//按钮
		BStart=new JButton();
		MainLabel.add(BStart);
    	BStart.setIcon(bStart);
    	BStart.setRolloverIcon(bStartHighlight);
		BStart.setBounds(50,260,bStart.getIconWidth(),bStart.getIconHeight());
		BStart.setBorderPainted(false);
    	
    	BExit=new JButton();
		MainLabel.add(BExit);
    	BExit.setIcon(bExit);
    	BExit.setRolloverIcon(bExitHighlight);
    	BExit.setBounds(170,320,bExit.getIconWidth(),bExit.getIconHeight());
    	BExit.setBorderPainted(false);
    	
    	BSet=new JButton();
		MainLabel.add(BSet);
		BSet.setIcon(bSet);
		BSet.setRolloverIcon(bSetHighlight);
		BSet.setBounds(50,320,bSet.getIconWidth(),bSet.getIconHeight());
		BSet.setBorderPainted(false);
		
		BSetExit=new JButton();
		SetLabel.add(BSetExit);
    	BSetExit.setIcon(bSetExit);
    	BSetExit.setRolloverIcon(bSetExitHighlight);
		BSetExit.setBounds(170,420,bSetExit.getIconWidth(),bSetExit.getIconHeight());
		BSetExit.setBorderPainted(false);
		
    	BSetEnter=new JButton();
		SetLabel.add(BSetEnter);
    	BSetEnter.setIcon(bSetEnter);
    	BSetEnter.setRolloverIcon(bSetEnterHighlight);
		BSetEnter.setBounds(50,420,bSetEnter.getIconWidth(),bSetEnter.getIconHeight());
    	BSetEnter.setBorderPainted(false);

    	//文本框
    	
    	TName=new JTextField();
    	TNameLabel.add(TName);
    	TName.setBounds(10, 10, textLabel.getIconWidth()-20, 18);
    	TName.setFont(font);
    	TName.setForeground(Color.white);
    	TName.setOpaque(false);
    	TName.setBorder(BorderFactory.createEmptyBorder());
    	
    	TJavaPath=new JTextField();
    	TJavaPathLabel.add(TJavaPath);
    	TJavaPath.setBounds(10, 10, textLabel.getIconWidth()-20, 18);
    	TJavaPath.setFont(font);
    	TJavaPath.setForeground(Color.white);
    	TJavaPath.setOpaque(false);
    	TJavaPath.setBorder(BorderFactory.createEmptyBorder());
    	
     	TMaxMem=new JTextField();
    	TMaxMemLabel.add(TMaxMem);
    	TMaxMem.setBounds(10, 10, textLabel.getIconWidth()-20, 18);
    	TMaxMem.setFont(font);
    	TMaxMem.setForeground(Color.white);
    	TMaxMem.setOpaque(false);
    	TMaxMem.setBorder(BorderFactory.createEmptyBorder());
    	
    	TExtra=new JTextField();
    	TExtraLabel.add(TExtra);
    	TExtra.setBounds(10, 10, textLabel.getIconWidth()-20, 18);
    	TExtra.setFont(font);
    	TExtra.setForeground(Color.white);
    	TExtra.setOpaque(false);
    	TExtra.setBorder(BorderFactory.createEmptyBorder());
    	
    	TVersion=new JTextField();
    	MainLabel.add(TVersion);
    	TVersion.setBounds(10, 485, 200, 12);
    	TVersion.setEditable(false);
    	TVersion.setText("Version: "+versionInfo);
    	TVersion.setFont(font2);
    	TVersion.setForeground(Color.white);
    	TVersion.setOpaque(false);
    	TVersion.setBorder(BorderFactory.createEmptyBorder());
    	
    	//下拉菜单
    	CVersion=new Choice();
    	CVersion.setBounds((WidthOfMainPanel-200)/2,390,200,25);   	
    	//CVersion.add("");
    	int index = 0;
    	while( index<ArrayJsonFilePath.size()){	
    		String thisPath=ArrayJsonFilePath.get(index);
    		CVersion.add(thisPath.substring(thisPath.lastIndexOf("\\")+1, thisPath.lastIndexOf(".")));
    		index++;
    	}
    	MainLabel.add(CVersion);
    	//初始化
    	String[] argumentsSaved=new String[5];
		argumentsSaved=McLauncherCore.configRead(gamePath);
		try{
			CVersion.select(argumentsSaved[3]);
		}catch(Exception e1){
			CVersion.select(1);
		}
		TName.setText(argumentsSaved[2]);
		TJavaPath.setText(argumentsSaved[0]);
		TJavaPath.setCaretPosition(0);
		TMaxMem.setText(argumentsSaved[1]);
		TExtra.setText(argumentsSaved[4]);
		
    	//事件侦听
    	BStart.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			File javaw=new File(TJavaPath.getText());
    			if (!javaw.exists()) {
    				JOptionPane.showMessageDialog(null, "javaw.exe路径有误，请正确填写java路径后再尝试。","错误报告",  JOptionPane.ERROR_MESSAGE);
    			}
    			arguments[0]=TJavaPath.getText();
        		arguments[1]=TMaxMem.getText();
        		arguments[2]=TName.getText();	
        		arguments[3]=CVersion.getSelectedItem();
        		arguments[4]=TExtra.getText();
    			McLauncherCore.configCreate(gamePath);
        		McLauncherCore.batchCreate(ArrayJsonFilePath.get(CVersion.getSelectedIndex()));
        		String cmd="cmd /c start "+gamePath+"\\start.bat";    		
        		try {
        			Runtime.getRuntime().exec(cmd);
        			System.out.println("Batch has ran.");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.out.println("Batch can't ran.");
                }    
        		System.exit(0);
    		}
    	});
    	BSet.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			String[] argumentsSaved=new String[5];
    			argumentsSaved=McLauncherCore.configRead(gamePath);
    			TName.setText(argumentsSaved[2]);
    			TJavaPath.setText(argumentsSaved[0]);
    			TJavaPath.setCaretPosition(0);
    			TMaxMem.setText(argumentsSaved[1]);
    			TExtra.setText(argumentsSaved[4]);
    			cardLayout.show(MainPanel,"SetLabel");
    			SetLabel.repaint();
    		}
    	});
    	BExit.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			System.exit(0);
    		}
    	});
    	BSetExit.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			String[] argumentsSaved=new String[5];
    			argumentsSaved=McLauncherCore.configRead(gamePath);
    			TName.setText(argumentsSaved[2]);
    			TJavaPath.setText(argumentsSaved[0]);
    			TMaxMem.setText(argumentsSaved[1]);
    			TExtra.setText(argumentsSaved[4]);
    			cardLayout.show(MainPanel,"MainLabel");
    		}
    	});
    	BSetEnter.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			arguments[0]=TJavaPath.getText();
        		arguments[1]=TMaxMem.getText();
        		arguments[2]=TName.getText();
        		arguments[3]=CVersion.getSelectedItem();
        		arguments[4]=TExtra.getText();
    			McLauncherCore.configCreate(gamePath);
    			cardLayout.show(MainPanel,"MainLabel");
    			MainPanel.repaint();
    		}
    	});
    	
	}	 
	
	class MouseEventListener implements MouseInputListener {	     
	    Point origin;
	    McLauncher frame;   
	    public MouseEventListener(McLauncher frame) {
	      this.frame = frame;
	      origin = new Point();
	    }
	    //记录鼠标按下时的点
	    @Override
	    public void mousePressed(MouseEvent e) {
	      origin.x = e.getX(); 
	      origin.y = e.getY();
	    }
	    //鼠标在标题栏拖拽时，设置窗口的坐标位置
	    //窗口新的坐标位置 = 移动前坐标位置+（鼠标指针当前坐标-鼠标按下时指针的位置）
	    @Override
	    public void mouseDragged(MouseEvent e) {
	      Point p = this.frame.getLocation();
	      this.frame.setLocation(
	        p.x + (e.getX() - origin.x), 
	        p.y + (e.getY() - origin.y)); 
	    } 
	    @Override
	    public void mouseMoved(MouseEvent e) {}
	    @Override
	    public void mouseClicked(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
	    @Override
	    public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
	}


//Main	
	public static void main(String args[]){
		gamePath=McLauncherCore.getPath();
		gamePath=gamePath.substring(0, gamePath.lastIndexOf("\\"));//Launch4j打包改变了getPath()的结果，需去掉一部分
		System.out.println("gamePath="+gamePath);
		//********************测试用
		gamePath="C:\\Users\\Administrator\\Desktop\\Minecraft1.7.10";
		//gamePath="C:\\Users\\Administrator\\Desktop\\nightSky";
		//gamePath="C:\\Users\\Administrator\\Desktop\\Minecraft1.8.8";
		//********************测试用

		ArrayJsonFilePath=McLauncherCore.getJsonPath(gamePath);
		if(ArrayJsonFilePath.size()<1){
			JOptionPane.showMessageDialog(null, "未搜索到游戏关键文件，请将启动器放入游戏文件夹根目录后再尝试。","错误报告",  JOptionPane.ERROR_MESSAGE);
		}
		String jsonPath=ArrayJsonFilePath.get(0);
		arguments[3]=jsonPath.substring(jsonPath.lastIndexOf("\\")+1, jsonPath.lastIndexOf("."));//初始化
		String javaPath=null;
		if((javaPath=McLauncherCore.getJavawPath("C:\\Program Files\\Java"))!=null){
			arguments[0]=javaPath;
		}else if((javaPath=McLauncherCore.getJavawPath("C:\\Program Files (x86)\\Java"))!=null){
			arguments[0]=javaPath;
		}else{
			arguments[0]="";
		}
		arguments[1]="1024";
		arguments[2]="player";
		arguments[4]="";
			
		McLauncher mcLauncher= new McLauncher();
		mcLauncher.setVisible(true);
		
		String nativesPath=McLauncherCore.getNativesPath(gamePath);
		if(nativesPath!=null){
			McLauncherCore.nativesPath=nativesPath;
		}else{
			JOptionPane.showMessageDialog(null, "未搜索到natives目录，请自行下载。","错误报告",  JOptionPane.ERROR_MESSAGE);
		}
	}	
}




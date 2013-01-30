package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2740437090361841747L;
	private final int WIDTH = 800;
	private final int HEIGTH = 600;
	private final int SPLIT_RESIZERS_WIDTH = 3;
	private final String APP_NAME = "Testing tool";
	
	private JSplitPane centerPane;
	private JSplitPane bottomPane;
	
	private Container contentPane; 
	public static String dataPath = "./data"; 
	private File dataRoot;
	
	private JPanel editor;
	private ProjectNavigator navigator;
	private Console console;
	
	/*
	 * Popis
	 * 
	 * @param
	 * @return
	 */
	public MainWindow(){
		windowInit();
		addMenuBar();
		//addCenterPanel();
		//addConsole();
		addCenterPane();
		addBottomPane();
		this.pack();
	
	}
	
	/*
	 * 
	 */
	
	public static String getDataPath()
	{
		return MainWindow.dataPath;
	}
	
	public static void setDataPath(String path){
		
		MainWindow.dataPath = path;
	}
	
	
	public void showCaseEditor(){
		
	}
	
	public void removeCaseEditor(){
		
	}
	
	/*
	 * 
	 */
	private void windowInit()
	{
		this.setTitle(this.APP_NAME);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		// Determine the new location of the window
		int x = (dim.width-(this.WIDTH))/2;
		int y = (dim.height-(this.HEIGTH))/2;
		 
		
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(x, y, this.WIDTH, this.HEIGTH);
		this.setMinimumSize(new Dimension(this.WIDTH,this.HEIGTH));
		this.setLayout(new BorderLayout());
		initContentPane();
		this.setBackground(Color.gray);
		
		
	}
	
	private void initContentPane(){
		this.contentPane = this.getContentPane();
	}
	
	private JSplitPane getCenterPane(){
		return this.centerPane;
	}
	
	
	
	/*
	 * 
	 */
	private void addMenuBar(){
		this.setJMenuBar(new Menu());
		
	}
	
	
	private void addCenterPane(){
		initDataPath();
		initContent();
		this.navigator = new  ProjectNavigator(this.dataRoot);
		this.centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,this.navigator,this.editor);
		this.centerPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
		
	}
	
	private void addBottomPane(){
		initConsole();
		this.bottomPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,getCenterPane(),this.console);
		this.bottomPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
		this.bottomPane.setResizeWeight(0.90);
		getContentPane().add(this.bottomPane);
	}
	
	/*private void addToolBar(){
		
	}*/
	
	/*
	 * 
	 */
	private void initConsole(){
		this.console = new Console();
	}
	
	private void initContent(){
		
		
		
		
		this.editor = new TestCaseEditor();
		
		
		//this.editor.setLayout(null);		
		//JSplitPane windows = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,paneEditRequest,responseArea);
		
//		JInternalFrame frame = new JInternalFrame();
//		frame.setResizable(true);
//		frame.setClosable(true);
//		frame.setMaximizable(true);
//		this.editor.add(frame);
//		frame.setSize(100, 100);
//		frame.setVisible(true);
		//this.editor.;
		
		
		
	}
	
	/*
	 * 
	 */
	private void initDataPath()
	{
		this.dataRoot = new File(MainWindow.getDataPath());
		
		if(!dataRoot.exists()){
			boolean wasDirectoryMade = dataRoot.mkdirs();
		    if(wasDirectoryMade)System.out.println("Direcoty Created");
		}
		
	}
	
	
	
	
	
	
	
}

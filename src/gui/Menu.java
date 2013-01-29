package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import modalWindows.NewProjectWindow;
import modalWindows.NewTestCaseWindow;
import modalWindows.NewTestSuiteWindow;

public class Menu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5617155576631422259L;
	
	private JMenu file;
	private JMenu newSubMenu;
	//private JMenu view;
	private JMenu project;
	
	private JMenuItem fileOpen;
	private JMenuItem fileExit;
 	private JMenuItem fileChangeRoot;
 	
 	private JMenuItem projectSettings;
 	private JMenuItem projectRunningOptions;
 	private JMenuItem projectRun;
 	
	//private JMenuItem fileSave;
	//private JMenuItem fileSaveAs;
	//private JMenuItem fileSaveRequest;
	//private JMenuItem fileSaveResponse;
	 
	private JMenuItem newTestProject;
 	private JMenuItem newTestSuite;
 	private JMenuItem newTestCase;
 	
 	private NewProjectWindow newProjectWindow;
 	private NewTestSuiteWindow newTestSuiteWindow;
 	private NewTestCaseWindow newTestCaseWindow;
 	
 	//private JFrame parentWindow;
 	
 	
	/**
	 * 
	 */
	public Menu(){
		initMenus();
		
	}
	
	/**
	 * 
	 */
	private void initMenus(){
		
		//parentWindow =(JFrame) getParent();
		file = new JMenu("File");
		project = new JMenu("Project");
		newSubMenu = new JMenu("New");
		
		this.add(file);
		this.add(project);
		file.add(newSubMenu);
		initFileMenuItems();
		initProjectMenuItems();
		
	}
	
	/**
	 * 
	 */
	private void initFileMenuItems()
	{
		
		newTestProject = new JMenuItem("Test Project");
		newSubMenu.add(newTestProject);
		
		this.newTestProject.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				
				newProjectWindow = new NewProjectWindow();
				newProjectWindow.setVisible(true);
				System.out.println("new Test Project clicked");
			}
			
		} );
		
		
		newTestSuite = new JMenuItem("Test Suite");
		newSubMenu.add(newTestSuite);
		
		this.newTestSuite.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				newTestSuiteWindow = new NewTestSuiteWindow();
				newTestSuiteWindow.setVisible(true);
			
				System.out.println("new Test Suite clicked");
			}
			
		} );
		
		newTestCase = new JMenuItem("Test Case");
		newSubMenu.add(newTestCase);
		
		this.newTestCase.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				newTestCaseWindow = new NewTestCaseWindow();
				newTestCaseWindow.setVisible(true);
				
				System.out.println("new Test Case clicked");
			}
			
		} );
		
		
		fileOpen = new JMenuItem("Open");
		file.add(fileOpen);
		
		this.fileOpen.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file open clicked");
			}
			
		} );
		
		fileChangeRoot = new JMenuItem("Change Root");
		file.add(fileChangeRoot);
		
		this.fileChangeRoot.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				 
				JFileChooser changeRoot = new JFileChooser();
				changeRoot .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				String root = "nic nevybrano";
				int retVal = changeRoot.showDialog(getParent(), "Change");
				
				if(retVal == JFileChooser.APPROVE_OPTION){
					 root = changeRoot.getSelectedFile().toString();
					MainWindow.setDataPath(root);
					Main.restartGui();
				}
				System.out.println("Project changed to:" + root);
			}
			
		} );
		
//		fileGenerate = new JMenuItem("Generate");
//		file.add(fileGenerate);
//		
//		this.fileGenerate.addActionListener( new ActionListener(){
//			public void actionPerformed(ActionEvent ae) {
//				System.out.println("file generate clicked");
//			}
//			
//		} );
//		
		fileExit = new JMenuItem("Exit");
		file.add(fileExit);
		
		this.fileExit.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file exit clicked");
				System.exit(0);
			}
			
		} );
	}
	
	private void initProjectMenuItems(){
		
		projectSettings = new JMenuItem("Project Settings");
		project.add(projectSettings);
		
		this.projectSettings.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
								
				System.out.println("Project settings clicked");
			}
			
		} );
		
		
		projectRunningOptions = new JMenuItem("Running Options");
		project.add(projectRunningOptions);
		
		this.projectRunningOptions.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
							
				System.out.println("Running Options clicked");
			}
			
		} );
		
		projectRun = new JMenuItem("Run Project");
		project.add(projectRun);
		
		this.projectRun.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
						
				System.out.println("Project Run clicked");
			}
			
		} );
	}

}

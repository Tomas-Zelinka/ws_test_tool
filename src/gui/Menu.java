package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import central.Main;


import logging.ConsoleLog;
import modalWindows.NewTestCaseDialog;
import modalWindows.NewTestSuiteDialog;

public class Menu extends JMenuBar {

	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = -5617155576631422259L;
	
	/**
	 * 
	 */
	private JMenu file;
	
	/**
	 * 
	 */
	private JMenu newSubMenu;
	
	/**
	 * 
	 */
	private JMenu project;
	
	/**
	 * 
	 */
	private JMenu view;
	
	/**
	 * 
	 */
	private JMenu proxy;
	
	/**
	 * 
	 */
	private JMenu remote;
	
	/**
	 * 
	 */
	private NewTestSuiteDialog newTestSuiteWindow;
 	
	/**
	 * 
	 */
	private NewTestCaseDialog newTestCaseWindow;
 	
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
		view = new JMenu("View");
		project = new JMenu("Project");
		newSubMenu = new JMenu("New");
		proxy = new JMenu("Proxy");
		remote = new JMenu("Remote");
		
		this.add(file);
		file.add(newSubMenu);
		this.add(view);
		this.add(project);
		this.add(proxy);
		this.add(remote);
		
		
		initFileMenuItems();
		initViewMenuItems();
		initProjectMenuItems();
		initProxyMenuItems();
		initRemoteMenuItems();
	}
	
	/**
	 * 
	 */
	private MainWindow getMainWindowInstance(){
		return (MainWindow)this.getTopLevelAncestor();
	}
	
	/**
	 * 
	 */
	private void initFileMenuItems()
	{
		
		//addMenuItem(newSubMenu,"New Test Project", new TestProjectListener());
		addMenuItem(newSubMenu,"New Test Suite", new TestSuiteListener());
		addMenuItem(newSubMenu,"New Test Case", new TestCaseListener());
		//addMenuItem(newSubMenu,"New Test List", new TestListListener());
		
		addMenuItem(file,"Open", new OpenListener());
		addMenuItem(file,"Edit", new EditListener());
		addMenuItem(file,"Delete", new DeleteListener());
		addMenuItem(file,"Close Editor", new CloseEditorListener());
		addMenuItem(file,"Change Root", new ChangeRootListener());
		addMenuItem(file,"Exit", new ExitListener());
	}
	
	/**
	 * 
	 */
	private void  initViewMenuItems(){
		addMenuItem(view,"Test Editor",new ViewTestEditorListener());
		addMenuItem(view,"Test Unit",new ViewTestUnitListener());
		addMenuItem(view,"Proxy Monitor",new ViewProxyMonitorListener());
		addMenuItem(view,"Remote Control",new ViewRemoteControlListener());
		addMenuItem(view,"Statistics",new ViewStatisticsListener());
	}
	/**
	 * 
	 */
	private void initProjectMenuItems(){
		
		addMenuItem(project,"Project Settings", new OpenListener());
		addMenuItem(project,"Running Options", new ChangeRootListener());
		addMenuItem(project,"Run Project", new ExitListener());
		
	}	
	
	/**
	 * 
	 */
	private void initProxyMenuItems(){
		
		addMenuItem(proxy,"Proxy Run", new ProxyRunListener());
		addMenuItem(proxy,"Proxy Monitor", new ProxyMonitorListener());
		addMenuItem(proxy,"Proxy Log", new ProxyLogListener());
		addMenuItem(proxy,"Proxy Settings", new ProxySettingsListener());
	}
	
	/**
	 * 
	 */
	private void initRemoteMenuItems(){
		addMenuItem(remote,"Remote Control", new RemoteControlListener());
	}

	/**
	 * 
	 * @param menu
	 * @param label
	 * @param listener
	 */
	private void addMenuItem(JMenu menu,String label,ActionListener listener){
		
		JMenuItem menuItem = new JMenuItem(label);
		menu.add(menuItem);
		menuItem.addActionListener(listener);
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class EditListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			ConsoleLog.Print("file edit clicked");
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class CloseEditorListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			//getMainWindowInstance().removeContent();
			//getMainWindowInstance().setContent(new PlainPanel());
			ConsoleLog.Print("file exit clicked");
			
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class DeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			File node = new File(MainWindow.getEndpointPath());
			ConsoleLog.Print(MainWindow.getEndpointPath());
			if(node.exists()){
				ConsoleLog.Print("mazu");
				node.delete();
				MainWindow.setSuitePath("");
				ProjectNavigator.refreshTree();
			}else{
				//throw new Exception();
			}
			
		}
	} 
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ExitListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			ConsoleLog.Print("file exit clicked");
			System.exit(0);
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ChangeRootListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			 
			JFileChooser changeRoot = new JFileChooser();
			changeRoot .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			String root = "nic nevybrano";
			int retVal = changeRoot.showDialog(getParent(), "Change");
			
			if(retVal == JFileChooser.APPROVE_OPTION){
				 root = changeRoot.getSelectedFile().toString();
				MainWindow.setDataRoot(root);
				Main.restartGui();
			}
			ConsoleLog.Print("Project changed to:" + root);
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class OpenListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			ConsoleLog.Print("file open clicked");
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class TestCaseListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			newTestCaseWindow = new NewTestCaseDialog();
			newTestCaseWindow.setVisible(true);
			
			ConsoleLog.Print("new Test Case clicked");
		}
	}

	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class TestSuiteListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			newTestSuiteWindow = new NewTestSuiteDialog();
			newTestSuiteWindow.setVisible(true);
			ConsoleLog.Print("new Test Suite clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class TestListListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("new Test List clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	
	class TestProjectListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			
			newProjectWindow = new NewProjectWindow();
			newProjectWindow.setVisible(true);
			ConsoleLog.Print("new Test Project clicked");
		}
	} */	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ViewTestEditorListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			getMainWindowInstance().setContent(MainWindow.TESTCASE_EDITOR);
				ConsoleLog.Print("Test Editor clicked");
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ViewTestUnitListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			getMainWindowInstance().setContent(MainWindow.TESTING_UNIT);
				ConsoleLog.Print("Test Unit clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ViewProxyMonitorListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			getMainWindowInstance().setContent(MainWindow.PROXY_MONITOR);
			ConsoleLog.Print("Proxy Monitor clicked");
			}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ViewRemoteControlListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			getMainWindowInstance().setContent(MainWindow.REMOTE_CONTROL);
			ConsoleLog.Print("Remote Control clicked");
			}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ViewStatisticsListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			getMainWindowInstance().setContent(MainWindow.STATISTICS);
			ConsoleLog.Print("Statistics clicked");
			}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProjectSettingsListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			ConsoleLog.Print("Project settings clicked");
			}
		} 

	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class RunningOptionsListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			ConsoleLog.Print("Running Options clicked");
		}
	} 
		
		
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */	
	class ProjectRunListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			ConsoleLog.Print("Project Run clicked");
		}
	} 


	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProxySettingsListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("Proxy Settings clicked");
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProxyLogListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("Proxy Log clicked");
		}
	} 

	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProxyRunListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("Proxy Run clicked");
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProxyMonitorListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("Proxy Monitor clicked");
		}
	} 
	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class RemoteControlListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("Remote Control clicked");
		}
	} 
	
}

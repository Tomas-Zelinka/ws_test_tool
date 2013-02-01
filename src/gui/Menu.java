package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logging.ConsoleLog;
import modalWindows.NewProjectWindow;
import modalWindows.NewTestCaseWindow;
import modalWindows.NewTestSuiteWindow;

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
	private JMenu proxy;
	
	/**
	 * 
	 */
	private JMenu remote;
	
	/**
	 * 
	 */
	private NewProjectWindow newProjectWindow;
 	
	/**
	 * 
	 */
	private NewTestSuiteWindow newTestSuiteWindow;
 	
	/**
	 * 
	 */
	private NewTestCaseWindow newTestCaseWindow;
 	
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
		proxy = new JMenu("Proxy");
		remote = new JMenu("Remote");
		
		this.add(file);
		file.add(newSubMenu);
		this.add(project);
		this.add(proxy);
		this.add(remote);
		
		
		initFileMenuItems();
		initProjectMenuItems();
		initProxyMenuItems();
		initRemoteMenuItems();
	}
	
	/**
	 * 
	 */
	private void initFileMenuItems()
	{
		
		addMenuItem(newSubMenu,"New Test Project", new TestProjectListener());
		addMenuItem(newSubMenu,"New Test Suite", new TestSuiteListener());
		addMenuItem(newSubMenu,"New Test Case", new TestCaseListener());
		
		addMenuItem(file,"Open", new OpenListener());
		addMenuItem(file,"Edit", new EditListener());
		addMenuItem(file,"Change Root", new ChangeRootListener());
		addMenuItem(file,"Exit", new ExitListener());
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
				MainWindow.setDataPath(root);
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
			newTestCaseWindow = new NewTestCaseWindow();
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
			newTestSuiteWindow = new NewTestSuiteWindow();
			newTestSuiteWindow.setVisible(true);
			ConsoleLog.Print("new Test Suite clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class TestProjectListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			
			newProjectWindow = new NewProjectWindow();
			newProjectWindow.setVisible(true);
			ConsoleLog.Print("new Test Project clicked");
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

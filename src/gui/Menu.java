package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logging.ConsoleLog;
import modalWindows.NewTestCaseDialog;
import modalWindows.NewTestSuiteDialog;
import data.DataProvider;
import data.FaultInjectionData;
import data.HttpMessageData;
import data.TestCaseSettingsData;

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
	private JMenu view;
	
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
		
		file = new JMenu("File");
		view = new JMenu("View");
		newSubMenu = new JMenu("New");
		
		this.add(file);
		file.add(newSubMenu);
		this.add(view);
		
		initFileMenuItems();
		initViewMenuItems();
		
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
	private void initFileMenuItems(){
		
		addMenuItem(newSubMenu,"Test Suite", new TestSuiteListener());
		addMenuItem(newSubMenu,"Test Case", new TestCaseListener());
		addMenuItem(newSubMenu,"HTTP Test", new HttpTestListener());
		addMenuItem(newSubMenu,"Fault Injection", new FaultInjectionListener());
		file.addSeparator();
		addMenuItem(file,"Open", new OpenListener());
		addMenuItem(file,"Delete", new DeleteListener());
		addMenuItem(file,"Refresh", new RefreshTree());
		file.addSeparator();
		addMenuItem(file,"Export All Configurations", new ExportListener());
		addMenuItem(file,"Exit", new ExitListener());
	}
	
	/**
	 * 
	 */
	private void  initViewMenuItems(){
		
		addMenuItem(view,"Test Editor",new ViewTestEditorListener());
		addMenuItem(view,"Test Unit",new ViewTestUnitListener());
		addMenuItem(view,"Proxy Monitor",new ViewProxyMonitorListener());
		addMenuItem(view,"Statistics",new ViewStatisticsListener());
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
	class ExitListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			ConsoleLog.Print("[HeadMenu] File exit clicked");
			System.exit(0);
		}
	} 
	

	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class TestListListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("[HeadMenu]  New Test List clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ViewTestEditorListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			
			getMainWindowInstance().setContent(MainWindow.TESTCASE_EDITOR);
			ConsoleLog.Print("[HeadMenu] Test Editor clicked");
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
			ConsoleLog.Print("[HeadMenu] Test Unit clicked");
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
			ConsoleLog.Print("[HeadMenu] Proxy Monitor clicked");
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
			ConsoleLog.Print("[HeadMenu] Statistics clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProjectSettingsListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			
			ConsoleLog.Print("[HeadMenu] Project settings clicked");
		}
	} 

	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class RunningOptionsListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			ConsoleLog.Print("[HeadMenu] Running Options clicked");
		}
	} 
		
		
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */	
	class ProjectRunListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			
			ConsoleLog.Print("[HeadMenu] Project Run clicked");
		}
	} 


	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProxySettingsListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("[HeadMenu] Proxy Settings clicked");
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProxyLogListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("[HeadMenu] Proxy Log clicked");
		}
	} 

	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */ 
	class ProxyRunListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("[HeadMenu] Proxy Run clicked");
		}
	} 
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class ProxyMonitorListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("[HeadMenu] Proxy Monitor clicked");
		}
	} 
	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class RemoteControlListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
					
			ConsoleLog.Print("[HeadMenu] Remote Control clicked");
		}
	} 
	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	public class TestCaseListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			
			newTestCaseWindow = new NewTestCaseDialog();
			newTestCaseWindow.setVisible(true);
			ConsoleLog.Print("[HeadMenu] New Test Case clicked");
		}
	}

	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	public class TestSuiteListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			
			newTestSuiteWindow = new NewTestSuiteDialog();
			newTestSuiteWindow.setVisible(true);
			ConsoleLog.Print("[HeadMenu] New Test Suite clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	public class ExportListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			
			ConsoleLog.Print("[HeadMenu] Export Suite clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	public class HttpTestListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			
			if(!MainWindow.getCasePath().isEmpty()){
				File newHttpCase = new File(MainWindow.getCasePath()+File.separator+"Http");
				File inputDir = new File(newHttpCase.getPath() + File.separator + "input");
	            File outputDir = new File(newHttpCase.getPath() + File.separator + "output");
	            File httpDataFile = new File(inputDir.getPath() + File.separator + "httpRequest.xml");
	            DataProvider ioProvider = new DataProvider();
	            TestCaseSettingsData settings =(TestCaseSettingsData) ioProvider.readObject(MainWindow.getCasePath() + TestCaseSettingsData.filename );
	            HttpMessageData httpData = new HttpMessageData(settings.getName());
	            
	            if (newHttpCase.exists()){
					ConsoleLog.Print("[HeadMenu] You can set only 1 http test to this case");
	             }else{
	            	 
	            	 newHttpCase.mkdir();
	            	 inputDir.mkdir();
	            	 outputDir.mkdir();
	            	 
	            	 try{
		             		httpDataFile.createNewFile();
		             		ioProvider.writeObject(httpDataFile.getPath(),httpData);
		             }catch(Exception b){
		             		b.printStackTrace();
		             }
	            	 Navigator.refreshTree();
	            	 getMainWindowInstance().setPanelType(MainWindow.CASE_EDITOR_HTTP);
	            	 getMainWindowInstance().openTestCaseEditor();
	            	 ConsoleLog.Print("[HeadMenu] New project name: "+ MainWindow.getCasePath());
	            }
			}else{
				ConsoleLog.Print("[HeadMenu] Test case not selected ");
			}
			ConsoleLog.Print("[HeadMenu] New HttpTest clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 */
	 
	public class FaultInjectionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			
			if(!MainWindow.getCasePath().isEmpty()){
				File newHttpCase = new File(MainWindow.getCasePath()+File.separator+"FaultInjection");
				File inputDir = new File(newHttpCase.getPath() + File.separator + "input");
	            File outputDir = new File(newHttpCase.getPath() + File.separator + "output");
	            File faultInjectionDataFile = new File(inputDir.getPath() + File.separator + "faultInjection.xml");
	            DataProvider ioProvider = new DataProvider();
	            TestCaseSettingsData settings =(TestCaseSettingsData) ioProvider.readObject(MainWindow.getCasePath() + TestCaseSettingsData.filename );
	            FaultInjectionData faultData = new FaultInjectionData(settings.getName());
          
	            if (newHttpCase.exists()){
	            	ConsoleLog.Print("[HeadMenu] You can set only 1 fault injection test to this case");
	            
	            }else{
	            	 
	            	 newHttpCase.mkdir();
	            	 inputDir.mkdir();
	            	 outputDir.mkdir();
	            	 try{
	            		 faultInjectionDataFile.createNewFile();
	            		 ioProvider.writeObject(faultInjectionDataFile.getPath(),faultData);
	            	 }catch(Exception b){
	            		 b.printStackTrace();
	            	 }
	            	
	            	 Navigator.refreshTree();
	            	 getMainWindowInstance().setPanelType(MainWindow.CASE_EDITOR_FAULT);
	            	 getMainWindowInstance().openTestCaseEditor();
	            	 
	            	 
	            	 ConsoleLog.Print("[HeadMenu] New project name: "+ MainWindow.getCasePath());
	            }
			}else{
				ConsoleLog.Print("[HeadMenu] Test case not selected ");
			}
			ConsoleLog.Print("[HeadMenu] New Fault Injection clicked");
		}
	}		
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	public class OpenListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			
			int panelType = getMainWindowInstance().getPanelType();
			
			if(panelType == MainWindow.CASE_EDITOR_FAULT || panelType == MainWindow.CASE_EDITOR_SETTINGS
				||	panelType == MainWindow.CASE_EDITOR_HTTP	){
				
				getMainWindowInstance().openTestCaseEditor();
				ConsoleLog.Print("file edit clicked");
			
			}else if(panelType == MainWindow.TESTING_UNIT){
				getMainWindowInstance().openTestList();
			}
		}
	} 	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	public class DeleteListener implements ActionListener {
		
		public void actionPerformed(ActionEvent ae) {
			
			File node = new File(MainWindow.getEndpointPath());
			ConsoleLog.Print(MainWindow.getEndpointPath());
			
			if(node.exists()){
				delete(node);
				getMainWindowInstance().refreshTree();
			}else{
				
			}
		}
	} 
	
	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	public class RefreshTree implements ActionListener{
		
		public void actionPerformed(ActionEvent ae) {
			getMainWindowInstance().refreshTree();
		}
	} 
	
	public void delete(File f)  {
		  
		if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		}
		  
		if(!f.delete()){
			ConsoleLog.Print("[HeadMenu] Not deleted");
		}
	}
}

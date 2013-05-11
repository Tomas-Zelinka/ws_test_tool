package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import logging.ConsoleLog;
import modalWindows.NewTestCaseDialog;
import modalWindows.NewTestSuiteDialog;
import data.DataProvider;
import data.FaultInjectionData;
import data.HttpMessageData;
import data.TestCaseSettingsData;


public class Navigator extends JPanel {
	
	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = 6495944797574501122L;
	
	/**
	 * TODO zkusit se zbavit static
	 */
	private static JTree tree;
	
	/**
	 * 
	 */
	private JScrollPane scrollPane;
	
	/**
	 * 
	 */
	private JPopupMenu treeMenu;
	private JMenu newMenu;
	
	private final int SUITE_PATH_LENGTH = 2;
	private final int CASE_PATH_LENGTH = 3;
	private final int CASE_DETAIL_PATH_LENGTH = 4;
	
	
	
	
 	private NewTestSuiteDialog newTestSuiteWindow;
 	private NewTestCaseDialog newTestCaseWindow;
 	
	/**
	 * 
	 */
	public Navigator(File dir) {
		 
		 // Make a tree list with all the nodes, and make it a JTree
	     initTree(dir);
		 initPopupMenu();
		 
		 this.scrollPane = new JScrollPane();
		 this.setLayout(new BorderLayout());
		 this.scrollPane.getViewport().add(tree);
		 this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		 this.add(BorderLayout.CENTER, scrollPane); 
		 
	}
	
	
	public Dimension getMinimumSize() {
	    return new Dimension(200, 400);
	}

	public Dimension getPreferredSize() {
	    return new Dimension(200, 400);
	}
	
	/**
	 * method for refreshing tree by user
	 * for example when the filesystem is changed
	 * while the tool is running
	 */
	public static void refreshTree(){
		
		SwingUtilities.updateComponentTreeUI(tree);
		tree.treeDidChange();
		ConsoleLog.Print("[Navigator] Tree refreshed");
	}
	
	/**
	 * TODO osetreni
	 * @param f
	 */
	private void delete(File f)  {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
		  
		  if(!f.delete()){
			  ConsoleLog.Print("[Navigator] Not deleted");
		  }
		    
		}
	
	/**
	 * Document tree initialization
	 *  
	 * @param dir - the root directory for the tree
	 */
	private void initTree(File dir){
		tree = new HighlightedTree();
		tree.setModel(new MyTreeModel(dir));
		tree.setRootVisible(false);
		tree.setShowsRootHandles(false);
		
		MouseListener ml = new MyMouseAdapter(); 
	    tree.addMouseListener(ml);	
	   
	    TreeSelectionListener treeSelListener = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				tree.treeDidChange();
			}
		};
		tree.addTreeSelectionListener(treeSelListener);
	    MyTreeCellRenderer renderer = new MyTreeCellRenderer();
		tree.setCellRenderer( renderer);
		
	}

	/**
	 * 
	 */
	private MainWindow getMainWindowInstance(){
		return (MainWindow)this.getTopLevelAncestor();
	}
	

	/**
	 * 
	 * @param menu
	 * @param label
	 * @param listener
	 */
	private void addMenuItem(JPopupMenu menu,String label,ActionListener listener){
		
		JMenuItem menuItem = new JMenuItem(label);
		menu.add(menuItem);
		menuItem.addActionListener(listener);
	}

	private void addMenuItem(JMenu menu,String label,ActionListener listener){
		
		JMenuItem menuItem = new JMenuItem(label);
		menu.add(menuItem);
		menuItem.addActionListener(listener);
	}
	/**
	 * 
	 */
	private void initPopupMenu(){
		treeMenu = new JPopupMenu();
		newMenu = new JMenu("New");
		addMenuItem(newMenu,"Test Suite", new TestSuiteListener() );
		addMenuItem(newMenu,"Test Case", new TestCaseListener());
		addMenuItem(newMenu,"HTTP Test", new HttpTestListener());
		addMenuItem(newMenu,"Fault Injection", new FaultInjectionListener());
		treeMenu.add(newMenu);
		treeMenu.addSeparator();
		addMenuItem(treeMenu,"Insert to Unit", new InsertTestCaseListener());
		treeMenu.addSeparator();
		addMenuItem(treeMenu,"Open", new OpenListener());
		addMenuItem(treeMenu,"Close", new CloseListener());
		addMenuItem(treeMenu,"Delete", new DeleteListener());
		treeMenu.addSeparator();
		addMenuItem(treeMenu,"Refresh", new RefreshTree());
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
			getMainWindowInstance().setPanelType(MainWindow.CASE_EDITOR_SETTINGS);
			getMainWindowInstance().openTestCaseEditor();
			ConsoleLog.Print("[Navigator] New Test Case clicked");
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
			ConsoleLog.Print("[Navigator] Test Suite clicked");
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
					ConsoleLog.Print("[Navigator] You can set only 1 http test to this case");
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
	            	 ConsoleLog.Print("[Navigator] New project name: "+ MainWindow.getCasePath());
	            }
			}else{
				ConsoleLog.Print("[Navigator] Test case not selected ");
			}
			ConsoleLog.Print("[Navigator] new HttpTest clicked");
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
					ConsoleLog.Print("[Navigator] You can set only 1 fault injection test to this case");
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
	            	 
	            	 ConsoleLog.Print("[Navigator] New project name: "+ MainWindow.getCasePath());
	            }
			}else{
				ConsoleLog.Print("[Navigator] Test case not selected ");
			}
			ConsoleLog.Print("[Navigator] new Fault Injection clicked");
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
				ConsoleLog.Print("[Navigator] File edit clicked");
			
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
	public class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
				getMainWindowInstance().closeTestCase();
				ConsoleLog.Print("[Navigator] Close case clicked");
			
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
			ConsoleLog.Print("[Navigator] " + MainWindow.getEndpointPath());
			if(node.exists()){
				delete(node);
				refreshTree();
			}else{
				//TODO neco sem
			}
		}
	} 
	
	private class InsertTestCaseListener implements ActionListener{
		public void actionPerformed (ActionEvent e){
			getMainWindowInstance().insertTestCase();
			ConsoleLog.Print("[Navigator] Insert Test Case clicked");
			
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	public class RefreshTree implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			refreshTree();
		}
	} 
	
	

	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		
		private static final long serialVersionUID = 6647184007329048034L;
		
		public MyTreeCellRenderer() {
			this.setBorderSelectionColor(null); 
			this.setBackgroundSelectionColor( null); 
			this.setBackgroundNonSelectionColor(null);
		}

		public Color getBackground() {
			return null;
		}
		
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, 
				boolean leaf, int row, boolean hasFocus) {
			
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			FileNode node= (FileNode) value;
			
			
			
					
			if (node.isHttpTestCase()) {
				
				setIcon(new ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"request.png")));
			}
			else if (node.isFaultInjectionTestCase()){
				
				setIcon(new ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"fault.png")));
			}
			else if (node.isSettings()){
				
				setIcon(new ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"settings.png")));
			}
			else if (node.isTestList()){
				
				setIcon(new ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"list.png")));
			}
			else if (node.isSuite()){
				
				setIcon(new ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"suite.png")));
			}
			else
				setIcon(new ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"case.png")));
			
			return this;
		}

	}
	
	
	
	
	
	/**
	 * 
	 * This adapter is responsible for mouse event
	 * handling on the navigation tree
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class MyMouseAdapter extends MouseAdapter{
		
		/**
		 * 
		 *  @param e - triggered mouse event 
		 */
		public void mousePressed(MouseEvent e) {
    		JTree myTree = (JTree)e.getSource();
    		FileNode node = null;
    		MyTreeModel model = (MyTreeModel) myTree.getModel();
			int selRow = myTree.getClosestRowForLocation( e.getX(), e.getY());
			TreePath path = myTree.getPathForLocation( e.getX(),e.getY());
			if( selRow != -1) {
				Rectangle bounds = myTree.getRowBounds( selRow);
				boolean outside = e.getX() < bounds.x || e.getX() > bounds.x + bounds.width || e.getY() < bounds.y || e.getY() >= bounds.y + bounds.height;
				if( !outside) {
					ConsoleLog.Print( "[Navigator] TestSuite Selected: " + path.getPathCount());
					
					MainWindow.setSuitePath(path.getPathComponent(1).toString());
					
					MainWindow frameInstance = getMainWindowInstance();
									
					if (path.getPathCount() <= SUITE_PATH_LENGTH){
						frameInstance.setPanelType(MainWindow.TESTING_UNIT);
						MainWindow.setCasePath("");
					}
					
//					if(path.getPathCount() == SUITE_PATH_LENGTH){
//						node = (FileNode) path.getPathComponent(SUITE_PATH_LENGTH-1); 
//						if(node.isHttpTestCase()){
//							frameInstance.setPanelType(MainWindow.CASE_EDITOR_SETTINGS);
//						}
//					}
					
					if(path.getPathCount() == CASE_PATH_LENGTH){
						node = (FileNode) path.getPathComponent(CASE_PATH_LENGTH-1); 
						if(node.isTestList() || node.isSuite()){
							frameInstance.setPanelType(MainWindow.TESTING_UNIT);
							MainWindow.setCasePath("");
						}else{
							frameInstance.setPanelType(MainWindow.CASE_EDITOR_SETTINGS);
							MainWindow.setCasePath(path.getPathComponent(2).toString());
						}
					}
					
					if (path.getPathCount() == CASE_DETAIL_PATH_LENGTH){
						node = (FileNode) path.getPathComponent(CASE_DETAIL_PATH_LENGTH-1); 
						
						if(node.isFaultInjectionTestCase())
							frameInstance.setPanelType(MainWindow.CASE_EDITOR_FAULT);
						else if(node.isHttpTestCase())
							frameInstance.setPanelType(MainWindow.CASE_EDITOR_HTTP);
						else
							frameInstance.setPanelType(MainWindow.CASE_EDITOR_SETTINGS);
						
						MainWindow.setCasePath(path.getPathComponent(2).toString());
						
					}
					MainWindow.setEndpointPath(((FileNode)path.getLastPathComponent()).getAbsolutePath());
					
					if (SwingUtilities.isRightMouseButton(e))  
				    	myTree.setSelectionRow(selRow); 
				    
				    if (e.getClickCount() == 2 && !e.isConsumed()) {
				        e.consume();
				        
				     
				       if(model.isLeaf(path.getLastPathComponent())){
				    	  ConsoleLog.Print( "[Navigator] TestSuite Selected");
				       }
				        	
				   }
				}
			
			}
			ShowPopup(e);
		}
    	
		/**
		 * 
		 *  @param e - triggered mouse event 
		 */
    	public void mouseReleased(MouseEvent e) {
    		 ShowPopup(e);
	    }
    	
    	/**
    	 * 
    	 * @param e - triggered mouse event 
    	 */
    	private void ShowPopup(MouseEvent e) {
	        if (e.isPopupTrigger()) {
	            treeMenu.show(e.getComponent(),
	                       e.getX(), e.getY());
	        }
	    }
    	
    }
}

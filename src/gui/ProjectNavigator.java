package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
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
import data.HttpRequestData;


public class ProjectNavigator extends JPanel {
	
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
	
	private final int SUITE_PATH_LENGTH = 2;
	private final int CASE_PATH_LENGTH = 3;
	private final int CASE_DETAIL_PATH_LENGTH = 4;
	
	public static final int TEST_UNIT = 2;
	public static final int CASE_EDITOR_SETTINGS = 3;
	public static final int CASE_EDITOR_HTTP = 4;
	public static final int CASE_EDITOR_FAULT = 5;
	private int panelType;
	
 	private NewTestSuiteDialog newTestSuiteWindow;
 	private NewTestCaseDialog newTestCaseWindow;
 	
	/**
	 * 
	 */
	public ProjectNavigator(File dir) {
		 
		 // Make a tree list with all the nodes, and make it a JTree
	     initTree(dir);
		 initPopupMenu();
		 
		 this.scrollPane = new JScrollPane();
		 this.setLayout(new BorderLayout());
		 this.scrollPane.getViewport().add(tree);
		 this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		 this.add(BorderLayout.CENTER, scrollPane); 
		 this.panelType = 0;
	}
	
	public int getPanelType() {
		return panelType;
	}

	public void setPanelType(int panelType) {
		this.panelType = panelType;
	}

	public Dimension getMinimumSize() {
	    return new Dimension(200, 400);
	}

	public Dimension getPreferredSize() {
	    return new Dimension(200, 400);
	}
	
	/**
	 * TODO osetreni
	 * @param f
	 */
	void delete(File f)  {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
		  
		  if(!f.delete()){
			  ConsoleLog.Print("Not deleted");
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
	 * method for refreshing tree by user
	 * for example when the filesystem is changed
	 * while the tool is running
	 */
	public static void refreshTree(){
		
		SwingUtilities.updateComponentTreeUI(tree);
		tree.treeDidChange();
		ConsoleLog.Print("tree refreshed");
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

	/**
	 * 
	 */
	private void initPopupMenu(){
		treeMenu = new JPopupMenu();
		addMenuItem(treeMenu,"Open Test List", new TestListListener());
		addMenuItem(treeMenu,"Edit", new EditListener());
		addMenuItem(treeMenu,"Delete", new DeleteListener());
		addMenuItem(treeMenu,"New Test Suite", new TestSuiteListener());
		addMenuItem(treeMenu,"New Test Case", new TestCaseListener());
		addMenuItem(treeMenu,"New HTTP Test", new HttpTestListener());
		addMenuItem(treeMenu,"New Fault Injection", new FaultInjectionListener());
		addMenuItem(treeMenu,"Refresh", new RefreshTree());
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
	class HttpTestListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			if(!MainWindow.getCasePath().isEmpty()){
				File newHttpCase = new File(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+MainWindow.getCasePath()+File.separator+"Http");
				File inputDir = new File(newHttpCase.getPath() + File.separator + "input");
	            File outputDir = new File(newHttpCase.getPath() + File.separator + "output");
	            File httpDataFile = new File(inputDir.getPath() + File.separator + "httpRequest.xml");
	            DataProvider writer = new DataProvider();
	            HttpRequestData httpData = new HttpRequestData();
	            
	            
				if (newHttpCase.exists()){
	            	 System.out.println("You can set only 1 http test to this case");
	             }else{
	            	 
	            	 newHttpCase.mkdir();
	            	 inputDir.mkdir();
	            	 outputDir.mkdir();
	            	 try{
		             		httpDataFile.createNewFile();
		             		writer.writeObject(httpDataFile.getPath(),httpData);
		             	 }catch(Exception b){
		             		 b.printStackTrace();
		             	 }
	            	 ProjectNavigator.refreshTree();
	            	 getMainWindowInstance().setContent(MainWindow.TESTCASE_EDITOR);
	            	 System.out.println("New project name: "+ MainWindow.getCasePath());
	            }
			}else{
				System.out.println("Test case not selected ");
			}
			ConsoleLog.Print("new HttpTest clicked");
		}
	}
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 */
	 
	class FaultInjectionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			
			if(!MainWindow.getCasePath().isEmpty()){
				File newHttpCase = new File(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+MainWindow.getCasePath()+File.separator+"FaultInjection");
				File inputDir = new File(newHttpCase.getPath() + File.separator + "input");
	            File outputDir = new File(newHttpCase.getPath() + File.separator + "output");
	            File faultInjectionDataFile = new File(inputDir.getPath() + File.separator + "faultInjection.xml");
	            DataProvider writer = new DataProvider();
	            FaultInjectionData faultData = new FaultInjectionData(newHttpCase.getPath());

	           
				if (newHttpCase.exists()){
	            	 System.out.println("You can set only 1 fault injection test to this case");
	             }else{
	            	 
	            	 newHttpCase.mkdir();
	            	 inputDir.mkdir();
	            	 outputDir.mkdir();
	            	 try{
	            		 faultInjectionDataFile.createNewFile();
	            		 writer.writeObject(faultInjectionDataFile.getPath(),faultData);
	            	 }catch(Exception b){
	            		 b.printStackTrace();
	            	 }
	            	
	            	 ProjectNavigator.refreshTree();
	            	 getMainWindowInstance().setContent(MainWindow.TESTCASE_EDITOR);
	            	 
	            	 System.out.println("New project name: "+ MainWindow.getCasePath());
	            }
			}else{
				System.out.println("Test case not selected ");
			}
			ConsoleLog.Print("new Fault Injection clicked");
		}
	}		
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class TestListListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			getMainWindowInstance().setContent(MainWindow.TESTING_UNIT);
			
			ConsoleLog.Print("test list clicked");
		}
	} 	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class EditListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			switch(panelType){
				case TEST_UNIT:
					getMainWindowInstance().setContent(MainWindow.TESTING_UNIT);
					break;
				case CASE_EDITOR_SETTINGS:	
				case CASE_EDITOR_FAULT:
				case CASE_EDITOR_HTTP:
					getMainWindowInstance().setContent(MainWindow.TESTCASE_EDITOR);
					
					break;
				default:
					break;
			}
			ConsoleLog.Print("file edit clicked");
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
				delete(node);
				refreshTree();
			}else{
				//TODO neco sem
			}
		}
	} 
	
	
	
	/**
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	class RefreshTree implements ActionListener{
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
    		MyTreeModel model = (MyTreeModel) myTree.getModel();
			int selRow = myTree.getClosestRowForLocation( e.getX(), e.getY());
			TreePath path = myTree.getPathForLocation( e.getX(),e.getY());
			if( selRow != -1) {
				Rectangle bounds = myTree.getRowBounds( selRow);
				boolean outside = e.getX() < bounds.x || e.getX() > bounds.x + bounds.width || e.getY() < bounds.y || e.getY() >= bounds.y + bounds.height;
				if( !outside) {
					ConsoleLog.Print( "Project Selected: " + path.getPathCount());
					
					MainWindow.setSuitePath(path.getPathComponent(1).toString());
					
									
					if (path.getPathCount() <= SUITE_PATH_LENGTH){
						panelType = TEST_UNIT;
						MainWindow.setCasePath("");
					}
					
					if(path.getPathCount() == CASE_PATH_LENGTH){
						panelType = CASE_EDITOR_SETTINGS;
						MainWindow.setCasePath(path.getPathComponent(2).toString());
					}
					
					if (path.getPathCount() == CASE_DETAIL_PATH_LENGTH){
						if(((FileNode)path.getLastPathComponent()).isFaultInjectionTestCase())
							panelType = CASE_EDITOR_FAULT;
						else
							panelType = CASE_EDITOR_HTTP;
						MainWindow.setCasePath(path.getPathComponent(2).toString());
						
					}
					MainWindow.setEndpointPath(((FileNode)path.getLastPathComponent()).getAbsolutePath());
					
					if (SwingUtilities.isRightMouseButton(e))  
				    	myTree.setSelectionRow(selRow); 
				    
				    if (e.getClickCount() == 2 && !e.isConsumed()) {
				        e.consume();
				        
				     
				       if(model.isLeaf(path.getLastPathComponent())){
				    	   //getMainWindowInstance().removeContent();
				    	   //getMainWindowInstance().setContent(new TestCaseEditor());
				    	   ConsoleLog.Print( "Project Selected: ahoj");
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

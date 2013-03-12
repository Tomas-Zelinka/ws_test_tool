package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

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
import modalWindows.NewProjectWindow;
import modalWindows.NewTestCaseWindow;
import modalWindows.NewTestSuiteWindow;


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
	
	
	
 	private NewProjectWindow newProjectWindow;
 	private NewTestSuiteWindow newTestSuiteWindow;
 	private NewTestCaseWindow newTestCaseWindow;
	
 
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
		 this.add(BorderLayout.CENTER, scrollPane);
	}
	
	public Dimension getMinimumSize() {
	    return new Dimension(200, 400);
	}

	public Dimension getPreferredSize() {
	    return new Dimension(200, 400);
	}
	
	void delete(File f)  {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
		  f.delete();
		    
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
		
		//addMenuItem(treeMenu,"New Test Project", new TestProjectListener());
		addMenuItem(treeMenu,"New Test Suite", new TestSuiteListener());
		addMenuItem(treeMenu,"New Test Case", new TestCaseListener());
		addMenuItem(treeMenu,"New Test List", new TestListListener());
		addMenuItem(treeMenu,"Edit", new EditListener());
		addMenuItem(treeMenu,"Delete", new DeleteListener());
		addMenuItem(treeMenu,"Refresh", new RefreshTree());
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
	}	*/	
	
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
	class DeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			File node = new File(MainWindow.getEndpointPath());
			ConsoleLog.Print(MainWindow.getEndpointPath());
			if(node.exists()){
				delete(node);
				refreshTree();
			}else{
				
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
					//ConsoleLog.Print( "Project Selected: " + path.getPathComponent(1));
					
					MainWindow.setSuitePath(path.getPathComponent(1).toString());
					
					if(path.getPathCount() > 3)
						MainWindow.setCasePath(path.getPathComponent(2).toString());
						
					MainWindow.setEndpointPath(((FileNode)path.getLastPathComponent()).getAbsolutePath());
					
					if (SwingUtilities.isRightMouseButton(e))  
				    	myTree.setSelectionRow(selRow); 
				    
				    if (e.getClickCount() == 2 && !e.isConsumed()) {
				        e.consume();
				        
				     
				       if(model.isLeaf(path.getLastPathComponent())){
				    	   getMainWindowInstance().removeContent();
				    	   getMainWindowInstance().setContent(new TestCaseEditor());
				    	   ConsoleLog.Print( "Project Selected: ahoj");
				       }
				        	
				   }
				}
			
			}
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

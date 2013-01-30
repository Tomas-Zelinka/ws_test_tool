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
import java.util.Collections;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import modalWindows.NewProjectWindow;
import modalWindows.NewTestCaseWindow;
import modalWindows.NewTestSuiteWindow;


public class ProjectNavigator extends JPanel {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 6495944797574501122L;
	
	private JTree tree;
	private JScrollPane scrollPane;
	//private String activeDirectory;
	private JPopupMenu treeMenu;
	private String rootPath;
	
	private JMenuItem newTestProject;
 	private JMenuItem newTestSuite;
 	private JMenuItem newTestCase;
 	private JMenuItem refresh;
 	
 	private NewProjectWindow newProjectWindow;
 	private NewTestSuiteWindow newTestSuiteWindow;
 	private NewTestCaseWindow newTestCaseWindow;
	
 
	/*
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
	
	
	
	
	private void initTree(File dir){
		setRootPath(dir.getPath());
		tree = new HighlightedTree(addNodes(null, dir));
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
	
	public void refreshTree(){
		//tree = null;
		
		//tree = new HighlightedTree(addNodes(null, ));
		//initTree(new File(getRootPath()));
DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		
		((DefaultTreeModel) tree.getModel()).reload();
		SwingUtilities.updateComponentTreeUI(tree);
		//tree.st
		///tree.treeDidChange();
		//tree.revalidate();
		//tree.repaint();
		//this.scrollPane.getViewport().revalidate();
		//this.scrollPane.getViewport().repaint();
		this.remove(scrollPane);
		scrollPane = new JScrollPane();
		this.add(scrollPane.getViewport().add(tree));
		this.revalidate();
		
		this.repaint();
	    System.out.println("tree refreshed");

		
		
		//System.out.println("refresh clicked " + getRootPath()+ " :" +  tree.getModel().getChildCount(root));
		
		
		
	}
	
	private void setRootPath(String path){
		this.rootPath = path;
	}
	
	private String getRootPath(){
		return this.rootPath;
	}
	
	
	/*
	 * 
	 */
	private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
	   
		String curPath = dir.getPath();
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(dir.getName());
		Vector<String> ol = new Vector<String>();
	    String[] tmp = dir.list();
	    File f;
	    Vector<String> files = new Vector<String>();
	    
		
		if (curTop != null) { // should only be null at root
	    	curTop.add(curDir);
	    }
	    	    
	    for (int i = 0; i < tmp.length; i++)
	    	ol.addElement(tmp[i]);
	    
	    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
	    	    
	    // Make two passes, one for Dirs and one for Files. This is #1.
	    
	    for (int i = 0; i < ol.size(); i++) {
	    	String thisObject = (String) ol.elementAt(i);
	    	String newPath;
	    	
	    	if (curPath.equals("."))
	    		newPath = thisObject;
	    	else
	    		newPath = curPath + File.separator + thisObject;
	    	if ((f = new File(newPath)).isDirectory())
	    		addNodes(curDir, f);
	    	else
	    		files.addElement(thisObject);
	    }
	    
	    // Pass two: for files.
	    for (int fnum = 0; fnum < files.size(); fnum++)
	    	curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
	    return curDir;
	}

	
	  
	private class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		
		private static final long serialVersionUID = 6647184007329048034L;
		
		public MyTreeCellRenderer() {
			this.setBorderSelectionColor(null); // remove selection border
			this.setBackgroundSelectionColor( null); // remove selection background since we paint the selected row ourselves
			this.setBackgroundNonSelectionColor(null);
		}

		public Color getBackground() {
			return null;
		}

	}
	
	
	
	
	private void initPopupMenu(){
		
		treeMenu = new JPopupMenu();
		newTestProject = new JMenuItem("New Test Project");
		treeMenu.add(newTestProject);
		
		this.newTestProject.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				
				newProjectWindow = new NewProjectWindow();
				newProjectWindow.setVisible(true);
				System.out.println("new Test Project clicked");
			}
			
		} );
		
		
		newTestSuite = new JMenuItem("New Test Suite");
		treeMenu.add(newTestSuite);
		
		this.newTestSuite.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				newTestSuiteWindow = new NewTestSuiteWindow();
				newTestSuiteWindow.setVisible(true);
			
				System.out.println("new Test Suite clicked");
			}
			
		} );
		
		newTestCase = new JMenuItem("New Test Case");
		treeMenu.add(newTestCase);
		
		this.newTestCase.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				newTestCaseWindow = new NewTestCaseWindow();
				newTestCaseWindow.setVisible(true);
				
				System.out.println("new Test Case clicked");
			}
			
		} );
		
		
		
		refresh = new JMenuItem("Refresh");
		treeMenu.add(refresh);
		
		this.refresh.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				
				refreshTree();
				
			}
			
		} );
	    
		JMenuItem reload = new JMenuItem("Reload");
		treeMenu.add(reload);
		
		reload.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				
				reloadTree();
				
			}
			
		} );
		
		
	}
	
	public void reloadTree(){
		this.add(scrollPane);
	}
	
	/*
	 * This inner class is responsible for colorful selection 
	 * in JTree Navigator frame
	 * 
	 * 
	 * @param 
	 */
	private class MyMouseAdapter extends MouseAdapter{
    	
    	
    	
    	public void mousePressed(MouseEvent e) {
    		JTree myTree = (JTree)e.getSource();
    		
			int selRow = myTree.getClosestRowForLocation( e.getX(), e.getY());
			TreePath path = myTree.getPathForLocation( e.getX(),e.getY());
			if( selRow != -1) {
				Rectangle bounds = myTree.getRowBounds( selRow);
				boolean outside = e.getX() < bounds.x || e.getX() > bounds.x + bounds.width || e.getY() < bounds.y || e.getY() >= bounds.y + bounds.height;
				if( !outside) {
					//System.out.println( "Project Selected: " + path.getPathComponent(1));
					
					MainWindow.setDataPath(path.getPathComponent(1).toString());
				    if (SwingUtilities.isRightMouseButton(e))  
				    	myTree.setSelectionRow(selRow);  
				      	
				}
			
			}
		}
    	
    	public void mouseReleased(MouseEvent e) {
    		 ShowPopup(e);
	    }
    	
    	private void ShowPopup(MouseEvent e) {
	        if (e.isPopupTrigger()) {
	            treeMenu.show(e.getComponent(),
	                       e.getX(), e.getY());
	        }
	    }
    	
    }
  
	 
	  
	
	
	
}

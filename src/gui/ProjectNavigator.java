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
import javax.swing.tree.TreePath;

import modalWindows.NewProjectWindow;


public class ProjectNavigator extends JPanel {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 6495944797574501122L;
	
	private JTree tree;
	private JScrollPane scrollPane;
	//private String activeDirectory;
	private JPopupMenu treeMenu;
	private NewProjectWindow newProjectWindow;
	
	/** Construct a FileTree */
	public ProjectNavigator(File dir) {
		 
		// Make a tree list with all the nodes, and make it a JTree
	     initTree(dir);
		 initPopupMenu();
		 
		 
		 this.scrollPane = new JScrollPane();
		 this.setLayout(new BorderLayout());
		 this.scrollPane.getViewport().add(tree);
		 this.add(BorderLayout.CENTER, scrollPane);
	}

	/** Add nodes from under "dir" into curTop. Highly recursive. */
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

	public Dimension getMinimumSize() {
	    return new Dimension(200, 400);
	}

	public Dimension getPreferredSize() {
	    return new Dimension(200, 400);
	}
	  
	  
	private void initTree(File dir){
		tree = new HighlightedTree(addNodes(null, dir));
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		
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
	
	
	private void initPopupMenu(){
		
		treeMenu = new JPopupMenu();
	    JMenuItem menuItem = new JMenuItem();
		
	    menuItem = new JMenuItem("New Project");
	    menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				newProjectWindow = new NewProjectWindow();
				newProjectWindow.setVisible(true);
				System.out.println("new Test Project clicked");
			}
			
		});
	    treeMenu.add(menuItem);
	    menuItem = new JMenuItem("Another popup menu item");
	    menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				System.out.println("file open clicked");
			}
			
		});
	    treeMenu.add(menuItem);
	    
	}
	
	/*
	 * This inner class is responsible for colorful selection 
	 * in JTree Navigator frame
	 * 
	 * 
	 * @param 
	 */
	protected class MyMouseAdapter extends MouseAdapter{
    	
    	
    	
    	public void mousePressed(MouseEvent e) {
    		JTree myTree = (JTree)e.getSource();
    		
			int selRow = myTree.getClosestRowForLocation( e.getX(), e.getY());
			TreePath path = myTree.getPathForLocation( e.getX(),e.getY());
			if( selRow != -1) {
				Rectangle bounds = myTree.getRowBounds( selRow);
				boolean outside = e.getX() < bounds.x || e.getX() > bounds.x + bounds.width || e.getY() < bounds.y || e.getY() >= bounds.y + bounds.height;
				if( !outside) {
					System.out.println( "Project Selected: " + path.getPathComponent(1));
					
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
  
	 
	  
	protected class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		
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
	
	
}

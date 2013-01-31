package gui;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class MyTreeModel implements TreeModel {

	private String root; // The root identifier

	private Vector<TreeModelListener> listeners; // Declare the listeners vector

	public MyTreeModel(File dir) {

		root = dir.getPath();
		// FileNode tempFile = new FileNode(root);
		//root = tempFile.getParent();

		listeners = new Vector<TreeModelListener>();
	}

	public Object getRoot() {
		return (new FileNode(root));
	}

	public Object getChild(Object parent, int index) {
		FileNode directory = (FileNode) parent;
		String[] directoryMembers = directory.list();
		return (new FileNode(directory, directoryMembers[index]));
	}

	public int getChildCount(Object parent) {
		FileNode fileSystemMember = (FileNode) parent;
	    
		if (fileSystemMember.isDirectory()) {
	    	String[] directoryMembers = fileSystemMember.list();
	    	return directoryMembers.length;
	    
		}else {
			return 0;
	    }
	}

	public int getIndexOfChild(Object parent, Object child) {
		FileNode directory = (FileNode) parent;
	    FileNode directoryMember = (FileNode) child;
	    String[] directoryMemberNames = directory.list();
	    int result = -1;

	    for (int i = 0; i < directoryMemberNames.length; ++i) {
	    	if (directoryMember.getName().equals(directoryMemberNames[i])) {
	    		result = i;
	    		break;
	    	}
	    }

	    return result;
	}

	public boolean isLeaf(Object node) {
		return ((FileNode) node).isFile();
	}

	public void addTreeModelListener(TreeModelListener l) {
		if (l != null && !listeners.contains(l)) {
			listeners.addElement(l);
	    }
	}

	public void removeTreeModelListener(TreeModelListener l) {
		if (l != null) {
			listeners.removeElement(l);
	    }
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
	    // Does Nothing!
	}

	public void fireTreeNodesInserted(TreeModelEvent e) {
	    Enumeration<TreeModelListener> listenerCount = listeners.elements();
	    while (listenerCount.hasMoreElements()) {
	    	TreeModelListener listener = (TreeModelListener) listenerCount.nextElement();
	    	listener.treeNodesInserted(e);
	    }
	}

	public void fireTreeNodesRemoved(TreeModelEvent e) {
	    Enumeration<TreeModelListener> listenerCount = listeners.elements();
	    while (listenerCount.hasMoreElements()) {
	    	TreeModelListener listener = (TreeModelListener) listenerCount.nextElement();
	    	listener.treeNodesRemoved(e);
	    }

	}

	public void fireTreeNodesChanged(TreeModelEvent e) {
	    Enumeration<TreeModelListener> listenerCount = listeners.elements();
	    while (listenerCount.hasMoreElements()) {
	    	TreeModelListener listener = (TreeModelListener) listenerCount.nextElement();
	    	listener.treeNodesChanged(e);
	    }

	}

	public void fireTreeStructureChanged(TreeModelEvent e) {
	    Enumeration<TreeModelListener> listenerCount = listeners.elements();
	    while (listenerCount.hasMoreElements()) {
	    	TreeModelListener listener = (TreeModelListener) listenerCount.nextElement();
	    	listener.treeStructureChanged(e);
	    }

	}
	  
	  
	public class FileNode extends java.io.File {

		private static final long serialVersionUID = -2796997206556668704L;

		public FileNode(String directory) {
	        super(directory);
	    }

	    public FileNode(FileNode parent, String child) {
	        super(parent, child);
	    }

	    @Override
	    public String toString() {
	        return getName();
	    }
	}
			  
			  
}
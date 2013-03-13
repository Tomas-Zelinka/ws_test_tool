package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import logging.ConsoleLog;

public class TestCaseEditor extends JSplitPane {

	
	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = -2860238189144324557L;

	/**
	 * Width of the resizing frames
	 */
	private final int SPLIT_RESIZERS_WIDTH = 5;
	
	/**
	 * Extended JPanel holding JTree for document navigation
	 */
	private ProjectNavigator navigator;
	
	/**
	 * JPanel holding the various editors and controls
	 */
	private JPanel editor;
	
	
	
	public TestCaseEditor(){
		
		
		super(JSplitPane.HORIZONTAL_SPLIT);
		
		File root = new File(MainWindow.getDataRoot());
		
		if(!root.exists()){
			boolean wasDirectoryMade = root.mkdirs();
		    if(wasDirectoryMade)
		    	ConsoleLog.Print("Direcoty Created");
		}
		
		this.editor = new JPanel();
		this.navigator = new  ProjectNavigator(root);
		JTextArea testingArea = new JTextArea("ahoj",5,50);
		ToolBox toolBox = new ToolBox(); 
		JScrollPane paneEditRequest = new JScrollPane();
		JScrollPane paneEditResponse = new JScrollPane();
		JTextArea responseArea = new JTextArea("",5,10);
		JTextArea requestArea = new JTextArea("",5,10);
		JButton start = new JButton("Start");
		
		
		this.setDividerSize(SPLIT_RESIZERS_WIDTH);
		this.setLeftComponent(this.navigator);
		this.setRightComponent(this.editor);
		JSplitPane windows = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,paneEditResponse,paneEditRequest);
		
		testingArea.setPreferredSize(new Dimension(getWidth(),40));
		
		responseArea.setPreferredSize(new Dimension(getWidth(),100));
		
		requestArea.setPreferredSize(new Dimension(getWidth(),100));
		
		paneEditRequest.getViewport().add(requestArea);
		paneEditRequest.setBorder(BorderFactory.createEmptyBorder());
		
		paneEditResponse.getViewport().add(responseArea);
		paneEditResponse.setBorder(BorderFactory.createEmptyBorder());
		
		windows.setDividerSize(5);
		windows.setResizeWeight(0.45);
		
		toolBox.addButton(start);
		
		testingArea.add(toolBox,BorderLayout.LINE_START);
		testingArea.add(new JSeparator(),BorderLayout.SOUTH );
		this.editor.setLayout(new BorderLayout());
		this.editor.add(toolBox,BorderLayout.NORTH);
		this.editor.add(windows,BorderLayout.CENTER);
		
	}

}

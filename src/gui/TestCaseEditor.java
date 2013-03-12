package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

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
	 * Splitpane containing Project navigator and action place
	 */
	private JSplitPane centerPane;
	
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
		
		this.editor = new PlainPanel();
		this.navigator = new  ProjectNavigator(root);
		this.setDividerSize(SPLIT_RESIZERS_WIDTH);
		this.setLeftComponent(this.navigator);
		this.setRightComponent(this.editor);
		JTextArea testingArea = new JTextArea("ahoj",5,50);
		//JPanel testingArea = new JPanel();
		JScrollPane tablePane = new JScrollPane();
		ToolBox toolBox = new ToolBox(); 
		
		
		
		
		
		testingArea.setPreferredSize(new Dimension(getWidth(),40));
		
		JScrollPane paneEditRequest = new JScrollPane();
		JScrollPane paneEditResponse = new JScrollPane();
		
		JTextArea responseArea = new JTextArea("",5,10);
		responseArea.setPreferredSize(new Dimension(getWidth(),100));
		
		
		JTextArea requestArea = new JTextArea("",5,10);
		requestArea.setPreferredSize(new Dimension(getWidth(),100));
		
		
		paneEditRequest.getViewport().add(requestArea);
		paneEditResponse.getViewport().add(responseArea);
		
		JSplitPane windows = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,paneEditResponse,paneEditRequest);
		windows.setDividerSize(5);
		windows.setResizeWeight(0.45);
		
		
		//String[] columnNames = {"Attribute","Value"};
		//String[][] rowsData = {{"atribute","value"},{"atribute","value"},{"atribute","value"},{"atribute","value"}};
		//DefaultTableModel model = new DefaultTableModel(rowsData,columnNames);
		//InteractiveTableModel tableModel  = new InteractiveTableModel(columnNames);
		//JTable table = new JTable(model);
		//table.setBackground(Color.lightGray);
		//table.
		//table.setPreferredSize(new Dimension(200,60));
		//table.setSize(new Dimension(200,60));
		//testingArea.setLayout(new BorderLayout());
		//tablePane.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
		//tablePane.getViewport().add(table);
		//testingArea.add(tablePane,BorderLayout.CENTER);
		
		JButton start = new JButton("Start");
		
		toolBox.addButton(start);
		
		testingArea.add(toolBox,BorderLayout.LINE_START);
		testingArea.add(new JSeparator(),BorderLayout.SOUTH );
		this.editor.setLayout(new BorderLayout());
		this.editor.add(toolBox,BorderLayout.NORTH);
		this.editor.add(windows,BorderLayout.CENTER);
		this.editor.add(new JSeparator(),BorderLayout.SOUTH);
	}
	
	
	
	/**
	 * 
	 */
	private void initCenterPane(){
		
		File root = new File(MainWindow.getDataRoot());
		
		if(!root.exists()){
			boolean wasDirectoryMade = root.mkdirs();
		    if(wasDirectoryMade)
		    	ConsoleLog.Print("Direcoty Created");
		}
		
		this.editor = new PlainPanel();
		this.navigator = new  ProjectNavigator(root);
		this.centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,this.navigator,this.editor);
		this.centerPane.setDividerSize(SPLIT_RESIZERS_WIDTH);
		
	}
	

	
}

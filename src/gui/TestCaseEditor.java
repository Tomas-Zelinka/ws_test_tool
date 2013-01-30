package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class TestCaseEditor extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2860238189144324557L;

	public TestCaseEditor(){
		
		//JTextArea testingArea = new JTextArea("ahoj",5,50);
		JPanel testingArea = new JPanel();
		//JScrollPane tablePane = new JScrollPane();
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
		windows.setDividerSize(20);
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
		
		
		
		testingArea.add(toolBox,BorderLayout.LINE_START);
		testingArea.add(new JSeparator(),BorderLayout.SOUTH );
		this.setLayout(new BorderLayout());
		this.add(toolBox,BorderLayout.NORTH);
		this.add(windows,BorderLayout.CENTER);
		this.add(new JSeparator(),BorderLayout.SOUTH);
	}
	
}

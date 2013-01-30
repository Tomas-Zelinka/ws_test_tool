package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class TestCaseEditor extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2860238189144324557L;

	public TestCaseEditor(){
		
		//JTextArea testingArea = new JTextArea("ahoj",5,50);
		JPanel testingArea = new JPanel();
		testingArea.setPreferredSize(new Dimension(getWidth(),100));
		
		JScrollPane paneEditRequest = new JScrollPane();
		JScrollPane paneEditResponse = new JScrollPane();
		
		JTextArea responseArea = new JTextArea("ahoj",5,10);
		responseArea.setPreferredSize(new Dimension(getWidth(),100));
		
		
		JTextArea requestArea = new JTextArea("ahoj",5,10);
		requestArea.setPreferredSize(new Dimension(getWidth(),100));
		
		
		paneEditRequest.getViewport().add(requestArea);
		paneEditResponse.getViewport().add(responseArea);
		
		JSplitPane windows = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,paneEditResponse,paneEditRequest);
		windows.setDividerSize(20);
		windows.setResizeWeight(0.45);
		
	
		testingArea.setLayout(new BorderLayout());
		testingArea.add(new JSeparator(),BorderLayout.SOUTH );
		this.setLayout(new BorderLayout());
		this.add(testingArea,BorderLayout.NORTH);
		this.add(windows,BorderLayout.CENTER);
		this.add(new JSeparator(),BorderLayout.SOUTH);
	}
	
}

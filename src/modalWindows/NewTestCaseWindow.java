package modalWindows;

import gui.MainWindow;
import gui.ProjectNavigator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;


public class NewTestCaseWindow extends InputModalWindow {
	/**
	 * 
	 */
	
	private JTextField testCaseName;
	private JTextField path;
	private static final long serialVersionUID = 9187751988881264097L;

	public NewTestCaseWindow(){
		super("New Test Case", 640,480);
		
	}
	
		
	/*
	 * 
	 */
	@Override
	protected void putContent(){

		JPanel labels = new JPanel(new GridLayout(0,1,0,10));
		JPanel fields = new JPanel(new GridLayout(0,1,0,10));
		
		String selectedPath = MainWindow.getSuitePath();
		if(selectedPath.isEmpty()){
			messageLabel.setText("Path not selected. Please select Test Suite Path");
			
		}
		
		testCaseName = new JTextField(10);
		path = new JTextField(MainWindow.getSuitePath()+ File.separator,10);
		JLabel label = new JLabel("Test Case: ");
		JLabel pathLabel = new JLabel("Path: ");
        
		labels.add(pathLabel);
		labels.add(label);
		
		path.setEditable(false);
			
		fields.add(path);
		fields.add(testCaseName);
		fields.setSize(new Dimension(200,50));
		
				
		label.setHorizontalAlignment(JLabel.RIGHT);
        label.setFont(label.getFont().deriveFont(Font.PLAIN,14.0f));
        pathLabel.setHorizontalAlignment(JLabel.RIGHT);
        pathLabel.setFont(label.getFont().deriveFont(Font.PLAIN,14.0f));
        
        getSecondInsidePanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getSecondInsidePanel().setLayout(new BorderLayout());
        getSecondInsidePanel().add(labels,BorderLayout.WEST);
        getSecondInsidePanel().add(fields);
        
		
		
	}
	
	/*
	 * 
	 * 
	 */
	@Override
	protected String testEmptyInput(){
		return getCaseName();
	}
	
	/*
	 * 
	 */
	private String getCaseName(){
		return testCaseName.getText();
	}
	
	/*
	 * 
	 * 
	 */
	@Override
	protected void initButtons(){
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new OkButtonAction());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonAction()); 
		Document projectField = testCaseName.getDocument();
		projectField.addDocumentListener(new ButtonStateController(okButton,testCaseName,messageLabel));
		
		if(testEmptyInput().isEmpty())
			okButton.setEnabled(false);
		
		addButton(okButton);
		addButton(cancelButton);
		
	}
	
	/*
	 * 
	 */
	private class OkButtonAction  implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
             
			File newTestCase = new File(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+getCaseName());
             
			if (newTestCase.exists()){
            	 messageLabel.setText("Test suite with this name already exists");
             }else{
            	 
            	 newTestCase.mkdir();
            	 ProjectNavigator.refreshTree();
			
            	 System.out.println("New project name: "+ getCaseName());
            	 setVisible(false);
            	 dispose();
             }
		}
	}
	/*
	 * 
	 */
	private class CancelButtonAction  implements ActionListener{
				
		public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
	}
	
}

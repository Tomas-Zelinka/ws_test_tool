package modalWindows;

import gui.MainWindow;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.Document;


public class NewProjectWindow extends ModalWindow{

	/**
	 * 
	 */
	
	private JTextField projectName;
	private static final long serialVersionUID = 9187751988881264097L;

	public NewProjectWindow(){
		super("New Test Project", 640,480);
		
	}
	
		
	/*
	 * 
	 */
	@Override
	protected void putContent(){

		projectName = new JTextField(MainWindow.dataPath,30);
		JLabel label = new JLabel("Project Name");
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(label.getFont().deriveFont(Font.PLAIN,14.0f));
        
        addToSecondPanel(label);
        addToSecondPanel(projectName);
		
		
	}
	
	/*
	 * 
	 * 
	 */
	@Override
	protected String testEmptyInput(){
		return getProjectName();
	}
	
	/*
	 * 
	 */
	private String getProjectName(){
		return projectName.getText();
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
		Document projectField = projectName.getDocument();
		projectField.addDocumentListener(new ButtonStateController(okButton));
		
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
             System.out.println("New project name: "+getProjectName());
         	 setVisible(false);
             dispose();
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

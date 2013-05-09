package modalWindows;

import gui.MainWindow;
import gui.Navigator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import logging.ConsoleLog;
import data.DataProvider;
import data.TestCaseSettingsData;

/**
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class NewTestCaseDialog extends InputModalWindow {
   /**
	* 
	*/
	private JTextField testCaseName;
	private JTextField selectedTestSuite;
	private JPanel labels;
	private JPanel fields;
	private JLabel label;
	private JLabel pathLabel;
	private JButton okButton;
	private JButton cancelButton;
	
	private static final long serialVersionUID = 9187751988881264097L;

	public NewTestCaseDialog(){
		super("New Test Case", 640,580);
	}
	
	@Override
	protected void initComponents() {
		
		labels = new JPanel(new GridLayout(0,1,0,10));
		fields = new JPanel(new GridLayout(0,1,0,10));
		testCaseName = new JTextField(10);
		selectedTestSuite = new JTextField(MainWindow.getSuitePath(),10);
		label = new JLabel("Test Case: ");
		pathLabel = new JLabel("Test Suite: ");
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
	}
		
	/**
	 * 
	 */
	@Override
	protected void putContent(){
		
		String selectedPath = MainWindow.getSuitePath();
		if(selectedPath.isEmpty()){
			messageLabel.setText("Test suit not selected. Please select Test Suite");
		}
	
		labels.add(pathLabel);
		labels.add(label);
		
		selectedTestSuite.setEditable(false);
		
		fields.add(selectedTestSuite);
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
        
        addWindowListener(new WindowAdapter(){ 
        	public void windowOpened( WindowEvent e ){ 
            	testCaseName.requestFocus(); 
        	} 
        });
    }
  
	
	/**
	 * 
	 * 
	 */
	@Override
	protected String testEmptyInput(){
		return getCaseName();
	}
	
	/**
	 * 
	 */
	private String getCaseName(){
		return testCaseName.getText();
	}
	
	/**
	 * 
	 * 
	 */
	@Override
	protected void initButtons(){
		
		okButton.addActionListener(new OkButtonAction());
		cancelButton.addActionListener(new CancelButtonAction()); 
		
		Document projectField = testCaseName.getDocument();
		projectField.addDocumentListener(new ButtonStateController(okButton,testCaseName,messageLabel));
		
		if(testEmptyInput().isEmpty())
			okButton.setEnabled(false);
		
		addButton(okButton);
		addButton(cancelButton);
		
	}
	
	/**
	 * 
	 */
	private class OkButtonAction  implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
             
			File newTestCase = new File(MainWindow.getSuitePath()+File.separator+getCaseName());
			File settings = new File(newTestCase.getPath() + File.separator + "settings.xml");
            DataProvider writer = new DataProvider();
            TestCaseSettingsData testCase = new TestCaseSettingsData();
            testCase.setName(getCaseName());
            testCase.setPath(newTestCase.getPath());
			
			if (newTestCase.exists()){
            	 messageLabel.setText("Test case with this name already exists");
             }else{
            	 
            	 newTestCase.mkdir();
            	 try{
            		 settings.createNewFile();
            		 writer.writeObject(settings.getPath(), testCase);
            		 
            	 }catch(Exception b){
            		 b.printStackTrace();
            	 }
            	 Navigator.refreshTree();
            	 MainWindow.setCasePath(newTestCase.getName());
            	 ConsoleLog.Print("[ModalWindow] New case suite created,name: "+ getCaseName());
            	 setVisible(false);
            	 dispose();
             }
		}
	}
	/**
	 * 
	 */
	private class CancelButtonAction  implements ActionListener{
				
		public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
	}
	

}

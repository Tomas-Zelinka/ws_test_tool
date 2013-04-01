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


public class AddHttpTestDialog extends InputModalWindow {

	
	
	private JTextField httpTestName;
	private JTextField selectedTestSuite;
	private JTextField selectedTestCase;
	
	private JPanel labels;
	private JPanel fields;
	
	private JLabel testSuiteLabel;
	private JLabel testCaseLabel;
	private JLabel testNameLabel;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private String selectedTestSuitePath;
	private String selectedTestCasePath;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4044693181960816406L;

	public AddHttpTestDialog() {
		
		super("New Http Test", 640,580);
		
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void putContent() {
		
		
		if(selectedTestSuitePath.isEmpty() || selectedTestCasePath.isEmpty()){
			messageLabel.setText("Test case not selected. Please select Test Case");
		}
	     
		labels.add(testSuiteLabel);
		labels.add(testCaseLabel);
		labels.add(testNameLabel);
		
		selectedTestSuite.setEditable(false);
		selectedTestCase.setEditable(false);
			
		fields.add(selectedTestSuite);
		fields.add(selectedTestCase);
		fields.add(httpTestName);
		fields.setSize(new Dimension(200,50));
		
				
		testCaseLabel.setHorizontalAlignment(JLabel.RIGHT);
		testCaseLabel.setFont(testCaseLabel.getFont().deriveFont(Font.PLAIN,14.0f));
        testSuiteLabel.setHorizontalAlignment(JLabel.RIGHT);
        testSuiteLabel.setFont(testCaseLabel.getFont().deriveFont(Font.PLAIN,14.0f));
        testNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        testNameLabel.setFont(testNameLabel.getFont().deriveFont(Font.PLAIN,14.0f));
        
        
        getSecondInsidePanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getSecondInsidePanel().setLayout(new BorderLayout());
        getSecondInsidePanel().add(labels,BorderLayout.WEST);
        getSecondInsidePanel().add(fields);
		
	}

	@Override
	protected String testEmptyInput() {
		
		return getCaseName();
	}
	/**
	 * 
	 */
	private String getCaseName(){
		return httpTestName.getText();
	}
	
	@Override
	protected void initButtons() {
		
		okButton.addActionListener(new OkButtonAction());
		cancelButton.addActionListener(new CancelButtonAction()); 
		Document projectField = httpTestName.getDocument();
		projectField.addDocumentListener(new ButtonStateController(okButton,httpTestName,messageLabel));
		
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
             
			File newHttpCase = new File(MainWindow.getDataRoot()+File.separator+ selectedTestSuitePath + File.separator +selectedTestCasePath +File.separator +"Http");
			File inputDir = new File(newHttpCase.getPath() + File.separator + "input");
            File outputDir = new File(newHttpCase.getPath() + File.separator + "output");
			
			if (newHttpCase.exists()){
            	 messageLabel.setText("Test suite with this name already exists");
             }else{
            	 
            	 newHttpCase.mkdir();
            	 inputDir.mkdir();
            	 outputDir.mkdir();
//            	 try{
//            		 settings.createNewFile();
//            	 }catch(Exception b){
//            		 b.printStackTrace();
//            	 }
            	 ProjectNavigator.refreshTree();
			
            	 System.out.println("New project name: "+ getCaseName());
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
	
	
	
	
	
	protected void initComponents(){
		
		selectedTestSuitePath = MainWindow.getSuitePath();
		selectedTestCasePath = MainWindow.getCasePath();
		labels = new JPanel(new GridLayout(0,1,0,10));
		fields = new JPanel(new GridLayout(0,1,0,10));
		
		testSuiteLabel = new JLabel("TestSuite: ");
		testCaseLabel = new JLabel("Test Case: ");
		testNameLabel = new JLabel("Http test name: ");
		
		httpTestName = new JTextField(10);
		selectedTestSuite = new JTextField(selectedTestSuitePath,10);
		selectedTestCase = new JTextField(selectedTestCasePath,10);
		
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		
		
		
	}
}

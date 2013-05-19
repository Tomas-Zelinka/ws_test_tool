package cz.vutbr.fit.dp.xzelin15.modalWindows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class AddRemoteUnitDialog extends InputModalWindow {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4308031783655291131L;
	private boolean okButtonClicked;
	private JLabel hostLabel;
	private JLabel portLabel;
	private JTextField hostField;
	private JTextField portField;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel labels;
	private JPanel fields;
	
	
	public AddRemoteUnitDialog(){
		super("Add Remote Unit",640,510);
	}
	@Override
	protected void putContent() {
		
		labels.add(hostLabel);
		labels.add(portLabel);
		fields.add(hostField);
		fields.add(portField);
		
		 getSecondInsidePanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	     getSecondInsidePanel().setLayout(new BorderLayout());
	     getSecondInsidePanel().add(labels,BorderLayout.WEST);
	     getSecondInsidePanel().add(fields);
		
	     addWindowListener(new WindowAdapter(){ 
	        	public void windowOpened( WindowEvent e ){ 
	        		hostField.requestFocus(); 
	        	} 
	        });
		
	}

	@Override
	protected void initComponents() {
		
		
		
		labels = new JPanel(new GridLayout(0,1,0,10));
		fields = new JPanel(new GridLayout(0,1,0,10));
		 hostLabel = new JLabel("Host: ");
		 portLabel = new JLabel("Port: ");
		 hostField = new JTextField(20);
		portField = new JTextField(20);
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		
		
		if(testEmptyInput().isEmpty())
			okButton.setEnabled(false);
	}

	@Override
	protected String testEmptyInput() {
		return this.hostField.getText();
	}

	@Override
	protected void initButtons() {
		
		Document hostFieldDocument = hostField.getDocument();
		hostFieldDocument.addDocumentListener(new ButtonStateController(okButton,hostField,messageLabel));
		this.getRootPane().setDefaultButton(okButton);
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
	         	dispose();
			}
		});
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setOkButtonClicked(true);
				dispose();
				
			}
		});
		
		addButton(cancelButton);
		
		addButton(okButton);
		 
	}
	
	
	public void setOkButtonClicked(boolean status){
		this.okButtonClicked = status;
	}
	
	public boolean isOkButtonClicked(){
		return this.okButtonClicked;
	}
	
	public String getHost(){
		return this.hostField.getText();
	}
	
	public int getPort(){
		return Integer.parseInt(this.portField.getText());
	}

}

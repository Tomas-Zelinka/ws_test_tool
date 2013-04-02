package gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.TestCaseSettingsData;

public class TestCaseSettings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6666330414940182678L;

	
	private JTextField hostField;
	private JTextField portField;
	private JTextField threadsNumberField;
	 
	private JLabel hostLabel;
	
	
	private JPanel connectionSettings;
	private JPanel requestSettings;
	
	private TestCaseSettingsData settingsData;
	
	public TestCaseSettings(){
		initComponents();
		
		
		this.setLayout(new BorderLayout());
		
		connectionSettings.setBorder(BorderFactory.createTitledBorder("Connection Settings"));
		requestSettings.setBorder(BorderFactory.createTitledBorder("Request Settings"));
		
		connectionSettings.add(hostLabel);
		connectionSettings.add(hostField);
		
		add(connectionSettings,BorderLayout.NORTH);		
		add(requestSettings,BorderLayout.CENTER);		
	}
	
	
	
	private void initComponents(){
		connectionSettings = new JPanel();
		requestSettings = new JPanel();
		
		hostField = new JTextField(20);
		hostLabel = new JLabel("Hostname");
		
		settingsData = new TestCaseSettingsData();
	}
	
	public TestCaseSettingsData getSettingsData(){
		return this.settingsData;
	}
	
	
}

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import data.TestCaseSettingsData;

public class TestCaseSettings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6666330414940182678L;

	
	private JTextField loopNumberField;
	private JTextField threadsNumberField;
	private JTextField proxyHostField;
	private JTextField proxyPortField;
	private JTextField proxyTestedPortField;
	private JRadioButton runButton;
	 
	private JLabel proxyHostLabel;
	private JLabel proxyTestedPortLabel;
	private JLabel proxyPortLabel;
	private JLabel loopNumberLabel;
	private JLabel threadsNumberLabel;
	private JLabel runLabel;
	private JLabel httpSettingsLabel;
	private JLabel proxySettingsLabel;
	
	private JPanel httpRequestSettings;
	private JPanel proxySettings;
	private JPanel proxyLabels;
	private JPanel proxyFields;
	private JPanel httpLabels;
	private JPanel httpFields;
	private JPanel httpFieldsBox;
	private JPanel proxyFieldBox;
	private JSeparator separator;
	
	private TestCaseSettingsData settingsData;
	
	public TestCaseSettings(){
		initComponents();
		setupComponents();	
	}
		
	private void initComponents(){
		
		httpRequestSettings = new JPanel();
		proxySettings = new JPanel();
		proxyLabels = new JPanel(new GridLayout(4, 1));
		proxyFields = new JPanel(new GridLayout(4, 1));
		httpLabels = new JPanel(new GridLayout(3, 1));
		httpFields = new JPanel(new GridLayout(3, 1));
		proxyHostField = new JTextField(20);
		proxyHostLabel = new JLabel("Hostname");
		httpFieldsBox = new JPanel(new BorderLayout());
		proxyPortField = new JTextField(20);
		proxyPortLabel = new JLabel("Port");
		httpSettingsLabel = new JLabel("HTTP SETTINGS");
		proxySettingsLabel = new JLabel("PROXY SETTINGS");
		separator = new JSeparator();
		proxyFieldBox = new JPanel(new BorderLayout());
		
		proxyTestedPortField = new JTextField(20);
		proxyTestedPortLabel = new JLabel("Tested Port");
		
		loopNumberField = new JTextField(20);
		loopNumberLabel = new JLabel("Requests number");
		
		threadsNumberField = new JTextField(20);
		
		threadsNumberLabel = new JLabel("Threads number");
		
		runButton = new JRadioButton();
		runLabel = new JLabel("Run test case");
		
		settingsData = new TestCaseSettingsData();
	}
	
	private void setupComponents(){
		
		this.setLayout(new BorderLayout());
		httpRequestSettings.setLayout(new BorderLayout());
		
		 
		httpLabels.add(threadsNumberLabel);
		httpLabels.add(loopNumberLabel);
		httpLabels.add(runLabel); 
		httpFields.add(threadsNumberField);
		httpFields.add(loopNumberField);
		httpFields.add(runButton);
		httpFields.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		
		
		proxyLabels.add(proxyHostLabel);
		proxyLabels.add(proxyPortLabel);
		proxyLabels.add(proxyTestedPortLabel);
		proxyFields.add(proxyHostField);
		proxyFields.add(proxyPortField);
		proxyFields.add(proxyTestedPortField);
		
		httpSettingsLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
			
		httpRequestSettings.add(httpLabels,BorderLayout.LINE_START);
		httpRequestSettings.add(httpFields,BorderLayout.CENTER);
		httpRequestSettings.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		
		proxySettings.add(proxyLabels,BorderLayout.LINE_START);
		proxySettings.add(proxyFields,BorderLayout.CENTER);
		
		separator.setBorder(BorderFactory.createEmptyBorder(20,0,0,20));
		separator.setForeground(Color.gray);
				
		httpSettingsLabel.setFont(new Font("Arial", Font.BOLD, 13));
		proxySettingsLabel.setFont(new Font("Arial", Font.BOLD, 13));
		
		proxyFieldBox.setBorder(BorderFactory.createEmptyBorder(5,10,10,20));
		proxyFieldBox.add(proxySettingsLabel,BorderLayout.NORTH);
		proxyFieldBox.add(proxySettings,BorderLayout.LINE_START);
			
		httpFieldsBox.setBorder(BorderFactory.createEmptyBorder(5,10,10,20));
		httpFieldsBox.add(httpSettingsLabel,BorderLayout.NORTH);
		httpFieldsBox.add(httpRequestSettings,BorderLayout.LINE_START);
		httpFieldsBox.add(separator,BorderLayout.SOUTH);
		
		
		
		add(httpFieldsBox,BorderLayout.NORTH);		
		add(proxyFieldBox,BorderLayout.CENTER);		
	}
	
	private String getEditorProxyHostName(){
		return proxyHostField.getText();
	}
	
	private void setEditorProxyHostName(String str){
		this.proxyHostField.setText(str);
	}
	
	
	
	private Integer getEditorProxyPort(){
		return Integer.parseInt(proxyPortField.getText());
	}
	
	private void setEditorProxyPort(Integer port){
		this.proxyPortField.setText(port.toString());
	}
	
	private Integer getEditorProxyTestedPort(){
		return Integer.parseInt(proxyTestedPortField.getText());
	}
	
	private void setEditorProxyTestedPort(Integer port){
		this.proxyTestedPortField.setText(port.toString());
	}
	
	private Integer getEditorThreadsNumber(){
		return Integer.parseInt(threadsNumberField.getText());
	}
	
	private void setEditorThreadsNumber(Integer number){
		this.threadsNumberField.setText(number.toString());
	}
	
	private Integer getEditorLoopCount(){
		return Integer.parseInt(loopNumberField.getText());
	}
	
	private void setEditorLoopNumber(Integer number){
		this.loopNumberField.setText(number.toString());
	}
	
	private boolean getEditorRun(){
		return runButton.isSelected();
	}
	
	private void setEditorRun(boolean run){
		this.runButton.setSelected(run);
	}
	
	private void saveData(){
		this.settingsData.setName(MainWindow.getCasePath());
		this.settingsData.setThreadsNumber(getEditorThreadsNumber());
		this.settingsData.setLoopNumber(getEditorLoopCount());
		this.settingsData.setProxyTestedPort(getEditorProxyTestedPort());
		this.settingsData.setProxyPort(getEditorProxyPort());
		this.settingsData.setProxyHost(getEditorProxyHostName());
		this.settingsData.setRun(getEditorRun());
	}
	
	
	private void loadData(){
		setEditorProxyPort(this.settingsData.getProxyPort());
		setEditorProxyHostName(this.settingsData.getProxyHost());
		setEditorThreadsNumber(this.settingsData.getThreadsNumber());
		setEditorLoopNumber(this.settingsData.getLoopNumber());
		setEditorProxyTestedPort(this.settingsData.getProxyTestedPort());
		setEditorRun(this.settingsData.getRun());
	}
	
	
	
	
	
	public void setSettingsData(TestCaseSettingsData data){
		this.settingsData = data;
		loadData();
	}
	
	public TestCaseSettingsData getSettingsData(){
		saveData();
		return this.settingsData;
	}
	
	
}

package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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

	
	private JTextField loopNumberField;
	private JTextField threadsNumberField;
	private JTextField proxyHostField;
	private JTextField proxyPortField;
	private JTextField proxyTestedPortField;
	 
	private JLabel proxyHostLabel;
	private JLabel proxyTestedPortLabel;
	private JLabel proxyPortLabel;
	private JLabel loopNumberLabel;
	private JLabel threadsNumberLabel;
	
	private JPanel httpRequestSettings;
	private JPanel proxySettings;
	private JPanel proxyLabels;
	private JPanel proxyFields;
	private JPanel httpLabels;
	private JPanel httpFields;
	
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
		httpLabels = new JPanel(new GridLayout(2, 1));
		httpFields = new JPanel(new GridLayout(2, 1));
		
		proxyHostField = new JTextField(20);
		proxyHostLabel = new JLabel("Hostname");
		
		proxyPortField = new JTextField(20);
		proxyPortLabel = new JLabel("Port");
		
		proxyTestedPortField = new JTextField(20);
		proxyTestedPortLabel = new JLabel("Tested Port");
		
		loopNumberField = new JTextField(20);
		loopNumberLabel = new JLabel("Requests number");
		
		threadsNumberField = new JTextField(20);
		threadsNumberLabel = new JLabel("Threads number");
		
		settingsData = new TestCaseSettingsData();
	}
	
	private void setupComponents(){
		
		this.setLayout(new BorderLayout());
				
		httpRequestSettings.setBorder(BorderFactory.createTitledBorder("Testing Unit Settings"));
		proxySettings.setBorder(BorderFactory.createTitledBorder("Proxy Unit Settings"));
		
		httpLabels.add(threadsNumberLabel);
		httpLabels.add(loopNumberLabel);
		httpFields.add(threadsNumberField);
		httpFields.add(loopNumberField);
		
		proxyLabels.add(proxyHostLabel);
		proxyLabels.add(proxyPortLabel);
		proxyLabels.add(proxyTestedPortLabel);
		proxyFields.add(proxyHostField);
		proxyFields.add(proxyPortField);
		proxyFields.add(proxyTestedPortField);
		
		httpRequestSettings.add(httpLabels);
		httpRequestSettings.add(httpFields);
		
		proxySettings.add(proxyLabels);
		proxySettings.add(proxyFields);
		
		add(httpRequestSettings,BorderLayout.NORTH);		
		add(proxySettings,BorderLayout.CENTER);		
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
	
	private void setEditorThreadsNumber(Integer port){
		this.threadsNumberField.setText(port.toString());
	}
	
	private Integer getEditorLoopCount(){
		return Integer.parseInt(loopNumberField.getText());
	}
	
	private void setEditorLoopNumber(Integer port){
		this.loopNumberField.setText(port.toString());
	}
	
	
	
	private void saveData(){
		this.settingsData.setName(MainWindow.getCasePath());
		this.settingsData.setThreadsNumber(getEditorThreadsNumber());
		this.settingsData.setLoopNumber(getEditorLoopCount());
		this.settingsData.setProxyTestedPort(getEditorProxyTestedPort());
		this.settingsData.setProxyPort(getEditorProxyPort());
		this.settingsData.setProxyHost(getEditorProxyHostName());
	}
	
	
	private void loadData(){
		setEditorProxyPort(this.settingsData.getProxyPort());
		setEditorProxyHostName(this.settingsData.getProxyHost());
		setEditorThreadsNumber(this.settingsData.getThreadsNumber());
		setEditorLoopNumber(this.settingsData.getLoopNumber());
		setEditorProxyTestedPort(this.settingsData.getProxyTestedPort());
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

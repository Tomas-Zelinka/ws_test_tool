package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private JTextField schedulePeriodField;
	private JTextField connectionTimeoutField;
	private JTextField requestTimeoutField;
	private JCheckBox runButton;
	private JCheckBox useProxyBox;
	private JCheckBox scheduledRunButton;
	 
	private JLabel proxyHostLabel;
	private JLabel proxyTestedPortLabel;
	private JLabel proxyPortLabel;
	private JLabel loopNumberLabel;
	private JLabel threadsNumberLabel;
	private JLabel runLabel;
	private JLabel httpSettingsLabel;
	private JLabel proxySettingsLabel;
	private JLabel useProxyLabel;
	private JLabel schedulePeriodLabel;
	private JLabel connectionTimeoutLabel;
	private JLabel requestTimeoutLabel;
	private JLabel scheduledRunLabel;
	
	private JPanel httpRequestSettings;
	private JPanel proxySettings;
	private JPanel proxyLabels;
	private JPanel proxyFields;
	private JPanel httpLabels;
	private JPanel httpFields;
	private JPanel httpFieldsBox;
	private JPanel proxyFieldBox;
	
	private JSeparator separator;
	private boolean dataLoaded;
	private TestCaseSettingsData settingsData;
	
	public TestCaseSettings(){
		
		initComponents();
		setupComponents();	
		setDataLoaded(false);
	}
	
	public void setData(TestCaseSettingsData data){
		
		this.settingsData = data;
		loadData();
		setDataLoaded(true);
	}
	
	public TestCaseSettingsData getData(){
		
		saveData();
		return this.settingsData;
	}
	
	public void setEnablePanel(boolean enable){
		
		setEnableProxyPanel(enable);
		setEnableHttpPanel(enable);
		useProxyBox.setEnabled(enable);
		runButton.setEnabled(enable);
	}
	
	public boolean isDataLoaded() {
			
		return dataLoaded;
	}

	public void setDataLoaded(boolean dataLoaded) {
		
		this.dataLoaded = dataLoaded;
	}
	
	public void clearData(){
		
		runButton.setSelected(false);
		loopNumberField.setText("");
		threadsNumberField.setText("");
		
		useProxyBox.setSelected(false);
		proxyHostField.setText("");
		proxyTestedPortField.setText("");
		proxyPortField.setText("");
	} 
		
	private void initComponents(){
		
		httpRequestSettings = new JPanel();
		proxySettings = new JPanel();
		proxyLabels = new JPanel(new GridLayout(8,1,0,5));
		proxyFields = new JPanel(new GridLayout(8,1,0,5));
		httpLabels = new JPanel(new GridLayout(8,1,0,5));
		httpFields = new JPanel(new GridLayout(8,1,0,5));
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
		
		schedulePeriodField = new JTextField(20);
		schedulePeriodLabel = new JLabel("Schedule Period");
		
		connectionTimeoutField = new JTextField(20);
		connectionTimeoutLabel = new JLabel("Connection Timeout");
		
		requestTimeoutField = new JTextField(20);
		requestTimeoutLabel = new JLabel("Request Timeout");
		
		runButton = new JCheckBox();
		runLabel = new JLabel("Run test case");
		
		useProxyBox = new JCheckBox();
		useProxyLabel = new JLabel("Use Proxy");
		
		scheduledRunButton = new JCheckBox();
		scheduledRunLabel = new JLabel("Periodic Run");
		
		settingsData = new TestCaseSettingsData();
	}
	
	private void setupComponents(){
		
		this.setLayout(new BorderLayout());
		
		httpRequestSettings.setLayout(new BorderLayout());
		proxySettings.setLayout(new BorderLayout());
		useProxyBox.addActionListener(new UseProxyListener());
		runButton.addActionListener(new UseHttpListener());
		
		httpLabels.add(runLabel);
		httpLabels.add(connectionTimeoutLabel);
		httpLabels.add(requestTimeoutLabel);
		httpLabels.add(threadsNumberLabel);
		httpLabels.add(loopNumberLabel);
		httpLabels.add(scheduledRunLabel);
		httpLabels.add(schedulePeriodLabel);
		
		
		httpFields.add(runButton);
		httpFields.add(connectionTimeoutField);
		httpFields.add(requestTimeoutField);
		httpFields.add(threadsNumberField);
		httpFields.add(loopNumberField);
		httpFields.add(scheduledRunButton);
		httpFields.add(schedulePeriodField);
		
		
		httpFields.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		
		proxyLabels.add(useProxyLabel);
		
		proxyLabels.add(proxyHostLabel);
		proxyLabels.add(proxyPortLabel);
		proxyLabels.add(proxyTestedPortLabel);
		
		proxyFields.add(useProxyBox);
		
		proxyFields.add(proxyHostField);
		proxyFields.add(proxyPortField);
		proxyFields.add(proxyTestedPortField);
		
		
		
		scheduledRunButton.addActionListener(new ScheduledRunListener());
		
		proxyFields.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		
		httpSettingsLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		httpSettingsLabel.setFont(new Font("Arial", Font.BOLD, 13));
		
		proxySettingsLabel.setFont(new Font("Arial", Font.BOLD, 13));
		proxySettingsLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		
		httpRequestSettings.add(httpLabels,BorderLayout.LINE_START);
		httpRequestSettings.add(httpFields,BorderLayout.CENTER);
		
		proxySettings.add(proxyLabels,BorderLayout.LINE_START);
		proxySettings.add(proxyFields,BorderLayout.CENTER);
		
		separator.setForeground(Color.gray);
	
		httpFieldsBox.setBorder(BorderFactory.createEmptyBorder(5,10,0,20));
		
		httpFieldsBox.add(httpSettingsLabel,BorderLayout.NORTH);
		httpFieldsBox.add(httpRequestSettings,BorderLayout.LINE_START);
		httpFieldsBox.add(separator,BorderLayout.SOUTH);
		 
		
		proxyFieldBox.setBorder(BorderFactory.createEmptyBorder(5,10,0,20));
		proxyFieldBox.add(proxySettingsLabel,BorderLayout.NORTH);
		proxyFieldBox.add(proxySettings,BorderLayout.LINE_START);
		
		JPanel box = new JPanel(new GridLayout(2, 1));
			
		box.add(httpFieldsBox);		
		box.add(proxyFieldBox);
		add(box,BorderLayout.NORTH);
		setEnableHttpPanel(false);
		setEnableProxyPanel(false);
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
	
	
	private boolean getEditorUseProxy(){
		
		return useProxyBox.isSelected();
	}
	
	private void setEditorUseProxy(boolean use){
		
		this.useProxyBox.setSelected(use);
	}
	
	private void setEditorTimeout(Integer time){
		
		this.connectionTimeoutField.setText(time.toString());
	}

	private Integer getEditorTimeout(){
		
		return Integer.parseInt(connectionTimeoutField.getText());
	}
	
	
	private void setEditorRequestTimeout(Integer time){
		
		this.requestTimeoutField.setText(time.toString());
	}

	private Integer getEditorRequestTimeout(){
		
		return Integer.parseInt(requestTimeoutField.getText());
	}
	
	private void setEditorPeriod(Integer period){
		
		this.schedulePeriodField.setText(period.toString());
	}

	private Integer getEditorPeriod(){
		
		return Integer.parseInt(schedulePeriodField.getText());
	}
	
	
	private boolean getEditorSequentialRun(){
		
		return this.scheduledRunButton.isSelected();
	}
	
	private void setEditorSequentialRun(boolean run){
		
		this.scheduledRunButton.setSelected(run);
	}
	
	
	private void saveData(){
		
		this.settingsData.setRun(getEditorRun());
		this.settingsData.setUseProxy(getEditorUseProxy());
		
		
		if(getEditorRun() ){
			this.settingsData.setUseSequentialRun(getEditorSequentialRun());
			if(getEditorSequentialRun()){
				
				this.settingsData.setPeriod(getEditorPeriod());
			}else{
				this.settingsData.setPeriod(0);
			}
			
			this.settingsData.setThreadsNumber(getEditorThreadsNumber());
			this.settingsData.setLoopNumber(getEditorLoopCount());
			this.settingsData.setTimeout(getEditorTimeout());
			this.settingsData.setRequestTimeout(getEditorRequestTimeout());
		}
		if(getEditorUseProxy()){
			
			this.settingsData.setProxyTestedPort(getEditorProxyTestedPort());
			this.settingsData.setProxyPort(getEditorProxyPort());
			this.settingsData.setProxyHost(getEditorProxyHostName());
		}
		
		
		
	}
	
	
	private void loadData(){
		
		setEditorSequentialRun(this.settingsData.isUseSequentialRun());
		
		setEnableHttpPanel(this.settingsData.getRun());
		setEnableProxyPanel(this.settingsData.getUseProxy());
		
		setEditorRun(this.settingsData.getRun());
		setEditorProxyPort(this.settingsData.getProxyPort());
		setEditorProxyHostName(this.settingsData.getProxyHost());
		setEditorThreadsNumber(this.settingsData.getThreadsNumber());
		setEditorLoopNumber(this.settingsData.getLoopNumber());
		setEditorProxyTestedPort(this.settingsData.getProxyTestedPort());
		setEditorUseProxy(this.settingsData.getUseProxy());
		setEditorPeriod(this.settingsData.getPeriod());
		setEditorTimeout(this.settingsData.getTimeout());
		setEditorRequestTimeout(this.settingsData.getRequestTimeout());
	}
	
	
	
	private void setEnableProxyPanel(boolean use){
		
		Component[] fields = proxyFields.getComponents(); 
        Component[] labels = proxyLabels.getComponents();
		
		for (int a = 1; a < fields.length; a++) {  
			fields[a].setEnabled(use);
   	     	labels[a].setEnabled(use);
		} 
		
	}
	
	
	private void setEnableHttpPanel(boolean use){
		
		Component[] fields = httpFields.getComponents(); 
        Component[] labels = httpLabels.getComponents();
		
		for (int a = 1; a < fields.length; a++) {  
			
			if(a == 5){
				if(use && scheduledRunButton.isSelected() ){
					fields[a].setEnabled(true);
	   	     		labels[a].setEnabled(true);
	   	     		
				}else{
					fields[a].setEnabled(false);
	   	     		labels[a].setEnabled(false);
				}
				continue;
			}
					
			fields[a].setEnabled(use);
     		labels[a].setEnabled(use);
		} 
		
	}
	
	
	
	private class UseProxyListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			AbstractButton abstractButton = (AbstractButton)e.getSource();
	        boolean selected = abstractButton.getModel().isSelected();
	        if(selected){
	        	setEnableProxyPanel(true);
	        }else{
	        	setEnableProxyPanel(false);
	        }
		}
	}
	
	private class UseHttpListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			AbstractButton abstractButton = (AbstractButton)e.getSource();
	        boolean selected = abstractButton.getModel().isSelected();
	        if(selected){
	        	setEnableHttpPanel(true);
	        }else{
	        	setEnableHttpPanel(false);
	        }
		}
	}
	
	
	
	private class ScheduledRunListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			JCheckBox button = (JCheckBox)e.getSource();
	        schedulePeriodField.setEnabled(button.isSelected());
	       
		}
	}
	
	
}

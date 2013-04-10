package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import logging.ConsoleLog;

public class TestingMonitor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6401376001409493032L;

	
	private JTabbedPane tabbedPane;
	
	private int machineCounter;
	public TestingMonitor(){
		
		machineCounter = 0;
		initComponents();
		setupComponents();
		
	}
	
	private void initComponents(){
		
		
		tabbedPane = new JTabbedPane();
		
		this.setLayout(new BorderLayout());
		this.add(tabbedPane,BorderLayout.CENTER);
		
		
	}
	
	private void setupComponents(){
		addMachine();
	}
	
	
	public void exportConfiguration(){
		
	}
	
	public void addMachine(){
		
		MachinePanel panel = new MachinePanel(machineCounter);
		
		if(machineCounter == 0)
			tabbedPane.addTab("Local Unit",panel);
		else
			tabbedPane.addTab("Remote Unit "+ machineCounter,panel);
		machineCounter++;
			
		
	}
	
	public void removeMachine(){
		
		int panelIndex = tabbedPane.getSelectedIndex();
		
		if (panelIndex != 0){
			tabbedPane.remove(panelIndex);
			tabbedPane.revalidate();
		}else{
			ConsoleLog.Print("You cannot close local testing unit");
		}
	
	}
	
	
	
	
	
}

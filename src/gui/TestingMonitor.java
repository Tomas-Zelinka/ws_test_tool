package gui;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class TestingMonitor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6401376001409493032L;

	
	private JTable testCases;
	private JTable responses;
	private JSplitPane verticalSplit;
	private JPanel topPanel;
	
	private JSplitPane outputDetails;
	private JPanel requestPanel;
	private JPanel responsePanel;
	private JScrollPane requestScrollPane;
	private JScrollPane responseScrollPane;
	private JLabel 		responseLabel;
	private JLabel 		requestLabel;
	private JEditorPane requestEditorPane;
	private JEditorPane responseEditorPane;
	
	
	public TestingMonitor(){
		
		initComponents();
		setupComponents();
	}
	
	
	private void initComponents(){
		
		testCases = new JTable();
		responses = new JTable();
		outputDetails = new JSplitPane();
		verticalSplit = new JSplitPane();
		topPanel = new JPanel();
		requestPanel = new JPanel();
		responsePanel = new JPanel();
		requestScrollPane = new JScrollPane();
		responseScrollPane = new JScrollPane();
		responseLabel = new JLabel();
		requestLabel = new JLabel();
		requestEditorPane = new JEditorPane();
		responseEditorPane = new JEditorPane();
		
		
	}
	
	private void setupComponents(){
		
		this.setLayout(new BorderLayout());
		
		topPanel.setLayout(new BorderLayout());
		topPanel.add(testCases,BorderLayout.NORTH);
		topPanel.add(responses,BorderLayout.CENTER);
		this.add(topPanel,BorderLayout.NORTH);
		this.add(verticalSplit, BorderLayout.CENTER);
		verticalSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
		verticalSplit.setTopComponent(topPanel);
		verticalSplit.setBottomComponent(outputDetails);
		
		outputDetails.setDividerLocation(430);

			requestLabel.setText("Response");
	        requestEditorPane.setEditable(false);
	        requestScrollPane.setViewportView(requestEditorPane);
	        //MainWindow.initEditorPane(reqlEditorPane, reqOriginalScrollPane);

	        javax.swing.GroupLayout requestPanelLayout = new javax.swing.GroupLayout(requestPanel);
	        requestPanel.setLayout(requestPanelLayout);
	        requestPanelLayout.setHorizontalGroup(
	            requestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(requestPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(requestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(requestScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
	                    .addComponent(requestLabel))
	                .addContainerGap())
	        );
	        requestPanelLayout.setVerticalGroup(
	            requestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(requestPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(requestLabel)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(requestScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
	                .addContainerGap())
	        );

	        outputDetails.setLeftComponent(requestPanel);

	        
	        responseLabel.setText("Request");
	        responseEditorPane.setEditable(false);
	        responseScrollPane.setViewportView(responseEditorPane);
	        //MainWindow.initEditorPane(reqChangedEditorPane, reqChangedScrollPane);

	        javax.swing.GroupLayout responsePanelLayout = new javax.swing.GroupLayout(responsePanel);
	        responsePanel.setLayout(responsePanelLayout);
	        responsePanelLayout.setHorizontalGroup(
	            responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(responsePanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(responseScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
	                    .addComponent(responseLabel))
	                .addContainerGap())
	        );
	        responsePanelLayout.setVerticalGroup(
	            responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(responsePanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(responseLabel)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(responseScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
	                .addContainerGap())
	        );

	        outputDetails.setRightComponent(responsePanel);

	       
	        
	        

	        //resOriginalEditorPane.setEditable(false);
	       // resOriginalScrollPane.setViewportView(resOriginalEditorPane);
	       // MainWindow.initEditorPane(resOriginalEditorPane, resOriginalScrollPane);

	       
	}
	
	
	
}

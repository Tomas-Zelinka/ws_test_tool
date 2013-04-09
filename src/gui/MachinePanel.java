package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import testingUnit.LocalTestUnit;
import testingUnit.RemoteTestUnit;
import testingUnit.TestingUnit;

public class MachinePanel extends JPanel {

	
	private JTable testCases;
	private JTable responses;
	private JPanel outputDetailPanel;
	private JPanel panel;
	private JPanel topPanel;
	private JPanel centerPanel;
	
	private JSplitPane outputDetails;
	private JPanel requestPanel;
	private JPanel responsePanel;
	private JScrollPane requestScrollPane;
	private JScrollPane responseScrollPane;
	private JScrollPane testCasesTableScrollPane;
	private JScrollPane responsesTableScrollPane;
	private JLabel 		responseLabel;
	private JLabel 		requestLabel;
	private JEditorPane requestEditorPane;
	private JEditorPane responseEditorPane;
	private TestingUnit testUnit;
	
	public MachinePanel (int type){
		initComponents();
		setupComponents();
		
		
		if(type == 0){
			testUnit = new LocalTestUnit();
		}else{
			testUnit = new RemoteTestUnit();
		}
		
		
	}

	private void initComponents(){
		testCases = new JTable();
		responses = new JTable();
		outputDetails = new JSplitPane();
		outputDetailPanel = new JPanel();
		topPanel = new JPanel();
		centerPanel = new JPanel();
		panel = new JPanel();
		requestPanel = new JPanel();
		responsePanel = new JPanel();
		requestScrollPane = new JScrollPane();
		responseScrollPane = new JScrollPane();
		testCasesTableScrollPane = new JScrollPane();
		responsesTableScrollPane = new JScrollPane();
		responseLabel = new JLabel();
		requestLabel = new JLabel();
		requestEditorPane = new JEditorPane();
		responseEditorPane = new JEditorPane();
	}
	
	
	private void setupComponents(){
		this.setLayout(new BorderLayout());
		
		panel.setLayout(new BorderLayout());
		panel.add(topPanel,BorderLayout.PAGE_START);
		panel.add(centerPanel,BorderLayout.CENTER);
		this.add(panel,BorderLayout.PAGE_START);
		this.add(outputDetailPanel, BorderLayout.CENTER);
		
		outputDetailPanel.setLayout(new BorderLayout());
		outputDetailPanel.add(outputDetails,BorderLayout.CENTER);
		
		
		topPanel.add(testCasesTableScrollPane,BorderLayout.PAGE_START);
		centerPanel.add(responsesTableScrollPane,BorderLayout.CENTER);
		
		testCasesTableScrollPane.getViewport().add(testCases);
		responsesTableScrollPane.getViewport().add(responses);
		
		outputDetails.setDividerLocation(430);
		
		testCases.setOpaque(false);
		responses.setOpaque(false);
		testCases.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {{},{},{},{}

	            },
	            new String [] {
	                "Type", "Detail"
	            }
	        ) {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 3376163326142594714L;
				boolean[] canEdit = new boolean [] {
	                false, false
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });
		
		
		responses.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {{},{},{},{}

	            },
	            new String [] {
	            		"Type", "Detail"
	            }
	        ) {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 3376163326142594714L;
				boolean[] canEdit = new boolean [] {
	                false, false
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });
		
		
		javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
		topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(testCasesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addGroup(topPanelLayout.createSequentialGroup()
                   .addContainerGap())))
        );
            
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testCasesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addContainerGap())
        );
		
		
		
		
        javax.swing.GroupLayout centerPanelLayout = new javax.swing.GroupLayout(centerPanel);
		centerPanel.setLayout(centerPanelLayout);
        centerPanelLayout.setHorizontalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(responsesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addGroup(topPanelLayout.createSequentialGroup()
                   .addContainerGap())))
        );
            
        centerPanelLayout.setVerticalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(responsesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE,100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addContainerGap())
        );
		
		
		requestLabel.setText("Response");
        requestEditorPane.setEditable(false);
        requestScrollPane.setViewportView(requestEditorPane);
        MainWindow.initEditorPane(requestEditorPane, requestScrollPane);

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
                .addComponent(requestScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        outputDetails.setLeftComponent(requestPanel);

        
        responseLabel.setText("Request");
        responseEditorPane.setEditable(false);
        responseScrollPane.setViewportView(responseEditorPane);
        MainWindow.initEditorPane(responseEditorPane, responseScrollPane);

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
                .addComponent(responseScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        outputDetails.setRightComponent(responsePanel);
	}
}

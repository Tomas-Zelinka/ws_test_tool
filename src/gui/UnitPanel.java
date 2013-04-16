package gui;

import java.awt.BorderLayout;
import java.io.File;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import logging.ConsoleLog;
import testingUnit.TestingUnit;
import data.DataProvider;
import data.TestCaseSettingsData;
import data.TestList;

public class UnitPanel extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5352882010763584802L;
	
	private static final String settingsPath = "";
	private static final String HttpPath = "";
	
	
	private JTable testCasesTable;
	private JTable responsesTable;
	private DefaultTableModel testCasesTableModel;
	private DefaultTableModel responsesTableModel;
	
	private JPanel outputDetailPanel;
	private JPanel panel;
	private JPanel topPanel;
	private JPanel centerPanel;
	
	private JPanel requestPanel;
	private JPanel responsePanel;
	private JSplitPane outputDetails;
	private JScrollPane requestScrollPane;
	private JScrollPane responseScrollPane;
	private JScrollPane testCasesTableScrollPane;
	private JScrollPane responsesTableScrollPane;
	private JLabel 		responseLabel;
	private JLabel 		requestLabel;
	private JEditorPane requestEditorPane;
	private JEditorPane responseEditorPane;
	
	private TestingUnit testUnit;
	private DataProvider ioProvider;
	
	private TestList testListData;
	private int unitType;
	
	public UnitPanel (int type){
		this.unitType = type;
		initComponents();
		setupComponents();
	}
	
	 /**
	 * 
	 * @param path
	 */
	public void openTestList(String path){
		
		clearTable();
		File testListFile= new File(path);
		if(testListFile.exists()){
			testListData =(TestList) ioProvider.readObject(path);
			HashMap<Integer,String> testCases = testListData.getTestCases();
					
			if(!testCases.isEmpty()){
				for(Integer testCaseId : testCases.keySet()){
					putTestCaseToTable(testCaseId);
				}
			}
			
			ConsoleLog.Print("Opened Test List: "+path);
		}else{
			ConsoleLog.Print("Test list not found: "+path);
		}
		
		
	}
	
	/**
	 * 
	 * @param path
	 */
	public void saveTestList(String path){
		
		ioProvider.writeObject(path, this.testListData);
	}
	
	/**
	 * 
	 * @param casePath
	 */
	private void putTestCaseToTable(Integer id){
		
		String casePath = this.testListData.getTestCases().get(id);
		TestCaseSettingsData testCaseSettings = (TestCaseSettingsData) ioProvider.readObject(casePath);
		
		if(testCaseSettings == null){
			ConsoleLog.Message("Test case file from list not found.");
		}else{
			Object[] newRow = new Object[] {id,testCaseSettings.getName(),testCaseSettings.getThreadsNumber(),testCaseSettings.getLoopNumber(),new Boolean(testCaseSettings.getRun())};
			testCasesTableModel.insertRow(testCasesTable.getRowCount(), newRow);
		}
	}
	
	
	public void insertTestCaseToTable(String casePath)
	{
		TestCaseSettingsData testCaseSettings = (TestCaseSettingsData) ioProvider.readObject(casePath);
				
		if(detectDuplicityInTable(testCaseSettings.getName())){
			ConsoleLog.Message("Test case already in test list");
		}else{
			this.testListData.addTestCase(casePath);
			Object[] newRow = new Object[] {this.testListData.getLastId(),testCaseSettings.getName(),testCaseSettings.getThreadsNumber(),testCaseSettings.getLoopNumber(),new Boolean(testCaseSettings.getRun())};
			testCasesTableModel.insertRow(testCasesTable.getRowCount(), newRow);
		}
	}
	/**
	 * 
	 */
	public void removeSelectedTestCase(){
		
		int row = testCasesTable.getSelectedRow();
		ConsoleLog.Print("row: "+row);
		
		if(row >= 0){
			Integer id = (Integer)testCasesTableModel.getValueAt(row, 0);
			this.testListData.removeTestCase(id);
			testCasesTableModel.removeRow(row);
		}
	}
	
	public void removeTestCaseAt(int row){
		if(row >= 0){
			Integer id = (Integer)testCasesTableModel.getValueAt(row, 0);
			this.testListData.removeTestCase(id);
			testCasesTableModel.removeRow(row);
		}
	}
	/**
	 * 
	 */
	private boolean detectDuplicityInTable(String caseName){
		String caseNameFromTable = "";
		int rowNumber = testCasesTable.getRowCount();
		
		for (int i = 0; i < rowNumber;i++){
			caseNameFromTable = (String) testCasesTableModel.getValueAt(i,1);
			
			if(caseNameFromTable.compareTo(caseName) == 0)
				return true;
		}
		return false;
	}
	
	
	public void setTestUnit(TestingUnit unit){
		this.testUnit = unit;
	}
	
	private void clearTable(){
		
		ConsoleLog.Print(""+testCasesTableModel.getRowCount());
		for(int i = 0; i < testCasesTableModel.getRowCount(); i++){
			removeTestCaseAt(i);
		}
			
	}
	//public void cle
	
	/**
	 * 
	 */
	private void initComponents(){
		testCasesTable = new JTable();
		responsesTable = new JTable();
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
		ioProvider = new DataProvider();
	}
	
	/**
	 * 
	 */
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
		
		testCasesTableScrollPane.getViewport().add(testCasesTable);
		responsesTableScrollPane.getViewport().add(responsesTable);
		
		outputDetails.setDividerLocation(430);
		
		testCasesTable.setOpaque(false);
		responsesTable.setOpaque(false);
		testCasesTable.setModel(new javax.swing.table.DefaultTableModel(
	            
				
				new Object [][] {

	            },
	            new Object [] {
	                "#", "Name","Threads","Count","Run"
	            }
	        ) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 2602176893989437449L;

				public Class<?> getColumnClass(int index){
					
					if(index == 4)
						return Boolean.class;
					
					return getValueAt(0, index).getClass();
				}
				
				boolean[] canEdit = new boolean [] {
	                false, false,false,false,false
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
			
		}	);
		
		testCasesTableModel = (DefaultTableModel) testCasesTable.getModel();
		responsesTable.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		 "#", "Http Code","Method","URI"
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
		responsesTableModel = (DefaultTableModel) responsesTable.getModel();
		
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
		
		
		requestLabel.setText("Request");
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

        
        responseLabel.setText("Response");
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

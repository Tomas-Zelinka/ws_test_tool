package cz.vutbr.fit.dp.xzelin15.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONException;
import org.json.JSONObject;

import cz.vutbr.fit.dp.xzelin15.data.DataProvider;
import cz.vutbr.fit.dp.xzelin15.data.HttpMessageData;
import cz.vutbr.fit.dp.xzelin15.data.TestCaseSettingsData;
import cz.vutbr.fit.dp.xzelin15.data.XMLFormat;
import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;
import cz.vutbr.fit.dp.xzelin15.testingUnit.NewResponseListener;

/**
 * Head component of testing, it show test list and results of testing
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class TestUnitPanel extends JPanel implements NewResponseListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5352882010763584802L;
	
	private JTable testCasesTable;
	private JTable responsesTable;
	private DefaultTableModel testCasesTableModel;
	private DefaultTableModel responsesTableModel;
	
	private JPanel requestPanel;
	private JPanel responsePanel;
	private JPanel outputDetailPanel;
	private JPanel panel;
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel expectedResponsePanel;
	private JScrollPane expectedResponseScrollPane;
	private JEditorPane expectedEditorPane;
	private JTabbedPane responseTabbedPane;
	private JSplitPane outputDetails;
	private JScrollPane requestScrollPane;
	private JScrollPane responseScrollPane;
	private JScrollPane testCasesTableScrollPane;
	private JScrollPane responsesTableScrollPane;
	private JLabel requestTableLabel;
	private JLabel responsesTableLabel;
	private JLabel 		responseLabel;
	private JLabel 		requestLabel;
	private JLabel		expectedResponseLabel;
	private JEditorPane requestEditorPane;
	private JEditorPane responseEditorPane;
	
	/**
	 * Test cases settings storage - data from this storage are shown like test list 
	 */
	private ArrayList<TestCaseSettingsData> testListData;
	
	/**
	 * Mapping test case names to rows number - this is used when the test case is finished
	 * and the row has to be updated to green color
	 */
	private HashMap<String,Integer> testNumbers;
	
	/**
	 * Storage for received responses
	 */
	private HashMap<String,HttpMessageData[]> responses;
	
	/**
	 * First test in test list - for first case are the data received in realtime
	 * so we have to know the name
	 */
	private String firstTest;
	
	/**
	 * With data is received period number and in this variable is stored the last one 
	 */
	private Integer periodNumber;
	
		
	public TestUnitPanel (){
		initComponents();
		setupComponents();
		
	}
	
	 /**
	 *  Load test case settings data to first table
	 * @param path
	 */
	public void openTestList(ArrayList<TestCaseSettingsData> dataArray){
		
		clearTable(testCasesTableModel);
		this.testListData = dataArray;
		for(TestCaseSettingsData data : dataArray){
			loadTestCaseToTable(data);
		}
		
		ConsoleLog.Print("[UnitPanel] Opened Test List");
		
	}
	
	/**
	 * Put one test case to table
	 * @param data - settings data of one test case
	 */
	private void loadTestCaseToTable(TestCaseSettingsData data){
				
		if(data == null){
			ConsoleLog.Message("Test case file from list not found.");
		}else{
			insertToTable(data);
		}
	}
	
	/**
	 * Insert new test case in test list
	 * @param data - setting data of one test case
	 */
	public void insertTestCaseToTable(TestCaseSettingsData data){
		
		
		if(this.testListData != null){
			if(detectDuplicityInTable(data.getName())){
				ConsoleLog.Message("Test case already in test list");
			}else{
				
				this.testListData.add(data);
				insertToTable(data);
			}
		}else{
			ConsoleLog.Message("Any opened test suite");
		}
	}

	/**
	 * This method is called by listener. By this method the response data are received
	 * @param dataArray - responses array from one thread 
	 * @param period - period id, when the periodic mode is used
	 */
	
	@Override
	public void onNewResponseEvent(HttpMessageData[] dataArray, int period) {
		
		this.periodNumber = period;
		
		//get info from first record - these kind of information are unchanged 
		//in whole array
		String name = dataArray[0].getName();
		Integer responseThreadNumber = dataArray[0].getThreadNumber();
		String  adrress = name + responseThreadNumber + period ;
		ConsoleLog.Print("[TESTPANEL] vkladam: " +adrress);
		responses.put(adrress, dataArray);
		
		//get row number from case-name->row nubmer mapping
		int caseRow = this.testNumbers.get(name);
		int requestThreadNumber =(Integer)testCasesTableModel.getValueAt(caseRow,2); 
		
		//update the finish flag of test case
		if (requestThreadNumber ==  responseThreadNumber+1){
			testCasesTableModel.setValueAt(true,caseRow,6);
		}
		
		//show results of first test case immediately when the are received
		if(firstTest.compareTo(name)== 0){
			testCasesTable.setRowSelectionInterval(0, 0);
			boolean test = false;
			for (int i = 0; i < dataArray.length; i++){
				
				//from nanoseconds to milliseconds
				float time = dataArray[i].getElapsedRemoteTime()/1000000;
				
				//comparsion of response body with expected result
				test = compareResponses(dataArray[i].getResponseBody(),dataArray[i].getExpectedBody(),dataArray[i].getContentType());
				
				Object[] newRow = new Object[] {dataArray[i].getName(),dataArray[i].getResponseCode(),dataArray[i].getMethod(),dataArray[i].getResource(),time,dataArray[i].getLoopNumber(),dataArray[i].getThreadNumber(),period,test};
				responsesTableModel.insertRow(responsesTable.getRowCount(), newRow);
			}
		}
	}
	
	/**
	 * Clear test results
	 */
	public void clearResults(){
		if(this.testListData != null){
			ConsoleLog.Print("[UnitPanel] clearing");
			responses.clear();
			clearTable(responsesTableModel);
			responseEditorPane.setText("");
			requestEditorPane.setText("");
			if(testCasesTableModel.getRowCount() != 0){
				firstTest =(String) testCasesTableModel.getValueAt(0,1);
				
				//clear the finish flag
				for(int i =0 ;i <testCasesTableModel.getRowCount();i++ ){
					testCasesTableModel.setValueAt(false, i, 6);
				}
				
			}
			this.periodNumber = 0;
		}else{
			ConsoleLog.Message("Any opened test suite");
		}
	}
	
	/**
	 * Clear the test cases table
	 */
	public void clearTestList(){
		clearTable(testCasesTableModel);
		testCasesTableModel.fireTableDataChanged();
		this.testListData = null;
	}
	
	/**
	 * Return list of test cases data
	 * 
	 * @return list of test case data
	 */
	public ArrayList<TestCaseSettingsData> getTestListData(){
		
		if(this.testListData != null ){
		
			ConsoleLog.Print("[UnitPanel] Testlist size: "+ testListData.size());
			
			for(int id = 0; id <testCasesTableModel.getRowCount(); id++){
			
				Integer threadsNumberRow = (Integer)testCasesTableModel.getValueAt(id,2);
				Integer loopsCountRow =  (Integer)testCasesTableModel.getValueAt(id,3);
				Boolean runTestRow =  (Boolean)testCasesTableModel.getValueAt(id,4);
				
				TestCaseSettingsData testCaseSettings = testListData.remove(id);
				testCaseSettings.setThreadsNumber(threadsNumberRow);
				testCaseSettings.setLoopNumber(loopsCountRow);
				testCaseSettings.setRun(runTestRow);
				testListData.add(id, testCaseSettings);
			}
				
			
		}else{
			ConsoleLog.Message("Any opened test suite");
		}
		
		return this.testListData;
	}
	
	
	
	
	
	/**
	 * Remove the selected test case from table
	 */
	public void removeSelectedTestCase(){
		
		int row = testCasesTable.getSelectedRow();
		ConsoleLog.Print("[UnitPanel] row: "+row);
		
		if(row >= 0){
			this.testListData.remove(row);
			testCasesTableModel.removeRow(row);
			
			for(int i =0; i < testCasesTable.getRowCount(); i++){
				testCasesTableModel.setValueAt((Integer)i, i,0);
			}
			
		}
	}

	/**
	 * Function for clearing tables
	 */
	private void clearTable(DefaultTableModel table){
		table.setRowCount(0);
	}
	
	/**
	 * Insert the data in the test cases table
	 * @param data
	 */
	private void insertToTable(TestCaseSettingsData data){
		int rowCount = testCasesTable.getRowCount();
		Object[] newRow = new Object[] {rowCount,data.getName(),data.getThreadsNumber(),data.getLoopNumber(),new Boolean(data.getRun()),new Boolean(data.getUseProxy()),false};
		testCasesTableModel.insertRow(rowCount, newRow);
		this.testNumbers.put(data.getName(),rowCount);
	}
	
	/**
	 * Detection of duplicity in test cases table - in testlist
	 * cannot be test case twice
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
	
	
	
	/**
	 * 
	 * Click listener for test cases table
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class RequestSelectionAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			
			//clearing
			clearTable(responsesTableModel);
			responseEditorPane.setText("");
			requestEditorPane.setText("");
			expectedEditorPane.setText("");
			
			//initialization
			int row = testCasesTable.getSelectedRow();
			String name = (String) testCasesTableModel.getValueAt(row,1);
			Integer threadNumber = (Integer) testCasesTableModel.getValueAt(row,2);
			boolean test = false;
			
			ConsoleLog.Print("[UnitPanel] case to find in table:" + responses.keySet().size());
			if(!firstTest.isEmpty()){
				
				// first iterate over number of periods 
				for(int a = 0; a <= periodNumber; a++){
					
					//the over thread numbers
					for(int i = 0; i < threadNumber; i++){
						String key = name + i + a ;
						HttpMessageData[] caseResponses = responses.get(key);
							
						if(caseResponses != null){
							
							//sequence of responses from one thread
							for(HttpMessageData data : caseResponses){
								test = compareResponses(data.getResponseBody(),data.getExpectedBody(), data.getContentType());
													
								float time = data.getElapsedRemoteTime()/1000000; 
								Object[] newRow = new Object[] {data.getName(),data.getResponseCode(),data.getMethod(),data.getResource(), time ,data.getLoopNumber(),data.getThreadNumber(),a,test};
								responsesTableModel.insertRow(responsesTable.getRowCount(), newRow);
							}
						}else{
							ConsoleLog.Message("Response not present!");
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * Click listener for one response - show content of response
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class ResponseSelectionAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = responsesTable.getSelectedRow();
			
			String name = (String) responsesTableModel.getValueAt(row,0);
			Integer threadNumber = (Integer) responsesTableModel.getValueAt(row,6);
			Integer loopNumber = (Integer) responsesTableModel.getValueAt(row,5);
			Integer period = (Integer) responsesTableModel.getValueAt(row,7);
			String key = name + threadNumber + period;
			HttpMessageData caseResponse =(responses.get(key))[loopNumber];
			setEditorContent(requestEditorPane, caseResponse.getContentType(), caseResponse.getRequestBody());
			setEditorContent(responseEditorPane, caseResponse.getContentType(), caseResponse.getResponseBody());
			setEditorContent(expectedEditorPane, caseResponse.getContentType(), caseResponse.getExpectedBody());
			
		}
	}
	
	/**
	 * 
	 * Detection of received data format
	 * 
	 * @param editor - text are where the content is shown
	 * @param contentType - content type of the response
	 * @param content - content of response
	 */
	private void setEditorContent(JEditorPane editor,String contentType,String content){
		ConsoleLog.Print(contentType);
		
		if(content.isEmpty()){
			
			editor.setText("Body is empty");
		}else if(contentType.contains("xml")){
			
			editor.setText(XMLFormat.format(content));
		}else if(contentType.contains("json") || contentType.contains("javascript")){
			JSONObject json;
			
			try {
				json = new JSONObject(content);
				if(json != null)
					editor.setText(json.toString(2));
			} catch (JSONException e) {
					
			}
			
		}else{
			
			editor.setText(content);
		}
	}
	
	/**
	 * 
	 * Comparsion of received response and expected one
	 * 
	 * @param output
	 * @param expected
	 * @param contentType
	 * @return
	 */
	private boolean compareResponses(String output, String expected, String contentType){
		
		//XML data 
		if(contentType.contains("xml") && !output.isEmpty() && !expected.isEmpty()){
			String one = XMLFormat.format(output);
			String two = XMLFormat.format(expected);
			
			return one.contentEquals(two);
		}
		
		if(contentType.contains("json") || contentType.contains("javascript")){
		
			try {
				 	JSONObject json1 = new JSONObject(output);
				 	JSONObject json2 = new JSONObject(expected);
				 
				 	if(json1 != null && json2 !=null)
					 return json1.toString().contentEquals(json2.toString());
				 
			 } catch (JSONException e) {}
		 }
		
		return false;
	}
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
		expectedResponsePanel = new JPanel();
		requestScrollPane = new JScrollPane();
		responseScrollPane = new JScrollPane();
		expectedResponseScrollPane = new JScrollPane();
		testCasesTableScrollPane = new JScrollPane();
		responsesTableScrollPane = new JScrollPane();
		responseLabel = new JLabel();
		requestLabel = new JLabel();
		requestEditorPane = new JEditorPane();
		responseEditorPane = new JEditorPane();
		expectedEditorPane = new JEditorPane();
		requestTableLabel = new JLabel("Requests");
		responsesTableLabel = new JLabel("Responses");
		expectedResponseLabel = new JLabel();
		responses = new HashMap<String,HttpMessageData[]>();
		firstTest = "";
		responseTabbedPane = new JTabbedPane();
		testNumbers = new HashMap<String,Integer>();
	}
	
	/**
	 * 
	 * Column formating
	 * 
	 * @param table
	 * @param alignment
	 * @param column
	 */
	private void setColumnAligment(JTable table,int alignment, int column){
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( alignment );
		table.getColumnModel().getColumn(column).setCellRenderer( centerRenderer );
		
	}
	
	/**
	 * Component initialization
	 */
	private void setupComponents(){
		this.setLayout(new BorderLayout());
		
		panel.setLayout(new BorderLayout());
		panel.add(topPanel,BorderLayout.PAGE_START);
		panel.add(centerPanel,BorderLayout.CENTER);
		this.add(panel,BorderLayout.PAGE_START);
		this.add(outputDetailPanel, BorderLayout.CENTER);
		
		outputDetailPanel.setLayout(new BorderLayout());
		outputDetailPanel.add( responseTabbedPane,BorderLayout.CENTER);
		
		
		topPanel.add(testCasesTableScrollPane,BorderLayout.PAGE_START);
		centerPanel.add(responsesTableScrollPane,BorderLayout.CENTER);
		
		testCasesTableScrollPane.getViewport().add(testCasesTable);
		responsesTableScrollPane.getViewport().add(responsesTable);
		
		outputDetails.setDividerLocation(430);
		
		testCasesTable.setOpaque(false);
		responsesTable.setOpaque(false);
		testCasesTable.addMouseListener(new RequestSelectionAdapter());
		responsesTable.addMouseListener(new ResponseSelectionAdapter());
		
		
		
		
		
		testCasesTable.setModel(new javax.swing.table.DefaultTableModel(
	            
				
				new Object [][] {

	            },
	            new Object [] {
	                "#", "Name","Threads","Count","Http Unit","Proxy Unit","Finished"
	            }
	        ) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 2602176893989437449L;

				public Class<?> getColumnClass(int index){
					
					if(index == 4 || index == 5)
						return Boolean.class;
					
					return getValueAt(0, index).getClass();
				}
				
				boolean[] canEdit = new boolean [] {
	                false, false,true,true,true,false,false
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
			
		}	);
		
		testCasesTableModel = (DefaultTableModel) testCasesTable.getModel();
		
		TableColumn column = testCasesTable.getColumnModel().getColumn(6);
		column.setCellRenderer(new IconCellRenderer());
		
		for(int i = 0; i < testCasesTable.getColumnCount()-3; i++)
			setColumnAligment(testCasesTable,JLabel.CENTER, i);
		
		
		
		responsesTable.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {
	            		
	            },
	            new String [] {
	            		 "Name","Http Code", "Method","Service","Elapsed Time" ,"Loop number","Thread number","Period","Success" 
	            }
	        ) {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 3376163326142594714L;
				boolean[] canEdit = new boolean [] {
	                false,false, false,false, false,false, false,false,false
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });
		for(int i = 0; i < responsesTable.getColumnCount()-1; i++)
			setColumnAligment(responsesTable,JLabel.CENTER, i);
		
		
		
		responsesTableModel = (DefaultTableModel) responsesTable.getModel();
		TableColumn columnResponses = responsesTable.getColumnModel().getColumn(8);
		columnResponses.setCellRenderer(new IconCellRenderer());
		javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
		topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
            	 .addContainerGap()
            	  .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(testCasesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addComponent(requestTableLabel))
                 .addContainerGap())
        );
            
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
               
                 .addComponent(requestTableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testCasesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
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
                    .addComponent(responsesTableLabel))
                   .addContainerGap())
        );
            
        centerPanelLayout.setVerticalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerPanelLayout.createSequentialGroup()
                
                .addComponent(responsesTableLabel)
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

        outputDetails.setLeftComponent(responsePanel);
        outputDetails.setRightComponent(expectedResponsePanel);
        
        
        
        expectedResponseLabel.setText("Expected Response");
        expectedEditorPane.setEditable(false);
        expectedResponseScrollPane.setViewportView(expectedEditorPane);
        MainWindow.initEditorPane(expectedEditorPane, expectedResponseScrollPane);

        javax.swing.GroupLayout expectedResponsePanelLayout = new javax.swing.GroupLayout(expectedResponsePanel);
        expectedResponsePanel.setLayout(expectedResponsePanelLayout);
        expectedResponsePanelLayout.setHorizontalGroup(
            expectedResponsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expectedResponsePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(expectedResponsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(expectedResponseScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addComponent(expectedResponseLabel))
                .addContainerGap())
        );
        expectedResponsePanelLayout.setVerticalGroup(
            expectedResponsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expectedResponsePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(expectedResponseLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expectedResponseScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );
       
        
        
        responseTabbedPane.addTab("Request",requestPanel);
        responseTabbedPane.addTab("Response",outputDetails);
        responseTabbedPane.setSelectedComponent(outputDetails);
        
	}
	
	
	/**
	 * 
	 * Renderer of the finish flag cell
	 * 
	 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
	 *
	 */
	private class IconCellRenderer extends JLabel implements TableCellRenderer{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4723842849563229455L;

		public Component getTableCellRendererComponent(JTable table, Object status, boolean isSelected, boolean hasFocus, int row, int column) {
			
			
			this.setBackground(Color.white);
			this.setForeground(Color.white);
			setHorizontalAlignment( JLabel.CENTER );
			this.setOpaque(true);
			
			if((Boolean)status)
				setIcon(new ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"case.png")));
			else
				setIcon(new ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"false.png")));
			
			return this;
		}
	}
	
}

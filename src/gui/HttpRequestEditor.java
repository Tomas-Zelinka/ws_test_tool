package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import logging.ConsoleLog;
import data.DataProvider;
import data.HttpMessageData;
 
public class HttpRequestEditor extends JPanel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4286324537862142268L;
	
	
	
    private  JTable headersTable;
    
    private JLabel headersLabel;
    private JLabel contentLabel;
    private JLabel responseLabel;
    private JScrollPane headersScrollPane;
    private JButton addHeaderButton;
    private JButton removeHeaderButton;
    private  JPanel headersPanel;
    private JEditorPane requestEditorPane;
    private DefaultTableModel  headersTableModel;
    private JScrollPane httpBodyScrollPane;
    private JScrollPane responseScrollPane;
    private HttpMessageData requestData;
    private JSplitPane requestPane;
    private JPanel httpBodyPanel;
    private JPanel responsePanel;
    private boolean dataLoaded;
    private JEditorPane responseEditorPane;
    private JSplitPane bodiesPane;
    
    public HttpRequestEditor() {
        super(new BorderLayout());
        this.setLayout(new BorderLayout());
        initComponents();
        initHeaderPane();
        initHttpBodyPane();
        add(requestPane);
        requestPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  
        requestPane.setTopComponent(headersPanel);
        requestPane.setBottomComponent(bodiesPane);
        requestPane.setResizeWeight(0.4);
        
        bodiesPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);  
        bodiesPane.setLeftComponent(httpBodyPanel);
        bodiesPane.setRightComponent(responsePanel);
        bodiesPane.setResizeWeight(0.4);
        
        setDataLoaded(false);
    }
    
   
    public void setEnablePanel(boolean enable){
    	headersTable.setEnabled(enable);
    	requestEditorPane.setEnabled(enable);
    	addHeaderButton.setEnabled(enable);
    	removeHeaderButton.setEnabled(enable);
    }
    
    public HttpMessageData getData(){
    	saveData();    	
    	return this.requestData;
    }
    
    public void setData(HttpMessageData data){
    	this.requestData = data;
    	loadData();
    	setDataLoaded(true);
    }
    
    
    public boolean isDataLoaded() {
		return dataLoaded;
	 }

	public void setDataLoaded(boolean dataLoaded) {
		this.dataLoaded = dataLoaded;
	}
	
	public void clearData(){
		clearTable(headersTableModel);
		requestEditorPane.setText("");
		
	}
	
	public void setEditorContent(String content){
    	this.requestEditorPane.setText(content);
    }
 
	/**
	 * 
	 */
	private void clearTable(DefaultTableModel table){
		for(int i = 0; i < table.getRowCount(); i++){
			table.setValueAt("",i,1);
		}
	}
	
	
    private void saveData(){
    	this.requestData.setMethod((String) headersTable.getValueAt(0, 1));
    	this.requestData.setContentType((String) headersTable.getValueAt(1, 1));
    	this.requestData.setHost((String) headersTable.getValueAt(2, 1));
    	this.requestData.setResource((String) headersTable.getValueAt(3, 1));
    	this.requestData.setParams(buildParams());
    	this.requestData.setRequestBody(requestEditorPane.getText());
    	this.requestData.setExpectedBody(responseEditorPane.getText());
    }
    
    private void  loadData(){
    	headersTableModel.setValueAt(this.requestData.getMethod(), 0, 1);
    	headersTableModel.setValueAt(this.requestData.getContentType(), 1, 1);
    	headersTableModel.setValueAt(this.requestData.getHost(), 2, 1);
    	headersTableModel.setValueAt(this.requestData.getResource(), 3, 1);
    	restoreParams(this.requestData.getParams());
    	this.requestEditorPane.setText(this.requestData.getRequestBody());
    	this.responseEditorPane.setText(this.requestData.getExpectedBody());
    }
    
  
    private String buildParams(){
    	String parameters = "";
    	
    	for(int i= 4; i< headersTable.getRowCount(); i++){
    		String param = (String) headersTable.getValueAt(i, 0);
    		String value = (String) headersTable.getValueAt(i, 1);
    		
    		if(param != null && value != null)
    			parameters += param + "=" + value + "&";
    	}
    	
    	//cut the last &
    	if(!parameters.isEmpty())
    		parameters = parameters.substring(0, parameters.length()-2);
    	
    	
    	return parameters;
    }
    
    private void restoreParams(String str){
    	    	
    	if(!str.isEmpty()){
    		headersTableModel.setNumRows(4);
	    	String[] params = str.split("&");
	    	for(int i =0; i < params.length; i++){
	    		String [] splittedParam = params[i].split("=");
	    		String name = splittedParam[0];
	    		String value = splittedParam[1];
	    		headersTableModel.insertRow(i+4, new Object[]{name,value});
	    	}
    	}
    }
    
    
    
    
   /**
     * 
     */
    private void initComponents(){
    	headersPanel = new JPanel();
    	httpBodyPanel = new JPanel();
    	responsePanel = new JPanel();
    	contentLabel = new JLabel();
    	headersLabel = new JLabel();
    	responseLabel = new JLabel();
    	headersTable = new JTable();
    	headersScrollPane = new JScrollPane();
    	httpBodyScrollPane = new JScrollPane();
    	responseScrollPane = new JScrollPane();
    	addHeaderButton = new JButton();
    	removeHeaderButton = new JButton(); 
    	requestEditorPane = new JEditorPane();
    	responseEditorPane = new JEditorPane();
    	requestPane = new JSplitPane();
    	bodiesPane = new JSplitPane();
    }
   
    
   
    private void initHeaderPane(){
		 
    		headersLabel.setText("Http parameters:");
			
			
			headersTable.setDefaultEditor(Object.class, new MyCellEditor());
			headersTable.createDefaultColumnsFromModel();
			headersTable.setRowHeight(25);
	        headersTable.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {{"METHOD"," click and select"},{"CONTENT-TYPE"," click and select"},{"HOST"},{"RESOURCE/SERVICE"}
	            		
	            },
	            new String [] {
	                "Header", "Value"
	            }
	       ) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 7491637677918342777L;
	       });
	
			
	        headersTableModel = (DefaultTableModel) headersTable.getModel();

	        //conditionTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
	        headersScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			
	        headersScrollPane.setViewportView(headersTable);
	        headersTable.getColumnModel().getColumn(0).setPreferredWidth(200);
	        headersTable.getColumnModel().getColumn(1).setPreferredWidth(500);
	        
	        addHeaderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"add_small.png"))); // NOI18N
	        addHeaderButton.setText("Add");
	        addHeaderButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	 headersTableModel.addRow(new Object[]{""});
	            }
	        });

	        removeHeaderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"remove_small.png"))); // NOI18N
	        removeHeaderButton.setText("Remove Last");
	        removeHeaderButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	int rowCount = headersTable.getRowCount();
	            	
	            	if(rowCount > 4){
	         			
	         			if(!headersTable.isCellSelected(rowCount-1, 0) && !headersTable.isCellSelected(rowCount-1, 0) )
	         			{
	         				headersTableModel.removeRow(rowCount-1);
	         				headersTableModel.fireTableStructureChanged();
	         			}else{
	         				ConsoleLog.Message("Please stop editing the cell before you will delete it!");
	         			}
	            	}
	            }
	            
	        });
	        
	        javax.swing.GroupLayout conditionPanelLayout = new javax.swing.GroupLayout(headersPanel);
	        headersPanel.setLayout(conditionPanelLayout);
	        conditionPanelLayout.setHorizontalGroup(
	            conditionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(conditionPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(conditionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(headersScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
	                    .addComponent(headersLabel)
	                    .addGroup(conditionPanelLayout.createSequentialGroup()
	                        .addComponent(addHeaderButton)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(removeHeaderButton)))
	                .addContainerGap())
	        );
	        conditionPanelLayout.setVerticalGroup(
	            conditionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(conditionPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(headersLabel)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(headersScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(conditionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(addHeaderButton)
	                    .addComponent(removeHeaderButton))
	                .addContainerGap())
	        );
	}
    
    private void initHttpBodyPane(){
    
	    contentLabel.setText("Http Body");
	    requestEditorPane.setEditable(true);
	    httpBodyScrollPane.setViewportView(requestEditorPane);
	    MainWindow.initEditorPane(requestEditorPane, httpBodyScrollPane);
	
	    javax.swing.GroupLayout requestPanelLayout = new javax.swing.GroupLayout(httpBodyPanel);
	    httpBodyPanel.setLayout(requestPanelLayout);
	    requestPanelLayout.setHorizontalGroup(
	        requestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	        .addGroup(requestPanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .addGroup(requestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addComponent( httpBodyScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
	                .addComponent(contentLabel))
	            .addContainerGap())
	    );
	    requestPanelLayout.setVerticalGroup(
	        requestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	        .addGroup(requestPanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .addComponent(contentLabel)
	            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	            .addComponent( httpBodyScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
	            .addContainerGap())
	    );
	    
	    
	    responseLabel.setText("Expected Response Body");
	    responseEditorPane.setEditable(true);
	    responseScrollPane.setViewportView(responseEditorPane);
	    MainWindow.initEditorPane(responseEditorPane, responseScrollPane);
	
	    javax.swing.GroupLayout responsePanelLayout = new javax.swing.GroupLayout(responsePanel);
	    responsePanel.setLayout(responsePanelLayout);
	    responsePanelLayout.setHorizontalGroup(
	        responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	        .addGroup(responsePanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .addGroup(responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addComponent( responseScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
	                .addComponent(responseLabel))
	            .addContainerGap())
	    );
	    responsePanelLayout.setVerticalGroup(
	        responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	        .addGroup(responsePanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .addComponent(responseLabel)
	            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	            .addComponent( responseScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
	            .addContainerGap())
	    );
	    
    
    }
    
    
    
  
    

    
    private class MyCellEditor extends DefaultCellEditor{
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = -6297110427296558124L;
		private JComboBox editedBox;
    	private Component result;
		
    	public MyCellEditor() {
			super(new JTextField());
			super.setClickCountToStart(1);
		}
		
    	
    	
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			      int row, int column) {
				
			if(row==1 && column ==1){
				
				String[] objectArray = {"text/plain","text/html","text/xhtml","text/xml","application/json","applicationt/yaml"};
				this.editedBox = new JComboBox(objectArray);
				setupBox(this.editedBox,table,row);
				this.editedBox.setSelectedItem(value);
				this.result =  this.editedBox;
				
				
			}
			
			else if(row==0 && column ==1){ // comBoBox for First row
				
				String[] objectArray = {"GET","POST","PUT","DELETE","HEAD","OPTIONS","TRACE"};
				this.editedBox = new JComboBox(objectArray);
				setupBox(this.editedBox,table,row);
				this.editedBox.setSelectedItem(value);
				this.result = this.editedBox;
				
			}
			
			else{
			   
				JTextField textField = (JTextField)super.getTableCellEditorComponent(table, value, isSelected, row, column);
				
				if(row < 4 && column == 0)
					textField.setEditable(false);
				else
					textField.setEditable(true);
				
				this.result =  textField;
			}
			
			return this.result;
		}
    
		@Override
		public Object getCellEditorValue(){
			
			return super.getCellEditorValue();
		}
		
		private JComboBox setupBox(JComboBox box, JTable table, int row){
			final JTable myTable = table;
			 final int myRow = row; 
			
			//box.setEditable(true);
			     
            ItemListener itemListener = new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED) {
                        if(null != myTable.getCellEditor()){ 
                        	myTable.getCellEditor().stopCellEditing();
                        }
                        ConsoleLog.Print("[RENDERER action]: " + e.getItem()+ " "+myRow);
                        myTable.setValueAt(e.getItem(), myRow, 1);
                    }
                }
            };
            box.addItemListener(itemListener);
            PopupMenuListener popMenuEvent = new PopupMenuListener() {

                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                }
                
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                	JComboBox sValue = (JComboBox)e.getSource();//(String)myTable.getValueAt(myRow, 1);
                    if(null != myTable.getCellEditor()){ 
                        myTable.getCellEditor().stopCellEditing();
                    }
                    ConsoleLog.Print("[RENDERERinvisible]: " + sValue.getSelectedItem()+ " "+myRow);
                    myTable.setValueAt(sValue.getSelectedItem(), myRow, 1);
                }

                public void popupMenuCanceled(PopupMenuEvent e) {   
                	JComboBox sValue = (JComboBox)e.getSource();
                	myTable.setValueAt(sValue.getSelectedItem(), myRow, 1);
                }

            };
            box.addPopupMenuListener(popMenuEvent);
            return box;
		}
    
    }
}
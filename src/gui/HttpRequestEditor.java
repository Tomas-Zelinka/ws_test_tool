package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import modalWindows.AddWsdlToTestSuite;
import data.DataProvider;
import data.HttpMessageData;
 
public class HttpRequestEditor extends JPanel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4286324537862142268L;
	
//	private boolean DEBUG = false;
 
    private  JTable headersTable;
    
    private JLabel headersLabel;
    private JLabel contentLabel;
    private JScrollPane headersScrollPane;
    private JButton addHeaderButton;
    private JButton removeHeaderButton;
    private JButton addWsdlButton;
    private  JPanel headersPanel;
    private JEditorPane httpBodyEditorPane;
    private DefaultTableModel  headersTableModel;
    private JScrollPane httpBodyScrollPane;
    private HttpMessageData requestData;
    private JSplitPane contentPane;
    private JPanel httpBodyPanel;
    private AddWsdlToTestSuite wsdlWindow;
    
    
    public HttpRequestEditor() {
        super(new BorderLayout());
        this.setLayout(new BorderLayout());
        initComponents();
        initHeaderPane();
        initHttpBodyPane();
        add(contentPane);
        contentPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  
        contentPane.setTopComponent(headersPanel);
        contentPane.setBottomComponent(httpBodyPanel);
        contentPane.setResizeWeight(0.4);
    }
    
    public HttpMessageData getHttpRequestData(){
    	return this.requestData;
    }
    
    public void readData(){
    	
    }
    
    public void saveData(){
    	
    }
    
    public void openFile(){    	
    
    }
 
   /**
     * 
     */
    private void initComponents(){
    	headersPanel = new JPanel();
    	httpBodyPanel = new JPanel();
    	contentLabel = new JLabel();
    	headersLabel = new JLabel();
    	headersTable = new JTable();
    	headersScrollPane = new JScrollPane();
    	httpBodyScrollPane = new JScrollPane();
    	addHeaderButton = new JButton();
    	removeHeaderButton = new JButton(); 
    	addWsdlButton = new JButton();
    	httpBodyEditorPane = new JEditorPane();
    	contentPane = new JSplitPane();
    	
    	
    }
    
    
    private void initHeaderPane(){
		 
    		addWsdlButton.addActionListener(new AddWsdlListener());
			headersLabel.setText("Http header:");
			
			
			
			headersTable.setRowHeight(25);
	        headersTable.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {{"HTTP version"," click and select"},{"HTTP method"},{"URI"},{"Content-Type"}
	            		
	            },
	            new String [] {
	                "Header", "Value"
	            }
	            
	            
	        ) {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 3376163326142594714L;
				boolean[] canEdit = new boolean [] {
	                true, true,true
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	            	
	           
	            
	        });
	        TableColumn valueVersion = headersTable.getColumnModel().getColumn(0);
	        TableColumn valueMethod = headersTable.getColumnModel().getColumn(1);
	        TableColumn valueContentType = headersTable.getColumnModel().getColumn(3);
	        //valueColumn.setCellRenderer(new ColumnRenderer());
	        ColumnRenderer httpMethod= new ColumnRenderer();
	        ColumnRenderer httpVersion= new ColumnRenderer();
	        ColumnRenderer httpContentType= new ColumnRenderer();
	        
	        httpMethod.addItem("GET");
			httpMethod.addItem("POST");
			httpMethod.addItem("PUT");
			httpMethod.addItem("HEADER");
			httpMethod.addItem("DELETE");
			httpVersion.addItem("HTTP 1.0");
			httpVersion.addItem("HTTP 1.1");
			httpContentType.addItem("text/xml");
			httpContentType.addItem("application/json");
			
			valueVersion.setCellEditor(new DefaultCellEditor(httpVersion));
			valueMethod.setCellEditor(new DefaultCellEditor(httpMethod));
			
			
			
			
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
	        removeHeaderButton.setText("Remove");
	        removeHeaderButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	int[] selectedRows = headersTable.getSelectedRows();
	         		
	         		for(int i = selectedRows.length-1; i >= 0; i-- ){
	         			 headersTableModel.removeRow(selectedRows[i]);
	         		}
	            }
	        });
	        
	        addWsdlButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"add_small.png"))); // NOI18N
	        addWsdlButton.setText("Get WSDL");
	        addWsdlButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	 System.out.println("haoj");
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
	                        .addComponent(removeHeaderButton)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
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
    
	    contentLabel.setText("Http body");
	    httpBodyEditorPane.setEditable(true);
	    httpBodyScrollPane.setViewportView(httpBodyEditorPane);
	    MainWindow.initEditorPane(httpBodyEditorPane, httpBodyScrollPane);
	
	    javax.swing.GroupLayout responsePanelLayout = new javax.swing.GroupLayout(httpBodyPanel);
	    httpBodyPanel.setLayout(responsePanelLayout);
	    responsePanelLayout.setHorizontalGroup(
	        responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	        .addGroup(responsePanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .addGroup(responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addComponent( httpBodyScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
	                .addComponent(contentLabel))
	            .addContainerGap())
	    );
	    responsePanelLayout.setVerticalGroup(
	        responsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	        .addGroup(responsePanelLayout.createSequentialGroup()
	            .addContainerGap()
	            .addComponent(contentLabel)
	            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	            .addComponent( httpBodyScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
	            .addContainerGap())
	    );
    
    }
    
    
    
    private class AddWsdlListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		wsdlWindow = new AddWsdlToTestSuite();
    		wsdlWindow.setVisible(true);
    		
    	}
    }
    
    
    private class ColumnRenderer extends JComboBox implements TableCellRenderer
    {  
          
        public void updateUI(){  
            super.updateUI();  
        }  
        
        public void revalidate() {}  
        public Component getTableCellRendererComponent(  
                     JTable table, Object value,  
                     boolean isSelected, boolean hasFocus,  
                     int row, int column)  
        {  
            if (value != null) {  
                //System.out.println(value.toString());  
                removeAllItems();  
                addItem(value);  
                
            }  
            return this;  
        }  
    }  
    
  
    
    
}
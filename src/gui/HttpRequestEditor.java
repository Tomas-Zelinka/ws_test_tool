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
    private  JPanel headersPanel;
    private JEditorPane httpBodyEditorPane;
    private DefaultTableModel  headersTableModel;
    private JScrollPane httpBodyScrollPane;
    private HttpMessageData requestData;
    private JSplitPane contentPane;
    private JPanel httpBodyPanel;
    
    
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
    
    public void setHttpRequestData(HttpMessageData data){
    	this.requestData = data;
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
    	httpBodyEditorPane = new JEditorPane();
    	contentPane = new JSplitPane();
    	
    	
    }
    
    
    private void initHeaderPane(){
		 
    		headersLabel.setText("Http header:");
			
			
			headersTable.setDefaultEditor(Object.class, new MyCellEditor());
			headersTable.createDefaultColumnsFromModel();
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
	        removeHeaderButton.setText("Remove");
	        removeHeaderButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	int[] selectedRows = headersTable.getSelectedRows();
	         		
	         		for(int i = selectedRows.length-1; i >= 0; i-- ){
	         			 headersTableModel.removeRow(selectedRows[i]);
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
    
    
    public void setEditorContent(String content){
    	this.httpBodyEditorPane.setText(content);
    }
  
    
//    private class ColumnRenderer extends JComboBox implements TableCellRenderer
//    {  
//          
//        /**
//		 * 
//		 */
//		private static final long serialVersionUID = -5869802814644340167L;
//		
//		public void updateUI(){  
//            super.updateUI();  
//        }  
//        
//        public void revalidate() {}  
//        @SuppressWarnings("unchecked")
//		public Component getTableCellRendererComponent(  
//                     JTable table, Object value,  
//                     boolean isSelected, boolean hasFocus,  
//                     int row, int column)  
//        {  
//            if (value != null) {  
//                //System.out.println(value.toString());  
//                removeAllItems();  
//                addItem(value);  
//                this.setEditable(true);
//            }  
//            return this;  
//        }  
//    } 
    
    
    private class MyCellEditor extends DefaultCellEditor{
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = -6297110427296558124L;
		private JComboBox<String> editedBox;
    	
    	public MyCellEditor() {
			super(new JTextField());
			super.setClickCountToStart(1);
		}
		
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			      int row, int column) {
			if(row==0 && column ==1){ // comBoBox for First row
				String[] objectArray = {"HTTP 1.0","HTTP 1.1"};
				this.editedBox = new JComboBox<String>(objectArray);
				setupBox(this.editedBox,table,row);
				this.editedBox.setSelectedItem(value);
				return this.editedBox;
			}
			
			if(row==1 && column ==1){ // comBoBox for First row
				String[] objectArray = {"GET","POST","PUT","HEAD","DELETE"};
				this.editedBox = new JComboBox<String>(objectArray);
				setupBox(this.editedBox,table,row);
				this.editedBox.setSelectedItem(value);
				return this.editedBox;
			}
			  
			if(column == 0 && row < 4){
			    JTextField textField = (JTextField)super.getTableCellEditorComponent(table, value, isSelected, row, column);
		        textField.setEditable(false);
			    return textField;
			}
			 
			JTextField textField = (JTextField)super.getTableCellEditorComponent(table, value, isSelected, row, column);
			textField.setEditable(true);
			return textField;
		}
    
		@Override
		public Object getCellEditorValue(){
			
			return super.getCellEditorValue();
		}
		
		private JComboBox<String> setupBox(JComboBox<String> box, JTable table, int row){
			final JTable myTable = table;
			 final int myRow = row; 
			
			box.setEditable(true);
			     
            ItemListener itemListener = new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED) {
                        if(null != myTable.getCellEditor()){ 
                        	myTable.getCellEditor().stopCellEditing();
                        }

                        myTable.setValueAt(e.getItem(), myRow, 1);
                    }
                }
            };
            box.addItemListener(itemListener);
            PopupMenuListener popMenuEvent = new PopupMenuListener() {

                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                }

                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    String sValue = (String)myTable.getValueAt(myRow, 1);
                    if(null != myTable.getCellEditor()){ 
                        myTable.getCellEditor().stopCellEditing();
                    }
                    myTable.setValueAt(sValue, myRow, 1);
                }

                public void popupMenuCanceled(PopupMenuEvent e) {   
                }

            };
            box.addPopupMenuListener(popMenuEvent);
            return box;
		}
    
    }
}
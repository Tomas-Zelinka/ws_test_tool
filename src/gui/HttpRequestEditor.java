package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
 
public class HttpRequestEditor extends JPanel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4286324537862142268L;
	
	private boolean DEBUG = false;
 
    private  JTable headers;
    
    private ToolBox toolBox;
    
    DefaultTableModel tableModel;
   
    
    public HttpRequestEditor() {
        super(new BorderLayout());
        this.setLayout(new BorderLayout());
        initComponents();
        
        
        headers.setModel(tableModel);
        headers.setPreferredScrollableViewportSize(new Dimension(500, 70));
        headers.setFillsViewportHeight(true);
        
        if (DEBUG) {
            headers.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(headers);
                }
            });
        }
 
        JSplitPane contentPane = new JSplitPane();
        
        JScrollPane contentScrollPane = new JScrollPane();
        JScrollPane scrollPane = new JScrollPane();
        
        JPanel tablePanel = new JPanel();
        JPanel contentPanel = new JPanel();
        
        JTextArea content = new JTextArea("ahoj",20,5);
        
        tablePanel.setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        
        
        contentPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  
        
        
        scrollPane.getViewport().add(headers);
        contentScrollPane.getViewport().add(content);
        //contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(contentScrollPane,BorderLayout.CENTER);
        tablePanel.add(toolBox,BorderLayout.NORTH);
        tablePanel.add(scrollPane,BorderLayout.CENTER);
        
        
        contentScrollPane.setBorder(BorderFactory.createTitledBorder("HTTP content"));
        scrollPane.setBorder(BorderFactory.createTitledBorder("HTTP header"));
        
        add(contentPane);
        contentPane.setTopComponent(tablePanel);
        contentPane.setBottomComponent(contentPanel);
        contentPane.setResizeWeight(0.4);
       
      
    }
    
    
    
    public void readData(){
    	
    }
    
    
    public void saveData(){
    	
    }
    
    public void openFile(){    	
    
    }
 
    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();
 
        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
 
    private void initComponents(){
    	headers = new JTable();
    	toolBox = new ToolBox();
    	initToolBox();
    	initTableModel();
    }
    
    private void initToolBox(){
    	
    	 JButton addButton = new JButton("Add");
         JButton removeButton = new JButton("Remove Selected");
         JButton removeFirstButton = new JButton("Remove First");
         JButton removeLastButton = new JButton("Remove Last");
         addButton.addActionListener(new ActionListener(){
         	public void actionPerformed(ActionEvent e){
         		tableModel.addRow(new Object[]{""});
         	}
         });
         
         removeButton.addActionListener(new ActionListener(){
         	public void actionPerformed(ActionEvent e){
         		int[] selectedRows = headers.getSelectedRows();
         		
         		for(int i = selectedRows.length-1; i >= 0; i-- ){
         			tableModel.removeRow(selectedRows[i]);
         		}
         	}
         });
         
         removeLastButton.addActionListener(new ActionListener(){
         	public void actionPerformed(ActionEvent e){
         		int rowCount = headers.getRowCount();
         		tableModel.removeRow(rowCount-1);
         	}
         });
         
         removeFirstButton.addActionListener(new ActionListener(){
         	public void actionPerformed(ActionEvent e){
         		tableModel.removeRow(0);
         	}
         });
         
         toolBox.addButton(addButton);
         toolBox.addButton(removeButton);
         toolBox.addButton(removeFirstButton);
         toolBox.addButton(removeLastButton);
         toolBox.setFloatable(false);
    	
    	
    }
    
    private void initTableModel(){
    	String[] columnNames = {"Key",
        "Value"};

		final Object[][] data = {
		{"", ""},
		{"", ""},
		{"", ""},
		{"", ""},
		{"", ""}
		};


		tableModel = new DefaultTableModel(data, columnNames);
    }
    
}
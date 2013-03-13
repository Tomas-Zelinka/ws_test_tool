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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
 
public class TableData extends JPanel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4286324537862142268L;
	
	private boolean DEBUG = false;
 
    
   
    
    public TableData() {
        super(new BorderLayout());
 
        String[] columnNames = {"First Name",
                "Last Name"};
 
        final Object[][] data = {
        {"Kathy", "Smith"},
        {"John", "Doe"},
        {"Sue", "Black"},
        {"Jane", "White"},
        {"Joe", "Brown"}
        };
        
        
 
        final JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        final DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
        
        
        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }
 
        
        JScrollPane contentScrollPane = new JScrollPane();
        JScrollPane scrollPane = new JScrollPane();
        JPanel tablePanel = new JPanel();
        //JPanel contentPanel = new JPanel();
        
        tablePanel.setLayout(new BorderLayout());
        
               
        ToolBox toolBox = new ToolBox();
        JTextArea content = new JTextArea("ahoj", 5,5);
        
        
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove Selected");
        JButton removeFirstButton = new JButton("Remove First");
        JButton removeLastButton = new JButton("Remove Last");
        addButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		model.addRow(data[0]);
        	}
        });
        
        removeButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		int[] selectedRows = table.getSelectedRows();
        		
        		for(int i = selectedRows.length-1; i >= 0; i-- ){
        			model.removeRow(selectedRows[i]);
        		}
        	}
        });
        
        removeLastButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		int rowCount = table.getRowCount();
        		model.removeRow(rowCount-1);
        	}
        });
        
        removeFirstButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		model.removeRow(0);
        	}
        });
        
        toolBox.addButton(addButton);
        toolBox.addButton(removeButton);
        toolBox.addButton(removeFirstButton);
        toolBox.addButton(removeLastButton);
        toolBox.setFloatable(false);
        tablePanel.add(toolBox,BorderLayout.NORTH);
        tablePanel.add(table);
        
       
        scrollPane.getViewport().add(tablePanel);
        contentScrollPane.getViewport().add(content);
        
        add(scrollPane, BorderLayout.CENTER);
        add(contentScrollPane,BorderLayout.SOUTH);
      
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
 
    
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        TableData newContentPane = new TableData();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
    
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
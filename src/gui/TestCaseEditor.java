package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import logging.ConsoleLog;
import modalWindows.AntiAliasedEditorPane;
import proxyUnit.Condition;
import proxyUnit.Fault;
import proxyUnit.Test;
import proxyUnit.TestStatement;


public class TestCaseEditor extends JSplitPane {

	
	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = -2860238189144324557L;

	/**
	 * Width of the resizing frames
	 */
	private final int SPLIT_RESIZERS_WIDTH = 5;
	
	
	/**
	 * 
	 */
	public TestCaseEditor(){
		
		initComponents();
		//setConditionPane();
		//setFaultPane();
		//setHTTPRequestPane();
		//setTestToolbar();
		JPanel httpEditor = new HttpRequestEditor();
		
	     
	    mainTabbedPane.addTab("HTTP Request", httpEditor);
	    mainTabbedPane.addTab("Fault Injection",statementDetailSplitPane);
	    mainTabbedPane.addTab("Test Case Settings",settings);
		
		
		
		this.setLeftComponent(navigator);
		this.setRightComponent(mainTabbedPane);
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.setDividerSize(SPLIT_RESIZERS_WIDTH);
		
	}
	
	/**
	 * 
	 */
//	private void setTestToolbar(){
//		//testSplitPane.setDividerLocation(220);
//
//        //testToolBar.setFloatable(false);
//        //testToolBar.setRollover(true);
//
//        addTestButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/add_test_small.png"))); // NOI18N
//        addTestButton.setToolTipText("Add new test");
//        addTestButton.setFocusable(false);
//        addTestButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        addTestButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        addTestButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                addTestButtonActionPerformed(evt);
//            }
//        });
//        //testToolBar.add(addTestButton);
//
//        addStatementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/add_statement_small.png"))); // NOI18N
//        addStatementButton.setToolTipText("Add new statement");
//        addStatementButton.setFocusable(false);
//        addStatementButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        addStatementButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        addStatementButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                addStatementButtonActionPerformed(evt);
//            }
//        });
//        //testToolBar.add(addStatementButton);
//        //testToolBar.add(jSeparator2);
//
//        removeNodeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/remove_small.png"))); // NOI18N
//        removeNodeButton.setToolTipText("Remove");
//        removeNodeButton.setFocusable(false);
//        removeNodeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        removeNodeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        removeNodeButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                removeNodeButtonActionPerformed(evt);
//            }
//        });
        //testToolBar.add(removeNodeButton);

        //testScrollPane.setViewportView(testTree);
        //testTree.addMouseListener(testTreePopupListener);
        //nastaveni vykresleni ikon v JTree
        //testTreeRenderer= new TestTreeCellRenderer();
       // testTree.setCellRenderer(testTreeRenderer);

//        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
//        jPanel8.setLayout(jPanel8Layout);
//        jPanel8Layout.setHorizontalGroup(
//            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(testToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
//            .addComponent(testScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
//        );
//        jPanel8Layout.setVerticalGroup(
//            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel8Layout.createSequentialGroup()
//                .addComponent(testToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(testScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
//        );

       // testSplitPane.setLeftComponent(jPanel8);

 //   }
	
	
//	/**
//	 * 
//	 */
//	private void setHTTPRequestPane(){
//		
//		
//		//ToolBox toolBox = new ToolBox(); 
//		JTabbedPane httpConentEditor = new JTabbedPane();
//		JPanel httpEditor = new HttpRequestEditor();
//		JScrollPane paneEditRequest = new JScrollPane();
//		JScrollPane paneEditResponse = new JScrollPane();
//		JTextArea responseArea = new JTextArea("",5,10);
//		JTextArea requestArea = new JTextArea("",5,10);
//		JButton start = new JButton("Start");
//		
//		JSplitPane windows = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,paneEditResponse,paneEditRequest);
//		
//		responseArea.setPreferredSize(new Dimension(getWidth(),100));
//		
//		requestArea.setPreferredSize(new Dimension(getWidth(),100));
//		
//		paneEditRequest.getViewport().add(requestArea);
//		paneEditRequest.setBorder(BorderFactory.createEmptyBorder());
//		
//		paneEditResponse.getViewport().add(responseArea);
//		paneEditResponse.setBorder(BorderFactory.createEmptyBorder());
//		
//		windows.setDividerSize(5);
//		windows.setResizeWeight(0.45);
//		
//		//toolBox.addButton(start);
//		
//		
//		
//		editor.setLayout(new BorderLayout());
//		editor.add(httpEditor);
//		editor.add(httpEditor,BorderLayout.CENTER);
//		
//	}
	
	
	
	
	/**
	 * 
	 */
	private void initComponents(){
		
		File root = new File(MainWindow.getDataRoot());
		
		if(!root.exists()){
			boolean wasDirectoryMade = root.mkdirs();
		    if(wasDirectoryMade)
		    	ConsoleLog.Print("Direcoty Created");
		}
		
		settings = new JPanel();
		navigator = new  ProjectNavigator(root);
		statementDetailSplitPane = new FaultInjectionEditor();
		
		
		testTreePopupMenu = new javax.swing.JPopupMenu();
		renameMenuItem = new javax.swing.JMenuItem();
        
        topToolBar = new javax.swing.JToolBar();
        testComboBox = new javax.swing.JComboBox();
        startTestToggleButton = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        proxySettingsButton = new javax.swing.JButton();
        startProxyToggleButton = new javax.swing.JToggleButton();
        mainTabbedPane = new javax.swing.JTabbedPane();
        messageSplitPane = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
       // jScrollPane1 = new javax.swing.JScrollPane();
       // interactionTable = new javax.swing.JTable();
        messageTabbedPane = new javax.swing.JTabbedPane();
        reqSplitPane = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        reqOriginalLabel = new javax.swing.JLabel();
        reqOriginalScrollPane = new javax.swing.JScrollPane();
        reqOriginalEditorPane = new AntiAliasedEditorPane();
        jPanel3 = new javax.swing.JPanel();
        reqChangedLabel = new javax.swing.JLabel();
        reqChangedScrollPane = new javax.swing.JScrollPane();
        reqChangedEditorPane = new AntiAliasedEditorPane();
        resSplitPane = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        resOriginalLabel = new javax.swing.JLabel();
        resOriginalScrollPane = new javax.swing.JScrollPane();
        resOriginalEditorPane = new AntiAliasedEditorPane();
        jPanel6 = new javax.swing.JPanel();
        resChangedLabel = new javax.swing.JLabel();
        resChangedScrollPane = new javax.swing.JScrollPane();
        resChangedEditorPane = new AntiAliasedEditorPane();
        //testSplitPane = new javax.swing.JSplitPane();
        jPanel8 = new javax.swing.JPanel();
        //testToolBar = new javax.swing.JToolBar();
       
        jSeparator2 = new javax.swing.JToolBar.Separator();
        
        testScrollPane = new javax.swing.JScrollPane();
       // testTree = new javax.swing.JTree();
        
        
        
        
        topMenuBar = new javax.swing.JMenuBar();
       // fileMenu = new javax.swing.JMenu();
       // exitMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        settingsMenuItem = new javax.swing.JMenuItem();
        ///editor = new JPanel();
		mainTabbedPane = new JTabbedPane();
	}
	
	private JPanel settings;
	private ProjectNavigator navigator;
	//private JPanel editor;
	private JTabbedPane mainTabbedPane;
	
    
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
   // private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JSplitPane messageSplitPane;
    private javax.swing.JTabbedPane messageTabbedPane;
    private javax.swing.JButton proxySettingsButton;
   
    private javax.swing.JMenuItem renameMenuItem;
    private javax.swing.JEditorPane reqChangedEditorPane;
    private javax.swing.JLabel reqChangedLabel;
    private javax.swing.JScrollPane reqChangedScrollPane;
    private javax.swing.JEditorPane reqOriginalEditorPane;
    private javax.swing.JLabel reqOriginalLabel;
    private javax.swing.JScrollPane reqOriginalScrollPane;
    private javax.swing.JSplitPane reqSplitPane;
    private javax.swing.JEditorPane resChangedEditorPane;
    private javax.swing.JLabel resChangedLabel;
    private javax.swing.JScrollPane resChangedScrollPane;
    private javax.swing.JEditorPane resOriginalEditorPane;
    private javax.swing.JLabel resOriginalLabel;
    private javax.swing.JScrollPane resOriginalScrollPane;
    private javax.swing.JSplitPane resSplitPane;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JMenuItem settingsMenuItem;
    private javax.swing.JToggleButton startProxyToggleButton;
    private javax.swing.JToggleButton startTestToggleButton;
    private javax.swing.JSplitPane statementDetailSplitPane;
    private javax.swing.DefaultComboBoxModel testComboBoxModel;
    private javax.swing.JComboBox testComboBox;
    private javax.swing.JScrollPane testScrollPane;
    private javax.swing.JSplitPane testSplitPane;
    //private javax.swing.JToolBar testToolBar;
    private javax.swing.tree.DefaultTreeModel testTreeModel;
    private javax.swing.tree.DefaultTreeCellRenderer testTreeRenderer;
    //private javax.swing.JTree testTree;
    private javax.swing.JPopupMenu testTreePopupMenu;
    private javax.swing.JMenuBar topMenuBar;
    private javax.swing.JToolBar topToolBar;
}

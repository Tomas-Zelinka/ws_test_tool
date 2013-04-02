package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import data.HttpRequestData;
import data.Test;
import data.TestCaseSettingsData;


public class TestCaseEditor extends JPanel {

	
	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = -2860238189144324557L;

	/**
	 * 
	 */
	public static final int FAULT_TAB = 2;
	public static final int HTTP_TAB = 1;
	public static final int SETTINGS_TAB = 0;
	
	private TestCaseSettings settings;
	private JTabbedPane mainTabbedPane;
	private FaultInjectionEditor statementDetailSplitPane;
	private HttpRequestEditor httpEditor;
	
	public TestCaseEditor(){
		
		initComponents();
		
		
		
		mainTabbedPane.addTab("Test Case Settings",settings);
	    mainTabbedPane.addTab("HTTP Request", httpEditor);
	    mainTabbedPane.addTab("Fault Injection",statementDetailSplitPane);
	    
		setLayout(new BorderLayout());
		add(mainTabbedPane,BorderLayout.CENTER);
		//mainTabbedPane.setSelectedIndex(2);
	}
	
	
	public void setTab(int tab){
		System.out.println("tab:"+tab);
		mainTabbedPane.setSelectedIndex(tab);
		mainTabbedPane.revalidate();
		mainTabbedPane.repaint();
	}
	
	public int getTab(){
		return mainTabbedPane.getSelectedIndex();
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
	
	/**
	 * 
	 */
	private void initComponents(){
		settings = new TestCaseSettings();
		httpEditor = new HttpRequestEditor();
		statementDetailSplitPane = new FaultInjectionEditor();
		mainTabbedPane = new JTabbedPane();
	}
	
	
	public void saveTestCase(){
		
		Test test = statementDetailSplitPane.getFaultInjetionData();
		HttpRequestData requestData = httpEditor.getHttpRequestData();
		TestCaseSettingsData settingsData = settings.getSettingsData();
		
		System.out.println("saved");
	}
}

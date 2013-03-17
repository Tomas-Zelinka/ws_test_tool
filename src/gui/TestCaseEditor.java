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
		setConditionPane();
		setFaultPane();
		setHTTPRequestPane();
		setTestToolbar();
			
		statementDetailSplitPane.setDividerLocation(250);
	    statementDetailSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
	    statementDetailSplitPane.setBottomComponent(faultPanel);
	    statementDetailSplitPane.setTopComponent(conditionPanel);
	     
	    mainTabbedPane.addTab("HTTP Request", editor);
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
	private void setTestToolbar(){
		//testSplitPane.setDividerLocation(220);

        //testToolBar.setFloatable(false);
        //testToolBar.setRollover(true);

        addTestButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/add_test_small.png"))); // NOI18N
        addTestButton.setToolTipText("Add new test");
        addTestButton.setFocusable(false);
        addTestButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addTestButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTestButtonActionPerformed(evt);
            }
        });
        //testToolBar.add(addTestButton);

        addStatementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/add_statement_small.png"))); // NOI18N
        addStatementButton.setToolTipText("Add new statement");
        addStatementButton.setFocusable(false);
        addStatementButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addStatementButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addStatementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStatementButtonActionPerformed(evt);
            }
        });
        //testToolBar.add(addStatementButton);
        //testToolBar.add(jSeparator2);

        removeNodeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/remove_small.png"))); // NOI18N
        removeNodeButton.setToolTipText("Remove");
        removeNodeButton.setFocusable(false);
        removeNodeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeNodeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeNodeButtonActionPerformed(evt);
            }
        });
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

    }
	
	
	/**
	 * 
	 */
	private void setHTTPRequestPane(){
		
		
		//ToolBox toolBox = new ToolBox(); 
		JTabbedPane httpConentEditor = new JTabbedPane();
		JPanel httpEditor = new HttpHeaderEditor();
		JScrollPane paneEditRequest = new JScrollPane();
		JScrollPane paneEditResponse = new JScrollPane();
		JTextArea responseArea = new JTextArea("",5,10);
		JTextArea requestArea = new JTextArea("",5,10);
		JButton start = new JButton("Start");
		
		JSplitPane windows = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,paneEditResponse,paneEditRequest);
		
		responseArea.setPreferredSize(new Dimension(getWidth(),100));
		
		requestArea.setPreferredSize(new Dimension(getWidth(),100));
		
		paneEditRequest.getViewport().add(requestArea);
		paneEditRequest.setBorder(BorderFactory.createEmptyBorder());
		
		paneEditResponse.getViewport().add(responseArea);
		paneEditResponse.setBorder(BorderFactory.createEmptyBorder());
		
		windows.setDividerSize(5);
		windows.setResizeWeight(0.45);
		
		//toolBox.addButton(start);
		
		
		
		editor.setLayout(new BorderLayout());
		editor.add(httpEditor,BorderLayout.NORTH);
		//editor.add(toolBox,BorderLayout.NORTH);
		//editor.add(windows,BorderLayout.CENTER);
		
	}
	
	
	
	/**
	 * 
	 */
	private void setConditionPane(){
		 
		conditionLabel.setText("Conditions:");

	        conditionTable.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	                "Type", "Detail"
	            }
	        ) {
	            boolean[] canEdit = new boolean [] {
	                false, false
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });
	        conditionTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
	        jScrollPane3.setViewportView(conditionTable);
	        conditionTable.getColumnModel().getColumn(0).setPreferredWidth(200);
	        conditionTable.getColumnModel().getColumn(1).setPreferredWidth(500);

	        addConditionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/add_small.png"))); // NOI18N
	        addConditionButton.setText("Add");
	        addConditionButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                addConditionButtonActionPerformed(evt);
	            }
	        });

	        removeConditionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/remove_small.png"))); // NOI18N
	        removeConditionButton.setText("Remove");
	        removeConditionButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                removeConditionButtonActionPerformed(evt);
	            }
	        });

	        javax.swing.GroupLayout conditionPanelLayout = new javax.swing.GroupLayout(conditionPanel);
	        conditionPanel.setLayout(conditionPanelLayout);
	        conditionPanelLayout.setHorizontalGroup(
	            conditionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(conditionPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(conditionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
	                    .addComponent(conditionLabel)
	                    .addGroup(conditionPanelLayout.createSequentialGroup()
	                        .addComponent(addConditionButton)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(removeConditionButton)))
	                .addContainerGap())
	        );
	        conditionPanelLayout.setVerticalGroup(
	            conditionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(conditionPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(conditionLabel)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(conditionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(addConditionButton)
	                    .addComponent(removeConditionButton))
	                .addContainerGap())
	        );
	}
	
	/**
	 * 
	 */
	private void setFaultPane(){
		
		
		faultLabel.setText("Faults:");

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        faultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        faultTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane4.setViewportView(faultTable);
        faultTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        faultTable.getColumnModel().getColumn(1).setPreferredWidth(500);

        addFaultButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/add_small.png"))); // NOI18N
        addFaultButton.setText("Add");
        addFaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFaultButtonActionPerformed(evt);
            }
        });

        removeFaultButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("resources/remove_small.png"))); // NOI18N
        removeFaultButton.setText("Remove");
        removeFaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFaultButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout faultPanelLayout = new javax.swing.GroupLayout(faultPanel);
        faultPanel.setLayout(faultPanelLayout);
        faultPanelLayout.setHorizontalGroup(
            faultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faultPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(faultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addComponent(faultLabel)
                    .addGroup(faultPanelLayout.createSequentialGroup()
                        .addComponent(addFaultButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeFaultButton)))
                .addContainerGap())
        );
        faultPanelLayout.setVerticalGroup(
            faultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faultPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(faultLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(faultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addFaultButton)
                    .addComponent(removeFaultButton))
                .addContainerGap())
        );

	}
	
	/**
	 * Metoda pro aktualizaci tabulky a tlacitek v sekci podminek v zavislosti na oznacene polozce
	 * v testTree.
	 * @param selectedNode oznacena polozka v testTree
	 */
	private void refreshConditionPanel(DefaultMutableTreeNode selectedNode) {
		
		//vymazeme vsechny radky tabulky
		conditionTableModel.setRowCount(0);
		
		Object selectedObject= selectedNode.getUserObject();
		
		//pokud bylo kliknuto na pravidlo..zobrazime prislusnou podminku v tabulce
		if (selectedObject instanceof TestStatement) {
			conditionTable.setEnabled(true);
			addConditionButton.setEnabled(true);
			removeConditionButton.setEnabled(true);
			TestStatement selectedStatement= (TestStatement) selectedObject;
			for (Condition currentCondition : selectedStatement.getConditionSet()) {
				//vlozeni noveho radku do tabulky podminek
				Object[] newRow= new Object[] {currentCondition, currentCondition.getDescription()};
				conditionTableModel.insertRow(conditionTable.getRowCount(), newRow);
			}
		}
		//pokud byl oznacen test nebo korenovy uzel..znepristupnime tabulky a tlacitka
		else {
			conditionTable.setEnabled(false);
			addConditionButton.setEnabled(false);
			removeConditionButton.setEnabled(false);
		}
			
	}
	
	
	
	/**
	 * Metoda pro aktualizaci tabulky a tlacitek v sekci poruch v zavislosti na oznacene polozce v
	 * testTree.
	 * @param selectedNode oznacena polozka v testTree
	 */
	private void refreshFaultPanel(DefaultMutableTreeNode selectedNode) {
		
		//vymazeme vsechny radky tabulky
		faultTableModel.setRowCount(0);
		
		Object selectedObject= selectedNode.getUserObject();
		
		//pokud bylo kliknuto na pravidlo..zobrazime prislusne poruchy v tabulce
		if (selectedObject instanceof TestStatement) {
			faultTable.setEnabled(true);
			addFaultButton.setEnabled(true);
			removeFaultButton.setEnabled(true);
			TestStatement selectedStatement= (TestStatement) selectedObject;
			for (Fault currentFault : selectedStatement.getFaultList()) {
				//vlozeni noveho radku do tabulky poruch
				Object[] newRow= new Object[] {currentFault, currentFault.getDescription()};
				faultTableModel.insertRow(faultTable.getRowCount(), newRow);
			}
		}
		//pokud byl oznacen test nebo korenovy uzel..znepristupnime tabulky a tlacitka
		else {
			faultTable.setEnabled(false);
			addFaultButton.setEnabled(false);
			removeFaultButton.setEnabled(false);
		}
	}
	

	private void addConditionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addConditionButtonActionPerformed
		
//		//zabraneni modifikace prave beziciho testu
////		if (isRunningTestOrStatementSelected()) {
////			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
////			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
////			return;
////		}
//		//zobrazime novy dialog pro pridani podminky
//		AddConditionDialog addConditionDialog= new AddConditionDialog(this, true, controller.getNewConditionId());
//		addConditionDialog.setVisible(true);
//		
//		//pokud bylo stisknuto tlacitko "pridat", prevezmeme nove vzniklou podminku z dialogu a zaradime ji
//		// do prislusne kolekce
//		if (addConditionDialog.isAddButtonClicked()) {
//			Condition newCondition= addConditionDialog.getNewCondition();
//			//zjistime oznacene pravidlo ve stromu a pridame do jeho kolekce novou podminku
//			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//			TestStatement selectedStatement= (TestStatement) selectedNode.getUserObject();
//			selectedStatement.addToConditionSet(newCondition);
//			//refresh tabulky podminek
//			refreshConditionPanel(selectedNode);
//					
//		}
	}//GEN-LAST:event_addConditionButtonActionPerformed

	private void addFaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFaultButtonActionPerformed
		
//		//zabraneni modifikace prave beziciho testu
////		if (isRunningTestOrStatementSelected()) {
////			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
////			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
////			return;
////		}
//		//zobrazime novy dialog pro pridani poruchy
//		AddFaultDialog addFaultDialog= new AddFaultDialog(this, true, controller.getNewFaultId());
//		addFaultDialog.setVisible(true);
//		
//		//pokud bylo stisknuto tlacitko "pridat", prevezmeme nove vzniklou poruchu z dialogu a zaradime ji
//		//do prislusne kolekce
//		if (addFaultDialog.isAddButtonClicked()) {
//			Fault newFault= addFaultDialog.getNewFault();
//			//zjistime oznacene pravidlo ve stromu a pridame do jeho kolekce novou poruchu
//			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//			TestStatement selectedStatement= (TestStatement) selectedNode.getUserObject();
//			selectedStatement.addToFaultList(newFault);
//			//refresh tabulky poruch
//			refreshFaultPanel(selectedNode);
//		}
	}//GEN-LAST:event_addFaultButtonActionPerformed

	private void addTestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTestMenuItemActionPerformed
		
//		//zobrazime dialog pro pridani noveho testu
//		AddTestDialog addTestDialog= new AddTestDialog(this, true, controller.getNewTestId());
//		addTestDialog.setVisible(true);
//		
//		//pokud bylo stisknuto "ok", zaradime novy test do kolekce
//		if (addTestDialog.isOkButtonClicked()) {
//			Test newTest= addTestDialog.getNewTest();
//			controller.addToTestList(newTest);
//			
//			//vlozime novy uzel do stromu na prislusne misto
//			DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newTest);
//			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//			testTreeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
//			//rozbal strom tak, aby novy uzel sel videt
//			testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
//			
//			//aktualizace testComboBoxu
//			testComboBoxModel.addElement(newTest);
//		}
//		
	}//GEN-LAST:event_addTestMenuItemActionPerformed

	private void addStatementMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStatementMenuItemActionPerformed
		
//		//zabraneni modifikace prave beziciho testu
////		if (isRunningTestOrStatementSelected()) {
////			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
////			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
////			return;
////		}
//		//zobrazime dialog pro pridani noveho pravidla
//		AddStatementDialog addStatementDialog= new AddStatementDialog(this, true, controller.getNewTestStatementId());
//		addStatementDialog.setVisible(true);
//		
//		//pokud bylo stisknuto "ok", zaradime nove pravidlo do kolekce
//		if (addStatementDialog.isOkButtonClicked()) {
//			TestStatement newStatement= addStatementDialog.getNewStatement();
//			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//			Test selectedTest= (Test) selectedNode.getUserObject();
//			selectedTest.addToStatementList(newStatement);
//			
//			//vlozime novy uzel do stromu na prislusne misto
//			DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newStatement);
//			testTreeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
//			//rozbal strom tak, aby novy uzel sel videt
//			testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
//		}
//		
	}//GEN-LAST:event_addStatementMenuItemActionPerformed

	private void renameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameMenuItemActionPerformed
		
//		//zabraneni modifikace prave beziciho testu
////		if (isRunningTestOrStatementSelected()) {
////			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
////			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
////			return;
////		}
//		//zobrazime dialog pro prejmenovani uzlu
//		RenameDialog renameDialog= new RenameDialog(this, true);
//		renameDialog.setVisible(true);
//		
//		//pokud bylo stisknuto "ok", prejmenujeme oznaceny uzel
//		if (renameDialog.isOkButtonClicked()) {
//			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//			Object selectedObject= selectedNode.getUserObject();
//			//pokud bylo kliknuto na test..
//			if (selectedObject instanceof Test) {
//				Test selectedTest= (Test) selectedObject;
//				selectedTest.setTestName(renameDialog.getNewName());
//				//aktualizace testComboBoxu
//				testComboBox.repaint();
//			}
//			//jinak bylo kliknuto na pravidlo..
//			else {
//				TestStatement selectedStatement= (TestStatement) selectedObject;
//				selectedStatement.setStatementName(renameDialog.getNewName());
//			}
//			//publikovani zmen v datovem modelu stromu
//			testTreeModel.nodeChanged(selectedNode);
//			
//		}
		
	}//GEN-LAST:event_renameMenuItemActionPerformed

	private void removeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMenuItemActionPerformed
//		
//		//zabraneni modifikace prave beziciho testu
////		if (isRunningTestOrStatementSelected()) {
////			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
////			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
////			return;
////		}
//		//zjistime, ktery uzel je oznacen
//		DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//		TreePath selectionPath= testTree.getSelectionPath();
//		Object selectedObject= selectedNode.getUserObject();
//		//pokud je oznacen test..
//		if (selectedObject instanceof Test) {
//			Test selectedTest= (Test) selectedObject;
//			//zobrazime jednoduchy dialog pro ujisteni, zda se ma test smazat
//			Object[] options= {"Yes", "No"};
//			int answer= JOptionPane.showOptionDialog(this, "Are you really want to remove " + selectedTest.getTestName() +
//					" with all particular statements?", "Remove test", JOptionPane.YES_NO_OPTION,
//					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//			//pokud ok, odstranime test z kolekce a take z datoveho modelu stromu
//			if (answer == JOptionPane.YES_OPTION) {
//				controller.removeTestFromList(selectedTest);
//				testTreeModel.removeNodeFromParent(selectedNode);
//				//aktualizace testComboBoxu
//				testComboBoxModel.removeElement(selectedTest);
//				testComboBox.repaint();
//			}
//			else
//				return;
//		}
//		//pokud je oznaceno pravidlo..
//		else {
//			//odstranime pravidlo z kolekce a z datoveho modelu stromu
//			TestStatement selectedStatement= (TestStatement) selectedObject;
//			DefaultMutableTreeNode parentNode= (DefaultMutableTreeNode) selectedNode.getParent();
//			Test parentTest= (Test) parentNode.getUserObject();
//			parentTest.removeFromStatementList(selectedStatement);
//			testTreeModel.removeNodeFromParent(selectedNode);
//		}
//		
//		//nastavime oznaceni na otcovsky uzel a refresh panelu
//		testTree.setSelectionPath(selectionPath.getParentPath());
//		DefaultMutableTreeNode newSelectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//		refreshConditionPanel(newSelectedNode);
//		refreshFaultPanel(newSelectedNode);
//		
//		//pokud je oznacen root uzel..znepristupnime prislusne tlacitka
//		if (newSelectedNode.equals(testTreeModel.getRoot())) {
//			addStatementButton.setEnabled(false);
//			removeNodeButton.setEnabled(false);
//		}
//			
//		
	}//GEN-LAST:event_removeMenuItemActionPerformed

	private void addTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTestButtonActionPerformed
		
//		//zobrazime dialog pro pridani noveho testu
//		AddTestDialog addTestDialog= new AddTestDialog(this, true, controller.getNewTestId());
//		addTestDialog.setVisible(true);
//		
//		//pokud bylo stisknuto "ok", zaradime novy test do kolekce
//		if (addTestDialog.isOkButtonClicked()) {
//			Test newTest= addTestDialog.getNewTest();
//			controller.addToTestList(newTest);
//			
//			//vlozime novy uzel do stromu na prislusne misto
//			DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newTest);
//			DefaultMutableTreeNode rootNode= (DefaultMutableTreeNode) testTreeModel.getRoot();
//			testTreeModel.insertNodeInto(newNode, rootNode, rootNode.getChildCount());
//			//rozbal strom tak, aby novy uzel sel videt
//			testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
//			
//			//aktualizace testComboBoxu
//			testComboBoxModel.addElement(newTest);
//		}
//		
	}//GEN-LAST:event_addTestButtonActionPerformed

	private void addStatementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStatementButtonActionPerformed
		
//		//zabraneni modifikace prave beziciho testu
////		if (isRunningTestOrStatementSelected()) {
////			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
////			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
////			return;
////		}
//		//zobrazime dialog pro pridani noveho pravidla
//		AddStatementDialog addStatementDialog= new AddStatementDialog(this, true, controller.getNewTestStatementId());
//		addStatementDialog.setVisible(true);
//		
//		//pokud bylo stisknuto "ok", zaradime nove pravidlo do kolekce
//		if (addStatementDialog.isOkButtonClicked()) {
//			TestStatement newStatement= addStatementDialog.getNewStatement();
//			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//			Object selectedObject= selectedNode.getUserObject();
//			//pokud je momentalne oznacen test..pridame nove pravidlo do jeho kolekce
//			if (selectedObject instanceof Test) {
//				Test selectedTest= (Test) selectedObject;
//				selectedTest.addToStatementList(newStatement);
//				//vlozime uzel do stromu na prislusne misto
//				DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newStatement);
//				testTreeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
//				//rozbal strom tak, aby novy uzel sel videt
//				testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
//			}
//			//jinak je oznaceno pravidlo..pridame nove pravidlo do otcovskeho uzlu 
//			else {
//				DefaultMutableTreeNode parentNode= (DefaultMutableTreeNode) selectedNode.getParent();
//				Test parentTest= (Test) parentNode.getUserObject();
//				parentTest.addToStatementList(newStatement);
//				//vlozime uzel do stromu na prislusne misto
//				DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newStatement);
//				testTreeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
//				//rozbal strom tak, aby novy uzel sel videt
//				testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
//			}
//			
//		}
		
	}//GEN-LAST:event_addStatementButtonActionPerformed

	private void removeNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeNodeButtonActionPerformed
		
		//stejna funkce jako z kontextoveho menu
		removeMenuItemActionPerformed(evt);
	}//GEN-LAST:event_removeNodeButtonActionPerformed

	private void removeConditionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeConditionButtonActionPerformed
		
//		//zabraneni modifikace prave beziciho testu
////		if (isRunningTestOrStatementSelected()) {
////			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
////			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
////			return;
////		}
//		//zjistime, ktery radek v tabulce je oznacen a ziskame referenci na konkretni podminku
//		int selectedRow= conditionTable.getSelectedRow();
//		if (selectedRow == -1)
//			return;
//		Condition selectedCondition= (Condition) conditionTableModel.getValueAt(selectedRow, 0);
//		
//		//zjistime, ktera podminka ve stromu je oznacena
//		DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//		TestStatement selectedStatement= (TestStatement) selectedNode.getUserObject();
//		
//		//odstranime podminku jak z tabulky, tak z kolekce
//		selectedStatement.removeFromConditionSet(selectedCondition);
//		conditionTableModel.removeRow(selectedRow);
//				
	}//GEN-LAST:event_removeConditionButtonActionPerformed

	private void removeFaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFaultButtonActionPerformed
		
//		//zabraneni modifikace prave beziciho testu
////		if (isRunningTestOrStatementSelected()) {
////			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
////			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
////			return;
////		}
//		//zjistime, ktery radek v tabulce je oznacen a ziskame referenci na konkretni poruchu
//		int selectedRow= faultTable.getSelectedRow();
//		if (selectedRow == -1)
//			return;
//		Fault selectedFault= (Fault) faultTableModel.getValueAt(selectedRow, 0);
//		
//		//zjistime, ktera porucha ve stromu je oznacena
//		DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//		TestStatement selectedStatement= (TestStatement) selectedNode.getUserObject();
//		
//		//odstranime poruchu jak z tabulky, tak z kolekce
//		selectedStatement.removeFromFaultList(selectedFault);
//		faultTableModel.removeRow(selectedRow);
	}//GEN-LAST:event_removeFaultButtonActionPerformed

	
	

//	private void settingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsMenuItemActionPerformed
//		
//		//stejna operace jako predesla metoda
//		proxySettingsButtonActionPerformed(evt);
//		
//	}//GEN-LAST:event_settingsMenuItemActionPerformed

	
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
		testTreePopupMenu = new javax.swing.JPopupMenu();
        addTestMenuItem = new javax.swing.JMenuItem();
        addStatementMenuItem = new javax.swing.JMenuItem();
        renameMenuItem = new javax.swing.JMenuItem();
        removeMenuItem = new javax.swing.JMenuItem();
        topToolBar = new javax.swing.JToolBar();
        testComboBox = new javax.swing.JComboBox();
        startTestToggleButton = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        proxySettingsButton = new javax.swing.JButton();
        startProxyToggleButton = new javax.swing.JToggleButton();
        mainTabbedPane = new javax.swing.JTabbedPane();
        messageSplitPane = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        interactionTable = new javax.swing.JTable();
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
        addTestButton = new javax.swing.JButton();
        addStatementButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        removeNodeButton = new javax.swing.JButton();
        testScrollPane = new javax.swing.JScrollPane();
       // testTree = new javax.swing.JTree();
        statementDetailSplitPane = new javax.swing.JSplitPane();
        conditionPanel = new javax.swing.JPanel();
        conditionLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        conditionTable = new javax.swing.JTable();
        addConditionButton = new javax.swing.JButton();
        removeConditionButton = new javax.swing.JButton();
        faultPanel = new javax.swing.JPanel();
        faultLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        faultTable = new javax.swing.JTable();
        addFaultButton = new javax.swing.JButton();
        removeFaultButton = new javax.swing.JButton();
        topMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        settingsMenuItem = new javax.swing.JMenuItem();
        editor = new JPanel();
		mainTabbedPane = new JTabbedPane();
	}
	
	private JPanel settings;
	private ProjectNavigator navigator;
	private JPanel editor;
	private JTabbedPane mainTabbedPane;
	private javax.swing.JButton addConditionButton;
    private javax.swing.JButton addFaultButton;
    private javax.swing.JButton addStatementButton;
    private javax.swing.JMenuItem addStatementMenuItem;
    private javax.swing.JButton addTestButton;
    private javax.swing.JMenuItem addTestMenuItem;
    private javax.swing.JLabel conditionLabel;
    private javax.swing.JPanel conditionPanel;
    private javax.swing.table.DefaultTableModel conditionTableModel;
    private javax.swing.JTable conditionTable;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JLabel faultLabel;
    private javax.swing.JPanel faultPanel;
    private javax.swing.table.DefaultTableModel faultTableModel;
    private javax.swing.JTable faultTable;
    private javax.swing.JMenu fileMenu;
    private javax.swing.table.DefaultTableModel interactionTableModel;
    private javax.swing.JTable interactionTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
   // private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JSplitPane messageSplitPane;
    private javax.swing.JTabbedPane messageTabbedPane;
    private javax.swing.JButton proxySettingsButton;
    private javax.swing.JButton removeConditionButton;
    private javax.swing.JButton removeFaultButton;
    private javax.swing.JMenuItem removeMenuItem;
    private javax.swing.JButton removeNodeButton;
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

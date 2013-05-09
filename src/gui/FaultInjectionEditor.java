package gui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;

import modalWindows.AddConditionDialog;
import modalWindows.AddFaultDialog;
import data.Condition;
import data.DataProvider;
import data.Fault;
import data.FaultInjectionData;

public class FaultInjectionEditor extends JSplitPane {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7371762817016101649L;
	
	private javax.swing.JLabel faultLabel;
    private javax.swing.JPanel faultPanel;
	private javax.swing.JLabel conditionLabel;
    private javax.swing.JPanel conditionPanel;
    private javax.swing.JButton addConditionButton;
    private javax.swing.JButton addFaultButton;
    private javax.swing.JButton removeConditionButton;
    private javax.swing.JButton removeFaultButton;
    private javax.swing.table.DefaultTableModel faultTableModel;
    private javax.swing.table.DefaultTableModel conditionTableModel;
    private javax.swing.JTable conditionTable;
    private javax.swing.JTable faultTable;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private FaultInjectionData editedTest;
    private boolean dataLoaded;
    
   


	public FaultInjectionEditor(){
		
		initComponents();
		setConditionPane();
		setFaultPane();
		setDataLoaded(false);
		
		this.setDividerLocation(250);
		this.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		this.setBottomComponent(faultPanel);
		this.setTopComponent(conditionPanel);
				
		//ziskame referenci na datovy model tabulky podminek
		conditionTableModel= (DefaultTableModel) conditionTable.getModel();
		//ziskame referenci na datovy model tabulky poruch
		faultTableModel= (DefaultTableModel) faultTable.getModel();
	}
	
	
	public FaultInjectionData getData(){
		
		return this.editedTest;
	}
	
	
	
	public void setData(FaultInjectionData data){
		
		this.editedTest = data;
		refreshFaultPanel();
		refreshConditionPanel();
		setDataLoaded(true);
	}
	
	public void setEnablePanel(boolean enable){
		
		addFaultButton.setEnabled(enable);
		addConditionButton.setEnabled(enable);
		removeFaultButton.setEnabled(enable);
		removeConditionButton.setEnabled(enable);
	}
	
	 public boolean isDataLoaded() {
		
		 return dataLoaded;
	 }


	public void setDataLoaded(boolean dataLoaded) {
		
		this.dataLoaded = dataLoaded;
	}
	
	public void clearData(){
		
		clearTable(conditionTableModel);
		clearTable(faultTableModel);
	}
	
	/**
	 * 
	 */
	private void clearTable(DefaultTableModel table){
		
		for(int i = 0; i < table.getRowCount(); i++){
			table.removeRow(i);
		}
	}
	
	
	
	
	
	
	/**
	 * Metoda pro aktualizaci tabulky a tlacitek v sekci podminek v zavislosti na oznacene polozce
	 * v testTree.
	 * @param selectedNode oznacena polozka v testTree
	 */
	private void refreshConditionPanel() {
		
		conditionTableModel.setRowCount(0);
		//conditionTable.setEnabled(true);
		//addConditionButton.setEnabled(true);
		//removeConditionButton.setEnabled(true);
		FaultInjectionData selectedTest= getData();
		
		for (Condition currentCondition : selectedTest.getConditionSet()) {
			//vlozeni noveho radku do tabulky podminek
			Object[] newRow= new Object[] {currentCondition, currentCondition.getDescription()};
			conditionTableModel.insertRow(conditionTable.getRowCount(), newRow);
		}
	}
	
	
	
	/**
	 * Metoda pro aktualizaci tabulky a tlacitek v sekci poruch v zavislosti na oznacene polozce v
	 * testTree.
	 * @param selectedNode oznacena polozka v testTree
	 */
	private void refreshFaultPanel() {
		
		//vymazeme vsechny radky tabulky
		faultTableModel.setRowCount(0);
		//faultTable.setEnabled(true);
		//addFaultButton.setEnabled(true);
		//removeFaultButton.setEnabled(true);
		FaultInjectionData selectedTest= getData();
		
		for (Fault currentFault : selectedTest.getFaultList()) {
			//vlozeni noveho radku do tabulky poruch
			Object[] newRow= new Object[] {currentFault, currentFault.getDescription()};
			faultTableModel.insertRow(faultTable.getRowCount(), newRow);
		}
	}
	

	private void addConditionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addConditionButtonActionPerformed
		
	
		AddConditionDialog addConditionDialog= new AddConditionDialog((JFrame)this.getTopLevelAncestor(), true);
		addConditionDialog.setVisible(true);
		
		if (addConditionDialog.isAddButtonClicked()) {
			
			Condition newCondition= addConditionDialog.getNewCondition();
			//zjistime oznacene pravidlo ve stromu a pridame do jeho kolekce novou podminku
			
			FaultInjectionData selectedTest= getData();
			selectedTest.addToConditionSet(newCondition);
			
			//refresh tabulky podminek
			refreshConditionPanel();
					
		}
	}//GEN-LAST:event_addConditionButtonActionPerformed

	private void addFaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFaultButtonActionPerformed
		
		AddFaultDialog addFaultDialog= new AddFaultDialog((JFrame)this.getTopLevelAncestor(), true);
		addFaultDialog.setVisible(true);
		
		if (addFaultDialog.isAddButtonClicked()) {
			
			Fault newFault= addFaultDialog.getNewFault();
			
			//zjistime oznacene pravidlo ve stromu a pridame do jeho kolekce novou poruchu
			FaultInjectionData selectedTest=  getData();
			selectedTest.addToFaultList(newFault);
			
			//refresh tabulky poruch
			refreshFaultPanel();
		}
	}//GEN-LAST:event_addFaultButtonActionPerformed

	private void removeConditionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeConditionButtonActionPerformed
		
		int selectedRow= conditionTable.getSelectedRow();
		if (selectedRow == -1)
			return;
		
		Condition selectedCondition= (Condition) conditionTableModel.getValueAt(selectedRow, 0);
		FaultInjectionData selectedTest=  getData();
		
		//odstranime podminku jak z tabulky, tak z kolekce
		selectedTest.removeFromConditionSet(selectedCondition);
		conditionTableModel.removeRow(selectedRow);
	
	}
	private void removeFaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFaultButtonActionPerformed
		
		int selectedRow= faultTable.getSelectedRow();
		if (selectedRow == -1)
			return;
		
		Fault selectedFault= (Fault) faultTableModel.getValueAt(selectedRow, 0);
		
		//zjistime, ktera porucha ve stromu je oznacena
		FaultInjectionData selectedTest= getData();
		
		//odstranime poruchu jak z tabulky, tak z kolekce
		selectedTest.removeFromFaultList(selectedFault);
		faultTableModel.removeRow(selectedRow);
	}

	private void initComponents(){
		
		conditionPanel = new javax.swing.JPanel();
        conditionLabel = new javax.swing.JLabel();
        
        faultPanel = new javax.swing.JPanel();
        faultLabel = new javax.swing.JLabel();
        
        addConditionButton = new javax.swing.JButton();
        addFaultButton = new javax.swing.JButton();
        
        removeFaultButton = new javax.swing.JButton();
        removeConditionButton = new javax.swing.JButton();
        
        conditionTable = new javax.swing.JTable();
        faultTable = new javax.swing.JTable();
        
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        
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
       
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
        jScrollPane3.setViewportView(conditionTable);
        conditionTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        conditionTable.getColumnModel().getColumn(1).setPreferredWidth(500);
        addConditionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"add_small.png"))); // NOI18N
        addConditionButton.setText("Add");
        addConditionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addConditionButtonActionPerformed(evt);
            }
        });

        removeConditionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"remove_small.png"))); // NOI18N
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
            /**
			 * 
			 */
			private static final long serialVersionUID = 9137638585465984115L;
			boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
       
		jScrollPane4.setViewportView(faultTable);
        faultTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        faultTable.getColumnModel().getColumn(1).setPreferredWidth(500);

        addFaultButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"add_small.png"))); // NOI18N
        addFaultButton.setText("Add");
        addFaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFaultButtonActionPerformed(evt);
            }
        });

        removeFaultButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(DataProvider.getResourcePath()+"remove_small.png"))); // NOI18N
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

}

package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.PlainDocument;

import modalWindows.AntiAliasedEditorPane;

import org.bounce.text.LineNumberMargin;
import org.bounce.text.xml.XMLEditorKit;

import proxyUnit.Controller;
import proxyUnit.HttpInteraction;
import proxyUnit.HttpRequest;
import proxyUnit.HttpResponse;
import proxyUnit.NewMessageListener;
import proxyUnit.Test;
import proxyUnit.UnknownHostListener;

public class ProxyMonitor extends JSplitPane implements NewMessageListener, UnknownHostListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7672468377133931212L;
	//private javax.swing.JButton addConditionButton;
    //private javax.swing.JButton addFaultButton;
   // private javax.swing.JButton addStatementButton;
    //private javax.swing.JMenuItem addStatementMenuItem;
   // private javax.swing.JButton addTestButton;
    //private javax.swing.JMenuItem addTestMenuItem;
   // private javax.swing.JLabel conditionLabel;
   // private javax.swing.JPanel conditionPanel;
  //  private javax.swing.table.DefaultTableModel conditionTableModel;
   /// private javax.swing.JTable conditionTable;
   // private javax.swing.JMenuItem exitMenuItem;
   // private javax.swing.JLabel faultLabel;
    //private javax.swing.JPanel faultPanel;
  //  private javax.swing.table.DefaultTableModel faultTableModel;
   // private javax.swing.JTable faultTable;
   // private javax.swing.JMenu fileMenu;
    private javax.swing.table.DefaultTableModel interactionTableModel;
    private javax.swing.JTable interactionTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
   // private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
   // private javax.swing.JScrollPane jScrollPane3;
    //private javax.swing.JScrollPane jScrollPane4;
   // private javax.swing.JToolBar.Separator jSeparator1;
   // private javax.swing.JToolBar.Separator jSeparator2;
    //private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JSplitPane messageSplitPane;
    private javax.swing.JTabbedPane messageTabbedPane;
    //private javax.swing.JButton proxySettingsButton;
   // private javax.swing.JButton removeConditionButton;
   // private javax.swing.JButton removeFaultButton;
   // private javax.swing.JMenuItem removeMenuItem;
   // private javax.swing.JButton removeNodeButton;
   /// private javax.swing.JMenuItem renameMenuItem;
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
   // private javax.swing.JMenu settingsMenu;
   /// private javax.swing.JMenuItem settingsMenuItem;
    //private javax.swing.JToggleButton startProxyToggleButton;
    //private javax.swing.JToggleButton startTestToggleButton;
    //private javax.swing.JSplitPane statementDetailSplitPane;
   // private javax.swing.DefaultComboBoxModel testComboBoxModel;
   // private javax.swing.JComboBox testComboBox;
   // private javax.swing.JScrollPane testScrollPane;
    //private javax.swing.JSplitPane testSplitPane;
   // private javax.swing.JToolBar testToolBar;
    //private javax.swing.tree.DefaultTreeModel testTreeModel;
   // private javax.swing.tree.DefaultTreeCellRenderer testTreeRenderer;
    //private javax.swing.JTree testTree;
   // private javax.swing.JPopupMenu testTreePopupMenu;
  //  private javax.swing.JMenuBar topMenuBar;
 //   private javax.swing.JToolBar topToolBar;
    
    
    private Controller controller;
	private InteractionTablePopupListener interactionTablePopupListener;
	//private TestTreePopupListener testTreePopupListener;
		
    
    public ProxyMonitor(){
    	controller= new Controller();
		//zaregistrujeme se jako odberatel udalosti o novych zpravach z business vrstvy
		controller.addNewMessageListener(this);
		controller.addUnknownHostListener(this);
		
		interactionTablePopupListener= new InteractionTablePopupListener();
		//testTreePopupListener= new TestTreePopupListener();
				
		initComponents();
		
		//pripravime testComboBox
		//initTestComboBox();
		
		//pripravime datovy model pro testTree komponentu
		//initTestTreeModel();
		//ziskame referenci na datovy model interakcni tabulky
		interactionTableModel= (DefaultTableModel) interactionTable.getModel();
		//ziskame referenci na datovy model tabulky podminek
		//conditionTableModel= (DefaultTableModel) conditionTable.getModel();
		//ziskame referenci na datovy model tabulky poruch
		//faultTableModel= (DefaultTableModel) faultTable.getModel();
		
    }
    
    /**
	 * Metoda pro aktualizaci nabidky testu v komponente testComboBox.
	 */
//	private void initTestComboBox() {
//		
//		testComboBoxModel= new DefaultComboBoxModel();
//		List<Test> testList= controller.getTestList();
//		for (Test currentTest : testList) {
//			testComboBoxModel.addElement(currentTest);
//		}
//		testComboBox.setModel(testComboBoxModel);
//			
//	}
	
	
	/**
	 * Obsluha udalost pri zachyceni nove zpravy vychazejici z navrhoveho vzoru observer.
	 */
	@Override
	public void onUnknownHostEvent() {
		
		JOptionPane.showMessageDialog(this, "Specified unknown host.",
				"Proxy stopped", JOptionPane.WARNING_MESSAGE);
		//startProxyToggleButton.setSelected(false);
		//proxySettingsButton.setEnabled(true);
	}
	
	/**
	 * Obsluha udalosti pri zachyceni nove zpravy (zobrazeni v tabulce interakci + obsah zpravy) vychazejici
	 * z navrhoveho vzoru Observer.
	 * @param interactionId id interakce
	 * @param interaction objekt interakce
	 */
	@Override
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) {
		
		//hledame v tabulce, zda tam jiz prislusna interakce neni..pokud ano, pridame nove informace
		boolean interactionInserted= false;
		for (int i= 0; i < interactionTableModel.getRowCount(); i++)
			if ((Integer)interactionId == interactionTableModel.getValueAt(i, 0)) {
				setValueAtInteractionTable(i, interactionId, interaction);
				interactionInserted= true;
				break;
			}
		
		//pokud interakce nebyla nalezena, vytvorime novy radek
		if (!interactionInserted)
			insertRowInteractionTable(interactionId, interaction);
		
	}
	
	/**
	 * Metoda pro editovani radku do tabulky interakci.
	 * @param row cislo radku
	 * @param interactionId id interakce
	 * @param interaction objekt interakce
	 */
	private void setValueAtInteractionTable(int row, int interactionId, HttpInteraction interaction) {
		
		HttpRequest httpRequest= interaction.getHttpRequest();
		HttpResponse httpResponse= interaction.getHttpResponse();
		
		//vlozeni cisla interakce
		interactionTableModel.setValueAt(interactionId, row, 0);
		
		//HTTP REQUEST
		if (httpRequest != null) {
			//vlozeni http metody
			interactionTableModel.setValueAt(httpRequest.getHttpMethod(), row, 2);
			//vlozeni iniciatora komunikace
			interactionTableModel.setValueAt(httpRequest.getInitiator(), row, 3);
			//vlozeni URI
			interactionTableModel.setValueAt(httpRequest.getUri(), row, 4);
		}
		
		//HTTP RESPONSE
		//vlozeni http kodu
		if (httpResponse != null)
			interactionTableModel.setValueAt(httpResponse.getHttpCode() + " " + httpResponse.getHttpCodeDesc(),
					row, 1);
		
		
	}
	
	
	/**
	 * Metoda pro vlozeni noveho radku do tabulky interakci.
	 * @param interactionId id interakce
	 * @param interaction objekt interakce
	 */
	private void insertRowInteractionTable(int interactionId, HttpInteraction interaction) {
		
		HttpRequest httpRequest= interaction.getHttpRequest();
		HttpResponse httpResponse= interaction.getHttpResponse();
		
		//zapiseme do tabulky pouzity test
		Test activeTest= controller.getActiveTest();
		String usedTest= "None";
		if (activeTest != null)
			usedTest= activeTest.getTestName();
		
		//HTTP REQUEST
		if (httpRequest != null) {
			Object[] newRow= new Object[] {interactionId, null, httpRequest.getHttpMethod(),
					httpRequest.getInitiator(), httpRequest.getUri(), usedTest};
			interactionTableModel.insertRow(interactionTable.getRowCount(), newRow);
		}
		
		//HTTP RESPONSE
		else {
			Object[] newRow= new Object[] {interactionId, httpResponse.getHttpCode() + " " + 
					httpResponse.getHttpCodeDesc(), null, null, null, usedTest};
			interactionTableModel.insertRow(interactionTable.getRowCount(), newRow);
		}
		
		
	}
	
	
	/**
	 * Metoda k overeni, zda neni oznacen bezici test nebo pravidlo. Pokud se uzivatel pokusi modifikovat
	 * bezici test, bude mu v tom zabraneno dialogem.
	 * @return true, pokud 
	 */
//	private boolean isRunningTestOrStatementSelected() {
//		
//		//ziskame prave bezici test
//		Test activeTest= controller.getActiveTest();
//		if (activeTest == null)
//			return false;
//		DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
//		Object selectedObject= selectedNode.getUserObject();
//		//pokud je oznacen test...zjistime zda nejde o stejnou instanci jako aktivni test
//		if (selectedObject instanceof Test) {
//			return activeTest.equals((Test) selectedObject);
//		}
//		//pokud je oznaceno pravidlo..zjistime, zda nepatri do aktivniho testu
//		if (selectedObject instanceof TestStatement) {
//			DefaultMutableTreeNode rootNode= (DefaultMutableTreeNode) selectedNode.getParent();
//			Test parentTest= (Test) rootNode.getUserObject();
//			return activeTest.equals(parentTest);
//		}
//		//jinak je oznacen korenovy uzel
//		return false;
//	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

    
      //  proxySettingsButton = new javax.swing.JButton();
        // mainTabbedPane = new javax.swing.JTabbedPane();
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
       // jPanel8 = new javax.swing.JPanel();
       // testToolBar = new javax.swing.JToolBar();
        //addTestButton = new javax.swing.JButton();
       // addStatementButton = new javax.swing.JButton();
       // jSeparator2 = new javax.swing.JToolBar.Separator();
        //removeNodeButton = new javax.swing.JButton();
       // testScrollPane = new javax.swing.JScrollPane();
       // testTree = new javax.swing.JTree();
        //statementDetailSplitPane = new javax.swing.JSplitPane();
       // conditionPanel = new javax.swing.JPanel();
        //conditionLabel = new javax.swing.JLabel();
       // jScrollPane3 = new javax.swing.JScrollPane();
       // conditionTable = new javax.swing.JTable();
        //addConditionButton = new javax.swing.JButton();
      //  removeConditionButton = new javax.swing.JButton();
       // faultPanel = new javax.swing.JPanel();
       // faultLabel = new javax.swing.JLabel();
       // jScrollPane4 = new javax.swing.JScrollPane();
      //  faultTable = new javax.swing.JTable();
       // addFaultButton = new javax.swing.JButton();
      //  removeFaultButton = new javax.swing.JButton();
       
       // messageSplitPane.setDividerLocation(200);
       // messageSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        this.setDividerLocation(200);
        this.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        interactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Http code", "Method", "Initiator", "URI", "Test"
            }
        ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 7932709950895630069L;
			boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        interactionTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        interactionTable.setShowHorizontalLines(false);
        interactionTable.setShowVerticalLines(false);
        interactionTable.addMouseListener(interactionTablePopupListener);
        jScrollPane1.setViewportView(interactionTable);
        interactionTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        interactionTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        interactionTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        interactionTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        interactionTable.getColumnModel().getColumn(4).setPreferredWidth(400);
        interactionTable.getColumnModel().getColumn(5).setPreferredWidth(100);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 861, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        //messageSplitPane.setTopComponent(jPanel1);
        this.setTopComponent(jPanel1);
        reqSplitPane.setDividerLocation(430);

        reqOriginalLabel.setText("Original message:");

        reqOriginalEditorPane.setEditable(false);
        reqOriginalScrollPane.setViewportView(reqOriginalEditorPane);
        MainWindow.initEditorPane(reqOriginalEditorPane, reqOriginalScrollPane);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reqOriginalScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addComponent(reqOriginalLabel))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reqOriginalLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reqOriginalScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        reqSplitPane.setLeftComponent(jPanel2);

        reqChangedLabel.setText("Changed message:");

        reqChangedEditorPane.setEditable(false);
        reqChangedScrollPane.setViewportView(reqChangedEditorPane);
        MainWindow.initEditorPane(reqChangedEditorPane, reqChangedScrollPane);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reqChangedScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addComponent(reqChangedLabel))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reqChangedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reqChangedScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        reqSplitPane.setRightComponent(jPanel3);

        messageTabbedPane.addTab("Request", reqSplitPane);

        resSplitPane.setDividerLocation(430);

        resOriginalLabel.setText("Original message:");

        resOriginalEditorPane.setEditable(false);
        resOriginalScrollPane.setViewportView(resOriginalEditorPane);
        MainWindow.initEditorPane(resOriginalEditorPane, resOriginalScrollPane);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resOriginalScrollPane)
                    .addComponent(resOriginalLabel))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resOriginalLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resOriginalScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        resSplitPane.setLeftComponent(jPanel5);

        resChangedLabel.setText("Changed message:");

        resChangedEditorPane.setEditable(false);
        resChangedScrollPane.setViewportView(resChangedEditorPane);
        MainWindow.initEditorPane(resChangedEditorPane, resChangedScrollPane);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resChangedScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addComponent(resChangedLabel))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resChangedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resChangedScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );

        resSplitPane.setRightComponent(jPanel6);

        messageTabbedPane.addTab("Response", resSplitPane);
        this.setBottomComponent(messageTabbedPane);

        //mainTabbedPane.addTab("Service communication", messageSplitPane);

        //mainTabbedPane.addTab("Service tests", testSplitPane);

        //this.add(messageSplitPane);
       
    }// </editor-fold>//GEN-END:initComponents
	
	
	
	
	
	//<editor-fold defaultstate="collapsed" desc="InteractionTablePopupListener class">
		/**
		 * Pomocna vnorena trida reprezentujici obsluhu udalosti pri kliknuti do tabulky interakci.
		 * Pri kliknuti pravym tlacitkem se krome oznaceni radku zobrazi prislusne kontextove menu.
		 */
		public class InteractionTablePopupListener extends MouseAdapter {

			
			private int lastSelectedInteraction= -1;
			
			
			@Override
			public void mousePressed(MouseEvent me) {
				clickAction(me);
				
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					clickAction(me);
				
			}
			
			private void clickAction(MouseEvent me) {
				
				//zjistime id interakce, na kterou bylo kliknuto
				Point point= me.getPoint();
				int row= interactionTable.rowAtPoint(point);
				int interactionId= (Integer) interactionTable.getValueAt(row, 0);
				
				//pokud nebylo znovu kliknuto na stejnou interakci..
				if (interactionId != lastSelectedInteraction) {
					//zobrazime obsah http zprav do komponent tridy JEditorPane
					lastSelectedInteraction= interactionId;
					HttpInteraction selectedInteraction= controller.getInteractionMap().get(interactionId);
					HttpRequest httpRequest= selectedInteraction.getHttpRequest();
					HttpResponse httpResponse= selectedInteraction.getHttpResponse();
					if (httpRequest != null) {
						reqOriginalEditorPane.setText(httpRequest.getHttpHeader() + "\n\n" +
								httpRequest.getFormattedContent());
						//nechceme odscrollovat dolu
						reqOriginalEditorPane.setCaretPosition(0);
						
						reqChangedEditorPane.setText(httpRequest.getChangedHttpHeader() + "\n\n" + 
								httpRequest.getChangedFormattedContent());
						//nechceme odscrollovat dolu
						reqChangedEditorPane.setCaretPosition(0);
					}
					if (httpResponse != null) {
						resOriginalEditorPane.setText(httpResponse.getHttpHeader() + "\n\n" +
								httpResponse.getFormattedContent());
						//nechceme odscrollovat dolu
						resOriginalEditorPane.setCaretPosition(0);
						
						resChangedEditorPane.setText(httpResponse.getChangedHttpHeader() + "\n\n" +
								httpResponse.getChangedFormattedContent());
						//nechceme odscrollovat dolu
						resChangedEditorPane.setCaretPosition(0);
					}
				}
				
			}
			
			
		}
}


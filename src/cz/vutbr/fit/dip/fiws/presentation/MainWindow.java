/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 10.2.2012
 */


package cz.vutbr.fit.dip.fiws.presentation;


import cz.vutbr.fit.dip.fiws.business.Condition;
import cz.vutbr.fit.dip.fiws.business.Controller;
import cz.vutbr.fit.dip.fiws.business.Fault;
import cz.vutbr.fit.dip.fiws.business.HttpInteraction;
import cz.vutbr.fit.dip.fiws.business.HttpRequest;
import cz.vutbr.fit.dip.fiws.business.HttpResponse;
import cz.vutbr.fit.dip.fiws.business.NewMessageListener;
import cz.vutbr.fit.dip.fiws.business.Test;
import cz.vutbr.fit.dip.fiws.business.TestStatement;
import cz.vutbr.fit.dip.fiws.business.UnknownHostListener;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.bounce.text.LineNumberMargin;
import org.bounce.text.xml.XMLEditorKit;





/**
 * Hlavni okno aplikace.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class MainWindow extends javax.swing.JFrame implements NewMessageListener, UnknownHostListener {
	
	
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
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="TestTreePopupListener class">
	/**
	 * Pomocna vnorena trida pro zachyceni kliknuti mysi do stromove struktury s testy. Zajistuje i pripadne
	 * vyvolani kontextoveho menu pri stisku praveho tlacitka. Rozsiruje abstraktni tridu MouseAdapter a
	 * implementuje jeji metody mousePressed a mouseReleased, pricemz zavisi na konkretnim operacnim systemu
	 * pri ktere z nich se detekuji PopupTrigger.
	 */
	public class TestTreePopupListener extends MouseAdapter {
		
		
		
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
			
			int xCoords= me.getX();
			int yCoords= me.getY();

			//zjistime na ktery uzel stromu uzivatel kliknul
			TreePath path= testTree.getPathForLocation(xCoords, yCoords);
			if (path == null)
				return;
			//nastavit oznaceni
			testTree.setSelectionPath(path);
			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
			//refresh tabulek na zaklade noveho oznaceni
			refreshConditionPanel(selectedNode);
			refreshFaultPanel(selectedNode);
			
			Object selectedObject= selectedNode.getUserObject();
			//pokud je oznacen root uzel..znepristupnime tlacitka v hornim panelu
			if (selectedObject instanceof String) {
				addStatementButton.setEnabled(false);
				removeNodeButton.setEnabled(false);
			}
			else {
				addStatementButton.setEnabled(true);
				removeNodeButton.setEnabled(true);
			}
			
			//pokud bylo stisknuto prave tlacitko..zobrazime kontextove menu
			if (me.isPopupTrigger()) {
				//pokud bylo kliknuto na test..
				if (selectedNode.getUserObject() instanceof Test) {
					addTestMenuItem.setEnabled(false);
					addStatementMenuItem.setEnabled(true);
					renameMenuItem.setEnabled(true);
					removeMenuItem.setEnabled(true);
				}
				else
					//pokud bylo kliknuto na pravidlo..
					if (selectedNode.getUserObject() instanceof TestStatement) {
						addTestMenuItem.setEnabled(false);
						addStatementMenuItem.setEnabled(false);
						renameMenuItem.setEnabled(true);
						removeMenuItem.setEnabled(true);
					}
					//pokud bylo kliknuto na korenovy uzel
					else {
						addTestMenuItem.setEnabled(true);
						addStatementMenuItem.setEnabled(false);
						renameMenuItem.setEnabled(false);
						removeMenuItem.setEnabled(false);
					}
						
				testTreePopupMenu.show(me.getComponent(), xCoords, yCoords);
			}
		}
		
	}
	//</editor-fold>
	
	
	
	private Controller controller;
	private InteractionTablePopupListener interactionTablePopupListener;
	private TestTreePopupListener testTreePopupListener;
		

	/** Creates new form MainWindow */
	public MainWindow() {
		
		controller= new Controller();
		//zaregistrujeme se jako odberatel udalosti o novych zpravach z business vrstvy
		controller.addNewMessageListener(this);
		controller.addUnknownHostListener(this);
		
		interactionTablePopupListener= new InteractionTablePopupListener();
		testTreePopupListener= new TestTreePopupListener();
				
		initComponents();
		
		//pripravime testComboBox
		initTestComboBox();
		
		//pripravime datovy model pro testTree komponentu
		initTestTreeModel();
		//ziskame referenci na datovy model interakcni tabulky
		interactionTableModel= (DefaultTableModel) interactionTable.getModel();
		//ziskame referenci na datovy model tabulky podminek
		conditionTableModel= (DefaultTableModel) conditionTable.getModel();
		//ziskame referenci na datovy model tabulky poruch
		faultTableModel= (DefaultTableModel) faultTable.getModel();
		
		
	}
	
	private void initTestTreeModel() {
		
		List<Test> testList= controller.getTestList();
		DefaultMutableTreeNode topNode= new DefaultMutableTreeNode("Tests");
		
		//nacteme vsechny testy a pravidla z kolekci a vytvorime prislusny hierarchicky strom
		for (Test currentTest : testList) {
			DefaultMutableTreeNode testNode= new DefaultMutableTreeNode(currentTest);
			topNode.add(testNode);
			
			List<TestStatement> statementList= currentTest.getStatementList();
			for (TestStatement currentStatement : statementList) {
				DefaultMutableTreeNode statementNode= new DefaultMutableTreeNode(currentStatement);
				testNode.add(statementNode);
				
			}
		}
		//vytvorime novou instanci tree modelu
		testTreeModel= new DefaultTreeModel(topNode);
		testTree.setModel(testTreeModel);
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
	 * Obsluha udalost pri zachyceni nove zpravy vychazejici z navrhoveho vzoru observer.
	 */
	@Override
	public void onUnknownHostEvent() {
		
		JOptionPane.showMessageDialog(this, "Specified unknown host.",
				"Proxy stopped", JOptionPane.WARNING_MESSAGE);
		startProxyToggleButton.setSelected(false);
		proxySettingsButton.setEnabled(true);
	}
	
		
	
	/**
	 * Metoda pro inicializaci komponenty tridy JEditorPane pro zobrazovani XML zprav.
	 */
	protected static void initEditorPane(JEditorPane currentEditorPane, JScrollPane currentScrollPane) {
		
		//pouziti bounce editor kitu pro zvyrazneni syntaxe XML
		XMLEditorKit xmlKit= new XMLEditorKit();
		currentEditorPane.setEditorKit(xmlKit);
		
		//nastaveni fontu
		currentEditorPane.setFont(new Font("Courier", Font.PLAIN, 12));
		//nastaveni sirky tabulatoru
		currentEditorPane.getDocument().putProperty(PlainDocument.tabSizeAttribute, new Integer(1));

		//zobrazeni postranniho panelu s cisly radku
		JPanel rowHeader= new JPanel(new BorderLayout());
		//rowHeader.add(new XMLFoldingMargin(reqOriginalEditorPane), BorderLayout.EAST);
		rowHeader.add(new LineNumberMargin(currentEditorPane), BorderLayout.WEST);
		currentScrollPane.setRowHeaderView(rowHeader);
		
	}
	
	/**
	 * Metoda pro aktualizaci nabidky testu v komponente testComboBox.
	 */
	private void initTestComboBox() {
		
		testComboBoxModel= new DefaultComboBoxModel();
		List<Test> testList= controller.getTestList();
		for (Test currentTest : testList) {
			testComboBoxModel.addElement(currentTest);
		}
		testComboBox.setModel(testComboBoxModel);
			
	}
	
	/**
	 * Metoda k overeni, zda neni oznacen bezici test nebo pravidlo. Pokud se uzivatel pokusi modifikovat
	 * bezici test, bude mu v tom zabraneno dialogem.
	 * @return true, pokud 
	 */
	private boolean isRunningTestOrStatementSelected() {
		
		//ziskame prave bezici test
		Test activeTest= controller.getActiveTest();
		if (activeTest == null)
			return false;
		DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
		Object selectedObject= selectedNode.getUserObject();
		//pokud je oznacen test...zjistime zda nejde o stejnou instanci jako aktivni test
		if (selectedObject instanceof Test) {
			return activeTest.equals((Test) selectedObject);
		}
		//pokud je oznaceno pravidlo..zjistime, zda nepatri do aktivniho testu
		if (selectedObject instanceof TestStatement) {
			DefaultMutableTreeNode rootNode= (DefaultMutableTreeNode) selectedNode.getParent();
			Test parentTest= (Test) rootNode.getUserObject();
			return activeTest.equals(parentTest);
		}
		//jinak je oznacen korenovy uzel
		return false;
	}
	

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        testSplitPane = new javax.swing.JSplitPane();
        jPanel8 = new javax.swing.JPanel();
        testToolBar = new javax.swing.JToolBar();
        addTestButton = new javax.swing.JButton();
        addStatementButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        removeNodeButton = new javax.swing.JButton();
        testScrollPane = new javax.swing.JScrollPane();
        testTree = new javax.swing.JTree();
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

        addTestMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/add_test_small.png"))); // NOI18N
        addTestMenuItem.setText("Add test..");
        addTestMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTestMenuItemActionPerformed(evt);
            }
        });
        testTreePopupMenu.add(addTestMenuItem);

        addStatementMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/add_statement_small.png"))); // NOI18N
        addStatementMenuItem.setText("Add statement..");
        addStatementMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStatementMenuItemActionPerformed(evt);
            }
        });
        testTreePopupMenu.add(addStatementMenuItem);

        renameMenuItem.setText("Rename..");
        renameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameMenuItemActionPerformed(evt);
            }
        });
        testTreePopupMenu.add(renameMenuItem);

        removeMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/remove_small.png"))); // NOI18N
        removeMenuItem.setText("Remove");
        removeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMenuItemActionPerformed(evt);
            }
        });
        testTreePopupMenu.add(removeMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fault Injector for Web Services");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        topToolBar.setFloatable(false);
        topToolBar.setRollover(true);

        testComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        testComboBox.setMaximumSize(new java.awt.Dimension(200, 32767));
        topToolBar.add(testComboBox);

        startTestToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/start_inject_small.png"))); // NOI18N
        startTestToggleButton.setToolTipText("Run test");
        startTestToggleButton.setFocusable(false);
        startTestToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startTestToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        startTestToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTestToggleButtonActionPerformed(evt);
            }
        });
        topToolBar.add(startTestToggleButton);
        topToolBar.add(jSeparator1);

        proxySettingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/proxy_settings_small.png"))); // NOI18N
        proxySettingsButton.setToolTipText("Proxy settings");
        proxySettingsButton.setFocusable(false);
        proxySettingsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        proxySettingsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        proxySettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proxySettingsButtonActionPerformed(evt);
            }
        });
        topToolBar.add(proxySettingsButton);

        startProxyToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/start_proxy_small.png"))); // NOI18N
        startProxyToggleButton.setToolTipText("Run proxy");
        startProxyToggleButton.setFocusable(false);
        startProxyToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startProxyToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        startProxyToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startProxyToggleButtonActionPerformed(evt);
            }
        });
        topToolBar.add(startProxyToggleButton);

        messageSplitPane.setDividerLocation(200);
        messageSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        interactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Http code", "Method", "Initiator", "URI", "Test"
            }
        ) {
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

        messageSplitPane.setTopComponent(jPanel1);

        reqSplitPane.setDividerLocation(430);

        reqOriginalLabel.setText("Original message:");

        reqOriginalEditorPane.setEditable(false);
        reqOriginalScrollPane.setViewportView(reqOriginalEditorPane);
        initEditorPane(reqOriginalEditorPane, reqOriginalScrollPane);

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
        initEditorPane(reqChangedEditorPane, reqChangedScrollPane);

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
        initEditorPane(resOriginalEditorPane, resOriginalScrollPane);

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
        initEditorPane(resChangedEditorPane, resChangedScrollPane);

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

        messageSplitPane.setRightComponent(messageTabbedPane);

        mainTabbedPane.addTab("Service communication", messageSplitPane);

        testSplitPane.setDividerLocation(220);

        testToolBar.setFloatable(false);
        testToolBar.setRollover(true);

        addTestButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/add_test_small.png"))); // NOI18N
        addTestButton.setToolTipText("Add new test");
        addTestButton.setFocusable(false);
        addTestButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addTestButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTestButtonActionPerformed(evt);
            }
        });
        testToolBar.add(addTestButton);

        addStatementButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/add_statement_small.png"))); // NOI18N
        addStatementButton.setToolTipText("Add new statement");
        addStatementButton.setFocusable(false);
        addStatementButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addStatementButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addStatementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStatementButtonActionPerformed(evt);
            }
        });
        testToolBar.add(addStatementButton);
        testToolBar.add(jSeparator2);

        removeNodeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/remove_small.png"))); // NOI18N
        removeNodeButton.setToolTipText("Remove");
        removeNodeButton.setFocusable(false);
        removeNodeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeNodeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeNodeButtonActionPerformed(evt);
            }
        });
        testToolBar.add(removeNodeButton);

        testScrollPane.setViewportView(testTree);
        testTree.addMouseListener(testTreePopupListener);
        //nastaveni vykresleni ikon v JTree
        testTreeRenderer= new TestTreeCellRenderer();
        testTree.setCellRenderer(testTreeRenderer);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(testToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
            .addComponent(testScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(testToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
        );

        testSplitPane.setLeftComponent(jPanel8);

        statementDetailSplitPane.setDividerLocation(250);
        statementDetailSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

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

        addConditionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/add_small.png"))); // NOI18N
        addConditionButton.setText("Add");
        addConditionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addConditionButtonActionPerformed(evt);
            }
        });

        removeConditionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/remove_small.png"))); // NOI18N
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

        statementDetailSplitPane.setTopComponent(conditionPanel);

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

        addFaultButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/add_small.png"))); // NOI18N
        addFaultButton.setText("Add");
        addFaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFaultButtonActionPerformed(evt);
            }
        });

        removeFaultButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/vutbr/fit/dip/fiws/presentation/resources/remove_small.png"))); // NOI18N
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

        statementDetailSplitPane.setRightComponent(faultPanel);

        testSplitPane.setRightComponent(statementDetailSplitPane);

        mainTabbedPane.addTab("Service tests", testSplitPane);

        topMenuBar.setFont(new java.awt.Font("Dialog", 0, 12));

        fileMenu.setText("File");

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        topMenuBar.add(fileMenu);

        settingsMenu.setText("Settings");

        settingsMenuItem.setText("Proxy settings..");
        settingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsMenuItemActionPerformed(evt);
            }
        });
        settingsMenu.add(settingsMenuItem);

        topMenuBar.add(settingsMenu);

        setJMenuBar(topMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(topToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void startProxyToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startProxyToggleButtonActionPerformed
		
		if (startProxyToggleButton.isSelected()) {
			controller.startProxy();
			proxySettingsButton.setEnabled(false);
		}
		else {
			controller.stopProxy();
			proxySettingsButton.setEnabled(true);
		}
	}//GEN-LAST:event_startProxyToggleButtonActionPerformed

	private void addConditionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addConditionButtonActionPerformed
		
		//zabraneni modifikace prave beziciho testu
		if (isRunningTestOrStatementSelected()) {
			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//zobrazime novy dialog pro pridani podminky
		AddConditionDialog addConditionDialog= new AddConditionDialog(this, true, controller.getNewConditionId());
		addConditionDialog.setVisible(true);
		
		//pokud bylo stisknuto tlacitko "pridat", prevezmeme nove vzniklou podminku z dialogu a zaradime ji
		// do prislusne kolekce
		if (addConditionDialog.isAddButtonClicked()) {
			Condition newCondition= addConditionDialog.getNewCondition();
			//zjistime oznacene pravidlo ve stromu a pridame do jeho kolekce novou podminku
			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
			TestStatement selectedStatement= (TestStatement) selectedNode.getUserObject();
			selectedStatement.addToConditionSet(newCondition);
			//refresh tabulky podminek
			refreshConditionPanel(selectedNode);
					
		}
	}//GEN-LAST:event_addConditionButtonActionPerformed

	private void addFaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFaultButtonActionPerformed
		
		//zabraneni modifikace prave beziciho testu
		if (isRunningTestOrStatementSelected()) {
			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//zobrazime novy dialog pro pridani poruchy
		AddFaultDialog addFaultDialog= new AddFaultDialog(this, true, controller.getNewFaultId());
		addFaultDialog.setVisible(true);
		
		//pokud bylo stisknuto tlacitko "pridat", prevezmeme nove vzniklou poruchu z dialogu a zaradime ji
		//do prislusne kolekce
		if (addFaultDialog.isAddButtonClicked()) {
			Fault newFault= addFaultDialog.getNewFault();
			//zjistime oznacene pravidlo ve stromu a pridame do jeho kolekce novou poruchu
			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
			TestStatement selectedStatement= (TestStatement) selectedNode.getUserObject();
			selectedStatement.addToFaultList(newFault);
			//refresh tabulky poruch
			refreshFaultPanel(selectedNode);
		}
	}//GEN-LAST:event_addFaultButtonActionPerformed

	private void addTestMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTestMenuItemActionPerformed
		
		//zobrazime dialog pro pridani noveho testu
		AddTestDialog addTestDialog= new AddTestDialog(this, true, controller.getNewTestId());
		addTestDialog.setVisible(true);
		
		//pokud bylo stisknuto "ok", zaradime novy test do kolekce
		if (addTestDialog.isOkButtonClicked()) {
			Test newTest= addTestDialog.getNewTest();
			controller.addToTestList(newTest);
			
			//vlozime novy uzel do stromu na prislusne misto
			DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newTest);
			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
			testTreeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
			//rozbal strom tak, aby novy uzel sel videt
			testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
			
			//aktualizace testComboBoxu
			testComboBoxModel.addElement(newTest);
		}
		
	}//GEN-LAST:event_addTestMenuItemActionPerformed

	private void addStatementMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStatementMenuItemActionPerformed
		
		//zabraneni modifikace prave beziciho testu
		if (isRunningTestOrStatementSelected()) {
			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//zobrazime dialog pro pridani noveho pravidla
		AddStatementDialog addStatementDialog= new AddStatementDialog(this, true, controller.getNewTestStatementId());
		addStatementDialog.setVisible(true);
		
		//pokud bylo stisknuto "ok", zaradime nove pravidlo do kolekce
		if (addStatementDialog.isOkButtonClicked()) {
			TestStatement newStatement= addStatementDialog.getNewStatement();
			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
			Test selectedTest= (Test) selectedNode.getUserObject();
			selectedTest.addToStatementList(newStatement);
			
			//vlozime novy uzel do stromu na prislusne misto
			DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newStatement);
			testTreeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
			//rozbal strom tak, aby novy uzel sel videt
			testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
		}
		
	}//GEN-LAST:event_addStatementMenuItemActionPerformed

	private void renameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameMenuItemActionPerformed
		
		//zabraneni modifikace prave beziciho testu
		if (isRunningTestOrStatementSelected()) {
			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//zobrazime dialog pro prejmenovani uzlu
		RenameDialog renameDialog= new RenameDialog(this, true);
		renameDialog.setVisible(true);
		
		//pokud bylo stisknuto "ok", prejmenujeme oznaceny uzel
		if (renameDialog.isOkButtonClicked()) {
			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
			Object selectedObject= selectedNode.getUserObject();
			//pokud bylo kliknuto na test..
			if (selectedObject instanceof Test) {
				Test selectedTest= (Test) selectedObject;
				selectedTest.setTestName(renameDialog.getNewName());
				//aktualizace testComboBoxu
				testComboBox.repaint();
			}
			//jinak bylo kliknuto na pravidlo..
			else {
				TestStatement selectedStatement= (TestStatement) selectedObject;
				selectedStatement.setStatementName(renameDialog.getNewName());
			}
			//publikovani zmen v datovem modelu stromu
			testTreeModel.nodeChanged(selectedNode);
			
		}
		
	}//GEN-LAST:event_renameMenuItemActionPerformed

	private void removeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMenuItemActionPerformed
		
		//zabraneni modifikace prave beziciho testu
		if (isRunningTestOrStatementSelected()) {
			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//zjistime, ktery uzel je oznacen
		DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
		TreePath selectionPath= testTree.getSelectionPath();
		Object selectedObject= selectedNode.getUserObject();
		//pokud je oznacen test..
		if (selectedObject instanceof Test) {
			Test selectedTest= (Test) selectedObject;
			//zobrazime jednoduchy dialog pro ujisteni, zda se ma test smazat
			Object[] options= {"Yes", "No"};
			int answer= JOptionPane.showOptionDialog(this, "Are you really want to remove " + selectedTest.getTestName() +
					" with all particular statements?", "Remove test", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			//pokud ok, odstranime test z kolekce a take z datoveho modelu stromu
			if (answer == JOptionPane.YES_OPTION) {
				controller.removeTestFromList(selectedTest);
				testTreeModel.removeNodeFromParent(selectedNode);
				//aktualizace testComboBoxu
				testComboBoxModel.removeElement(selectedTest);
				testComboBox.repaint();
			}
			else
				return;
		}
		//pokud je oznaceno pravidlo..
		else {
			//odstranime pravidlo z kolekce a z datoveho modelu stromu
			TestStatement selectedStatement= (TestStatement) selectedObject;
			DefaultMutableTreeNode parentNode= (DefaultMutableTreeNode) selectedNode.getParent();
			Test parentTest= (Test) parentNode.getUserObject();
			parentTest.removeFromStatementList(selectedStatement);
			testTreeModel.removeNodeFromParent(selectedNode);
		}
		
		//nastavime oznaceni na otcovsky uzel a refresh panelu
		testTree.setSelectionPath(selectionPath.getParentPath());
		DefaultMutableTreeNode newSelectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
		refreshConditionPanel(newSelectedNode);
		refreshFaultPanel(newSelectedNode);
		
		//pokud je oznacen root uzel..znepristupnime prislusne tlacitka
		if (newSelectedNode.equals(testTreeModel.getRoot())) {
			addStatementButton.setEnabled(false);
			removeNodeButton.setEnabled(false);
		}
			
		
	}//GEN-LAST:event_removeMenuItemActionPerformed

	private void addTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTestButtonActionPerformed
		
		//zobrazime dialog pro pridani noveho testu
		AddTestDialog addTestDialog= new AddTestDialog(this, true, controller.getNewTestId());
		addTestDialog.setVisible(true);
		
		//pokud bylo stisknuto "ok", zaradime novy test do kolekce
		if (addTestDialog.isOkButtonClicked()) {
			Test newTest= addTestDialog.getNewTest();
			controller.addToTestList(newTest);
			
			//vlozime novy uzel do stromu na prislusne misto
			DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newTest);
			DefaultMutableTreeNode rootNode= (DefaultMutableTreeNode) testTreeModel.getRoot();
			testTreeModel.insertNodeInto(newNode, rootNode, rootNode.getChildCount());
			//rozbal strom tak, aby novy uzel sel videt
			testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
			
			//aktualizace testComboBoxu
			testComboBoxModel.addElement(newTest);
		}
		
	}//GEN-LAST:event_addTestButtonActionPerformed

	private void addStatementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStatementButtonActionPerformed
		
		//zabraneni modifikace prave beziciho testu
		if (isRunningTestOrStatementSelected()) {
			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//zobrazime dialog pro pridani noveho pravidla
		AddStatementDialog addStatementDialog= new AddStatementDialog(this, true, controller.getNewTestStatementId());
		addStatementDialog.setVisible(true);
		
		//pokud bylo stisknuto "ok", zaradime nove pravidlo do kolekce
		if (addStatementDialog.isOkButtonClicked()) {
			TestStatement newStatement= addStatementDialog.getNewStatement();
			DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
			Object selectedObject= selectedNode.getUserObject();
			//pokud je momentalne oznacen test..pridame nove pravidlo do jeho kolekce
			if (selectedObject instanceof Test) {
				Test selectedTest= (Test) selectedObject;
				selectedTest.addToStatementList(newStatement);
				//vlozime uzel do stromu na prislusne misto
				DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newStatement);
				testTreeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
				//rozbal strom tak, aby novy uzel sel videt
				testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
			}
			//jinak je oznaceno pravidlo..pridame nove pravidlo do otcovskeho uzlu 
			else {
				DefaultMutableTreeNode parentNode= (DefaultMutableTreeNode) selectedNode.getParent();
				Test parentTest= (Test) parentNode.getUserObject();
				parentTest.addToStatementList(newStatement);
				//vlozime uzel do stromu na prislusne misto
				DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(newStatement);
				testTreeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
				//rozbal strom tak, aby novy uzel sel videt
				testTree.scrollPathToVisible(new TreePath(newNode.getPath()));
			}
			
		}
		
	}//GEN-LAST:event_addStatementButtonActionPerformed

	private void removeNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeNodeButtonActionPerformed
		
		//stejna funkce jako z kontextoveho menu
		removeMenuItemActionPerformed(evt);
	}//GEN-LAST:event_removeNodeButtonActionPerformed

	private void removeConditionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeConditionButtonActionPerformed
		
		//zabraneni modifikace prave beziciho testu
		if (isRunningTestOrStatementSelected()) {
			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//zjistime, ktery radek v tabulce je oznacen a ziskame referenci na konkretni podminku
		int selectedRow= conditionTable.getSelectedRow();
		if (selectedRow == -1)
			return;
		Condition selectedCondition= (Condition) conditionTableModel.getValueAt(selectedRow, 0);
		
		//zjistime, ktera podminka ve stromu je oznacena
		DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
		TestStatement selectedStatement= (TestStatement) selectedNode.getUserObject();
		
		//odstranime podminku jak z tabulky, tak z kolekce
		selectedStatement.removeFromConditionSet(selectedCondition);
		conditionTableModel.removeRow(selectedRow);
				
	}//GEN-LAST:event_removeConditionButtonActionPerformed

	private void removeFaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFaultButtonActionPerformed
		
		//zabraneni modifikace prave beziciho testu
		if (isRunningTestOrStatementSelected()) {
			JOptionPane.showMessageDialog(this, "Cannot modify settings of the running test.",
			"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//zjistime, ktery radek v tabulce je oznacen a ziskame referenci na konkretni poruchu
		int selectedRow= faultTable.getSelectedRow();
		if (selectedRow == -1)
			return;
		Fault selectedFault= (Fault) faultTableModel.getValueAt(selectedRow, 0);
		
		//zjistime, ktera porucha ve stromu je oznacena
		DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode) testTree.getLastSelectedPathComponent();
		TestStatement selectedStatement= (TestStatement) selectedNode.getUserObject();
		
		//odstranime poruchu jak z tabulky, tak z kolekce
		selectedStatement.removeFromFaultList(selectedFault);
		faultTableModel.removeRow(selectedRow);
	}//GEN-LAST:event_removeFaultButtonActionPerformed

	private void startTestToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTestToggleButtonActionPerformed
		
		//pokud je stlaceno tlacitko pro zahajeni testovani..nastaveni noveho aktivniho test z testComboBox
		if (startTestToggleButton.isSelected()) {
			controller.setActiveTest((Test) testComboBoxModel.getSelectedItem());
			testComboBox.setEnabled(false);
		}
		//jinak nenastaveni zadneho testu
		else {
			controller.setActiveTest(null);
			testComboBox.setEnabled(true);
		}
	}//GEN-LAST:event_startTestToggleButtonActionPerformed

	private void proxySettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proxySettingsButtonActionPerformed
		
		ProxySettingsDialog proxySettingsDialog= new ProxySettingsDialog(this, true, controller.getProxyHost(),
				controller.getProxyPort(), controller.getTestedWsPort());
		proxySettingsDialog.setVisible(true);
		
		if (proxySettingsDialog.isOkButtonClicked()) {
			controller.setProxyHost(proxySettingsDialog.getProxyHost());
			controller.setProxyPort(proxySettingsDialog.getProxyPort());
			controller.setTestedWsPort(proxySettingsDialog.getTestedWsPort());
		}
		
	}//GEN-LAST:event_proxySettingsButtonActionPerformed

	private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
		
		//pred ukoncenim aplikace ulozime vsechny testy na disk
		controller.saveDataToXML();
	}//GEN-LAST:event_formWindowClosing

	private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
		
		//pred ukoncenim aplikace ulozime vsechny testy na disk
		controller.saveDataToXML();
		
		this.setVisible(false);
		System.exit(0);
	}//GEN-LAST:event_exitMenuItemActionPerformed

	private void settingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsMenuItemActionPerformed
		
		//stejna operace jako predesla metoda
		proxySettingsButtonActionPerformed(evt);
		
	}//GEN-LAST:event_settingsMenuItemActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
//		
//		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//		 */
//		try {
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (InstantiationException ex) {
//			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (IllegalAccessException ex) {
//			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
//			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}
//		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new MainWindow().setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JTabbedPane mainTabbedPane;
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
    private javax.swing.JToolBar testToolBar;
    private javax.swing.tree.DefaultTreeModel testTreeModel;
    private javax.swing.tree.DefaultTreeCellRenderer testTreeRenderer;
    private javax.swing.JTree testTree;
    private javax.swing.JPopupMenu testTreePopupMenu;
    private javax.swing.JMenuBar topMenuBar;
    private javax.swing.JToolBar topToolBar;
    // End of variables declaration//GEN-END:variables
}

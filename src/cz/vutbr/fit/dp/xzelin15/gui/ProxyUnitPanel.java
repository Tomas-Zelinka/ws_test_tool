package cz.vutbr.fit.dp.xzelin15.gui;


import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;

import cz.vutbr.fit.dp.xzelin15.logging.ConsoleLog;
import cz.vutbr.fit.dp.xzelin15.modalWindows.AntiAliasedEditorPane;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.HttpInteraction;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.HttpMessageParser;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.HttpRequest;
import cz.vutbr.fit.dp.xzelin15.proxyUnit.HttpResponse;


public class ProxyUnitPanel extends JSplitPane  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7672468377133931212L;
	 private javax.swing.table.DefaultTableModel interactionTableModel;
    private javax.swing.JTable interactionTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane messageTabbedPane;
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
    private InteractionTablePopupListener interactionTablePopupListener;
    private Map<Integer, HttpInteraction> interactionMap;
   
	//private TestTreePopupListener testTreePopupListener;
		
    
    public ProxyUnitPanel(){
    	
		//zaregistrujeme se jako odberatel udalosti o novych zpravach z business vrstvy
		interactionTablePopupListener= new InteractionTablePopupListener();
			
		initComponents();
		
		//ziskame referenci na datovy model interakcni tabulky
		interactionTableModel= (DefaultTableModel) interactionTable.getModel();
		
		
    }
    
 	
	/**
	 * Obsluha udalost pri zachyceni nove zpravy vychazejici z navrhoveho vzoru observer.
	 */
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
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) {
		//ConsoleLog.Print("[ProxyUnitPanel] vkladam do tabulky" + port);
		//hledame v tabulce, zda tam jiz prislusna interakce neni..pokud ano, pridame nove informace
		
		
		
		//pokud v mape dosud neni prislusna http interakce..vytvorime novou
		if (!interactionMap.containsKey(interactionId) ){
			interactionMap.put(interactionId, interaction);
			insertRowInteractionTable(interactionId, interaction);
			
		}
		//pokud v mape jiz existuje tato interakce..pridame k ni prislusny request/response
		else {
			interactionMap.remove(interactionId);
			interactionMap.put(interactionId, interaction);
			
			for (int i= 0; i < interactionTableModel.getRowCount(); i++){
				ConsoleLog.Print("[ProxyUnitPanel] hledam"+ interactionId+" nachazim " +interactionTableModel.getValueAt(i, 0));
				if (interactionId == (Integer)interactionTableModel.getValueAt(i, 0)) {
					ConsoleLog.Print("[ProxyUnitPanel] AGREGUJU" + interactionId);
					setValueAtInteractionTable(i,interaction);
					break;
				}
			}
			
		}
	}
	
	/**
	 * Metoda pro editovani radku do tabulky interakci.
	 * @param row cislo radku
	 * @param interactionId id interakce
	 * @param interaction objekt interakce
	 */
	private void setValueAtInteractionTable(int row, HttpInteraction interaction) {
		
		HttpRequest httpRequest= interaction.getHttpRequest();
		HttpResponse httpResponse= interaction.getHttpResponse();
		
		//vlozeni cisla interakce
		//interactionTableModel.setValueAt(interactionId, row, 0);
		
		//HTTP REQUEST
		if (httpRequest != null) {
			//vlozeni http metody
			interactionTableModel.setValueAt(httpRequest.getHttpMethod(), row, 2);
			//vlozeni iniciatora komunikace
			interactionTableModel.setValueAt(httpRequest.getInitiator(), row, 3);
			//vlozeni URI
			interactionTableModel.setValueAt(httpRequest.getUri(), row, 4);
			
			interactionTableModel.setValueAt(httpRequest.isChanged(), row, 6);
		}
		
		//HTTP RESPONSE
		//vlozeni http kodu
		if (httpResponse != null){
			interactionTableModel.setValueAt(httpResponse.getHttpCode() + " " + httpResponse.getHttpCodeDesc(),
					row, 1);
			interactionTableModel.setValueAt(httpRequest.isChanged(), row, 6);
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
		String usedTest= interaction.getName();
		
		//HTTP REQUEST
		if (httpRequest != null) {
			Object[] newRow= new Object[] {interactionId, null, httpRequest.getHttpMethod(),
					httpRequest.getInitiator(), httpRequest.getUri(), usedTest,httpRequest.isChanged()};
			interactionTableModel.insertRow(interactionTable.getRowCount(), newRow);
		}
		
		//HTTP RESPONSE
		else {
			Object[] newRow= new Object[] {interactionId, httpResponse.getHttpCode() + " " + 
					httpResponse.getHttpCodeDesc(), null,null,null, usedTest,httpResponse.isChanged()};
			interactionTableModel.insertRow(interactionTable.getRowCount(), newRow);
		}
		
		
	}
	
	
	//<editor-fold defaultstate="collapsed" desc="InteractionTablePopupListener class">
	/**
	 * Pomocna vnorena trida reprezentujici obsluhu udalosti pri kliknuti do tabulky interakci.
	 * Pri kliknuti pravym tlacitkem se krome oznaceni radku zobrazi prislusne kontextove menu.
	 */
	private class InteractionTablePopupListener extends MouseAdapter {

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
				//ConsoleLog.Print("[ProxyUnitPanel] vkladam interakci");
				
				if(interactionMap.containsKey(interactionId))
				{
					HttpInteraction selectedInteraction= interactionMap.get(interactionId);
				
					HttpRequest httpRequest= selectedInteraction.getHttpRequest();
					HttpResponse httpResponse= selectedInteraction.getHttpResponse();
					if (httpRequest != null && (httpRequest.getHttpHeader() != null) && (httpRequest.getContent() != null)) {
						reqOriginalEditorPane.setText(httpRequest.getHttpHeader() + 
								HttpMessageParser.formatXmlMessage(httpRequest.getContent()));
						//nechceme odscrollovat dolu
						reqOriginalEditorPane.setCaretPosition(0);
						if(httpRequest.isChanged()){
							reqChangedEditorPane.setText(httpRequest.getChangedHttpHeader() +   
									HttpMessageParser.formatXmlMessage(httpRequest.getChangedContent()));
							//nechceme odscrollovat dolu
							reqChangedEditorPane.setCaretPosition(0);
						}else{
							reqChangedEditorPane.setText("nothing changed");
							//nechceme odscrollovat dolu
							reqChangedEditorPane.setCaretPosition(0);
						}
					}
					if (httpResponse != null &&  httpResponse.getHttpHeader() != null && httpResponse.getContent() != null) {
						resOriginalEditorPane.setText(httpResponse.getHttpHeader() +  
								HttpMessageParser.formatXmlMessage(httpResponse.getContent()));
						//nechceme odscrollovat dolu
						resOriginalEditorPane.setCaretPosition(0);
						if(httpResponse.isChanged()){
							resChangedEditorPane.setText(httpResponse.getChangedHttpHeader() + 
									HttpMessageParser.formatXmlMessage(httpResponse.getChangedContent()));
							//nechceme odscrollovat dolu
							resChangedEditorPane.setCaretPosition(0);
						}else{
							resChangedEditorPane.setText("nothing changed");
							//nechceme odscrollovat dolu
							resChangedEditorPane.setCaretPosition(0);
						}
					}
				}else{ 
					reqOriginalEditorPane.setText("");
					reqChangedEditorPane.setText("");
					resOriginalEditorPane.setText("");
					resChangedEditorPane.setText("");
				}
			}
			
		}
	}
		
		
	private void initComponents() {

	    
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
	        interactionMap = new ConcurrentHashMap<Integer,HttpInteraction>();
	       
	        this.setDividerLocation(200);
	        this.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
	        interactionTable.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	                "#", "Http code", "Method", "Initiator", "URI", "Test", "Injected"
	            }
	        ) {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 7932709950895630069L;
				boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, false,false
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });
	        //interactionTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
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

	           
	    }// </editor-fold>//GEN-END:initComponents
}

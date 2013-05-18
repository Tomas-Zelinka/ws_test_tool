package modalWindows;

import gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;

import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;

/**
 * Dialog pro pridani nove poruchy do prislusneho pravidla.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class GenerateFromWSDLDialog extends InputModalWindow {
	
	  private javax.swing.JButton addButton;
	    private javax.swing.JButton cancelButton;
	    private javax.swing.JEditorPane contentEditorPane;
	    private javax.swing.JLabel contentLabel;
	    private javax.swing.JScrollPane contentScrollPane;
	    private javax.swing.JButton downloadButton;
	    private javax.swing.JSeparator jSeparator1;
	    private javax.swing.JComboBox operationComboBox;
	    private javax.swing.JLabel operationLabel;
	    private javax.swing.JLabel uriLabel;
	    private javax.swing.JTextField uriTextField;
	    private javax.swing.JPanel wsdlOperationPanel;
	    private Thread downloadThread;
		private boolean addButtonClicked;
		private SoapBuilder builder;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/** Creates new form AddFaultDialog */
	public GenerateFromWSDLDialog() {
		super("Generate Content",640, 530);
		
		//zobrazit dialog nad hlavnim oknem
		
		//this.newFaultId= newFaultId;
		initComponents();
		//vychozi oznaceni tlacitka Pridat
		
		
		//ovladaci prvky v karte WSDL poruchy jsou znepristupneny, dokud neni popis stahnut
		operationComboBox.setEnabled(false);
		putContent();
	}
	
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	 // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   protected void initComponents() {

        wsdlOperationPanel = new javax.swing.JPanel();
        uriLabel = new javax.swing.JLabel();
        uriTextField = new javax.swing.JTextField();
        downloadButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        operationLabel = new javax.swing.JLabel();
        operationComboBox = new javax.swing.JComboBox();
        contentScrollPane = new javax.swing.JScrollPane();
        contentEditorPane = new AntiAliasedEditorPane();
        contentLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
    
    }// </editor-fold>//GEN-END:initComponents
	
	
	
	//<editor-fold defaultstate="collapsed" desc="WsdlDownloadThread class">
	/**
	 * Pomocna vnitrni trida implementujici chovani noveho vlakna urceneho pro stahovani WSDL
	 * popisu sluzby.
	 */
	public class WsdlDownloadThread extends Thread {
		
		@Override
		public void run() {
			
			try {
				System.out.println("Downloading WSDL.."+uriTextField.getText());
			
		   		
				List<String> operationList= parseWSDL(uriTextField.getText());
				for (String operation : operationList)
					operationComboBox.addItem(operation);
				
				operationComboBox.setEnabled(true);
				contentEditorPane.setEnabled(true);
			}
			catch (Exception ex) {
				showWsdlNotFound();
				//ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Metoda pro zobrazeni dialogu, ze se nepodarilo ziskat WSDL ze zadane adresy.
	 */
	protected void showWsdlNotFound() {
		
		JOptionPane.showMessageDialog(this, "WSDL description could not be retrieved from the specified address.",
				"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Metoda pro zjisteni, zda bylo stisknuto tlacitko "pridat".
	 * @return true, pokud bylo stisknuto
	 */
	public boolean isAddButtonClicked() {
		return addButtonClicked;
	}
	
	/**
	 * Metoda pro stahnuti a ziskani seznamu WSDL operaci nad sluzbou.
	 * @param wsdlUri 
	 */
	private List<String> parseWSDL(String wsdlUri) throws Exception {
		
		Wsdl wsdl = Wsdl.parse(wsdlUri);
		//ConsoleLog.Print("preparsovano"); 
		List<QName> bindings = wsdl.getBindings();
		QName binding = bindings.get(0);
		builder = wsdl.binding().localPart(binding.getLocalPart()).find();
		
		//prochazime jednotlive elementy <message> a <part> a hledame jmena operaci
		List<String> operationList= new ArrayList<String>();
		List<SoapOperation> operations = builder.getOperations();	
		for (SoapOperation operation : operations) {
			//ziskani nazvu operace + odstraneni prefixu jmenneho prostoru
			operationList.add(operation.getOperationName());
			
		}
		return operationList;
	}

	/**
	 * 
	 */
	public void putContent() {
		uriLabel.setText("WSDL URI:");
		
		operationComboBox.addActionListener(new GenerateListner());
		downloadButton.setText("Download");
		downloadButton.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		
				downloadThread= new WsdlDownloadThread();
				downloadThread.start();
		    }
		});
		
		operationLabel.setText("WSDL operation:");
		contentEditorPane.setEditable(true);
		contentScrollPane.setViewportView(contentEditorPane);
		MainWindow.initEditorPane(contentEditorPane, contentScrollPane); 
		
		contentLabel.setText("Content:");
		
		
		javax.swing.GroupLayout wsdlOperationPanelLayout = new javax.swing.GroupLayout(wsdlOperationPanel);
		wsdlOperationPanel.setLayout(wsdlOperationPanelLayout);
		wsdlOperationPanelLayout.setHorizontalGroup(
		    wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(wsdlOperationPanelLayout.createSequentialGroup()
		        .addContainerGap()
		        .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
		            .addGroup(wsdlOperationPanelLayout.createSequentialGroup()
		                .addComponent(operationLabel)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addComponent(operationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
		            .addGroup(wsdlOperationPanelLayout.createSequentialGroup()
		                .addComponent(uriLabel)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wsdlOperationPanelLayout.createSequentialGroup()
		                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
		                        .addComponent(downloadButton))
		                    .addComponent(uriTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)))
		            .addComponent(contentLabel)
		            .addComponent(contentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
		        .addContainerGap())
		);
		wsdlOperationPanelLayout.setVerticalGroup(
		    wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(wsdlOperationPanelLayout.createSequentialGroup()
		        .addContainerGap()
		        .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		            .addComponent(uriLabel)
		            .addComponent(uriTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		            .addComponent(downloadButton))
		            .addGap(18, 18, 18)
		        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		            .addComponent(operationLabel)
		            .addComponent(operationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(contentLabel)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(contentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
		        .addContainerGap())
		);
		
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		    	setVisible(false);
		    }
		});
		
		addButton.setText("Add");
	    addButton.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	        	addButtonClicked = true;
	    		setVisible(false);
	        }
	    });
		
	   getSecondPanel().add(wsdlOperationPanel);
	   pack();
		
	}

	private class GenerateListner implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			JComboBox box =(JComboBox) e.getSource();
			String method = (String)box.getSelectedItem();
			
			SoapOperation operation = builder.operation().name(method).find();
			String request = builder.buildInputMessage(operation);
			contentEditorPane.setText(request);
		}
	}



	public String getGeneratedContent(){
		return contentEditorPane.getText();
	}

	@Override
	protected String testEmptyInput() {
		return null;
	}


	@Override
	protected void initButtons() {
		this.getRootPane().setDefaultButton(addButton);
		addButton(addButton);
	     addButton(cancelButton);
	     
	}
	
}

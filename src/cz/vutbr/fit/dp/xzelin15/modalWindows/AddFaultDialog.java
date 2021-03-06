/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */


package cz.vutbr.fit.dp.xzelin15.modalWindows;


import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jdom2.JDOMException;

import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.Part;
import com.predic8.wsdl.WSDLParser;

import cz.vutbr.fit.dp.xzelin15.data.DelayFault;
import cz.vutbr.fit.dp.xzelin15.data.EmptyingFault;
import cz.vutbr.fit.dp.xzelin15.data.Fault;
import cz.vutbr.fit.dp.xzelin15.data.HeaderCorruptionFault;
import cz.vutbr.fit.dp.xzelin15.data.JSONFault;
import cz.vutbr.fit.dp.xzelin15.data.MultiplicationFault;
import cz.vutbr.fit.dp.xzelin15.data.StringCorruptionFault;
import cz.vutbr.fit.dp.xzelin15.data.WsdlOperationFault;
import cz.vutbr.fit.dp.xzelin15.data.XPathCorruptionFault;
import cz.vutbr.fit.dp.xzelin15.gui.MainWindow;


/**
 * Dialog pro pridani nove poruchy do prislusneho pravidla.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class AddFaultDialog extends javax.swing.JDialog {
	
	
	//<editor-fold defaultstate="collapsed" desc="WsdlDownloadThread class">
	/**
	 * Pomocna vnitrni trida implementujici chovani noveho vlakna urceneho pro stahovani WSDL
	 * popisu sluzby.
	 */
	public class WsdlDownloadThread extends Thread {
		
		
		@Override
		public void run() {
			
			try {
				downloadLabel.setText("Downloading WSDL..");
				List<String> operationList= parseWSDL(uriTextField.getText());
				for (String operation : operationList)
					operationComboBox.addItem(operation);
				
				downloadLabel.setText("");
				operationComboBox.setEnabled(true);
				contentEditorPane.setEnabled(true);
			}
			
			catch (Exception ex) {
				showWsdlNotFound();
				downloadLabel.setText("");
			}
		}
	}
	//</editor-fold>
	
	
		
	private static final String STRING_CORRUPTION_PANEL= "StringCorruptionFault";
	private static final String XPATH_CORRUPTION_PANEL= "XPathCorruptionFault";
	private static final String MULTIPLICATION_PANEL= "MultiplicationFault";
	private static final String EMPTYING_PANEL= "EmptyingFault";
	private static final String DELAY_PANEL= "DelayFault";
	private static final String HEADER_CORRUPTION_PANEL= "HeaderCorruptionFault";
	private static final String WSDL_OPERATION_PANEL= "WsdlOperationFault";
	private static final String JSON_CORRUPTION_PANEL = "JSONFault";
	
	private static final String[] TYPE_COMBO_BOX_ITEMS= {STRING_CORRUPTION_PANEL, XPATH_CORRUPTION_PANEL,
			MULTIPLICATION_PANEL, EMPTYING_PANEL, DELAY_PANEL, HEADER_CORRUPTION_PANEL, WSDL_OPERATION_PANEL,JSON_CORRUPTION_PANEL};
	
	
	private Thread downloadThread;
	
	private boolean addButtonClicked;
	//private int newFaultId;
	private Fault newFault;
	

	/** Creates new form AddFaultDialog */
	public AddFaultDialog(JFrame parent, boolean modal) {
		
		super(parent, modal);
		//zobrazit dialog nad hlavnim oknem
		this.setLocationRelativeTo(parent);
		this.setLocation(this.getX() - 200, this.getY() - 200);
		
		//this.newFaultId= newFaultId;
		initComponents();
		//vychozi oznaceni tlacitka Pridat
		this.getRootPane().setDefaultButton(addButton);
		
		//ovladaci prvky v karte WSDL poruchy jsou znepristupneny, dokud neni popis stahnut
		operationComboBox.setEnabled(false);
		contentEditorPane.setEnabled(false);
				
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
	 * Metoda pro ziskani nove vytvorene poruchy.
	 * @return novy objekt poruchy
	 */
	public Fault getNewFault() {
		
		return newFault;
	}
	
	/**
	 * Metoda pro stahnuti a ziskani seznamu WSDL operaci nad sluzbou.
	 * @param wsdlUri 
	 */
	private List<String> parseWSDL(String wsdlUri) throws Exception {
		
		
		
		WSDLParser parser= new WSDLParser();
		Definitions defs= parser.parse(wsdlUri);

		//prochazime jednotlive elementy <message> a <part> a hledame jmena operaci
		List<String> operationList= new ArrayList<String>();
		List<Message> messageList= defs.getMessages();
		for (Message message : messageList) {
			List<Part> partList= message.getParts();
			for (Part currentPart : partList) {
				//ziskani nazvu operace + odstraneni prefixu jmenneho prostoru
				operationList.add(currentPart.getElement().replaceFirst(".*:", ""));
			}
		}
		
		
		
		return operationList;
	}

		
	
	

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typeLabel = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox(TYPE_COMBO_BOX_ITEMS);
        settingsPanel = new javax.swing.JPanel();
        stringCorruptionPanel = new javax.swing.JPanel();
        originalLabel = new javax.swing.JLabel();
        originalTextField = new javax.swing.JTextField();
        newLabel = new javax.swing.JLabel();
        newTextField = new javax.swing.JTextField();
        xPathCorruptionPanel = new javax.swing.JPanel();
        xPathLabel = new javax.swing.JLabel();
        xPathTextField = new javax.swing.JTextField();
        newXPathLabel = new javax.swing.JLabel();
        newXPathTextField = new javax.swing.JTextField();
        multiplicationPanel = new javax.swing.JPanel();
        xPath2Label = new javax.swing.JLabel();
        xPath2TextField = new javax.swing.JTextField();
        countLabel = new javax.swing.JLabel();
        countTextField = new javax.swing.JTextField();
        emptyingPanel = new javax.swing.JPanel();
        emptyingLabel = new javax.swing.JLabel();
        delayPanel = new javax.swing.JPanel();
        delayLabel = new javax.swing.JLabel();
        delayTextField = new javax.swing.JTextField();
        headerCorruptionPanel = new javax.swing.JPanel();
        originalHeaderLabel = new javax.swing.JLabel();
        originalHeaderTextField = new javax.swing.JTextField();
        newHeaderLabel = new javax.swing.JLabel();
        newHeaderTextField = new javax.swing.JTextField();
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
        downloadLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jsonPanel = new JPanel();
        jsonVariableLabel = new JLabel();
        jsonValueLabel = new JLabel();
        jsonVariableField = new JTextField();
        jsonValueField = new JTextField();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add fault");
        setResizable(false);

        typeLabel.setText("Fault type:");

        typeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeComboBoxItemStateChanged(evt);
            }
        });

        settingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));
        settingsPanel.setLayout(new java.awt.CardLayout());

        originalLabel.setText("Original substring:");

        newLabel.setText("New substring:");

        javax.swing.GroupLayout stringCorruptionPanelLayout = new javax.swing.GroupLayout(stringCorruptionPanel);
        stringCorruptionPanel.setLayout(stringCorruptionPanelLayout);
        stringCorruptionPanelLayout.setHorizontalGroup(
            stringCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stringCorruptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stringCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(originalLabel)
                    .addComponent(newLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(stringCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                    .addComponent(originalTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                .addContainerGap())
        );
        stringCorruptionPanelLayout.setVerticalGroup(
            stringCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stringCorruptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stringCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(originalLabel)
                    .addComponent(originalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(stringCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newLabel)
                    .addComponent(newTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(241, Short.MAX_VALUE))
        );

        settingsPanel.add(stringCorruptionPanel, "StringCorruptionFault");

        xPathLabel.setText("XPath:");

        newXPathLabel.setText("New substring:");

        javax.swing.GroupLayout xPathCorruptionPanelLayout = new javax.swing.GroupLayout(xPathCorruptionPanel);
        xPathCorruptionPanel.setLayout(xPathCorruptionPanelLayout);
        xPathCorruptionPanelLayout.setHorizontalGroup(
            xPathCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xPathCorruptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(xPathCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xPathLabel)
                    .addComponent(newXPathLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(xPathCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newXPathTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(xPathTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
                .addContainerGap())
        );
        xPathCorruptionPanelLayout.setVerticalGroup(
            xPathCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(xPathCorruptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(xPathCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xPathLabel)
                    .addComponent(xPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(xPathCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newXPathLabel)
                    .addComponent(newXPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(241, Short.MAX_VALUE))
        );

        settingsPanel.add(xPathCorruptionPanel, "XPathCorruptionFault");

        xPath2Label.setText("XPath:");

        countLabel.setText("Number of duplications: ");

        javax.swing.GroupLayout multiplicationPanelLayout = new javax.swing.GroupLayout(multiplicationPanel);
        multiplicationPanel.setLayout(multiplicationPanelLayout);
        multiplicationPanelLayout.setHorizontalGroup(
            multiplicationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(multiplicationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(multiplicationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xPath2Label)
                    .addComponent(countLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(multiplicationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(countTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(xPath2TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                .addContainerGap())
        );
        multiplicationPanelLayout.setVerticalGroup(
            multiplicationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(multiplicationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(multiplicationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xPath2Label)
                    .addComponent(xPath2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(multiplicationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countLabel)
                    .addComponent(countTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(235, Short.MAX_VALUE))
        );

        settingsPanel.add(multiplicationPanel, "MultiplicationFault");

        emptyingLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        emptyingLabel.setText("The body of the HTTP message will be removed. Just the header will be sent. ");

        javax.swing.GroupLayout emptyingPanelLayout = new javax.swing.GroupLayout(emptyingPanel);
        emptyingPanel.setLayout(emptyingPanelLayout);
        emptyingPanelLayout.setHorizontalGroup(
            emptyingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emptyingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(emptyingLabel)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        emptyingPanelLayout.setVerticalGroup(
            emptyingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emptyingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(emptyingLabel)
                .addContainerGap(276, Short.MAX_VALUE))
        );

        settingsPanel.add(emptyingPanel, "EmptyingFault");

        delayLabel.setText("Delay in miliseconds:");

        javax.swing.GroupLayout delayPanelLayout = new javax.swing.GroupLayout(delayPanel);
        delayPanel.setLayout(delayPanelLayout);
        delayPanelLayout.setHorizontalGroup(
            delayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(delayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(delayLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delayTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );
        delayPanelLayout.setVerticalGroup(
            delayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(delayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(delayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delayLabel)
                    .addComponent(delayTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(272, Short.MAX_VALUE))
        );

        settingsPanel.add(delayPanel, "DelayFault");

        originalHeaderLabel.setText("Original substring:");

        newHeaderLabel.setText("New substring:");

        javax.swing.GroupLayout headerCorruptionPanelLayout = new javax.swing.GroupLayout(headerCorruptionPanel);
        headerCorruptionPanel.setLayout(headerCorruptionPanelLayout);
        headerCorruptionPanelLayout.setHorizontalGroup(
            headerCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerCorruptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(originalHeaderLabel)
                    .addComponent(newHeaderLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(headerCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newHeaderTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                    .addComponent(originalHeaderTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                .addContainerGap())
        );
        headerCorruptionPanelLayout.setVerticalGroup(
            headerCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerCorruptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(originalHeaderLabel)
                    .addComponent(originalHeaderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(headerCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newHeaderLabel)
                    .addComponent(newHeaderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(241, Short.MAX_VALUE))
        );

        settingsPanel.add(headerCorruptionPanel, "HeaderCorruptionFault");

        uriLabel.setText("WSDL URI:");

        downloadButton.setText("Download");
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        operationLabel.setText("WSDL operation:");

        contentScrollPane.setViewportView(contentEditorPane);
        MainWindow.initEditorPane(contentEditorPane, contentScrollPane); 

        contentLabel.setText("Content:");

        downloadLabel.setFont(new java.awt.Font("Dialog", 0, 12));

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
                                .addComponent(downloadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(downloadButton)
                    .addComponent(downloadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        settingsPanel.add(wsdlOperationPanel, "WsdlOperationFault");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(typeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(addButton))
                .addContainerGap())
        );

        
        
        settingsPanel.add(jsonPanel, "JSONFault");

        jsonVariableLabel.setText("Variable Name:");

        jsonValueLabel.setText("Variable value:");

        javax.swing.GroupLayout JSONCorruptionPanelLayout = new javax.swing.GroupLayout(jsonPanel);
        jsonPanel.setLayout(JSONCorruptionPanelLayout);
        JSONCorruptionPanelLayout.setHorizontalGroup(
        		JSONCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JSONCorruptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JSONCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsonVariableLabel)
                    .addComponent(jsonValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JSONCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsonVariableField, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                    .addComponent(jsonValueField, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                .addContainerGap())
        );
        JSONCorruptionPanelLayout.setVerticalGroup(
        		JSONCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JSONCorruptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JSONCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jsonVariableLabel)
                    .addComponent(jsonVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JSONCorruptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jsonValueLabel)
                    .addComponent(jsonValueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void typeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeComboBoxItemStateChanged
		
		//pri zmene vybraneho typu poruchy se zobrazi prislusny panel s nastavenim
		CardLayout cardLayout= (CardLayout) settingsPanel.getLayout();
		cardLayout.show(settingsPanel, (String) evt.getItem());
	}//GEN-LAST:event_typeComboBoxItemStateChanged

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		
		this.setVisible(false);
	}//GEN-LAST:event_cancelButtonActionPerformed

	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
		
		//vytvorime novy objekt poruchy v zavislosti na uzivatelskem nastaveni dialogu
		addButtonClicked= true;
		String selectedItem= (String) typeComboBox.getSelectedItem();
		
		//STRING CORRUPTION FAULT
		if (selectedItem.equals(STRING_CORRUPTION_PANEL)) {
			newFault= new StringCorruptionFault( originalTextField.getText(), newTextField.getText());
			this.setVisible(false);
		}
		
		//XPATH CORRUPTION FAULT
		if (selectedItem.equals(XPATH_CORRUPTION_PANEL)) {
			try {
				newFault= new XPathCorruptionFault( xPathTextField.getText(), newXPathTextField.getText());
				this.setVisible(false);
			}
			catch (JDOMException ex) {
				JOptionPane.showMessageDialog(this, "THEADER_CONDITION_PANELhe specified path does not match the XPath expression.",
						"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
				addButtonClicked= false;
//			
			}
		}
		
		//MULTIPLICATION FAULT
		if (selectedItem.equals(MULTIPLICATION_PANEL)) {
			try {
				newFault= new MultiplicationFault(xPath2TextField.getText(), 
						Integer.parseInt(countTextField.getText()));
				this.setVisible(false);
			}
			catch (JDOMException ex) {
				JOptionPane.showMessageDialog(this, "The specified path does not match the XPath expression",
						"Cannot complete operation", JOptionPane.WARNING_MESSAGE);
				addButtonClicked= false;
				
			}
			
		}
		
		//EMPTYING FAULT
		if (selectedItem.equals(EMPTYING_PANEL)) {
			newFault= new EmptyingFault();
			this.setVisible(false);
		}
		
		//DELAY FAULT
		if (selectedItem.equals(DELAY_PANEL)) {
			newFault= new DelayFault(Integer.parseInt(delayTextField.getText()));
			this.setVisible(false);
		}
		
		//HEADER CORRUPTION FAULT
		if (selectedItem.equals(HEADER_CORRUPTION_PANEL)) {
			newFault= new HeaderCorruptionFault( originalHeaderTextField.getText(), 
					newHeaderTextField.getText());
			this.setVisible(false);
			
		}
		
		if (selectedItem.equals(JSON_CORRUPTION_PANEL)) {
			newFault= new JSONFault(jsonVariableField.getText(),jsonValueField.getText());
			this.setVisible(false);
			
		}
		
		
		//WSDL OPERATION FAULT
		if (selectedItem.equals(WSDL_OPERATION_PANEL)) {
			newFault= new WsdlOperationFault(uriTextField.getText(), 
					(String) operationComboBox.getSelectedItem(),
					StringEscapeUtils.unescapeXml(contentEditorPane.getText()));
			this.setVisible(false);
		}
		
		
	}//GEN-LAST:event_addButtonActionPerformed

	private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
		
		downloadThread= new WsdlDownloadThread();
		downloadThread.start();
		
	}//GEN-LAST:event_downloadButtonActionPerformed

	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JEditorPane contentEditorPane;
    private javax.swing.JLabel contentLabel;
    private javax.swing.JScrollPane contentScrollPane;
    private javax.swing.JLabel countLabel;
    private javax.swing.JTextField countTextField;
    private javax.swing.JLabel delayLabel;
    private javax.swing.JPanel delayPanel;
    private javax.swing.JTextField delayTextField;
    private javax.swing.JButton downloadButton;
    private javax.swing.JLabel downloadLabel;
    private javax.swing.JLabel emptyingLabel;
    private javax.swing.JPanel emptyingPanel;
    private javax.swing.JPanel headerCorruptionPanel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel multiplicationPanel;
    private javax.swing.JLabel newHeaderLabel;
    private javax.swing.JTextField newHeaderTextField;
    private javax.swing.JLabel newLabel;
    private javax.swing.JTextField newTextField;
    private javax.swing.JLabel newXPathLabel;
    private javax.swing.JTextField newXPathTextField;
    private javax.swing.JComboBox operationComboBox;
    private javax.swing.JLabel operationLabel;
    private javax.swing.JLabel originalHeaderLabel;
    private javax.swing.JTextField originalHeaderTextField;
    private javax.swing.JLabel originalLabel;
    private javax.swing.JTextField originalTextField;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JPanel stringCorruptionPanel;
    private javax.swing.JComboBox typeComboBox;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JLabel uriLabel;
    private javax.swing.JTextField uriTextField;
    private javax.swing.JPanel wsdlOperationPanel;
    private javax.swing.JLabel xPath2Label;
    private javax.swing.JTextField xPath2TextField;
    private javax.swing.JPanel xPathCorruptionPanel;
    private javax.swing.JLabel xPathLabel;
    private javax.swing.JTextField xPathTextField;
    private JPanel jsonPanel;
    private JLabel jsonVariableLabel;
    private JLabel jsonValueLabel;
    private JTextField jsonVariableField;
    private JTextField jsonValueField;
    // End of variables declaration//GEN-END:variables
}

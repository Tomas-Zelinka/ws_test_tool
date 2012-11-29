/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */


package cz.vutbr.fit.dip.fiws.presentation;

import javax.swing.JOptionPane;

/**
 * Dialog pro nastaveni proxy monitorovaci jednotky. Je mozno nastavit umisteni proxy, port na kterem bude
 * proxy monitor naslouchat a port na kterem bezi testovana webova sluzba.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class ProxySettingsDialog extends javax.swing.JDialog {
	
	
	private boolean okButtonClicked;
	private int proxyPort;
	private int testedWsPort;
	private String proxyHost;

	/** Creates new form ProxySettingsDialog */
	public ProxySettingsDialog(java.awt.Frame parent, boolean modal, String proxyHost, int proxyPort,
			int testedWsPort) {
		
		super(parent, modal);
		//zobrazit dialog nad hlavnim oknem
		this.setLocationRelativeTo(parent);
		this.setLocation(this.getX() - 200, this.getY() - 200);
		
		okButtonClicked= false;
		
		this.proxyHost= proxyHost;
		this.proxyPort= proxyPort;
		this.testedWsPort= testedWsPort;
		
		initComponents();
		
		proxyHostTextField.setText(proxyHost);
		proxyPortTextField.setText(String.valueOf(proxyPort));
		testedWSTextField.setText(String.valueOf(testedWsPort));
	}

	/**
	 * Metoda pro zjisteni, zda bylo kliknuto na tlacitko OK.
	 * @return true, pokud bylo kliknuto na OK
	 */
	public boolean isOkButtonClicked() {
		
		return okButtonClicked;
	}

	/**
	 * Metoda pro ziskani zadaneho proxy umisteni.
	 * @return zadane proxy umisteni
	 */
	public String getProxyHost() {
		
		return proxyHost;
	}

	/**
	 * Metoda pro ziskani zadaneho proxy portu.
	 * @return zadany proxy port
	 */
	public int getProxyPort() {
		
		return proxyPort;
	}

	/**
	 * Metoda pro ziskani zadaneho portu testovane sluzby.
	 * @return zadany port testovane sluzby
	 */
	public int getTestedWsPort() {
		
		return testedWsPort;
	}
	
	

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        proxyHostLabel = new javax.swing.JLabel();
        proxyPortLabel = new javax.swing.JLabel();
        testedWSLabel = new javax.swing.JLabel();
        testedWSTextField = new javax.swing.JTextField();
        proxyPortTextField = new javax.swing.JTextField();
        proxyHostTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proxy settings");
        setResizable(false);

        proxyHostLabel.setText("Proxy host:");

        proxyPortLabel.setText("Proxy port:");

        testedWSLabel.setText("Tested WS port:");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(testedWSLabel)
                            .addComponent(proxyPortLabel)
                            .addComponent(proxyHostLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proxyPortTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(testedWSTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(proxyHostTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proxyHostLabel)
                    .addComponent(proxyHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proxyPortLabel)
                    .addComponent(proxyPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testedWSLabel)
                    .addComponent(testedWSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		
		this.setVisible(false);
	}//GEN-LAST:event_cancelButtonActionPerformed

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
		
		try {
			proxyPort= Integer.parseInt(proxyPortTextField.getText());
			testedWsPort= Integer.parseInt(testedWSTextField.getText());
			proxyHost= proxyHostTextField.getText().trim();
			okButtonClicked= true;
			this.setVisible(false);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Wrong values set.", "Cannot complete operation", 
					JOptionPane.WARNING_MESSAGE);
					
		}
		
	}//GEN-LAST:event_okButtonActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				ProxySettingsDialog dialog = new ProxySettingsDialog(new javax.swing.JFrame(), true, "", 0, 0);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {

					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel proxyHostLabel;
    private javax.swing.JTextField proxyHostTextField;
    private javax.swing.JLabel proxyPortLabel;
    private javax.swing.JTextField proxyPortTextField;
    private javax.swing.JLabel testedWSLabel;
    private javax.swing.JTextField testedWSTextField;
    // End of variables declaration//GEN-END:variables
}

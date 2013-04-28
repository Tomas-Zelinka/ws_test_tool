package modalWindows;

import gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AddWsdlToTestSuite extends InputModalWindow{
	
	
	private JButton closeButton;
	private JButton downloadWSDLButton;
	private JLabel wsdlUrlLabel;
	private JLabel downloadLabel;
	private JPanel wsdlOperationPanel;
    private JTextField uriTextField;
    private JScrollPane contentScrollPane;
    private JEditorPane contentEditorPane;
	
	
	public AddWsdlToTestSuite() {
		super("Add WSDL to request ", 640,580);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8398265929902182308L;

	@Override
	protected void initComponents() {
		
		wsdlUrlLabel = new JLabel("WSLD Url: ");
		downloadLabel = new JLabel("Download");
		uriTextField = new JTextField(20);
		contentEditorPane = new JEditorPane();
		contentScrollPane = new JScrollPane();
		wsdlOperationPanel = new JPanel();
		downloadWSDLButton = new JButton("Download");
		closeButton = new JButton("Close");
	}
	@Override
	protected void putContent() {
		 	
				        
	        downloadWSDLButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                downloadButtonActionPerformed(evt);
	            }
	        });
	
	        contentScrollPane.setViewportView(contentEditorPane);
	        MainWindow.initEditorPane(contentEditorPane, contentScrollPane); 

	        
	        downloadLabel.setFont(new java.awt.Font("Dialog", 0, 12));

	        javax.swing.GroupLayout wsdlOperationPanelLayout = new javax.swing.GroupLayout(wsdlOperationPanel);
	        wsdlOperationPanel.setLayout(wsdlOperationPanelLayout);
	        wsdlOperationPanelLayout.setHorizontalGroup(
	            wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(wsdlOperationPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                  // .addComponent(new JSeparator(), javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
	                    .addGroup(wsdlOperationPanelLayout.createSequentialGroup()
	                        .addComponent(wsdlUrlLabel)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wsdlOperationPanelLayout.createSequentialGroup()
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
	                                .addComponent(downloadWSDLButton))
	                            .addComponent(uriTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE))))
	                .addContainerGap())
	        );
	        wsdlOperationPanelLayout.setVerticalGroup(
	            wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(wsdlOperationPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(wsdlUrlLabel)
	                    .addComponent(uriTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(wsdlOperationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(downloadWSDLButton)
	                    )
	                .addGap(18, 18, 18)
	                //.addComponent(new JSeparator(), javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
	                
	        );

	        getSecondInsidePanel().add(wsdlOperationPanel);

	        pack();
	}

	

	@Override
	protected String testEmptyInput() {
		return null;
	}

	@Override
	protected void initButtons() {
		
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
	         	dispose();
			}
		});
		addButton(closeButton);
	}
	
	
	
	
	
	private void downloadButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
		
		
		WsdlDownloadThread downloadThread= new WsdlDownloadThread();
		downloadThread.start();
		
	}
	
	/**
	 * Pomocna vnitrni trida implementujici chovani noveho vlakna urceneho pro stahovani WSDL
	 * popisu sluzby.
	 */
	public class WsdlDownloadThread extends Thread {
		
		
		@Override
		public void run() {
			
				//downloadLabel.setText("Downloading WSDL..");
				
				String fileURL =  uriTextField.getText();
				String downloadedFileName = fileURL.substring(fileURL.lastIndexOf("/")+1);
		         try{
			        // Open connection to the file
			        URL url = new URL(fileURL);
			        InputStream is = url.openStream();
			        // Stream to the destionation file
			        FileOutputStream fos = new FileOutputStream(MainWindow.getDataRoot()+File.separator+MainWindow.getSuitePath()+File.separator+ downloadedFileName);
			  
			        // Read bytes from URL to the local file
			        byte[] buffer = new byte[4096];
			        int bytesRead = 0;
			         
			        System.out.print("Downloading " + downloadedFileName);
			        while ((bytesRead = is.read(buffer)) != -1) {
			            System.out.print(".");  // Progress bar :)
			            fos.write(buffer,0,bytesRead);
			        }
			        System.out.println("done!");
			  
			        // Close destination stream
			        fos.close();
			        // Close URL stream
			        is.close();
		         } catch(MalformedURLException m) {
		            System.out.println(m);
			     }catch(IOException io) {
			            System.out.println(io);
			      }
				
		}
			
	}
}

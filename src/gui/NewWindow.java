package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class NewWindow extends JDialog{

	/**
	 * 
	 */
	
	private JPanel firstPanel;
	private JPanel secondPanel;
	private JPanel thirdPanel;
	
	
	private final int WIDTH = 640;
	private final int HEIGHT = 480;
	
	private String dialogName = "New Test Project";
	private String dataPath;
	
	private JButton okButton;
	private JButton cancelButton;
	
	
	private static final long serialVersionUID = 9187751988881264097L;

	
	
	public NewWindow(){
		this.setTitle(dialogName);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		// Determine the new location of the window
		int x = (dim.width-(this.WIDTH))/2;
		int y = (dim.height-(this.HEIGHT))/2;
		 
		
		this.setLayout(new BorderLayout());
		this.setBounds(x, y, this.WIDTH, this.HEIGHT);
		this.setMinimumSize(new Dimension(this.WIDTH,this.HEIGHT));
		
		this.dataPath = MainWindow.getDataPath();
		
		
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
		
		
		this.okButton = new JButton("OK");
		this.okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
		
		
		JLabel label = new JLabel("<html><p align=center>"
                + "This is a non-modal dialog.<br>"
                + "You can have one or more of these up<br>"
                + "and still use the main window.");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(label.getFont().deriveFont(Font.PLAIN,
                                                     14.0f));

		
		
		firstPanel = new JPanel( );
		
		
		firstPanel.setPreferredSize(new Dimension(getWidth(),50));
		firstPanel.setBackground(Color.white);
		secondPanel = new JPanel();
		
		secondPanel.add(label);
		
		thirdPanel = new JPanel();
		
		thirdPanel.setLayout(new BoxLayout(thirdPanel,
                BoxLayout.LINE_AXIS));
		
		thirdPanel.add(cancelButton);
		thirdPanel.setBorder(BorderFactory.
             createEmptyBorder(0,0,5,5));
		thirdPanel.add(okButton);
		
		//thirdPanel.
		
		this.getContentPane().add(firstPanel,BorderLayout.PAGE_START);
		this.getContentPane().add( new JSeparator());
		this.getContentPane().add(secondPanel,BorderLayout.CENTER);
		//this.getContentPane().add( new JSeparator());
		this.getContentPane().add(thirdPanel,BorderLayout.PAGE_END);
	/*	JTextField projectName = new JTextField(20);
		
		JLabel label = new JLabel("<html><p align=center>"
                + "This is a non-modal dialog.<br>"
                + "You can have one or more of these up<br>"
                + "and still use the main window.");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(label.getFont().deriveFont(Font.PLAIN,
                                                     14.0f));

		 JPanel closePanel = new JPanel();
		 
		 closePanel.setLayout(new BoxLayout(closePanel,
                                            BoxLayout.LINE_AXIS));
         //closePanel.add(Box.createHorizontalGlue());
         closePanel.add(cancelButton);
         closePanel.setBorder(BorderFactory.
             createEmptyBorder(0,0,5,5));
         closePanel.add(okButton);
         
 		
 		
         
         JPanel contentPane = new JPanel(new BorderLayout());
         contentPane.add(projectName, BorderLayout.PAGE_START);
         contentPane.add(new JSeparator());
         contentPane.add(label, BorderLayout.CENTER);
         contentPane.add(new JSeparator());
         contentPane.add(closePanel, BorderLayout.PAGE_END);
         contentPane.setOpaque(true);
         this.setContentPane(contentPane);
		
		
		
		System.out.println(this.dataPath);
	}
	
	private void initButtons (){
		
	*/	
	}
	
	
	
	
	
}

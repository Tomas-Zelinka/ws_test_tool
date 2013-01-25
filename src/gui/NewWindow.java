package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private final int TOP_PANEL_HEIGHT = 50;
	private final int BOTTOM_PANEL_HEIGHT = 70;
	
	
	private String dialogName = "New Test Project";
	//private String dataPath;
	
	private JButton okButton;
	private JButton cancelButton;
	
	
	private static final long serialVersionUID = 9187751988881264097L;

	public NewWindow(){
		
		initWindow();
		initFirstPanel();
		initSecondPanel();
		initThirdPanel();
		//this.dataPath = MainWindow.getDataPath();
		
		this.add(firstPanel,BorderLayout.PAGE_START);
		this.add(secondPanel,BorderLayout.CENTER);
		this.add(thirdPanel,BorderLayout.PAGE_END);
	
	}
	
	/*
	 * 
	 */
	private void initWindow(){
		
		this.setTitle(dialogName);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		// Determine the new location of the window
		int x = (dim.width-(this.WIDTH))/2;
		int y = (dim.height-(this.HEIGHT))/2;
		 
		
		this.setLayout(new BorderLayout());
		this.setBounds(x, y, this.WIDTH, this.HEIGHT);
		this.setMinimumSize(new Dimension(this.WIDTH,this.HEIGHT));
		this.setModal(true);
	}
	
	/*
	 * 
	 */
	private void initFirstPanel(){
		JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
		firstPanel = new JPanel( );
		firstPanel.setPreferredSize(new Dimension(getWidth(),this.TOP_PANEL_HEIGHT));
		firstPanel.setBackground(Color.white);
		firstPanel.setLayout(new BorderLayout());
		firstPanel.add(separator, BorderLayout.PAGE_END);
		
	}
	
	/*
	 * 
	 */
	private void initSecondPanel(){

		JLabel label = new JLabel("<html><p align=center>"
                + "This is a non-modal dialog.<br>"
                + "You can have one or more of these up<br>"
                + "and still use the main window.");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(label.getFont().deriveFont(Font.PLAIN,
                                                     14.0f));
      
		secondPanel = new JPanel();
		secondPanel.setLayout(new BorderLayout());
		secondPanel.add(label,BorderLayout.CENTER);
	}
	
	
	
	/*
	 * 
	 */
	private void initThirdPanel(){

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
		
	
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.gray);
		
		thirdPanel = new JPanel();
		thirdPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		thirdPanel.setPreferredSize(new Dimension(getWidth(),this.BOTTOM_PANEL_HEIGHT));
		//separator.setSize(new Dimension(getWidth(),10));
		
		thirdPanel.setLayout(new BorderLayout());
		thirdPanel.add(separator,BorderLayout.NORTH);
		
		
		
		JPanel insidePanel = new JPanel();
		insidePanel.setPreferredSize(new Dimension(getWidth(),this.BOTTOM_PANEL_HEIGHT));
		insidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
		insidePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		insidePanel.add(cancelButton);
		insidePanel.add(okButton);
		
		thirdPanel.add(insidePanel);
			
	}
	
	

	
	
}

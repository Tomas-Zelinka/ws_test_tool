package modalWindows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public abstract class InputModalWindow extends JDialog {
	
	protected JPanel firstPanel;
	protected JPanel secondPanel;
	protected JPanel thirdPanel;
	protected JPanel thirdInsidePanel;
	protected JPanel secondInsidePanel;
	protected JLabel messageLabel;
	
	
	private final int TOP_PANEL_HEIGHT = 50;
	private final int BOTTOM_PANEL_HEIGHT = 70;
	
	private static final long serialVersionUID = 9187751988881264097L;

	public InputModalWindow(String name,int width, int height){
		
		initWindow(name, width, height);
		initComponents();
		initFirstPanel();
		initSecondPanel();
		putContent();
		initThirdPanel();
		
		this.add(firstPanel,BorderLayout.PAGE_START);
		this.add(secondPanel,BorderLayout.CENTER);
		this.add(thirdPanel,BorderLayout.PAGE_END);
	
	}
	
	/**
	 * 
	 * @param name
	 * @param width
	 * @param height
	 */
	private void initWindow(String name,int width, int height){
		
		this.setTitle(name);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		int x = (dim.width-(width))/2;
		int y = (dim.height-(height))/2;
		 
		
		this.setLayout(new BorderLayout());
		this.setBounds(x, y, width, height);
		this.setMinimumSize(new Dimension(width,height));
		this.setModal(true);
	}
	
	/**
	 * 
	 */
	private void initFirstPanel(){
		JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.red);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
        firstPanel = new JPanel( );
		firstPanel.setPreferredSize(new Dimension(getWidth(),this.TOP_PANEL_HEIGHT));
		firstPanel.setBackground(Color.white);
		firstPanel.setLayout(new BorderLayout());
		firstPanel.add(messageLabel,BorderLayout.CENTER);
		firstPanel.add(separator, BorderLayout.PAGE_END);
		
	}
	
	/**
	 * 
	 */
	protected void initSecondPanel(){
		
		secondPanel = new JPanel();
		secondInsidePanel = new JPanel();
		secondPanel.setLayout(new BorderLayout());
		secondPanel.add(secondInsidePanel,BorderLayout.NORTH);
		
	}
	
	/**
	 * 
	 */
	abstract protected void putContent();
	
	/**
	 * 
	 */
	abstract protected void initComponents();
	
	/**
	 * 
	 */
	private void initThirdPanel(){

		initThirdInsidePanel();	
		initButtons();
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.gray);
		
		thirdPanel = new JPanel();
		thirdPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		thirdPanel.setPreferredSize(new Dimension(getWidth(),this.BOTTOM_PANEL_HEIGHT));
		thirdPanel.setLayout(new BorderLayout());
		thirdPanel.add(separator,BorderLayout.NORTH);
		thirdPanel.add(thirdInsidePanel);
	}
	
	/**
	 * 
	 */
	private void  initThirdInsidePanel(){
		thirdInsidePanel = new JPanel();
		thirdInsidePanel.setPreferredSize(new Dimension(getWidth(),this.BOTTOM_PANEL_HEIGHT));
		thirdInsidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
		thirdInsidePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	}
	
	/**
	 * 
	 * @return
	 */
	abstract protected String testEmptyInput();
	
	/**
	 * 
	 */
	abstract protected void initButtons();
	
	/**
	 * 
	 * @return
	 */
	protected JPanel getFirstPanel(){
		return this.firstPanel;
	}
	
	/**
	 * @return
	 */
	protected JPanel getSecondPanel(){
		return this.secondPanel;
	}
	
	/**
	 * @return
	 */
	protected JPanel getThirdPanel(){
		return this.thirdPanel;
	}
	
	/**
	 * @return
	 */
	protected JPanel getSecondInsidePanel(){
		return this.secondInsidePanel;
	}
	
	/**
	 * @return
	 */
	protected JPanel getThirdInsidePanel(){
		return this.thirdInsidePanel;
	}
	
	/**
	 * 
	 * @param button
	 */
	protected void addButton(JButton button){
		
		getThirdInsidePanel().add(button);
	}
	
	/**
	 * 
	 * @param comp
	 */
	protected void addToSecondPanel(Component comp){
		getSecondInsidePanel().add(comp);
	}
	
	

}

	

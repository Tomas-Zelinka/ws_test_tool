package cz.vutbr.fit.dp.xzelin15.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JTree;

/**
 * 
 * This class extends JTree because of highlighting the selected nodes
 * 
 * @author Tomas Zelinka, xzelin15@stud.fit.vutbr.cz
 *
 */
public class HighlightedTree extends JTree {
	
	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = 8741586474668272227L;
	
	/**
	 * Filling color of selected rectangle
	 */
	public static Color selectedColor = new Color(115,164,209);
	
	/**
	 * Border color of selected rectangle
	 */
	public static Color selectedBorderColor = new Color(57,105,138);
	
	/**
	 * Overridden method for own drawing of the selected tree node
	 * 
	 *  @param g - Graphic context for the component
	 */
	@Override
	protected void paintComponent(Graphics g) {

		// paint background
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());

		// paint selected node's background and border
		int fromRow = getRowForPath( getSelectionPath());
		
		/*it will color the rectangle around selected node
		 * we have to get bounds around selected row and
		 * according these bounds we can fill the rectangle
		 * around the row 
		*/
		if (fromRow != -1) {
			int toRow = fromRow + 1;
			Rectangle fromBounds = getRowBounds(fromRow);
			Rectangle toBounds = getRowBounds(toRow - 1);
			if (fromBounds != null && toBounds != null) {
				g.setColor(selectedColor);
				g.fillRect(0, fromBounds.y, getWidth(), toBounds.y - fromBounds.y + toBounds.height);
				g.setColor(selectedBorderColor);
				g.drawRect(0, fromBounds.y, getWidth() - 1, toBounds.y - fromBounds.y + toBounds.height);
			}
		}

		//set opaque of node to see our drawn rectangle
		setOpaque(false); 
		super.paintComponent(g);
		
	}
}

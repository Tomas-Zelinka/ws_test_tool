package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JTree;

public class HighlightedTree extends JTree {
	
	private static final long serialVersionUID = 8741586474668272227L;
	public static Color selectedColor = new Color(115,164,209);
	public static Color selectedBorderColor = new Color(57,105,138);
	
	
	public HighlightedTree(){
		
	}
	
	protected void paintComponent(Graphics g) {

		// paint background
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());

		// paint selected node's background and border
		int fromRow = getRowForPath( getSelectionPath());
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

		// perform operation of superclass
		setOpaque(false); // trick not to paint background
		super.paintComponent(g);
		setOpaque(false);
	}
}

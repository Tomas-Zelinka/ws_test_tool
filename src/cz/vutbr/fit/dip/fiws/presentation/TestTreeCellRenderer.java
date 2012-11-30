/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dip.fiws.presentation;

import cz.vutbr.fit.dip.fiws.business.Test;
import cz.vutbr.fit.dip.fiws.business.TestStatement;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Trida reprezentujici render pro vykreslovani ikon v komponente JTree. V zavislosti na typu uzivatelskeho
 * objektu zobrazime prislusnou ikonu.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public class TestTreeCellRenderer extends DefaultTreeCellRenderer {

	
	
	
	/**
	 * Metoda predefinovava puvodni metodu tridy DefaultTreeCellRenderer pro vykresleni jednotlivych uzlu
	 * JTree komponenty.
	 * @param tree komponenta
	 * @param value objekt uzlu
	 * @param sel
	 * @param expanded true, pokud je slozka otevrena
	 * @param leaf true, pokud se jedna o list stromu
	 * @param row radek stromu
	 * @param hasFocus true pokud uzel ma focus
	 * @return vykresleny uzel
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, 
			boolean leaf, int row, boolean hasFocus) {
		
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode renderedNode= (DefaultMutableTreeNode) value;
		Object renderedNodeObject= renderedNode.getUserObject();
		if (renderedNodeObject instanceof Test) {
			setIcon(new ImageIcon(getClass().getResource("/resources/test_small.png")));
		}
		else
			if (renderedNodeObject instanceof TestStatement)
				setIcon(new ImageIcon(getClass().getResource("/resources/statement_small.png")));
			else
				setIcon(getDefaultClosedIcon());
		
		return this;
	}
	
	
	
	
	
}

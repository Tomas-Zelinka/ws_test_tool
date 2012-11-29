/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 3.2.2012
 */
package cz.vutbr.fit.dip.fiws.presentation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JEditorPane;

/**
 * Trida rozsiruje komponentu JEditorPane tak, aby pro vykreslovani fontu byl pouzit antialiasing.
 * @author Martin Zouzelka (xzouze00)
 */
public class AntiAliasedEditorPane extends JEditorPane{
	
	@Override
	public void paintComponent(Graphics graphics) {
		
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		super.paintComponent(graphics2D);
			
	}
	
}

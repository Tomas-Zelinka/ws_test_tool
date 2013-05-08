/**
 * Injekce poruch pro webove sluzby
 * Diplomovy projekt
 * Fakulta informacnich technologii VUT Brno
 * 16.2.2012
 */
package proxyUnit;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Soucast navrhoveho vzoru Observer. Vsichni odberatele, kteri se zajimaji o prichod nove zpravy ve tride
 * Controller musi implementovat toto rozhrani. Timto zpusobem je prezentacni vrstva upozornovana na nove
 * zpravy, ktere jsou nasledne zobrazeny v tabulce.
 * @author Martin Zouzelka (xzouze00@stud.fit.vutbr.cz)
 */
public interface ProxyListener extends Remote {
	
	
	public void onNewMessageEvent(int interactionId, HttpInteraction interaction) throws RemoteException;
	public void onUnknownHostEvent() throws RemoteException;	
	
}

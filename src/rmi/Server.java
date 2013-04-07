package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
 
public class Server {
 
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(1099);
        Hello hello = new HelloImpl();
        Naming.rebind("server.Hello", hello);
        System.out.println("server.RMI Server is ready.");
    }
}

package rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public void ConnectionTest() throws RemoteException, MalformedURLException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("pc02");
        Hello hello = (Hello) registry.lookup("server.Hello");
        System.out.println(hello.greeting());
    }
}
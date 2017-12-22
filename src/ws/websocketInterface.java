package ws;

import java.rmi.Remote;

public interface websocketInterface extends Remote {
    boolean print_on_websocket(String m) throws java.rmi.RemoteException;
}
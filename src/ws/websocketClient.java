package ws;

import javax.websocket.Session;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class websocketClient extends UnicastRemoteObject implements websocketInterface{

    private websocketAnnotation web;
    public websocketClient() throws RemoteException {
        super();
    }
    public websocketClient(websocketAnnotation w) throws RemoteException {
        super();
        this.web = w;
    }

    private Session session;
    private String username;


    public boolean print_on_websocket(String m) throws RemoteException {
        try {
            this.web.print_on_websocket(m);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public websocketAnnotation getWeb() {
        return web;
    }

    public void setWeb(websocketAnnotation web) {
        this.web = web;
    }
}



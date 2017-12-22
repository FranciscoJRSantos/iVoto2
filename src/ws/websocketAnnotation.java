package ws;

import RMIServer.ServerInterface;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class websocketAnnotation implements Serializable,websocketInterface{

    private static final AtomicInteger sequence = new AtomicInteger(1);
    private String username;
    private ServerInterface server;
    private Session session;
    private websocketClient aux;
    private websocketAnnotation web;
    private static final Set<websocketAnnotation> users = new CopyOnWriteArraySet<>();

    public websocketAnnotation() {}

    public websocketAnnotation(websocketClient a) {
        this.aux = a;
    }

    public websocketAnnotation(String a, Session s) {
        this.username = a;
        this.session = s;
    }
    @OnOpen
    public void start(Session session) throws RemoteException{
        System.out.println("At start of WebSocketAnnotation");


        this.session = session;

        try {
            this.server = (ServerInterface) Naming.lookup("//127.0.0.1:1099/ivoto");
            this.users.add(this);

            aux = new websocketClient(this);
            this.server.subscribeWeb((websocketInterface) aux);
        }
        catch(NotBoundException |MalformedURLException |RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void end() throws RemoteException{
        System.out.println("WebSocketAnnotation end()");
        // clean up once the WebSocket connection is closed
        this.server.unsubscribeWeb(aux);
        users.remove(this);
    }

    @OnMessage
    public void receiveMessage(String message) {
        // one should never trust the client, and sensitive HTML
        // characters should be replaced with &lt; &gt; &quot; &amp;
        String upperCaseMessage = message.toUpperCase();
        sendMessage("[" + username + "] " + upperCaseMessage);
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    public void sendMessage(String text) {
        // uses *this* object's session to call sendText()
        try {
            this.session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean print_on_websocket(String m) throws RemoteException{
        System.out.println("Entrei em print_on_websocket() WebSocketAnnotation");
        try {
            for(websocketAnnotation user:users){
                System.out.println("Iterating users...");
                user.session.getBasicRemote().sendText(m);

            }
        } catch (IOException e) {
            // clean up once the WebSocket connection is closed
            try {
                this.session.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }


}


package Beans;

import Helpers.ConfigHelper;
import RMIServer.ServerInterface;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Bean implements Serializable{

    ServerInterface server = null;

    public Bean(){

        try{
            ConfigHelper config = new ConfigHelper();
            String RMIName = config.getRMIName();

            this.server = (ServerInterface) Naming.lookup(RMIName);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}

package Beans;

import RMIServer.*;
import Helpers.ConfigHelper;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

class Bean {

    ServerInterface server;

    Bean(){

        try{
            ConfigHelper config = new ConfigHelper();
            String RMIName = config.getRMIName();

            server = (ServerInterface) Naming.lookup(RMIName);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}

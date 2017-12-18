package Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UnidadeOrganicaBean extends Bean{

    public String [] fillable = {"Nome", "Nome Faculdade"};

    public UnidadeOrganicaBean(){
        super();
    }

    public ArrayList<String> getUnidadesOrganicas(){
        try{
            return server.showAllUO();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UnidadeOrganicaBean extends Bean{

    private ArrayList<String> unidades_organicas;
    private String pertence;
    private String nome;

    public UnidadeOrganicaBean(){
        super();
    }

    public ArrayList<String> getUnidadesOrganicas(){
        try{
            this.unidades_organicas = server.showAllUO();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
        return this.unidades_organicas;
    }
    
    public boolean createUnidadeOrganica(){
        try{
            this.server.createUnidadeOrganica(this.nome,this.pertence);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setPertence(String pertence) {
        this.pertence = pertence;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

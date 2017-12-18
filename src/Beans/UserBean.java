package Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserBean extends Bean{

    public String [] fillable = {"Numero_cc", "Username","Password","Email", "Tipo", "Morada", "Contacto"};
    private Integer numero_cc = null;
    private String username = null;
    private String password = null;
    private ArrayList<ArrayList<String>> eleicoes = null;

    public UserBean(){
        super();
    }

    public boolean tryLogin(){
        try {
            return this.server.checkLogin(this.numero_cc,this.username,this.password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setNumeroCC(String numeroCC) {
        this.numero_cc = Integer.valueOf(numeroCC);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<ArrayList<String>> getEleicoesDecorrrer(){
        try {
            eleicoes = server.showEleicoesDecorrer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return eleicoes;
    }
}

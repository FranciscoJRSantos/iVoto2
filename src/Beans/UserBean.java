package Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserBean extends Bean{

    private Integer numero_cc = null;
    private String username = null;
    private String password = null;
    private ArrayList<ArrayList<String>> eleicoes = null;
    private ArrayList<String> utilizadores = null;
    private String morada;
    private Integer contacto;
    private String validade_cc;
    private Integer tipo;
    private String un_org_nome;

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

    public boolean createUser(){
        try {
            return this.server.createUser(this.numero_cc,this.username ,this.password,this.morada, this.contacto, this.validade_cc, this.tipo, this.un_org_nome);
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

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setContacto(Integer contacto) {
        this.contacto = contacto;
    }

    public void setValidade_cc(String validade_cc) {
        this.validade_cc = validade_cc;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public void setUn_org_nome(String un_org_nome) {
        this.un_org_nome = un_org_nome;
    }

    public void setEleicoesDecorrrer(){
        try {
            this.eleicoes = server.showEleicoesDecorrer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getUsers() {
        try {
            this.utilizadores = server.showAllUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return this.utilizadores;
    }

    public void logoutRMI(String ncc){
        try {
            server.logoutUser(ncc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

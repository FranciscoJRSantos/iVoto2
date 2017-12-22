package Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserBean extends Bean{

    private Integer numero_cc = null;
    private String username = null;
    private String password = null;
    private String facebookID = null;
    private String facebookToken = null;
    private ArrayList<ArrayList<String>> eleicoes = null;
    private ArrayList<ArrayList<String>> utilizadores = null;
    private String morada;
    private Integer contacto;
    private String validade_cc;
    private Integer tipo;
    private String un_org_nome;
    private Integer eleicao_id;
    private String lista;
    private Integer numeroCCNovo;

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

    public String checkIfCanVote(){
        try {
            return this.server.checkCC(this.numero_cc, this.eleicao_id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer checkFacebookID(){
        try {
            return this.server.findFacebookID(this.facebookID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFacebookID(){
        try {
            return this.server.getUserFacebookID(this.numero_cc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFacebookToken(){
        try {
            return this.server.getUserFacebookToken(this.numero_cc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean linkAccount(){
        try {

            return this.server.linkFacebook(this.numero_cc, this.facebookID, this.facebookToken);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlinkAccount(){
        try {
            return this.server.unlinkFacebook(this.numero_cc);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFacebookToken(){
        try {
            return this.server.updateUserFacebookToken(this.numero_cc, this.facebookToken);
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

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
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

    public ArrayList<ArrayList<String>> getUsers() {
        try {
            this.utilizadores = server.showAllUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return this.utilizadores;
    }

    public ArrayList<String> pickListasFromEleicao() {
        try {
            return this.server.showListsFromElection(this.eleicao_id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setEleicao_id(Integer eleicao_id) {
        this.eleicao_id = eleicao_id;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public String vote(){
        try {
            return this.server.anticipatedVote(this.numero_cc,this.lista,this.eleicao_id,this.password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getEleicao_id() {
        return eleicao_id;
    }

    public boolean updateUtilizador() {
        try{
            return this.server.updateUser(this.numero_cc,this.numeroCCNovo,this.username,this.morada,this.password,this.validade_cc,this.contacto,this.un_org_nome);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setNumeroCCNovo(Integer numeroCCNovo) {
        this.numeroCCNovo = numeroCCNovo;
    }

    public ArrayList<String> getVotingInfo() {
        try {
            return this.server.showPersonVotingInfoAll(this.numero_cc);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

}

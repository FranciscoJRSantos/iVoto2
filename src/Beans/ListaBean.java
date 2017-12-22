package Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ListaBean extends Bean{

    private String nome;
    private Integer eleicao_id;
    private Integer numero_cc;
    private Integer tipo;
    private ArrayList<String> listasFromEleicao;

    public ListaBean() { super(); }


    public void setEleicao_ID(Integer eleicao_id) {
        this.eleicao_id = eleicao_id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean createLista() {
        try {
            this.server.createLista(this.nome,this.tipo,this.eleicao_id,this.numero_cc);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setNumero_cc(Integer numero_cc) {
        this.numero_cc = numero_cc;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getListasFromEleicao() {
        try {
            return this.server.showListsFromElection(this.eleicao_id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

}

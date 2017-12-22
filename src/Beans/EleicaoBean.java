package Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class EleicaoBean extends Bean {

    private ArrayList<ArrayList<String>> eleicoes;
    private String descricao;
    private String nome;
    private String inicio;
    private String fim;
    private Integer tipo;
    private String unidade_organica;
    private Integer toShowID;
    private ArrayList<String> eleicaoToShow;
    private ArrayList<String> totalVotos;
    private ArrayList<ArrayList<String>> results;

    public EleicaoBean() { super(); }

    public ArrayList<ArrayList<String>> getEleicoesFuturas() {
        try {
            this.eleicoes = server.showEleicoesFuturas();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return eleicoes;
    }

    public ArrayList<ArrayList<String>> getEleicoesDecorrer() {
        try {
            this.eleicoes = server.showEleicoesDecorrer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return eleicoes;
    }

    public boolean createEleicao(){
        try{
            return this.server.createEleicao(this.nome,this.inicio,this.fim,this.descricao,this.tipo,this.unidade_organica);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateEleicao(){
        try{
            return this.server.updateEleicao(this.toShowID,this.nome,this.inicio,this.fim,this.descricao,this.tipo,this.unidade_organica);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public void setUnidadeOrganica(String unidade_organica) {
        this.unidade_organica= unidade_organica;
    }

    public ArrayList<String> getEleicaoToShow() {
        try {
            return this.eleicaoToShow = this.server.showEleicao(this.toShowID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setToShowID(Integer toShowID) {
        this.toShowID = toShowID;
    }

    public void setEleicaoToShow(ArrayList<String> eleicaoToShow) {
        this.eleicaoToShow = eleicaoToShow;
    }

    public Integer getToShowID() {
        return this.toShowID;
    }

    public ArrayList<ArrayList<String>> getResults() {
        try{
            this.results = this.server.showResultadosFromEleicao(this.toShowID);
            return this.results;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ArrayList<String>> getEleicoesPassadas() {
        try {
            this.eleicoes = server.showEleicoesPassadas();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return eleicoes;
    }
}

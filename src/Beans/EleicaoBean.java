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
}

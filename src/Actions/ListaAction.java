package Actions;

import Beans.EleicaoBean;
import Beans.ListaBean;

import java.util.ArrayList;

public class ListaAction extends Action{

    private String nome;
    private String eleicao_id;
    private ListaBean bean = new ListaBean();
    private String numero_cc;
    private String tipo;
    private ArrayList<ArrayList<String>> eleicoes;
    private ArrayList<String> eleicoes_id;
    private ArrayList<String> eleicoes_titulo;
    private ArrayList<String> eleicoes_local;

    public String create() throws Exception {
        this.bean.setEleicao_ID(Integer.parseInt(this.eleicao_id));
        this.bean.setNome(this.nome);
        this.bean.setNumero_cc(Integer.parseInt(this.numero_cc));
        this.bean.setTipo(Integer.parseInt(this.tipo));
        if(this.bean.createLista()){
            return "success";
        }
        return "error";
    }

    public String show() throws Exception {
        return "success";
    }

    public String showAll() throws Exception {
        return "success";
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEleicao_id(String eleicao_id) {

        this.eleicao_id = eleicao_id;
    }

    public void setNumero_cc(String numero_cc) {
        this.numero_cc = numero_cc;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getEleicoes_id() {
        this.eleicoes = new EleicaoBean().getEleicoesFuturas();
        this.eleicoes_id = this.eleicoes.get(0);
        return eleicoes_id;
    }

    public ArrayList<String> getEleicoes_titulo() {
        this.eleicoes = new EleicaoBean().getEleicoesFuturas();
        this.eleicoes_titulo = this.eleicoes.get(1);
        return eleicoes_titulo;
    }

    public ArrayList<String> getEleicoes_local() {
        this.eleicoes = new EleicaoBean().getEleicoesFuturas();
        this.eleicoes_local = this.eleicoes.get(2);
        return eleicoes_local;
    }
}

package Actions;

import Beans.EleicaoBean;
import Beans.UnidadeOrganicaBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;

public class EleicaoAction extends Action implements SessionAware{

    private String descricao;
    private String nome;
    private String inicio;
    private String fim;
    private Integer tipo;
    private String unidade_organica_nome;
    private EleicaoBean bean;
    private ArrayList<String> unidades_organicas;
    private ArrayList<ArrayList<String>> eleicoes;
    private ArrayList<String> eleicoes_id;
    private ArrayList<String> eleicoes_titulo;
    private ArrayList<String> eleicoes_local;
    private ArrayList<String> eleicaoToShow;
    private Integer toShowID;

    public String create() throws Exception {
        this.bean = new EleicaoBean();
        this.bean.setNome(this.nome);
        this.bean.setDescricao(this.descricao);
        this.bean.setInicio(this.inicio);
        System.out.println(this.inicio);
        this.bean.setFim(this.fim);
        System.out.println(this.fim);
        this.bean.setTipo(this.tipo);
        this.bean.setUnidadeOrganica(this.unidade_organica_nome);
        if(this.bean.createEleicao()){
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

    public String getFim() {
        return this.fim;
    }

    public String getInicio() {
        return this.inicio;
    }

    public ArrayList<String> getUnidades_organicas() {
        this.unidades_organicas = new UnidadeOrganicaBean().getUnidadesOrganicas();
        System.out.println(this.unidades_organicas);
        return unidades_organicas;
    }

    public void setUnidade_organica_nome(String unidade_organica_nome) {
        this.unidade_organica_nome = unidade_organica_nome;
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

    public ArrayList<ArrayList<String>> getEleicoes() {
        this.eleicoes = new EleicaoBean().getEleicoesFuturas();
        return this.eleicoes;
    }

    public ArrayList<String> getEleicaoToShow() {
        EleicaoBean bean = new EleicaoBean();
        bean.setToShowID(this.toShowID);
        eleicaoToShow = bean.getEleicaoToShow();
        return eleicaoToShow;
    }

    public void setToShowID(Integer toShowID) {
        this.toShowID = toShowID;
    }
}

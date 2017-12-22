package Actions;

import Beans.EleicaoBean;
import Beans.FacebookApi2;
import Beans.UnidadeOrganicaBean;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
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
    private ArrayList<String> eleicoes_passadas_id;
    private ArrayList<String> eleicoes_passadas_titulo;
    private ArrayList<String> eleicoes_passadas_local;
    private Integer toShowID;
    private ArrayList<ArrayList<String>> results;
    private ArrayList<String> total_votos;
    private ArrayList<String> nome_lista;
    private ArrayList<String> votos_lista;
    private ArrayList<String> percentagem_votos;

    public String create() throws Exception {
        this.bean = new EleicaoBean();
        this.bean.setNome(this.nome);
        this.bean.setDescricao(this.descricao);
        this.bean.setInicio(this.inicio);
        this.bean.setFim(this.fim);
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
        this.bean = new EleicaoBean();
        bean.setToShowID(this.toShowID);
        eleicaoToShow = bean.getEleicaoToShow();
        return eleicaoToShow;
    }

    public void setToShowID(Integer toShowID) {
        this.toShowID = toShowID;
    }

    public String updateEleicao() throws Exception {
        this.bean = new EleicaoBean();
        this.bean.setToShowID(this.toShowID);
        this.bean.setNome(this.nome);
        this.bean.setDescricao(this.descricao);
        this.bean.setInicio(this.inicio);
        this.bean.setFim(this.fim);
        this.bean.setTipo(this.tipo);
        this.bean.setUnidadeOrganica(this.unidade_organica_nome);
        if(this.bean.updateEleicao()){
            return "success";
        }
        return "error";
    }

    public Integer getToShowID() {
        String paramValue = ServletActionContext.getRequest().getParameter("toShowID");
        return Integer.parseInt(paramValue);
    }

    public String showResultados() throws Exception {
        return "success";
    }

    public ArrayList<String> getTotal_votos() {
        bean = new EleicaoBean();
        bean.setToShowID(this.toShowID);
        this.results = bean.getResults();
        this.total_votos = this.results.get(0);
        return total_votos;
    }

    public ArrayList<String> getNome_lista() {
        bean = new EleicaoBean();
        bean.setToShowID(this.toShowID);
        this.results = bean.getResults();
        this.nome_lista = this.results.get(1);
        return nome_lista;
    }

    public ArrayList<String> getVotos_lista() {
        bean = new EleicaoBean();
        bean.setToShowID(this.toShowID);
        this.results = bean.getResults();
        this.votos_lista = this.results.get(2);
        return votos_lista;
    }

    public ArrayList<String> getPercentagem_votos() {
        bean = new EleicaoBean();
        bean.setToShowID(this.toShowID);
        this.results = bean.getResults();
        this.percentagem_votos = this.results.get(3);
        return percentagem_votos;
    }

    public ArrayList<String> getEleicoes_passadas_id() {
        this.eleicoes = new EleicaoBean().getEleicoesPassadas();
        this.eleicoes_passadas_id = this.eleicoes.get(0);
        return eleicoes_passadas_id;
    }

    public ArrayList<String> getEleicoes_passadas_titulo() {
        this.eleicoes = new EleicaoBean().getEleicoesPassadas();
        this.eleicoes_passadas_titulo = this.eleicoes.get(1);
        return this.eleicoes_passadas_titulo;
    }

    public ArrayList<String> getEleicoes_passadas_local() {
        this.eleicoes = new EleicaoBean().getEleicoesPassadas();
        this.eleicoes_passadas_local = this.eleicoes.get(1);
        return this.eleicoes_passadas_local ;
    }

    public String showPast() throws Exception {
        return "success";
    }
}

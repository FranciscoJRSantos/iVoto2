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
        System.out.println("OIIIIIIIIIIII");
        EleicaoBean bean = new EleicaoBean();
        bean.setToShowID(this.toShowID);
        eleicaoToShow = bean.getEleicaoToShow();
        return eleicaoToShow;
    }

    public void setToShowID(Integer toShowID) {
        System.out.println("ENTAO???");
        this.toShowID = toShowID;
    }

    public String updateEleicao() throws Exception {
        String paramValue = ServletActionContext.getRequest().getParameter("toShowID");
        this.bean = new EleicaoBean();
        this.bean.setNome(this.nome);
        this.bean.setDescricao(this.descricao);
        this.bean.setInicio(this.inicio);
        this.bean.setFim(this.fim);
        this.bean.setTipo(this.tipo);
        this.bean.setUnidadeOrganica(this.unidade_organica_nome);
        return "success";
    }

    public String postElectionFacebook(){
        String paramValue = ServletActionContext.getRequest().getParameter("electionID");

        OAuthService service = new ServiceBuilder()
                .provider(FacebookApi2.class)
                .apiKey("157491105017602")
                .apiSecret("798b13e2014862882190ab49d5cebd0f")
                .scope("publish_actions")
                .build();
        String election_url = "http://localhost:8080/showListsFromElection.action?eleicaoToVote=" + paramValue;
        Token token = (Token) session.get("accessToken");
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://graph.facebook.com/me/feed", service);
        request.addBodyParameter("message", "Come vote at: " + election_url);
        service.signRequest(token, request);
        Response response = request.send();

        return "success";
    }
}

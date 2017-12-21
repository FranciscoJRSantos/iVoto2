package Actions;

import Beans.EleicaoBean;
import Beans.ListaBean;
import Beans.UserBean;
import Beans.FacebookApi2;      //not sure if it will keep being a bean
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;

public class UserAction extends Action implements SessionAware{

    private String numero_cc = null;
    private String username = null;
    private String password = null;
    private ArrayList<String> users = null;
    private String validade_cc;
    private String morada;
    private String contacto;
    private String tipo;
    private String unidade_organica;
    private ArrayList<ArrayList<String>> eleicoes;
    private ArrayList<String> eleicoes_id;
    private ArrayList<String> eleicoes_titulo;
    private ArrayList<String> eleicoes_local;
    private ArrayList<String> listas;
    private String eleicaoToVote;
    private String listaToVote;


    public String loginFacebook() throws Exception {
        OAuthService service = new ServiceBuilder()
                .provider(FacebookApi2.class)
                .apiKey("157491105017602")
                .apiSecret("798b13e2014862882190ab49d5cebd0f")
                .callback("http://localhost:8080/loginFacebook.action")
                .scope("publish_actions")
                .build();
        String paramValue = ServletActionContext.getRequest().getParameter("code");
        Verifier v = new Verifier(paramValue);
        Token accessToken = service.getAccessToken(null, v);
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me", service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
        return "success";
    }

    public String login() throws Exception {
        if (this.numero_cc == null || this.username == null || this.password == null || this.numero_cc.isEmpty() || this.username.isEmpty() || this.password.isEmpty()){
            return "error";
        }
        this.getUserBean().setNumeroCC(this.numero_cc);
        this.getUserBean().setUsername(this.username);
        this.getUserBean().setPassword(this.password);
        if (this.numero_cc.equals("0") && this.username.equals("admin") && this.password.equals("secret")){
            if (this.getUserBean() != null){
                session.put("numero_cc",this.username);
                session.put("loggedIn",true);
                return "admin";
            }
        }
        else if (this.getUserBean().tryLogin()){
            session.put("numero_cc",this.username);
            session.put("loggedIn",true);
            this.getUserBean().setEleicoesDecorrrer();
            return "success";
        }
        return "error";
    }

    public String create() throws Exception {
        this.getUserBean().setNumeroCC(this.numero_cc);
        this.getUserBean().setUsername(this.username);
        this.getUserBean().setPassword(this.password);
        this.getUserBean().setContacto(Integer.parseInt(this.contacto));
        this.getUserBean().setMorada(this.morada);
        this.getUserBean().setValidade_cc(this.validade_cc);
        this.getUserBean().setUn_org_nome(this.unidade_organica);
        this.getUserBean().setTipo(Integer.parseInt(this.tipo));
        if(this.getUserBean().createUser()){
            return "success";
        }
        else return "error";
    }

    private UserBean getUserBean(){
        if(!session.containsKey("UserBean")){
            this.setLoginBean(new UserBean());
        }
        return (UserBean) session.get("UserBean");
    }

    private void setLoginBean(UserBean userBean){
        session.put("UserBean",userBean);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumero_cc(String numero_cc) {
        this.numero_cc = numero_cc;
    }

    public void setUsername(String username) { this.username = username; }

    public void setValidade_cc(String validade_cc) { this.validade_cc = validade_cc; }

    public void setMorada(String morada) { this.morada = morada; }

    public void setContacto(String contacto) { this.contacto = contacto; }

    public void setUnidade_organica(String unidade_organica) { this.unidade_organica = unidade_organica; }

    public String show() throws Exception {
        return "success";
    }

    public String showAll() throws Exception {
        setUsers(this.getUserBean().getUsers());
        return "success";
    }

    public String vote() throws Exception {
        System.out.println(this.listaToVote);
        if (this.getUserBean().getEleicao_id() == null){
            return "error";
        }
        this.getUserBean().setLista(this.listaToVote);
        this.getUserBean().vote();
        return "success";
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String logout() throws Exception {
        this.session.remove("numero_cc");
        this.session.remove("loggedIn");
        this.session.remove("UserBean");
        return "success";
    }

    public ArrayList<ArrayList<String>> getEleicoes() {
        return new EleicaoBean().getEleicoesDecorrer();
    }

    public ArrayList<String> getEleicoes_id() {
        this.eleicoes = new EleicaoBean().getEleicoesDecorrer();
        this.eleicoes_id = this.eleicoes.get(0);
        return eleicoes_id;
    }

    public ArrayList<String> getEleicoes_titulo() {
        this.eleicoes = new EleicaoBean().getEleicoesDecorrer();
        this.eleicoes_titulo = this.eleicoes.get(1);
        return eleicoes_titulo;
    }

    public ArrayList<String> getEleicoes_local() {
        this.eleicoes = new EleicaoBean().getEleicoesDecorrer();
        this.eleicoes_local = this.eleicoes.get(2);
        return eleicoes_local;
    }

    public ArrayList<String> getListas() {
        String paramValue = ServletActionContext.getRequest().getParameter("eleicaoToVote");
        ListaBean bean = new ListaBean();
        bean.setEleicao_ID(Integer.parseInt(this.eleicaoToVote));
        this.getUserBean().setEleicao_id(Integer.parseInt(this.eleicaoToVote));
        this.listas = bean.getListasFromEleicao();
        return listas;
    }

    public void setListaToVote(String listaToVote) {
        this.listaToVote = listaToVote;
    }

    public String getEleicaoToVote() {
        return this.eleicaoToVote;
    }

    public void setEleicaoToVote(String eleicaoToVote) {
        this.eleicaoToVote = eleicaoToVote;
    }

    public String showListas() throws Exception {
        return "success";
    }
}

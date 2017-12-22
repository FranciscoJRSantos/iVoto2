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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class UserAction extends Action implements SessionAware {

    private String numero_cc = null;
    private String username = null;
    private String password = null;
    private String facebookID = null;
    private ArrayList<String> users = null;
    private String validade_cc;
    private String morada;
    private String contacto;
    private String tipo;
    private String unidade_organica;
    private ArrayList<ArrayList<String>> eleicoes;
    private ArrayList<ArrayList<String>> utilizadores = null;
    private ArrayList<String> listas;
    private ArrayList<String> cc = null;
    private ArrayList<String> nome = null;
    private String eleicaoToVote;
    private String listaToVote;
    private String userToShow;
    private Integer cc_novo;
    private ArrayList<String> userVotingInfo;
    private String post_id;


    public String loginFacebook() {
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
        JSONObject json = null;
        try {

            json = (JSONObject) (new JSONParser().parse(response.getBody()));
        } catch (ParseException e) {
            return "error";
        }
        facebookID = (String) json.get("id");
        this.getUserBean().setFacebookID(this.facebookID);
        Integer aux = this.getUserBean().checkFacebookID();
        if (aux != null) {
            this.getUserBean().setFacebookToken(accessToken.getToken());
            session.put("numero_cc", Integer.toString(aux));
            this.getUserBean().updateFacebookToken();
            session.put("facebookID", this.facebookID);
            session.put("accessToken", accessToken);
            session.put("loggedIn", true);
            return "success";
        }
        return "error";
    }

    public String linkFacebook() {
        OAuthService service = new ServiceBuilder()
                .provider(FacebookApi2.class)
                .apiKey("157491105017602")
                .apiSecret("798b13e2014862882190ab49d5cebd0f")
                .callback("http://localhost:8080/linkFacebook.action")
                .scope("publish_actions")
                .build();
        String paramValue = ServletActionContext.getRequest().getParameter("code");
        Verifier v = new Verifier(paramValue);
        Token accessToken = service.getAccessToken(null, v);
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me", service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        JSONObject json = null;
        try {
            json = (JSONObject) (new JSONParser().parse(response.getBody()));
        } catch (ParseException e) {
            return "error";
        }
        facebookID = (String) json.get("id");
        this.getUserBean().setFacebookID(this.facebookID);
        Integer aux = this.getUserBean().checkFacebookID();
        if (aux == null) {
            this.getUserBean().setFacebookToken(accessToken.getToken());
            if (this.getUserBean().linkAccount()) {
                session.put("facebookID", this.facebookID);
                session.put("accessToken", accessToken);
                return "success";
            }
        }
        return "error";
    }

    public String unlinkFacebook() {
        this.getUserBean().setFacebookID(null);
        this.getUserBean().setNumeroCC((String)session.get("numero_cc"));
        if (this.getUserBean().unlinkAccount()) {
            this.session.remove("facebookID");
            this.session.remove("accessToken");
            return "success";
        }
        //TODO: Can you request facebook to unlink it?
        return "error";
    }

    public String login() {
        if (this.numero_cc == null || this.username == null || this.password == null || this.numero_cc.isEmpty() || this.username.isEmpty() || this.password.isEmpty()) {
            return "error";
        }
        this.getUserBean().setNumeroCC(this.numero_cc);
        this.getUserBean().setUsername(this.username);
        this.getUserBean().setPassword(this.password);
        if (this.numero_cc.equals("0") && this.username.equals("admin") && this.password.equals("secret")) {
            if (this.getUserBean() != null) {
                session.put("numero_cc", this.numero_cc);
                session.put("loggedIn", true);
                return "admin";
            }
        } else if (this.getUserBean().tryLogin()) {
            session.put("numero_cc", this.numero_cc);

            this.facebookID = this.getUserBean().getFacebookID();
            this.getUserBean().setFacebookID(this.facebookID);
            session.put("facebookID", this.facebookID);

            if (this.facebookID!=null) {
                Token accessToken = new Token(this.getUserBean().getFacebookToken(), "");
                this.getUserBean().setFacebookToken(accessToken.getToken());
                session.put("accessToken", accessToken);
            }

            session.put("loggedIn", true);
            return "success";
        }
        return "error";
    }

    public String showPlatform(){
        eleicoes = loadEleicoes();
        return "success";
    }

    public String create() {
        this.getUserBean().setNumeroCC(this.numero_cc);
        this.getUserBean().setUsername(this.username);
        this.getUserBean().setPassword(this.password);
        this.getUserBean().setContacto(Integer.parseInt(this.contacto));
        this.getUserBean().setMorada(this.morada);
        this.getUserBean().setValidade_cc(this.validade_cc);
        this.getUserBean().setUn_org_nome(this.unidade_organica);
        this.getUserBean().setTipo(Integer.parseInt(this.tipo));
        if (this.getUserBean().createUser()) {
            return "success";
        } else return "error";
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
        JSONObject json = null;
        try {
            json = (JSONObject) (new JSONParser().parse(response.getBody()));
        } catch (ParseException e) {
            return "error";
        }
        post_id = (String) json.get("id");
        if(post_id == null){
            return "error";
        }
        return "success";
    }

    private UserBean getUserBean() {
        if (!session.containsKey("UserBean")) {
            this.setLoginBean(new UserBean());
        }
        return (UserBean) session.get("UserBean");
    }

    private void setLoginBean(UserBean userBean) {
        session.put("UserBean", userBean);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumero_cc(String numero_cc) {
        this.numero_cc = numero_cc;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setValidade_cc(String validade_cc) {
        this.validade_cc = validade_cc;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setUnidade_organica(String unidade_organica) {
        this.unidade_organica = unidade_organica;
    }

    public String show() throws Exception {
        return "success";
    }

    public String showAll() throws Exception {
        setUtilizadores(this.getUserBean().getUsers());
        return "success";
    }

    public String vote(){
        if (this.getUserBean().getEleicao_id() == null) {
            return "error";
        }
        this.getUserBean().setLista(this.listaToVote);
        if(this.getUserBean().vote()!=null) return "success";
        return "error";
    }

    public String postVote(){
        Integer id = this.getUserBean().getEleicao_id();
        if (id == null) {
            return "error";
        }
        OAuthService service = new ServiceBuilder()
                .provider(FacebookApi2.class)
                .apiKey("157491105017602")
                .apiSecret("798b13e2014862882190ab49d5cebd0f")
                .scope("publish_actions")
                .build();
        String election_url = "http://localhost:8080/showListsFromElection.action?eleicaoToVote=" + id;
        Token token = (Token) session.get("accessToken");
        OAuthRequest request = new OAuthRequest(Verb.POST, "https://graph.facebook.com/me/feed", service);
        request.addBodyParameter("message", "I've just voted on this election: " + election_url);
        service.signRequest(token, request);
        Response response = request.send();
        JSONObject json = null;
        try {
            json = (JSONObject) (new JSONParser().parse(response.getBody()));
        } catch (ParseException e) {
            return "error";
        }
        post_id = (String) json.get("id");
        if(post_id == null){
            return "error";
        }
        return "success";
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<ArrayList<String>> getUsers() {
        return utilizadores;
    }

    public void setUtilizadores(ArrayList<ArrayList<String>> users) {
        this.utilizadores = users;
    }

    public String logout() {
        this.session.remove("numero_cc");
        this.session.remove("facebookID");
        this.session.remove("accessToken");
        this.session.remove("loggedIn");
        this.session.remove("UserBean");
        return "success";
    }

    public ArrayList<ArrayList<String>> loadEleicoes() {
        ArrayList<String> f_id = new ArrayList<String>();
        ArrayList<String> f_ti = new ArrayList<String>();
        ArrayList<String> f_lo = new ArrayList<String>();
        ArrayList<ArrayList<String>> aux = new EleicaoBean().getEleicoesDecorrer();
        ArrayList<String> aux_id = aux.get(0);
        ArrayList<String> aux_ti = aux.get(1);
        ArrayList<String> aux_lo = aux.get(2);
        this.getUserBean().setNumeroCC((String) this.session.get("numero_cc"));
        for (int i = 0; i <aux_id.size(); i++) {
            String id = aux_id.get(i);
            this.getUserBean().setEleicao_id(Integer.parseInt(id));
            if(this.getUserBean().checkIfCanVote() != null){
                f_id.add(aux_id.get(i));
                f_ti.add(aux_ti.get(i));
                f_lo.add(aux_lo.get(i));
            }
        }
        this.getUserBean().setEleicao_id(null);
        ArrayList<ArrayList<String>> f = new ArrayList<ArrayList<String>>();
        f.add(f_id);
        f.add(f_ti);
        f.add(f_lo);
        return f;
    }

    public ArrayList<ArrayList<String>> getEleicoes() {
        return eleicoes;
    }

    public ArrayList<String> getEleicoes_id() {
        return this.eleicoes.get(0);
    }

    public ArrayList<String> getEleicoes_titulo() {
        return this.eleicoes.get(1);
    }

    public ArrayList<String> getEleicoes_local() {
        return this.eleicoes.get(2);
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

    public ArrayList<String> getCc() {
        return this.getUserBean().getUsers().get(0);
    }

    public ArrayList<String> getNome() {
        return this.getUserBean().getUsers().get(1);
    }

    public String getUserToShow() {
        String paramValue = ServletActionContext.getRequest().getParameter("userToShow");
        System.out.println(paramValue);
        this.userToShow = paramValue;
        return this.userToShow;
    }

    public void setUserToShow(String userToShow) {
        this.userToShow = userToShow;
    }

    public String updateUtilizador() throws Exception {
        this.getUserBean().setNumeroCC(this.numero_cc);
        this.getUserBean().setNumeroCCNovo(this.cc_novo);
        this.getUserBean().setValidade_cc(this.validade_cc);
        this.getUserBean().setUsername(this.username);
        this.getUserBean().setPassword(this.password);
        this.getUserBean().setTipo(Integer.parseInt(this.tipo));
        this.getUserBean().setUn_org_nome(this.unidade_organica);
        this.getUserBean().setMorada(this.morada);
        this.getUserBean().setContacto(Integer.parseInt(this.contacto));
        if(this.getUserBean().updateUtilizador()){

        }
        return "success";
    }

    public String showVoteDetails() throws Exception {
        return "success";
    }

    public ArrayList<String> getUserVotingInfo() {
        this.getUserBean().setNumeroCC(getUserToShow());
        this.userVotingInfo = this.getUserBean().getVotingInfo();
        return this.userVotingInfo;
    }

    public String getPost_id() {
        return post_id;
    }
}

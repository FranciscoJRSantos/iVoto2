package Actions;

import Beans.UserBean;
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

    public String loginFacebook() throws Exception {
        return "success";
    }

    public String login() throws Exception {
        if (this.numero_cc == null || this.username == null || this.password == null){
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
        System.out.println(this.session);
        this.getUserBean().logoutRMI(numero_cc);
        return "success";
    }
}

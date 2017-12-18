package Actions;

import Beans.UserBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;

public class UserAction extends Action implements SessionAware{

    private String numero_cc = null;
    private String username = null;
    private String password = null;
    private UserBean bean = null;
    private ArrayList<ArrayList<String>> eleicoes;

    public String login() throws Exception {
        this.getUserLoginBean().setNumeroCC(this.numero_cc);
        this.getUserLoginBean().setUsername(this.username);
        this.getUserLoginBean().setPassword(this.password);
        if (this.numero_cc.equals("0") && this.username.equals("admin") && this.password.equals("secret")){
            session.put("username",this.username);
            session.put("password",this.password);
            return "admin";
        }
        else if (this.getUserLoginBean().tryLogin()){
            session.put("numero_cc",this.numero_cc);
            session.put("username",this.username);
            session.put("password",this.password);
            eleicoes = getUserLoginBean().getEleicoesDecorrrer();
            return "success";
        }
        return "error";
    }

    public String create() throws Exception {
        return "success";
    }

    private UserBean getUserLoginBean(){
        if(!session.containsKey("UserLoginBean")){
            this.setLoginBean(new UserBean());
        }
        return (UserBean) session.get("UserLoginBean");
    }

    private void setLoginBean(UserBean userBean){
        session.put("UserLoginBean",userBean);
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumero_cc(String numero_cc) {
        this.numero_cc = numero_cc;
    }

    public void setUsername(String username) { this.username = username; }

    public String show() throws Exception {
        return "success";
    }

}

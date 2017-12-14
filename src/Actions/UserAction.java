package Actions;

import Beans.UserBean;

import java.util.Map;

public class UserAction extends Action{

    private Map session;
    private String numero_cc = null;
    private String username = null;
    private String password = null;

    public String checkLogin() throws Exception {
        if (!session.containsKey("UserLoginBean")){
            return "error";
        }
        return "success";
    }

    public String login() throws Exception {
        System.out.println(this.username);
        System.out.println(this.password);
        this.getUserLoginBean().setNumeroCC(this.numero_cc);
        this.getUserLoginBean().setUsername(this.username);
        this.getUserLoginBean().setPassword(this.password);
        if (this.getUserLoginBean().tryLogin()){
            session.put("numero_cc",this.numero_cc);
            session.put("username",this.username);
            session.put("password",this.password);
            return "loginSucess";
        }
        return "loginError";
    }

    public String register() throws Exception {
        return "registerSucess";
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumero_cc(String numero_cc) {
        this.numero_cc = numero_cc;
    }

    public void setUsername(String username) { this.username = username; }

    private UserBean getUserLoginBean(){
        if(!session.containsKey("UserLoginBean")){
            this.setLoginBean(new UserBean());
        }
        return (UserBean) session.get("UserLoginBean");
    }

    private void setLoginBean(UserBean userBean){
        this.session.put("UserLoginBean",userBean);
    }

    @Override
    public void setSession(Map map){
        this.session = map;
    }

}

package Beans;

import java.rmi.RemoteException;

public class UserBean extends Bean{

    public String [] fillable = {"Numero_cc", "Username","Password","Email", "Tipo", "Morada", "Contacto"};
    private Integer numero_cc = null;
    private String username = null;
    private String password = null;

    public UserBean(){
        super();
    }

    public boolean tryLogin() throws RemoteException {
        return this.server.checkLogin(this.numero_cc,this.username,this.password);
    }

    public void setNumeroCC(String numeroCC) {
        this.numero_cc = Integer.valueOf(numeroCC);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

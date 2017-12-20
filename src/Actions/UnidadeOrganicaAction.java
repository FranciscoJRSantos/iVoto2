package Actions;

import Beans.UnidadeOrganicaBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;

public class UnidadeOrganicaAction extends Action implements SessionAware{
    private ArrayList<String> unidades_organicas;
    private String nome;
    private String pertence;
    private UnidadeOrganicaBean bean;

    public String create() throws Exception {
        this.bean = new UnidadeOrganicaBean();
        this.bean.setNome(this.nome);
        this.bean.setPertence(this.pertence);
        if(this.bean.createUnidadeOrganica()){
            return "success";
        }
        return "error";
    }

    public String show() throws Exception {
        return "success";
    }

    public String showAll() throws Exception {
        this.unidades_organicas = new UnidadeOrganicaBean().getUnidadesOrganicas();
        return "success";
    }

    public ArrayList<String> getUnidades_organicas() {
        return unidades_organicas;
    }

    public String getPertence() {
        return pertence;
    }

    public void setPertence(String pertence) {
        this.pertence = pertence;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

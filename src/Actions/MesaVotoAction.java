package Actions;

import Beans.EleicaoBean;
import Beans.MesaVotoBean;
import Beans.UnidadeOrganicaBean;
import Beans.UserBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;

public class MesaVotoAction extends Action implements SessionAware{
    private ArrayList<String> unidades_organicas;
    private ArrayList<ArrayList<String>> eleicoes;
    private ArrayList<String> utilizadores;
    private ArrayList<String> eleicoes_titulo;
    private ArrayList<String> eleicoes_id;
    private ArrayList<String> eleicoes_local;
    private String numero_cc;
    private MesaVotoBean bean;

    public String create() throws Exception {
        this.bean = new MesaVotoBean();
        return "success";
    }

    public String show() throws Exception{
        return "success";
    }

    public ArrayList<String> getUnidades_organicas() {
        this.unidades_organicas = new UnidadeOrganicaBean().getUnidadesOrganicas();
        return unidades_organicas;
    }

    public ArrayList<ArrayList<String>> getEleicoes() {
        this.eleicoes = new EleicaoBean().getEleicoesFuturas();
        return this.eleicoes;
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

    public ArrayList<String> getUtilizadores() {
        return utilizadores = new UserBean().getUsers();
    }
}

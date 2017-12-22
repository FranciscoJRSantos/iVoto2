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
    private ArrayList<String> eleicoes_titulo;
    private ArrayList<String> eleicoes_id;
    private ArrayList<String> eleicoes_local;
    private MesaVotoBean bean;
    private String numero_cc;
    private String unidade_organica;


    private String eleicao_id;

    public String create() throws Exception {
        if (this.numero_cc == null || this.unidade_organica == null || this.eleicao_id == null){
           return "error";
        }
        this.bean = new MesaVotoBean();
        this.bean.setNumero_cc(Integer.parseInt(this.numero_cc));
        this.bean.setUnidade_organica(this.unidade_organica);
        this.bean.setEleicao(Integer.parseInt(this.eleicao_id));
        if (this.bean.createMesaVoto()){
            return "success";
        }
        return "error";
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

    public void setNumero_cc(String numero_cc) {
        this.numero_cc = numero_cc;
    }

    public void setUnidade_organica(String unidade_organica) {
        this.unidade_organica = unidade_organica;
    }

    public void setEleicao_id(String eleicao_id) {
        this.eleicao_id = eleicao_id;
    }
}

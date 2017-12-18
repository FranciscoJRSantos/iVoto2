package Actions;

import Beans.UnidadeOrganicaBean;

import java.util.ArrayList;

public class UnidadeOrganicaAction extends Action {
    private ArrayList<String> unidades_organicas;

    public String create() throws Exception {
        return "success";
    }

    public String show() throws Exception {
        this.unidades_organicas = new UnidadeOrganicaBean().getUnidadesOrganicas();
        return "success";
    }
}

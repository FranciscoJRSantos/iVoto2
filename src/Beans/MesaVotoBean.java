package Beans;

import java.rmi.RemoteException;

public class MesaVotoBean extends Bean{

    private String unidade_organica;
    private Integer eleicao;
    private Integer numero_cc;

    public MesaVotoBean() { super(); }


    public boolean createMesaVoto(){
        try{
            this.server.createMesaVoto(this.unidade_organica,this.eleicao,this.numero_cc);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setNumero_cc(Integer numero_cc) {
        this.numero_cc = numero_cc;
    }

    public void setEleicao(Integer eleicao) {
        this.eleicao = eleicao;
    }

    public void setUnidade_organica(String unidade_organica) {
        this.unidade_organica = unidade_organica;
    }
}

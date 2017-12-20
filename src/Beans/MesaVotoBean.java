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
}

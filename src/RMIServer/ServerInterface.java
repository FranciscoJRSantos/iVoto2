package RMIServer;


import ws.websocketInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ServerInterface extends Remote{
    //websockets
    public void subscribeWeb(websocketInterface w) throws RemoteException, SQLException;
    public void unsubscribeWeb(websocketInterface w) throws RemoteException;
    public void logoutUser(String ncc) throws RemoteException;
    public void mesaNotifica(int i, int flag) throws RemoteException;
    // Create
    boolean createUser(int numero_cc, String nome, String password_hashed, String morada, int contacto, String validade_cc, int tipo, String un_org_nome) throws RemoteException;
    boolean createUnidadeOrganica(String nome, String pertence) throws RemoteException;
    boolean createEleicao(String titulo, String inicio, String fim, String descricao, int tipo, String un_org_nome) throws RemoteException;
    boolean createLista(String nome, int tipo, int eleicao_id, int numero_cc) throws RemoteException;
    boolean createMesaVoto(String un_org_nome, int eleicao_id, int numero_cc) throws RemoteException;

    // Read
    ArrayList<String> showUtilizador(int numero_cc) throws RemoteException;
    ArrayList<String> showUO(String nome) throws RemoteException;
    ArrayList<ArrayList<String>> showAllUsers() throws RemoteException;
    ArrayList<String> showAllUO() throws RemoteException;
    ArrayList<String> showAllUONotFac() throws RemoteException;
    ArrayList<String> showEleicao(int id) throws RemoteException;
    ArrayList<ArrayList<String>> showEleicoesDecorrer() throws RemoteException;
    ArrayList<ArrayList<String>> showEleicoesPassadas() throws RemoteException;
    ArrayList<ArrayList<String>> showEleicoesFuturas() throws RemoteException;
    ArrayList<String> showLista(String nome, int eleicao_id) throws RemoteException;
    ArrayList<String> showPersonVotingInfo(int numero_cc, int eleicao_id) throws RemoteException;
    ArrayList<ArrayList<String>> showMesasVotoEleicao(int eleicao_id) throws RemoteException;
    ArrayList<ArrayList<String>> showUtilizadoresMesaVoto(int numero, int eleicao_id) throws RemoteException;
    ArrayList<String> pickListsFromElection(int numero_cc, int eleicao_id) throws RemoteException;
    ArrayList<String> showListsFromElection(int eleicao_id) throws RemoteException;
    ArrayList<ArrayList<String>> showResultadosFromEleicao(int eleicao_id) throws RemoteException;
    ArrayList<String> showPersonVotingInfoAll(int numero_cc) throws RemoteException;
    // Update

    boolean updateUtilizador(int cc, String new_info, int flag) throws RemoteException;
    String vote(int cc, String lista, int eleicao_id, int mesavoto_id) throws RemoteException;
    String anticipatedVote(int cc, String lista, int eleicao_id, String pass) throws RemoteException;
    boolean updateEleicoesData(int id, String newdate, int flag) throws RemoteException;
    boolean updateEleicoesDescricao(int id, String newdate) throws RemoteException;
    boolean updateUnidadeOrganica(String nome, String novo_nome, int flag) throws RemoteException;
    boolean updateMesaVotoUtilizadores(int numero_cc, int mesa_voto_numero, int id_eleicao) throws RemoteException;
    boolean updateEleicao(Integer id, String nome, String inicio, String fim, String descricao, Integer tipo, String unidade_organica) throws RemoteException;
    boolean updateUser(Integer cc_velho, Integer cc_novo, String nome, String morada, String password, String validade_cc, Integer contacto, String un_org_nome) throws RemoteException;
    boolean linkFacebook(Integer cc, String id, String token) throws RemoteException;
    boolean unlinkFacebook(Integer cc) throws RemoteException;
    boolean updateUserFacebookToken(Integer cc, String token) throws RemoteException;

    // Delete
    boolean deleteUtilizador(int numero_cc) throws RemoteException;
    boolean deleteUO(String nome) throws RemoteException;
    boolean deleteLista(String nome, int eleicao_id) throws RemoteException;
    boolean deleteMesaVoto(int numero,String un_org_nome, int eleicao_id) throws RemoteException;
    // Security

    String checkCC(int numero_cc, int eleicao_id) throws RemoteException;
    boolean checkLogin(int numero_cc, String nome, String password_hashed) throws RemoteException;
    boolean isConnected() throws RemoteException;
    Integer findFacebookID(String id) throws RemoteException;
    String getUserFacebookID(Integer cc) throws RemoteException;
    String getUserFacebookToken(Integer cc) throws RemoteException;
}

/*
 * created by FranciscoJRSantos at 09/10/2017
 *
 **/

package RMIServer;

import ws.websocketInterface;
import java.io.IOException;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {

    private static final long serialVersionUID = 1L;
    private static DatabaseConnection database = null;
    private static String rmiName;
    private static int rmiPort; // 1099
    private UDPConnection heartbeat = null;
    private boolean mainServer = true;
    private static ArrayList <websocketInterface> webSocketAnnotations = new ArrayList<websocketInterface>();
    ArrayList <String> users_online = new ArrayList<String>();
    ArrayList <Integer> mesas_online = new ArrayList<Integer>();




    private RMIServer() throws RemoteException {

        RMIConfigLoader newConfig = new RMIConfigLoader();
        RMIServer.rmiName = newConfig.getRMIName();
        RMIServer.rmiPort = newConfig.getRMIPort();
        String dbIP = newConfig.getDBIP();
        int dbPort = newConfig.getDBPort();
        database = new DatabaseConnection(dbIP, dbPort);
        startRMIServer();
    }

    public static void main(String args[]) throws RemoteException {

        new RMIServer();

    }

    private void startRMIServer() {

        try {

            Registry r = LocateRegistry.createRegistry(rmiPort);
            Naming.rebind(rmiName, this);

            System.out.println("Main RMIServer Started");
            if (this.heartbeat == null) {
                this.startUDPConnection();
            }
        } catch (ExportException ee) {

            this.setMainServer(false);
            System.out.println(this.mainServer);
            if (this.heartbeat == null) {
                this.startUDPConnection();
            }
            System.out.println("Backup RMIServer Starting");
        } catch (RemoteException re) {
            System.out.println("RemoteException: " + re);
        } catch (MalformedURLException murle) {
            System.out.println("MalformedURLException: " + murle);
        }
    }

    private void setMainServer(boolean n) {
        this.mainServer = n;
    }

    private void startUDPConnection() {
        this.heartbeat = new UDPConnection(mainServer);
    }

    //Websockets
    public void subscribeWeb(websocketInterface w) throws RemoteException, SQLException {
        //System.out.println("Subscribing new client!");
        if(w != null) {
            webSocketAnnotations.add(w);
            System.out.println("added");
        }
    }



    public void unsubscribeWeb(websocketInterface w) throws RemoteException{
        System.out.println("tryout");
        //System.out.println("Unsubscribing client...");
        if(w != null) {
            webSocketAnnotations.remove(w);
            System.out.println("out");
        }
    }

    public void webNotifier(String message){

        for (int j = 0; j < webSocketAnnotations.size(); j++) {
            try {
                webSocketAnnotations.get(j).print_on_websocket(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void logoutUser(String ncc) throws RemoteException{
        if(users_online.contains(ncc)){
            this.users_online.remove(ncc);
        }
        String all_users = "";
        for(int i = 0; i<users_online.size(); i++){
            all_users = all_users + users_online.get(i) + ", ";
        }
        System.out.println(users_online);
        webNotifier("user|Utilizador com o cartão de cidadão "+ncc+ " acabou de sair!|Utilizadores online:\n"+all_users);
    }

    public void mesaNotifica(int id, int flag) throws RemoteException { //0 login, 1 logout
        if (flag == 0) {
            if (!mesas_online.contains(id))
                mesas_online.add(id);

            String all_tables = "";
            for (int i = 0; i < mesas_online.size(); i++) {
                all_tables = all_tables + String.valueOf(mesas_online.get(i)) + " ";
            }
            webNotifier("mesa| Mesa com id " + id + " acabou de connectar!\n Mesas online:\n" + all_tables);

        }
        if (flag == 1) {
            if (mesas_online.contains(id))
                mesas_online.remove(id);

            String all_tables = "";
            for (int i = 0; i < mesas_online.size(); i++) {
                all_tables = all_tables + String.valueOf(mesas_online.get(i)) + " ";
            }
            webNotifier("mesa|Mesa com id " + id + " acabou de desconectar!\n Mesas online:\n" + all_tables);
        }
    }

    public void votosNotifica() throws RemoteException{

    }


    // Create

    public boolean createUser(int numero_cc, String nome, String password_hashed, String morada, int contacto, String validade_cc, int tipo, String un_org_nome) throws RemoteException {

        boolean answer = true;
        if ((tipo >= 0) && (tipo <= 3)){
            String proc_call = "CALL createUtilizador(" + numero_cc + ",'" + nome + "','" + password_hashed + "','" + morada + "'," + contacto + ",'" + validade_cc + "'," + tipo + ",'" + un_org_nome + "');";
            database.submitUpdate(proc_call);
        }
        else answer = false;

        return answer;
    }

    public boolean createUnidadeOrganica(String nome, String pertence) throws RemoteException{

        boolean answer = true;
        ArrayList protection;
        String sql1,sql2;

        sql1 = "SELECT * FROM unidade_organica WHERE nome='" + nome + "';";
        protection = database.submitQuery(sql1);
        if (protection.isEmpty()){
            if (pertence != null &&  pertence.trim().length() != 0){
                sql2 = "INSERT INTO unidade_organica (nome,pertence) VALUES('" + nome + "','" + pertence + "');";
            }
            else{
                sql2 = "INSERT INTO unidade_organica (nome,pertence) VALUES('" + nome + "',NULL);";
            }
            database.submitUpdate(sql2);

        }
        else answer = false;
        return answer;
    }

    public boolean createEleicao(String titulo, String inicio, String fim, String descricao, int tipo, String un_org_nome) throws RemoteException{
        boolean answer = true;
        if ((tipo >= 0) && (tipo < 3)){
            String proc_call = "CALL createEleicao('" + titulo + "','" + inicio + "','" + fim + "','" + descricao + "'," + tipo + ",'" + un_org_nome + "');";
            database.submitUpdate(proc_call);
        }
        else answer = false;

        return answer;
    }

    public boolean createLista(String nome, int tipo, int eleicao_id, int numero_cc) throws RemoteException{

        boolean answer = true;
        String protection = "SELECT * FROM eleicao WHERE id=" + eleicao_id + " AND NOW() NOT BETWEEN inicio AND fim;";
        ArrayList security = database.submitQuery(protection);

        if (!security.isEmpty()){
            if ((tipo >= 0) && (tipo < 3)){
                String proc_call = "CALL createLista('" + nome + "'," + tipo + "," + eleicao_id + "," + numero_cc + ");";
                database.submitUpdate(proc_call);
            }
            else answer = false;
        }
        else answer=false;

        return answer;

    }

    public boolean createMesaVoto(String un_org_nome, int eleicao_id, int numero_cc) throws RemoteException{

        boolean answer = true;
        int tipo_eleicao;
        ArrayList<String> check;
        String protection = "SELECT COUNT(*) FROM mesa_voto WHERE unidade_organica_nome LIKE '" + un_org_nome + "' AND eleicao_id='" + eleicao_id + "';";
        check = database.submitQuery(protection);

        String protect = "SELECT tipo FROM eleicao WHERE id='" + eleicao_id + "';";

        if ((Integer.parseInt(check.get(0))) >= 1){
            answer = false;
        }
        else{
            tipo_eleicao = Integer.parseInt(database.submitQuery(protect).get(0));
            if ((tipo_eleicao != 1 && tipo_eleicao != 3 ) || (Integer.parseInt(check.get(0)) < 1)){
                String proc_call = "CALL createMesaVoto('" + un_org_nome + "'," + eleicao_id + "," + numero_cc + ");";
                database.submitUpdate(proc_call);
            }
        }
        return answer;
    }

    // Read

    public ArrayList<String> showUtilizador(int numero_cc) throws RemoteException{

        ArrayList<String> utilizador;
        String sql = "SELECT * FROM utilizador WHERE numero_cc LIKE " + numero_cc + ";";
        utilizador = database.submitQuery(sql);

        return utilizador;

    }

    public ArrayList<ArrayList<String>> showAllUsers() throws RemoteException{
        ArrayList<ArrayList<String>> utilizadores = new ArrayList<>();
        ArrayList<String> cc;
        ArrayList<String> nome;
        String sql_cc = "SELECT numero_cc FROM utilizador;";
        String sql_nome ="SELECT nome FROM utilizador;";
        cc = database.submitQuery(sql_cc);
        nome = database.submitQuery(sql_nome);

        utilizadores.add(cc);
        utilizadores.add(nome);

        return utilizadores;
    }

    public ArrayList<String> showUO(String nome) throws RemoteException{

        ArrayList<String> unidade_organica;
        String sql = "SELECT * FROM unidade_organica WHERE nome LIKE " + nome + ";";
        unidade_organica = database.submitQuery(sql);

        return unidade_organica;
    }

    public ArrayList<String> showAllUO() throws RemoteException{

        ArrayList<String> unidades_organicas;
        String sql_un_orgs = "SELECT nome FROM unidade_organica";

        unidades_organicas = database.submitQuery(sql_un_orgs);

        return unidades_organicas;

    }

    public ArrayList<String> showAllUONotFac() throws RemoteException{

        ArrayList<String> departamento;
        String sql_uo = "SELECT nome FROM unidade_organica WHERE pertence IS NULL";

        departamento = database.submitQuery(sql_uo);

        return departamento;
    }

    public ArrayList<String> showEleicao(int id) throws RemoteException{

        ArrayList<String> eleicao;
        String sql = "SELECT * FROM eleicao WHERE id='" + id + "';";
        eleicao = database.submitQuery(sql);

        return eleicao;
    }

    public ArrayList<ArrayList<String>> showResultadosFromEleicao(int eleicao_id) throws RemoteException{
        // recebe uma lista com [[lista,nº de votos],...]. return [[null,null]] em caso de insucesso
        ArrayList<ArrayList<String>> resultados = new ArrayList<>();
        ArrayList<String> nome_lista;
        ArrayList<String> votos_lista;
        ArrayList<String> total_votos_lista;
        ArrayList<String> percentagem_votos_lista = new ArrayList<>();
        Integer total_votos;
        Integer percentagem;

        String sqlTotalVotos = "SELECT SUM(votos) FROM lista WHERE eleicao_id ='" + eleicao_id + "';";

        String sqlNome = "SELECT nome FROM lista WHERE eleicao_id='" + eleicao_id +"' ORDER BY votos DESC;";
        String sqlVotos = "SELECT votos FROM lista WHERE eleicao_id='" + eleicao_id +"' ORDER BY votos DESC;";

        total_votos_lista = database.submitQuery(sqlTotalVotos);
        nome_lista = database.submitQuery(sqlNome);
        votos_lista = database.submitQuery(sqlVotos);

        total_votos = Integer.parseInt(total_votos_lista.get(0));

        for (String votos: votos_lista){
            percentagem = Integer.parseInt(votos)/total_votos;
            percentagem_votos_lista.add(String.valueOf(percentagem));
        }

        resultados.add(total_votos_lista);
        resultados.add(nome_lista);
        resultados.add(votos_lista);
        resultados.add(percentagem_votos_lista);

        System.out.println(resultados);

        return resultados;
    }

    public ArrayList<ArrayList<String>> showEleicoesDecorrer() throws RemoteException{

        ArrayList<ArrayList<String>> eleicoes = new ArrayList<>();
        ArrayList<String> id;
        ArrayList<String> descricao;
        ArrayList<String> local;
        String sql_id = "SELECT id FROM eleicao WHERE NOW() BETWEEN inicio AND fim;";
        String sql_descricao = "SELECT descricao FROM eleicao WHERE NOW() BETWEEN inicio AND fim;";
        String sql_local = "SELECT unidade_organica_nome FROM eleicao AS e, unidade_organica_eleicao uoe WHERE e.id = uoe.eleicao_id AND NOW() BETWEEN inicio AND fim;";
        id = database.submitQuery(sql_id);
        descricao = database.submitQuery(sql_descricao);
        local = database.submitQuery(sql_local);

        eleicoes.add(id);
        eleicoes.add(descricao);
        eleicoes.add(local);

        return eleicoes;
    }

    public ArrayList<ArrayList<String>> showEleicoesPassadas() throws RemoteException{

        ArrayList<ArrayList<String>> eleicoes = new ArrayList<>();
        ArrayList<String> id;
        ArrayList<String> descricao;
        ArrayList<String> local;
        String sql_id = "SELECT id FROM eleicao WHERE inicio < NOW() AND NOW() NOT BETWEEN inicio AND fim;";
        String sql_descricao = "SELECT descricao FROM eleicao WHERE inicio < NOW() AND NOW() NOT BETWEEN inicio AND fim;";
        String sql_local = "SELECT unidade_organica_nome FROM eleicao AS e, unidade_organica_eleicao uoe WHERE e.id = uoe.eleicao_id AND inicio < NOW() AND NOW() NOT BETWEEN inicio AND fim;";
        id = database.submitQuery(sql_id);
        descricao = database.submitQuery(sql_descricao);
        local = database.submitQuery(sql_local);

        eleicoes.add(id);
        eleicoes.add(descricao);
        eleicoes.add(local);

        return eleicoes;
    }

    public ArrayList<ArrayList<String>> showEleicoesFuturas() throws RemoteException{

        ArrayList<ArrayList<String>> eleicoes = new ArrayList<>();
        ArrayList<String> id;
        ArrayList<String> descricao;
        ArrayList<String> local;
        String sql_id = "SELECT id FROM eleicao WHERE inicio > NOW() AND NOW() NOT BETWEEN inicio AND fim;";
        String sql_descricao = "SELECT titulo FROM eleicao WHERE inicio > NOW() AND NOW() NOT BETWEEN inicio AND fim;";
        String sql_local = "SELECT unidade_organica_nome FROM eleicao AS e, unidade_organica_eleicao uoe WHERE e.id = uoe.eleicao_id AND inicio > NOW() AND NOW() NOT BETWEEN inicio AND fim;";
        id = database.submitQuery(sql_id);
        descricao = database.submitQuery(sql_descricao);
        local = database.submitQuery(sql_local);

        eleicoes.add(id);
        eleicoes.add(descricao);
        eleicoes.add(local);

        return eleicoes;
    }

    public ArrayList<String> showLista(String nome, int eleicao_id) throws RemoteException{

        ArrayList<String> lista;
        String sql = "SELECT * FROM lista WHERE nome LIKE " + nome + " AND eleicao_id='" + eleicao_id + "';";
        lista = database.submitQuery(sql);

        return lista;
    }

    public ArrayList<String> showListsFromElection(int eleicao_id) throws RemoteException{

        ArrayList<String> lists;
        String sql_get_lists = "SELECT nome FROM lista WHERE eleicao_id='" + eleicao_id + "' AND nome NOT LIKE 'Blank' AND nome NOT LIKE 'Null'";

        lists = database.submitQuery(sql_get_lists);

        return lists;
    }

    public ArrayList<String> pickListsFromElection(int numero_cc, int eleicao_id) throws RemoteException{

        String sql_get_lists = "SELECT nome FROM lista WHERE eleicao_id = '" + eleicao_id + "' AND tipo_utilizador = ( SELECT tipo FROM utilizador WHERE numero_cc='" + numero_cc + "' ) AND tipo_utilizador != 0;";

        return database.submitQuery(sql_get_lists);
    }

    public ArrayList<ArrayList<String>> showUtilizadoresMesaVoto(int numero, int eleicao_id) throws RemoteException{

        ArrayList<ArrayList<String>> utilizadores = new ArrayList<>();
        ArrayList<String> id;
        ArrayList<String> nome;
        String sql_id = "SELECT u.numero_cc FROM utilizador AS u, mesa_voto_utilizador AS mvu WHERE u.numero_cc = mvu.utilizador_numero_CC AND mvu.mesa_voto_numero = '" + numero + "' AND mvu.eleicao_id ='" + eleicao_id + "';";
        String sql_nome = "SELECT u.nome FROM utilizador AS u, mesa_voto_utilizador AS mvu WHERE u.numero_cc = mvu.utilizador_numero_CC AND mvu.mesa_voto_numero = '" + numero + "' AND mvu.eleicao_id ='" + eleicao_id + "';";
        id = database.submitQuery(sql_id);
        nome = database.submitQuery(sql_nome);

        utilizadores.add(id);
        utilizadores.add(nome);

        return utilizadores;
    }

    public ArrayList<String> showPersonVotingInfo(int numero_cc, int eleicao_id) throws RemoteException{

        ArrayList<String> info;
        String sql = "SELECT * FROM eleicao_utilizador WHERE utilizador_numero_cc ='" + numero_cc + "' AND eleicao_id = '" + eleicao_id + "';";
        info = database.submitQuery(sql);

        if (info.isEmpty()){
            info = null;
        }

        return info;

    }

    public ArrayList<String> showPersonVotingInfoAll(int numero_cc) throws RemoteException{

        ArrayList<String> info;
        String sql = "SELECT * FROM eleicao_utilizador WHERE utilizador_numero_cc ='" + numero_cc + "';";
        info = database.submitQuery(sql);

        if (info.isEmpty()){
            info = null;
        }

        return info;

    }

    public ArrayList<ArrayList<String>> showMesasVotoEleicao(int eleicao_id) throws RemoteException{

        ArrayList<ArrayList<String>> mesas = new ArrayList<>();
        ArrayList<String> numero_mesas;
        ArrayList<String> localizacao_mesas;

        String sql_mesas = "SELECT numero FROM mesa_voto WHERE eleicao_id=" + eleicao_id + ";";
        String sql_un_org = "SELECT unidade_organica_nome FROM unidade_organica_eleicao WHERE eleicao_id =" + eleicao_id + " GROUP BY unidade_organica_nome;";
        numero_mesas = database.submitQuery(sql_mesas);
        localizacao_mesas = database.submitQuery(sql_un_org);

        mesas.add(numero_mesas);
        mesas.add(localizacao_mesas);

        return mesas;
    }

    public ArrayList<String> showListasFromEleicao(int eleicao_id) throws RemoteException{

        ArrayList<String> listas;
        String sql_listas = "SELECT nome FROM lista WHERE eleicao_id = '" + eleicao_id + "';";

        listas = database.submitQuery(sql_listas);

        return listas;
    }

    // Update

    public boolean updateUtilizador(int cc, String new_info, int flag) throws RemoteException{

        int aux;
        String sql="";

        switch (flag){
            case 1:
                sql = "UPDATE utilizador SET nome='" + new_info + "' WHERE numero_cc='" + cc + "';";
                break;
            case 2:
                sql = "UPDATE utilizador SET morada='" + new_info + "' WHERE numero_cc='" + cc + "';";
                break;
            case 3:
                aux = Integer.parseInt(new_info);
                sql = "UPDATE utilizador SET contacto='" + new_info + "' WHERE numero_cc='" + cc + "';";
                break;
            case 4:
                aux = Integer.parseInt(new_info);
                sql = "UPDATE utilizador SET numero_cc='" + aux + "' WHERE numero_cc='" + cc + "';";
                break;
            case 5:
                sql = "UPDATE utilizador SET validade_cc='" + new_info + "' WHERE numero_cc='" + cc + "';";
                break;
            case 6:
                aux = Integer.parseInt(new_info);
                sql = "UPDATE unidade_organica_utilizador SET unidade_organica_nome='" + aux +"';";
                break;
            case 7:
                sql = "UPDATE utilizador SET password_hashed = '" + new_info + "' WHERE numero_cc = '" + cc + "';";
            default:
                break;

        }
        if (!sql.equals("")){
            database.submitUpdate(sql);
        }
        else return false;

        return true;
    }

    public boolean updateUser(Integer cc_velho, Integer cc_novo, String nome, String morada, String password, String validade_cc, Integer contacto, String un_org_nome) throws RemoteException {

        if (cc_velho == null){
            return false;
        }
        if (cc_novo != null){
            this.updateUtilizador(cc_velho,cc_novo.toString(),4);
        }
        if (nome != null){
            if (cc_novo == null){
                this.updateUtilizador(cc_velho,nome,1);
            }
            else{
                this.updateUtilizador(cc_novo,nome,1);
            }
        }
        if (morada != null){
            if (cc_novo == null){
                this.updateUtilizador(cc_velho,morada,2);
            }
            else{
                this.updateUtilizador(cc_novo,morada,2);
            }
        }
        if (contacto != null){
            if (cc_novo == null){
                this.updateUtilizador(cc_velho, contacto.toString(), 3);
            }
            else {
                this.updateUtilizador(cc_novo, contacto.toString(), 3);
            }
        }
        if (validade_cc != null){
            if (cc_novo == null){
                this.updateUtilizador(cc_velho, validade_cc, 5);
            }
            else {
                this.updateUtilizador(cc_novo,validade_cc,5);
            }
        }
        if (password != null){
            if (cc_novo == null){
                this.updateUtilizador(cc_velho,password,7);
            }
            else{
                this.updateUtilizador(cc_novo,password,7);
            }
        }
        if (un_org_nome != null){
            if (cc_novo == null){
                this.updateUtilizador(cc_velho,un_org_nome,6);
            }
            else{
                this.updateUtilizador(cc_novo,un_org_nome,6);
            }
        }
        return true;
    }

    public boolean updateEleicoesNome(Integer id, String nome){
        boolean toClient = true;
        String sql = "SELECT * FROM eleicao WHERE ID='" + id + "' AND NOW() NOT BETWEEN inicio AND fim;";
        ArrayList<String> aux = database.submitQuery(sql);
        if (aux.isEmpty()){
            toClient = false;
        }
        else{
            sql = "UPDATE eleicao SET titulo ='" + nome + "' WHERE id ='" + id + "';";
            database.submitUpdate(sql);
        }

        return toClient;
    }

    public boolean updateEleicao(Integer id, String nome, String inicio, String fim, String descricao, Integer tipo, String unidade_organica) throws RemoteException{
        if (id == null){
            return false;
        }
        if (nome != null && !nome.isEmpty()){
            this.updateEleicoesNome(id,nome);
        }
        if (descricao != null && !descricao.isEmpty()){
            this.updateEleicoesDescricao(id,descricao);
        }
        if (inicio != null && !inicio.isEmpty()){
            this.updateEleicoesData(id,inicio,1);
        }
        if (fim != null && !fim.isEmpty()){
            this.updateEleicoesData(id,fim,2);
        }
        return true;
    }

    public boolean updateEleicoesDescricao(int id, String newdate) throws RemoteException{

        boolean toClient = true;
        String sql = "SELECT * FROM eleicao WHERE ID='" + id + "' AND NOW() NOT BETWEEN inicio AND fim;";
        ArrayList<String> aux = database.submitQuery(sql);
        if (aux.isEmpty()){
            toClient = false;
        }
        else{
            sql = "UPDATE eleicao SET descricao ='" + newdate + "' WHERE id ='" + id + "';";
            database.submitUpdate(sql);
        }

        return toClient;
    }
    public boolean updateEleicoesData(int id, String newdate, int flag) throws RemoteException{

        boolean toClient = true;
        String sql = "SELECT * FROM eleicao WHERE ID='" + id + "' AND NOW() NOT BETWEEN inicio AND fim;";
        ArrayList<String> aux = database.submitQuery(sql);
        if (aux.isEmpty()){
            toClient = false;
        }
        else{
            if (flag == 1){
                sql = "UPDATE eleicao SET inicio='" + newdate + "' WHERE ID='" + id + "';";
            }
            else if (flag == 2){
                sql = "UPDATE eleicao SET fim ='" + newdate + "' WHERE ID='" + id + "';";
            }
            database.submitUpdate(sql);
        }

        return toClient;
    }


    public boolean updateUnidadeOrganica(String nome, String novo_nome, int flag) throws RemoteException{

        //edita nome de departamento / faculdade. flag 1 - dep, flag 2 - fac
        String sql = "";

        if (flag == 1){
            sql = "UPDATE unidade_organica SET nome='" + novo_nome + "' WHERE nome='" + nome + "';";
        }
        else if (flag == 2){
            sql = "UPDATE unidade_organica SET pertence='" + novo_nome + "' WHERE nome='" + nome + "';";
        }

        if (!sql.equals("")){
            database.submitUpdate(sql);
        }
        return true;
    }

    public boolean updateMesaVotoUtilizadores(int numero_cc, int mesa_voto_numero, int id_eleicao) throws RemoteException{

        String protection = "SELECT COUNT(*) FROM mesa_voto_utilizador WHERE mesa_voto_numero = '" + mesa_voto_numero + "' AND eleicao_id = '" + id_eleicao + "';";
        ArrayList<String> aux = database.submitQuery(protection);

        if (Integer.parseInt(aux.get(0)) > 3){
            return false;
        }

        String sql = "INSERT INTO mesa_voto_utilizador (mesa_voto_numero, eleicao_id, utilizador_numero_cc) VALUES (" + mesa_voto_numero + "," +  id_eleicao + "," + numero_cc + ") ON DUPLICATE KEY UPDATE mesa_voto_numero = '" + mesa_voto_numero+ "', eleicao_id = '" + id_eleicao + "', utilizador_numero_cc = '" + numero_cc + "';";

        database.submitUpdate(sql);

        return true;

    }

    public String vote(int cc, String lista, int eleicao_id, int mesavoto_id) throws RemoteException{

        String toClient = null;
        ArrayList<String> aux1;
        ArrayList<String> aux2;
        ArrayList<String> aux3;
        String sql6 = "SELECT nome FROM lista WHERE nome LIKE '" + lista + "' AND eleicao_id='" + eleicao_id +"';";
        aux3 = database.submitQuery(sql6);
        if (lista.equals("") || lista.trim().isEmpty()){
            aux3.add("Blank");
        }
        else if (aux3.isEmpty()){
            aux3.add("Null");
        }

        String sql7 = "SELECT validade_cc FROM utilizador WHERE numero_cc = '" + cc + "' AND validade_cc > NOW();";
        if(database.submitQuery(sql7).isEmpty()){
            return null;
        }

        System.out.println(aux3);

        String sql2 = "SELECT * FROM eleicao_utilizador WHERE utilizador_numero_cc  ='" + cc + "' AND eleicao_id='" +  eleicao_id + "';";

        aux2 = database.submitQuery(sql2);
        System.out.println(aux2);
        if (aux2.isEmpty()){
            String sql3 = "SELECT unidade_organica_nome FROM mesa_voto WHERE eleicao_id = '" + eleicao_id + "' AND numero='" + mesavoto_id + "';";
            aux1 = database.submitQuery(sql3);
            String sql4 = "INSERT INTO eleicao_utilizador (unidade_organica_nome,eleicao_id,utilizador_numero_cc,mesa_voto_numero) VALUES('" + aux1.get(0) + "','" + eleicao_id + "','" + cc +"','" + mesavoto_id + "');";
            String sql1 = "UPDATE lista SET votos = votos +1 WHERE nome LIKE '" + lista + "' AND eleicao_id='" + eleicao_id + "';";
            database.submitUpdate(sql4);
            database.submitUpdate(sql1);
            toClient = lista;
        }
        else{
            toClient = null;
        }

        webNotifier("voto|Um voto para eleição com id " + String.valueOf(eleicao_id));
        return toClient;
    }

    public String anticipatedVote(int cc, String lista, int eleicao_id, String pass) throws RemoteException{

        String toClient;
        ArrayList<String> aux2;
        ArrayList<String> aux3;
        ArrayList<String> check;
        String sql1 = "SELECT * FROM utilizador WHERE numero_cc='" + cc + "' AND password_hashed LIKE '" + pass + "';";
        check = database.submitQuery(sql1);
        if (check.isEmpty()){
            return null;
        }
        String sql2 = "SELECT nome FROM lista WHERE nome LIKE '" + lista + "' AND eleicao_id='" + eleicao_id +"';";
        aux3 = database.submitQuery(sql2);
        if (lista.equals("")){
            aux3.add("Blank");
        }
        else if (!aux3.isEmpty()){
            aux3.add("Null");
        }

        String sql3 = "SELECT * FROM eleicao_utilizador WHERE utilizador_numero_cc  ='" + cc + "' AND eleicao_id='" +  eleicao_id + "';";

        aux2 = database.submitQuery(sql3);
        System.out.println(aux2);
        if (aux2.isEmpty()){
            String sql4 = "INSERT INTO eleicao_utilizador (unidade_organica_nome,eleicao_id,utilizador_numero_cc,mesa_voto_numero) VALUES(NULL,'" + eleicao_id + "','" + cc + "',NULL);";
            String sql5 = "UPDATE lista SET votos = votos +1 WHERE nome LIKE '" + lista + "' AND eleicao_id='" + eleicao_id + "';";
            database.submitUpdate(sql4);
            database.submitUpdate(sql5);
            toClient = lista;
        }
        else{
            toClient = null;
        }

        webNotifier("voto|Um voto para eleição com id " + String.valueOf(eleicao_id));
        return toClient;
    }

    public boolean linkFacebook(Integer cc, String id, String token) throws RemoteException {
        String sql = "UPDATE utilizador SET facebookID='" + id + "' WHERE numero_cc='" + cc + "';";
        database.submitUpdate(sql);
        return updateUserFacebookToken(cc, token);
    }

    public boolean unlinkFacebook(Integer cc) throws RemoteException {
        String sql = "UPDATE utilizador SET facebookID=NULL WHERE numero_cc='" + cc + "';";
        database.submitUpdate(sql);
        sql = "UPDATE utilizador SET facebookToken=NULL WHERE numero_cc='" + cc + "';";
        database.submitUpdate(sql);
        return true;
    }

    public boolean updateUserFacebookToken(Integer cc, String token) throws RemoteException {
        String sql = "UPDATE utilizador SET facebookToken='" + token + "' WHERE numero_cc='" + cc + "';";
        database.submitUpdate(sql);
        return true;
    }
    // Delete

    public boolean deleteUtilizador(int numero_cc) throws RemoteException{

        String sql = "DELETE FROM utilizador WHERE numero_cc='" + numero_cc +"';";
        database.submitUpdate(sql);

        return true;
    }

    public boolean deleteUO(String nome) throws RemoteException{

        String sql = "DELETE FROM unidade_organica WHERE nome LIKE " + nome +";";
        database.submitUpdate(sql);

        return true;
    }

    public boolean deleteLista(String nome, int eleicao_id) throws RemoteException{

        String sql = "DELETE FROM lista WHERE LIKE '" + nome +"' AND eleicao id=" + eleicao_id + ";";
        database.submitUpdate(sql);

        return true;
    }

    public boolean deleteMesaVoto(int numero,String un_org_nome, int eleicao_id) throws RemoteException{

        String sql = "DELETE FROM mesa_voto WHERE unidade_organica_nome LIKE '" + un_org_nome + "' AND eleicao_id=" + eleicao_id + " AND numero=" + numero + ";";
        database.submitUpdate(sql);

        return true;
    }

    // Security

    public boolean isConnected() throws RemoteException{
        return true;
    }

    public Integer findFacebookID(String id) throws RemoteException {
        String sql = "SELECT numero_cc FROM utilizador WHERE facebookID ='" + id + "';";
        ArrayList<String> user_data = database.submitQuery(sql);

        if (user_data.isEmpty()) return null;

        return Integer.parseInt(user_data.get(0));
    }

    public String getUserFacebookID(Integer cc) throws RemoteException {
        String sql = "SELECT facebookID FROM utilizador WHERE numero_cc ='" + cc + "';";
        ArrayList<String> user_data = database.submitQuery(sql);
        if (user_data.isEmpty()) return null;
        return user_data.get(0);
    }

    public String getUserFacebookToken(Integer cc) throws RemoteException {
        String sql = "SELECT facebookToken FROM utilizador WHERE numero_cc ='" + cc + "';";
        ArrayList<String> user_data = database.submitQuery(sql);
        if (user_data.isEmpty()) return null;
        return user_data.get(0);
    }

    public boolean checkLogin(int numero_cc, String nome, String password_hashed) throws RemoteException{

        boolean answer;
        String sql = "SELECT * FROM utilizador WHERE numero_cc = '" + numero_cc + "' AND nome LIKE '" + nome + "' AND password_hashed LIKE '" + password_hashed + "';";
        ArrayList check = database.submitQuery(sql);

        answer = !check.isEmpty();
        if(answer){
            if(!users_online.contains(String.valueOf(numero_cc))){
                this.users_online.add(String.valueOf(numero_cc));
            }
            String all_users = "";
            for(int i = 0; i<users_online.size(); i++){
                all_users = all_users + users_online.get(i) + ", ";
            }
            System.out.println("Fez login");
            webNotifier("user|Utilizador com o cartão de cidadão "+String.valueOf(numero_cc)+ " acabou de entrar!|Utilizadores online:\n"+all_users);
        }

        return answer;
    }

    public String checkCC(int numero_cc, int eleicao_id) throws RemoteException{

        String answer;
        String sql_user_data = "SELECT nome,tipo FROM utilizador WHERE numero_cc LIKE '" + numero_cc + "';";
        ArrayList<String> user_data = database.submitQuery(sql_user_data);

        if (!user_data.isEmpty()){
            String sql_eleicao_data = "SELECT e.tipo, uoe.unidade_organica_nome FROM eleicao AS e, unidade_organica_eleicao AS uoe WHERE e.id='" + eleicao_id + "' AND uoe.eleicao_id ='" + eleicao_id + "';";
            ArrayList<String> eleicao_data = database.submitQuery(sql_eleicao_data);

            if(!eleicao_data.isEmpty()){
                if (eleicao_data.get(0).equals("1")){
                    answer = user_data.get(0);
                }
                else{
                    String sql_user_unorg = "SELECT unidade_organica_nome FROM unidade_organica_utilizador WHERE utilizador_numero_cc ='" + numero_cc + "';";
                    ArrayList<String> user_unorg = database.submitQuery(sql_user_unorg);

                    if (!user_unorg.isEmpty()){
                        if((eleicao_data.get(0).equals("0") || eleicao_data.get(0).equals("2") ) && eleicao_data.get(1).equals(user_unorg.get(0))){
                            answer = user_data.get(0);
                        }
                        else {
                            String sql_check_pertence = "SELECT uo1.pertence, uo2.pertence FROM unidade_organica AS uo1, unidade_organica AS uo2 WHERE uo1.nome LIKE '" + eleicao_data.get(1) + "' AND uo2.nome LIKE " + user_unorg.get(0) + ";";
                            ArrayList<String> unorg_pertence = database.submitQuery(sql_check_pertence);

                            if(eleicao_data.get(0).equals("3") && unorg_pertence.get(0).equals(unorg_pertence.get(1))){
                                answer = user_data.get(0);
                            } else answer = null;

                        }
                    } else answer = null;

                }
            } else answer = null;

        } else answer = null;

        return answer;
    }

    class UDPConnection extends Thread {

        int mainUDP, secUDP;
        int pingFrequency;
        int retries;
        boolean mainServer = true;

        UDPConnection(boolean serverType) {

            RMIConfigLoader config = new RMIConfigLoader();
            mainUDP = config.getMainUDP();
            secUDP = config.getSecUDP();

            System.out.println("Main UDP: " + mainUDP);
            System.out.println("Secondary UDP: " + secUDP);

            pingFrequency = config.getPingFrequency();
            retries = config.getRetries();
            mainServer = serverType;
            this.start();
            System.out.println("UDPConnection Started");

        }

        @Override
        public void run() {

            DatagramSocket aSocket = null;
            byte[] buffer = new byte[1024];
            if (mainServer) {
                try {
                    aSocket = new DatagramSocket(mainUDP);
                    aSocket.setSoTimeout(pingFrequency);
                } catch (SocketException se) {
                    se.printStackTrace();
                }
                while (true) {

                    byte[] message = "ping pong".getBytes();

                    int i = 0;
                    do {
                        try {

                            Thread.sleep(1000);
                            DatagramPacket toSend = new DatagramPacket(message, message.length, InetAddress.getByName("127.0.0.1"), secUDP);
                            aSocket.send(toSend);
                            DatagramPacket toReceive = new DatagramPacket(buffer, buffer.length);
                            aSocket.receive(toReceive);
                            i = 0;

                        } catch (SocketTimeoutException ste) {
                            i++;
                        } catch (IOException ioe) {
                            System.out.println("Networking Problems");
                        } catch (InterruptedException ie) {
                            RMIServer.this.heartbeat = null;

                            try {
                                Naming.unbind(RMIServer.rmiName);
                            } catch (RemoteException | NotBoundException | MalformedURLException re) {
                            }

                        }

                    } while (i < retries);


                }

            } else if (!mainServer) {

                try {
                    aSocket = new DatagramSocket(secUDP);
                    aSocket.setSoTimeout(pingFrequency);
                } catch (SocketException se) {
                    se.printStackTrace();
                }
                System.out.println("Is Backup Server");

                while (true) {

                    byte[] message = "ping pong".getBytes();

                    int i = 0;

                    do {

                        try {

                            Thread.sleep(1000);
                            DatagramPacket toSend = new DatagramPacket(message, message.length, InetAddress.getByName("127.0.0.1"), mainUDP);

                            aSocket.send(toSend);
                            DatagramPacket toReceive = new DatagramPacket(buffer, buffer.length);
                            aSocket.receive(toReceive);
                            i = 0;

                        } catch (SocketTimeoutException ste) {
                            i++;
                        } catch (IOException ioe) {
                            System.out.println("Network Problems");
                        } catch (InterruptedException ie) {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } while (i < retries);

                    System.out.println("RMIServer failed \nAssuming Main Server Status");

                    try {
                        aSocket.close();
                        RMIServer.this.heartbeat = null;
                        RMIServer.this.mainServer = true;
                        RMIServer.this.startRMIServer();
                        Thread.currentThread().join();
                    } catch (InterruptedException ie) {
                        System.out.println("Thread Interrupted");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }
}

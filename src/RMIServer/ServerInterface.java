package RMIServer;

import java.rmi.*;
import java.util.*;

public interface ServerInterface extends Remote{
    public boolean isConnected() throws RemoteException;
    // TCP
    public String checkID(int cc, int eleicao_id) throws RemoteException;
    public boolean checkLogin(int cc, String username, String password) throws RemoteException;     //recebe CC, username, password, checka se bate certo na base de dados
    public String vote(int cc, String lista, int eleicao_id, int mesavoto_id) throws RemoteException;

    // Admin Console - todas as boolean retornam true em caso de sucesso e false em caso de insucesso
    public boolean addPerson(String name, String Address, int phone, int ccn, String ccv, int dep, int fac, String pass, int type) throws RemoteException; //registar pessoa, type 1 - docente, type 2 - funcionario, type 3 - aluno. ccn - numero do cc, ccv - validade do cc
    public boolean addDepFac(int faculdade_id, String newName, int flag) throws RemoteException; //add departamento / faculdade. flag 1 - dep, flag 2 - fac
    public boolean rmDepFac(int dep, int flag) throws RemoteException; //remove departamento / faculdade. flag 1 - dep, flag 2 - fac
    public boolean editDepFac(int dep, String newName, int flag) throws RemoteException; //edita nome de departamento / faculdade. flag 1 - dep, flag 2 - fac
    public boolean criaEleicaoNE(String beginning, String end, String title, String description, int dep) throws RemoteException; //cria eleição Nucleo de estudantes. . As eleições para núcleo de estudantes decorrem num único departamento e podem votar apenas os estudantes desse departamento.
    public boolean criaEleicaoCG(String beginning, String end, String title, String description) throws RemoteException; // os estudantes votam apenas nas listas de estudantes, os docentes votam apenas nas listas de docentes, e os funcionários votam apenas nas listas de funcionários.
    public boolean criaEleicaoDD(String beginning, String end, String title, String description, int idDep) throws RemoteException; //cria eleição para a direção do departamento, concorrem e votam docentes desse departamento
    public boolean criaEleicaoDF(String beginning, String end, String title, String description, int idFac) throws RemoteException; //cria eleição para a direção da faculdade, cria eleição para a direção do departamento
    public boolean manageList(int idElec, int listType, String List, int flag) throws RemoteException; //flag 1 - add list, flag 2 - remove list
    public ArrayList<String> viewListsFromElection(int id) throws RemoteException; //recebe id da eleição e mostra as listas disponiveis
    public boolean changeElectionsText(int id, String text, int flag) throws RemoteException; //Muda titulo ou descriçao de uma eleiçao. flag 1 - titulo, flag 2 - descrição
    public boolean changeElectionsDates(int id, String newdate, int flag) throws RemoteException; //Muda a hora de uma eleiçao. flag 1 - inicio, flag 2 - fim
    public int checkTable(int idUser, int idElec) throws RemoteException; //saber onde uma pessoa votou, retorna -1 em caso de insucesso
    public java.util.Date showHour(int idUser, int idElec) throws RemoteException; //saber quando uma pessoa votou, retorna algo que indique erro :) nao sei :) fds :)
    public int TableInfo(int idTable, int idElec) throws RemoteException; //realtime info sobre o estado das mesas (return -1) if down, e numero de votos feitos naquela mesa (return n)
    public ArrayList<ArrayList<String>> checkResults(int idElec) throws RemoteException;
    public boolean anticipatedVote(int idElec, int idUser, String vote, String pass) throws RemoteException; //vote antecipado. o int vote é um int da lista de listas disponiveis retornada pela "viewListsFromElection"
    public boolean editPerson(int idUser, String newInfo, int flag) throws RemoteException; //edita a info de uma pessoa, manda a newinfo sempre como string e depois cabe ao server passar de string para int caso seja necessario. flag 1 - name, flag 2 - Address, flag 3 - phone, flag 4 - ccn, flag 5 - ccv, flag 6 - dep, flag 7 - pass
    public ArrayList<String> tableMembers(int idTable) throws RemoteException; //retorna a lista de pessoas que estão na mesa
    public boolean manageTable(int idTable, int idUser, int idNewUser) throws RemoteException; //mudar a pessoa que está na mesa
    public ArrayList<ArrayList<String>> verFaculdades() throws RemoteException;
    public ArrayList<ArrayList<String>> verDepartamentos() throws RemoteException;
    public boolean createList(String nome, int tipo,int eleicao_id) throws RemoteException;
    public ArrayList<ArrayList<String>> showTables(int eleicao_id) throws RemoteException;
    public ArrayList<ArrayList<String>> showUserTable(int eleicao_id, int mesavoto_id) throws RemoteException;
    public boolean addTableToElection(int elecID, int idDep) throws RemoteException;
    public boolean removeTableFromElection(int elecID, int table) throws RemoteException;
    public ArrayList<ArrayList<String>> viewCurrentElections() throws RemoteException; 
    public ArrayList<ArrayList<String>> viewPastCurrentElections() throws RemoteException;
    public ArrayList<ArrayList<String>> viewPastElections() throws RemoteException;
    public ArrayList<ArrayList<String>> viewFutureElections() throws RemoteException;
    public ArrayList<String> printListsFromElection(int id) throws RemoteException;
    public boolean addToTable(int idTable, int numeroCC) throws RemoteException;
}

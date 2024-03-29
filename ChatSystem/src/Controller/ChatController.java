/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GUI.ChatGUI;
import NI.ChatNI;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.ParseException;
import java.util.HashMap;

/**Classe contenant à la fois le modèle et le contrôleur du système, elle sert d'intermédiaire entre les interfaces graphiques et réseau, qui sont les deux vues du programme.
 */
public class ChatController {
    
    /////////////////////////////////////////////////////////////////////
    // MODEL : LOCAL NICKNAME AND LIST OF [Nickname, Ip Address] ////////
    /////////////////////////////////////////////////////////////////////
    
    /** Modèle : Nickname local */
    private String nickname;
    /** Modèle : Liste des utilisateurs connectés et de leurs adresses ip */
    HashMap<String,InetAddress> users;
    
    //////////////
    //GUI and NI//
    //////////////
    
    /**
     * Interface graphique du système
     */
    private ChatGUI gui;
    /**
     * Interface réseau du système
     */
    private ChatNI ni;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    /**Création du ChatGUI et lancement de son thread. Le ChatNI est crée lors de la connexion */
    public ChatController() throws SocketException {
        this.users = new HashMap<String,InetAddress>();
        
        /**Création du ChatGUI, le NI est crée lors de la CONNEXION */
        
        gui=new ChatGUI();
        gui.setChatctr(this);
        
        /**Lancement du Thread du ChatGUI */
        (new Thread(gui)).start(); 
    }
    
    ///////////////////////////////////////////
    ///FONCTIONS DE CONNEXION/DECONNEXION//////
    ///////////////////////////////////////////
    
    /**Demande de connexion lancé a partir du ChatGUI*/
    public void processConnect(String nickname) throws IOException{
        /**CREATION DU CHATNI */
        ni = new ChatNI();
        ni.setControl(this);
        
        /**Instanciation du nickname */
        this.setNickname(nickname);
        
        /**Lancement de la procédure de connexion */
        this.ni.performConnect();
    }
    /** Demande de déconnexion lancée a partir du ChatGUI */
    public void processDisconnect() throws IOException, InterruptedException{
        int i;
        this.ni.performDisconnect();
        /** vider la liste des utilisateurs*/
        users.clear();
        /** fermer les threas et sockets du NI*/
        ni.closeThreads();
        ni.closeSocket();
        this.ni=null;
        this.nickname=null;
    }
    
    ///////////////////////////////////////////
    //// FONCTIONS DE GESTION DE LA LISTE /////
    ///////////////////////////////////////////
    
    /** Rajoute un utilisateur à notre liste d'utilisateurs,
     fonction appelée par le ChatNI*/
    public void addUser(String nickname, InetAddress ip){//ususally called by chatNI
        users.put(nickname, ip);
        gui.updateList(nickname,true);
    }
    /** Supprime un utilisateur de la liste, 
     fonction appelée par le ChatNI*/
    public void deleteUser(String nickname){//usually called by chatNI
        users.remove(nickname);
        gui.updateList(nickname,false);
    }
    
    /////////////////////////////////////////////////////
    ///FONCTIONS D'ENVOI DE MESSAGES ET DE FICHIERS//////
    /////////////////////////////////////////////////////
    
    /** Fonction d'envoi de messages, lancée à partir du ChatGUI */
    public void processSend(String nickname,String message,int conv) throws IOException{
        if (users.containsKey(nickname)){
            ni.sendMessageTo(users.get(nickname), message, conv);
        }
        
    }
   /** Fonction d'envoi de fichiers, lancée à partir du ChatGUI */ 
    public void processSendFile(String nick,File f) {
        ni.transferFile(users.get(nick), f,nick);
    }
    
    /////////////////////////////////////////////////////////
    ///FONCTIONS DE NOTIFICATION DE RECEPTION DE MESSAGE/////
    /////////////////////////////////////////////////////////
    /** Fonction de notification de récéption de message, appelée par le ChatNI,
     cette fonction rajoute le message à l'historique du ChatGUI */
    public void notify(String nickn,String message,int conv) throws ParseException{//called by NI
        gui.addMsgtoHistory(message, nickn);
    }
    
    ///////////////////////////////
    //////GETTERS ET SETTERS///////
    ///////////////////////////////
    
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    
    public static void main(String[] args) throws IOException {
        ChatController ctr=new ChatController();
    }
}

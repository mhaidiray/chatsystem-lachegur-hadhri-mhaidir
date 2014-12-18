/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import chatsystem.chatgui.ChatGUI;
import chatsystem.chatni.ChatNI;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.ParseException;
import java.util.HashMap;

/**
 *
 * @author lachegur
 */
public class ChatController {
    
    /////////////////////////////////////////////////////////////////////
    // MODEL : LOCAL NICKNAME AND LIST OF [Nickname, Ip Address] ////////
    /////////////////////////////////////////////////////////////////////
    /** nom local */
    private String nickname;
    /**contient les personnes connectées */
    HashMap<String,InetAddress> users;
    
    //////////////
    //GUI and NI//
    //////////////

    private ChatGUI gui;
    private ChatNI ni;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    /**création du ChatGUI et lancement de son thread. Le ChatNI est crée lors de la connexion */
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
    /** demande de déconnexion lancée a partir du ChatGUI */
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
    
    /** rajoute un utilisateur à notre liste d'utilisateurs,
     fonction appelée par le ChatNI*/
    public void addUser(String nickname, InetAddress ip){//ususally called by chatNI
        users.put(nickname, ip);
        gui.updateList(nickname,true);
    }
    /** supprimer un utilisateur de la liste, 
     fonction appelée par le ChatNI*/
    public void deleteUser(String nickname){//usually called by chatNI
        users.remove(nickname);
        gui.updateList(nickname,false);
    }
    
    /////////////////////////////////////////////////////
    ///FONCTIONS D'ENVOI DE MESSAGES ET DE FICHIERS//////
    /////////////////////////////////////////////////////
    
    /** fonction d'envoie de messages, lancée à partir du ChatGUI */
    public void processSend(String nickname,String message,int conv) throws IOException{
        if (users.containsKey(nickname)){
            ni.sendMessageTo(users.get(nickname), message, conv);
        }
        
    }
   /** fonction d'envoie de fichiers, lancée à partir du ChatGUI */ 
    public void processSendFile(String nick,File f) {
        ni.transferFile(users.get(nick), f,nick);
    }
    
    /////////////////////////////////////////////////////////
    ///FONCTIONS DE NOTIFICATION DE RECEPTION DE MESSAGE/////
    /////////////////////////////////////////////////////////
    /** fonction de notification de reception de message, appelée par le ChatNI,
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
}

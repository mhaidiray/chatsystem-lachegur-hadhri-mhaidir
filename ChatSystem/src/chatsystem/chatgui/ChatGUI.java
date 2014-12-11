/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatgui;

import chatsystem.ChatController;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFrame;

/**
 *
 * @author lachegur
 */
public class ChatGUI extends JFrame implements Runnable  {
    ////////////////////////////////////
    ////////////DECLARATIONS////////////
    ////////////////////////////////////
    
    private ChatController control;
    
    //Vue d'accueil : elle permet de rentrer un nickname et de se connecter.
    private Accueil accueil;
    
    //Vue principale : elle permet de communiquer, d'envoyer des files, et de se déconnecter.
    private FenetreChat principale;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    public ChatGUI () {
        //PARAMETRAGE DE LA FENETRE
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("ChatSystem");
                
        //CREATION DES DEUX VUES : VUE D'ACCUEIL ET FENETRE DE CHAT
        this.principale=new FenetreChat();
        principale.setGui(this);
        this.accueil=new Accueil();
        accueil.setGui(this);
        
        //INITIALISATION SUR LA VUE D'ACCUEIL
        this.setContentPane(accueil);
        this.setSize(accueil.getPreferredSize());
        this.setVisible(true);
    }
    
    //////////////////////////////////////////////////////////////////////
    ///FONCTIONS DE SWITCH ENTRE LA VUE D'ACCUEIL ET LA FENETRE DE CHAT///
    //////////////////////////////////////////////////////////////////////
    
    public void switchView() {//go from accueil to chatfenetre
        principale.nickname(local_nickname());
        this.setSize(principale.getPreferredSize());
        this.setContentPane(principale);
        this.setVisible(true);
    }
    
    public void switchBack() {//go back to accueil from chatfenetre
        principale.nickname(null);
        principale.initHistory();
        this.setSize(accueil.getPreferredSize());
        this.setContentPane(accueil);
        this.setVisible(true);
    }
    
    public void run () {
        
    }
    
    ///////////////////////////////////////////
    ///FONCTIONS DE CONNEXION/DECONNEXION//////
    ///////////////////////////////////////////
    
    public void performConnect(String nickname) throws IOException {
        this.control.processConnect(nickname);
    }
    
    public void performDisconnect() throws IOException, InterruptedException {
        this.control.processDisconnect();
    }
    
    /////////////////////////////////////////////////////////////
    //// FONCTIONS DE GESTION DE LA LISTE ET DE L'HISTORIQUE/////
    /////////////////////////////////////////////////////////////
    
    public void updateList(String nickname,boolean x){
        principale.updateList(nickname,x);
    }
    
    public void addMsgtoHistory(String msg,String sender) throws ParseException {
        principale.addToHistory(msg,sender);
    }
    
    /////////////////////////////////////////////////////
    ///FONCTIONS D'ENVOI DE MESSAGES ET DE FICHIERS//////
    /////////////////////////////////////////////////////
    
    public void performSend(String msg,String nickname) throws IOException{
        this.control.processSend(nickname,msg,0);
    }
    
    public void performSendFile(String nickname,File f){
        control.processSendFile(nickname, f);
    }
    
    ///////////////////////////////
    //////GETTERS ET SETTERS///////
    ///////////////////////////////
    
      public ChatController getChatctr() {
        return control;
    }
      
    public String local_nickname(){
        return this.control.getNickname();
    }
    
    public void setChatctr(ChatController chatctr) {
        this.control = chatctr;
    }
}

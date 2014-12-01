/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatgui;

import chatsystem.ChatController;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author lachegur
 */
public class ChatGUI extends JFrame implements Runnable  {
    
    private ChatController control;
    private Accueil accueil;
    private FenetreChat principale;
    
    public ChatGUI () {
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.principale=new FenetreChat();
        this.accueil=new Accueil();
        this.setTitle("ChatSystem");
        accueil.setGui(this);
        principale.setGui(this);
        this.setSize(accueil.getPreferredSize());
        this.setContentPane(accueil);
        this.setVisible(true);
    }
    
    public void switchView() {//go from accueil to chatfenetre
        principale.nickname(local_nickname());
        this.setSize(principale.getPreferredSize());
        this.setContentPane(principale);
        this.setVisible(true);
    }
    
    public void switchBack() {//go back to accueil from chatfenetre
        principale.nickname(null);
        principale.clearHistory();
        this.setSize(accueil.getPreferredSize());
        this.setContentPane(accueil);
        this.setVisible(true);
    }
    
    public void run () {
        
    }
      public ChatController getChatctr() {
        return control;
    }
      
    public String local_nickname(){
        return this.control.getNickname();
    }
    
    public void setChatctr(ChatController chatctr) {
        this.control = chatctr;
    }
    
    public void connect(String nickname) throws IOException {
        this.control.processConnect(nickname);
    }
    
    public void disconnect() throws IOException {
        this.control.processDisconnect();
    }
    
    public void send(String msg,String nickname) throws IOException{
        this.control.processSend(nickname,msg,0);
    }
    
    public void updateList(String nickname,boolean x){
        principale.updateList(nickname,x);
    }
    
    public void addMsgtoHistory(String msg,String sender) {
        principale.addToHistory(msg,sender);
    }
}

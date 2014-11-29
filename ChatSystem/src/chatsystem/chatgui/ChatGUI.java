/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatgui;

import chatsystem.ChatController;
import javax.swing.JFrame;

/**
 *
 * @author lachegur
 */
public class ChatGUI implements Runnable{
    
    private ChatController chatctr;
    
    private JFrame accueil;
    private JFrame principale;
    public void run() {
        accueil=new Accueil();
        accueil.setVisible(true);
    }
    
      public ChatController getChatctr() {
        return chatctr;
    }

    public void setChatctr(ChatController chatctr) {
        this.chatctr = chatctr;
    }
    
}

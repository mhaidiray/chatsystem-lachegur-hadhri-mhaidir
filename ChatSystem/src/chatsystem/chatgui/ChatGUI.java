/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatgui;

import javax.swing.JFrame;

/**
 *
 * @author lachegur
 */
public class ChatGUI implements Runnable{
    private JFrame accueil;
    private JFrame principale;
    public void run() {
        accueil=new Accueil();
        accueil.setVisible(true);
    }
    
}

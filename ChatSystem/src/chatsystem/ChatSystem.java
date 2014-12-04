/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;
import chatsystem.chatgui.ChatGUI;
import chatsystem.chatni.ChatNI;
import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author lachegur
 */
public class ChatSystem {

    /**
     * @param args the command line arguments
     * TODO : -régler le problème bizarre de l'historique bugué par moments
     * -ajouter notifications(jlistrenderer?)
     * -gérer la fenetre de sélection de file
     * -se mettre d'accord sur les attributs file : nom, taille, extension
     * -tspsender et tcpreceiver
     * -jouer un son
     * -améliorer le gui
     * -
     */
    public static void main(String[] args) throws IOException {
        ChatController ctr=new ChatController();
    }
}

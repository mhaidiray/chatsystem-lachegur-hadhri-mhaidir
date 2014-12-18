/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controller.ChatController;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Interface graphique du système, et façade reliant tous les objets graphiques
 * au contrôleur du programme.
 */
public class ChatGUI extends JFrame implements Runnable {

    private ChatController control;
    /**
     * Vue d'accueil : elle permet de rentrer un nickname et de se connecter.
     */
    private Accueil accueil;
    /**
     * Vue principale : elle permet de communiquer, d'envoyer des files, et de
     * se déconnecter.
     */
    private FenetreChat principale;
    /**
     * Windowadapter permettant de gérer la fermeture de la fenêtre
     */
    private WindowAdapter wa;
    /**
     * Booléen nous indiquant si l'on est connecté ou pas
     */
    private boolean connected;

    public ChatGUI() {
        //PARAMETRAGE DE LA FENETRE
        wa = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ChatGUI frame = (ChatGUI) e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit the application?",
                        "Exit Application",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    if (connected) {
                        try {
                            frame.performDisconnect();
                        } catch (IOException ex) {
                        } catch (InterruptedException ex) {
                        }
                    }
                    System.exit(0);
                }

            }
        };
        this.setTitle("ChatSystem");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(wa);
        //CREATION DES DEUX VUES : VUE D'ACCUEIL ET FENETRE DE CHAT
        this.principale = new FenetreChat();
        principale.setGui(this);
        this.accueil = new Accueil();
        accueil.setGui(this);

        connected = false;
        //INITIALISATION SUR LA VUE D'ACCUEIL
        this.setContentPane(accueil);
        this.setSize(accueil.getPreferredSize());
        this.setVisible(true);
    }

    /**
     * Le passage de l'acceuil à la fenetre du chat
     */
    public void switchView() {
        principale.nickname(local_nickname());
        this.setSize(principale.getPreferredSize());
        this.setContentPane(principale);
        this.setVisible(true);
        connected = true;
    }

    /**
     * Retour vers accueil depuis la fenetre du chat
     */
    public void switchBack() {
        principale.nickname(null);
        principale.initHistory();
        this.setSize(accueil.getPreferredSize());
        this.setContentPane(accueil);
        this.addWindowListener(null);
        this.setVisible(true);
        connected = false;
    }

    @Override
    public void run() {

    }

    /**
     * Fonction de connexion
     */
    public void performConnect(String nickname) throws IOException {
        this.control.processConnect(nickname);
    }

    /**
     * Fonction de déconnexion
     */
    public void performDisconnect() throws IOException, InterruptedException {
        this.control.processDisconnect();
    }

    /**
     * Gestion de la liste des personnes connectées
     */
    public void updateList(String nickname, boolean x) {
        principale.updateList(nickname, x);
    }

    /**
     * L'ajout des messages à l'historique
     */
    public void addMsgtoHistory(String msg, String sender) throws ParseException {
        principale.addToHistory(msg, sender);
    }

    /**
     * envoi de message
     */
    public void performSend(String msg, String nickname) throws IOException {
        this.control.processSend(nickname, msg, 0);
    }

    /**
     * envoi de fichier
     */
    public void performSendFile(String nickname, File f) {
        control.processSendFile(nickname, f);
    }

    /**
     * GETTERS ET SETTERS
     */
    public ChatController getChatctr() {
        return control;
    }

    public String local_nickname() {
        return this.control.getNickname();
    }

    public void setChatctr(ChatController chatctr) {
        this.control = chatctr;
    }
}

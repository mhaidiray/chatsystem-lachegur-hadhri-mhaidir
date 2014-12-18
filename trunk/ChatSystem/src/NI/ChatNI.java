/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;

import Controller.ChatController;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.text.ParseException;

/**Interface réseau du système, et façade reliant tous les composants réseau du programme au contrôleur.
 */
public class ChatNI {
    
    
    /** Classe UDPSender, chargée de l'envoi des messages textes et des signaux*/
    private UDPSender udpsend;
    /** Classe UDPSender, chargée de la réception des messages textes et des signaux*/
    private UDPReceiver udprcv;
    /** Classe TCPServer, serveur en attente de connexions, utilisé pour la réception de fichiers*/
    private TCPServer tcpserv;
    /** Classe ChatController, intermédiaire entre l'interface graphique et l'interface réseau, contrôleur et modèle de notre application*/
    private ChatController control;
    /** Socket UDP utilisé pour l'envoi et la réception de signaux et de messages textes*/
    private DatagramSocket sock;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    /**Création du socket UDP, des classes UDPSender et UDPReceiver, du TCPServer, et lancement des Threads*/
    public ChatNI() throws SocketException, IOException{
        
        DatagramSocket socket=new DatagramSocket(9876);
        socket.setBroadcast(true);
        sock=socket;
        
        /**Création des classes UDPSender et UDPReceiver*/
        this.udprcv=new UDPReceiver(socket);
        udprcv.setNi(this);
        
        this.udpsend=new UDPSender(socket);
        udpsend.setNi(this);
        
        /**Création du TCPServer*/
        this.tcpserv=new TCPServer();
        tcpserv.setNi(this);
        
        /**Lancement des threads*/
        (new Thread(tcpserv)).start();
        (new Thread(udprcv)).start();
    }
    
    /////////////////////////////////////
    //FERMETURE DES THREADS ET SOCKETS///
    /////////////////////////////////////
    
    /** Fonction de fermeture des threads de UDPReceiver et TCPServer*/
    public void closeThreads() throws IOException, InterruptedException {
        udprcv.close();
        tcpserv.interrupt();
        tcpserv.close();
    }
    
    /** Fonction de fermeture du socket UDP*/
    public void closeSocket() {
        sock.close();
        sock=null;
    }
    
    ///////////////////////////////////////////
    ///FONCTIONS DE CONNEXION/DECONNEXION//////
    ///////////////////////////////////////////
    
    /** Lance la procédure de connexion.
     Fonction appelée par ChatGUI*/
    public void performConnect() throws IOException{//sends a hello in broadcast
       udpsend.sendHello();
    }
    
    /** Lance la procédure de déconnexion.
     * Fonction appelée par ChatGUI*/
    public void performDisconnect() throws IOException{//sends a goodbye to the designed ip
       udpsend.sendGoodBye();
    }
    
    /////////////////////////////////////////////////////
    ///FONCTIONS D'ENVOI DE MESSAGES ET DE FICHIERS//////
    /////////////////////////////////////////////////////
    
    /** Envoie un message à l'ip donnée, ainsi qu'un identifiant de conversation.
     Fonction appelée par ChatGUI*/
    public void sendMessageTo(InetAddress ip,String message,int conv) throws IOException{
        //envoyer le message à l'ip donnée
        udpsend.sendMsg(ip, message);
    }
    
    /** Transfert un fichier à l'ip donnée.
     Fonction appelée par ChatGUI*/
    public void transferFile(InetAddress ip,File file,String nick){
        //Créer une instance tcpsender sur un autre thread, et l'activer
        TCPSender tcpsend=new TCPSender(ip,file,this,nick);
        (new Thread(tcpsend)).start();
    }
    
    //////////////////////////////////////////////////
    ///FONCTIONS DE GESTION DES SIGNAUX ENTRANTS//////
    //////////////////////////////////////////////////
    
    /** Signale la réception d'un message HELLO, en fournissant l'ip et le nickname de l'envoyeur.
     * Fonction appelée par ChatNI*/
    public void processHello(String nickname,InetAddress ip) throws IOException{
        udpsend.sendHelloAck(ip);
        //ajouter l'utilisateur distant dans la liste
        this.control.addUser(nickname, ip);
    }
    
    /** Signale la réception d'un message HELLOACK, en fournissant l'ip et le nickname de l'envoyeur.
     * Fonction appelée par ChatNI*/
    public void processHelloAck(String nickname,InetAddress ip){
        //ajouter l'utilisateur distant dans la liste
        this.control.addUser(nickname, ip);
    }
    
    /** Signale la réception d'un message GOODBYE, en fournissant le nickname de l'envoyeur.
     * Fonction appelée par ChatNI*/
    public void processGoodBye(String nickname){
        //retirer l'utilisateur distant de la liste
        this.control.deleteUser(nickname);
    }
    
    /** Signale la réception d'un message TEXTMESSAGE, en fournissant le message, le nickname de l'envoyeur et un identifiant de conversation.
     * Fonction appelée par ChatNI*/
    public void processMsg(String nickn,String message,int conv) throws ParseException{
        //écrire le message dans l'historique
        control.notify(nickn,message,conv);
    }
    
    ///////////////////////////////
    //////GETTERS ET SETTERS///////
    ///////////////////////////////
    
    public void setControl(ChatController control) {
        this.control = control;
    }
    
    public UDPSender getUdpsend() {
        return udpsend;
    }

    public UDPReceiver getUdprcv() {
        return udprcv;
    }
    
    /**Fonction renvoyant le nickname local*/
    public String local_nickname(){
        //Permet de récupérer le nickname local
        return this.control.getNickname();
    }
}
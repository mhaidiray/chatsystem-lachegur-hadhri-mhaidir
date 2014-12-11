/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import chatsystem.ChatController;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.text.ParseException;

/**
 *
 * @author hadhri
 */
public class ChatNI {
    private UDPSender udpsend;
    private UDPReceiver udprcv;
    private TCPServer tcpserv;
    private ChatController control;
    private DatagramSocket sock;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    public ChatNI() throws SocketException, IOException{
        //Création du socket UDP
        DatagramSocket socket=new DatagramSocket(9876);
        socket.setBroadcast(true);
        sock=socket;
        
        //Création des classes UDPSender et UDPReceiver
        this.udprcv=new UDPReceiver(socket);
        udprcv.setNi(this);
        
        this.udpsend=new UDPSender(socket);
        udpsend.setNi(this);
        
        //Création du TCPServer
        this.tcpserv=new TCPServer();
        tcpserv.setNi(this);
        
        //Lancement des threads
        (new Thread(tcpserv)).start();
        (new Thread(udprcv)).start();
    }
    
    /////////////////////////////////////
    //FERMETURE DES THREADS ET SOCKETS///
    /////////////////////////////////////
    
    public void closeThreads() throws IOException, InterruptedException {
        udprcv.close();
        tcpserv.interrupt();
        tcpserv.close();
    }
    
    public void closeSocket() {
        sock.close();
        sock=null;
    }
    
    ///////////////////////////////////////////
    ///FONCTIONS DE CONNEXION/DECONNEXION//////
    ///////////////////////////////////////////
    
    public void performConnect() throws IOException{//sends a hello in broadcast
       udpsend.sendHello();
    }
    
    public void performDisconnect() throws IOException{//sends a goodbye to the designed ip
       udpsend.sendGoodBye();
    }
    
    /////////////////////////////////////////////////////
    ///FONCTIONS D'ENVOI DE MESSAGES ET DE FICHIERS//////
    /////////////////////////////////////////////////////
    
    public void sendMessageTo(InetAddress ip,String message,int conv) throws IOException{
        //envoyer le message à l'ip donnée
        udpsend.sendMsg(ip, message);
    }
    
    public void transferFile(InetAddress ip,File file,String nick){
        //Créer une instance tcpsender sur un autre thread, et l'activer
        TCPSender tcpsend=new TCPSender(ip,file,this,nick);
        (new Thread(tcpsend)).start();
    }
    
    //////////////////////////////////////////////////
    ///FONCTIONS DE GESTION DES SIGNAUX ENTRANTS//////
    //////////////////////////////////////////////////
    
    public void processHello(String nickname,InetAddress ip) throws IOException{
        udpsend.sendHelloAck(ip);
        //ajouter l'utilisateur distant dans la liste
        this.control.addUser(nickname, ip);
    }
    
    public void processHelloAck(String nickname,InetAddress ip){
        //ajouter l'utilisateur distant dans la liste
        this.control.addUser(nickname, ip);
    }
    
    public void processGoodBye(String nickname){
        //retirer l'utilisateur distant de la liste
        this.control.deleteUser(nickname);
    }
    
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
    
    public String local_nickname(){
        //Permet de récupérer le nickname local
        return this.control.getNickname();
    }
}
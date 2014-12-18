/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Machd
 */
public class TCPServer extends Thread{
    ////////////////////////////////////
    ////////////DECLARATIONS////////////
    ////////////////////////////////////
    
    private ChatNI ni;
    private ServerSocket serv;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    public TCPServer() throws IOException{
        serv=new ServerSocket(6789);
    }
    
    ///////////////////////////////////////////////////////////
    //FONCTION DE FERMETURE DU SOCKET DEPUIS UNE CLASSE EXTERNE
    ///////////////////////////////////////////////////////////
    
    public void close() throws IOException {
        this.serv.close();
    }
    
    ///////////////////////
    //FONCTION PRINCIPALE//
    ///////////////////////
    
    @Override
    public void run() {
        
        /*PRINCIPE DU TCPSERVER : Le TCPServer écoute en permanence, 
        dès qu'une connexion entrante est détectée, il forme la 
        liaison et crée une instance de TCPReceiver sur un nouveau
        thread. Il reprend ensuite directement son travail d'écoute.
        */
        while(true) {
            Socket sock=null;
            try {
                if(!serv.isClosed()){
                    
                    //LE SOCKET SERVEUR ATTEND UNE CONNEXION
                    
                    sock=serv.accept();
                }
            } catch (IOException ex) {
                System.out.println("Fermeture du socket serveur TCP réussie");
            }
                if (sock!=null){
                    
                    //LE SOCKET A RECU UNE CONNEXION
                    
                    System.out.println("Accepted connection : " + sock);
                    
                    //On lance une instance de TCPReceiver, avec le socket
                    //distant, sur un nouveau thread
                    
                    (new Thread((new TCPReceiver(sock,ni)))).start();
                    
                }
        }
        
    }
    
    ////////////////////////////////////////
    ///////////GETTERS AND SETTERS//////////
    ////////////////////////////////////////
    
    public void setNi(ChatNI ni) {
        this.ni = ni;
    }

    public ServerSocket getServ() {
        return serv;
    }
}


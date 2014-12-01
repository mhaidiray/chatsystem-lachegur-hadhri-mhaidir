/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Machd
 */
public class TCPServer implements Runnable{
    private ChatNI ni;
    private ServerSocket serv;
    private boolean stop;
    public void setNi(ChatNI ni) {
        this.ni = ni;
    }

    public ServerSocket getServ() {
        return serv;
    }
    
    public TCPServer() throws IOException{
        serv=new ServerSocket(6789);
        this.stop=false;
    }
    
    public void close() {
        this.stop=true;
    }
    @Override
    public void run() {
        while(true||!stop) {
        Socket sock=null;
        try {
            sock=serv.accept();
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        InetAddress ip=sock.getInetAddress();
        //TODO : réception du fichier
        
        
        
        }
        
        //procédure de fermeture de la classe et du socket
        
        try {
            this.serv.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread.currentThread().interrupt();
    }
    
}


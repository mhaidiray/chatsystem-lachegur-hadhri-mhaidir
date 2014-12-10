/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class TCPServer extends Thread{
    private ChatNI ni;
    private ServerSocket serv;
    public void setNi(ChatNI ni) {
        this.ni = ni;
    }

    public ServerSocket getServ() {
        return serv;
    }
    
    public TCPServer() throws IOException{
        serv=new ServerSocket(6789);
    }
    
    public void close() throws IOException {
        this.serv.close();
    }
    
    @Override
    public void run() {
        while(true) {
            Socket sock=null;
            try {
                if(!serv.isClosed()){
                    sock=serv.accept();
                }
            } catch (IOException ex) {
                //Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
                if (sock!=null){
                    System.out.println("Accepted connection : " + sock);
                    (new Thread((new TCPReceiver(sock,ni)))).start();
                }
        }
        
    }
    
}


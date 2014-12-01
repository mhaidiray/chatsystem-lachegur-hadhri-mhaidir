/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Machd
 */
public class TCPSender extends Thread {
    private File file;
    private Socket sock;
    private ServerSocket srv;
    public TCPSender(File f,Socket sock,ServerSocket srv){
        this.srv=srv;
        this.sock=sock;
        this.file=f;
    }
}

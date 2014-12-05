/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import Signals.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
public class TCPSender extends Thread {
    private File file;
    private InetAddress remoteip;
    public TCPSender(InetAddress ip,File f){
        this.file=f;
        this.remoteip=ip;
    }
    
    public void run() {
        try {
            FileMessage fm=new FileMessage(file);
            Socket sock = new Socket(remoteip,6789);
            
            //Envoi des infos sur le fichier
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            out = new ObjectOutputStream(bos);   
            out.writeObject(fm);
            byte[] byteArray=new byte[512];
            byteArray=bos.toByteArray();
            OutputStream os = sock.getOutputStream();
            os.write(byteArray, 0, byteArray.length);
            System.out.println("File data sent to " + sock);
            
            //Envoi du fichier
            
            byte[] mybytearray = new byte[1024];
            InputStream is = sock.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(mybytearray, 0, mybytearray.length);
            os.write(mybytearray, 0, mybytearray.length);
            System.out.println("File sent to " + sock);
            
            bos.close();
            out.flush();
            os.flush();
            sock.close();
            this.interrupt();
        } catch (IOException ex) {
            Logger.getLogger(TCPSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

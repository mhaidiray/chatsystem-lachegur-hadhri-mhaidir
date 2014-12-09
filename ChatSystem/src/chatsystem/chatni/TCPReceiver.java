/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import Signals.AbstractMessage;
import Signals.FileMessage;
import Signals.Hello;
import Signals.typeContenu;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
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
public class TCPReceiver extends Thread {
    private Socket sock;
    public TCPReceiver(Socket sock) {
        this.sock=sock;
    }
    
    public void run() {
        if (sock!=null){
            try {
                InputStream is = null;
                is = sock.getInputStream();
                byte[] byteArray=new byte[512];
                is.read(byteArray);
                ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArray);
                ObjectInputStream inp=new ObjectInputStream(byteIn);
                AbstractMessage aMessage = (AbstractMessage) inp.readObject();
                  
                FileMessage fmSerialise = (FileMessage) aMessage;
                System.out.println(fmSerialise.getFileSize());
                System.out.println("C'est un FILEMESSAGE ! " + fmSerialise.getNamefile());
		
                byteArray=null;
                byteArray=new byte[(int)fmSerialise.getSize()];
                  
                FileOutputStream fos = new FileOutputStream("./ReceivedFiles/"+fmSerialise.getNamefile());
                int bytesRead = is.read(byteArray);
                System.out.println(bytesRead);
                fos.write(byteArray, 0, byteArray.length);
                sock.close();
                is.close();
                this.interrupt();
            } catch (IOException ex) {
                Logger.getLogger(TCPReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TCPReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
    
}

}
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
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Machd
 */
public class TCPReceiver extends Thread {
    private ChatNI ni;
    private Socket sock;
    public TCPReceiver(Socket sock,ChatNI ni) {
        this.sock=sock;
        this.ni=ni;
    }
    
    public String extractNickname(String nick){
        return nick.substring(0,nick.indexOf('@'));
    }
    
    public InetAddress extractIp(String nick) throws UnknownHostException{
        String aux=nick.substring(nick.indexOf('@')+1);
        return InetAddress.getByName(aux);
    }
    
    public void run() {
        if (sock!=null){
            try {
                // RECEPTION DU FILEMESSAGE
                InputStream is = sock.getInputStream();
                byte[] byteArray=new byte[512];
                is.read(byteArray);
                ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArray);
                ObjectInputStream inp=new ObjectInputStream(byteIn);
                AbstractMessage aMessage = (AbstractMessage) inp.readObject();
                  
                FileMessage fmSerialise = (FileMessage) aMessage;
                System.out.println("C'est un FILEMESSAGE ! " + fmSerialise.getNamefile());
                
		ni.processMsg(extractNickname(fmSerialise.getNickname()), ("File added : "+fmSerialise.getNamefile()), 0);
                
                //RECEPTION DU FILE
                byteArray=null;
                byteArray=new byte[(int)fmSerialise.getSize()]; 
                FileOutputStream fos = new FileOutputStream("./ReceivedFiles/"+fmSerialise.getNamefile());
                int bytesRead;
                while((bytesRead=is.read(byteArray, 0,byteArray.length))!=-1){
                    fos.write(byteArray,0,bytesRead);
                }
                
                sock.close();
                is.close();
                this.interrupt();
                        
            } catch (IOException ex) {
                Logger.getLogger(TCPReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TCPReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(TCPReceiver.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
    
}

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import Signals.AbstractMessage;
import Signals.FileMessage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 *
 * @author Machd
 */
public class TCPReceiver extends Thread {
    private ChatNI ni;
    private Socket sock;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    public TCPReceiver(Socket sock,ChatNI ni) {
        this.sock=sock;
        this.ni=ni;
    }
    
    //////////////////////////////////////////////////////
    ////FONCTIONS D'EXCTRACTION DU NICKNAME ET DE L'IP////
    //////////////////////////////////////////////////////
    
    public String extractNickname(String nick){
        return nick.substring(0,nick.indexOf('@'));
    }
    
    public InetAddress extractIp(String nick) throws UnknownHostException{
        String aux=nick.substring(nick.indexOf('@')+1);
        return InetAddress.getByName(aux);
    }
    
    //////////////////////////////////////////////////////
    /////////////////FONCTION PRINCIPALE//////////////////
    //////////////////////////////////////////////////////
    
    public void run() {
        if (sock!=null){
            try {
                /*PRINCIPE GENERAL : On reçoit d'abord un
                FileMessage avec le nom, la taille, le pseudo
                de l'envoyeur et des récepteurs. On extrait ces infos
                puis on reçoit le ByteArray contenant le fichier
                en lui-même. On crée ensuite le fichier dans un dossier
                "ReceivedFiles" situé à la racine du projet.
                */
                
                /////////////////////////////////
                //// RECEPTION DU FILEMESSAGE////
                /////////////////////////////////
                
                InputStream is = sock.getInputStream();
                byte[] byteArray=new byte[512];
                is.read(byteArray);
                ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArray);
                ObjectInputStream inp=new ObjectInputStream(byteIn);
                AbstractMessage aMessage = (AbstractMessage) inp.readObject();
                  
                FileMessage fmSerialise = (FileMessage) aMessage;
                System.out.println("C'est un FILEMESSAGE ! " + fmSerialise.getNamefile());
                
		ni.processMsg(extractNickname(fmSerialise.getNickname()), ("File added : "+fmSerialise.getNamefile()+"\n   The file was saved to your ChatSystem/ReceivedFiles directory."),0);
                
                ////////////////////////////////////
                ////////RECEPTION DU FICHIER////////
                ////////////////////////////////////
                
                byteArray=null;
                byteArray=new byte[(int)fmSerialise.getSize()]; 
                FileOutputStream fos = new FileOutputStream("./ReceivedFiles/"+fmSerialise.getNamefile());
                int bytesRead;
                while((bytesRead=is.read(byteArray, 0,byteArray.length))!=-1){
                    fos.write(byteArray,0,bytesRead);
                }
                
                ///////////////////////////////////////////
                /////FERMETURE SOCKET, STREAMS, THREAD/////
                ///////////////////////////////////////////
                
                sock.close();
                is.close();
                this.interrupt();
                        
            } catch (IOException ex) {
                System.out.println("Erreur réseau dans la réception du fichier");
            } catch (ClassNotFoundException ex) {
                System.out.println("Erreur de sérialisation dans la réception du fichier");
            } catch (ParseException ex) {
                System.out.println("Erreur de parsing dans la réception du fichier");
            } 
    }
    
}

}
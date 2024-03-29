/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;

import Signals.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**Classe émettrice de fichiers, elle est lancée lorsque l'utilisateur local envoie un fichier et s'arrête lorsque l'envoi est terminé.
 */
public class TCPSender extends Thread {
    ////////////////////////////////////
    ////////////DECLARATIONS////////////
    ////////////////////////////////////
    
    /** Fichier à envoyer*/
    private File file;
    /** Adresse du destinataire*/
    private InetAddress remoteip;
    /** Nickname du destinataire*/
    private String remotenick;
    /** Classe ChatNI, façade intermédiaire entre cette classe et le contrôleur du système*/
    private ChatNI ni;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    /** Constructeur prenant en argument l'ip, le fichier, le nickname et le ChatNI*/
    public TCPSender(InetAddress ip,File f,ChatNI ni,String nick){
        this.file=f;
        this.remoteip=ip;
        this.ni=ni;
        this.remotenick=nick;
    }
    
    /** FONCTION PRINCIPALE : On crée un FileMessage 
            contenant des infos générales sur le fichier (nom, taille, 
            pseudo de l'envoyeur et des recepteurs. On envoie d'abord ce 
            fichier d'infos, avant d'envoyer le fichier en lui-même
            sous forme de ByteArray.
            */
    public void run() {
        try {
            /* FONCTIONNEMENT GENERAL : On crée un FileMessage 
            contenant des infos générales sur le fichier (nom, taille, 
            pseudo de l'envoyeur et des recepteurs. On envoie d'abord ce 
            fichier d'infos, avant d'envoyer le fichier en lui-même
            sous forme de ByteArray.
            */
            
            /////////////////////////////////////
            /////CREATION DU FILEMESSAGE/////////
            /////////////////////////////////////
            
            ArrayList<String> dest=new ArrayList<String>();
            dest.add(remotenick+"@"+remoteip.getHostAddress());
            FileMessage fm=new FileMessage(file.getName(),dest,file.length());
            fm.setNickname(ni.local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());
            Socket sock = new Socket(remoteip,6789);
            System.out.println(dest);
            
            /////////////////////////////////////
            ////////Envoi du FILEMESSAGE/////////
            /////////////////////////////////////
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            out = new ObjectOutputStream(bos);   
            out.writeObject(fm);
            byte[] byteArray=new byte[512];
            byteArray=bos.toByteArray();
            OutputStream os = sock.getOutputStream();
            os.write(byteArray, 0, byteArray.length);
            System.out.println("File data sent to " + sock);
            os.flush();
            
            ////////////////////////////////////
            //////////Envoi du fichier//////////
            ////////////////////////////////////
            
            FileInputStream fis = new FileInputStream(file);
            byte[] mybytearray = new byte[(int)file.length()];
            int bytesRead = fis.read(mybytearray, 0, mybytearray.length);
            os.write(mybytearray, 0, bytesRead);
            System.out.println("File sent to " + sock );
            
            ///////////////////////////////////////////
            /////FERMETURE SOCKET, STREAMS, THREAD/////
            ///////////////////////////////////////////
            
            bos.close();
            out.flush();
            os.flush();
            sock.close();
            file=null;
            this.interrupt();
            
        } catch (IOException ex) {
            System.out.println("Erreur lors de l'envoi du fichier");
        }
    }
}

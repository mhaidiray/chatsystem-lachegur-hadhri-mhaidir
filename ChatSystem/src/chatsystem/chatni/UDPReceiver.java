/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import Signals.TextMessage;
import Signals.Hello;
import Signals.Goodbye;
import Signals.typeContenu;
import Signals.AbstractMessage;
import Signals.HelloAck;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.text.ParseException;
/**
 *
 * @author Machd
 */
public class UDPReceiver implements Runnable{
    ////////////////////////////////////
    ////////////DECLARATIONS////////////
    ////////////////////////////////////
    
    /** Socket UDP utilisé pour l'envoi et la réception de signaux et de messages textes*/
    private DatagramSocket socket=null;
    /** Classe ChatNI, façade intermédiaire entre cette classe et le contrôleur du système*/
    private ChatNI ni;
    /**Booléen permettant d'arrêter le thread actuel*/
    private volatile boolean stop;
    /**Paquet UDP reçu*/
    private DatagramPacket packet=null;
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    public UDPReceiver(DatagramSocket socket) {
        this.socket = socket;
        stop=false;
    }
    
    //////////////////////////////////////////////////////
    ////FONCTIONS D'EXCTRACTION DU NICKNAME ET DE L'IP////
    //////////////////////////////////////////////////////
    
    /** Fonction permettant d'extraire le nickname de l'ensemble "nickname@ip"*/
    public String extractNickname(String nick){
        return nick.substring(0,nick.indexOf('@'));
    }
    
    /** Fonction permettant d'extraire l'ip de l'ensemble "nickname@ip"*/
    public InetAddress extractIp(String nick) throws UnknownHostException{
        String aux=nick.substring(nick.indexOf('@')+1);
        return InetAddress.getByName(aux);
    }
    
    ////////////////////////////////
    ////////FONCTION D'ARRET////////
    ////////////////////////////////
    
    /**Fonction de fermeture du socket*/
    public void close() {
        this.stop=true;
    }
    
    ////////////////////////////////
    //////FONCTION PRINCIPALE///////
    ////////////////////////////////
    
    /**Réception d'un paquet, déserialisation, et traitement du signal*/
    public void run(){
        try {
            byte[] buf=new byte[512];
            packet=new DatagramPacket(buf,buf.length);
            while (!stop){
                
                //////////////////////////////////////////////////
                //RECEPTION D'UN PAQUET ET DESERIALISATION////////
                //////////////////////////////////////////////////
                
                socket.receive(packet);
                ObjectInput in = null;
                ByteArrayInputStream byteIn = new ByteArrayInputStream(packet.getData());
                in = new ObjectInputStream(byteIn);
                AbstractMessage aMessage = (AbstractMessage) in.readObject();
                String nickn=extractNickname(aMessage.getNickname());
                InetAddress remoteip=extractIp(aMessage.getNickname());
                
                ////////////////////////////////////////////////////////////////////////////
                //On considère pour l'instant qu'un utilisateur peur s'envoyer des messages/
                //à lui même, pour tester sa connexion, entre autres.///////////////////////
                //désactiver les messages envoyés à soi-même=> enlever le "true||" ci-bas///
                ////////////////////////////////////////////////////////////////////////////
                
                
                //////////////////////////////
                //TRAITEMENT DU MESSAGE RECU//
                //////////////////////////////
                
                if (!(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress()).equals(aMessage.getNickname())){
                       
                    if (aMessage.getTypeContenu() == typeContenu.HELLO){
                            Hello helloSerialise = (Hello) aMessage;
                            System.out.println("C'est un HELLO ! " + nickn);
                            this.ni.processHello(nickn,remoteip);
                    }
                    else if (aMessage.getTypeContenu() == typeContenu.GOODBYE){
                            Goodbye goodbyeSerialise = (Goodbye) aMessage;
                            System.out.println("C'est un GOODBYE ! " + nickn);
                            this.ni.processGoodBye(nickn);
                    }
                    else if (aMessage.getTypeContenu() == typeContenu.HELLOACK){
                            HelloAck helloackSerialise = (HelloAck) aMessage;
                            System.out.println("C'est un HELLOACK ! " + nickn);
                            this.ni.processHelloAck(nickn, remoteip);
                    }
                    else if (aMessage.getTypeContenu() == typeContenu.TEXTMESSAGE){
                            TextMessage msgSerialise = (TextMessage) aMessage;
                            System.out.println("C'est un TEXTMESSAGE ! " + nickn +":"+msgSerialise.getMessage());
                            this.ni.processMsg(nickn, msgSerialise.getMessage(), 0);
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("Fermeture du socket serveur UDP réussie");
        } catch (IOException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //ARRET DU THREAD
        
        Thread.currentThread().interrupt();
    }
    
    ////////////////////////////////////////
    ///////////GETTERS AND SETTERS//////////
    ////////////////////////////////////////
    
    public ChatNI getNi() {
        return ni;
    }

    public void setNi(ChatNI ni) {
        this.ni = ni;
    }
}
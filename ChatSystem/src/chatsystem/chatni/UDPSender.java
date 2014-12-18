/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;
import Signals.TextMessage;
import Signals.Hello;
import Signals.Goodbye;
import Signals.HelloAck;
import java.io.IOException;
import java.net.*;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
/**
 *
 * @author Machd
 */
public class UDPSender {
    
    ////////////////////////////////////
    ////////////DECLARATIONS////////////
    ////////////////////////////////////
    
    /** Classe ChatNI, façade intermédiaire entre cette classe et le contrôleur du système*/
    private ChatNI ni;
    /** Socket UDP utilisé pour l'envoi et la réception de signaux et de messages textes*/
    private DatagramSocket socket;
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    public UDPSender(DatagramSocket socket) {
        this.socket = socket;
    }
    
    ////////////////////////////////////////
    //FONCTION GENERIQUE D'ENVOI DE PAQUET//
    ////////////////////////////////////////
    
    /**Fonction d'envoi générique*/
    public void send(InetAddress ip, byte[] msg) throws SocketException, IOException{
        DatagramPacket packet = new DatagramPacket(msg,msg.length,ip,9876);
        socket.send(packet);
    }
    
    ////////////////////////////////
    //FONCTIONS D'ENVOI DE SIGNAUX//
    ////////////////////////////////
    
    /**Fonction d'envoi de signal HELLO*/
    public void sendHello() throws IOException{
        //Création d'un signal Hello
        Hello h=new Hello(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());//création de l'instance hello
        
        //Conversion de la classe Hello en bytearray
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(h);
        byte[] byteArray = bos.toByteArray();
        
        //Envoi du bytearray en broadcast
        send(InetAddress.getByName("255.255.255.255"),byteArray);
    }
    
    /**Fonction d'envoi de signal HELLOACK*/
    public void sendHelloAck(InetAddress ip) throws IOException{
        //Création d'un signal HelloAck
        HelloAck h=new HelloAck(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());//création de l'instance hello
        
        //Conversion de la classe HelloAck en bytearray
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(h);
        byte[] byteArray = bos.toByteArray();
        
        //Envoi du bytearray à l'ip fournie en paramètre
        send(ip,byteArray);
    }
    
    /**Fonction d'envoi de signal GOODBYE*/
    public void sendGoodBye() throws IOException{
        //Création d'un signal Goodbye
        Goodbye h=new Goodbye(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());//création de l'instance hello
        
        //Conversion de la classe Goodbye en bytearray
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(h);
        byte[] byteArray = bos.toByteArray();
        
        //Envoi du bytearray en broadcast
        send(InetAddress.getByName("255.255.255.255"),byteArray);
    }
    
    /**Fonction d'envoi de signal TEXTMESSAGE*/
    public void sendMsg(InetAddress ip, String msg) throws SocketException, IOException{
        //Création du Signal TextMessage
        TextMessage h=new TextMessage(msg);
        h.setNickname(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());
        
        //Conversion de la classe TextMessage en bytearray
        ByteArrayOutputStream bos = new ByteArrayOutputStream(256);
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(h);
        byte[] byteArray = bos.toByteArray();
        
        //Envoi du bytearray à l'ip fournie en paramètre
        send(ip,byteArray);
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
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
import chatsystem.chatni.ChatNI;
import java.io.IOException;
import java.net.*;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.NetworkInterface;
import java.util.Enumeration;
/**
 *
 * @author Machd
 */
public class UDPSender {
    private ChatNI ni;

    public ChatNI getNi() {
        return ni;
    }

    public void setNi(ChatNI ni) {
        this.ni = ni;
    }
    private DatagramSocket socket=null;

    public UDPSender(DatagramSocket socket) {
        this.socket = socket;
    }
    
    private DatagramPacket packet=null;
    
    
    public void send(InetAddress ip, byte[] msg) throws SocketException, IOException{
        packet = new DatagramPacket(msg,msg.length,ip,9876);
        socket.send(packet);
    }
    
    
    
    public void sendHello() throws IOException{
        Hello h=new Hello(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());//création de l'instance hello
        //classe -> bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(h);
        byte[] byteArray = bos.toByteArray();
        //envoie en broadcast
        send(InetAddress.getByName("255.255.255.255"),byteArray);
    }
    
    public void sendHelloAck(InetAddress ip) throws IOException{
        HelloAck h=new HelloAck(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());//création de l'instance hello
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(h);
        byte[] byteArray = bos.toByteArray();
        send(ip,byteArray);
    }
    
    public void sendGoodBye(InetAddress ip) throws IOException{
        Goodbye h=new Goodbye(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());//création de l'instance hello
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(h);
        byte[] byteArray = bos.toByteArray();
        send(ip,byteArray);
    }
    
    public void sendMsg(InetAddress ip, String msg) throws SocketException, IOException{
        TextMessage h=new TextMessage(msg);//création de l'instance hello
        h.setNickname(this.getNi().local_nickname()+"@"+InetAddress.getLocalHost().getHostAddress());
        //gérer la liste des destinataires
        ByteArrayOutputStream bos = new ByteArrayOutputStream(256);
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(h);
        byte[] byteArray = bos.toByteArray();
        send(ip,byteArray);
    }
}
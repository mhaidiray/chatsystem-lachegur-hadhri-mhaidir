/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatni;

import chatsystem.ChatController;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author hadhri
 */
public class ChatNI {
    
    private String local_nickname;
    private UDPSender udpsend;
    private UDPReceiver udprcv;
    private ChatController control;

    public void setControl(ChatController control) {
        this.control = control;
    }
    
    public UDPSender getUdpsend() {
        return udpsend;
    }

    public UDPReceiver getUdprcv() {
        return udprcv;
    }

    public void setLocal_nickname(String local_nickname) {
        this.local_nickname = local_nickname;
    }
    
    public String getLocal_nickname() {
        return local_nickname;
    }
    
    public ChatNI() throws SocketException{
        DatagramSocket socket=new DatagramSocket(9876);
        socket.setBroadcast(true);
        
        UDPSender sender=new UDPSender(socket);
        UDPReceiver receiver=new UDPReceiver(socket);
        this.udprcv=receiver;
        this.udpsend=sender;
        receiver.setNi(this);
        sender.setNi(this);
        (new Thread(receiver)).start();
    }
    
    public void closeThreads() {
        udprcv.close();
        //TODO : close thread of tcp instances
    }
    
    public void performConnect() throws IOException{//sends a hello in broadcast
       udpsend.sendHello();
    }
    
    public void disconnectFrom(InetAddress ip) throws IOException{//sends a goodbye to the designed ip
       udpsend.sendGoodBye(ip);
    }
    
    public void processHello(String nickname,InetAddress ip) throws IOException{
        udpsend.sendHelloAck(ip);
        //ajouter l'utilisateur distant dans la liste
        this.control.addUser(nickname, ip);
    }
    public void processHelloAck(String nickname,InetAddress ip){
        //ajouter l'utilisateur distant dans la liste
        this.control.addUser(nickname, ip);
    }
    
    public void processGoodBye(String nickname){
        //retirer l'utilisateur distant de la liste
        this.control.deleteUser(nickname);
    }
    
    public void sendMessageTo(InetAddress ip,String message,int conv) throws IOException{
        udpsend.sendMsg(ip, message);
    }
    
    public void processMsg(InetAddress ip,String message,int conv){
        //écrire le message dans l'historique
        control.notify(ip,message,conv);
    }

    //public void transferFile(ip,filepath,conv){}
    //public void processFile(filepath, remote ip,conv){}
    
    
}
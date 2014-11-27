/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

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

    public UDPSender getUdpsend() {
        return udpsend;
    }

    public void setUdpsend(UDPSender udpsend) {
        this.udpsend = udpsend;
    }

    public UDPReceiver getUdprcv() {
        return udprcv;
    }

    public void setUdprcv(UDPReceiver udprcv) {
        this.udprcv = udprcv;
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
    
    public void setLocal_nickname(String local_nickname) {
        this.local_nickname = local_nickname;
    }
    public String getLocal_nickname() {
        return local_nickname;
    }
    
    public void performConnect() throws IOException{
       udpsend.sendHello();
    }
    
    public void processHello(String nickname,InetAddress ip) throws IOException{
        udpsend.sendHelloAck(ip);
        //ajouter l'utilisateur distant dans la liste
    }
    public void processHelloAck(String nickname,InetAddress ip){
        //ajouter l'utilisateur distant dans la liste
    }
    
    public void processGoodBye(String nickname,InetAddress ip){
        //retirer l'utilisateur distant de la liste
    }
    
    public void sendMessageTo(InetAddress ip,String message,int conv) throws IOException{
        udpsend.sendMsg(ip, message);
    }
    
    public void processMsg(InetAddress ip,String message,int conv){
        //écrire le message dans l'historique
    }

    //public void transferFile(ip,filepath,conv){}
    //public void processFile(filepath, remote ip,conv){}
    
    
}
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
    
    public String local_nickname(){
        return this.control.getNickname();
    }
    private UDPSender udpsend;
    private UDPReceiver udprcv;
    private TCPServer tcpserv;
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

    private DatagramSocket sock;

    public void closeSocket() {
        sock.close();
        sock=null;
    }
    
    public ChatNI() throws SocketException, IOException{
        DatagramSocket socket=new DatagramSocket(9876);
        socket.setBroadcast(true);
        sock=socket;
        this.udprcv=new UDPReceiver(socket);
        this.udpsend=new UDPSender(socket);
        
        udprcv.setNi(this);
        udpsend.setNi(this);
        
        this.tcpserv=new TCPServer();
        tcpserv.setNi(this);
        
        (new Thread(udprcv)).start();
    }
    
    public void closeThreads() {
        udprcv.close();
        tcpserv.close();
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
    
    public void processMsg(String nickn,String message,int conv){
        //écrire le message dans l'historique
        control.notify(nickn,message,conv);
    }

    //public void transferFile(ip,filepath,conv){}
    //public void processFile(filepath, remote ip,conv){}
    
    
}
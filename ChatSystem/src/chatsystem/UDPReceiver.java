/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Signals.*;
import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
/**
 *
 * @author Machd
 */
public class UDPReceiver implements Runnable{
    private DatagramSocket socket=null;
    private ChatNI ni;

    public ChatNI getNi() {
        return ni;
    }

    public void setNi(ChatNI ni) {
        this.ni = ni;
    }
    public UDPReceiver(DatagramSocket socket) {
        this.socket = socket;
    }
    
    public String extractNickname(String nick){
        return nick.substring(0,nick.indexOf('@'));
    }
    
    public InetAddress extractIp(String nick) throws UnknownHostException{
        String aux=nick.substring(nick.indexOf('@')+1);
        return InetAddress.getByName(aux);
    }
    
    private DatagramPacket packet=null;
    public void run(){
        try {
            byte[] buf=new byte[512];
            packet=new DatagramPacket(buf,buf.length);
            while (true){
            socket.receive(packet);
            ByteArrayInputStream bin = null;
            ObjectInput in = null;
            ByteArrayInputStream byteIn = new ByteArrayInputStream(packet.getData());
            in = new ObjectInputStream(byteIn);
            AbstractMessage aMessage = (AbstractMessage) in.readObject();
            String nickn=extractNickname(aMessage.getNickname());
            InetAddress remoteip=extractIp(aMessage.getNickname());
            if (!(this.getNi().getLocal_nickname()+"@"+InetAddress.getLocalHost().getHostAddress()).equals(aMessage.getNickname())){

                if (aMessage.getTypeContenu() == typeContenu.HELLO){
                        Hello helloSerialise = (Hello) aMessage;
                        System.out.println("C'est un HELLO ! " + nickn);
                        this.ni.processHello(nickn,remoteip);
                        
                }
                else if (aMessage.getTypeContenu() == typeContenu.GOODBYE){
                        Goodbye goodbyeSerialise = (Goodbye) aMessage;
                        System.out.println("C'est un GOODBYE ! " + nickn);
                }
                else if (aMessage.getTypeContenu() == typeContenu.HELLOACK){
                        HelloAck helloackSerialise = (HelloAck) aMessage;
                        System.out.println("C'est un HELLOACK ! " + nickn);
                     
                }
                else if (aMessage.getTypeContenu() == typeContenu.TEXTMESSAGE){
                        TextMessage msgSerialise = (TextMessage) aMessage;
                        System.out.println("C'est un TEXTMESSAGE ! " + nickn);
                }
            }
            }
        } catch (SocketException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UDPReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
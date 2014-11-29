/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import chatsystem.chatni.ChatNI;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author lachegur
 */
public class ChatController {
    private ChatNI ni;
    //private ChatGUI gui;
    
    HashMap<String,InetAddress> users;

    public ChatController() throws SocketException {
        this.users = new HashMap<String,InetAddress>();
        ChatNI chatni = new ChatNI();
        this.ni=chatni;
        chatni.setControl(this);
        
    }
    
    public void processConnect(String nickname) throws IOException{
        this.ni.setLocal_nickname(nickname);
        this.ni.performConnect();
    }
    
    public void processDisconnect() throws IOException{
        int i;
        Set<String> set=users.keySet();
        for (String nickn: set){
            this.ni.disconnectFrom(users.get(nickn));
        }
        users.clear();
        
    }
    
    public void addUser(String nickname, InetAddress ip){//ususally called by chatNI
        this.users.put(nickname, ip);
        //ajouter l'utilisateur dans la liste visuelle
    }
    
    public void deleteUser(String nickname){//usually called by chatNI
        this.users.remove(nickname);
        //retirer l'utilisateur dans la liste visuelle
    }
    
    /*public void notify(remote ip, payload,conv){}//called by NI
    public void processSend(nickname,ip,message/filepath,conv){}
    public void fileReceived(filepath,remote ip,conv){}//NI
    public void displayMsg(message, remote ip,conv){}//NI
    */
}

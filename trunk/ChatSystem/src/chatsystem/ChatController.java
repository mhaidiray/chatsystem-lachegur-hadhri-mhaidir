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
        //TODO : fermer les thread du ni
    }
    
    public void addUser(String nickname, InetAddress ip){//ususally called by chatNI
        this.users.put(nickname, ip);
        //TODO : ajouter l'utilisateur dans la liste visuelle
        System.out.println(users);
    }
    
    public void deleteUser(String nickname){//usually called by chatNI
        this.users.remove(nickname);
        //TODO : etirer l'utilisateur dans la liste visuelle
        System.out.println(users);
    }
    
    public void processSend(String nickname,String message,int conv){
        ni.processMsg(users.get(nickname), message, conv);
    }
    
    public void notify(InetAddress ip,String message,int conv){//called by NI
        //TODO : afficher une notification de message recu, stocke le message dans une liste qqpart, attend de l'afficher
    }
    
    
    //public void fileReceived(filepath,remote ip,conv){}//NI
    
    
}

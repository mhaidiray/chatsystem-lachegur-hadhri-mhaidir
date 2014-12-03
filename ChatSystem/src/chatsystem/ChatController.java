/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import chatsystem.chatgui.ChatGUI;
import chatsystem.chatni.ChatNI;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author lachegur
 */
public class ChatController {
    //the model
    private String nickname;
    HashMap<String,InetAddress> users;
    
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    //GUI and NI
    private ChatGUI gui;
    private ChatNI ni;
    
    public ChatController() throws SocketException {
        this.users = new HashMap<String,InetAddress>();
        gui=new ChatGUI();
        gui.setChatctr(this);
        (new Thread(gui)).start(); 
    }
    
    public void processConnect(String nickname) throws IOException{
        ChatNI chatni = new ChatNI();
        this.ni=chatni;
        chatni.setControl(this);
        this.setNickname(nickname);
        this.ni.performConnect();
    }
    
    public void processDisconnect() throws IOException{
        int i;
        Set<String> set=users.keySet();
        for (String nickn: set){
            this.ni.disconnectFrom(users.get(nickn));
        }
        users.clear();
        ni.closeThreads();
        ni.closeSocket();
        this.ni=null;
        this.nickname=null;
    }
    
    public void addUser(String nickname, InetAddress ip){//ususally called by chatNI
        users.put(nickname, ip);
        gui.updateList(nickname,true);
    }
    
    public void deleteUser(String nickname){//usually called by chatNI
        users.remove(nickname);
        gui.updateList(nickname,false);
    }
    
    public void processSend(String nickname,String message,int conv) throws IOException{
        if (users.containsKey(nickname)){
            ni.sendMessageTo(users.get(nickname), message, conv);
        }
    }
    
    public void notify(String nickn,String message,int conv) throws ParseException{//called by NI
        gui.addMsgtoHistory(message, nickn);
    }
    
    
    //public void fileReceived(filepath,remote ip,conv){}//NI
    
    
}

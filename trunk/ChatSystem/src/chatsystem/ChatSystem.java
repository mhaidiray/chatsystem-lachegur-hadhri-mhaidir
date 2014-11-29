/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import chatsystem.chatni.ChatNI;
import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author lachegur
 */
public class ChatSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ChatNI ni=new ChatNI();
        ni.setLocal_nickname("salim");
        ni.performConnect();
        ni.sendMessageTo(InetAddress.getByName("INSA-08135"), "tete de bite", 0);
    }
}

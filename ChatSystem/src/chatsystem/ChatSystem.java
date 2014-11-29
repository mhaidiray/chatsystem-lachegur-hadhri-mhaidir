/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import chatsystem.chatni.ChatNI;
import java.io.IOException;

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
        ni.setLocal_nickname("samih");
        ni.performConnect();
    }
}

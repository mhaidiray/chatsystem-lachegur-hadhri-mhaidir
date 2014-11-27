/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Machd
 */
public class ChatSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException {
        
        ChatNI ni=new ChatNI();
       ni.setLocal_nickname("salim");
       ni.performConnect();
        
    }
    
}
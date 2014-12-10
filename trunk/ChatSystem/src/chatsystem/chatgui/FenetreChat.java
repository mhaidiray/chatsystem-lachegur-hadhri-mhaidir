/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor...
 */
package chatsystem.chatgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

/**
 *
 * @author Samih
 */
public class FenetreChat extends javax.swing.JPanel {
    private ChatGUI gui;
    HashMap<String, String> histoMap = new HashMap<String, String>();
    public void setGui(ChatGUI gui) {
        this.gui = gui;
    }
    
    public void nickname(String nick) {
        this.Nickname.setText(nick);
    }
    /**
     * Creates new form FenetreChat
     */
    
    CellRender cr = new CellRender();
    DefaultListModel model;
    public void updateList(String nickname,boolean x){
        if (!x&&model.contains(nickname)) {
            model.removeElement(nickname);
            histoMap.remove(nickname);
        }
        else if (x&&!model.contains(nickname)){
            model.addElement(nickname);
            histoMap.put(nickname,"Talking to "+nickname);
        }
    }
    
    public void initHistory() {
        HistoricArea.setText("Hello, choose someone to talk to!");
        histoMap.clear();
        model.clear();
        cr.clear();
    }
    
    public void updateHistory() {
        if (!UserList.isSelectionEmpty()) {
            HistoricArea.setText(histoMap.get(UserList.getSelectedValue().toString()));
            HistoricArea.setCaretPosition(HistoricArea.getText().length() -1);
        }
    }
    
    public void addToHistory(String message,String sender) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String text=(histoMap.get(sender)+"\n "+ dateFormat.format(date)+" "+sender+" : "+ message);
        histoMap.put(sender, text);
        //histoMap.replace(sender, (histoMap.get(sender)+"\n "+ dateFormat.format(date)+ " "+sender+" : "+message));
        updateHistory();
        notification(sender);
        UserList.repaint();
    }
    
    public void notification(String nickname) {
        if (!UserList.isSelectionEmpty()) {
            if (model.contains(nickname)&&(!UserList.getSelectedValue().toString().equals(nickname))) {
                cr.addNotif(model.indexOf(nickname));
            }
        }
        else {
            if (model.contains(nickname)) {
                cr.addNotif(model.indexOf(nickname));
            }
        }
    }
    
    public void sendFile(File f) {
            String sender=UserList.getSelectedValue().toString();
            gui.sendFile(sender, f);
    }
    
    public FenetreChat() {
        initComponents();
        model=new DefaultListModel();
        UserList.setModel(model);
        initHistory();
        cr.setList(UserList);
        UserList.setCellRenderer(cr);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        UserList = new javax.swing.JList();
        DisconnectButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        HistoricArea = new javax.swing.JTextArea();
        MessageTF = new javax.swing.JTextField();
        SendButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Nickname = new javax.swing.JTextField();
        AddFile = new javax.swing.JButton();

        setBackground(new java.awt.Color(230, 229, 229));

        UserList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(UserList);

        DisconnectButton.setFont(DisconnectButton.getFont());
        DisconnectButton.setText("Disconnect");
        DisconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisconnectButtonActionPerformed(evt);
            }
        });

        HistoricArea.setColumns(20);
        HistoricArea.setRows(5);
        jScrollPane2.setViewportView(HistoricArea);

        MessageTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MessageTFActionPerformed(evt);
            }
        });

        SendButton.setText("Send");
        SendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendButtonActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(153, 153, 255));
        jLabel1.setText("Hey dear");

        Nickname.setEditable(false);
        Nickname.setBackground(new java.awt.Color(242, 242, 242));
        Nickname.setBorder(null);

        AddFile.setText("Add File");
        AddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(MessageTF, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AddFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Nickname, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DisconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DisconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(Nickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MessageTF, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SendButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AddFile))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed
        if(UserList.getSelectedValue()!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date= new Date();
            String sender=UserList.getSelectedValue().toString();
            String text=(histoMap.get(sender)+"\n "+ dateFormat.format(date)+" Me : "+ MessageTF.getText());
            histoMap.put(sender, text);
            //histoMap.replace(sender, (histoMap.get(sender)+"\n "+ dateFormat.format(date)+" Me : "+ MessageTF.getText()));
            updateHistory();
            try {
                gui.send(MessageTF.getText(), UserList.getSelectedValue().toString());
            } catch (IOException ex) {
                Logger.getLogger(FenetreChat.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        MessageTF.setText(null);
    }//GEN-LAST:event_SendButtonActionPerformed

    private void DisconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisconnectButtonActionPerformed
        gui.switchBack();
        try {
            gui.disconnect();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
            Logger.getLogger(FenetreChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DisconnectButtonActionPerformed

    private void AddFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFileActionPerformed
        JFileChooser jf=new JFileChooser();
           int returnVal = jf.showOpenDialog(null);
        if (returnVal ==jf.APPROVE_OPTION) {
            File file = jf.getSelectedFile();
            sendFile(file);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date= new Date();
            String sender=UserList.getSelectedValue().toString();
            String text=(histoMap.get(sender)+"\n "+ dateFormat.format(date)+" Me : File added : "+ file.getName());
            histoMap.put(sender, text);
            updateHistory();
        }
        else if (returnVal == jf.CANCEL_OPTION) {
        }
        
    }//GEN-LAST:event_AddFileActionPerformed

    private void UserListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserListMouseClicked
         
        if(UserList.getSelectedValue()!=null){
            this.HistoricArea.setText(histoMap.get(UserList.getSelectedValue().toString()));
        }
        cr.deNotif(model.indexOf(UserList.getSelectedValue().toString()));
        UserList.repaint();
    }//GEN-LAST:event_UserListMouseClicked

    private void MessageTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MessageTFActionPerformed
        // TODO add your handling code here:
            if(UserList.getSelectedValue()!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date= new Date();
            String sender=UserList.getSelectedValue().toString();
            String text=(histoMap.get(sender)+"\n "+ dateFormat.format(date)+" Me : "+ MessageTF.getText());
            histoMap.put(sender, text);
            updateHistory();
            try {
                gui.send(MessageTF.getText(), UserList.getSelectedValue().toString());
            } catch (IOException ex) {
                Logger.getLogger(FenetreChat.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        MessageTF.setText(null);
        
    }//GEN-LAST:event_MessageTFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddFile;
    private javax.swing.JButton DisconnectButton;
    private javax.swing.JTextArea HistoricArea;
    private javax.swing.JTextField MessageTF;
    private javax.swing.JTextField Nickname;
    private javax.swing.JButton SendButton;
    private javax.swing.JList UserList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}

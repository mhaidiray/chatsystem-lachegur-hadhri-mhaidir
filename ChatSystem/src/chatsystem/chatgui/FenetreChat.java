/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor...
 */
package chatsystem.chatgui;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

/**
 *
 * @author Samih
 */
public class FenetreChat extends javax.swing.JPanel {
    ////////////////////////////////////
    ////////////DECLARATIONS////////////
    ////////////////////////////////////
    
    private ChatGUI gui;
    CellRender cr = new CellRender();
    DefaultListModel model;
    
    //Hashmap qui associe à un utilisateur l'ensemble des messages
    //échangés avec lui depuis le début de la session.
    HashMap<String, String> histoMap = new HashMap<String, String>();
    
    public void setGui(ChatGUI gui) {
        this.gui = gui;
    }
    
    //Fonction affichant le nickname de l'utilisateur dans un label en haut de la fenêtre
    public void nickname(String nick) {
        this.Nickname.setText(nick);
    }
    
    /////////////////////////////////////////////////////////////
    //// FONCTIONS DE GESTION DE LA LISTE ET DE L'HISTORIQUE/////
    /////////////////////////////////////////////////////////////
    
    //Ajout ou retrait d'utilisateur de la liste
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
    
    //Initialisation de la zone d'historique
    public void initHistory() {
        HistoricArea.setText("Hello, choose someone to talk to!");
        histoMap.clear();
        model.clear();
        cr.clear();
    }
    
    //Fonction utile à la mise à jour du contenu de l'historique suite à
    //un clic dans la liste d'utilisateurs
    public void updateHistory() {
        if (!UserList.isSelectionEmpty()) {
            HistoricArea.setText(histoMap.get(UserList.getSelectedValue().toString()));
            HistoricArea.setCaretPosition(HistoricArea.getText().length() -1);
        }
    }
    
    //Fonction utile à la mise à jour du contenu de l'historique suite à 
    //la réception d'un message
    public void addToHistory(String message,String sender) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String text=(histoMap.get(sender)+"\n "+ dateFormat.format(date)+" "+sender+" : "+ message);
        histoMap.put(sender, text);
        updateHistory();
        notification(sender);
        UserList.repaint();
    }
    
    //Fonction de notification de message reçu
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
    
    /////////////////////////////////////////////////////
    ///FONCTIONS D'ENVOI DE MESSAGES ET DE FICHIERS//////
    /////////////////////////////////////////////////////
    
    public void sendFile(File f) {
        if(UserList.getSelectedValue()!=null){
            String sender=UserList.getSelectedValue().toString();
            gui.performSendFile(sender, f);
        }
        else {
            System.out.println("Vous n'avez pas sélectionné de destinataire pour ce file!");
        }
    }
    
    public void sendMessage(){
        if(UserList.getSelectedValue()!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date= new Date();
            String sender=UserList.getSelectedValue().toString();
            String text=(histoMap.get(sender)+"\n "+ dateFormat.format(date)+" Me : "+ MessageTF.getText());
            histoMap.put(sender, text);
            updateHistory();
            try {
                gui.performSend(MessageTF.getText(), UserList.getSelectedValue().toString());
            } catch (IOException ex) {
                Logger.getLogger(FenetreChat.class.getName()).log(Level.SEVERE, null, ex);
            }
            MessageTF.setText(null);
        }
        else {
            System.out.println("Vous n'avez pas sélectionné de destinataire pour ce message!");
        }
    }
    
    
    ////////////////////////////////////
    ////////////CONSTRUCTEUR////////////
    ////////////////////////////////////
    
    
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
        jLabel1.setText("Connected as");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(MessageTF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(AddFile, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane2))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Nickname, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(345, 345, 345)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(DisconnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DisconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(Nickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SendButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AddFile))
                            .addComponent(MessageTF)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed
        sendMessage();
    }//GEN-LAST:event_SendButtonActionPerformed

    private void DisconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisconnectButtonActionPerformed
        gui.switchBack();
        try {
            gui.performDisconnect();
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
            Logger.getLogger(FenetreChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DisconnectButtonActionPerformed

    private void AddFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFileActionPerformed
        //On utilise un JFILECHOOSER pour choisir le fichier à envoyer
        JFileChooser jf=new JFileChooser();
           int returnVal = jf.showOpenDialog(null);
        if (returnVal ==jf.APPROVE_OPTION) {
            File file = jf.getSelectedFile();
            
            //Affichage de l'envoi du fichier
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date= new Date();
            String sender=UserList.getSelectedValue().toString();
            String text=(histoMap.get(sender)+"\n "+ dateFormat.format(date)+" Me : File added : "+ file.getName());
            histoMap.put(sender, text);
            updateHistory();
            
            //Envoi du fichier
            sendFile(file);
            
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
        sendMessage();    
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

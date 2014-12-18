/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**Classe permettant de styliser les cases de la liste d'utilisateurs, elle permet d'en mettre une en valeur quand un message est reçu, par exemple.
 */
public class CellRender extends JLabel implements ListCellRenderer {

    private JList list;
    private  HashMap<Integer,Integer> usersNotif=new HashMap<Integer,Integer>();

    public CellRender() {
        setOpaque(true);
    }

    /**Ajout - Retrait de notification*/
    
    public void addNotif(int x) {
        int aux=1;
        if (usersNotif.containsKey(x)) {
            aux=usersNotif.get(x)+1;
        }
        usersNotif.put(x,aux);
    }
    
    public void clear(){
        usersNotif.clear();
    }
    
    @Override
    public Component getListCellRendererComponent(JList list,
                                                   Object value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) {

         setText(value.toString());

         Color background;
         Color foreground;

         // check if this cell represents the current DnD drop location
         JList.DropLocation dropLocation = list.getDropLocation();
         if (dropLocation != null
                 && !dropLocation.isInsert()
                 && dropLocation.getIndex() == index) {

             background = Color.RED;
             foreground = Color.WHITE;

         // check if this cell is selected
         } else if (isSelected) {
             background = Color.BLUE;
             foreground = Color.WHITE;
         // unselected, and not the DnD drop location
         }
         //Verification des messages reçus
         else if(usersNotif.containsKey(index)) {
             //si l'utilisateur nous a envoyé un message, on met en valeur la case
             background = Color.GREEN;
             foreground = Color.BLACK;
             setText(value.toString()+" ("+usersNotif.get(index)+")");
         }
         
         else {
             background = Color.WHITE;
             foreground = Color.BLACK;
         }
         

         setBackground(background);
         setForeground(foreground);

         return this;
     }

    //Enlever la notification d'une case
    void deNotif(int indexOf) {
        usersNotif.remove(indexOf);
    }
    public void setList(JList list) {
        this.list = list;
    }
    
    
    
}

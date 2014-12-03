/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.chatgui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Machd
 */
public class CellRender extends JLabel implements ListCellRenderer<Object> {
    private JList list;

    public void setList(JList list) {
        this.list = list;
    }
    public CellRender() {
        setOpaque(true);
    }

    private  ArrayList<Integer> usersNotif=new ArrayList<Integer>();
    
    public void addNotif(int x) {
        usersNotif.add(x);
    }
    
    public void clear(){
        usersNotif.clear();
    }
    
    @Override
    public Component getListCellRendererComponent(JList<?> list,
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
         else if(usersNotif.contains(index)) {
             //setFont(new Font(getFont(),Font.BOLD,getFont().getSize()));
             background = Color.GREEN;
             foreground = Color.WHITE;
         }
         
         else {
             background = Color.WHITE;
             foreground = Color.BLACK;
         }
         

         setBackground(background);
         setForeground(foreground);

         return this;
     }

    void deNotif(int indexOf) {
        usersNotif.remove(indexOf);
    }
    
    
    
    
}

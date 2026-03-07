/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableKamarInap extends DefaultTableCellRenderer {

    /**
     *
     * @param table
     * @param value
     * @param isSelected
     * @param hasFocus
     * @param row
     * @param column
     * @return
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        } 
        switch (table.getValueAt(row,23).toString()) {
            case "Membaik":
                component.setBackground(new Color(240,171,59));
                component.setForeground(new Color(245,255,245));
                break;
            case "Keadaan Khusus":
                component.setBackground(new Color(255,243,109));
                component.setForeground(new Color(120,110,50));
                break;
            case "Meninggal":
                component.setBackground(new Color(50,50,50));
                component.setForeground(new Color(255,255,255));
                break;
            case "Rawat Gabung":
                component.setBackground(new Color(255,102,204));
                component.setForeground(new Color(245,255,245));
                break;
            default:
                break;

        }
        if(!table.getValueAt(row,24).toString().equals("Belum Terkirim")){
            component.setBackground(new Color(106,50,159));
            component.setForeground(new Color(255,255,255));
    
        }
        
        return component;
    }

}

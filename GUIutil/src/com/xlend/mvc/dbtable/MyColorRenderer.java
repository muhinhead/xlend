package com.xlend.mvc.dbtable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Nick Mukhin
 */
public class MyColorRenderer extends JLabel implements TableCellRenderer {

    private Color stripColor = new Color(0, 0, 255, 16);
    private final ITableView tv;

    public MyColorRenderer(ITableView tv) {
        super();
        
        Font font = getFont();
        Font newFont = new Font(font.getName(),font.getStyle(),10);
        setFont(newFont);        
        this.tv = tv;
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Vector line = (Vector) tv.getRowData().get(row);
        if (value != null) {
            setText(value.toString());
        }
        this.setOpaque(true);
        Color backColor = (row % 2 == 0 && !isSelected) ? stripColor : (isSelected ? table.getSelectionBackground() : table.getBackground());
        Color foreColor = isSelected ? table.getSelectionForeground() : table.getForeground();
        setBackground(backColor);
        setForeground(foreColor);
        return this;
    }
}


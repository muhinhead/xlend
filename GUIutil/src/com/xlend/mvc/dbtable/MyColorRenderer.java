package com.xlend.mvc.dbtable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Label;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Nick Mukhin
 */
public class MyColorRenderer extends JLabel implements TableCellRenderer {

    private Color stripColor = new Color(0, 0, 255, 16);
    private Color searchStringColor = Color.BLUE;
    private Color searchBackColor = Color.CYAN;
    private final ITableView tv;

    public MyColorRenderer(ITableView tv) {
        super();

        Font font = getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), 10);
        setFont(newFont);
        this.tv = tv;
    }

//    protected void alignRight(int column) {
//        JLabel label = null;
//        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//        rightRenderer.setHorizontalAlignment(label.RIGHT);
//        tableView.getColumnModel().getColumn(column).setCellRenderer(rightRenderer);
//    }
    
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (row < tv.getRowData().size()) {
            Vector line = (Vector) tv.getRowData().get(row);
            if (value != null) {
                setText(value.toString());
            }
            this.setOpaque(true);
            String searchStr = tv.getSearchString();
            String ceilStr = (String) line.get(column);
            boolean found = (searchStr != null && searchStr.length() > 0 && ceilStr.toUpperCase().indexOf(searchStr.toUpperCase()) >= 0);
            Color backColor = (row % 2 == 0 && !isSelected) ? found ? searchBackColor : stripColor : (isSelected
                    ? table.getSelectionBackground()
                    : found ? searchBackColor : table.getBackground());
            Color foreColor = isSelected ? found ? searchStringColor : table.getSelectionForeground()
                    : found ? searchStringColor : table.getForeground();
            setBackground(backColor);
            setForeground(foreColor);
        }
        return this;
    }
}

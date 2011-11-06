package com.xlend.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nick Mukhin
 */
public class NotEmptyFocusAdapter extends FocusAdapter {

    private Color prevForeground;
    private JTextComponent field;
    private final JLabel label;

    public NotEmptyFocusAdapter(JLabel label, JTextComponent field) {
        this.label = label;
        prevForeground = label.getForeground();
        this.field = field;
    }

    @Override
    public void focusLost(FocusEvent e) {
        Component opposite = e.getOppositeComponent();
        boolean ok = true;
        if (opposite instanceof JButton) {
            JButton opBtn = (JButton) opposite;
            if (opBtn.getText().equals("Cancel")) {
                ok = false;
            }
        }
        if (ok) {
            if (field.getText().trim().length() == 0) {
                label.setForeground(Color.red);
            } else {
                label.setForeground(prevForeground);
            }
        }
    }
}

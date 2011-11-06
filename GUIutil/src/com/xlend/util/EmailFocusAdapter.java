package com.xlend.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nick Mukhin
 */
public class EmailFocusAdapter extends FocusAdapter {

    public static final String EMAIL_REGEX = "^[a-z0-9_\\+-]+(\\.[a-z0-9_\\+-]"
            + "+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2,4})$";
    private Color prevForeground;
    private JTextComponent field;
    private final JLabel label;
    private String prevValue = "";

    public EmailFocusAdapter(JLabel label, JTextComponent field) {
        this.label = label;
        prevForeground = label.getForeground();
        this.field = field;
    }

    @Override
    public void focusLost(FocusEvent e) {
        Component opposite = e.getOppositeComponent();
        boolean ok = true;
        if (!prevValue.equals(field.getText())) {
            if (opposite instanceof JButton) {
                JButton opBtn = (JButton) opposite;
                if (opBtn.getText().equals("Cancel")) {
                    ok = false;
                }
            }
            if (ok) {
                if (!field.getText().matches(EMAIL_REGEX)) {
                    JOptionPane.showMessageDialog(null, "Invalid e-mail", "Error", JOptionPane.ERROR_MESSAGE);
                    label.setForeground(Color.red);
                } else {
                    label.setForeground(prevForeground);
                }
            }
            prevValue = field.getText();
        }
    }
}

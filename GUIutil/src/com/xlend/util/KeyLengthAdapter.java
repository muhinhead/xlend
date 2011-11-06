package com.xlend.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
public class KeyLengthAdapter extends KeyAdapter {

    private int maxLength;

    public KeyLengthAdapter(int maxLength) {
        super();
        this.maxLength = maxLength;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        JTextField field = (JTextField) e.getComponent();
        if (field.getText().length() > maxLength - 1) {
            field.setText(field.getText().substring(0, maxLength - 1));
        }
    }
}

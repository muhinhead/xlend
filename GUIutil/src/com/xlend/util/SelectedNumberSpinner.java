package com.xlend.util;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nick Mukhin
 */
public class SelectedNumberSpinner extends JSpinner {

    public SelectedNumberSpinner(double initialval, double minval, double maxval, double step) {
        super(new SpinnerNumberModel(initialval, minval, maxval, step));

        ((JSpinner.DefaultEditor) getEditor()).getTextField().addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(final FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        ((JTextComponent) e.getSource()).selectAll();
                    }
                });
            }
        });
        addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                setColor();
            }
        });
    }

    public void setValue(Object val) {
        super.setValue(val);
        setColor();
    }

    private void setColor() {
        Color color;
        if (getValue() instanceof Double) {
            color = ((Double) getValue() > 0 ? Color.BLUE : Color.LIGHT_GRAY);
        } else {
            color = ((Integer) getValue() > 0 ? Color.BLUE : Color.LIGHT_GRAY);
        }
        ((JSpinner.DefaultEditor) getEditor()).getTextField().setForeground(color);
    }
}

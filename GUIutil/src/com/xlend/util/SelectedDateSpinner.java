package com.xlend.util;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nick Mukhin
 */
public class SelectedDateSpinner extends JSpinner {
    public SelectedDateSpinner() {
        super(new SpinnerDateModel());
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
    }
}

package com.xlend.gui.work;

import com.xlend.gui.DashBoard;
import com.xlend.gui.XlendWorks;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene.layout.BorderPane;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Nick Mukhin
 */
public class PrintMachineOrderDialog extends PopupDialog {
    private MachineOrderReport preview;
    public PrintMachineOrderDialog(Integer xmachineorderID) {
        super(null, "Machine Order Placement (print preview)", xmachineorderID);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        JScrollPane sp = new JScrollPane(preview = new MachineOrderReport(XlendWorks.getExchanger(), (Integer) getObject()));
        getContentPane().add(sp);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(new JButton(new AbstractAction("Print"){

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    preview.getEditorPanel().print();
                } catch (PrinterException ex) {
                    XlendWorks.logAndShowMessage(ex);
                }
            }
        }));
        btnPanel.add(new JButton(new AbstractAction("Close"){

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        }));
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(1100,700));
    }
    
    
}

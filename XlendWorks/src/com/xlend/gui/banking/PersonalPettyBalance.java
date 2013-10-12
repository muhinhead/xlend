package com.xlend.gui.banking;

import com.xlend.gui.DashBoard;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xemployee;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class PersonalPettyBalance extends PopupDialog {
    private JEditorPane ep;

    public PersonalPettyBalance(Object[] params) {
        super(null, "Outstanding Balance", params);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        Xemployee emp = null;
        Object[] params = (Object[]) getObject();
        if (params[2]==null) {
            params[2] = new Date();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            emp = (Xemployee)XlendWorks.getExchanger().loadDbObjectOnID(Xemployee.class, (Integer)params[1]);
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        StringBuffer html = new StringBuffer("<html>"
                + "<table border=\"0\" style=\"font-size: 100%; font-family: sans-serif\">"
                + "<tr>"
                + "<td align=\"left\">Date:</td>"
                + "<th align=\"right\">"+dateFormat.format((Date)params[2])+"</th>"
                + "</tr>"
                + "<tr>"
                + "<td align=\"left\">Name & Clock Number:</td>"
                + "<th align=\"right\">"+(emp!=null?emp.getFirstName() + " " 
                    + emp.getSurName() + " (" + emp.getClockNum() + ")":"unknown")+"</th>"
                + "</tr>"
                + "<tr>"
                + "<td align=\"left\">Outstanding Balance:</td>"
                + "<th align=\"right\">"+String.format("R%.2f", params[0])+"</th>"
                + "</tr>"
                + "<tr>"
                + "<td align=\"left\">Notes:</td>"
                + "<th rowspan=\"4\" width=\"250\" align=\"left\">"+params[3]+"</th>"
                + "</tr>"
                + "<tr><td></td></tr>"
                + "<tr><td></td></tr>"
                + "<tr><td></td></tr>"
                + "<tr><td></td></tr>"
                + "<tr><td></td></tr>"
                + "<tr><td></td></tr>"
                + "<tr>"
                + "<td align=\"left\">Signature:</td>"
                + "<th>________________________________</th>"
                + "</tr>"
                + "</table>"
                + "</html>");
//        JEditorPane ep;
        add(ep = new JEditorPane("text/html", html.toString()));
        ep.setEditable(false);
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton printBtn;
        btnPanel.add(printBtn = new JButton(new AbstractAction("Print"){

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ep.print();
                } catch (PrinterException ex) {
                    XlendWorks.logAndShowMessage(ex);
                }
            }
        }));
        JButton closeBtn;
        btnPanel.add(closeBtn = new JButton(new AbstractAction("Close"){

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        
        }));
        add(btnPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(printBtn);
    }

    @Override
    public void freeResources() {
    }
}

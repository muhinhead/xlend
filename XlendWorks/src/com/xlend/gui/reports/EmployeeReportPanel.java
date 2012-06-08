package com.xlend.gui.reports;

import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xposition;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Hashtable;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class EmployeeReportPanel extends GeneralReportPanel {

    private final JComboBox categoryCB;
    private JScrollPane scrollPane;
    private static Hashtable<Integer, String> durationMap = new Hashtable<Integer, String>();

    static {
        durationMap.put(0, "Permanent");
        durationMap.put(1, "1 month");
        durationMap.put(2, "2 months");
        durationMap.put(3, "3 months");
        durationMap.put(6, "6 months");
        durationMap.put(12, "1 year");
    }

    public EmployeeReportPanel(IMessageSender exchanger) {
        super(exchanger);
        DefaultComboBoxModel cats = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadWageCategories(exchanger)) {
            cats.addElement(ci);
        }
        JPanel upperPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        upperPane.add(new JLabel("  Wage Category:"));
        upperPane.add(categoryCB = new JComboBox(cats));
        add(upperPane, BorderLayout.NORTH);
        categoryCB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                updateReport();
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    @Override
    public void updateReport() {
        if (scrollPane != null && editorPanel != null) {
            scrollPane.remove(scrollPane);
            remove(scrollPane);
        }
        editorPanel = null;
        JEditorPane p;
        scrollPane = new JScrollPane(p = createEditorPanel());
        setVisible(false);
        add(scrollPane, BorderLayout.CENTER);
        p.setCaretPosition(0);
        setVisible(true);
    }

    @Override
    protected JEditorPane createEditorPanel() {
        StringBuffer html = new StringBuffer("<html>"
                + "<table>"
                + "<tr>"
                + "<td><img margin=20 src='file:./images/XlendCost.jpg'/></td>"
                + "<td style=\"font-size: 80%; font-family: sans-serif\" vallign=\"center\"><H3 allign=\"left\">Employee Report</H3><BR>"
                + "<H4 allign=\"left\">Wage Category: " + categoryCB.getSelectedItem().toString() + "</H4>"
                + Calendar.getInstance().getTime().toString() + "</td>"
                + "</tr>"
                + "</table>"
                + "<table frame=\"abowe\" width=\"500\"><tr bgcolor=\"#dedede\">"
                + "<th width=\"15%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">Name</th>"
                + "<th width=\"15%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">Surname</th>"
                + "<th width=\"10%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">ID Number</th>"
                + "<th width=\"10%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">Tel Number 1</th>"
                + "<th width=\"10%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">Tel Number 1</th>"
                + "<th width=\"10%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">Work Position</th>"
                + "<th width=\"10%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">Rate of Pay</th>"
                + "<th width=\"10%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">Contract Duration</th>"
                + "<th width=\"10%\" align=\"left\" style=\"font-size: 80%; font-family: sans-serif\">Start Date</th>"
                + getEmployeeListHTML()
                + "</tr>"
                + "<tr bgcolor=\"#dedede\"><th align=\"left\" style=\"font-size: 80%; font-family: sans-serif\"> </th>"
                + "</table>"
                + "</html>");

        editorPanel = new JEditorPane("text/html", html.toString());
//        editorPanel.setFont(editorPanel.getFont().deriveFont(1, (float) 8.0));
        return editorPanel;
    }

    private String getEmployeeListHTML() {
        StringBuffer body = new StringBuffer("");
        try {
            ComboItem itm = (ComboItem) categoryCB.getSelectedItem();
            DbObject[] recs = exchanger.getDbObjects(Xemployee.class, "wage_category=" + itm.getId(), "sur_name");
            for (DbObject rec : recs) {
                Xemployee emp = (Xemployee) rec;
                Xposition pos = (Xposition) exchanger.loadDbObjectOnID(Xposition.class, emp.getXpositionId());
                String line = "<tr><td style=\"font-size: 80%; font-family: sans-serif\">" + ifNull(emp.getFirstName()) + "</td>"
                        + "<td style=\"font-size: 80%; font-family: sans-serif\">" + ifNull(emp.getSurName()) + "</td>"
                        + "<td style=\"font-size: 80%; font-family: sans-serif\">" + ifNull(emp.getClockNum()) + "</td><"
                        + "<td style=\"font-size: 80%; font-family: sans-serif\">" + ifNull(emp.getPhone0Num()) + "</td>"
                        + "<td style=\"font-size: 80%; font-family: sans-serif\">" + ifNull(emp.getPhone1Num()) + "</td>"
                        + "<td style=\"font-size: 80%; font-family: sans-serif\">" + (pos == null ? "" : ifNull(pos.getPos())) + "</td>"
                        + "<td style=\"font-size: 80%; font-family: sans-serif\">" + String.format("%.02f", emp.getRate()) + "</td>"
                        + "<td style=\"font-size: 80%; font-family: sans-serif\">" + (emp.getContractLen() == null ? "" : durationMap.get(emp.getContractLen())) + "</td>"
                        + "<td style=\"font-size: 80%; font-family: sans-serif\">" + (emp.getContractStart() == null ? "" : emp.getContractStart().toString()) + "</td>"
                        + "</tr>";
                body.append(line);
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return body.toString();
    }

    private static String ifNull(String s) {
        return s == null ? "" : s;
    }
}

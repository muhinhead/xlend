package com.xlend.gui.reports;

import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xposition;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
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
        upperPane.add(new JLabel("  Wage Category:"));
        upperPane.add(categoryCB = new JComboBox(cats));
        categoryCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                html = null;
                updateReport();
            }
        });
    }

    @Override
    protected JEditorPane createEditorPanel() {
        if (html == null) {
            prevZoomerValue = zoomer.getValue();
            html = new StringBuffer("<html>"
                    + "<table border=\"0\">"
                    + "<tr><table>"
                    + "<tr>"
                    + "<td rowspan=\"3\" style=\"font-size: " + (prevZoomerValue - 10) + "%; font-family: sans-serif\" ><img margin=20 src='file:./images/XlendCost.jpg'/><br>" + Calendar.getInstance().getTime().toString() + "</td>"
                    + "<th style=\"font-size: " + (prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Employee Report</th>"
                    + "</tr>"
                    + "<tr><th style=\"font-size: " + (prevZoomerValue * 1.2) + "%; font-family: sans-serif\">Wage Category: " + categoryCB.getSelectedItem().toString() + "</th>"
                    + "</tr><tr> </tr>"
                    + "</table></tr>"
                    + "<tr><table frame=\"abowe\" ><tr bgcolor=\"#dedede\">"
                    + "<th width=\"15%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Name</th>"
                    + "<th width=\"15%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Surname</th>"
                    + "<th width=\"10%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">ID Number</th>"
                    + "<th width=\"10%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Tel Number 1</th>"
                    + "<th width=\"10%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Tel Number 2</th>"
                    + "<th width=\"10%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Work Position</th>"
                    + "<th width=\"10%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Rate of Pay</th>"
                    + "<th width=\"10%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Contract Duration</th>"
                    + "<th width=\"10%\" align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Start Date</th>"
                    + getEmployeeListHTML()
                    + "</tr>"
                    + "<tr bgcolor=\"#dedede\"><th align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\"> </th>"
                    + "</table></tr>"
                    + "</table>"
                    + "</html>");
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
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
                String line = "<tr><td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + ifNull(emp.getFirstName()) + "</td>"
                        + "<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + ifNull(emp.getSurName()) + "</td>"
                        + "<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + ifNull(emp.getClockNum()) + "</td><"
                        + "<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + ifNull(emp.getPhone0Num()) + "</td>"
                        + "<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + ifNull(emp.getPhone1Num()) + "</td>"
                        + "<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + (pos == null ? "" : ifNull(pos.getPos())) + "</td>"
                        + "<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + String.format("%.02f", emp.getRate()) + "</td>"
                        + "<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + (emp.getContractLen() == null ? "" : durationMap.get(emp.getContractLen())) + "</td>"
                        + "<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + (emp.getContractStart() == null ? "" : emp.getContractStart().toString()) + "</td>"
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

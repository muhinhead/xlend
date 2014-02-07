package com.xlend.gui;

import com.xlend.constants.Selects;
import com.xlend.gui.reports.GeneralReportPanel;
import com.xlend.remote.IMessageSender;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;

/**
 *
 * @author Nick Mukhin
 */
public class AssignmentsReport extends GeneralReportPanel {

    private final SelectedDateSpinner onDateSP;
    private final JButton filterBtn;
    private final String OLD_WHERE = "where a.date_end is null";

    public AssignmentsReport(IMessageSender exchanger) {
        super(exchanger, null);
        upperPane.add(new JLabel("On date:"));
        upperPane.add(onDateSP = new SelectedDateSpinner());
        upperPane.add(filterBtn = new JButton(new AbstractAction("show") {//null, new ImageIcon(Util.loadImage("filter-icon.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                html = null;
                updateReport();
            }
        }));
        onDateSP.setEditor(new JSpinner.DateEditor(onDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(onDateSP);
    }

    @Override
    protected JEditorPane createEditorPanel() {
        if (html == null) {
            prevZoomerValue = zoomer.getValue();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = (Date) onDateSP.getValue();
            html = new StringBuffer("<html>"
                    + "<table border=\"0\">"
                    + "<tr><table>"
                    + "<tr>"
                    + "<td rowspan=\"3\" style=\"font-size: " + (int) (prevZoomerValue - 10) + "%; font-family: sans-serif\" ><img margin=20 src='file:./images/XlendCost.jpg'/><br>" + Calendar.getInstance().getTime().toString() + "</td>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Assignments Report</th>"
                    + "</tr>"
                    + "<tr><th style=\"font-size: " + (int) (prevZoomerValue * 1.1) 
                    + "%; font-family: sans-serif\">Operators and machines working on sites at "+dateFormat.format(dt) +"</th>"
                    + "</tr>"
                    + "</table></tr>"
                    + "<tr><table frame=\"abowe\" ><tr bgcolor=\"#dedede\">"
                    + "<th align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Site</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Order No</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Client</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Machine</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Clock No</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">First Name</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Surname</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">From</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">To</th>"
                    + getAssignmentsInfoHTML()
                    + "</tr>"
                    + "</table>"
                    + "</html>");
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }

    private String getAssignmentsInfoHTML() {
        StringBuilder body = new StringBuilder("");
        String prevSite = "---";
        String curSite = "";
        String prevOrder = "---";
        String curOrder = "";
        String prevClient = "---";
        String curClient = "";
        try {
            StringBuilder select = new StringBuilder(Selects.SELECT_ASSIGNMENTSREPORT);
            if (onDateSP.getValue() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String sd = dateFormat.format((Date) onDateSP.getValue());
                String whereCond = "where '" + sd + "' between a.date_start and ifnull(a.date_end, now())";
                int p = select.indexOf(OLD_WHERE);
                select.replace(p, p + OLD_WHERE.length(), whereCond);
            }
            Vector[] info = exchanger.getTableBody(select.toString());
            Vector tabBody = info[1];
            for (int i = 0; i < tabBody.size(); i++) {
                Vector ceils = (Vector) tabBody.get(i);
                body.append("<tr>");
                for (int c = 0; c < ceils.size(); c++) {
                    String ceil = (String) ceils.get(c);
                    switch (c) {
                        case 0:
                            curSite = ceil;
                            break;
                        case 1:
                            curOrder = ceil;
                            break;
                        case 2:
                            curClient = ceil;
                            break;
                    }
                    body.append("<td style=\"font-size: ").append(zoomer.getValue())
                            .append("%; font-family: sans-serif\">")
                            .append(curSite.equals(prevSite) && c == 0 ? "---"
                            : (curOrder.equals(prevOrder) && c == 1 ? "---"
                            : (curClient.equals(prevClient) && c == 2 ? "---" : ceil))).append("</td>");
                    if (c == ceils.size() - 1) {
                        prevSite = curSite;
                        prevOrder = curOrder;
                        prevClient = curClient;
                    }
                }
                body.append("</tr>");
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return body.toString();
    }
}

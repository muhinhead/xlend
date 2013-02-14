package com.xlend.gui.reports;

import com.xlend.remote.IMessageSender;
import java.util.Calendar;
import javax.swing.JEditorPane;

/**
 *
 * @author Nick Mukhin
 */
public class IncidentsReport extends GeneralReportPanel {

    public IncidentsReport(IMessageSender exchanger) {
        super(exchanger);
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
                    + "<th style=\"font-size: " + (prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Incidents Report</th>"
                    + "</tr>"
                    + "<tr><th style=\"font-size: " + (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">Incidents </th>"
//                    + "<tr><tr></tr>"
//                    + "</tr><tr> "
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
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Date assigned</th>"
//                    + getAssignmentsInfoHTML()
                    + "</tr>"
                    + "</table>"
                    + "</html>");
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }
    
}

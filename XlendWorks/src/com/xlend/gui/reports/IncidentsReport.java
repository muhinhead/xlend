package com.xlend.gui.reports;

import com.xlend.constants.Selects;
import com.xlend.gui.XlendWorks;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Vector;
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
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Incidents Report</th>"
                    + "</tr>"
                    + "<tr><th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">Incidents </th>"
                    //                    + "<tr><tr></tr>"
                    //                    + "</tr><tr> "
                    + "</tr>"
                    + "</table></tr>"
                    + "<tr><table frame=\"abowe\" ><tr bgcolor=\"#dedede\">"
                    + "<th align=\"left\" width=\"15%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Name</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Clock No</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Dates</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Costs</th>"
                    + "<th align=\"left\" width=\"15%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Vehicle</th>"
                    + "<th align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Description</th>"
                    + getIncidentsInfoHTML()
                    + "</tr>"
                    + "</table>"
                    + "</html>");
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }

    private String getIncidentsInfoHTML() {
        StringBuilder body = new StringBuilder("");
        String curClockNo = "";
        String prevClockNo = "---";
        try {
            Vector[] info = exchanger.getTableBody(Selects.SELECT_INCIDENTSREPORT);
            Vector tabBody = info[1];
            for (int i = 0; i < tabBody.size(); i++) {
                Vector ceils = (Vector) tabBody.get(i);
                curClockNo = (String) ceils.get(1);
                body.append("<tr>");
                for (int c = 0; c < ceils.size(); c++) {
                    String ceil = (String) ceils.get(c);
                    body.append("<td style=\"font-size: ").append(zoomer.getValue()).append("%;")
                            .append(c == 3 ? "align=\"right\"" : "")
                            .append("font-family: sans-serif\">")
                            .append(c < 2 && prevClockNo.equals(curClockNo) ? "" : ceil).append("</td>");
                    if (c == ceils.size() - 1) {
                        prevClockNo = curClockNo;
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

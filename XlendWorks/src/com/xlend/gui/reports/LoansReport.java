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
public class LoansReport extends GeneralReportPanel {

    public LoansReport(IMessageSender exchanger) {
        super(exchanger);
    }

    @Override
    protected JEditorPane createEditorPanel() {
        StringBuffer html = new StringBuffer(
                "<html>"
                + "<table border=\"0\">"
                + "<tr><table>"
                + "<tr>"
                + "<td rowspan=\"3\" style=\"font-size: " + (zoomer.getValue() - 10) + "%; font-family: sans-serif\" ><img margin=20 src='file:./images/XlendCost.jpg'/><br>" + Calendar.getInstance().getTime().toString() + "</td>"
                + "<th style=\"font-size: " + (zoomer.getValue() * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Loans Report</th>"
                + "</tr>"
                + "<tr><tr></tr>"
                //                + "<th style=\"font-size: " + (zoomer.getValue()*1.2) + "%; font-family: sans-serif\">Outstanding Ammounts</th>"
                + "</tr><tr> </tr>"
                + "</table></tr>"
                + "<tr><table frame=\"abowe\" ><tr bgcolor=\"#dedede\">"
                + "<th align=\"left\" style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">Clock No</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">First Name</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">Surname</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">Date issued</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">Amount</th>"
                //                + "<th width=\"10%\" align=\"right\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\">&gt 120</th>"
                //                + "<th width=\"10%\" align=\"right\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\">Total</th>"
                + getLoansInfoHTML()
                + "</tr>"
                //                + "<tr bgcolor=\"#dedede\"><th align=\"left\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\">Total:</th>"
                //                + "<th align=\"right\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\" " + getColor(total30) + ">R " + stdFormat(total30) + "</th>"
                //                + "<th align=\"right\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\" " + getColor(total60) + ">R " + stdFormat(total60) + "</th>"
                //                + "<th align=\"right\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\" " + getColor(total90) + ">R " + stdFormat(total90) + "</th>"
                //                + "<th align=\"right\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\" " + getColor(total120) + ">R " + stdFormat(total120) + "</th>"
                //                + "<th align=\"right\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\" " + getColor(total0) + ">R " + stdFormat(total0) + "</th>"
                //                + "<th align=\"right\" style=\"font-size: "+zoomer.getValue()+"%; font-family: sans-serif\" " + getColor(total) + ">R " + stdFormat(total) + "</th></tr>"
                + "</table></tr>"
                + "</table>"
                + "</html>");
        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }

    private String getLoansInfoHTML() {
        StringBuffer body = new StringBuffer("");
        String prevClockNum = "---";
        String curClockNum = "";
        try {
            Vector[] info = exchanger.getTableBody(Selects.SELECT_LOANLIST);
            Vector tabBody = info[1];
            for (int i = 0; i < tabBody.size(); i++) {
                Vector ceils = (Vector) tabBody.get(i);
                body.append("<tr>");
                for (int c = 0; c < ceils.size(); c++) {
                    String ceil = (String) ceils.get(c);
                    if (c == 0) {
                        curClockNum = ceil;
                    }
                    body.append("<td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">"
                            + (curClockNum.equals(prevClockNum) && c < 3 ? "" : ceil)
                            + "</td>");
                    if (c == ceils.size() - 1) {
                        prevClockNum = curClockNum;
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

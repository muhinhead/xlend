package com.xlend.gui.reports;

import com.xlend.constants.Selects;
import com.xlend.gui.XlendWorks;
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
public class LoansReport extends GeneralReportPanel {

    private final SelectedDateSpinner endDateSP;
    private final SelectedDateSpinner startDateSP;

    public LoansReport(IMessageSender exchanger) {
        super(exchanger);
        upperPane.add(new JLabel("  Dates between:"));
        upperPane.add(startDateSP = new SelectedDateSpinner());
        upperPane.add(new JLabel(" and "));
        upperPane.add(endDateSP = new SelectedDateSpinner());
        upperPane.add(new JButton(new AbstractAction("show") {//null, new ImageIcon(Util.loadImage("filter-icon.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                html = null;
                updateReport();
            }
        }));
        startDateSP.setEditor(new JSpinner.DateEditor(startDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(startDateSP);
        endDateSP.setEditor(new JSpinner.DateEditor(endDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(endDateSP);
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.YEAR, 1990);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        startDateSP.setValue(firstDayOfMonth);
    }

    @Override
    protected JEditorPane createEditorPanel() {
        if (html == null) {
            prevZoomerValue = zoomer.getValue();
            html = new StringBuffer(
                    "<html>"
                    + "<table border=\"0\">"
                    + "<tr><table>"
                    + "<tr>"
                    + "<td rowspan=\"3\" style=\"font-size: " + (prevZoomerValue - 10) + "%; font-family: sans-serif\" ><img margin=20 src='file:./images/XlendCost.jpg'/><br>" + Calendar.getInstance().getTime().toString() + "</td>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Loans Report</th>"
                    + "</tr>"
                    + "</tr>"
                    + "</table></tr>"
                    + "<tr><table frame=\"abowe\" ><tr bgcolor=\"#dedede\">"
                    + "<th align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Clock No</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">First Name</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Surname</th>"
                    + "<th align=\"left\" width=\"10%\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Date issued</th>"
                    + "<th align=\"left\" width=\"10%\" align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Amount</th>"
                    //                + "<th width=\"10%\" align=\"right\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\">&gt 120</th>"
                    //                + "<th width=\"10%\" align=\"right\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\">Total</th>"
                    + getLoansInfoHTML()
                    + "</tr>"
                    //                + "<tr bgcolor=\"#dedede\"><th align=\"left\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\">Total:</th>"
                    //                + "<th align=\"right\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\" " + getColor(total30) + ">R " + stdFormat(total30) + "</th>"
                    //                + "<th align=\"right\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\" " + getColor(total60) + ">R " + stdFormat(total60) + "</th>"
                    //                + "<th align=\"right\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\" " + getColor(total90) + ">R " + stdFormat(total90) + "</th>"
                    //                + "<th align=\"right\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\" " + getColor(total120) + ">R " + stdFormat(total120) + "</th>"
                    //                + "<th align=\"right\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\" " + getColor(total0) + ">R " + stdFormat(total0) + "</th>"
                    //                + "<th align=\"right\" style=\"font-size: "+prevZoomerValue+"%; font-family: sans-serif\" " + getColor(total) + ">R " + stdFormat(total) + "</th></tr>"
                    + "</table></tr>"
                    + "</table>"
                    + "</html>");
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }

    private String getLoansInfoHTML() {
        StringBuffer body = new StringBuffer("");
        String prevClockNum = "---";
        String curClockNum = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sd1 = dateFormat.format((Date) startDateSP.getValue());
            String sd2 = dateFormat.format((Date) endDateSP.getValue());
            String select = Selects.SELECT_LOANLIST;
            select = select.replaceFirst(" where ", " where issueddate between '"+sd1+"' and '"+sd2+"' and ");
            Vector[] info = exchanger.getTableBody(select);
            Vector tabBody = info[1];
            for (int i = 0; i < tabBody.size(); i++) {
                Vector ceils = (Vector) tabBody.get(i);
                body.append("<tr>");
                for (int c = 0; c < ceils.size(); c++) {
                    String ceil = (String) ceils.get(c);
                    if (c == 0) {
                        curClockNum = ceil;
                    }
                    body.append("<td " + (c == ceils.size() - 1 ? "align=\"right\" " : "") + " style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">"
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

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
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class IncidentsReport extends GeneralReportPanel {

    private final SelectedDateSpinner startDateSP;
    private final SelectedDateSpinner endDateSP;
    private final JLabel fromLbl;
    private final JLabel toLbl;
    private final JCheckBox showFilterCB;
    private final JButton filterBtn;
//    private final SimpleDateFormat mysqlDateFormat;

    public IncidentsReport(IMessageSender exchanger) {
        super(exchanger);
//        mysqlDateFormat = new SimpleDateFormat("yyyy-MM-DD");
        upperPane.add(showFilterCB = new JCheckBox(new AbstractAction("Date Filter") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledDateFilter(showFilterCB.isSelected());
                adjustCache();
                if (!showFilterCB.isSelected()) {
                    html = null;
                    updateReport();
                }
            }
        }));
        showFilterCB.setHorizontalTextPosition(SwingConstants.LEFT);
        upperPane.add(fromLbl = new JLabel("from:"));
        upperPane.add(startDateSP = new SelectedDateSpinner());
        upperPane.add(toLbl = new JLabel("to:"));
        upperPane.add(endDateSP = new SelectedDateSpinner());
        upperPane.add(filterBtn = new JButton(new AbstractAction("show") {//null, new ImageIcon(Util.loadImage("filter-icon.png"))) {
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
        setEnabledDateFilter(false);

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        startDateSP.setValue(firstDayOfMonth);
    }

    private void setEnabledDateFilter(boolean enable) {
        for (JComponent c : new JComponent[]{fromLbl, toLbl, startDateSP, endDateSP, filterBtn}) {
            c.setEnabled(enable);
        }
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
                    + "<tr><th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">Incidents for all time</th>"
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
//        } else {
//            adjustCache();
        }
        adjustCache();

        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }

    protected void adjustCache() {
        super.adjustCache();
        int p = html.indexOf("Incidents for ");
        int pp = html.indexOf("</th>", p);
        if (!showFilterCB.isSelected()) {
            html.replace(p, pp, "Incidents for all time");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = (Date) startDateSP.getValue();
            Date endDate = (Date) endDateSP.getValue();
            html.replace(p, pp, "Incidents for period: "
                    + dateFormat.format(startDate) + " - " + dateFormat.format(endDate));
        }
    }

    private String getIncidentsInfoHTML() {
        StringBuilder body = new StringBuilder("");
        String curClockNo = "";
        String prevClockNo = "---";
        try {
            StringBuilder select = new StringBuilder(Selects.SELECT_INCIDENTSREPORT);
            if (showFilterCB.isSelected()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String sd1 = dateFormat.format((Date) startDateSP.getValue());
                String sd2 = dateFormat.format((Date) endDateSP.getValue());
                String whereCond = "where incidentdate between '"
                        + sd1 + "' and '"
                        + sd2 + "'";
                int p = select.indexOf("order by");
                select.replace(p, p + 8, whereCond + " order by");
            }
            Vector[] info = exchanger.getTableBody(select.toString());
            Vector tabBody = info[1];
            for (int i = 0; i < tabBody.size(); i++) {
                Vector ceils = (Vector) tabBody.get(i);
                curClockNo = (String) ceils.get(1);
                body.append("<tr>");
                for (int c = 0; c < ceils.size(); c++) {
                    String ceil = (String) ceils.get(c);
                    body.append("<td ")
                            //                            .append(c == 3 ? "align=\"right\" " : "")
                            .append("style=\"font-size: ").append(zoomer.getValue()).append("%;")
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

package com.xlend.gui.reports;

import com.xlend.gui.XlendWorks;
import static com.xlend.gui.reports.GeneralReportPanel.exchanger;
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
public class CashTurnoverReport extends GeneralReportPanel {

    private final SelectedDateSpinner fromDateSP;
    private final SelectedDateSpinner toDateSP;
    private final JButton filterBtn;

    public CashTurnoverReport(IMessageSender exchanger) {
        super(exchanger);
        upperPane.add(new JLabel("  From:"));
        upperPane.add(fromDateSP = new SelectedDateSpinner());
        upperPane.add(new JLabel(" to:"));
        upperPane.add(toDateSP = new SelectedDateSpinner());
        upperPane.add(filterBtn = new JButton(new AbstractAction("show") {//null, new ImageIcon(Util.loadImage("filter-icon.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                html = null;
                updateReport();
            }
        }));
        fromDateSP.setEditor(new JSpinner.DateEditor(fromDateSP, "dd/MM/yyyy HH:mm"));
        Util.addFocusSelectAllAction(fromDateSP);
        toDateSP.setEditor(new JSpinner.DateEditor(toDateSP, "dd/MM/yyyy HH:mm"));
        Util.addFocusSelectAllAction(toDateSP);

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.YEAR, 1990);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        fromDateSP.setValue(firstDayOfMonth);
    }

    @Override
    protected JEditorPane createEditorPanel() {
        if (html == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dt1 = (Date) fromDateSP.getValue();
            Date dt2 = (Date) toDateSP.getValue();
            html = new StringBuffer("<html>"
                    + "<table border=\"0\">"
                    + "<tr><table>"
                    + "<tr>"
                    + "<td rowspan=\"3\" style=\"font-size: " + (prevZoomerValue - 10) + "%; font-family: sans-serif\" ><img margin=20 src='file:./images/XlendCost.jpg'/><br>" + Calendar.getInstance().getTime().toString() + "</td>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Cash Turnover</th>"
                    + "</tr>"
                    + "<tr><table><tr><th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">From:</th>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif; color:blue\">" + dateFormat.format(dt1) + "</th>"
                    + "<th> to </th>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif; color:blue\">" + dateFormat.format(dt2) + "</th>"
                    + "</tr></table>"
                    + "</tr></table>"
                    + getReportBody(dt1, dt2)
                    + "</table></html>");
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        editorPanel.setEditable(false);
        return editorPanel;
    }

    private String getReportBody(Date dt1, Date dt2) {
        StringBuilder sb = new StringBuilder("<tr><table>");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            String select;
            Vector[] data = exchanger.getTableBody(select =
                    "select xcashdrawn_id as id,'Cash drawn' as \"Operation\", '' as \"Mode\", to_char(cur_date,'YYYY-MM-DD HH24:MI') as \"Date\", concat('R ',cash_drawn+add_monies) as \"Amount\", "
                    + " concat('R ',balance) as \"Balance\" "
                    + "  from xcashdrawn where cur_date between '" + dateFormat.format(dt1) + "' and '" + dateFormat.format(dt2) + "' "
                    + "union "
                    + "select xpetty_id as id,'Petty in/out' as \"Operation\",if(is_loan,'LOAN',if(is_petty,'PETTY',if(is_allowance,'ALLOWANCE','unknown'))) as \"Mode\","
                    + "       to_char(issue_date,'YYYY-MM-DD HH24:MI') as \"Date\",concat('R ',amount-ifnull(change_amt,0)) as \"Amount\", concat('R ',balance) as \"Balance\" "
                    + "  from xpetty where issue_date between '" + dateFormat.format(dt1) + "' and '" + dateFormat.format(dt2) + "' "
                    + " order by date");
            Vector hdr = data[0];
            Vector arr = data[1];
            sb.append("<tr>");
            for (Object o : hdr) {
                sb.append("<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">")
                        .append(o.toString()).append("</th>");
            }
            sb.append("</tr>");
            for (Object l : arr) {
                Vector line = (Vector) l;
                sb.append("<tr>");
                int col = 0;
                for (Object ceil : line) {
                    sb.append("<td " + (ceil.toString().startsWith("R ") ? "align=\"right\"" : "") + " style=\"font-size: " + (int) (prevZoomerValue) + "%; font-family: sans-serif;"
                            + (col > 0 ? " color:blue" : "")
                            + "\">")
                            .append(ceil.toString()).append("</td>");
                    col++;
                }
                sb.append("</tr>");
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        sb.append("</table></tr>");
        return sb.toString();
    }
}

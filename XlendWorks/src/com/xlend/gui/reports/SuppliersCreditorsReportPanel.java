package com.xlend.gui.reports;

import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author nick
 */
public class SuppliersCreditorsReportPanel extends GeneralReportPanel {

    private double total, total30, total60, total90, total120, total0;
    private JEditorPane editorPanel;

    public SuppliersCreditorsReportPanel(IMessageSender exchanger) {
        super(exchanger);
//        updateReport();
    }

    @Override
    public void updateReport() {
        removeAll();
        editorPanel = null;
        JEditorPane p;
        JScrollPane sp = new JScrollPane(p = createEditorPanel());
        add(sp, BorderLayout.CENTER);
        p.setCaretPosition(0);
    }

    public JEditorPane getEditorPanel() {
        return editorPanel;
    }

    private JEditorPane createEditorPanel() {
        StringBuffer html = new StringBuffer("<html>"
                + "<table>"
                + "<tr>"
                + "<td><img margin=20 src='file:./images/XlendCost.jpg'/></td>"
                + "<td vallign=\"center\"><H1 allign=\"left\">Supplier and Creditor Report</H1><BR>"
                + "<H2 allign=\"left\">Outstanding Ammounts</H2>"
                + Calendar.getInstance().getTime().toString() + "</td>"
                + "</tr>"
                + "</table>"
                + "<table frame=\"abowe\" width=\"800\"><tr bgcolor=\"#dedede\"><th align=\"left\" style=\"font-size: 90%; font-family: sans-serif\">Supplier Name</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: 90%; font-family: sans-serif\">30 Days</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: 90%; font-family: sans-serif\">60 Days</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: 90%; font-family: sans-serif\">90 Days</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: 90%; font-family: sans-serif\">120 Days</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: 90%; font-family: sans-serif\">&gt 120</th>"
                + "<th width=\"10%\" align=\"right\" style=\"font-size: 90%; font-family: sans-serif\">Total</th>"
                + getSupplierInfoHTML()
                + "</tr>"
                + "<tr bgcolor=\"#dedede\"><th align=\"left\" style=\"font-size: 90%; font-family: sans-serif\">Total:</th>"
                + "<th align=\"right\" " + getColor(total30) + ">R " + stdFormat(total30) + "</th>"
                + "<th align=\"right\" " + getColor(total60) + ">R " + stdFormat(total60) + "</th>"
                + "<th align=\"right\" " + getColor(total90) + ">R " + stdFormat(total90) + "</th>"
                + "<th align=\"right\" " + getColor(total120) + ">R " + stdFormat(total120) + "</th>"
                + "<th align=\"right\" " + getColor(total0) + ">R " + stdFormat(total0) + "</th>"
                + "<th align=\"right\" " + getColor(total) + ">R " + stdFormat(total) + "</th></tr>"
                + "</table>"
                + "</html>");

        editorPanel = new JEditorPane("text/html", html.toString());
//        editorPanel.setFont(editorPanel.getFont().deriveFont(1, (float) 8.0));
        return editorPanel;
    }

    private String getColor(double sum) {//style=\"font-size: 160%; font-family: sans-serif\"
        return (sum>0.0?"color=\"#ff0000\"":sum<0.0?"color=\"#0000ff\"":"")+"style=\"font-size: 90%; font-family: arial\"";
    }
    
    private String getSupplierInfoHTML() {
        StringBuffer body = new StringBuffer("");
        try {
            total30 = total60 = total90 = total120 = total0 = total = 0.0;
            String line = "";
            DbObject[] recs = exchanger.getDbObjects(Xsupplier.class, null, "companyname");
            for (DbObject rec : recs) {
                Xsupplier sup = (Xsupplier) rec;
                int supplier_id = sup.getXsupplierId();
                if (XlendWorks.calcOutstandingAmtSum(exchanger, sup.getXsupplierId()) != 0) {
                    line = "<tr><td style=\"font-size: 90%; font-family: sans-serif\">" + sup.getCompanyname() + "</td>"
                            + get30OutstandingAmount(supplier_id)
                            + get60OutstandingAmount(supplier_id)
                            + get90OutstandingAmount(supplier_id)
                            + get120OutstandingAmount(supplier_id)
                            + getMore120OutstandingAmount(supplier_id)
                            + getTotalOutstandingAmount(supplier_id)
                            + "</tr>";
                    body.append(line);
                }
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return body.toString();
    }

    private String stdFormat(double sum) {
        return String.format("%.02f",sum);
    }
    
    private String showCeil(double sum) {
        return "<td align=\"right\" " + getColor(sum) + "/>R " + stdFormat(sum) + "</td>";
    }

    private String get30OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 30, 0);
        total30 += sum;
        return showCeil(sum);
    }

    private String get60OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 60, 30);
        total60 += sum;
        return showCeil(sum);
    }

    private String get90OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 90, 60);
        total90 += sum;
        return showCeil(sum);
    }

    private String get120OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 120, 90);
        total120 += sum;
        return showCeil(sum);
    }

    private String getMore120OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 0, 120);
        total0 += sum;
        return showCeil(sum);
    }

    //calcOutstandingAmtSum30
    private String getTotalOutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSum(exchanger, xsupplierId);
        total += sum;
        return showCeil(sum);
    }
}

package com.xlend.gui.reports;

import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

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
                + "<H2 allign=\"left\">Outstanding Ammounts</H2></td><td align=\"right\">"+Calendar.getInstance().getTime().toString()+"</td>"
                + "</tr>"
                + "</table>"
                + "<table frame=\"abowe\"><tr bgcolor=\"#dedede\"><th>Supplier Name</th>"
                + "<th>30 Days</th>"
                + "<th>60 Days</th>"
                + "<th>90 Days</th>"
                + "<th>120 Days</th>"
                + "<th>&gt 120</th>"
                + "<th width=>Total</th>"
                + getSupplierInfoHTML()
                + "</tr>"
                + "<tr bgcolor=\"#dedede\"><th>Total:"
                + "</th>"
                + "<th " + (total30 > 0.0 ? "color=\"#ff0000\"" : "") + ">R " + total30 + "</th>"
                + "<th " + (total60 > 0.0 ? "color=\"#ff0000\"" : "") + ">R " + total60 + "</th>"
                + "<th " + (total90 > 0.0 ? "color=\"#ff0000\"" : "") + ">R " + total90 + "</th>"
                + "<th " + (total120 > 0.0 ? "color=\"#ff0000\"" : "") + ">R " + total120 + "</th>"
                + "<th " + (total0 > 0.0 ? "color=\"#ff0000\"" : "") + ">R " + total0 + "</th>"
                + "<th " + (total > 0.0 ? "color=\"#ff0000\"" : "") + ">R " + total + "</th></tr>"
                + "</table>"
                + "</html>");

        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
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
//                if (XlendWorks.calcOutstandingAmtSum(exchanger, sup.getXsupplierId()) > 0) {
                    line = "<tr><td>" + sup.getCompanyname() + "</td>"
                            + get30OutstandingAmount(supplier_id)
                            + get60OutstandingAmount(supplier_id)
                            + get90OutstandingAmount(supplier_id)
                            + get120OutstandingAmount(supplier_id)
                            + getMore120OutstandingAmount(supplier_id)
                            + getTotalOutstandingAmount(supplier_id)
                            + "</tr>";
                    body.append(line);
//                }
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return body.toString();
    }

    private String get30OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 30, 0);
        total30 += sum;
        return "<td align=\"right\" " + (sum > 0.0 ? " color=\"#ff0000\" " : "") + "/>R " + sum + "</td>";
    }

    private String get60OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 60, 30);
        total60 += sum;
        return "<td align=\"right\" " + (sum > 0.0 ? " color=\"#ff0000\" " : "") + "/>R " + sum + "</td>";
    }

    private String get90OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 90, 60);
        total90 += sum;
        return "<td align=\"right\" " + (sum > 0.0 ? " color=\"#ff0000\" " : "") + "/>R " + sum + "</td>";
    }

    private String get120OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 120, 90);
        total120 += sum;
        return "<td align=\"right\" " + (sum > 0.0 ? " color=\"#ff0000\" " : "") + "/>R " + sum + "</td>";
    }
    private String getMore120OutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSumBetweenDays(exchanger, xsupplierId, 0, 120);
        total0 += sum;
        return "<td align=\"right\" " + (sum > 0.0 ? " color=\"#ff0000\" " : "") + "/>R " + sum + "</td>";
    }

    //calcOutstandingAmtSum30
    private String getTotalOutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSum(exchanger, xsupplierId);
        total += sum;
        return "<td align=\"right\" " + (sum > 0.0 ? " color=\"#ff0000\" " : "") + "/>R " + sum + "</td>";
    }
}

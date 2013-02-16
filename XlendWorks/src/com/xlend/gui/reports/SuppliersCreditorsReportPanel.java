package com.xlend.gui.reports;

import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.swing.JEditorPane;

/**
 *
 * @author nick
 */
public class SuppliersCreditorsReportPanel extends GeneralReportPanel {

    private double total, total30, total60, total90, total120, total0;
    private int prevZoomerValue = 0;

    public SuppliersCreditorsReportPanel(IMessageSender exchanger) {
        super(exchanger);
//        updateReport();
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
                    + "<th style=\"font-size: " + (int)(prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Supplier and Creditor Report</th>"
                    + "</tr>"
                    + "<tr><th style=\"font-size: " + (int)(prevZoomerValue * 1.1) + "%; font-family: sans-serif\">Outstanding Ammounts</th>"
                    + "</tr><tr> </tr>"
                    + "</table></tr>"
                    + "<table frame=\"abowe\" ><tr bgcolor=\"#dedede\"><th align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Supplier Name</th>"
                    + "<th width=\"10%\" align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">30 Days</th>"
                    + "<th width=\"10%\" align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">60 Days</th>"
                    + "<th width=\"10%\" align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">90 Days</th>"
                    + "<th width=\"10%\" align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">120 Days</th>"
                    + "<th width=\"10%\" align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">&gt 120</th>"
                    + "<th width=\"10%\" align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Total</th>"
                    + getSupplierInfoHTML()
                    + "</tr>"
                    + "<tr bgcolor=\"#dedede\"><th align=\"left\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\">Total:</th>"
                    + "<th align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" " + getColor(total30) + ">R " + stdFormat(total30) + "</th>"
                    + "<th align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" " + getColor(total60) + ">R " + stdFormat(total60) + "</th>"
                    + "<th align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" " + getColor(total90) + ">R " + stdFormat(total90) + "</th>"
                    + "<th align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" " + getColor(total120) + ">R " + stdFormat(total120) + "</th>"
                    + "<th align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" " + getColor(total0) + ">R " + stdFormat(total0) + "</th>"
                    + "<th align=\"right\" style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" " + getColor(total) + ">R " + stdFormat(total) + "</th></tr>"
                    + "</table></tr>"
                    + "</table>"
                    + "</html>");
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }

    private String getColor(double sum) {//style=\"font-size: 160%; font-family: sans-serif\"
        return (sum > 0.0 ? "color=\"#ff0000\"" : sum < 0.0 ? "color=\"#0000ff\"" : "") + "style=\"font-size: "
                + zoomer.getValue() + "%; font-family: arial\"";
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
                    line = "<tr><td style=\"font-size: " + zoomer.getValue() + "%; font-family: sans-serif\">" + sup.getCompanyname() + "</td>"
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
        return String.format("%.02f", sum);
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

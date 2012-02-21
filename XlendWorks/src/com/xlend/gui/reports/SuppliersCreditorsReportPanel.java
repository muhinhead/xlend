package com.xlend.gui.reports;

import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.rmi.RemoteException;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 *
 * @author nick
 */
public class SuppliersCreditorsReportPanel extends GeneralReportPanel {
    private double total;
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
                +"<table>"
                + "<tr>"
                + "<td><img margin=20 src='file:./images/XlendCost.jpg'/></td>"
                + "<td vallign=\"center\"><H1 allign=\"left\">Supplier and Creditor Report</H1><BR>"
                + "<H2 allign=\"left\"><u>Outstanding Amnounts</u></H2></td>"
                + "</tr>"
                + "</table>"
                + "<table frame=\"abowe\"><tr bgcolor=\"#dedede\"><th>Supplier Name</th>"
                + "<th width=\"10%\">30 Days</th>"
                + "<th width=\"10%\">60 Days</th>"
                + "<th width=\"10%\">90 Days</th>"
                + "<th width=\"10%\">120 Days+</th>"
                + "<th width=\"10%\">Total</th>"
                + getSupplierInfoHTML()
                + "</tr>"
                + "<tr bgcolor=\"#dedede\"><th>Total:</th><th>R 0</th><th>R 0</th><th>R 0</th><th>R 0</th>"
                + "<th "+(total>0.0?"color=\"#ff0000\"":"")+">R "+total+"</th></tr>"
                + "</table>"
                +"</html>");

        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }

    private String getSupplierInfoHTML() {
        StringBuffer body = new StringBuffer("");
        try {
            total = 0.0;
            String line = "";
            DbObject[] recs = exchanger.getDbObjects(Xsupplier.class, null, "companyname");
            for (DbObject rec : recs) {
                Xsupplier sup = (Xsupplier) rec;
                line = "<tr><td>"+sup.getCompanyname()+"</td>"
                        +"<td align=\"right\"></td>"
                        +"<td align=\"right\"></td>"
                        +"<td align=\"right\"></td>"
                        +"<td align=\"right\"></td>"
                        +getTotalOutstandingAmount(sup.getXsupplierId())
                        + "</tr>";
                body.append(line);
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return body.toString();
    }

    private String getTotalOutstandingAmount(Integer xsupplierId) {
        double sum = XlendWorks.calcOutstandingAmtSum(exchanger, xsupplierId);
        total+=sum;
        return "<td align=\"right\" "+(sum>0.0?" color=\"#ff0000\" ":"")+"/>R "+sum+"</td>";
    }
}

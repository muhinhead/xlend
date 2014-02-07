package com.xlend.gui.work;

import com.xlend.gui.XlendWorks;
import com.xlend.gui.reports.GeneralReportPanel;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xmachine;
import com.xlend.orm.Xmachineorder;
import com.xlend.orm.Xmachineorderitm;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;

/**
 *
 * @author Nick Mukhin
 */
public class MachineOrderReport extends GeneralReportPanel {

    public MachineOrderReport(IMessageSender exchanger, Integer xmachineorderID) {
        super(exchanger, xmachineorderID, 90);
        updateReport();
    }

    @Override
    protected JEditorPane createEditorPanel() {
        if (html == null) {
            html = new StringBuffer();
            try {
                Xmachineorder mo = (Xmachineorder) exchanger.loadDbObjectOnID(Xmachineorder.class, intParameter);
                prevZoomerValue = zoomer.getValue();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                html.append("<html>")
                        .append("<table border=\"0\">")
                        .append("<tr><table>")
                        .append("<tr><table>")
                        .append("<tr>")
                        .append("<td rowspan=\"3\" style=\"font-size: " + (int) (prevZoomerValue - 10) + "%; font-family: sans-serif\" ><img margin=20 src='file:./images/XlendCost.jpg'/><br>" + Calendar.getInstance().getTime().toString() + "</td>")
                        .append("<th style=\"font-size: " + (int) (prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">")
                        .append("Internal Machine Order Placement</th>")
                        .append("</tr><tr/><tr/><tr/>")
                        .append(
                        "<tr><td colspan=\"5\"><table>"
                        + "<tr><td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"right\">ID</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">" + mo.getXmachineorderId() + "</th></tr>"
                        + "<tr><td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"right\">Person Requesting Plant:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + getEmployeeOnID(mo.getXemployeeId())
                        + "</th>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\"  align=\"right\">Site:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + getSiteOnID(mo.getXsiteId())
                        + "</th>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\"  align=\"right\">Site Address:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + mo.getSiteAddress()
                        + "</th>"
                        + "</tr>"
                        + "<tr><td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"right\">Date of Issue:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + dateFormat.format(mo.getIssueDate())
                        + "</th>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\"  align=\"right\">Client:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + getClientOnID(mo.getXclientId())
                        + "</th>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\"  align=\"right\">Distance to Site:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + mo.getDistance2site()
                        + "</th>"
                        + "</tr>"
                        + "<tr><td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"right\">Date Required:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + dateFormat.format(mo.getRequireDate())
                        + "</th>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\"  align=\"right\">Order No:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + getOrderOnID(mo.getXorderId())
                        + "</th>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\"  align=\"right\">Foreman Requesting Plant:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + mo.getForemanReqPlant()
                        + "</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" colspan=\"5\" align=\"right\">Foreman Contract Details:</td>"
                        + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" align=\"left\">"
                        + mo.getForemanContact()
                        + "</th>"
                        + "</tr>"
                        + "<tr></tr><tr>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" colspan=\"2\" align=\"center\">Plant to Site</td>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" colspan=\"2\" align=\"center\">Type of Plant</td>"
                        + "<td style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" colspan=\"2\" align=\"center\">Operator</td>"
                        + getItemList()
                        + "</tr>"
                        + "</table></td></tr>"
                        //                    + "<tr><th style=\"font-size: " + (int) (prevZoomerValue * 1.1) 
                        //                    + "%; font-family: sans-serif\">--------</th>"
                        //                    + "</tr>"
                        + "</table></tr>"
                        + "</table>"
                        + "</html>");
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        return editorPanel;
    }

    private String getEmployeeOnID(Integer xemployeeID) throws RemoteException {
        Xemployee emp = (Xemployee) exchanger.loadDbObjectOnID(Xemployee.class, xemployeeID);
        return emp == null ? "-" : emp.getClockNum() + " " + emp.getFirstName() + " " + emp.getSurName();
    }

    private String getSiteOnID(Integer xsiteID) throws RemoteException {
        Xsite site = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, xsiteID);
        return site == null ? "-" : site.getName();
    }

    private String getClientOnID(Integer xclientID) throws RemoteException {
        Xclient client = (Xclient) exchanger.loadDbObjectOnID(Xclient.class, xclientID);
        return client == null ? "-" : client.getCompanyname();
    }

    private String getOrderOnID(Integer xorderID) throws RemoteException {
        Xorder order = (Xorder) exchanger.loadDbObjectOnID(Xorder.class, xorderID);
        return order == null ? "-" : order.getOrdernumber();
    }
    
    private String getMachineOnID(Integer xmachineID) throws RemoteException {
        Xmachine machine = (Xmachine)exchanger.loadDbObjectOnID(Xmachine.class, xmachineID);
        return machine.getTmvnr();
    }

    private String getItemList() throws RemoteException {
        DbObject[] itms = exchanger.getDbObjects(Xmachineorderitm.class, "xmachineorder_id="+intParameter, null);
        StringBuilder sb = new StringBuilder("");
        for (DbObject itm : itms) {
            Xmachineorderitm mi = (Xmachineorderitm) itm;
            sb.append("<tr>"
                    + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" colspan=\"2\">"
                    + getMachineOnID(mi.getXmachineId())
                    + "</th>"
                    + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" colspan=\"2\">"
                    + XlendWorks.getMachineType1(mi.getXmachineId())
                    + "</th>"
                    + "<th style=\"font-size: " + prevZoomerValue + "%; font-family: sans-serif\" colspan=\"2\">"
                    + getEmployeeOnID(mi.getXemployeeId())
                    + "</th>"
                    + "</tr>");
        }
        return sb.toString();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.reports;

import com.xlend.gui.XlendWorks;
import com.xlend.orm.Cbitems;
import com.xlend.orm.Xpettyitem;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nick Mukhin
 */
public class PettyReport extends GeneralReportPanel {

    private final JComboBox sitesCB;
    private final SelectedDateSpinner fromDateSP;
    private final SelectedDateSpinner toDateSP;
    private final JButton filterBtn;

    public PettyReport(IMessageSender exchanger) {
        super(exchanger);
        DefaultComboBoxModel sitesCbMoel = new DefaultComboBoxModel(XlendWorks.loadSites("xsite_id>0"));
        upperPane.add(new JLabel("  Site:"));
        upperPane.add(sitesCB = new JComboBox(sitesCbMoel));
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
        sitesCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                html = null;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        updateReport();
                    }
                });
            }
        });

        fromDateSP.setEditor(new JSpinner.DateEditor(fromDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(fromDateSP);
        toDateSP.setEditor(new JSpinner.DateEditor(toDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(toDateSP);
//        setEnabledDateFilter(false);

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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = (Date) fromDateSP.getValue();
            Date dt2 = (Date) toDateSP.getValue();
            ComboItem siteItm = (ComboItem) sitesCB.getSelectedItem();
            prevZoomerValue = zoomer.getValue();
            html = new StringBuffer("<html>"
                    + "<table border=\"0\">"
                    + "<tr><table>"
                    + "<tr>"
                    + "<td rowspan=\"3\" style=\"font-size: " + (prevZoomerValue - 10) + "%; font-family: sans-serif\" ><img margin=20 src='file:./images/XlendCost.jpg'/><br>" + Calendar.getInstance().getTime().toString() + "</td>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.2) + "%; font-family: sans-serif\" allign=\"left\">Petty Report</th>"
                    + "</tr>"
                    + "<tr><table><tr><th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">From:</th>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif; color:blue\">" + dateFormat.format(dt1) + "</th>"
                    + "<th> to </th>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif; color:blue\">" + dateFormat.format(dt2) + "</th>"
                    + "</tr></table></tr>"
                    + "<tr><table><tr><th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">Site:</th>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif; color:blue\">" + sitesCB.getSelectedItem().toString() + "</th>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">Total Petty Allocated to site:</th>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif; color:blue\">" + getTotalPettyForSite((ComboItem) sitesCB.getSelectedItem(), dt1, dt2) + "</th></tr></table>"
                    + "</tr><tr>"
                    + "<th></th><table width=\"100%\">"
                    + "<tr>"
                    + machinesList(siteItm.getId(), dt1, dt2)
                    + "</tr>"
                    + "</table>"
                    + "<tr>"
                    + "<th></th>"
                    + "<table><tr>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif\">Unallocated Total:</th>"
                    + "<th style=\"font-size: " + (int) (prevZoomerValue * 1.1) + "%; font-family: sans-serif; color:blue\">" + getUnallocatedTotalPettyForSite((ComboItem) sitesCB.getSelectedItem(), dt1, dt2) + "</th></tr></table>"
                    + "</tr>"
                    + "<tr>"
                    + "<th></th><table width=\"100%\">"
                    + categoriesList(siteItm.getId(), dt1, dt2)
                    + "</tr>"
                    + "</table>"
                    + "</tr></table>"
                    + "</tr>"
                    + "</table>"
                    + "</html>");
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        editorPanel.setEditable(false);
        return editorPanel;
    }

    private String machinesList(int siteID, Date dt1, Date dt2) {
        StringBuilder sb = new StringBuilder("");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String select;
            Vector[] data = exchanger.getTableBody(
                    select = "Select ifnull(concat(xmachine.classify,xmachine.tmvnr),'-') ,sum(xpettyitem.amount) "
                    + "from xpetty,xpettyitem left outer join xmachine on xmachine.xmachine_id = xpettyitem.xmachine_id "
                    + "where xpettyitem.xpetty_id = xpetty.xpetty_id "
                    + " and xpettyitem.xsite_id=" + siteID
                    + " and xpetty.receipt_date between '" + dateFormat.format(dt1) + "' and '" + dateFormat.format(dt2) + "'"
                    + " group by xpettyitem.xmachine_id");
            Vector arr = data[1];
            double sum1 = 0.0;
            double sum2 = 0.0;
            int tms = 0;
            int n = 0;
            for (Object o : arr) {
                Vector line = (Vector) o;
                if (line.get(0).toString().startsWith("M") || line.get(0).toString().startsWith("T")) {
                    sum1 += Double.parseDouble(line.get(1).toString());
                    tms++;
                } else {
                    sum2 += Double.parseDouble(line.get(1).toString());
                }
            }
            long diff = (long) ((sum1 + sum2) * 100);
            for (Object o : arr) {
                n++;
                Vector line = (Vector) o;
                if (line.get(0).toString().startsWith("M") || line.get(0).toString().startsWith("T")) {
                    sb.append("<tr " + (n == arr.size() ? "style=\"border-bottom-style:solid; border-bottom-width:1px;\"" : "") + ">");
                    for (int i = 0; i < line.size(); i++) {
                        String c = ((String) line.get(i)).replace(',', '.');
                        sb.append("<th align=\"" + (i == 1 ? "right" : "left") + "\" style=\"font-size: " + (int) (prevZoomerValue)
                                + "%; font-family: sans-serif\">");
                        sb.append(i == 1 ? String.format("R %.2f", Double.parseDouble(c)) : c);
                        sb.append("</th>");
                        if (i == 1) {
                            sb.append("<th align=\"right\" style=\"font-size: " + (int) (prevZoomerValue)
                                    + "%; font-family: sans-serif\">");
                            diff -= Math.round(100 * (Double.parseDouble(c) + (sum2 / tms)));
                            sb.append(i == 1 ? String.format("R %.2f",
                                    Math.round(100 * (Double.parseDouble(c)
                                    + (sum2 / tms)) + (n == arr.size() ? diff : 0)) / 100.0).replace(',', '.') : c);
                            sb.append("</th>");
                        }
                    }
                    sb.append("</tr>");
                }
            }

            sb.append("<tr style=\"border-bottom-style:solid; border-bottom-width:1px;\">");
            sb.append("<th ></th><th align=\"right\" style=\"font-size: "
                    + (int) (prevZoomerValue) + "%; font-family: sans-serif\">")
                    .append(String.format("R %.2f", sum1).replace(',', '.'))
                    .append("</th>");
            sb.append("<th align=\"right\" style=\"font-size: "
                    + (int) (prevZoomerValue) + "%; font-family: sans-serif\">")
                    .append(String.format("R %.2f", sum1 + sum2).replace(',', '.'))
                    .append("</th>");
            sb.append("</tr>");
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return sb.toString();
    }

    private String categoriesList(int siteID, Date dt1, Date dt2) {
        StringBuilder sb = new StringBuilder("");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String select;
            Vector[] data = exchanger.getTableBody(
                    select = "select i.xpettycategory_id,concat(category_name, '|', sum(i.amount)) "
                    + "  from xpetty p, xpettyitem i, xpettycategory c "
                    + " where i.xpetty_id = p.xpetty_id and c.xpettycategory_id=i.xpettycategory_id"
                    + "   and (xmachine_id is null or xmachine_id in (select xmachine_id from xmachine where not classify in ('T','M')))"
                    + " and i.xsite_id=" + siteID
                    + " and p.receipt_date between '" + dateFormat.format(dt1) + "' and '" + dateFormat.format(dt2) + "'"
                    + " group by i.xpettycategory_id");
            Vector arr = data[1];
            for (Object o : arr) {
                Vector line = (Vector) o;
                String[] itm = new String[2];//((String) line.get(1)).split("|");
                int p = ((String) line.get(1)).indexOf('|');
                itm[0] = ((String) line.get(1)).substring(0,p);
                itm[1] = String.format("R %.2f", Double.parseDouble(((String) line.get(1)).substring(p+1)));
                for (int i = 0; i < itm.length; i++) {
                    sb.append("<th align=\"" + (i == 1 ? "right" : "left") + "\" style=\"font-size: " + (int) (prevZoomerValue)
                            + "%; font-family: sans-serif\">");
                    sb.append(itm[i].replace(',', '.'));
                    sb.append("</th>");
                }
                sb.append("</tr>");
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return sb.toString();
    }

    private String getTotalPettyForSite(ComboItem itm, Date dt1, Date dt2) {
        String ans = "R 0.0";
        if (itm != null) {
            Double sum = XlendWorks.getTotalPettyForSite(itm.getId(), dt1, dt2);
            if (sum != null) {
                ans = String.format("R %.2f", sum.doubleValue());
            }
        }
        return ans.replace(',', '.');
    }

    private String getUnallocatedTotalPettyForSite(ComboItem itm, Date dt1, Date dt2) {
        String ans = "R 0.0";
        if (itm != null) {
            Double sum = XlendWorks.getUnallocatedTotalPettyForSite(itm.getId(), dt1, dt2);
            if (sum != null) {
                ans = String.format("R %.2f", sum.doubleValue());
            }
        }
        return ans.replace(',', '.');
    }
}

package com.xlend.gui;

import com.xlend.gui.fleet.*;
import com.xlend.remote.IMessageSender;
//import com.xlend.gui.HTMLpanel;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.rmi.RemoteException;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class FleetFrame extends GeneralFrame {

    /**
     * @param aSheetList the sheetList to set
     */
    public static void setSheetList(String[] aSheetList) {
        sheetList = aSheetList;
    }
    private GeneralGridPanel machinesPanel;
    private GeneralGridPanel trackPanel;
    private GeneralGridPanel lowbedsPanel;
    private GeneralGridPanel machineRentalRatesPanel;
    private GeneralGridPanel poolVehiclesPanel;
    private GeneralGridPanel companyVehiclesPanel;
    private GeneralGridPanel dieselCartsPanel;
    private GeneralGridPanel machineServicesPanel;
    private GeneralGridPanel batteriesPurchasePanel;
    private GeneralGridPanel batteriesIssuePanel;
    private JPanel dieselPricesHTMLpanel;
    private static String[] sheetList = new String[]{
        "Machine Files", "Truck Files", "Low-Beds", "Pool Vehicles",
        "Company Vehicles", "Machine Rental Rates", "Diesel Carts",
        "Service", "Batteries", "Diesel Price"
    };

    public FleetFrame(IMessageSender exch) {
        super("Fleet", exch);
    }

    @Override
    protected String[] getSheetList() {
        return sheetList;
    }

    public static String[] sheets() {
        return sheetList;
    }

    protected JTabbedPane getMainPanel() {
        MyJideTabbedPane fleetTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getMachinesPanel()...");
            fleetTab.addTab(getMachinesPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getTrackPanel()...");
            fleetTab.addTab(getTrackPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getLowBedsPanel()...");
            fleetTab.addTab(getLowBedsPanel(), sheets()[2]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[3])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getPoolVehiclesPanel()...");
            fleetTab.addTab(getPoolVehiclesPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[4])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getCompanyVehiclesPanel()...");
            fleetTab.addTab(getCompanyVehiclesPanel(), sheets()[4]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[5])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getMachineRentalRates()...");
            fleetTab.addTab(getMachineRentalRates(), sheets()[5]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[6])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getDieselCarts()...");
            fleetTab.addTab(getDieselCarts(), sheets()[6]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[7])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getMachineServices()...");
            fleetTab.addTab(getMachineServices(), sheets()[7]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[8])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getBatteries()...");
            fleetTab.addTab(getBatteries(), sheets()[8]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[9])) {
//            XlendWorks.log("@@@@ Before call FleetFrame.getDieselPrices()...");
            fleetTab.addTab(getDieselPrices(), sheets()[9]);
        }
        return fleetTab;
    }

    private JPanel getMachinesPanel() {
        if (machinesPanel == null) {
            try {
                registerGrid(machinesPanel = new MachineGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return machinesPanel;
    }

    private JPanel getTrackPanel() {
        if (trackPanel == null) {
            try {
                registerGrid(trackPanel = new TrackGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return trackPanel;
    }

    private JPanel getLowBedsPanel() {
        if (lowbedsPanel == null) {
            try {
                registerGrid(lowbedsPanel = new LowBedGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return lowbedsPanel;
    }

    private JPanel getMachineRentalRates() {
        if (machineRentalRatesPanel == null) {
            try {
                registerGrid(machineRentalRatesPanel = new MachineRentalRatesGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return machineRentalRatesPanel;
    }

    private JPanel getPoolVehiclesPanel() {
        if (poolVehiclesPanel == null) {
            try {
                registerGrid(poolVehiclesPanel = new PoolVehicleGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return poolVehiclesPanel;
    }

    private JPanel getCompanyVehiclesPanel() {
        if (companyVehiclesPanel == null) {
            try {
                registerGrid(companyVehiclesPanel = new CompanyVehicleGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return companyVehiclesPanel;
    }

    private JPanel getMachineServices() {
        if (machineServicesPanel == null) {
            try {
                registerGrid(machineServicesPanel = new ServiceGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return machineServicesPanel;
    }

    private JComponent getDieselCarts() {
        if (dieselCartsPanel == null) {
            try {
                registerGrid(dieselCartsPanel = new DieselCartsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return dieselCartsPanel;
    }

    private JComponent getBatteries() {
        //TODO: batteries panel
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.setDividerLocation(250);
        try {
            sp.add(batteriesPurchasePanel = new BatteryPurchaseGrid(getExchanger()));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
            errMessageBox("Error:", ex.getMessage());
            sp.add(new JLabel(ex.getMessage(), SwingConstants.CENTER));
        }

        try {
            sp.add(batteriesIssuePanel = new BatteryIssuesGrid(getExchanger()));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
            errMessageBox("Error:", ex.getMessage());
            sp.add(new JLabel(ex.getMessage(), SwingConstants.CENTER));
        }
        return sp;
    }

    private JComponent getDieselPrices() {
        if (dieselPricesHTMLpanel == null) {
            dieselPricesHTMLpanel = new JPanel(new BorderLayout());
            if (System.getProperty("os.name").equals("Windows XP")) {
                HTMLapplet browser = new HTMLapplet("http://www.aa.co.za/on-the-road/calculator-tools/fuel-pricing.html");
//            HTMLapplet.setUrl(//"http://ec2-23-22-145-131.compute-1.amazonaws.com:8080/XlendWebWorks");
//                "http://www.aa.co.za/on-the-road/calculator-tools/fuel-pricing.html");
                browser.init();
                dieselPricesHTMLpanel.add(browser.getContentPane());
                browser.start();
            } else {
                String html = loadHtmlFromURL("http://www.aa.co.za/on-the-road/calculator-tools/fuel-pricing.html");
                JEditorPane htmlPanel = new JEditorPane("text/html", html);
                dieselPricesHTMLpanel.add(new JScrollPane(htmlPanel));
            }
        }
        return dieselPricesHTMLpanel;
    }

    private String loadHtmlFromURL(String urlString) {
        StringBuffer buf = new StringBuffer("<html>");
        LineNumberReader r = null;
        try {
            boolean include = false;
            URL url = new URL(urlString);
            r = new LineNumberReader(new InputStreamReader(url.openStream()));
            String s;
            while ((s = r.readLine()) != null) {
                if (include) {
                    buf.append("\n" + clearClassRef(s));
                    if (s.indexOf("</TABLE>") > -1) {
                        include = false;
                    }
                } else if (s.trim().startsWith("<a name=\"petrol\">") || s.trim().startsWith("<a name=\"diesel\">")) {
                    include = true;
                }
            }
        } catch (Exception ex) {
            buf.append(ex.getStackTrace().toString());
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (IOException ex) {
                }
            }
        }
        buf.append("\n</html>");
        return convertPrice2Rands(buf.toString());
    }

    private String clearClassRef(String s) {
        int p = s.indexOf("class=");
        if (p > 0) {
            if (s.indexOf("colAlternating1") > 0 || s.indexOf("colAlternating2") > 0 || s.indexOf("graphSubHeader") > 0) {
                s = s.replaceAll("TD", "TH").replaceAll("td", "th");
            }
            int pp = s.indexOf(">", p);
            int sp = s.indexOf(" ", p);
            pp = sp < pp && sp >= 0 ? sp : pp;
            if (pp > 0) {
                s = s.substring(0, p) + s.substring(pp);
            }
        }
        return s.replace("border=0", "border=1")
                .replace("<TBODY>", "").replace("</TBODY>", "");
    }

    private String convertPrice2Rands(String s) {
        StringBuffer sb = new StringBuffer(s);
        char prev = ' ';
        for (int i = 0; i < sb.length() - 4; i++) {
            if ("0123456789".indexOf(sb.charAt(i)) > -1 && "0123456789".indexOf(prev) < 0
                    && "0123456789".indexOf(sb.charAt(i + 1)) > -1
                    && "0123456789".indexOf(sb.charAt(i + 2)) > -1
                    && "0123456789".indexOf(sb.charAt(i + 3)) > -1) {
                sb.replace(i, i + 4, "R" + new Double(sb.substring(i, i + 4)) / 100.0);
                i++;
            }
            prev = sb.charAt(i);
        }
        return sb.toString();
    }
}

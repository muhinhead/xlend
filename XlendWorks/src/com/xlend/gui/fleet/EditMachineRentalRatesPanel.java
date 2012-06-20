package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachrentalrate;
import com.xlend.orm.Xmachrentalrateitm;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditMachineRentalRatesPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner dateSP;
    private JSpinner dieselPriceSP;
    private JSpinner factorSP;
    private JSpinner[] litersHoursSPs;
    private JSpinner[] drySPs;
    private JSpinner[] realWetSPs;
    private JSpinner[] goodWetSPs;
    private JLabel[] dieselPriceLBLs;
    private JLabel[] factorLBLs;
    private JLabel[] compWetLBLs;
    private JLabel[] compDryLBLs;
    private JLabel[] realDryLBLs;
    private JLabel[] goodDryLBLs;
    private int[] cbitemIDs;
    private boolean isBchanged;

    public EditMachineRentalRatesPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] topLbls = new String[]{"Liters/Hour", "Diesel Price", "Dry",
            "Factor", "Competative Wet", "Real Wet", "Good Wet",
            "Competative Dry", "Real Dry", "Good Dry"};
        ComboItem[] ratedMachines = XlendWorks.loadRatedMachines(DashBoard.getExchanger());

        litersHoursSPs = new JSpinner[ratedMachines.length];
        drySPs = new JSpinner[ratedMachines.length];
        realWetSPs = new JSpinner[ratedMachines.length];
        goodWetSPs = new JSpinner[ratedMachines.length];
        dieselPriceLBLs = new JLabel[ratedMachines.length];
        factorLBLs = new JLabel[ratedMachines.length];
        compWetLBLs = new JLabel[ratedMachines.length];
        compDryLBLs = new JLabel[ratedMachines.length];
        realDryLBLs = new JLabel[ratedMachines.length];
        goodDryLBLs = new JLabel[ratedMachines.length];
        cbitemIDs = new int[ratedMachines.length];
        for (int i = 0; i < cbitemIDs.length; i++) {
            cbitemIDs[i] = ratedMachines[i].getId();
        }

        setLayout(new BorderLayout(5, 5));
        JPanel upperPanel = new JPanel(new BorderLayout(5, 5));

        JPanel upperLeftPanel = new JPanel(new GridLayout(ratedMachines.length + 3, 1));
        upperLeftPanel.add(getGridPanel(new JComponent[]{new JLabel("ID:", SwingConstants.RIGHT),
                    idField = new JTextField()}));
        upperLeftPanel.add(getGridPanel(new JComponent[]{new JLabel("Date of Revision:", SwingConstants.CENTER),
                    dateSP = new SelectedDateSpinner()}));
        upperLeftPanel.add(new JPanel());
        for (ComboItem rm : ratedMachines) {
            upperLeftPanel.add(new JLabel(rm.getValue(), SwingConstants.LEFT));
        }
        upperPanel.add(upperLeftPanel, BorderLayout.WEST);

        JPanel upperCenterPanel = new JPanel(new GridLayout(ratedMachines.length + 3, 10));

        for (String title : topLbls) {
            upperCenterPanel.add(new JLabel(title, SwingConstants.CENTER));
        }
        int lh = 0, dr = 0, rw = 0, gw = 0,
                dp = 0, fc = 0, cw = 0, cd = 0, rd = 0, gd = 0;
        for (int r = 0; r < ratedMachines.length + 2; r++) {
            for (int c = 0; c < 10; c++) {
                if (r == 0) {
                    if (c == 1) {
                        upperCenterPanel.add(dieselPriceSP = new SelectedNumberSpinner(0.0, 0.0, 10000.0, 0.01));
                    } else if (c == 3) {
                        upperCenterPanel.add(factorSP = new SelectedNumberSpinner(0.0, 0.0, 10000.0, 0.01));
                    } else {
                        upperCenterPanel.add(new JPanel());
                    }
                } else if (r >= 2) {
                    switch (c) {
                        case 0:
                            upperCenterPanel.add(litersHoursSPs[lh++] = new SelectedNumberSpinner(0.0, 0.0, 10000.0, 0.01));
                            break;
                        case 1:
                            upperCenterPanel.add(dieselPriceLBLs[dp] = new JLabel("", SwingConstants.RIGHT));
                            dieselPriceLBLs[dp++].setBorder(BorderFactory.createEtchedBorder());
                            break;
                        case 2:
                            upperCenterPanel.add(drySPs[dr++] = new SelectedNumberSpinner(0.0, 0.0, 10000.0, 0.01));
                            break;
                        case 3:
                            upperCenterPanel.add(factorLBLs[fc] = new JLabel("", SwingConstants.RIGHT));
                            factorLBLs[fc++].setBorder(BorderFactory.createEtchedBorder());
                            break;
                        case 4:
                            upperCenterPanel.add(compWetLBLs[cw] = new JLabel("", SwingConstants.RIGHT));
                            compWetLBLs[cw++].setBorder(BorderFactory.createEtchedBorder());
                            break;
                        case 5:
                            upperCenterPanel.add(realWetSPs[rw++] = new SelectedNumberSpinner(0.0, 0.0, 10000.0, 0.01));
                            break;
                        case 6:
                            upperCenterPanel.add(goodWetSPs[gw++] = new SelectedNumberSpinner(0.0, 0.0, 10000.0, 0.01));
                            break;
                        case 7:
                            upperCenterPanel.add(compDryLBLs[cd] = new JLabel("", SwingConstants.RIGHT));
                            compDryLBLs[cd++].setBorder(BorderFactory.createEtchedBorder());
                            break;
                        case 8:
                            upperCenterPanel.add(realDryLBLs[rd] = new JLabel("", SwingConstants.RIGHT));
                            realDryLBLs[rd++].setBorder(BorderFactory.createEtchedBorder());
                            break;
                        case 9:
                            upperCenterPanel.add(goodDryLBLs[gd] = new JLabel("", SwingConstants.RIGHT));
                            goodDryLBLs[gd++].setBorder(BorderFactory.createEtchedBorder());
                            break;
                        default:
                            upperCenterPanel.add(new JPanel());
                    }
                } else {
                    upperCenterPanel.add(new JPanel());
                }
            }
        }

        addListeners();

        upperPanel.add(upperCenterPanel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(upperPanel, BorderLayout.NORTH);
        add(new JScrollPane(mainPanel));

        idField.setEnabled(false);
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
    }

    @Override
    public void loadData() {
        //
        Xmachrentalrate xmr = (Xmachrentalrate) getDbObject();
        if (xmr != null) {
            isBchanged = true;
            idField.setText(xmr.getXmachrentalrateId().toString());
            if (xmr.getActualDate() != null) {
                dateSP.setValue(new java.util.Date(xmr.getActualDate().getTime()));
            }
            dieselPriceSP.setValue(xmr.getDieselPrice());
            factorSP.setValue(xmr.getFactor());
            for (int i = 0; i < cbitemIDs.length; i++) {
                try {
                    DbObject[] obs = DashBoard.getExchanger().getDbObjects(Xmachrentalrateitm.class,
                            "xmachrentalrate_id=" + xmr.getXmachrentalrateId() + " and cbitem_id=" + cbitemIDs[i], null);
                    if (obs.length > 0) {
                        Xmachrentalrateitm itm = (Xmachrentalrateitm) obs[0];
                        litersHoursSPs[i].setValue(itm.getLitresHour());
                        drySPs[i].setValue(itm.getDry());
                        realWetSPs[i].setValue(itm.getRealWet());
                        goodWetSPs[i].setValue(itm.getGoodWet());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                }
            }
            isBchanged = false;
        }
    }

    @Override
    public boolean save() throws Exception {
        Xmachrentalrate xmr = (Xmachrentalrate) getDbObject();
        boolean isNew = false;
        if (xmr == null) {
            xmr = new Xmachrentalrate(null);
            xmr.setXmachrentalrateId(0);
        }
        isNew = xmr.getXmachrentalrateId() == 0;
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            xmr.setActualDate(new java.sql.Date(dt.getTime()));
        }
        xmr.setDieselPrice((Double) dieselPriceSP.getValue());
        xmr.setFactor((Double) factorSP.getValue());
        boolean ok = saveDbRecord(xmr, isNew);
        DbObject[] obs;
        StringBuffer itemIds = new StringBuffer();
        if (ok) {
            xmr = (Xmachrentalrate) getDbObject();
            for (int i = 0; i < cbitemIDs.length; i++) {
                if (i > 0) {
                    itemIds.append(",");
                }
                itemIds.append(cbitemIDs[i]);
                Xmachrentalrateitm itm;
                obs = DashBoard.getExchanger().getDbObjects(Xmachrentalrateitm.class,
                        "xmachrentalrate_id=" + xmr.getXmachrentalrateId() + " and cbitem_id=" + cbitemIDs[i], null);
                if (obs.length == 0) {
                    isNew = true;
                    itm = new Xmachrentalrateitm(null);
                    itm.setXmachrentalrateitmId(0);
                    itm.setCbitemId(cbitemIDs[i]);
                    itm.setXmachrentalrateId(xmr.getXmachrentalrateId());
                } else {
                    isNew = false;
                    itm = (Xmachrentalrateitm) obs[0];
                }
                itm.setLitresHour((Double) litersHoursSPs[i].getValue());
                itm.setDry((Double) drySPs[i].getValue());
                itm.setRealWet((Double) realWetSPs[i].getValue());
                itm.setGoodWet((Double) goodWetSPs[i].getValue());
                saveDbRecord(itm, isNew);
            }
            //to remove obsolete items (if any)
            obs = DashBoard.getExchanger().getDbObjects(Xmachrentalrateitm.class,
                    "xmachrentalrate_id=" + xmr.getXmachrentalrateId() + " and not cbitem_id in(" + itemIds.toString() + ")", null);
            for (DbObject ob : obs) {
                DashBoard.getExchanger().deleteObject(ob);
            }
        }
        return ok;
    }

    private static double roundDecimal(double val, int decs) {
        return new BigDecimal(val).setScale(decs, RoundingMode.HALF_EVEN).doubleValue();
    }

    private void addListeners() {
        dieselPriceSP.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                isBchanged = true;
                Double dp = (Double) dieselPriceSP.getValue();
                for (int i = 0; i < litersHoursSPs.length; i++) {
                    Double lh = (Double) litersHoursSPs[i].getValue();
                    if (lh != null && dp != null) {
                        dieselPriceLBLs[i].setText("" + roundDecimal(lh * dp, 2));
                    } else {
                        dieselPriceLBLs[i].setText("0.00");
                    }
                    calcCompetativeDry(i);
                    calcRealDry(i);
                    calcGoodDry(i);
                }
                isBchanged = false;
            }
        });
        for (int i = 0; i < litersHoursSPs.length; i++) {
            litersHoursSPs[i].addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    if (!isBchanged) {
                        JSpinner sp = (JSpinner) e.getSource();
                        int i;
                        for (i = 0; i < litersHoursSPs.length; i++) {
                            if (sp == litersHoursSPs[i]) {
                                break;
                            }
                        }
                        Double dp = (Double) dieselPriceSP.getValue();
                        Double lh = (Double) litersHoursSPs[i].getValue();
                        dieselPriceLBLs[i].setText("" + roundDecimal(lh * dp, 2));
                        calcCompetativeDry(i);
                        calcRealDry(i);
                        calcGoodDry(i);
                    }
                }
            });
        }
        factorSP.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                for (JLabel lb : factorLBLs) {
                    lb.setText("" + roundDecimal((Double) factorSP.getValue(), 2));
                }
                isBchanged = true;
                Double dp = (Double) factorSP.getValue();
                for (int i = 0; i < drySPs.length; i++) {
                    Double lh = (Double) drySPs[i].getValue();
                    if (lh != null && dp != null) {
                        compWetLBLs[i].setText("" + roundDecimal(lh * dp, 2));
                    } else {
                        compWetLBLs[i].setText("0.00");
                    }
                    calcCompetativeDry(i);
                }
                isBchanged = false;
            }
        });
        for (int i = 0; i < drySPs.length; i++) {
            drySPs[i].addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    if (!isBchanged) {
                        JSpinner sp = (JSpinner) e.getSource();
                        int i;
                        for (i = 0; i < drySPs.length; i++) {
                            if (sp == drySPs[i]) {
                                break;
                            }
                        }
                        Double dp = (Double) factorSP.getValue();
                        Double lh = (Double) drySPs[i].getValue();
                        compWetLBLs[i].setText("" + roundDecimal(lh * dp, 2));
                        calcCompetativeDry(i);
                    }
                }
            });
        }
        for (int i = 0; i < realWetSPs.length; i++) {
            realWetSPs[i].addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner sp = (JSpinner) e.getSource();
                    int i;
                    for (i = 0; i < realWetSPs.length; i++) {
                        if (sp == realWetSPs[i]) {
                            break;
                        }
                    }
                    calcRealDry(i);
                }
            });
        }
        for (int i = 0; i < goodWetSPs.length; i++) {
            goodWetSPs[i].addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner sp = (JSpinner) e.getSource();
                    int i;
                    for (i = 0; i < goodWetSPs.length; i++) {
                        if (sp == goodWetSPs[i]) {
                            break;
                        }
                    }
                    calcGoodDry(i);
                }
            });
        }
    }

    private void calcCompetativeDry(int i) {
        compDryLBLs[i].setText("" + roundDecimal((Double) factorSP.getValue() * (Double) drySPs[i].getValue()
                - (Double) dieselPriceSP.getValue() * (Double) litersHoursSPs[i].getValue(), 2));
    }

    private void calcRealDry(int i) {
        realDryLBLs[i].setText("" + roundDecimal((Double) realWetSPs[i].getValue()
                - (Double) dieselPriceSP.getValue() * (Double) litersHoursSPs[i].getValue(), 2));
    }

    private void calcGoodDry(int i) {
        goodDryLBLs[i].setText("" + roundDecimal((Double) goodWetSPs[i].getValue()
                - (Double) dieselPriceSP.getValue() * (Double) litersHoursSPs[i].getValue(), 2));
    }
}

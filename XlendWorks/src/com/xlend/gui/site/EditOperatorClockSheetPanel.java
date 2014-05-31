package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xopclocksheet;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditOperatorClockSheetPanel extends RecordEditPanel {

    private DefaultComboBoxModel siteCbModel, operatorCbModel, machineCbModel;
    private JComboBox siteCB, operatorCB, machineCB;
    private JSpinner weekendSP;
    private JSpinner kmStartSP[];
    private JSpinner kmStopSP[];
    private JSpinner workFromSP[];
    private JSpinner workToSP[];
    private JSpinner standFromSP[];
    private JSpinner standToSP[];
    private JTextField reasonTF[];
    private JTextField idField;
    private String[] days;

    public EditOperatorClockSheetPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        days = new String[]{
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturay"
        };
        String titles[] = new String[]{
            "ID:",
            "Operator:", //"Site",
            "Week ending:", //"Machine/Truck"
            "",
            "",
            "",
            days[0], days[1], days[2], days[3], days[4], days[5], days[6]
        };
        kmStartSP = new JSpinner[7];
        kmStopSP = new JSpinner[7];
        workFromSP = new JSpinner[7];
        workToSP = new JSpinner[7];
        standFromSP = new JSpinner[7];
        standToSP = new JSpinner[7];
        reasonTF = new JTextField[7];

        siteCbModel = new DefaultComboBoxModel();
        operatorCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites()) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees()) {
            operatorCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllMachines()) {
            machineCbModel.addElement(ci);
        }
        for (int i = 0; i < 7; i++) {
            kmStartSP[i] = new SelectedNumberSpinner(.0, .0, 99999999.99, .1);
            kmStopSP[i] = new SelectedNumberSpinner(.0, .0, 99999999.99, .1);
            workFromSP[i] = new SelectedDateSpinner();
            workToSP[i] = new SelectedDateSpinner();
            standFromSP[i] = new SelectedDateSpinner();
            standToSP[i] = new SelectedDateSpinner();
            reasonTF[i] = new JTextField();
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 10),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel), new EmployeeLookupAction(operatorCB)),
                new JLabel("Site:", SwingConstants.RIGHT),
//                comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel),new SiteLookupAction(siteCB))
                comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), 
                    new SiteLookupAction(siteCB,Selects.SELECT_SITES4LOOKUP.replaceFirst("is_active=1", "1=1")))
            }),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{weekendSP = new SelectedDateSpinner()}),
                new JLabel("Machine/Truck:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null))
            }),
            new JPanel(),
            getGridPanel(new JComponent[]{
                new JLabel("Hours/Kilometers", SwingConstants.CENTER),
                new JLabel("Working", SwingConstants.CENTER),
                new JLabel("Standing", SwingConstants.CENTER)
            }, 4),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{
                    new JLabel("Start", SwingConstants.CENTER),
                    new JLabel("Stop", SwingConstants.CENTER)
                }),
                getGridPanel(new JComponent[]{
                    new JLabel("From", SwingConstants.CENTER),
                    new JLabel("To", SwingConstants.CENTER)
                }),
                getGridPanel(new JComponent[]{
                    new JLabel("From", SwingConstants.CENTER),
                    new JLabel("To", SwingConstants.CENTER)
                }),
                new JLabel("Reason", SwingConstants.CENTER)
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{kmStartSP[0], kmStopSP[0]}),
                getGridPanel(new JComponent[]{workFromSP[0], workToSP[0]}),
                getGridPanel(new JComponent[]{standFromSP[0], standToSP[0]}),
                reasonTF[0]
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{kmStartSP[1], kmStopSP[1]}),
                getGridPanel(new JComponent[]{workFromSP[1], workToSP[1]}),
                getGridPanel(new JComponent[]{standFromSP[1], standToSP[1]}),
                reasonTF[1]
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{kmStartSP[2], kmStopSP[2]}),
                getGridPanel(new JComponent[]{workFromSP[2], workToSP[2]}),
                getGridPanel(new JComponent[]{standFromSP[2], standToSP[2]}),
                reasonTF[2]
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{kmStartSP[3], kmStopSP[3]}),
                getGridPanel(new JComponent[]{workFromSP[3], workToSP[3]}),
                getGridPanel(new JComponent[]{standFromSP[3], standToSP[3]}),
                reasonTF[3]
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{kmStartSP[4], kmStopSP[4]}),
                getGridPanel(new JComponent[]{workFromSP[4], workToSP[4]}),
                getGridPanel(new JComponent[]{standFromSP[4], standToSP[4]}),
                reasonTF[4]
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{kmStartSP[5], kmStopSP[5]}),
                getGridPanel(new JComponent[]{workFromSP[5], workToSP[5]}),
                getGridPanel(new JComponent[]{standFromSP[5], standToSP[5]}),
                reasonTF[5]
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{kmStartSP[6], kmStopSP[6]}),
                getGridPanel(new JComponent[]{workFromSP[6], workToSP[6]}),
                getGridPanel(new JComponent[]{standFromSP[6], standToSP[6]}),
                reasonTF[6]
            })
        };
        for (int i = 0; i < 7; i++) {
            workFromSP[i].setEditor(new JSpinner.DateEditor(workFromSP[i], "HH:mm"));
            workToSP[i].setEditor(new JSpinner.DateEditor(workToSP[i], "HH:mm"));
            standFromSP[i].setEditor(new JSpinner.DateEditor(standFromSP[i], "HH:mm"));
            standToSP[i].setEditor(new JSpinner.DateEditor(standToSP[i], "HH:mm"));
            Util.addFocusSelectAllAction(workFromSP[i]);
            Util.addFocusSelectAllAction(workToSP[i]);
            Util.addFocusSelectAllAction(standFromSP[i]);
            Util.addFocusSelectAllAction(standToSP[i]);
        }
        weekendSP.setEditor(new JSpinner.DateEditor(weekendSP, "dd/MM/yyyy"));
        weekendSP.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                shiftDayLabels();
            }
        });
        Util.addFocusSelectAllAction(weekendSP);

        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        shiftDayLabels();
    }

    private void shiftDayLabels() {
        Date weekend = (Date) weekendSP.getValue();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekend);
        Date dates[] = new Date[7];
        dates[6] = calendar.getTime();
        int d;
        for (d = 5; d >= 0; d--) {
            calendar.add(Calendar.DATE, -1);
            dates[d] = calendar.getTime();
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 6; i < 13; i++) {
            d = dates[i - 6].getDay();
            labels[i].setText(days[d] + "," + df.format(dates[i - 6]));
        }
    }

    @Override
    public void loadData() {
        Xopclocksheet xs = (Xopclocksheet) getDbObject();
        if (xs != null) {
            Double kmStart[] = new Double[]{
                xs.getKmStart1(), xs.getKmStart2(), xs.getKmStart3(),
                xs.getKmStart4(), xs.getKmStart5(), xs.getKmStart6(),
                xs.getKmStart7()
            };
            Double kmStop[] = new Double[]{
                xs.getKmStop1(), xs.getKmStop2(), xs.getKmStop3(),
                xs.getKmStop4(), xs.getKmStop5(), xs.getKmStop6(),
                xs.getKmStop7()
            };
            String reason[] = new String[]{
                xs.getReason1(), xs.getReason2(), xs.getReason3(),
                xs.getReason4(), xs.getReason5(), xs.getReason6(),
                xs.getReason7()
            };
            Timestamp workFrom[] = new Timestamp[]{
                xs.getWorkFrom1(), xs.getWorkFrom2(), xs.getWorkFrom3(),
                xs.getWorkFrom4(), xs.getWorkFrom5(), xs.getWorkFrom6(),
                xs.getWorkFrom7()
            };
            Timestamp workTo[] = new Timestamp[]{
                xs.getWorkTo1(), xs.getWorkTo2(), xs.getWorkTo3(),
                xs.getWorkTo4(), xs.getWorkTo5(), xs.getWorkTo6(),
                xs.getWorkTo7()
            };
            Timestamp standFrom[] = new Timestamp[]{
                xs.getStandFrom1(), xs.getStandFrom2(), xs.getStandFrom3(),
                xs.getStandFrom4(), xs.getStandFrom5(), xs.getStandFrom6(),
                xs.getStandFrom7()
            };
            Timestamp standTo[] = new Timestamp[]{
                xs.getStandTo1(), xs.getStandTo2(), xs.getStandTo3(),
                xs.getStandTo4(), xs.getStandTo5(), xs.getStandTo6(),
                xs.getStandTo7()
            };

            idField.setText(xs.getXopclocksheetId().toString());
            if (xs.getSheetDate() != null) {
                weekendSP.setValue(new java.util.Date(xs.getSheetDate().getTime()));
            }
            if (xs.getXemployeeId() != null) {
                selectComboItem(operatorCB, xs.getXemployeeId());
            }
            if (xs.getXmachineId() != null) {
                selectComboItem(machineCB, xs.getXmachineId());
            }
            if (xs.getXsiteId() != null) {
                RecordEditPanel.addSiteItem(siteCbModel, xs.getXsiteId());
                selectComboItem(siteCB, xs.getXsiteId());
            }
            for (int i = 0; i < 7; i++) {
                if (kmStart[i] != null) {
                    kmStartSP[i].setValue(kmStart[i]);
                }
                if (kmStop[i] != null) {
                    kmStopSP[i].setValue(kmStop[i]);
                }
                if (workFrom[i] != null) {
                    Timestamp dt = workFrom[i];
                    workFromSP[i].setValue(new java.util.Date(dt.getTime() - TimeZone.getDefault().getOffset(dt.getTime())));
                }
                if (workTo[i] != null) {
                    Timestamp dt = workTo[i];
                    workToSP[i].setValue(new java.util.Date(dt.getTime() - TimeZone.getDefault().getOffset(dt.getTime())));
                }
                if (standFrom[i] != null) {
                    Timestamp dt = standFrom[i];
                    standFromSP[i].setValue(new java.util.Date(dt.getTime() - TimeZone.getDefault().getOffset(dt.getTime())));
                }
                if (standTo[i] != null) {
                    Timestamp dt = standTo[i];
                    standToSP[i].setValue(new java.util.Date(dt.getTime() - TimeZone.getDefault().getOffset(dt.getTime())));
                }
                reasonTF[i].setText(reason[i]);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xopclocksheet xs = (Xopclocksheet) getDbObject();
        if (xs == null) {
            isNew = true;
            xs = new Xopclocksheet(null);
            xs.setXopclocksheetId(0);
        }
        xs.setXemployeeId(getSelectedCbItem(operatorCB));
        xs.setXmachineId(getSelectedCbItem(machineCB));
        xs.setXsiteId(getSelectedCbItem(siteCB));
        Date dt = (Date) weekendSP.getValue();
        if (dt != null) {
            xs.setSheetDate(new java.sql.Date(dt.getTime()));
            if (isNew) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String sd = dateFormat.format(dt);
                int qty = XlendWorks.getExchanger().getCount("select xopclocksheet_id from xopclocksheet "
                        + "where xemployee_id=" + xs.getXemployeeId() + " and sheet_date='" + sd + "'");
                if (qty > 0) {
                    if (GeneralFrame.yesNo("Attention!", 
                            "Operator clock sheet for this employee and date already exists! "
                            + "\nDo you really want to save duplicate?")!=JOptionPane.YES_OPTION) {
                        return false;
                    }
                }
            }
        }

        xs.setKmStart1((Double) kmStartSP[0].getValue());
        xs.setKmStart2((Double) kmStartSP[1].getValue());
        xs.setKmStart3((Double) kmStartSP[2].getValue());
        xs.setKmStart4((Double) kmStartSP[3].getValue());
        xs.setKmStart5((Double) kmStartSP[4].getValue());
        xs.setKmStart6((Double) kmStartSP[5].getValue());
        xs.setKmStart7((Double) kmStartSP[6].getValue());

        xs.setKmStop1((Double) kmStopSP[0].getValue());
        xs.setKmStop2((Double) kmStopSP[1].getValue());
        xs.setKmStop3((Double) kmStopSP[2].getValue());
        xs.setKmStop4((Double) kmStopSP[3].getValue());
        xs.setKmStop5((Double) kmStopSP[4].getValue());
        xs.setKmStop6((Double) kmStopSP[5].getValue());
        xs.setKmStop7((Double) kmStopSP[6].getValue());

        dt = (Date) workFromSP[0].getValue();
        if (dt != null) {
            xs.setWorkFrom1(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workFromSP[1].getValue();
        if (dt != null) {
            xs.setWorkFrom2(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workFromSP[2].getValue();
        if (dt != null) {
            xs.setWorkFrom3(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workFromSP[3].getValue();
        if (dt != null) {
            xs.setWorkFrom4(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workFromSP[4].getValue();
        if (dt != null) {
            xs.setWorkFrom5(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workFromSP[5].getValue();
        if (dt != null) {
            xs.setWorkFrom6(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workFromSP[6].getValue();
        if (dt != null) {
            xs.setWorkFrom7(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }

        dt = (Date) workToSP[0].getValue();
        if (dt != null) {
            xs.setWorkTo1(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workToSP[1].getValue();
        if (dt != null) {
            xs.setWorkTo2(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workToSP[2].getValue();
        if (dt != null) {
            xs.setWorkTo3(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workToSP[3].getValue();
        if (dt != null) {
            xs.setWorkTo4(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workToSP[4].getValue();
        if (dt != null) {
            xs.setWorkTo5(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workToSP[5].getValue();
        if (dt != null) {
            xs.setWorkTo6(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) workToSP[6].getValue();
        if (dt != null) {
            xs.setWorkTo7(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }

        dt = (Date) standFromSP[0].getValue();
        if (dt != null) {
            xs.setStandFrom1(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standFromSP[1].getValue();
        if (dt != null) {
            xs.setStandFrom2(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standFromSP[2].getValue();
        if (dt != null) {
            xs.setStandFrom3(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standFromSP[3].getValue();
        if (dt != null) {
            xs.setStandFrom4(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standFromSP[4].getValue();
        if (dt != null) {
            xs.setStandFrom5(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standFromSP[5].getValue();
        if (dt != null) {
            xs.setStandFrom6(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standFromSP[6].getValue();
        if (dt != null) {
            xs.setStandFrom7(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }

        dt = (Date) standToSP[0].getValue();
        if (dt != null) {
            xs.setStandTo1(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standToSP[1].getValue();
        if (dt != null) {
            xs.setStandTo2(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standToSP[2].getValue();
        if (dt != null) {
            xs.setStandTo3(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standToSP[3].getValue();
        if (dt != null) {
            xs.setStandTo4(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standToSP[4].getValue();
        if (dt != null) {
            xs.setStandTo5(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standToSP[5].getValue();
        if (dt != null) {
            xs.setStandTo6(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        dt = (Date) standToSP[6].getValue();
        if (dt != null) {
            xs.setStandTo7(new Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
        }
        xs.setReason1(reasonTF[0].getText());
        xs.setReason2(reasonTF[1].getText());
        xs.setReason3(reasonTF[2].getText());
        xs.setReason4(reasonTF[3].getText());
        xs.setReason5(reasonTF[4].getText());
        xs.setReason6(reasonTF[5].getText());
        xs.setReason7(reasonTF[6].getText());

        return saveDbRecord(xs, isNew);
    }

    @Override
    public void freeResources() {
        //TODO
    }
}

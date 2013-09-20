package com.xlend.gui.admin;

import com.jidesoft.swing.Flashable;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Userprofile;
import com.xlend.orm.Usersheet;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditSheetAccessPanel extends RecordEditPanel {

    private Hashtable<Integer, JCheckBox> checkBoxes;
    private Hashtable<JCheckBox, JCheckBox[]> cbGroups;
    private boolean insideChildCb;

    public EditSheetAccessPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        insideChildCb = false;
        checkBoxes = new Hashtable<Integer, JCheckBox>();
        cbGroups = new Hashtable<JCheckBox, JCheckBox[]>();
        ComboItem[] citms = XlendWorks.loadRootSheets();
        setLayout(new BorderLayout(5, 5));
        JPanel leftPanel = new JPanel(new GridLayout(citms.length, 1, 10, 10));
        JPanel cntrPanel = new JPanel(new GridLayout(citms.length, 1, 10, 10));
        for (int i = 0; i < citms.length; i++) {
            JCheckBox cb = new JCheckBox(citms[i].getValue());
            checkBoxes.put(citms[i].getId(), cb);
            leftPanel.add(cb);
            cntrPanel.add(rightPanel(citms[i].getId(), cb));
            cb.addChangeListener(parentCbChangeListener());
        }
        cntrPanel.setBorder(BorderFactory.createEtchedBorder());
        add(leftPanel, BorderLayout.WEST);
        add(cntrPanel, BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
        Userprofile user = (Userprofile) getDbObject();
        if (user != null) {
            try {
                DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Usersheet.class, "profile_id=" + user.getProfileId(), null);
                for (DbObject o : recs) {
                    Usersheet us = (Usersheet) o;
                    JCheckBox cb = checkBoxes.get(us.getSheetId());
                    if (cb != null) {
                        insideChildCb = true;
                        cb.setSelected(true);
                        insideChildCb = false;
                    }
                }
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Userprofile user = (Userprofile) getDbObject();
        if (user != null) {
            DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Usersheet.class, "profile_id=" + user.getProfileId(), null);
            for (DbObject rec : recs) {
                XlendWorks.getExchanger().deleteObject(rec);
            }
            for (JCheckBox cb : checkBoxes.values()) {
                Integer sheetId = findSheetIdOnCheckBox(cb);
                if (cb.isSelected() && sheetId != null) {
                    Usersheet us = new Usersheet(null);
                    us.setUsersheetId(0);
                    us.setProfileId(user.getProfileId());
                    us.setSheetId(sheetId);
                    saveDbRecord(us, true);
                }
            }
        }
        return true;
    }

    private JPanel rightPanel(int parent_id, JCheckBox parentCb) {
        ComboItem[] citms = XlendWorks.loadSubSheets(parent_id);
        JCheckBox[] cbGrp = new JCheckBox[citms.length];
        JPanel rPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));//new JPanel(new GridLayout(1, citms.length, 5, 5));
        rPanel.setBorder(BorderFactory.createEtchedBorder());
        int i = 0;
        for (ComboItem ci : citms) {
            cbGrp[i] = new JCheckBox(ci.getValue());
            checkBoxes.put(ci.getId(), cbGrp[i]);
            rPanel.add(cbGrp[i]);
            cbGrp[i].addChangeListener(childCbChangeListener(parentCb));
            i++;
        }
        cbGroups.put(parentCb, cbGrp);
        return rPanel;
    }

    private ChangeListener parentCbChangeListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!insideChildCb) {
                    JCheckBox papa = (JCheckBox) e.getSource();
                    JCheckBox[] grp = cbGroups.get(papa);
                    for (JCheckBox son : grp) {
                        son.setSelected(papa.isSelected());
                    }
                }
            }
        };
    }

    private Integer findSheetIdOnCheckBox(JCheckBox cb) {
        Enumeration<Integer> en = checkBoxes.keys();
        while (en.hasMoreElements()) {
            Integer sheetId = en.nextElement();
            JCheckBox itm = checkBoxes.get(sheetId);
            if (cb == itm) {
                return sheetId;
            }
        }
        return null;
    }

    private ChangeListener childCbChangeListener(final JCheckBox parentCb) {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                if (cb.isSelected()) {
                    insideChildCb = true;
                    parentCb.setSelected(true);
                    insideChildCb = false;
                }
            }
        };
    }
}

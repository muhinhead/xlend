package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xbateryissue;
import com.xlend.orm.Xbattery;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class BatteryIssuesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public BatteryIssuesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_BATTISSUES, maxWidths, false);
        setBorder(BorderFactory.createTitledBorder("Output"));
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Issue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditBatteryIssueDialog ed = new EditBatteryIssueDialog("New Issue", null);
                    if (EditBatteryIssueDialog.okPressed) {
                        Xbateryissue bi = (Xbateryissue) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), bi.getXbateryissueId(),
                                getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit Issue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xbateryissue bi = (Xbateryissue) exchanger.loadDbObjectOnID(Xbateryissue.class, id);
                    new EditBatteryIssueDialog("Edit Issue", bi);
                    if (EditBatteryIssueDialog.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete Issue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xbateryissue bi = (Xbateryissue) exchanger.loadDbObjectOnID(Xbateryissue.class, id);
                    if (bi != null && GeneralFrame.yesNo("Attention!", "Do you want to delete issue record?") == JOptionPane.YES_OPTION) {
                        DbObject[] itms = exchanger.getDbObjects(Xbattery.class,
                                "xbateryissue_id=" + bi.getXbateryissueId(), null);
                        try {
                            for (DbObject o : itms) {
                                Xbattery bat = (Xbattery) o;
                                bat.setXbateryissueId(null);
                                XlendWorks.getExchanger().saveDbObject(bat);
                            }
                            exchanger.deleteObject(bi);
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                    getSelect(), null, getPageSelector().getSelectedIndex());
                        } catch (Exception ex) {
                            XlendWorks.log(ex);
                            GeneralFrame.errMessageBox("Error:", ex.getMessage());
                        }
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}

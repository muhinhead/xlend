package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xessential;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class EssentialsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public EssentialsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_ESSENTIALS, maxWidths, false);
    }

    public EssentialsGrid(IMessageSender exchanger, int driver_id) throws RemoteException {
        super(exchanger, Selects.SELECT_ESSENTIALS_ON_DRIVER.replace("#", "" + driver_id), maxWidths, true);
    }

    public void reloadForDriver(int driver_id) {
        setSelect(Selects.SELECT_ESSENTIALS_ON_DRIVER.replace("#", "" + driver_id));
        refresh();
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New issue(s)") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = getSelectedID();
                    if (id > 0) {
                        Xessential xi = (Xessential) XlendWorks.getExchanger().loadDbObjectOnID(Xessential.class, id);
                        if (xi != null) {
                            EssentialsPanel.setDriverID(xi.getDriverId());
                        }
                    }
                    EssentialsDialog ed = new EssentialsDialog("New issue(s)", null);
                    if (EssentialsDialog.okPressed) {
                        Xessential xi = (Xessential) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xi != null ? xi.getXessentialId() : null,
                                getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Return(s)") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xessential xi = (Xessential) XlendWorks.getExchanger().loadDbObjectOnID(Xessential.class, id);
                        if (xi != null) {
                            EssentialsPanel.setDriverID(xi.getDriverId());
                        }
                        new EssentialsDialog("Returns(s)", xi);
                        if (EssentialsDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                        }

                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        if (XlendWorks.getCurrentUser().getSupervisor() == 1) {
            return new AbstractAction("Delete record") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = getSelectedID();
                    try {
                        Xessential xi = (Xessential) XlendWorks.getExchanger().loadDbObjectOnID(Xessential.class, id);
                        if (xi != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(xi);
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            };
        } else {
            return null;
        }
    }

}

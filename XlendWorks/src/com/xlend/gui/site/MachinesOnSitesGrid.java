package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachineonsite;
import com.xlend.orm.Xsite;
import com.xlend.remote.IMessageSender;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

/**
 *
 * @author Nick Mukhin
 */
public class MachinesOnSitesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xsite xsite = null;
    private static final String whereId = "xsite_id = #";

    static {
        maxWidths.put(0, 40);
    }

    public MachinesOnSitesGrid(IMessageSender exchanger, String slct) throws RemoteException {
        super(exchanger, slct, maxWidths, false);
        int p = Selects.SELECTMACHINESONSITE.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECTMACHINESONSITE.substring(0, p))) {
            xsite = (Xsite) exchanger.loadDbObjectOnID(
                    Xsite.class, Integer.parseInt(getSelect().substring(p + whereId.length() - 1)));
        }
    }

    protected void init(AbstractAction[] acts, Vector[] tableBody, HashMap<Integer, Integer> maxWidths) {
        AbstractAction[] superActs = new AbstractAction[acts.length + 1];
        for (int i = 0; i < acts.length; i++) {
            superActs[i] = acts[i];
        }
        superActs[acts.length] = deEstablishAction();
        super.init(superActs, tableBody, maxWidths);
        enableActions();
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Machine to site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                EditMachineOnSiteItemDialog ed;
                try {
                    EditMachineOnSiteItemDialog.xsite = getXsite();
                    if (getXsite() != null) {
                        ed = new EditMachineOnSiteItemDialog("Add Machine to site", null);
                        if (EditMachineOnSiteItemDialog.okPressed) {
                            Xmachineonsite xsitemachine = (Xmachineonsite) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                    xsitemachine.getXmachineonsateId());
                        }
                    } else {
                        GeneralFrame.infoMessageBox("Attention!", "Save site please before adding machines");
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
        return new AbstractAction("Edit Entry") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachineonsite xsitemachine = (Xmachineonsite) exchanger.loadDbObjectOnID(Xmachineonsite.class, id);
                        EditMachineOnSiteItemDialog.xsite = getXsite();
                        EditMachineOnSiteItemDialog dlg = new EditMachineOnSiteItemDialog("Edit Machine on Site", xsitemachine);
                        if (dlg.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id);
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
        return new AbstractAction("Delete Entry") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xmachineonsite xsitemachine = (Xmachineonsite) exchanger.loadDbObjectOnID(Xmachineonsite.class, id);
                    if (xsitemachine != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete machine on site?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xsitemachine);
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

//    @Override
    protected AbstractAction deEstablishAction() {
        return new AbstractAction("De-establish from site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xmachineonsite xsitemachine = (Xmachineonsite) exchanger.loadDbObjectOnID(Xmachineonsite.class, id);
                    JSpinner dtSp = new SelectedDateSpinner();
                    dtSp.setEditor(new JSpinner.DateEditor(dtSp, "dd/MM/yyyy"));
                    Util.addFocusSelectAllAction(dtSp);
                    dtSp.setValue(xsitemachine.getDeestdate() == null ? 
                            Calendar.getInstance().getTime() : xsitemachine.getDeestdate());
                    new DeEstablishMachineDate("De-establish machine from site", dtSp);
                    if (DeEstablishMachineDate.okPressed) {
                        java.util.Date dt = (java.util.Date) dtSp.getValue();
                        xsitemachine.setDeestdate(new java.sql.Date(dt.getTime()));
                        exchanger.saveDbObject(xsitemachine);
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null);
                    }
                } catch (Exception ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    /**
     * @return the xsite
     */
    public Xsite getXsite() {
        return xsite;
    }
}

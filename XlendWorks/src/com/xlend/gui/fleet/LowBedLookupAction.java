package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.work.SitesGrid;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin 
 */
public class LowBedLookupAction extends AbstractAction {
    private final JComboBox lowbedCB;

    public LowBedLookupAction(JComboBox cb) {
        super("...");
        this.lowbedCB = cb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Low-bed Lookup", lowbedCB,
                    new LowBedGrid(DashBoard.getExchanger(), Selects.SELECT_ALL_LOWBEDS, true),
                    new String[]{"l.xlowbed_id","m.classify+m.tmvnr", "d.clock_num", "d.first_name", "a.clock_num", "a.first_name"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}

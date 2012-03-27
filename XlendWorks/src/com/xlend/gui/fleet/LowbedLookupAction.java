package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class LowbedLookupAction extends AbstractAction {

    private JComboBox lowbedCB;
    private final String additionalCondition;

    public LowbedLookupAction(JComboBox cBox, String addWhereCond) {
        super("...");
        this.lowbedCB = cBox;
        this.additionalCondition = addWhereCond;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Lowbed Lookup", lowbedCB,
                    new LowBedGrid(DashBoard.getExchanger(),
                    Selects.SELECT_LOWBEDS4LOOKUP
                    + (additionalCondition == null ? "" : (" and " + additionalCondition)), false),
                    new String[]{"tmvnr", "reg_nr"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}


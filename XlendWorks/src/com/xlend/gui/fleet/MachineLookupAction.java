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
public class MachineLookupAction extends AbstractAction {

    private JComboBox machineCB;
    private final String additionalCondition;

    public MachineLookupAction(JComboBox cBox, String addWhereCond) {
        super("...");
        this.machineCB = cBox;
        this.additionalCondition = addWhereCond;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Machines Lookup", machineCB,
                    new MachineGrid(DashBoard.getExchanger(),
                    Selects.SELECT_MASCHINES4LOOKUP
                    + (additionalCondition == null ? "" : (" and " + additionalCondition)), false),
                    new String[]{"tmvnr", "reg_nr"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}

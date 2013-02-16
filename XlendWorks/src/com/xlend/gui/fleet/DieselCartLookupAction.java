package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.work.SuppliersGrid;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class DieselCartLookupAction extends AbstractAction {

    private JComboBox dieselCartCB;

    public DieselCartLookupAction(JComboBox cBox) {
        super("...");
        this.dieselCartCB = cBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showSupplierLookup();
    }

    private void showSupplierLookup() {
        try {
            LookupDialog ld = new LookupDialog("Diesel Cart Lookup", dieselCartCB,
                    new DieselCartsGrid(DashBoard.getExchanger(), Selects.SELECT_DIESELCARTS4LOOKUP, false),
                    new String[]{"fleet_nr", "reg_nr", "chassis_nr"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}

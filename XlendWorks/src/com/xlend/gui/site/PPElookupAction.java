package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.admin.PPEtypeGrid;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class PPElookupAction extends AbstractAction {
    private final JComboBox ppeTypeCB;

    public PPElookupAction(JComboBox ppeTypeCB) {
        super("...");
        this.ppeTypeCB = ppeTypeCB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("PPE Types Lookup",ppeTypeCB,
                    new PPEtypeGrid(DashBoard.getExchanger()),new String[]{"xppetype"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }    
}

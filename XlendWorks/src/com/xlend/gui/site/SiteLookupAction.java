package com.xlend.gui.site;

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
public class SiteLookupAction extends AbstractAction {

    private final JComboBox siteCB;
    private final String select;

    public SiteLookupAction(JComboBox cb) {
        this(cb, null);
    }

    public SiteLookupAction(JComboBox cb, String select) {
        super("...");
        this.siteCB = cb;
        this.select = select;
    }

    private String getLookupSelect() {
        return (select == null ? Selects.SELECT_SITES4LOOKUP : select);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Site Lookup", siteCB,
                    new SitesGrid(DashBoard.getExchanger(),
                    getLookupSelect(), true), new String[]{"name"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}

package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.creditor.EditPaymentDialog;
import com.xlend.orm.Xhourcompare;
import com.xlend.orm.Xpayment;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class HourCompareGrid extends GeneralGridPanel {

    public HourCompareGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_HOURCOMPARE, getMaxWidths(new int[]{40, 150, 100}), false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Hour Comparison") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditHourCompareDialog ed = new EditHourCompareDialog("New Hour Comparison", null);
                    if (EditHourCompareDialog.okPressed) {
                        Xhourcompare xc = (Xhourcompare) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xc.getXhourcompareId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Hour Comparison") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xhourcompare xc = (Xhourcompare) exchanger.loadDbObjectOnID(Xhourcompare.class, id);
                        new EditHourCompareDialog("Edit Hour Comparison", xc);
                        if (EditHourCompareDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                    getSelect(), id, getPageSelector().getSelectedIndex());
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
                    Xhourcompare xc = (Xhourcompare) exchanger.loadDbObjectOnID(Xhourcompare.class, id);
                    if (xc != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xc);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}

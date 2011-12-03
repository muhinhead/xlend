package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.employee.EditEmployeeDialog;
import com.xlend.orm.Xemployee;
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
public class EmployeesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public EmployeesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_EMPLOYEE, maxWidths, false);
    }

    public EmployeesGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, maxWidths, true);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Employee") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditEmployeeDialog ed = new EditEmployeeDialog("New Employee", null);
                    if (EditEmployeeDialog.okPressed) {
                        Xemployee emp = (Xemployee) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), emp.getXemployeeId());
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
        return new AbstractAction("Edit Employee") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xemployee emp = (Xemployee) exchanger.loadDbObjectOnID(Xemployee.class, id);
                        new EditEmployeeDialog("Edit Employee", emp);
                        if (EditEmployeeDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id);
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
        return new AbstractAction("Delete Employee") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xemployee emp = (Xemployee) exchanger.loadDbObjectOnID(Xemployee.class, id);
                    if (emp != null && GeneralFrame.yesNo("Attention!", "Do you want to delete employee  ["
                            + emp.getFirstName()+" "+emp.getSurName() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(emp);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null);
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}

package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.employee.EditEmployeeDialog;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.orm.Xemployee;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class EmployeesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    private JCheckBox showDisappearedCB;

    public EmployeesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.selectActiveEmployees(), maxWidths, false);
        addTopControls();
    }

    public EmployeesGrid(IMessageSender exchanger, String select, boolean readOnly) throws RemoteException {
        super(exchanger, select, maxWidths, readOnly);
    }

    private void addTopControls() {
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        upperPanel.add(showDisappearedCB = new JCheckBox("Show dismissed/resigned/absconded/deceased"));
        showDisappearedCB.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                String newSelect = showDisappearedCB.isSelected()
                        ? Selects.SELECT_FROM_EMPLOYEE
                        : Selects.selectActiveEmployees();
                DbTableDocument td = (DbTableDocument) getController().getDocument();//.setSelectStatement(newSelect);
                setSelect(newSelect);
                td.setSelectStatement(newSelect);
                try {
                    td.setBody(exchanger.getTableBody(newSelect,0,PAGESIZE));
                    updatePageCounter(newSelect);
                    GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    XlendWorks.log(ex);
                }
            }
        });

        add(upperPanel, BorderLayout.NORTH);
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
                                getTableView(), getTableDoc(), getSelect(), emp.getXemployeeId(), getPageSelector().getSelectedIndex());
                        XlendWorks.refreshEmployeeCache(exchanger);
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
                        Xemployee emp = (Xemployee) exchanger.loadDbObjectOnID(Xemployee.class, id);
                        new EditEmployeeDialog("Edit Employee", emp);
                        if (EditEmployeeDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                            XlendWorks.refreshEmployeeCache(exchanger);
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
                    Xemployee emp = (Xemployee) exchanger.loadDbObjectOnID(Xemployee.class, id);
                    if (emp != null && GeneralFrame.yesNo("Attention!", "Do you want to delete employee  ["
                            + emp.getFirstName() + " " + emp.getSurName() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(emp);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                        XlendWorks.refreshEmployeeCache(exchanger);
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}

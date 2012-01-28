package com.xlend.gui.contract;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.PagesPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xcontractpage;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.Util;
import java.awt.event.ActionEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class ContractPagesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    private final int contract_id;

    public ContractPagesGrid(IMessageSender exchanger, int contract_id) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_CONTRACTPAGE.replace("#", "" + contract_id), maxWidths, false);
        this.contract_id = contract_id;
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Contract Page(s)") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser =
                            new JFileChooser(DashBoard.readProperty("imagedir", "./"));
                    chooser.setMultiSelectionEnabled(true);
                    chooser.setFileFilter(new PagesPanel.PagesDocFileFilter());
                    chooser.setDialogTitle("Load External Documents");
                    chooser.setApproveButtonText("Load");
                    int retVal = chooser.showOpenDialog(null);

                    if (retVal == JFileChooser.APPROVE_OPTION) {
                        File[] files = chooser.getSelectedFiles();
                        int n = DashBoard.getExchanger().getDbObjects(Xcontractpage.class, "xcontract_id=" + contract_id, null).length + 1;
                        for (File f : files) {
                            Xcontractpage contractPage = new Xcontractpage(null);
                            contractPage.setXcontractpageId(0);
                            contractPage.setXcontractId(contract_id);
                            contractPage.setDescription("Page " + n);
                            contractPage.setPagenum(n++);
                            String extension = f.getName().substring(f.getName().lastIndexOf(".")+1);
                            contractPage.setFileextension(extension);
                            contractPage.setPagescan(Util.readFile(f.getAbsolutePath()));
                            contractPage.setNew(true);
                            DbObject saved = DashBoard.getExchanger().saveDbObject(contractPage);
                        }
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null);
                    }

                } catch (Exception ex) {
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
                        Xcontractpage contractPage = (Xcontractpage) exchanger.loadDbObjectOnID(Xcontractpage.class, id);
                        new EditContractPageDialog("Edit Contract Page", contractPage);
                        if (EditContractPageDialog.okPressed) {
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
                    Xcontractpage contractPage = (Xcontractpage) exchanger.loadDbObjectOnID(Xcontractpage.class, id);
                    if (contractPage != null && GeneralFrame.yesNo("Attention!", "Do you want to delete contract page No_"
                            + contractPage.getPagenum() + "?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(contractPage);
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

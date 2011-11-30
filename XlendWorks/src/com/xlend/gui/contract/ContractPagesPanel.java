package com.xlend.gui.contract;

import com.xlend.gui.PagesPanel;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xcontractpage;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.remote.IMessageSender;
import com.xlend.util.NoFrameButton;
import com.xlend.util.PopupListener;
import com.xlend.util.Util;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author Nick Mukhin
 */
public class ContractPagesPanel extends PagesPanel {

    public ContractPagesPanel(IMessageSender exchanger, final int contract_id) throws RemoteException {
        super(exchanger, contract_id);
    }

    protected void processFiles(File[] files) throws SQLException, RemoteException, ForeignKeyViolationException {
        int n = DashBoard.getExchanger().getDbObjects(Xcontractpage.class,
                "xcontract_id=" + parent_id, null).length + 1;
        for (File f : files) {
            Xcontractpage contractPage = new Xcontractpage(null);
            contractPage.setXcontractpageId(0);
            contractPage.setXcontractId(parent_id);
            if (f.getName().toUpperCase().endsWith(".DOC")
                    || f.getName().toUpperCase().endsWith(".XLS")
                    || f.getName().toUpperCase().endsWith(".TXT")) {
                contractPage.setDescription(f.getName());
            } else {
                contractPage.setDescription("Page " + n);
            }
            String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);
            contractPage.setFileextension(extension);
            contractPage.setPagenum(n++);
            contractPage.setPagescan(Util.readFile(f.getAbsolutePath()));
            contractPage.setNew(true);
            if (parent_id == 0) {
                newPages.add(contractPage);
            } else {
                DbObject saved = DashBoard.getExchanger().saveDbObject(contractPage);
            }
        }
        reloadPages();
    }

    protected void reloadPages() throws RemoteException {
        DbObject[] pages = new DbObject[newPages.size()];
        if (parent_id == 0) {
            int i = 0;
            for (DbObject p : newPages) {
                pages[i++] = p;
            }
        } else {
            pages = exchanger.getDbObjects(Xcontractpage.class, "xcontract_id=" + parent_id, "pagenum");
        }
        setVisible(false);
        removeAll();
        super.reloadPages();
        for (DbObject o : pages) {
            NoFrameButton btn;
            final Xcontractpage contractPage = (Xcontractpage) o;
            Image ic = PagesPanel.getImageOnExtension(contractPage);
            add(btn = new NoFrameButton(new ImageIcon(ic)));
            String lbl = contractPage.getDescription() == null ? "" : contractPage.getDescription().trim();
            btn.setText(lbl);
            btn.setToolTipText(lbl);
            btn.setTag(contractPage);
            AbstractAction editAction = new AbstractAction("Edit page") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new EditContractPageDialog("Edit contract page", contractPage);
                        reloadPages();
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            };
            btn.addActionListener(editAction);
            JPopupMenu pm = new JPopupMenu();
            pm.add(editAction);
            pm.add(new AbstractAction("Delete page") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (GeneralFrame.yesNo("Attention!", "Do you want to delete contract page No_"
                            + contractPage.getPagenum() + "?") == JOptionPane.YES_OPTION) {
                        try {
                            exchanger.deleteObject(contractPage);
                            reloadPages();
                        } catch (RemoteException ex) {
                            XlendWorks.log(ex);
                            GeneralFrame.errMessageBox("Error:", ex.getMessage());
                        }
                    }
                }
            });
            btn.addMouseListener(new PopupListener(pm));
            btns.add(btn);
        }

        setVisible(true);
    }

    @Override
    protected void setParentId(DbObject ob, int newParent_id) throws SQLException, ForeignKeyViolationException {
        Xcontractpage contractPage = (Xcontractpage) ob;
        contractPage.setXcontractId(newParent_id);
    }
}

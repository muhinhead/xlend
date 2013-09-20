package com.xlend.gui.order;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.PagesPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xorderpage;
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
public class OrderPagesPanel extends PagesPanel {

    public OrderPagesPanel(IMessageSender exchanger, final int xquotation_id) throws RemoteException {
        super(exchanger, xquotation_id);
    }

    @Override
    protected void reloadPages() throws RemoteException {
        DbObject[] pages = new DbObject[newPages.size()];
        if (parent_id == 0) {
            int i = 0;
            for (DbObject p : newPages) {
                pages[i++] = p;
            }
        } else {
            pages = exchanger.getDbObjects(Xorderpage.class, "xorder_id=" + parent_id, "pagenum");
        }

        setVisible(false);
        removeAll();
        super.reloadPages();
        for (DbObject o : pages) {
            NoFrameButton btn;
            final Xorderpage xorderPage = (Xorderpage) o;
            Image ic = PagesPanel.getImageOnExtension(xorderPage);
            add(btn = new NoFrameButton(new ImageIcon(ic)));
            String lbl = xorderPage.getDescription() == null ? "" : xorderPage.getDescription().trim();
            btn.setText(lbl);
            btn.setToolTipText(lbl);
            btn.setTag(xorderPage);
            AbstractAction editAction = new AbstractAction("Edit page") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new EditOrderPageDialog("Edit order page", xorderPage);
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
                    if (GeneralFrame.yesNo("Attention!", "Do you want to delete order page No_"
                            + xorderPage.getPagenum() + "?") == JOptionPane.YES_OPTION) {
                        try {
                            exchanger.deleteObject(xorderPage);
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
    protected void processFiles(File[] files) throws SQLException, RemoteException, ForeignKeyViolationException {
        int n = XlendWorks.getExchanger().getDbObjects(Xorderpage.class,
                "xorder_id=" + parent_id, null).length + 1;
        for (File f : files) {
            Xorderpage orderPage = new Xorderpage(null);
            orderPage.setXorderpageId(0);
            orderPage.setXorderId(parent_id);
            if (f.getName().toUpperCase().endsWith(".DOC")
                    || f.getName().toUpperCase().endsWith(".XLS")
                    || f.getName().toUpperCase().endsWith(".TXT")) {
                orderPage.setDescription(f.getName());
            } else {
                orderPage.setDescription("Page " + n);
            }
            String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);
            orderPage.setFileextension(extension);
            orderPage.setPagenum(n++);
            orderPage.setPagescan(Util.readFile(f.getAbsolutePath()));
            orderPage.setNew(true);
            if (parent_id == 0) {
                newPages.add(orderPage);
            } else {
                DbObject saved = XlendWorks.getExchanger().saveDbObject(orderPage);
            }
        }
        reloadPages();
    }

    @Override
    protected void setParentId(DbObject ob, int newParent_id) throws SQLException, ForeignKeyViolationException {
        Xorderpage orderPage = (Xorderpage) ob;
        orderPage.setXorderId(newParent_id);
    }
}

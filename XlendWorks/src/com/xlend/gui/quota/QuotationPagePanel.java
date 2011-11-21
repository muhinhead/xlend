package com.xlend.gui.quota;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.PagesPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xquotationpage;
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
public class QuotationPagePanel extends PagesPanel {

    public QuotationPagePanel(IMessageSender exchanger, final int xquotation_id) throws RemoteException {
        super(exchanger, xquotation_id);
    }
    
    @Override
    protected void reloadPages() throws RemoteException {
        DbObject[] pages = exchanger.getDbObjects(Xquotationpage.class, "xquotation_id=" + parent_id, "pagenum");
        setVisible(false);
        removeAll();
        for (DbObject o : pages) {
            NoFrameButton btn;
            final Xquotationpage quotationPage = (Xquotationpage) o;
            Image ic = XlendWorks.loadImage("page.png", DashBoard.ourInstance);
            add(btn = new NoFrameButton(new ImageIcon(ic)));
            String lbl = quotationPage.getDescription() == null ? "" : quotationPage.getDescription().trim();
            btn.setText(lbl);
            btn.setToolTipText(lbl);
            btn.setTag(quotationPage);
            AbstractAction editAction = new AbstractAction("Edit page") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new EditQuotaPageDialog("Edit RFC page", quotationPage);
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
                    if (GeneralFrame.yesNo("Attention!", "Do you want to delete RFC page No_"
                            + quotationPage.getPagenum() + "?") == JOptionPane.YES_OPTION) {
                        try {
                            exchanger.deleteObject(quotationPage);
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
        int n = DashBoard.getExchanger().getDbObjects(Xquotationpage.class,
                "xquotation_id=" + parent_id, null).length + 1;
        for (File f : files) {
            Xquotationpage quotationpage = new Xquotationpage(null);
            quotationpage.setXquotationpageId(0);
            quotationpage.setXquotationId(parent_id);
            quotationpage.setPagenum(n++);
            quotationpage.setDescription("Page " + n);
            quotationpage.setPagescan(Util.readFile(f.getAbsolutePath()));
            quotationpage.setNew(true);
            DbObject saved = DashBoard.getExchanger().saveDbObject(quotationpage);
        }
        reloadPages();
    }
    
}

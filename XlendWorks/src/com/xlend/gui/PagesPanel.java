/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui;

import com.xlend.orm.IPage;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.remote.IMessageSender;
import com.xlend.util.NoFrameButton;
import com.xlend.util.PopupListener;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author nick
 */
public abstract class PagesPanel extends JPanel {

    private JPopupMenu pop;
    protected final IMessageSender exchanger;
    protected final int parent_id;
    protected final AbstractAction addAction;
    protected ArrayList<NoFrameButton> btns = new ArrayList<NoFrameButton>();
    protected ArrayList<DbObject> newPages = new ArrayList<DbObject>();
    private static HashMap<String, String> imagemap = new HashMap<String, String>();

    static {
        imagemap.put("jpg", "page_img.png");
        imagemap.put("jpeg", "page_img.png");
        imagemap.put("png", "page_img.png");
        imagemap.put("gif", "page_img.png");
        imagemap.put("doc", "page_txt.png");
        imagemap.put("xls", "page_xls.png");
        imagemap.put("ppt", "page_pps.png");
        imagemap.put("txt", "page.png");
    }

    public static class PagesDocFileFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {
            boolean ok = f.isDirectory()
                    || f.getName().toLowerCase().endsWith("jpg")
                    || f.getName().toLowerCase().endsWith("png")
                    || f.getName().toLowerCase().endsWith("jpeg")
                    || f.getName().toLowerCase().endsWith("gif")
                    || f.getName().toLowerCase().endsWith("doc")
                    || f.getName().toLowerCase().endsWith("txt")
                    || f.getName().toLowerCase().endsWith("ppt")
                    || f.getName().toLowerCase().endsWith("xls");
            return ok;
        }

        public String getDescription() {
            return "*.JPG ; *.GIF; *.PNG; *.TXT; *.DOC; *.XLS; *.PPT";
        }
    }

    public PagesPanel(IMessageSender exchanger, final int papa_id) throws RemoteException {
        super(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.exchanger = exchanger;
        this.parent_id = papa_id;

        activatePopup(addAction = new AbstractAction("Add image(s)") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chooseFiles();
                } catch (Exception ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        });

        reloadPages();
    }

    protected abstract void setParentId(DbObject ob, int newParent_id) throws SQLException, ForeignKeyViolationException;

    public void saveNewPages(int newParent_id) throws RemoteException, SQLException, ForeignKeyViolationException {
        for (DbObject page : newPages) {
            setParentId(page, newParent_id);
            DbObject saved = DashBoard.getExchanger().saveDbObject(page);
        }
    }

    protected void chooseFiles() throws SQLException, RemoteException, ForeignKeyViolationException {
        JFileChooser chooser =
                new JFileChooser(DashBoard.readProperty("imagedir", "./"));
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new PagesDocFileFilter());
        chooser.setDialogTitle("Load External Documents");
        chooser.setApproveButtonText("Load");
        int retVal = chooser.showOpenDialog(null);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            processFiles(chooser.getSelectedFiles());
        }
    }

    protected void reloadPages() throws RemoteException {
        Image ic = XlendWorks.loadImage("newdoc.png", DashBoard.ourInstance);
        NoFrameButton btn = new NoFrameButton(new ImageIcon(ic));
        btn.setText("Add");
        btn.setToolTipText("Add scanned document(s)");
        btn.addActionListener(addAction);
        add(btn);
    }

    protected static Image getImageOnExtension(IPage page) {
        String extension = page.getFileextension() == null ? "" : page.getFileextension().toLowerCase();
        String iconFile = imagemap.get(extension);
        return XlendWorks.loadImage(iconFile == null ? "page.png" : iconFile, DashBoard.ourInstance);
    }

    protected abstract void processFiles(File[] files)
            throws SQLException, RemoteException, ForeignKeyViolationException;

    public void activatePopup(AbstractAction addAction) {
        pop = new JPopupMenu();
        pop.add(addAction);
        addMouseListener(new PopupListener(pop));
    }
}

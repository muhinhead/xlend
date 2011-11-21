/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui;

import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.remote.IMessageSender;
import com.xlend.util.NoFrameButton;
import com.xlend.util.PopupListener;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
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

    public PagesPanel(IMessageSender exchanger, final int papa_id) throws RemoteException {
        super(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.exchanger = exchanger;
        this.parent_id = papa_id;
        reloadPages();

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
    }

    protected void chooseFiles() throws SQLException, RemoteException, ForeignKeyViolationException {
        JFileChooser chooser =
                new JFileChooser(DashBoard.readProperty("imagedir", "./"));
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

            public boolean accept(File f) {
                boolean ok = f.isDirectory()
                        || f.getName().toLowerCase().endsWith("jpg")
                        || f.getName().toLowerCase().endsWith("png")
                        || f.getName().toLowerCase().endsWith("jpeg")
                        || f.getName().toLowerCase().endsWith("gif");
                return ok;
            }

            public String getDescription() {
                return "*.JPG ; *.GIF; *.PNG";
            }
        });
        chooser.setDialogTitle("Load Pictures");
        chooser.setApproveButtonText("Load");
        int retVal = chooser.showOpenDialog(null);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            processFiles(chooser.getSelectedFiles());
        }
    }

    protected abstract void reloadPages() throws RemoteException;

    protected abstract void processFiles(File[] files)
            throws SQLException, RemoteException, ForeignKeyViolationException;

    public void activatePopup(AbstractAction addAction) {
        pop = new JPopupMenu();
        pop.add(addAction);
        addMouseListener(new PopupListener(pop));
    }
}

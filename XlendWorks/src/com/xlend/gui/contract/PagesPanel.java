package com.xlend.gui.contract;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xcontractpage;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.NoFrameButton;
import com.xlend.util.PopupListener;
import com.xlend.util.Util;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Nick Mukhin
 */
public class PagesPanel extends JPanel {

    private ArrayList<NoFrameButton> btns = new ArrayList<NoFrameButton>();
    private final AbstractAction addAction;
    private JPopupMenu pop;
    private final IMessageSender exchanger;
    private int contract_id;

    public PagesPanel(IMessageSender exchanger, final int contract_id) throws RemoteException {
        super(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.exchanger = exchanger;
        this.contract_id = contract_id;
        reloadPages();

        activatePopup(addAction = new AbstractAction("Add image(s)") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
                        File[] files = chooser.getSelectedFiles();
                        int n = DashBoard.getExchanger().getDbObjects(Xcontractpage.class,
                                "xcontract_id=" + contract_id, null).length + 1;
                        for (File f : files) {
                            Xcontractpage contractPage = new Xcontractpage(null);
                            contractPage.setXcontractpageId(0);
                            contractPage.setXcontractId(contract_id);
                            contractPage.setDescription("Page " + n);
                            contractPage.setPagenum(n++);
                            contractPage.setPagescan(Util.readFile(f.getAbsolutePath()));
                            contractPage.setNew(true);
                            DbObject saved = DashBoard.getExchanger().saveDbObject(contractPage);
                        }
                        //GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect());
                        reloadPages();
                    }

                } catch (Exception ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        });
    }

    private void reloadPages() throws RemoteException {
        DbObject[] pages = exchanger.getDbObjects(Xcontractpage.class, "xcontract_id=" + contract_id, "pagenum");

        setVisible(false);
        removeAll();
        for (DbObject o : pages) {
            NoFrameButton btn;
            final Xcontractpage contractPage = (Xcontractpage) o;
            add(btn = new NoFrameButton(new ImageIcon("images/page.png")));
            btn.setText(contractPage.getDescription() == null ? "" : contractPage.getDescription().trim());
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

    public void activatePopup(AbstractAction addAction) {
        pop = new JPopupMenu();
        pop.add(addAction);
        addMouseListener(new PopupListener(pop));
    }
}

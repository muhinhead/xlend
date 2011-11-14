package com.xlend.gui.contract;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.WorkFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.orm.Picture;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xcontract;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.util.PopupListener;
import com.xlend.util.TexturedPanel;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField contractRefField;
    private JTextArea descriptionField;
    private JComboBox contractorBox;
//    private JTabbedPane detailsTab;
//    private JPanel imagePanel;
    private DefaultComboBoxModel cbModel;
    private JButton loadButton;
    private JPanel picPanel;
    private ImageIcon currentPicture;
    private JPopupMenu picturePopMenu;

    public EditContractPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Contract Ref:", "Client:", "Description:"
        };
        cbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllClients(DashBoard.getExchanger())) {
            cbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            contractRefField = new JTextField(),
            contractorBox = new JComboBox(cbModel),
            new JScrollPane(descriptionField = new JTextArea(5, 55),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
        };
        descriptionField.setWrapStyleWord(true);
        descriptionField.setLineWrap(true);
        organizePanels(labels, edits);
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new BorderLayout(5, 5));

        idField.setEnabled(false);

        JPanel upper = new JPanel(new BorderLayout(5, 5));
        JPanel uplabel = new JPanel(new GridLayout(4, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(4, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);
        for (int i = 0; i < 4; i++) {
            uplabel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }
        JPanel ided = new JPanel(new GridLayout(1, 3, 5, 5));
        ided.add(idField);
        for (int i = 0; i < 5; i++) {
            ided.add(new JPanel());
        }
        upedit.add(ided);
        upedit.add(edits[1]);
        upedit.add(edits[2]);

        form.add(upper, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[3], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.setTopComponent(edits[3]);
        sp.setBottomComponent(getScanPanel());
        form.add(sp);

        alignPanelOnWidth(leftpanel, uplabel);

        add(form);

    }

    @Override
    public void loadData() {
        Xcontract xcontract = (Xcontract) getDbObject();
        if (xcontract != null) {
            idField.setText(xcontract.getXcontractId().toString());
            contractRefField.setText(xcontract.getContractref());
            selectComboItem(contractorBox, xcontract.getXclientId());
            descriptionField.setText(xcontract.getDescription());
            if (xcontract.getPictureId() != null) {
                try {
                    Picture pic = (Picture) DashBoard.getExchanger().loadDbObjectOnID(Picture.class, xcontract.getPictureId());
                    currentPicture = new ImageIcon((byte[]) pic.getPicture());
                    setPhoto((byte[]) pic.getPicture());
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                }
            } else {
                noImage();
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xcontract xcontract = (Xcontract) getDbObject();
        boolean isNew = false;
        if (xcontract == null) {
            xcontract = new Xcontract(null);
            xcontract.setXcontractId(0);
            isNew = true;
        }
        xcontract.setContractref(contractRefField.getText());
        xcontract.setDescription(descriptionField.getText());
        ComboItem itm = (ComboItem) contractorBox.getSelectedItem();
        if (itm.getValue().startsWith("--")) { // add new client
            EditClientDialog ecd = new EditClientDialog("New Client", null);
            if (EditClientDialog.okPressed) {
                Xclient cl = (Xclient) ecd.getEditPanel().getDbObject();
                if (cl != null) {
                    ComboItem ci = new ComboItem(cl.getXclientId(), cl.getCompanyname());
                    cbModel.addElement(ci);
                    contractorBox.setSelectedItem(ci);
                }
            }
        } else {
            try {
                xcontract.setXclientId(itm.getId());
                xcontract.setNew(isNew);
                DbObject saved = DashBoard.getExchanger().saveDbObject(xcontract);
                setDbObject(saved);
                return true;
            } catch (Exception ex) {
                WorkFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;

    }

    private JButton getLoadPictureButton() {
        loadButton = new JButton("Choose picture...");
        loadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadDocImageFromFile();
            }
        });
        return loadButton;
    }

    private void loadDocImageFromFile() {
        JFileChooser chooser =
                new JFileChooser(DashBoard.readProperty("imagedir", "./"));
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
        chooser.setDialogTitle("Import Picture");
        chooser.setApproveButtonText("Import");
        int retVal = chooser.showOpenDialog(null);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getAbsolutePath();
            byte[] imageData = Util.readFile(name);
            Xcontract xcontract = (Xcontract) getDbObject();
            if (xcontract == null) {
                try {
                    save();
                    xcontract = (Xcontract) getDbObject();
                    loadData();
                } catch (Exception ex) {
                    XlendWorks.log(ex);
                }
            }
            try {
                Picture picture = new Picture(null);
                picture.setPictureId(0);
                picture.setPicture(imageData);
                picture.setNew(true);
                DbObject saved = DashBoard.getExchanger().saveDbObject(picture);
                picture = (Picture) saved;
                xcontract.setPictureId(picture.getPictureId());
                setPhoto(imageData);
            } catch (Exception ex) {
                XlendWorks.log(ex);
            }
        }
    }

    private JTabbedPane getScanPanel() {
        JTabbedPane tp = new JTabbedPane();
        picPanel = new JPanel();//new BorderLayout());
        JPanel insPanel = new JPanel();
        insPanel.add(getLoadPictureButton());
        picPanel.add(insPanel);

        tp.add(new JScrollPane(picPanel), "Scanned image");
        return tp;
    }

    private void setPhoto(byte[] imgData) {//ImageIcon img) {
        String tmpImgFile = "$$$.img";
        currentPicture = new ImageIcon(imgData);
        Dimension d = picPanel.getSize();
        picPanel.setVisible(false);
        picPanel.removeAll();
        JScrollPane sp = null;
        int height = 1;
        int wscale = 1;
        int hscale = 1;
        int width = 0;
        Util.writeFile(new File(tmpImgFile), imgData);
        width = currentPicture.getImage().getWidth(null);
        height = currentPicture.getImage().getHeight(null);
        wscale = width / (d.width - 70);
        hscale = height / (d.height - 70);
        wscale = wscale <= 0 ? 1 : wscale;
        hscale = hscale <= 0 ? 1 : hscale;
        int scale = wscale < hscale ? wscale : hscale;
        StringBuffer html = new StringBuffer("<html>");
        html.append("<img margin=20 src='file:" + tmpImgFile + "' "
                + "width=" + width / scale + " height=" + height / scale
                + "></img>");
        JEditorPane ed = new JEditorPane("text/html", html.toString());
        ed.setEditable(false);
        picPanel.add(sp = new JScrollPane(ed), BorderLayout.CENTER);
        picPanel.setVisible(true);
        ed.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewDocumentImage();
                }
            }
        });
        ed.addMouseListener(new PopupListener(getPhotoPopupMenu()));
        new File(tmpImgFile).deleteOnExit();
    }

    private void viewDocumentImage() {
        JDialog dlg = new JDialog();
        dlg.setModal(true);
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(new JScrollPane(new JLabel(currentPicture)),
                BorderLayout.CENTER);
        dlg.setContentPane(pane);
        dlg.pack();
        dlg.setVisible(true);
    }

    private void noImage() {
        picPanel.setVisible(false);
        picPanel.removeAll();
        JPanel insPanel = new JPanel();
        insPanel.add(getLoadPictureButton());
        picPanel.add(insPanel);
        picPanel.setVisible(true);
        currentPicture = null;
    }

    private JPopupMenu getPhotoPopupMenu() {
        if (null == picturePopMenu) {
            picturePopMenu = new JPopupMenu();
            picturePopMenu.add(new AbstractAction("Open in window") {

                public void actionPerformed(ActionEvent e) {
                    viewDocumentImage();
                }
            });
            picturePopMenu.add(new AbstractAction("Replace image") {

                public void actionPerformed(ActionEvent e) {
                    loadDocImageFromFile();
                }
            });
            picturePopMenu.add(new AbstractAction("Save image to file") {

                public void actionPerformed(ActionEvent e) {
                    try {
                        Xcontract xcontract = (Xcontract) getDbObject();
                        Picture pic = (Picture) DashBoard.getExchanger().loadDbObjectOnID(Picture.class, xcontract.getPictureId());
                        exportDocImage((byte[]) pic.getPicture());
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                    }
                }
            });
            picturePopMenu.add(new AbstractAction("Remove image from DB") {

                public void actionPerformed(ActionEvent e) {
                    Xcontract xcontract = (Xcontract) getDbObject();
                    try {
                        Integer pictureId = xcontract.getPictureId();
                        xcontract.setPictureId(null);
                        Picture pic = (Picture) DashBoard.getExchanger().loadDbObjectOnID(Picture.class, pictureId);
                        //DashBoard.getExchanger().deleteObject(pic);
                        loadData();

                    } catch (Exception ex) {
                        XlendWorks.log(ex);
                    }
                }
            });
        }
        return picturePopMenu;
    }

    public static void exportDocImage(byte[] imageData) {
        JFileChooser chooser =
                new JFileChooser(DashBoard.readProperty("imagedir", "./"));
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

            public boolean accept(File f) {
                boolean ok = f.isDirectory()
                        || f.getName().toLowerCase().endsWith("jpg")
                        || f.getName().toLowerCase().endsWith("jpeg")
                        || f.getName().toLowerCase().endsWith("gif");

                return ok;
            }

            public String getDescription() {
                return "*.JPG ; *.GIF";
            }
        });
        chooser.setDialogTitle("Save image to file");
        chooser.setApproveButtonText("Save");
        int retVal = chooser.showOpenDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getAbsolutePath();
            name = (name.toLowerCase().endsWith(".jpg") ? name : name + ".jpg");
            File fout = new File(name);
            if (fout.exists()) {
                if (WorkFrame.yesNo("Attention",
                        "File " + name + " already exists, rewrite?") != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            Util.writeFile(fout, imageData);
        }
    }
}

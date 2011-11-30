package com.xlend.gui;

import com.xlend.orm.IPage;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.PopupListener;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
public abstract class EditPagePanel extends RecordEditPanel {

    protected JTextField idField;
    protected JTextField pageNumField;
    protected JTextArea descriptionField;
    protected JScrollPane sp;
    private JPanel picPanel;
    private JButton loadButton;
    private ImageIcon currentPicture;
    private JPopupMenu picturePopMenu;

    public EditPagePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{"Id:", "Index No:", "Description:"};
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            pageNumField = new JTextField(),
            sp = new JScrollPane(descriptionField = new JTextArea(3, 30),
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
        JPanel uplabel = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(2, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);

        uplabel.add(new JLabel(labels[0], SwingConstants.RIGHT));
        uplabel.add(new JLabel(labels[1], SwingConstants.RIGHT));

        JPanel ided = new JPanel(new GridLayout(1, 3, 5, 5));
        ided.add(idField);
        ided.add(new JPanel());
        ided.add(new JPanel());
        upedit.add(ided);
        upedit.add(pageNumField);

        JPanel half = new JPanel(new GridLayout(1,2));
        half.add(upper);
        half.add(new JPanel());
        form.add(half, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[2], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setTopComponent(edits[2]);
        split.setBottomComponent(getPicturesPanel());
//        form.add(edits[2]);
        form.add(split);

        alignPanelOnWidth(leftpanel, uplabel);

        add(form);
    }

//    @Override
//    public void loadData() {
//        Xcontractpage contrpage = (Xcontractpage) getDbObject();
//        if (contrpage.getXcontractpageId() != null) {
//            idField.setText(contrpage.getXcontractId().toString());
//            pageNumField.setText(contrpage.getPagenum().toString());
//            descriptionField.setText(contrpage.getDescription());
//            if (contrpage.getPagescan() != null) {
//                setPhoto((byte[]) contrpage.getPagescan());
//            }
//        }
//    }

//    @Override
//    public boolean save() throws Exception {
//        Xcontractpage contrpage = (Xcontractpage) getDbObject();
//        boolean isNew = false;
//        if (contrpage.getXcontractpageId() == null) {
//            contrpage.setXcontractpageId(0);
//            isNew = true;
//        }
//        contrpage.setPagenum(Integer.parseInt(pageNumField.getText()));
//        contrpage.setDescription(descriptionField.getText());
//        try {
//            contrpage.setNew(isNew);
//            DbObject saved = DashBoard.getExchanger().saveDbObject(contrpage);
//            setDbObject(saved);
//            return true;
//        } catch (Exception ex) {
//            GeneralFrame.errMessageBox("Error:", ex.getMessage());
//        }
//        return false;
//    }

    private JComponent getPicturesPanel() {
        picPanel = new JPanel();
        JPanel insPanel = new JPanel();
        insPanel.add(getLoadPictureButton());
        picPanel.add(insPanel);
        JScrollPane scroll = new JScrollPane(picPanel);
        scroll.setPreferredSize(new Dimension(600, 300));
        return scroll;
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
            setImage(Util.readFile(chooser.getSelectedFile().getAbsolutePath()));
        }
    }

    private void setImage(byte[] imageData) {
        IPage page = (IPage) getDbObject();
        try {
            page.setPagescan(imageData);
            setPhoto(imageData);
        } catch (Exception ex) {
            XlendWorks.log(ex);
        }
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

    protected void setPhoto(byte[] imgData) {
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
                    IPage page = (IPage) getDbObject();
                    exportDocImage((byte[]) page.getPagescan());
                }
            });
            picturePopMenu.add(new AbstractAction("Remove image from DB") {

                public void actionPerformed(ActionEvent e) {
                    IPage page = (IPage) getDbObject();
                    try {
                        page.setPagescan(null);
                        noImage();
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
                if (GeneralFrame.yesNo("Attention",
                        "File " + name + " already exists, rewrite?") != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            Util.writeFile(fout, imageData);
        }
    }
    
}

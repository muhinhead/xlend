/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui;

import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.FileFilterOnExtension;
import com.xlend.util.PopupListener;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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

/**
 *
 * @author nick
 */
public abstract class EditPanelWithPhoto extends RecordEditPanel {

    private ImageIcon currentPicture;
    protected JPanel picPanel;
    protected JPopupMenu picturePopMenu;
    protected byte[] imageData;

    public EditPanelWithPhoto(DbObject dbObject) {
        super(dbObject);
    }

    protected static void exportDocImage(byte[] imageData) {
        JFileChooser chooser = new JFileChooser(DashBoard.readProperty("imagedir", "./"));
        chooser.setFileFilter(new FileFilterOnExtension("jpg"));
        chooser.setDialogTitle("Save photo to file");
        chooser.setApproveButtonText("Save");
        int retVal = chooser.showOpenDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getAbsolutePath();
            File fout = new File(name);
            if (fout.exists()) {
                if (GeneralFrame.yesNo("Attention", "File " + name
                        + " already exists, rewrite?") != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            Util.writeFile(fout, imageData);
        }
    }

    private JPopupMenu getPhotoPopupMenu() {
        if (null == picturePopMenu) {
            picturePopMenu = new JPopupMenu();
            picturePopMenu.add(new AbstractAction("Open in window") {
                public void actionPerformed(ActionEvent e) {
                    viewDocumentImage(currentPicture);
                }
            });
            picturePopMenu.add(new AbstractAction("Replace image") {
                public void actionPerformed(ActionEvent e) {
                    loadDocImageFromFile();
                }
            });
            picturePopMenu.add(new AbstractAction("Save image to file") {
                public void actionPerformed(ActionEvent e) {
                    exportDocImage(imageData);
                }
            });
            picturePopMenu.add(new AbstractAction("Remove image from DB") {
                public void actionPerformed(ActionEvent e) {
                    noImage();
                }
            });
        }
        return picturePopMenu;
    }

    @Override
    protected JComponent getRightUpperPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        if (picPanel == null) {
            rightPanel.add(getPicPanel(), BorderLayout.CENTER);
            if (getImagePanelLabel() != null) {
                picPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(), getImagePanelLabel()));
            }
            picPanel.setPreferredSize(new Dimension(500, picPanel.getPreferredSize().height));
        }
        return rightPanel;
    }

    public JPanel getPicPanel() {
        if (picPanel == null) {
            picPanel = new JPanel(new BorderLayout());
            noImage();
        }
        return picPanel;
    }

    private JButton getLoadPictureButton() {
        JButton loadButton = new JButton("Choose picture...");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDocImageFromFile();
            }
        });
        return loadButton;
    }

    private void loadDocImageFromFile() {
        JFileChooser chooser = new JFileChooser(DashBoard.readProperty("imagedir", "./"));
        chooser.setFileFilter(new PagesPanel.PagesDocFileFilter());
        chooser.setDialogTitle("Import File");
        chooser.setApproveButtonText("Import");
        int retVal = chooser.showOpenDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            setImage(Util.readFile(f.getAbsolutePath()));
        }
    }

    private void noImage() {
        imageData = null;
        picPanel.setVisible(false);
        picPanel.removeAll();
        JPanel insPanel = new JPanel();
        insPanel.add(getLoadPictureButton());
        picPanel.add(insPanel);
        picPanel.setVisible(true);
        currentPicture = null;
    }

    protected void setImage(byte[] imageData) {
        this.imageData = imageData;
        if (imageData != null) {
            setPhoto();
        }
    }

    private void setPhoto() {
        picPanel.setVisible(false);
        picPanel.removeAll();
        String tmpImgFile = "$$$.img";
        currentPicture = new ImageIcon(imageData);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension d = new Dimension(screenSize.width / 3, screenSize.height / 3);
        JScrollPane sp = null;
        int height = 1;
        int wscale = 1;
        int hscale = 1;
        int width = 0;
        Util.writeFile(new File(tmpImgFile), imageData);
        width = currentPicture.getImage().getWidth(null);
        height = currentPicture.getImage().getHeight(null);
        wscale = width / (d.width - 70);
        hscale = height / (d.height - 70);
        wscale = wscale <= 0 ? 1 : wscale;
        hscale = hscale <= 0 ? 1 : hscale;
        int scale = wscale < hscale ? wscale : hscale;
        StringBuffer html = new StringBuffer("<html>");
        html.append("<img margin=20 src='file:" + tmpImgFile + "' " + "width="
                + width / scale + " height=" + height / scale + "></img>");
        JEditorPane ed = new JEditorPane("text/html", html.toString());
        ed.setEditable(false);
        picPanel.add(sp = new JScrollPane(ed), BorderLayout.CENTER);
        sp.setPreferredSize(new Dimension(300, 250));
        ed.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewDocumentImage(currentPicture);
                }
            }
        });
        ed.addMouseListener(new PopupListener(getPhotoPopupMenu()));
        new File(tmpImgFile).deleteOnExit();
        picPanel.setVisible(true);
    }

    protected void viewDocumentImage(ImageIcon curPic) {
        JDialog dlg = new JDialog();
        dlg.setModal(true);
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(new JScrollPane(new JLabel(curPic)), BorderLayout.CENTER);
        dlg.setContentPane(pane);
        dlg.pack();
        dlg.setVisible(true);
    }

    protected String getImagePanelLabel() {
        return "Photo";
    }
}

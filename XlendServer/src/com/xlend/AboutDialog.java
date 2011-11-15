package com.xlend;

import com.xlend.util.ImagePanel;
import com.xlend.util.PopupDialog;
import com.xlend.util.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class AboutDialog extends PopupDialog {

    private static final String BACKGROUNDIMAGE = "about.png";

    public AboutDialog() {
        super(null, "Xcost Server", null);
        XlendServer.setWindowIcon(this, "Xcost.png");

    }

    protected void fillContent() {
        super.fillContent();
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        getContentPane().add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(XlendServer.loadImage(BACKGROUNDIMAGE));
        this.setMinimumSize(new Dimension(img.getWidth(), img.getHeight()+70));
        JButton okBtn = new JButton(new AbstractAction("Close"){

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okBtn.setBounds(290,170,okBtn.getPreferredSize().width, okBtn.getPreferredSize().height);
        main.add(okBtn);
        setResizable(false);
    }

    @Override
    public void freeResources() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}

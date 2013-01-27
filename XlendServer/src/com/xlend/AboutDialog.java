package com.xlend;

import com.xlend.util.ImagePanel;
import com.xlend.util.PopupDialog;
import com.xlend.util.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class AboutDialog extends PopupDialog {
    
    private static final String BACKGROUNDIMAGE = "about.png";
    private AbstractAction closeAction;
    private JButton closeBtn;
    
    public AboutDialog() {
        super(null, "Xcost Server", null);
        XlendServer.setWindowIcon(this, "Xcost.png");        
    }
    
    protected void fillContent() {
        super.fillContent();
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        getContentPane().add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(XlendServer.loadImage(BACKGROUNDIMAGE));
        this.setMinimumSize(new Dimension(img.getWidth(), img.getHeight() + 70));
        closeBtn = new JButton(closeAction = new AbstractAction("Close") {
            
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        JLabel version = new JLabel("Version "+XlendServer.getVersion());
        version.setBounds(270, 10, version.getPreferredSize().width, version.getPreferredSize().height);
        main.add(version);
        
        JLabel devBy = new JLabel("Nick Mukhin (mukhin.nick@gmail.com) (c) 2013");
        devBy.setBounds(41, 110, devBy.getPreferredSize().width, devBy.getPreferredSize().height);
        main.add(devBy);
        
        closeBtn.setBounds(290, 170,
                closeBtn.getPreferredSize().width,
                closeBtn.getPreferredSize().height);
        
        main.add(closeBtn);
        setResizable(false);
    }
    
    @Override
    public void freeResources() {
        closeBtn.removeActionListener(closeAction);
        closeAction = null;
    }
}

package com.xlend.gui;

import com.xlend.remote.IMessageSender;
import com.xlend.util.ImagePanel;
import com.xlend.util.PopupDialog;
import com.xlend.util.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author Nick Mukhin
 */
public class LoginImagedDialog extends PopupDialog {

    private static final String BACKGROUNDIMAGE = "Login.png";
    private JPanel controlsPanel;
    private JComboBox loginField;
    private JPasswordField pwdField;
    private static IMessageSender exchanger;

    public LoginImagedDialog(Object obj) {
        super(null, "Login", obj);
    }

    @Override
    protected void fillContent() {
//        new Object[]{loginField, pwdField, exchanger}
        Object[] obs = (Object[]) getObject();
        loginField = (JComboBox) obs[0];
        pwdField = (JPasswordField) obs[1];
        //loginField = new JComboBox(XlendWorks.loadAllLogins(DashBoard.getExchanger()));
        controlsPanel = new JPanel(new BorderLayout());
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        controlsPanel.add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(XlendWorks.loadImage(BACKGROUNDIMAGE, this));
        addNotify();
        Insets insets = getInsets();
        int dashWidth = img.getWidth();
        int dashHeight = img.getHeight();
        int yShift = 20;
        int xShift = 10;
        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right, dashHeight + insets.top + insets.bottom));
        LayerPanel layers = new LayerPanel();
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);
        getContentPane().add(layers, BorderLayout.CENTER);

        loginField.setBounds(250, 262, 180, 27);
        main.add(loginField);
        pwdField.setBounds(250, 290, 180, 27);
        main.add(pwdField);
        
        setResizable(false);
    }

    @Override
    public void freeResources() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class LayerPanel extends JLayeredPane {

        private LayerPanel() {
            super();
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            controlsPanel.setBounds(getBounds());
        }
    }
}

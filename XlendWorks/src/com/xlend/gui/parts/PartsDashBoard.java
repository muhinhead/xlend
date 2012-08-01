package com.xlend.gui.parts;

import com.xlend.gui.AbstractDashBoard;
import com.xlend.remote.IMessageSender;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author Nick
 */
public class PartsDashBoard extends AbstractDashBoard {
    private static final String BACKGROUNDIMAGE = "partsdashboard.png";
    
    public PartsDashBoard(JFrame parentFrame) {
        super("Parts");
        setVisible(true);
        setResizable(false);
    }

    @Override
    protected void fillControlsPanel() throws HeadlessException {
        //centerOnScreen();
    }

    @Override
    protected String getBackGroundImage() {
        return BACKGROUNDIMAGE;
    }

    @Override
    public void lowLevelInit() {
    }
}

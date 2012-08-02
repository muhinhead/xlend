package com.xlend.gui.parts;

import com.xlend.gui.AbstractDashBoard;
import com.xlend.gui.DashBoard;
import com.xlend.gui.XlendWorks;
import com.xlend.util.ImagePanel;
import com.xlend.util.ToolBarButton;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Nick
 */
public class PartsDashBoard extends AbstractDashBoard {

    private static PartsDashBoard instance = null;
    private static final String BACKGROUNDIMAGE = "partsdashboard.png";
    private ToolBarButton machinesButton;
    private ToolBarButton trucksButton;
    private ToolBarButton vehiclesButton;
    private ToolBarButton miscButton;
    private ToolBarButton liquidsButton;
    private PartsCategoriesFrame machinePartsFrame;

    public PartsDashBoard(JFrame parentFrame) {
        super("Parts");
        instance = this;
        setVisible(true);
        setResizable(false);
    }

    @Override
    protected void fillControlsPanel() throws HeadlessException {
        centerOnScreen();

        ImagePanel img = new ImagePanel(XlendWorks.loadImage("machines.png", this));
        machinesButton = new ToolBarButton("machines.png");
        machinesButton.setBounds(24, 20, img.getWidth(), img.getHeight());
        main.add(machinesButton);

        img = new ImagePanel(XlendWorks.loadImage("trucks.png", this));
        trucksButton = new ToolBarButton("trucks.png");
        trucksButton.setBounds(140, 20, img.getWidth(), img.getHeight());
        main.add(trucksButton);

        img = new ImagePanel(XlendWorks.loadImage("vehicles.png", this));
        vehiclesButton = new ToolBarButton("vehicles.png");
        vehiclesButton.setBounds(254, 20, img.getWidth(), img.getHeight());
        main.add(vehiclesButton);

        img = new ImagePanel(XlendWorks.loadImage("misc.png", this));
        miscButton = new ToolBarButton("misc.png");
        miscButton.setBounds(368, 20, img.getWidth(), img.getHeight());
        main.add(miscButton);

        img = new ImagePanel(XlendWorks.loadImage("liquids.png", this));
        liquidsButton = new ToolBarButton("liquids.png");
        liquidsButton.setBounds(482, 20, img.getWidth(), img.getHeight());
        main.add(liquidsButton);

        machinesButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
//                if (machinePartsFrame == null) {
//                    machinePartsFrame = new PartsCategoriesFrame("Machines parts", DashBoard.getExchanger(), "machines");
//                } else {
//                    try {
//                        machinePartsFrame.setLookAndFeel(DashBoard.readProperty("LookAndFeel",
//                                UIManager.getSystemLookAndFeelClassName()));
//                    } catch (Exception ex) {
//                    }
//                    machinePartsFrame.setVisible(true);
//                }
            }
        });
    }

    @Override
    protected String getBackGroundImage() {
        return BACKGROUNDIMAGE;
    }

    @Override
    public void lowLevelInit() {
    }

    @Override
    public void dispose() {
        instance = null;
        super.dispose();
    }

    /**
     * @return the instance
     */
    public static PartsDashBoard getInstance() {
        return instance;
    }
}

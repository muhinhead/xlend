package com.xlend.gui.parts;

import com.xlend.gui.AbstractDashBoard;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xpartcategory;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.ImagePanel;
import com.xlend.util.ToolBarButton;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
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
    private static String[] sheetList = new String[]{
        "Machines", "Trucks", "Vehicles", "Misc", "Liquids"
    };

    public static String[] sheets() {
        return sheetList;
    }
    private ToolBarButton machinesButton;
    private ToolBarButton trucksButton;
    private ToolBarButton vehiclesButton;
    private ToolBarButton miscButton;
    private ToolBarButton liquidsButton;
    private PartsCategoriesFrame machinePartsFrame;
    private PartsCategoriesFrame trucksPartsFrame;
    private PartsCategoriesFrame vehiclesePartsFrame;
    private PartsCategoriesFrame miscPartsFrame;
    private PartsCategoriesFrame liquidsPartsFrame;

    public PartsDashBoard(JFrame parentFrame) {
        super("Parts", true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        instance = this;
        setVisible(true);
        setResizable(false);
//        setUndecorated(false);
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
                if (XlendWorks.availableForCurrentUser(sheets()[0])) {
                    if (machinePartsFrame == null) {
                        machinePartsFrame = createCategoriesFrame(1, "machines");
                    } else {
                        showPartsCategoriesFrame(machinePartsFrame);
                    }
                } else {
                    GeneralFrame.infoMessageBox("Sorry!", "Access denied to module " + sheets()[0]);
                }
            }
        });

        trucksButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (XlendWorks.availableForCurrentUser(sheets()[1])) {
                    if (trucksPartsFrame == null) {
                        trucksPartsFrame = createCategoriesFrame(2, "trucks");
                    } else {
                        showPartsCategoriesFrame(trucksPartsFrame);
                    }
                } else {
                    GeneralFrame.infoMessageBox("Sorry!", "Access denied to module " + sheets()[1]);
                }
            }
        });

        vehiclesButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (XlendWorks.availableForCurrentUser(sheets()[2])) {
                    if (vehiclesePartsFrame == null) {
                        vehiclesePartsFrame = createCategoriesFrame(3, "vehicles");
                    } else {
                        showPartsCategoriesFrame(vehiclesePartsFrame);
                    }
                } else {
                    GeneralFrame.infoMessageBox("Sorry!", "Access denied to module " + sheets()[2]);
                }
            }
        });

        miscButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (XlendWorks.availableForCurrentUser(sheets()[3])) {
                    if (miscPartsFrame == null) {
                        miscPartsFrame = createCategoriesFrame(4, "misc");
                    } else {
                        showPartsCategoriesFrame(miscPartsFrame);
                    }
                } else {
                    GeneralFrame.infoMessageBox("Sorry!", "Access denied to module " + sheets()[3]);
                }
            }
        });

        liquidsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (XlendWorks.availableForCurrentUser(sheets()[4])) {
                    if (liquidsPartsFrame == null) {
                        liquidsPartsFrame = createCategoriesFrame(5, "liquids");
                    } else {
                        showPartsCategoriesFrame(liquidsPartsFrame);
                    }
                } else {
                    GeneralFrame.infoMessageBox("Sorry!", "Access denied to module " + sheets()[4]);
                }
            }
        });
    }

    private static PartsCategoriesFrame createCategoriesFrame(int group_id, String name) {
        PartsCategoriesFrame categoriesFrame = null;
        try {
            DbObject[] obs = XlendWorks.getExchanger().getDbObjects(Xpartcategory.class, "group_id=" + group_id + " and parent_id is null", null);
            Xpartcategory xpc = (Xpartcategory) obs[0];
            categoriesFrame = new PartsCategoriesFrame(xpc.getName(), XlendWorks.getExchanger(), name);
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
        }
        return categoriesFrame;
    }

    private static void showPartsCategoriesFrame(PartsCategoriesFrame partsFrame) {
        try {
            partsFrame.setLookAndFeel(DashBoard.readProperty("LookAndFeel",
                    UIManager.getSystemLookAndFeelClassName()));
        } catch (Exception ex) {
        }
        partsFrame.setVisible(true);
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
//    private ActionListener lackOfPermissionsAction() {
//        return new AbstractAction() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GeneralFrame.infoMessageBox("Sorry!", "Access denied");
//            }
//        };
//    }
}

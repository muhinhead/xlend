package com.xlend.gui;

import com.xlend.remote.IMessageSender;
import java.util.Properties;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class HRFrame extends GeneralFrame {
    public HRFrame(IMessageSender exch) {
        super("HR", exch);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane hrTab = new JTabbedPane();
        hrTab.add(new JPanel(), "Employee Files");
        hrTab.add(new JPanel(), "Wages");
        hrTab.add(new JPanel(), "Salaries");
        hrTab.add(new JPanel(), "Diciplinary Actions");
        hrTab.add(new JPanel(), "Rewards Program");
        return hrTab;
    }
}

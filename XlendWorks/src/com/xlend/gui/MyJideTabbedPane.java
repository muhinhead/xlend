package com.xlend.gui;

import com.jidesoft.swing.JideTabbedPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author Nick Mukhin
 */
public class MyJideTabbedPane extends JideTabbedPane {

    public MyJideTabbedPane() {
        super(JideTabbedPane.TOP);
        setShowTabButtons(true);
        setBoldActiveTab(true);
        setColorTheme(JideTabbedPane.COLOR_THEME_OFFICE2003);
        setTabShape(JideTabbedPane.SHAPE_BOX);
    }

    public void addTab(JComponent comp, String title, Icon icon) {
        if (icon == null) {
            super.add(comp, title);
        } else {
            super.addTab(title, icon, comp);
        }
    }

    public void addTab(JComponent comp, String title) {
        addTab(comp, title, new ImageIcon(XlendWorks.loadImage("xlendfolder.jpg", DashBoard.ourInstance)));
    }
}
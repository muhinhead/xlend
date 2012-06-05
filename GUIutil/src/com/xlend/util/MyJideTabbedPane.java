package com.xlend.util;

import com.jidesoft.swing.JideTabbedPane;

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
    
}

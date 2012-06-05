package com.xlend.util;

import com.jidesoft.swing.JideTabbedPane;
import java.awt.Component;
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
        setTabShape(JideTabbedPane.SHAPE_EXCEL);
    }
    
}

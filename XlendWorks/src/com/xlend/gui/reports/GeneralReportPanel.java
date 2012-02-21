package com.xlend.gui.reports;

import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public abstract class GeneralReportPanel extends JPanel {
    protected static IMessageSender exchanger;
    
    public GeneralReportPanel(IMessageSender exchanger) {
        super(new BorderLayout());
        setExchanger(exchanger);
    }

    protected void setExchanger(IMessageSender exchanger) {
        this.exchanger = exchanger;
    }

    public abstract void updateReport();
}

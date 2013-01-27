package com.xlend.gui.reports;

import com.xlend.remote.IMessageSender;
import javax.swing.JEditorPane;

/**
 *
 * @author Nick Mukhin
 */
public class IncidentsReport extends GeneralReportPanel {

    public IncidentsReport(IMessageSender exchanger) {
        super(exchanger);
    }
    
    @Override
    protected JEditorPane createEditorPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

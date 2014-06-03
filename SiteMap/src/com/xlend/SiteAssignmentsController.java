package com.xlend;

import com.xlend.fx.util.Utils;
import com.xlend.orm.Xsite;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Nick Mukhin
 */
public class SiteAssignmentsController implements Initializable {

    private static final String SELECT_SITE_ASSIGNMENTS =
            "select to_char(date_start,'DD/MM/YYYY') \"Start Date\", "
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xopmachassing.xmachine_id) \"Machine/Truck\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xopmachassing.xemployee_id) \"Operator\" "
            + "from xopmachassing where xsite_id=# and date_end is null order by xmachine_id";
    @FXML
    private TextArea textArea;
    static SiteAssignmentsController instance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;

    }

    @FXML
    public void handleCancelAction(ActionEvent event) {
        SiteAssignmentsDialog.instanceStage.close();
    }

    public void populateControls(Integer siteID) {
        try {
            Xsite site = (Xsite) SiteMap.getExchanger().loadDbObjectOnID(Xsite.class, siteID);//new Xsite(SiteMap.getConnection()).loadOnId(siteID);
            int machines = SiteMap.getExchanger().getCount(
                    "select 1 from xopmachassing where date_end is null and xsite_id="
                    + + siteID + " and xmachine_id in(select xmachine_id from xmachine where classify='M')");
//                    Utils.getCount(SiteMap.getConnection(),
//                    "select 1 from xopmachassing where date_end is null and xsite_id="
//                    + siteID + " and xmachine_id in(select xmachine_id from xmachine where classify='M')");
            int trucks = SiteMap.getExchanger().getCount(
                    "select 1 from xopmachassing where date_end is null and xsite_id="
                    + siteID + " and xmachine_id in(select xmachine_id from xmachine where classify='T')");
            int personnel = SiteMap.getExchanger().getCount(
                    "select 1 from xopmachassing where date_end is null and xsite_id="
                    + siteID + " and not xemployee_id is null");

            textArea.setText("Site Name:        " + site.getName() + "\n"
                    + "No of Machines:   " + machines + "\n"
                    + "No of Trucks:     " + trucks + "\n"
                    + "No of Persdonnel: " + personnel + "\n\n"
                    + "Details\n\n"
                    + showAssignmentsDetails(siteID));

        } catch (Exception ex) {
            Utils.logAndShowMessage(ex);
        }
    }

    private String rpad(String s, int len, char sym) {
        for (int i = s.length(); i < len; i++) {
            s += sym;
        }
        return s;
    }

    private String showAssignmentsDetails(Integer siteID) throws Exception {
        Vector[] answer = SiteMap.getExchanger().getTableBody(SELECT_SITE_ASSIGNMENTS.replaceAll("#", siteID.toString()));
        StringBuilder sb = new StringBuilder("");
        Vector headers = answer[0];
        Vector lines = answer[1];
        int columns[] = new int[headers.size()];
        for (int r = 0; r < headers.size(); r++) {
            columns[r] = headers.get(r).toString().length() + 1;
        }
        for (int l = 0; l < lines.size(); l++) {
            Vector line = (Vector) lines.get(l);
            for (int r = 0; r < headers.size(); r++) {
                int len = line.get(r).toString().length() + 1;
                columns[r] = columns[r] < len ? len : columns[r];
            }
        }
        for (int r = 0; r < headers.size(); r++) {
            sb.append(rpad(headers.get(r).toString(), columns[r], ' '));
        }
        sb.append("\n");
        for (int r = 0; r < headers.size(); r++) {
            sb.append(rpad("-", columns[r] - 1, '-')).append(' ');
        }
        sb.append("\n");
        for (int l = 0; l < lines.size(); l++) {
            Vector line = (Vector) lines.get(l);
            for (int r = 0; r < headers.size(); r++) {
                sb.append(rpad(line.get(r).toString(), columns[r], ' '));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

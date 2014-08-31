package com.xlend.fx.admin;

import com.xlend.fx.util.RecEditPanel;
import com.xlend.fx.util.Utils;
import com.xlend.orm.dbobject.DbObject;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author Nick Mukhin
 */
class AdminEditPanel extends RecEditPanel {

    private TextField idField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField address1Field;
    private TextField address2Field;
    private TextField cityField;
    private ComboBox<String> stateBox;
    private TextField zipCodeField;
    private TextField phoneField;
    private TextField cPhoneField;
    private TextField emailField;

    public AdminEditPanel(DbObject obj) {
        super(obj);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{"ID:",
            "First Name:", "Last Name:", "Address (line1):", "Address (line2):", "City:",
            "Province:", "Zip Code:", "Phone:", "Cell Phone:", "E-mail:"
        };
        Node[] edits = new Node[]{
            idField = new TextField(),
            firstNameField = new TextField(),
            lastNameField = new TextField(),
            address1Field = new TextField(),
            address2Field = new TextField(),
            cityField = new TextField(),
            stateBox = new ComboBox<String>(),
            zipCodeField = new TextField(),
            phoneField = new TextField(),
            cPhoneField = new TextField(),
            emailField = new TextField()
        };
        idField.setEditable(false);
        stateBox.setItems(FXCollections.observableArrayList(new String[]{
            "Gauteng", "Freestate", "North-West",
            "Limpopo", "Mpumalanga", "Kwazulu-Natal", "Northern Cape",
            "Eastern Cape", "Western Cape"
        }));
        organizePanels(labels, edits);
        
    }

    @Override
    protected void organizePanels(String[] titles, Node[] edits) {
        organizePanels(edits.length);
        labels = createLabelsArray(titles);
        for (int i = 0; i < edits.length && i < titles.length; i++) {
            editPanel.add(labels[i], 0, i);
//            if (i == 0 || i == 7) {
//                FlowPane flow = new FlowPane();
//                flow.getChildren().add(edits[i]);
//                editPanel.add(flow, 1, i);
//            } else {
                editPanel.add(edits[i], 1, i, 3, 1);
//            }
        }
        GridPane rightPane = new GridPane();
        rightPane.add(new Label("Login:"),0,0);
        rightPane.add(new TextField(),1,0);
        rightPane.add(new Label("Password:"),0,1);
        rightPane.add(new TextField(),1,1);
        rightPane.add(new Label("Fax:"),0,2);
        rightPane.add(new TextField(),1,2);
        rightPane.add(new Label("Web address:"),0,3);
        rightPane.add(new TextField(),1,3);
        rightUpperPanel.setCenter(rightPane);
    }
    

    @Override
    public void loadData() {
        //TODO
    }

    @Override
    public boolean save() throws Exception {
        //TODO
        return true;
    }
}

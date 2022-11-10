package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ModifyPartController implements Initializable {

    public RadioButton inHouseRadio;
    public ToggleGroup partTypeToggle;
    public RadioButton outsourcedRadio;
    public TextField partIdField;
    public TextField partNameField;
    public TextField partInvField;
    public TextField partCostField;
    public TextField partMaxField;
    public TextField partMachineCustomer;
    public TextField partMinField;
    public Label machineCustomerLabel;
    private Part partSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partSelected = MainController.getPartToModify();
        if (partSelected instanceof InHouse) {
            inHouseRadio.setSelected(true);
            partNameField.setText(String.valueOf(((InHouse) partSelected).getMachineId()));
        }
        if (partSelected instanceof Outsourced) {
            outsourcedRadio.setSelected(true);
            partNameField.setText(((Outsourced) partSelected).getCompanyName());
        }

        partIdField.setText(String.valueOf(partSelected.getId()));
        partNameField.setText(partSelected.getName());
        partInvField.setText(String.valueOf(partSelected.getStock()));
        partCostField.setText(String.valueOf(partSelected.getPrice()));
        partMinField.setText(String.valueOf(partSelected.getMin()));
        partMaxField.setText(String.valueOf(partSelected.getMax()));
    }
    public void mPartCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void inHouseSelect(ActionEvent actionEvent) {
        machineCustomerLabel.setText("Machine ID");

    }

    public void outsourcedSelect(ActionEvent actionEvent) {
        machineCustomerLabel.setText("Company Name");
    }

    public void mPartSave(ActionEvent actionEvent) throws IOException {
        try {
            int id = partSelected.getId();
            String name = partNameField.getText();
            Double cost = Double.parseDouble(partCostField.getText());
            int inv = Integer.parseInt(partInvField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            boolean partAdded = false;
            String companyName;
            int machineId;

            if ((min < max && min > 0) && (inv >= min && inv <= max)) {
                if (inHouseRadio.isSelected()) {
                    try {
                        machineId = Integer.parseInt(partMachineCustomer.getText());
                        InHouse newInHouse = new InHouse(id, name, cost, inv, min, max, machineId);
                        Inventory.addPart(newInHouse);
                        partAdded = true;
                    } catch (Exception e) {
                        alertCases(2);
                    }
                }
                if (outsourcedRadio.isSelected()) {
                    companyName = partMachineCustomer.getText();
                    Outsourced newOutsourced = new Outsourced(id, name, cost, inv, min, max, companyName);
                    Inventory.addPart(newOutsourced);
                    partAdded = true;
                }
                if (partAdded) {
                    Inventory.deletePart(partSelected);

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            if (min >= max || min <= 0) {
                alertCases(3);
            }
            if (inv < min || inv > max) {
                alertCases(4);
            }
        } catch (Exception e) {
            alertCases(1);
        }
    }

        private void alertCases(int alertType) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            switch (alertType) {
                case 1:
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("There are blank fields and/or invalid values.");
                    alert.showAndWait();
                    break;
                case 2:
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("The Machine ID can only contain number.");
                    alert.showAndWait();
                    break;
                case 3:
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("Min should be greater than zero and less than Max.");
                    alert.showAndWait();
                    break;
                case 4:
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("Inventory must be equal to or between min and max.");
                    alert.showAndWait();
                    break;
            }
        }

    }


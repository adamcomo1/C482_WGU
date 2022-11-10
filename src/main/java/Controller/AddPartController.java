package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.stage.Stage;

public class AddPartController {
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public TextField partIdField;
    public TextField partNameField;
    public TextField partInvField;
    public TextField partCostField;
    public TextField partMaxField;
    public TextField partMachineCompany;
    public TextField partMinField;
    public Label machineCustomerLabel;
    public ToggleGroup partTypeToggle;




    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you wish to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void saveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = 0;
            String name = partNameField.getText();
            Double cost = Double.parseDouble(partCostField.getText());
            int inv = Integer.parseInt(partInvField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            String companyName;
            int machineId;
            boolean partAdded = false;

            if (name.isEmpty()) {
                alertCases(1);
            } else {
                if ((min < max && min > 0) && (inv >= min && inv <= max)) {
                    if (inHouseRadio.isSelected()) {
                        try {
                            id = Inventory.newPartId();
                            machineId = Integer.parseInt(partMachineCompany.getText());
                            InHouse newInHousePart = new InHouse(id, name, cost, inv, min, max, machineId);
                            Inventory.addPart(newInHousePart);
                            partAdded = true;
                        } catch (Exception e) {
                            alertCases(4);
                        }
                    }
                    if (outsourcedRadio.isSelected()) {
                        id = Inventory.newPartId();
                        companyName = partMachineCompany.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, cost, inv, min, max, companyName);
                        Inventory.addPart(newOutsourcedPart);
                        partAdded = true;
                    }
                    if (partAdded) {
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
                    alertCases(2);
                }
            }
        } catch (Exception e) {
            alertCases(5);
        }
    }




        public void inHouseSelect (ActionEvent actionEvent){
            machineCustomerLabel.setText("Machine ID");

        }

        public void outsourcedSelect (ActionEvent actionEvent){
            machineCustomerLabel.setText("Company Name");

        }

        private void alertCases (int alertType) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            switch (alertType) {
                case 1:
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Part Name");
                    alert.setContentText("Name cannot be empty.");
                    alert.showAndWait();
                    break;
                case 2:
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Inventory value");
                    alert.setContentText("Inventory must be an integer equal to or between Min and Max.");
                    alert.showAndWait();
                    break;
                case 3:
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid value for Min");
                    alert.setContentText("Min must be an integer greater than zero and less than Max.");
                    alert.showAndWait();
                    break;
                case 4:
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Machine ID");
                    alert.setContentText("Machine ID can only contain numbers.");
                    alert.showAndWait();
                    break;
                case 5:
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Adding Part");
                    alert.setContentText("Form contains blank fields and/or invalid values.");
                    alert.showAndWait();
                    break;
            }
        }
    }


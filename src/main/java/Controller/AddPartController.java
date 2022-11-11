package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javafx.scene.Node;
import javafx.stage.Stage;
/**
 * Controller class for the add part screen used for control logic.
 *
 * @author Adam Comoletti
 */
public class AddPartController {
    /**
     * Radio button for the in-house parts.
     */
    public RadioButton inHouseRadio;
    /**
     * Radio button for outsourced parts.
     */
    public RadioButton outsourcedRadio;
    /**
     * Text field for the part ID.
     */
    public TextField partIdField;
    /**
     * Text field for part name.
     */
    public TextField partNameField;
    /**
     * Text field for part inventory / stock.
     */
    public TextField partInvField;
    /**
     * Text field for part price / cost.
     */
    public TextField partCostField;
    /**
     * Text field for part inventory max.
     */
    public TextField partMaxField;
    /**
     * Text field for Machine ID or Company name.
     */
    public TextField partMachineCompany;
    /**
     * Text field for part inventory min.
     */
    public TextField partMinField;
    /**
     * Label for Machine ID or Company name.
     */
    public Label machineCompanyLabel;
    /**
     * Toggle group for toggling between in-house and outsourced.
     */
    public ToggleGroup partTypeToggle;


    /**
     * Delete button when pressed displays confirmation and loads MainController.
     * @param actionEvent Cancel button action.
     * @throws IOException From FXMLLoader.
     */
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
    /**
     * Saves changes to part and loads MainController.
     *
     * Input validation is performed with error messages displaying issue.
     * @param actionEvent Save button press action.
     * @throws IOException From FXMLLoader
     */
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
        /**
        * Sets Company name / Machine ID label to "Machine ID".
        * @param actionEvent In-house radio selected action.
        */
        public void inHouseSelect (ActionEvent actionEvent){
            machineCompanyLabel.setText("Machine ID");

        }
        /**
        * Sets Company name / Machine ID label to "Company Name".
        * @param actionEvent Outsourced radio selected action.
        */
        public void outsourcedSelect (ActionEvent actionEvent){
            machineCompanyLabel.setText("Company Name");

        }
        /**
        * Used to display different alert messages.
        * @param alertType Alert cases selector.
        */
        private void alertCases (int alertType) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            switch (alertType) {
                case 1 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Part Name");
                    alert.setContentText("Name cannot be empty.");
                    alert.showAndWait();
                }
                case 2 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Inventory value");
                    alert.setContentText("Inventory must be an integer equal to or between Min and Max.");
                    alert.showAndWait();
                }
                case 3 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Min must be an integer greater than zero and less than Max.");
                    alert.showAndWait();
                }
                case 4 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("Machine ID can only contain numbers.");
                    alert.showAndWait();
                }
                case 5 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("Form contains blank fields and/or invalid values.");
                    alert.showAndWait();
                }
            }
        }
    }


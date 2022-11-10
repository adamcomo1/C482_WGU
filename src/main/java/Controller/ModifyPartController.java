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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Controller class for the modify part screen used for control logic.
 *
 * @author Adam Comoletti
 */

public class ModifyPartController implements Initializable {
    /**
     * Radio button for the in-house parts.
     */
    public RadioButton inHouseRadio;
    /**
     * Toggle group for toggling between in-house and outsourced.
     */
    public ToggleGroup partTypeToggle;
    /**
     * Radio button for the outsourced parts.
     */
    public RadioButton outsourcedRadio;
    /**
     * Text field for the part ID.
     */
    public TextField partIdField;
    /**
     * Text field for the part name.
     */
    public TextField partNameField;
    /**
     * Text field for the part inventory / stock.
     */
    public TextField partInvField;
    /**
     * Text field for the part price / cost.
     */
    public TextField partCostField;
    /**
     * Text field for the part inventory / stock max.
     */
    public TextField partMaxField;
    /**
     * Text field for the part Machine ID or Customer Name.
     */
    public TextField partMachineCustomer;
    /**
     * Text field for the part inventory / stock min.
     */
    public TextField partMinField;
    /**
     * Label for the Customer Name or Machine ID.
     */
    public Label machineCustomerLabel;
    /**
     * Declaration of part object selected in the MainController.
     */
    private Part partSelected;

    /**
     * Initializes the controller and inserts data from the part selected into the text fields.
     * @param url The url used to resolve paths for root, null if not known.
     * @param resourceBundle Used to localize the root object, null if the root was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partSelected = MainController.getPartToModify();
        if (partSelected instanceof InHouse) {
            machineCustomerLabel.setText("Machine ID");
            inHouseRadio.setSelected(true);
            partMachineCustomer.setText(String.valueOf(((InHouse) partSelected).getMachineId()));
        }
        if (partSelected instanceof Outsourced) {
            machineCustomerLabel.setText("Company Name");
            outsourcedRadio.setSelected(true);
            partMachineCustomer.setText(((Outsourced) partSelected).getCompanyName());
        }

        partIdField.setText(String.valueOf(partSelected.getId()));
        partNameField.setText(partSelected.getName());
        partInvField.setText(String.valueOf(partSelected.getStock()));
        partCostField.setText(String.valueOf(partSelected.getPrice()));
        partMinField.setText(String.valueOf(partSelected.getMin()));
        partMaxField.setText(String.valueOf(partSelected.getMax()));
    }

    /**
     * Delete button when pressed displays confirmation and loads MainController.
     * @param actionEvent Cancel buttton action.
     * @throws IOException From FXMLLoader.
     */
    public void mPartCancel(ActionEvent actionEvent) throws IOException {
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
     * Sets Company name / Machine ID label to "Machine ID".
     * @param actionEvent In-house radio selected action.
     */
    public void inHouseSelect(ActionEvent actionEvent) { machineCustomerLabel.setText("Machine ID"); }

    /**
     * Sets Company name / Machine ID label to "Company Name".
     * @param actionEvent Outsourced radio selected action.
     */
    public void outsourcedSelect(ActionEvent actionEvent) {
        machineCustomerLabel.setText("Company Name");
    }

    /**
     * Saves changes to part and loads MainController.
     *
     * Input validation is performed with error messages displaying issue.
     * @param actionEvent Save button press action.
     * @throws IOException From FXMLLoader
     */
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
                    if (companyName.isBlank()) {
                        alertCases(1);
                    }
                    else {
                        Outsourced newOutsourced = new Outsourced(id, name, cost, inv, min, max, companyName);
                        Inventory.addPart(newOutsourced);
                        partAdded = true;
                    }
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

    /**
     * Used to display different alert messages.
     * @param alertType Alert cases selector.
     */
    private void alertCases(int alertType) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            switch (alertType) {
                case 1 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("There are blank fields and/or invalid values.");
                    alert.showAndWait();
                }
                case 2 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("The Machine ID can only contain number.");
                    alert.showAndWait();
                }
                case 3 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("Min should be greater than zero and less than Max.");
                    alert.showAndWait();
                }
                case 4 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Alert");
                    alert.setContentText("Inventory must be equal to or between min and max.");
                    alert.showAndWait();
                }
            }
        }

    }


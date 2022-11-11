package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Controller class for the add product screen used for control logic.
 *
 * @author Adam Comoletti
 */

public class AddProductController implements Initializable {
    /**
     * List of parts that are associated with the selected product.
     */
    private ObservableList<Part> productPartAssoc = FXCollections.observableArrayList();
    /**
     * Text field for the product ID.
     */
    public TextField productIdField;
    /**
     * Text field for product name.
     */
    public TextField productNameField;
    /**
     * Text field for product inventory / stock.
     */
    public TextField productInvField;
    /**
     * Text field for product price / cost.
     */
    public TextField productCostField;
    /**
     * Text field for product inventory Max.
     */
    public TextField productMaxField;
    /**
     * Text field for product inventory Min.
     */
    public TextField productMinField;
    /**
     * All parts table view.
     */
    public TableView<Part> allPartTable;
    /**
     * Part ID column in all parts table.
     */
    public TableColumn<Part, Integer> partIdColumn;
    /**
     * Part name column in all parts table.
     */
    public TableColumn<Part, String> partNameColumn;
    /**
     * Part inventory / stock column in all parts table.
     */
    public TableColumn<Part, Integer> partInvColumn;
    /**
     * Part cost / price column in all parts table.
     */
    public TableColumn<Part, Double> partCostColumn;
    /**
     * Text field for all parts table part search.
     */
    public TextField productPartText;
    /**
     * Table view for associated parts table.
     */
    public TableView<Part> associatedTable;
    /**
     * Part id column in associated part table.
     */
    public TableColumn<Part, Integer> aPartIdColumn;
    /**
     * Part name column in associated part table.
     */
    public TableColumn<Part, String> aPartNameColumn;
    /**
     * Part inventory / stock column in associated part table.
     */
    public TableColumn<Part, Integer> aPartInvColumn;
    /**
     * Part cost / price column in associated part table.
     */
    public TableColumn<Part, Double> aPartCostColumn;

    /**
     * Cancel button that displays cancel confirmation and loads main controller.
     * @param actionEvent Cancel button press.
     * @throws IOException From FXMLLoader.
     */
    public void aProductCancel(ActionEvent actionEvent) throws IOException {
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
     * Performs a search using the values entered into part search text field and displays them in parts table.
     * @param actionEvent Part search button pressed action.
     */
    public void productPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String searchText = productPartText.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchText) || part.getName().contains(searchText)) {
                foundPart.add(part);
            }
        }
        allPartTable.setItems(foundPart);
        if (foundPart.size() == 0) {
            alertCases(2);
        }
    }
    /**
     * Removes the selected associated part from the product's associated parts.
     * Gives confirmation alert to confirm removal of part.
     * Gives error message if no part is selected.
     * @param actionEvent Remove button pressed.
     */
    public void removePartPress(ActionEvent actionEvent) {
        Part partSelected = associatedTable.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            alertCases(5);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Confirmation");
            alert.setContentText("Are you sure you wish to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                productPartAssoc.remove(partSelected);
                associatedTable.setItems(productPartAssoc);
            }
        }
    }
    /**
     * Saves changes to part and loads MainController.
     * Input validation is performed with error messages displaying issue.
     * @param actionEvent Save button pressed.
     */
    public void saveProductPress(ActionEvent actionEvent) {
        try {
            int id = 0;
            String name = productNameField.getText();
            int inv = Integer.parseInt(productInvField.getText());
            Double cost = Double.parseDouble(productCostField.getText());
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());

            if (name.isEmpty()) {
                alertCases(6);
            }
            else {
                if ((min < max && min > 0) && (inv >= min && inv <= max)) {
                    Product newProduct = new Product(id, name, cost, inv, min, max);
                    for (Part part : productPartAssoc) {
                        newProduct.addAssociatedPart(part);
                    }
                    newProduct.setId(Inventory.newProductId());
                    Inventory.addProduct(newProduct);

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
                    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }

                if (min >= max || min <= 0) {
                    alertCases(3);
                }
                if (inv < min || inv > max) {
                    alertCases(4);
                }
            }
        } catch (Exception e) {
            alertCases(1);
        }
    }
    /**
     * AddProductController is initialized and all parts table populates.
     * @param url The url used to resolve paths for root, null if not known.
     * @param resourceBundle Used to localize the root object, null if the root was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartTable.setItems(Inventory.getAllParts());

        aPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    /**
     * Adds selected part to associated parts.
     * Gives error message if no part is selected.
     * @param actionEvent Add button pressed.
     */
    public void addToAssociate(ActionEvent actionEvent) {
        Part partSelected = allPartTable.getSelectionModel().getSelectedItem();
        if (partSelected == null) {
            alertCases(5);
        }
        else {
            productPartAssoc.add(partSelected);
            associatedTable.setItems(productPartAssoc);
        }
    }
    /**
     * Used to display different alert messages.
     * @param alertType Alert cases selector.
     */
    private void alertCases(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("Form contains blank fields and/or invalid values.");
                alert.showAndWait();
            }
            case 2 -> {
                alertInfo.setTitle("Alert");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Min Invalid");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Inventory Invalid");
                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error");
                alert.setHeaderText("No part Selected. Please select a part to remove.");
                alert.showAndWait();
            }
            case 6 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
            }
        }
    }
    /**
     * Displays all parts in table when part search text field is empty.
     * @param keyEvent Part search text field pressed.
     */
    public void partTableClear(KeyEvent keyEvent) {
        if (productPartText.getText().isEmpty()) {
            allPartTable.setItems(Inventory.getAllParts());
        }
    }
}

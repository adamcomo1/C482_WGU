package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the modify product screen used for control logic.
 *
 * @author Adam Comoletti
 */
public class ModifyProductController implements Initializable {
    /**
     * List of parts that are associated with the selected product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Part cost / price column in associated part table.
     */
    public TableColumn<Part, Double> aPartCostColumn;
    /**
     * Part stock / inventory column in associated part table.
     */
    public TableColumn<Part, Integer> aPartInvColumn;
    /**
     * Part name column in associated part table.
     */
    public TableColumn<Part, String> aPartNameColumn;
    /**
     * Part ID column in associated part table.
     */
    public TableColumn<Part, Integer> aPartIdColumn;
    /**
     * Associated part table view.
     */
    public TableView<Part> assocPartTable;
    /**
     * Text field for part search table.
     */
    public TextField partSearchField;
    /**
     * Product selected in the MainController.
     */
    Product productSelected;
    public TableColumn<Part, Double> partCostColumn;
    public TableColumn<Part, Integer> partInvColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Integer> partIdColumn;
    public TableView<Part> allPartTable;
    public TextField productMinField;
    public TextField productMaxField;
    public TextField productCostField;
    public TextField productInvField;
    public TextField productNameField;
    public TextField productIdField;

    public void mproCancel(ActionEvent actionEvent) throws IOException {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productSelected = MainController.getProductToModify();
        associatedParts = productSelected.getAllAssociatedParts();

        productIdField.setText(String.valueOf(productSelected.getId()));
        productNameField.setText(productSelected.getName());
        productInvField.setText(String.valueOf(productSelected.getStock()));
        productCostField.setText(String.valueOf(productSelected.getPrice()));
        productMinField.setText(String.valueOf(productSelected.getMin()));
        productMaxField.setText(String.valueOf(productSelected.getMax()));

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartTable.setItems(Inventory.getAllParts());

        aPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        assocPartTable.setItems(associatedParts);

    }

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

    public void saveButtonPress(ActionEvent actionEvent) {
        try {
            int id = productSelected.getId();
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
                    for (Part part : associatedParts) {
                        newProduct.addAssociatedPart(part);
                    }
                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(productSelected);

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

    public void removeAssocPart(ActionEvent actionEvent) {
        Part partSelected = assocPartTable.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            alertCases(5);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Confirmation");
            alert.setContentText("Are you sure you wish to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(partSelected);
                assocPartTable.setItems(associatedParts);
            }
        }
    }

    public void addAssocPart(ActionEvent actionEvent) {
        Part partSelected = allPartTable.getSelectionModel().getSelectedItem();
        if (partSelected == null) {
            alertCases(5);
        }
        else {
            associatedParts.add(partSelected);
            assocPartTable.setItems(associatedParts);
        }
    }

    public void partSearchText(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String searchText = partSearchField.getText();

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

    public void partSearchClear(KeyEvent keyEvent) {
        if (partSearchField.getText().isEmpty()) {
            allPartTable.setItems(Inventory.getAllParts());
        }
    }
}

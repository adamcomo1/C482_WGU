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

public class AddProductController implements Initializable {

    private ObservableList<Part> productPartAssoc = FXCollections.observableArrayList();
    public TextField productIdField;
    public TextField productNameField;
    public TextField productInvField;
    public TextField productCostField;
    public TextField productMaxField;
    public TextField productMinField;
    public TableView<Part> allPartTable;
    public TableColumn<Part, Integer> partIdColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Integer> partInvColumn;
    public TableColumn<Part, Double> partCostColumn;
    public TextField productPartText;
    public Button removePart;
    public Button saveProduct;
    public TableView<Part> associatedTable;
    public TableColumn<Part, Integer> aPartIdColumn;
    public TableColumn<Part, String> aPartNameColumn;
    public TableColumn<Part, Integer> aPartInvColumn;
    public TableColumn<Part, Double> aPartCostColumn;

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

    private void alertCases(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("Form contains blank fields and/or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Alert");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Min Invalid");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Inventory Invalid");
                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("No part Selected. Please select a part to remove.");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }

    public void partTableClear(KeyEvent keyEvent) {
        if (productPartText.getText().isEmpty()) {
            allPartTable.setItems(Inventory.getAllParts());
        }
    }
}

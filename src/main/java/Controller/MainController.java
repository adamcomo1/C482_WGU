package Controller;
import Model.Part;
import Model.Product;
import Model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
 * Controller class for the main screen used for control logic.
 *
 * @author Adam Comoletti
 */
public class MainController implements Initializable {
    /**
     * Part ID column in Parts table.
     */
    public TableColumn<Part, Integer> partIdCol;
    /**
     * Part name column in Parts table.
     */
    public TableColumn<Part, String> partNameCol;
    /**
     * Part inventory / stock column in parts table.
     */
    public TableColumn<Part, Integer> partInvCol;
    /**
     * Part cost / price column in parts table.
     */
    public TableColumn<Part, Double> partCostCol;
    /**
     * Product ID column in Product table.
     */
    public TableColumn<Product, Integer> productIdCol;
    /**
     * Product name column in Product table.
     */
    public TableColumn<Product, String> productNameCol;
    /**
     * Product inventory / stock column in product table.
     */
    public TableColumn<Product, Integer> productInvCol;
    /**
     * Product cost / price column in product table.
     */
    public TableColumn<Product, Double> productCostCol;
    /**
     * Product table view.
     */
    public TableView<Product> mainProductTable;
    /**
     * Part table view.
     */
    public TableView<Part> mainPartTable;
    /**
     * Part search text field.
     */
    public TextField partSearch;
    /**
     * Product search text field.
     */
    public TextField productSearch;

    /**
     * Returns the part selected.
     * @return A part object.
     */
    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     * Returns the product selected.
     * @return A product object.
     */
    public static Product getProductToModify() {return productToModify;}

    /**
     * Part selected in the part table view.
     */
    private static Part partToModify;
    /**
     * Product selected in the product table view
     */
    private static Product productToModify;

    /**
     * Initializes the controller and populates tables.
     * @param url The url used to resolve paths for root, null if not known.
     * @param resourceBundle Used to localize the root object, null if the root was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainProductTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainPartTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Loads AddPartController.
     * @param actionEvent Add part button pressed action.
     * @throws IOException From FXMLLoader.
     */
    public void addPartClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddPartForm.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads ModifyPartController.
     * @param actionEvent Modify part button pressed action.
     * @throws IOException From FXMLLoader.
     */
    public void modifyPartButton(ActionEvent actionEvent) throws IOException {
        partToModify = mainPartTable.getSelectionModel().getSelectedItem();
        if (partToModify == null) {
            alertCases(4);
        }
        else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ModifyPartForm.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Loads AddProductController
     * @param actionEvent Add product button pressed action.
     * @throws IOException From FXMLLoader.
     */
    public void addProductClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddProductForm.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads ModifyProductController.
     * @param actionEvent Modify product button pressed action.
     * @throws IOException From FXMLLoader.
     */
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        productToModify = mainProductTable.getSelectionModel().getSelectedItem();
        if (productToModify == null) {
            alertCases(3);
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ModifyProductForm.fxml")));
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
    public void partSearchPress(ActionEvent actionEvent) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFound = FXCollections.observableArrayList();
        String searchText = partSearch.getText();
        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchText) || part.getName().contains(searchText)) {
                partFound.add(part);
            }
        }
        mainPartTable.setItems(partFound);
        if(partFound.size() == 0) {
            alertCases(1);
        }
    }

    /**
     * Performs a search using the values entered into product search text field and displays them in product table.
     * @param actionEvent Product search button pressed action.
     */
    public void productSearchPress(ActionEvent actionEvent) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productFound = FXCollections.observableArrayList();
        String searchText = productSearch.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchText) || product.getName().contains(searchText)) {
                productFound.add(product);
            }
        }
        mainProductTable.setItems(productFound);
        if(productFound.size() == 0) {
            alertCases(2);
        }
    }

    /**
     * Used to display different alert messages.
     * @param alertType Alert cases selector.
     */
    private void alertCases(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("Part was not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("Product was not found");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("No product selected");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("No part selected");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Alert");
                alert.setContentText("Cannot remove product that has parts associated");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Exits Program.
     * @param actionEvent Exit button pressed action.
     */
    public void exitButton(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Deletes selected part in part table.
     * @param actionEvent Part delete button pressed action.
     */
    public void mainPartDelete(ActionEvent actionEvent) {
        Part partSelected = mainPartTable.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            alertCases(4);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you wish to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partSelected);
            }
        }
    }

    /**
     * Deletes selected product in product table.
     * Displays confirmation before deleting product, displays error message if no product selected, and
     * prevents deletion if product has associated parts.
     * @param actionEvent Product delete button pressed action.
     */
    public void mainProductDelete(ActionEvent actionEvent) {
        Product productSelected = mainProductTable.getSelectionModel().getSelectedItem();

        if (productSelected == null) {
            alertCases(3);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you wish to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> partsAssociated = productSelected.getAllAssociatedParts();
                if (partsAssociated.size() >= 1) {
                    alertCases(5);
                }
                else {
                    Inventory.deleteProduct(productSelected);
                }
            }
        }
    }

    /**
     * Displays all parts in table when part search text field is empty.
     * @param keyEvent Part search text field pressed.
     */
    public void partSearchClear(KeyEvent keyEvent) {
        if (partSearch.getText().isEmpty()) {
            mainPartTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Displays all products in table when product search text field is empty.
     * @param keyEvent Product search text field press.
     */
    public void productSearchClear(KeyEvent keyEvent) {
        if (productSearch.getText().isEmpty()) {
            mainProductTable.setItems(Inventory.getAllProducts());
        }
    }
}
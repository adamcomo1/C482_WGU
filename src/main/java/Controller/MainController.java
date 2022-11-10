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
import java.util.ResourceBundle;




public class MainController implements Initializable {

    @FXML
    public TableColumn<Part, Integer> partIdCol;
    @FXML
    public TableColumn<Part, String> partNameCol;
    @FXML
    public TableColumn<Part, Integer> partInvCol;
    @FXML
    public TableColumn<Part, Double> partCostCol;
    @FXML
    public TableColumn<Product, Integer> productIdCol;
    public TableColumn<Product, String> productNameCol;
    @FXML
    public TableColumn<Product, Integer> productInvCol;
    @FXML
    public TableColumn<Product, Double> productCostCol;
    @FXML
    public TableView<Product> mainProductTable;
    @FXML
    public TableView<Part> mainPartTable;
    public TextField partSearch;
    public Button addPartButton;
    public TextField productSearch;

    public static Part getPartToModify() {
        return partToModify;
    }

    private static Part partToModify;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Generates product table view
        mainProductTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Generates part table view
        mainPartTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Switches to Add Part Form when the "Add" button is pressed
    public void addPartClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddPartForm.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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

    public void addProductClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddProductForm.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ModifyProductForm.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void partSearchPress(KeyEvent keyEvent) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFound = FXCollections.observableArrayList();
        String searchText = partSearch.getText();
        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchText) || part.getName().contains(searchText)) {
                partFound.add(part);
            }
        }
        mainPartTable.setItems(partFound);

    }


    public void productSearchPress(KeyEvent keyEvent) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productFound = FXCollections.observableArrayList();
        String searchText = productSearch.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchText) || product.getName().contains(searchText)) {
                productFound.add(product);
            }
        }
        mainProductTable.setItems(productFound);
    }

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
}
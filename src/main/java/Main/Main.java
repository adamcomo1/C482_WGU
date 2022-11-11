package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
/**
 * Inventory management system application used to manage a companies inventory of products and parts.
 * FUTURE ENHANCEMENT Add a total cost / price for parts associated with a product.
 * @author Adam Comoletti
 */
public class Main extends Application {
    /**
     * Method that creates and loads the main screen.
     *
     * @param primaryStage The main stage of the application.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();





    }

    /**
     * Method used to add test data to application.
     */
   private static void addTestData() {
        InHouse test1 = new InHouse(25, "test1", 125.15, 5, 1, 10, 23);
        InHouse test2 = new InHouse(30, "test2", 20.00, 5, 1, 20, 23);
        Outsourced test3 = new Outsourced(31, "test3", 15.50, 3, 1, 25, "GE");
        Inventory.addPart(test1);
        Inventory.addPart(test2);
        Inventory.addPart(test3);
        //Product Test Data Import
        Product test4 = new Product(32,"test4", 786.89, 3, 1, 25);
        test4.addAssociatedPart(test1);
        test4.addAssociatedPart(test2);
        Inventory.addProduct(test4);
    }

    /**
     * Method that loads test data and launches appliation.
     * @param args
     */
    public static void main(String[] args) {
        // Importing test data function
        addTestData();

        launch(args);
    }
}
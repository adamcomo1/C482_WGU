package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static int partId = 0;

    private static int productId = 0;

    public static int newPartId() {
        partId += 1;
        return partId;
    }

    public static int newProductId() {
        productId += 1;
        return productId;
    }

    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {

        Part partFound = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        return partFound;
    }

    public static Product lookupProduct(int productId) {

        Product productFound = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
            }
        }
        return productFound;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    public static void updatePart (int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    public static void updateProduct (int index, Product newProduct) {

        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {

        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    public static ObservableList<Part> getAllParts() {

        return allParts;
    }
}

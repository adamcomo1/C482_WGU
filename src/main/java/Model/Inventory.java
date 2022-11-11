package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class model for Inventory Class
 * @author Adam Comoletti
 */
public class Inventory {
    /**
     * List used to store all products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * List used to store all parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Variable used for new Part IDs.
     */
    private static int partId = 0;
    /**
     * Variable used for new Product IDs.
     */
    private static int productId = 0;

    /**
     * Method used to get new part ID.
     * @return Returns new part ID going up by 1.
     */
    public static int newPartId() {
        partId += 1;
        return partId;
    }

    /**
     * Method used to get new Product ID.
     * @return Returns new Product ID going up by 1.
     */
    public static int newProductId() {
        productId += 1;
        return productId;
    }

    /**
     * Method used to add new part to all parts.
     * @param newPart The part to be added.
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * Method used to add new product to all products.
     * @param newProduct The product to be added.
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * Method used to lookup part in inventory using ID.
     * @param partId part ID being searched.
     * @return Returns the found Part.
     */
    public static Part lookupPart(int partId) {

        Part partFound = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        return partFound;
    }

    /**
     * Method used to lookup product in inventory using ID.
     * @param productId product ID being searched.
     * @return
     */
    public static Product lookupProduct(int productId) {

        Product productFound = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
            }
        }
        return productFound;
    }

    /**
     * Method used to lookup part in inventory using name.
     * @param partName part name being searched.
     * @return Returns the found Part.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }
    /**
     * Method used to lookup part in inventory using ID.
     * @param productName product name being searched.
     * @return Returns the found Product.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * Method used to update part.
     * @param index Part index for part being updated
     * @param selectedPart The part to be updated.
     */
    public static void updatePart (int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Method used to update product
     * @param index Product index for product being updated.
     * @param newProduct The product to be updated.
     */
    public static void updateProduct (int index, Product newProduct) {

        allProducts.set(index, newProduct);
    }

    /**
     * Method used to delete part from inventory.
     * @param selectedPart The part selected.
     * @return Returns true if part deleted and false if part not deleted.
     */
    public static boolean deletePart(Part selectedPart) {

        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Method used to delete product from inventory.
     * @param selectedProduct The part selected.
     * @return Returns true if product deleted and false if product not deleted.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method used to get a list of all products.
     * @return Returns all products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * Method used to get a list of all parts.
     * @return Returns all part in inventory.
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }
}

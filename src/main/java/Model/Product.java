package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Class model for Outsourced Class
 * @author Adam Comoletti
 */
public class Product {
    /**
     * List used to hold products associated Parts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Id of product.
     */
    private int id;
    /**
     * Name of product.
     */
    private String name;
    /**
     * Product price / cost.
     */
    private double price;
    /**
     * Product stock / inventory.
     */
    private int stock;
    /**
     * product stock / inventory min.
     */
    private int min;
    /**
     * product stock / inventory max.
     */
    private int max;

    /**
     * Constructor for new Product.
     * @param id Product Id.
     * @param name Product name.
     * @param price Product Price / Cost.
     * @param stock Product stock / inventory.
     * @param min Product stock / inventory min.
     * @param max PRoduct stock / inventory max.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Method for getting product ID.
     * @return Returns product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Method used to set product ID.
     * @param id product ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method used to get product name.
     * @return returns product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Method used to set product name.
     * @param name Name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method used to get product price.
     * @return Returns product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method used to set Product price.
     * @param price Product price / cost.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method used to get product stock / inventory.
     * @return returns product stock / inventory.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Method used to set product stock / inventory.
     * @param stock Product stock / inventory.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Method used to get minimum stock of product.
     * @return Product minimum stock.
     */
    public int getMin() {
        return min;
    }

    /**
     * Method used to set product stock min.
     * @param min Product stock min.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Method used to get product stock max.
     * @return returns product stock max.
     */
    public int getMax() {
        return max;
    }

    /**
     * Method used to set product stock max.
     * @param max Product stock max.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Method used to add part to Product associated parts.
     * @param part part to be added.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);

    }

    /**
     * Method used to delete associated part.
     * @param selectedAssociatedPart selected Part associated with Product.
     * @return Returns true if part found and removed and false if not.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Method used to get all associated parts to a product.
     * @return returns all associated parts to a product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;

    }
}

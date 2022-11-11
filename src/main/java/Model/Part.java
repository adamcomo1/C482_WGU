package Model; /**
* Supplied class Part.java
 *
 *
 * @author Adam Comoletti
 */
public abstract class Part {
    /**
     * Part ID.
     */
    private int id;
    /**
     * Part name.
     */
    private String name;
    /**
     * Part price / cost.
     */
    private double price;
    /**
     * Part stock / inventory.
     */
    private int stock;
    /**
     * Part stock / inventory min.
     */
    private int min;
    /**
     * Part stock / inventory max.
     */
    private int max;

    /**
     * Constructor for new Part.
     * @param id part ID.
     * @param name part Name.
     * @param price part price / cost.
     * @param stock part stock / inventory.
     * @param min part stock / inventory min.
     * @param max part stock / inventory max.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the part id.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the part id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the part name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return the stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min.
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max.
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}
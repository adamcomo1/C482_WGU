package Model;
/**
 * Class model for Outsourced Class
 * @author Adam Comoletti
 */
public class Outsourced extends Part {
    /**
     * Outsourced part company name.
     */
    private String companyName;

    /**
     * Constructor for new Outsourced parts.
     * @param id Part ID.
     * @param name Part name.
     * @param price Part price / cost.
     * @param stock Part stock / inventory.
     * @param min Part stock / inventory minimum.
     * @param max Part stock / inventory max.
     * @param companyName part company name for outsourced part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Method for getting company name of outsourced part.
     * @return returns company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Method for setting company name of outsourced part.
     * @param companyName company name to be set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

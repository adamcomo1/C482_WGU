package Model;
/**
 * Class model for InHouse Class
 * @author Adam Comoletti
 */
public class InHouse extends Part {
    /**
     * Part machine ID for InHouse part.
     */
    private int machineId;

    /**
     * Constructor for new InHouse part.
     * @param id Part ID.
     * @param name Part name.
     * @param price Part price / cost.
     * @param stock Part stock / inventory.
     * @param min Part stock / inventory min.
     * @param max Part stock / inventory max.
     * @param machineid InHouse Part machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineid) {
        super(id, name, price, stock, min, max);
        this.machineId = machineid;
    }

    /**
     * Method used to get Machine ID of InHouse part.
     * @return returns Machine ID of InHouse part.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Method used to set Machine ID of InHouse part.
     * @param machineId Machine ID to be set.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;

    }
}


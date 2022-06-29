package com.wgu.inventorytracker.models;

/**
 * Class to model an in house part.
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * Default construnctor.
     *
     * @param id        the part's id.
     * @param name      the part's name.
     * @param price     the part's price.
     * @param stock     the part's stock count.
     * @param min       the part's min value.
     * @param max       the part's max value.
     * @param machineId the part's machine identifier.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * The machine id setter.
     *
     * @param machineId the machine id to set.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * The machine id getter.
     *
     * @return the current machine id.
     */
    public int getMachineId() {
        return machineId;
    }
}

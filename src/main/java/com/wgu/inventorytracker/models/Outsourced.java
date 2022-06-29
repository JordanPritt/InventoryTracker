package com.wgu.inventorytracker.models;

/**
 * A class to model the Outsourced part.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Default constructor.
     *
     * @param id          the part's id.
     * @param name        the part's name.
     * @param price       the part's price.
     * @param stock       the part's inventory count.
     * @param min         the part's min value.
     * @param max         the part's max value.
     * @param companyName the part's company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Company Name getter.
     *
     * @return the part's company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Company Name setter.
     *
     * @param companyName the company name to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

/**
 * Name: JIAFU GUO
 * Course: CS-665 Software Designs & Patterns
 * Date: 11/10/2026
 * File Name: CustomerDataViaHttps.java
 * Description: The new system's API, customer data read over HTTPS.
 */

package edu.bu.met.cs665.https;

import edu.bu.met.cs665.customer.Customer;

/**
 * New System.
 * The API of the newly developed system.
 * In the Adapter pattern this is the Target.
 */
public interface CustomerDataViaHttps {

    /**
     * Print a customer's record.
     *
     * @param customerId the id of the customer to print
     */
    void printCustomer(int customerId);

    /**
     * Read one customer record over the HTTPS connection.
     *
     * @param customerId the id of the customer to read
     * @return the customer's record
     */
    Customer getCustomerViaHttps(int customerId);

}

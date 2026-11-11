/**
 * Name: JIAFU GUO
 * Course: CS-665 Software Designs & Patterns
 * Date: 11/10/2026
 * File Name: NewHttpsSystem.java
 * Description: Mock of the new system that reads customer data from an external server through HTTPS and a REST API.
 * The native implementation of the Target interface.
 */

package edu.bu.met.cs665.https;

import edu.bu.met.cs665.customer.Customer;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The new system: the native implementation of the Target interface.
 *
 * The real system would send a request to a REST API over HTTPS.
 * This mock keeps the customer records in memory and logs what the real system would do.
 * It knows nothing about the legacy system or about the adapter.
 */
public class NewHttpsSystem implements CustomerDataViaHttps {

    private static final Logger LOGGER = LogManager.getLogger(NewHttpsSystem.class);

    /**
     * Stands for the database behind the REST API.
     * Customer id to record.
     */
    private final Map<Integer, Customer> server = new HashMap<>();

    /**
     * Store a customer record on the mock server.
     * Used by Main and the tests to prepare data.
     *
     * @param customer the record to store
     * @throws IllegalArgumentException if customer is null
     */
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required.");
        }
        server.put(customer.getId(), customer);
    }

    /**
     * Read one customer record from the external server.
     *
     * @param customerId the id of the customer to read
     * @return the customer record
     * @throws IllegalArgumentException if the server has no customer with this id
     */
    @Override
    public Customer getCustomerViaHttps(int customerId) {
        LOGGER.info("HTTPS: GET https://api.example.com/customers/" + customerId);
        Customer customer = server.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException(
                    "No customer " + customerId + " on the HTTPS server.");
        }
        return customer;
    }

    /**
     * Print one customer record of the new system to the log.
     *
     * @param customerId the id of the customer to print
     * @throws IllegalArgumentException if the server has no customer with this id
     */
    @Override
    public void printCustomer(int customerId) {
        LOGGER.info("New system: " + getCustomerViaHttps(customerId));
    }

}

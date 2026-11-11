/**
 * Name: JIAFU GUO
 * Course: CS-665 Software Designs & Patterns
 * Date: 11/10/2026
 * File Name: LegacyUsbSystem.java
 * Description: Mock of the legacy system that reads customer data from binary files on an external disk connected through USB.
 * Adaptee of the Adapter pattern.
 */

package edu.bu.met.cs665.usb;

import edu.bu.met.cs665.customer.Customer;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The legacy system and the Adaptee of the Adapter pattern.
 *
 * The real system would open a USB connection and decode a binary file.
 * This mock keeps the customer records in memory and logs what the real system would do.
 * It knows nothing about the new system or about the adapter.
 */
public class LegacyUsbSystem implements CustomerDataViaUsb {

    private static final Logger LOGGER = LogManager.getLogger(LegacyUsbSystem.class);

    /**
     * Stands for the binary files on the external disk.
     * Customer id to record.
     */
    private final Map<Integer, Customer> disk = new HashMap<>();

    /**
     * Store a customer record on the mock disk.
     * Used by Main and the tests to prepare data.
     *
     * @param customer the record to store
     * @throws IllegalArgumentException if customer is null
     */
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required.");
        }
        disk.put(customer.getId(), customer);
    }

    /**
     * Read one customer record from the external disk.
     *
     * @param customerId the id of the customer to read
     * @return the customer record
     * @throws IllegalArgumentException if the disk has no customer with this id
     */
    @Override
    public Customer getCustomerViaUsb(int customerId) {
        LOGGER.info("USB: reading customer #" + customerId + ".bin from the external disk");
        Customer customer = disk.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("No customer " + customerId + " on the USB disk.");
        }
        return customer;
    }

    /**
     * Print one customer record of the legacy system to the log.
     *
     * @param customerId the id of the customer to print
     * @throws IllegalArgumentException if the disk has no customer with this id
     */
    @Override
    public void printCustomer(int customerId) {
        LOGGER.info("Legacy system: " + getCustomerViaUsb(customerId));
    }

}

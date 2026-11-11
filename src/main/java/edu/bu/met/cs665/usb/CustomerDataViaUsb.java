/**
 * Name: JIAFU GUO
 * Course: CS-665 Software Designs & Patterns
 * Date: 11/10/2026
 * File Name: CustomerDataViaUsb.java
 * Description: The legacy system's API, customer data read over USB.
 */

package edu.bu.met.cs665.usb;

import edu.bu.met.cs665.customer.Customer;

/**
 * The legacy system.
 * In the Adapter pattern this is the interface of the Adaptee.
 */
public interface CustomerDataViaUsb {

    /**
     * Print a customer's record.
     *
     * @param customerId the id of the customer to print
     */
    void printCustomer(int customerId);

    /**
     * Read a customer's record through the USB connection.
     *
     * @param customerId the id of the customer to read
     * @return the customer's record
     */
    Customer getCustomerViaUsb(int customerId);

}

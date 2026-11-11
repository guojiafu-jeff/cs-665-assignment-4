/**
 * Name: JIAFU GUO
 * Course: CS-665 Software Designs & Patterns
 * Date: 11/10/2026
 * File Name: UsbToHttpsAdapter.java
 * Description: Adapter that lets the legacy USB system be used through the new system's interface, CustomerDataViaHttps.
 */

package edu.bu.met.cs665.adapter;

import edu.bu.met.cs665.customer.Customer;
import edu.bu.met.cs665.https.CustomerDataViaHttps;
import edu.bu.met.cs665.usb.CustomerDataViaUsb;

/**
 * The Adapter of the Adapter pattern (object adapter).
 *
 * It IS-A CustomerDataViaHttps, so every client written for the new system accepts it.
 * It HAS-A CustomerDataViaUsb, the legacy system that does the real work.
 * Each method of the new interface is forwarded to the matching method of the legacy API.
 * The adapter adds no behavior and stores no customer data.
 */
public class UsbToHttpsAdapter implements CustomerDataViaHttps {

    /** The Adaptee, the legacy system that is wrapped. */
    private final CustomerDataViaUsb legacySystem;

    /**
     * Wrap a legacy system so that it can be used through the new interface.
     *
     * @param legacySystem the legacy system to adapt
     * @throws IllegalArgumentException if legacySystem is null
     */
    public UsbToHttpsAdapter(CustomerDataViaUsb legacySystem) {
        if (legacySystem == null) {
            throw new IllegalArgumentException("A legacy system to adapt is required.");
        }
        this.legacySystem = legacySystem;
    }

    /**
     * Print a legacy customer through the new interface.
     * Both interfaces use the same method name here, so the call is forwarded as it is.
     *
     * @param customerId the id of the customer to print
     */
    @Override
    public void printCustomer(int customerId) {
        legacySystem.printCustomer(customerId);
    }

    /**
     * Read a legacy customer through the new system's method name.
     * This is where the two incompatible interfaces are connected.
     *
     * @param customerId the id of the customer to read
     * @return the customer record read by the legacy system
     */
    @Override
    public Customer getCustomerViaHttps(int customerId) {
        return legacySystem.getCustomerViaUsb(customerId);
    }

}

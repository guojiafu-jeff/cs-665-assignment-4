/**
 * Name: JIAFU GUO
 * Course: CS-665 Software Designs & Patterns
 * Date: 11/10/2026
 * File Name: TestCustomerDataAdapter.java
 * Description: User interaction sequences: customers are read from the legacy USB system, from the new HTTPS system, and from the legacy system through the adapter and the new interface.
 */

package edu.bu.met.cs665;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import edu.bu.met.cs665.adapter.UsbToHttpsAdapter;
import edu.bu.met.cs665.customer.Customer;
import edu.bu.met.cs665.https.CustomerDataViaHttps;
import edu.bu.met.cs665.https.NewHttpsSystem;
import edu.bu.met.cs665.usb.LegacyUsbSystem;
import org.junit.Test;

/**
 * The legacy system holds customers A and B, the new system holds customer C.
 */
public class TestCustomerDataAdapter {
    private final Customer customerA = new Customer(1, "A", "A@gmail.com");
    private final Customer customerB = new Customer(2, "B", "B@gmail.com");
    private final Customer customerC = new Customer(101, "C", "C@gmail.com");

    /**
     * Create the legacy system with its mock data.
     *
     * @return a legacy system that holds customers A and B
     */
    private LegacyUsbSystem legacySystem() {
        LegacyUsbSystem legacySystem = new LegacyUsbSystem();
        legacySystem.addCustomer(customerA);
        legacySystem.addCustomer(customerB);
        return legacySystem;
    }

    /**
     * Create the new system with its mock data.
     *
     * @return a new system that holds customer C
     */
    private NewHttpsSystem newSystem() {
        NewHttpsSystem newSystem = new NewHttpsSystem();
        newSystem.addCustomer(customerC);
        return newSystem;
    }

    /**
     * Client code written for the new system: it knows only the new interface.
     *
     * @param source     any implementation of the new interface
     * @param customerId the id of the customer to look up
     * @return the customer's name
     */
    private String nameOf(CustomerDataViaHttps source, int customerId) {
        return source.getCustomerViaHttps(customerId).getName();
    }

    /**
     * The legacy system works on its own, through its own API.
     */
    @Test
    public void testLegacySystemReadsCustomerViaUsb() {
        LegacyUsbSystem legacySystem = legacySystem();

        Customer customer = legacySystem.getCustomerViaUsb(1);

        assertSame(customerA, customer);
    }

    /**
     * The new system works on its own, through its own API.
     */
    @Test
    public void testNewSystemReadsCustomerViaHttps() {
        NewHttpsSystem newSystem = newSystem();

        Customer customer = newSystem.getCustomerViaHttps(101);

        assertSame(customerC, customer);
        assertEquals("C@gmail.com", customer.getEmail());
    }

    /**
     * The required scenario: the new system's interface is used with the old system's API.
     */
    @Test
    public void testAdapterReadsLegacyCustomerThroughNewInterface() {
        // Given: a legacy system wrapped in the adapter, seen as the new interface
        CustomerDataViaHttps adapter = new UsbToHttpsAdapter(legacySystem());

        // When: the client calls the new system's method
        Customer customer = adapter.getCustomerViaHttps(2);

        // Then: it receives the record stored in the legacy system
        assertSame(customerB, customer);
        assertEquals("B", customer.getName());
    }

    /**
     * One piece of client code serves both systems, it cannot tell them apart.
     */
    @Test
    public void testSameClientCodeWorksWithBothSystems() {
        CustomerDataViaHttps nativeSource = newSystem();
        CustomerDataViaHttps adaptedSource = new UsbToHttpsAdapter(legacySystem());

        assertEquals("C", nameOf(nativeSource, 101));
        assertEquals("A", nameOf(adaptedSource, 1));
    }

    /**
     * The adapter stores no data. A customer added to the legacy system later is visible at once.
     */
    @Test
    public void testAdapterSeesLaterChangesOfLegacySystem() {
        LegacyUsbSystem legacySystem = legacySystem();
        CustomerDataViaHttps adapter = new UsbToHttpsAdapter(legacySystem);
        Customer customerD = new Customer(3, "D", "D@gmail.com");

        legacySystem.addCustomer(customerD);

        assertSame(customerD, adapter.getCustomerViaHttps(3));
    }

    /**
     * printCustomer is forwarded to the legacy system.
     * A known id prints, an unknown id is rejected by the legacy system itself.
     */
    @Test
    public void testAdapterForwardsPrintCustomer() {
        CustomerDataViaHttps adapter = new UsbToHttpsAdapter(legacySystem());

        adapter.printCustomer(1);

        try {
            adapter.printCustomer(999);
            fail("Expected an IllegalArgumentException for an unknown customer id");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("USB"));
        }
    }

    /**
     * A customer of the new system is not known to the legacy system, and the adapter does not hide that.
     */
    @Test
    public void testUnknownCustomerIsRejectedThroughAdapter() {
        CustomerDataViaHttps adapter = new UsbToHttpsAdapter(legacySystem());

        try {
            adapter.getCustomerViaHttps(101);
            fail("Expected an IllegalArgumentException for an unknown customer id");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("USB"));
        }
    }

    /**
     * An adapter without a legacy system to adapt cannot be created.
     */
    @Test
    public void testAdapterRequiresLegacySystem() {
        try {
            new UsbToHttpsAdapter(null);
            fail("Expected an IllegalArgumentException for a null legacy system");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}

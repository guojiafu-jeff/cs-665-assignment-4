/**
 * Name: JIAFU GUO
 * Course: CS-665 Software Designs & Patterns
 * Date: 11/10/2026
 * File Name: Main.java
 * Description: Console demo: client code written for the new HTTPS interface reads customers from the new system and, through the adapter, from the legacy USB system.
 */

package edu.bu.met.cs665;

import edu.bu.met.cs665.adapter.UsbToHttpsAdapter;
import edu.bu.met.cs665.customer.Customer;
import edu.bu.met.cs665.https.CustomerDataViaHttps;
import edu.bu.met.cs665.https.NewHttpsSystem;
import edu.bu.met.cs665.usb.LegacyUsbSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Client of the Adapter pattern.
 * It only uses the new interface, CustomerDataViaHttps.
 */
public class Main {
  private static final Logger LOGGER = LogManager.getLogger(Main.class);

  /**
   * Run the demo.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    LegacyUsbSystem legacySystem = new LegacyUsbSystem();
    legacySystem.addCustomer(new Customer(1, "A", "A@gmail.com"));
    legacySystem.addCustomer(new Customer(2, "B", "B@gmail.com"));

    NewHttpsSystem newSystem = new NewHttpsSystem();
    newSystem.addCustomer(new Customer(101, "C", "C@gmail.com"));

    // The same client code works with the new system ...
    showCustomer(newSystem, 101);

    // ... and with the legacy system, once it is wrapped in the adapter.
    CustomerDataViaHttps adaptedLegacySystem = new UsbToHttpsAdapter(legacySystem);
    showCustomer(adaptedLegacySystem, 1);
    showCustomer(adaptedLegacySystem, 2);
  }

  /**
   * Client code written for the new system.
   * It knows only the new interface and cannot tell whether the data comes from the HTTPS server or from the USB disk.
   *
   * @param source any implementation of the new interface
   * @param customerId the id of the customer to show
   */
  private static void showCustomer(CustomerDataViaHttps source, int customerId) {
    source.printCustomer(customerId);
    Customer customer = source.getCustomerViaHttps(customerId);
    LOGGER.info("Client received " + customer.getName() + " through the new interface");
  }
}

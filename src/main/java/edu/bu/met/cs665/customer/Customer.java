/**
 * Name: JIAFU GUO
 * Course: CS-665 Software Designs & Patterns
 * Date: 11/10/2026
 * File Name: Customer.java
 * Description: The customer record that the legacy system and the new system both return.
 */

package edu.bu.met.cs665.customer;

/**
 * One customer record: id, name and email address.
 * Both the legacy USB system and the new HTTPS system return objects of this class.
 * It is the only type the two systems share. And it's immutable.
 */
public class Customer {

    private final int id;
    private final String name;
    private final String email;

    /**
     * Create a customer record.
     *
     * @param id the customer's id, positive number
     * @param name the customer's name
     * @param email the customer's email address
     * @throws IllegalArgumentException if id is not positive, or name or email is null or blank
     */
    public Customer(int id, String name, String email) {
        if ((id <= 0) || (name == null) || (name.isBlank()) || (email == null) || (email.isBlank())) {
            throw new IllegalArgumentException("A positive id, a name and an email are required.");
        }
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Get the customer's id.
     * Both systems use it as the lookup key.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the customer's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the customer's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Describe the record for log messages: id, name and email in angle brackets.
     *
     * @return the record as one line of text
     */
    @Override
    public String toString() {
        return "Customer #" + id + " " + name + " <" + email + ">";
    }

}

package com.bank.models;

import java.time.LocalDate;

public class Client {
    private final String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Address address;
    private final LocalDate registrationDate;

    public Client(String id, String firstName, String lastName, String email, String phoneNumber, Address address){
        this.id = id;
        registrationDate = LocalDate.now();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNuber) { this.phoneNumber = phoneNuber; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public String getId() { return id; }
    public LocalDate getRegistrationDate() { return registrationDate; }

    @Override
    public String toString(){
        return String.format("%s, %s, %s, %s, %, %s", firstName, lastName, email, phoneNumber, address.toString(), registrationDate.toString());
    }
}

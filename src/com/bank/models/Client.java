package com.bank.models;

import java.time.LocalDate;
import java.util.UUID;

import com.bank.util.IdGenerator;

public class Client {
    private final String uniqueId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Address address;
    private final LocalDate registrationDate;

    private IdGenerator idGenerator;

    Client() {
        uniqueId = idGenerator.generateClientId();
        registrationDate = LocalDate.now();
    }

    Client(String firstName, String lastName, String email, String phoneNumber, Address address){
        uniqueId = String.valueOf(UUID.randomUUID());
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

    public String getUniqueId() { return uniqueId; }
    public LocalDate getRegistrationDate() { return registrationDate; }

    @Override
    public String toString(){
        return String.format("%s, %s, %s, %s, %, %s", firstName, lastName, email, phoneNumber, address.toString(), registrationDate.toString());
    }
}

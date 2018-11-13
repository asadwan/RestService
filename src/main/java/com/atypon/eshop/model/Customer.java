package com.atypon.eshop.model;;

public class Customer {

    private transient int id;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String country;


    public Customer() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) {
        this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city;  }


    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

}
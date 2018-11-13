package com.atypon.eshop.resources;


import com.atypon.eshop.model.Customer;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Path("/customers")
public class CustomersResource {

    private static final Gson gson = new Gson();
    private Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
    private AtomicInteger idCounter = new AtomicInteger();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(String body) {
        try {
            Customer customer = gson.fromJson(body, Customer.class);
            customer.setId(idCounter.incrementAndGet());
            customerDB.put(customer.getId(), customer);
            return Response.status(Response.Status.CREATED)
                    .entity(URI.create("/customer/" + customer.getId())).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Please check customer's details").build();
        }
    }


//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getCustomer(@PathParam("id") int id) {
//        Customer customer = customerDB.get(id);
//        if (customer == null) {
//            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
//                    .entity("No customer with id " + id + " exists").build());
//        }
//        String customerJson = gson.toJson(customer, Customer.class);
//        return customerJson;
//    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getCustomer(@QueryParam("id") int id) {
//        Customer customer = customerDB.get(id);
//        if (customer == null) {
//            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
//                    .entity("No customer with id " + id + " exists").build());
//        }
//        String customerJson = gson.toJson(customer, Customer.class);
//        return customerJson;
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomer(@MatrixParam("id") int id) {
        Customer customer = customerDB.get(id);
        if (customer == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("No customer with id " + id + " exists").build());
        }
        String customerJson = gson.toJson(customer, Customer.class);
        return customerJson;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int id, String body) {
        Customer customer = customerDB.get(id);
        if (customer == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("No customer with id '" + id + "' exists").build());
        }
        customer = gson.fromJson(body, Customer.class);
        customer.setId(id);
        customerDB.put(id, customer);
        return Response.status(Response.Status.OK)
                .entity("Updated Successfully").build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCustomers() {
        ArrayList<Customer> customers = customerDB.values().stream().collect(Collectors.toCollection(ArrayList::new));
        String customersJson = gson.toJson(customers);
        return customersJson;
    }

    @Path("{first}-{last}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomer(@PathParam("first") String firstName, @PathParam("last") String lastName) {
        Optional<Customer> customer = customerDB.values().stream().
                filter(customer1 -> customer1.getFirstName().equalsIgnoreCase(firstName) && customer1.getLastName().equalsIgnoreCase(lastName)).findFirst();

        if(customer.isPresent()) {
            return gson.toJson(customer.get());
        }

        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                .entity("No customer with name '" + firstName + " " + lastName + "' exists").build());
    }

}

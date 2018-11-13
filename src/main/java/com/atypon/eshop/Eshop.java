package com.atypon.eshop;

import com.atypon.eshop.resources.CustomersResource;
import com.atypon.eshop.resources.TestResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("eshop")
public class Eshop extends Application {

    private Set<Object> singltons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    @Override
    public Set<Class<?>> getClasses() {
        classes.add(TestResource.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        singltons.add(new CustomersResource());
        return singltons;
    }
}

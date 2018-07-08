package io.iktech.edu.hazelcastdemo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class Auto implements Serializable {

    private Long id;
    private String make;
    private String model;
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @JsonIgnore
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

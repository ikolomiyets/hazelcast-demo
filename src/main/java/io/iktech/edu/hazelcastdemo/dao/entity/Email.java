package io.iktech.edu.hazelcastdemo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class Email implements Serializable {

    private Long id;
    private String type = "HOM";
    private String emailAddress;
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

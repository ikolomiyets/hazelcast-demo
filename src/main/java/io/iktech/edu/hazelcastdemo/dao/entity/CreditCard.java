package io.iktech.edu.hazelcastdemo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class CreditCard implements Serializable {

    private Long id;
    private String type;
    private String cardNumber;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

package io.iktech.edu.hazelcastdemo.dao.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.*;

public class Customer implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String ssn;
    private String gender;
    private Date dateOfBirth;
    private String userId;
    private String currency;

    private Set<Address> addresses = new HashSet<>();
    private Set<CreditCard> creditCards = new HashSet<>();
    private Set<Email> emails = new HashSet<>();
    private Set<Auto> autos = new HashSet<>();
    private Set<Job> jobs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    public Set<Auto> getAutos() {
        return autos;
    }

    public void setAutos(Set<Auto> autos) {
        this.autos = autos;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

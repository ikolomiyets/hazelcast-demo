package io.iktech.edu.hazelcastdemo.dao;

import io.iktech.edu.hazelcastdemo.dao.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.*;

@org.springframework.stereotype.Service
public class CustomerService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Map<Long, Customer> findAll() {
        Logger logger = LoggerFactory.getLogger(CustomerService.class);
        Map<Long, Customer> customers = new HashMap<>();

        logger.info("Loading customers");
        jdbcTemplate.query("select * from customer", (rs) -> {
            Customer customer = new Customer();

            customer.setId(rs.getLong("ID"));
            customer.setUserId(rs.getString("USER_ID"));
            customer.setFirstName(rs.getString("FIRST_NAME"));
            customer.setLastName(rs.getString("LAST_NAME"));
            customer.setGender(rs.getString("GENDER"));
            customer.setCurrency(rs.getString("CURRENCY"));
            customer.setSsn(rs.getString("SSN"));
            customer.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
            customers.put(customer.getId(), customer);
        });

        logger.info("Loading customer addresses");
        jdbcTemplate.query("select * from customer_address", (rs) -> {
            Address address = new Address();

            address.setId(rs.getLong("ID"));
            address.setType(rs.getString("TYPE"));
            address.setLine1(rs.getString("LINE1"));
            address.setCity(rs.getString("CITY"));
            address.setState(rs.getString("STATE"));
            address.setZipCode(rs.getString("ZIP_CODE"));
            long customerId = rs.getLong("CUSTOMER_ID");
            Customer customer = customers.get(customerId);
            if (customer != null) {
                address.setCustomer(customer);
                customer.getAddresses().add(address);
            } else {
                logger.error("It is weird but cannot find customer {} in the collection", customerId);
            }
        });

        logger.info("Loading customer autos");
        jdbcTemplate.query("select * from customer_auto", (rs) -> {
            Auto auto = new Auto();

            auto.setId(rs.getLong("ID"));
            auto.setMake(rs.getString("MAKE"));
            auto.setModel(rs.getString("MODEL"));
            long customerId = rs.getLong("CUSTOMER_ID");
            Customer customer = customers.get(customerId);
            if (customer != null) {
                auto.setCustomer(customer);
                customer.getAutos().add(auto);
            } else {
                logger.error("It is weird but cannot find customer {} in the collection", customerId);
            }
        });

        logger.info("Loading customer credit cards");
        jdbcTemplate.query("select * from customer_credit_card", (rs) -> {
            CreditCard creditCard = new CreditCard();

            creditCard.setId(rs.getLong("ID"));
            creditCard.setType(rs.getString("TYPE"));
            creditCard.setCardNumber(rs.getString("CARD_NUMBER"));
            long customerId = rs.getLong("CUSTOMER_ID");
            Customer customer = customers.get(customerId);
            if (customer != null) {
                creditCard.setCustomer(customer);
                customer.getCreditCards().add(creditCard);
            } else {
                logger.error("It is weird but cannot find customer {} in the collection", customerId);
            }
        });

        logger.info("Loading customer emails");
        jdbcTemplate.query("select * from customer_email", (rs) -> {
            Email email = new Email();

            email.setId(rs.getLong("ID"));
            email.setType(rs.getString("TYPE"));
            email.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
            long customerId = rs.getLong("CUSTOMER_ID");
            Customer customer = customers.get(customerId);
            if (customer != null) {
                email.setCustomer(customer);
                customer.getEmails().add(email);
            } else {
                logger.error("It is weird but cannot find customer {} in the collection", customerId);
            }
        });

        logger.info("Loading customer jobs");
        jdbcTemplate.query("select * from customer_job", (rs) -> {
            Job job = new Job();

            job.setId(rs.getLong("ID"));
            job.setJobName(rs.getString("JOB_NAME"));
            long customerId = rs.getLong("CUSTOMER_ID");
            Customer customer = customers.get(customerId);
            if (customer != null) {
                job.setCustomer(customer);
                customer.getJobs().add(job);
            } else {
                logger.error("It is weird but cannot find customer {} in the collection", customerId);
            }
        });

        return customers;
    }

    public int findCount() {
        return jdbcTemplate.query("select count(*) from customer", (rs, rowNum) -> rs.getInt(1)).get(0);
    }

    public Customer getCustomerById(long id) {
        final Customer customer = new Customer();
        customer.setId(-1L);

        jdbcTemplate.query("select * from customer where id = ?", new Object[] { id }, (rs) -> {

            customer.setId(rs.getLong("ID"));
            customer.setUserId(rs.getString("USER_ID"));
            customer.setFirstName(rs.getString("FIRST_NAME"));
            customer.setLastName(rs.getString("LAST_NAME"));
            customer.setGender(rs.getString("GENDER"));
            customer.setCurrency(rs.getString("CURRENCY"));
            customer.setSsn(rs.getString("SSN"));
            customer.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
        });

        if (customer.getId() > -1L) {
            jdbcTemplate.query("select * from customer_address where customer_id = ?", new Object[]{ id }, (rs) -> {
                Address address = new Address();

                address.setId(rs.getLong("ID"));
                address.setType(rs.getString("TYPE"));
                address.setLine1(rs.getString("LINE1"));
                address.setCity(rs.getString("CITY"));
                address.setState(rs.getString("STATE"));
                address.setZipCode(rs.getString("ZIP_CODE"));
                address.setCustomer(customer);
                customer.getAddresses().add(address);
            });

            jdbcTemplate.query("select * from customer_auto where customer_id = ?", new Object[]{ id }, (rs) -> {
                Auto auto = new Auto();

                auto.setId(rs.getLong("ID"));
                auto.setMake(rs.getString("MAKE"));
                auto.setModel(rs.getString("MODEL"));
                auto.setCustomer(customer);
                customer.getAutos().add(auto);
            });

            jdbcTemplate.query("select * from customer_credit_card where customer_id = ?", new Object[]{ id }, (rs) -> {
                CreditCard creditCard = new CreditCard();

                creditCard.setId(rs.getLong("ID"));
                creditCard.setType(rs.getString("TYPE"));
                creditCard.setCardNumber(rs.getString("CARD_NUMBER"));
                creditCard.setCustomer(customer);
                customer.getCreditCards().add(creditCard);
            });

            jdbcTemplate.query("select * from customer_email where customer_id = ?", new Object[]{ id }, (rs) -> {
                Email email = new Email();

                email.setId(rs.getLong("ID"));
                email.setType(rs.getString("TYPE"));
                email.setEmailAddress(rs.getString("EMAIL_ADDRESS"));
                email.setCustomer(customer);
                customer.getEmails().add(email);
            });

            jdbcTemplate.query("select * from customer_job where customer_id = ?", new Object[]{ id }, (rs) -> {
                Job job = new Job();

                job.setId(rs.getLong("ID"));
                job.setJobName(rs.getString("JOB_NAME"));
                job.setCustomer(customer);
                customer.getJobs().add(job);
            });
        }

        return customer.getId() > -1L ? customer : null;
    }
}

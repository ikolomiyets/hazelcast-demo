package io.iktech.edu.hazelcastdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class Service {
    @Autowired
    CustomerRepository repository;

    public List<Customer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int findCount() {
        return repository.findCount();
    }

    public Customer getCustomerById(long id) {
        return repository.findById(id).get();
    }
}

package io.iktech.edu.hazelcastdemo;

import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class Service {
    @Autowired
    CustomerRepository repository;

    @PersistenceContext
    EntityManager em;

    @Qualifier("customerMap")
    @Autowired
    private IMap<Long, Customer> customerMap;

    public void fillCache() {
        repository.findAll().forEach(c -> {
            // Force lazy loading of the cascade data
            c.getAddresses().size();
            c.getAutos().size();
            c.getCreditCards().size();
            c.getEmails().size();
            c.getJobs().size();
            customerMap.put(c.getId(), c);
        });
        em.flush();
        em.clear();
    }

    public List<Customer> findAll(Pageable pageable) {
        List<Customer> customers = repository.findAll(pageable);

        customers.forEach(c -> {
            // Force lazy loading of the cascade data
            c.getAddresses().size();
            c.getAutos().size();
            c.getCreditCards().size();
            c.getEmails().size();
            c.getJobs().size();
        });
        em.flush();
        em.clear();
        return customers;
    }

    public int findCount() {
        return repository.findCount();
    }

    public Customer getCustomerById(long id) {
        return repository.findById(id).get();
    }
}

package io.iktech.edu.hazelcastdemo;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.ILock;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import io.iktech.edu.hazelcastdemo.dao.CustomerService;
import io.iktech.edu.hazelcastdemo.dao.entity.Customer;
import io.iktech.edu.hazelcastdemo.executors.EchoTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Qualifier("nameMap")
    @Autowired
    private IMap<String, String> nameMap;

    @Qualifier("customerMap")
    @Autowired
    private IMap<Long, Customer> customerMap;

    @Autowired
    private CustomerService service;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @RequestMapping(path = "putValue")
    public String putValue(@RequestParam(name = "key") String key, @RequestParam(name = "value") String value) {
        nameMap.put(key, value);
        return "{}";
    }

    @RequestMapping(path = "loadData")
    public String loadData() {
        int count = service.findCount();
        Map<Long, Customer> map = service.findAll();
        customerMap.putAll(map);
        logger.info("Data loaded into the cluster");
        return "{}";
    }

    @RequestMapping(path = "getValue")
    public String getValue(@RequestParam(name = "key") String key) {
        return String.format("{\"key\": \"%s\", \"value\": \"%s\"}", key, nameMap.get(key));
    }

    @RequestMapping(path = "getCustomerFromDb")
    public Customer getCustomerFromDb(@RequestParam(name = "id") Long id) {
        return service.getCustomerById(id);
    }

    @RequestMapping(path = "getCustomerFromCache")
    public Customer getCustomerFromCache(@RequestParam(name = "id") Long id) {
        return customerMap.get(id);
    }

    @RequestMapping(path = "clearCache")
    public String clearCache() {
        customerMap.clear();
        return "{}";
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Collection<Customer> sqlPredicate(@RequestBody String query) {
        return customerMap.values(new SqlPredicate(query));
    }

    @RequestMapping("/lockExample")
    public String lockExample() throws Exception {
        ILock myLock = hazelcastInstance.getLock("myLock");
        try {
            logger.info("Acquiring lock");
            myLock.lock();
            logger.info("Lock acquired");
            Thread.sleep(10000L);
            logger.info("lockExample() executed successfully");
        } finally {
            myLock.unlock();
        }

        return "{}";
    }

    @RequestMapping("/executeExample")
    public String executeExample() {
        IExecutorService executorService = hazelcastInstance.getExecutorService("exec");
        IntStream.range(1, 1000).forEach(i -> {
            executorService.submit(new EchoTask(i));
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {

            }
        });

        return "{}";
    }
}

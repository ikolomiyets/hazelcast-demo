package io.iktech.edu.hazelcastdemo;

import com.hazelcast.core.IMap;
import io.iktech.edu.hazelcastdemo.dao.CustomerService;
import io.iktech.edu.hazelcastdemo.dao.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
}

package io.iktech.edu.hazelcastdemo;

import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    private Service service;

    @RequestMapping(path = "putValue")
    public String putValue(@RequestParam(name = "key") String key, @RequestParam(name = "value") String value) {
        nameMap.put(key, value);
        return "{}";
    }

    @RequestMapping(path = "loadData")
    public String loadData() {
        int count = service.findCount();
        int batchSize = 10000;
        int currentCount = 0;
        int currentPage = 0;
        Map<Long, Customer> tmpMap = new HashMap<Long, Customer>(batchSize);
        while (currentCount < count) {
            service.findAll(PageRequest.of(currentPage, batchSize)).forEach(c -> tmpMap.put(c.getId(), c));
            currentCount += tmpMap.size();
            currentPage++;
            customerMap.putAll(tmpMap);
            tmpMap.clear();
            logger.info(String.format("Loaded page %d", currentPage));
        }
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

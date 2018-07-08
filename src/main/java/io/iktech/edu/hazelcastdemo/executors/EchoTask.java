package io.iktech.edu.hazelcastdemo.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class EchoTask implements Runnable, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(EchoTask.class);

    private int taskNumber;

    public EchoTask(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void run() {
        logger.info("Executing task {}", this.taskNumber);
    }
}

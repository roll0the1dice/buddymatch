package com.example.buddy_match.util;

import com.example.buddy_match.anotation.SnowflakeIdGenerator;
import com.example.buddy_match.model.atest.BuddyUser;
import com.example.buddy_match.model.atest.Team;
import java.io.Serializable;
import java.lang.reflect.Field;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * This is a generated SnowflakeIdGeneratorImplPlugin for demonstration purposes.
 */
public class SnowflakeIdGeneratorImpl implements IdentifierGenerator {
    /** This is an example. */
    private Long twepoch;

    /** This is an example. */
    private Long workerIdBits;

    /** This is an example. */
    private Long datacenterIdBits;

    /** This is an example. */
    private Long sequenceBits;

    /** This is an example. */
    private Long maxWorkerId;

    /** This is an example. */
    private Long maxDatacenterId;

    /** This is an example. */
    private Long sequenceMask;

    /** This is an example. */
    private Long workerIdShift;

    /** This is an example. */
    private Long datacenterIdShift;

    /** This is an example. */
    private Long timestampLeftShift;

    /** This is an example. */
    private Long workerId;

    /** This is an example. */
    private Long datacenterId;

    /** This is an example. */
    private Long sequence;

    /** This is an example. */
    private Long lastTimestamp;

    public SnowflakeIdGeneratorImpl() {
        twepoch = 1672531200000L;
        workerIdBits = 4L;
        datacenterIdBits = 2L;
        sequenceBits = 12L;
        
        maxWorkerId = (1L << workerIdBits) - 1;
        maxDatacenterId = (1L << datacenterIdBits) - 1;
        sequenceMask = (1L << sequenceBits) - 1;
        
        workerIdShift = sequenceBits;
        datacenterIdShift = sequenceBits + workerIdBits;
        timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        
        sequence = 0L;
        lastTimestamp = -1L;
        
        Class<BuddyUser> userClass = BuddyUser.class;
        
        Field[] fields = userClass.getDeclaredFields();
        try {
            for (Field __field : fields) {
                if (__field.isAnnotationPresent(jakarta.persistence.Id.class)) {
                    this.workerId = __field.getAnnotation(SnowflakeIdGenerator.class).workerId();
                    this.datacenterId = __field.getAnnotation(SnowflakeIdGenerator.class).datacenterId();
                    break;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("Worker ID out of range");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID out of range");
        }
    }

    public synchronized Long nextId() {
        long timestamp = System.currentTimeMillis();
        
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID");
        }
        
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                while ((timestamp = System.currentTimeMillis()) <= lastTimestamp) {
                    
                }
            }
        } else {
            sequence = 0L;
        }
        
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift)
        | (datacenterId << datacenterIdShift)
        | (workerId << workerIdShift)
        | sequence;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return nextId();
    }
}
package com.bank.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private AtomicLong transactionCounter;
    private AtomicLong clientCounter;

    public IdGenerator(){
        this.transactionCounter = new AtomicLong(1);
        this.clientCounter = new AtomicLong(1);
    }

    public String generateClientId(){
        return "CLT-" + String.format("%08d", clientCounter.getAndIncrement());
    }

    public String generateAccountNumber(String bankCode){
        String timeStamp = String.valueOf(System.currentTimeMillis() % 1000000);
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return String.format("%s-%s-%s", bankCode, timeStamp, random);
    }

    public String generateTransactionId(){
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sequence = String.format("%06d", transactionCounter.getAndIncrement());
        return "TXN-" + timeStamp + "-" + sequence;
    }

    public void resetCounters(){
        transactionCounter.set(1);
        clientCounter.set(1);
    }
}

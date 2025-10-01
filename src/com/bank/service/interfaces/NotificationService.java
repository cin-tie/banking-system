package com.bank.service.interfaces;

import com.bank.models.Client;

public interface NotificationService {
    public void sendTransferNotification(Client client, double amount, String fromAccount);
    
}

package com.bank.service.interfaces;

import com.bank.models.Client;

public interface NotificationService {
    public void sendTransferNotification(Client client, double amount, String fromAccount);
    public void sendDepositNotification(Client client, double amount);
    public void sendWithdrawalNotification(Client client, double amount);
    public void sendLowBalanceAlert(Client client, double currentBalance);
}

package com.bank.service.implementations;

import com.bank.models.Client;
import com.bank.service.interfaces.NotificationService;

public class EmailNotificationService implements NotificationService{
    private void sendEmail(String email, String subject, String message){
        System.out.println("=".repeat(50));
        System.out.println("EMAIL NOTIFICATION");
        System.out.println("To: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("=".repeat(50));
        System.out.println();
    }    

    @Override
    public void sendLowBalanceAlert(Client client, double currentBalance) {
        String message = String.format("Alert! Your account balance is currently at %.2f. We recommend topping up your balance.", currentBalance);
        sendEmail(client.getEmail(), "Low balance", message);
    }

    @Override
    public void sendWithdrawalNotification(Client client, double amount) {
        String message = String.format("%2f has been debited from your account. Dear %s %s!", amount, client.getFirstName(), client.getLastName());
        sendEmail(client.getEmail(), "Debit of funds", message);
    }

    @Override
    public void sendDepositNotification(Client client, double amount) {
        String message = String.format("%2f has been credited to your account. Dear %s %s!", amount, client.getFirstName(), client.getLastName());
        sendEmail(client.getEmail(), "Depositing funds", message);
    }

    @Override
    public void sendTransferNotification(Client client, double amount, String fromAccount) {
        String message = String.format("A transfer of %.2f was received from account %s. Dear %s %s!", amount, fromAccount, client.getFirstName(), client.getLastName());
        sendEmail(client.getEmail(), "Transfer of funds", message);
    }
}

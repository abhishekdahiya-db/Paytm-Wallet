package com.wallet.notifier.service.impl;

import com.wallet.kafka.DTO.UserCreatedKafkaObject;
import com.wallet.kafka.DTO.WalletUpdatedKafkaObject;
import com.wallet.notifier.service.NotifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

@Service
public class NotifierServiceImpl implements NotifierService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendWelcomeEmail(UserCreatedKafkaObject object) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("abhishekdahiya.dahiya48@gmail.com");
        mailMessage.setTo(object.getEmailId());
        mailMessage.setSubject("Welcome to Paytm wallet App.");
        mailMessage.setText(String.format("Hi %s, we are welcoming you to paytm e-wallet dummy App.",object.getName()));
        mailSender.send(mailMessage);
    }

    @Override
    public void balanceUpdateEmail(WalletUpdatedKafkaObject object) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("abhishekdahiya.dahiya48@gmail.com");
        mailMessage.setTo(object.getEmailId());
        mailMessage.setSubject("Wallet Balance Updated");
        mailMessage.setText(String.format("Hi %s, %s amount has been %s /n Your current balance is %s ."
                ,object.getName(),object.getTransactionAmount(),object.getAction(),object.getAmount()));
        mailSender.send(mailMessage);
    }
}

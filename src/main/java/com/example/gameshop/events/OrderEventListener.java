package com.example.gameshop.events;

import com.example.gameshop.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderEventListener {

    private final EmailService emailService;

    @EventListener
    public void onOrderComplete(OrderCompleteEvent event) {
        String subject = "Order Confirmation - GameShop";

        String message = String.format(
                "Hello %s,\n\n" +
                        "Thank you for your purchase! You have successfully bought: %s.\n" +
                        "Total Price: $%s\n\n" +
                        "Enjoy your games!",
                event.getUser().getUsername(),
                event.getGetNames(),
                event.getTotalPrice()
        );

        // რეალური იმეილის გაგზავნა
        emailService.sendEmail(event.getUser().getEmail(), subject, message);
    }
}
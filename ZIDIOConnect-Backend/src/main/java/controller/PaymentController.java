package com.zidioconnect.controller;

import com.zidioconnect.model.Payment;
import com.zidioconnect.model.Student;
import com.zidioconnect.service.EmailService;
import com.zidioconnect.service.PaymentService;
import com.zidioconnect.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public Payment createPayment(@RequestBody Payment payment) {
        // Save payment
        Payment savedPayment = paymentService.savePayment(payment);

        // Fetch student by ID (assumes studentId is passed inside the Payment object)
        Student student = studentService.getStudentById(payment.getStudentId());

        if (student != null) {
            // Prepare and send email
            String toEmail = student.getEmail();
            String subject = "Payment Successful - ZIDIOConnect";
            String body = "Hi " + student.getFullName() + ",\n\n" +
                          "Your payment of ₹" + savedPayment.getAmount() + " has been received successfully.\n\n" +
                          "Your subscription is now active. Enjoy using ZIDIOConnect!\n\n" +
                          "Regards,\nZIDIOConnect Team";

            emailService.sendEmail(toEmail, subject, body);
        }

        return savedPayment;
    }

    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}

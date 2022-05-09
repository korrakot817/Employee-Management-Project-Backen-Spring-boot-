package email.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //()ส่งไปหาใคร หัวข้ออะไร เนื้อความ( รูปแบบ html)
    public void send(String to, String subject, String html) {
        MimeMessagePreparator message = mimeMessage -> { // เปลี่ยนโค้ดใหเเป็น lambda สั้นลง

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(from + "@gmail.com"); // ส่งจากไหน
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

        };

        //mailSender.send(message);
        log.info("Mock Send Email");

    }
}

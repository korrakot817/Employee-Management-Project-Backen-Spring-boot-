package com.Project.Backend.business;

import com.Project.Backend.exception.BaseException;
import com.Project.Backend.exception.EmailException;
import com.project.common.EmailRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
@Log4j2
public class EmailBusiness {

    private final KafkaTemplate<String, EmailRequest> kafkaEmailTemplate;

    public EmailBusiness(KafkaTemplate<String, EmailRequest> kafkaTemplate) {
        this.kafkaEmailTemplate = kafkaTemplate;
    }

    public void sendActivateUserEmail(String email, String firstName, String lastName, String token) throws BaseException {
        // prepare content (HTML)
        String html;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }

        log.info("Token = " + token);

        String finalLink = "http://localhost:4200/activate/" + token;
        html = html.replace("${P_FIRSTNAME}", firstName);
        html = html.replace("${P_LASTNAME}", lastName);
        html = html.replace("${P_LINK}", finalLink);

        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setSubject("Please activate your account");
        request.setContent(html);

        // ส่งข้อมูล email ที่ register มา ไปให้ kafka โดยผ่าน topic ที่ชื่อว่า "activation-email"
        ListenableFuture<SendResult<String, EmailRequest>> future = kafkaEmailTemplate.send("activation-email", request);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            // ส่งไม่ได้
            public void onFailure(Throwable throwable) {
                log.error("Kafka send fail");
                log.error(throwable);
            }

            @Override
            //ส่งได้
            public void onSuccess(SendResult<String, EmailRequest> result) {
                log.info("Kafka send success");
                log.info(result);
            }
        });
    }

    //ฟังชั่นอ่าน HTML
    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));
    }

}

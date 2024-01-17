package logonedigital.webappapi.service.emailFeature;

import logonedigital.webappapi.entity.Activation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void notify(Activation activation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ngueemmanuel.prof@gmail.com");
        message.setTo(activation.getUser().getEmail());
        message.setSubject("Votre code d'activation");
        message.setText("Bonjour Mr/Mne "+activation.getUser().getFirstname()+" "+activation.getUser().getLastname()+"<br>"+"Your activation code is: " + activation.getActivationCode());

        javaMailSender.send(message);
    }

}

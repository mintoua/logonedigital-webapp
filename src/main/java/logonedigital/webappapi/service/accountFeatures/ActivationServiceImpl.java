package logonedigital.webappapi.service.accountFeatures;

import logonedigital.webappapi.entity.Activation;
import logonedigital.webappapi.entity.User;
import logonedigital.webappapi.exception.ProcessFailureException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.ActivationRepo;
import logonedigital.webappapi.service.emailFeature.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@AllArgsConstructor
public class ActivationServiceImpl implements ActivationService {

    private final ActivationRepo activationRepo;
    private final EmailService emailService;


    private String generateActivationCode(){
        Random random = new Random();
        Integer randomInteger = random.nextInt(9999);
        return String.format("%06d", randomInteger);
    }

    @Override
    public void saveActivationCode(User user) {
        Activation activation = new Activation();
        Instant activatedAt = Instant.now();
        activation.setCreatedAt(activatedAt);
        activation.setExpiredAt(activatedAt.plus(10, ChronoUnit.MINUTES));
        activation.setActivationCode(this.generateActivationCode());
        activation.setUser(user);
        try{
            this.emailService.notify(activation);
        }catch (Exception ex){
            throw new ProcessFailureException("Something wrong with Emailing server !");
        }
        this.activationRepo.save(activation);
    }

    @Override
    public Activation fetchByActivationCode(String code) {

        return this.activationRepo.findByActivationCode(code)
                .orElseThrow(()->new RessourceNotFoundException("Activation code doesn't exist"));
    }
}

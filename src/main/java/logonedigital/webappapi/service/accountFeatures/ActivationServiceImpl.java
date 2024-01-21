package logonedigital.webappapi.service.accountFeatures;

import logonedigital.webappapi.entity.Activation;
import logonedigital.webappapi.entity.User;
import logonedigital.webappapi.exception.ProcessFailureException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.ActivationRepo;
import logonedigital.webappapi.service.emailFeature.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
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
        Optional<Activation> activationOptional = this.activationRepo.findByUser(user);
        Activation activation = new Activation();

        if(activationOptional.isPresent())
        {
            activation = activationOptional.get();
            activation.setCreatedAt(Instant.now());
            activation.setExpiredAt(Instant.now().plus(10, ChronoUnit.MINUTES));
            activation.setActivationCode(this.generateActivationCode());
        }
        else{
            Instant activatedAt = Instant.now();
            activation.setCreatedAt(activatedAt);
            activation.setExpiredAt(activatedAt.plus(10, ChronoUnit.MINUTES));
            activation.setActivationCode(this.generateActivationCode());
            activation.setUser(user);
        }

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

    @Override
    public void saveActivationCodeWhenResetPassword(User user) {
        Activation activation = this.activationRepo
                .findByUser(user)
                .orElseThrow(()->new RessourceNotFoundException("Activation code doesn't exist"));
        Instant activatedAt = Instant.now();
        activation.setCreatedAt(Instant.now());
        activation.setExpiredAt(Instant.now().plus(10, ChronoUnit.MINUTES));
        //activation.setUpdatedAt();
        activation.setActivationCode(this.generateActivationCode());

        try{
            this.emailService.notify(activation);
        }catch (Exception ex){
            throw new ProcessFailureException("Something wrong with Emailing server !");
        }
        try {
            this.activationRepo.save(activation);
        }catch (Exception ex){
            throw new ProcessFailureException("Problem occurred when we're saving activation code!");
        }
    }

    @Scheduled(cron = "@daily")
    public void clearExpiredActivationCode(){
        log.info("suppression du code d'activation à {}", Instant.now());
        this.activationRepo.deleteAllByExpiredAtBefore(Instant.now());
    }
}

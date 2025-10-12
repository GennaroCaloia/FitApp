package it.gennaro.fitapp.config;

import it.gennaro.fitapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer {

    /**
     * Inizializza i dati di sviluppo essenziali (come le password degli utenti)
     * dopo che il database è stato popolato da Liquibase.
     * <p>
     * Questo metodo garantisce che l'hash della password per l'utente "demo" sia
     * coerente e generato utilizzando l'istanza {@code BCryptPasswordEncoder}
     * attiva nel contesto Spring. Viene eseguito solo all'avvio dell'applicazione
     * sotto il profilo "dev" e previene problemi di autenticazione dovuti
     * a hash statici non validi inseriti direttamente negli script SQL/YAML.
     *
     * @param userRepository Il repository per accedere e salvare le entità User.
     * @param passwordEncoder Il PasswordEncoder configurato (deve essere BCrypt)
     * utilizzato per generare l'hash della password.
     * @return Un {@code CommandLineRunner} che esegue la logica di inizializzazione.
     */
    @Bean
    @Transactional
    public CommandLineRunner initDevData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.findByUsername("demo").ifPresent(user -> {
                // Aggiorna l'hash della password usando il PasswordEncoder attivo nel contesto
                String newHash = passwordEncoder.encode("demo");
                user.setPasswordHash(newHash);
                userRepository.save(user);
                System.out.println("Hash per 'demo' rigenerato con l'encoder di sistema.");
            });
        };
    }

}

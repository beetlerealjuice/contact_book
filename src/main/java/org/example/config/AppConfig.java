package org.example.config;

import org.example.ConsoleUI;
import org.example.ContactInitializer;
import org.example.services.ContactService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("org.example")
@PropertySource("classpath:application.properties")

public class AppConfig {

    @Value("${contacts.file}")
    private String contactsFile;

    @Value("${default.contacts.file}")
    private String defaultContactsFile;

    @Bean
    @Profile("init")
    public ContactInitializer contactInitializer() {
        return new ContactInitializer(defaultContactsFile);
    }

    @Bean
    public ContactService contactService() {
        return new ContactService(contactsFile);
    }
    @Bean
    public ConsoleUI consoleUI(ContactService contactService) {
        return new ConsoleUI(contactService);
    }
}

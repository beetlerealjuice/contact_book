package org.example;

import org.example.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class ContactInitializer {
    private final String defaultContactsFile;

    @Autowired
    private ContactService contactService;

    public ContactInitializer(@Value("${contacts.file}") String defaultContactsFile) {
        this.defaultContactsFile = defaultContactsFile;
    }

    @PostConstruct
    public void initContacts() {
        try {
            contactService.loadContacts(defaultContactsFile);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке контактов: " + e.getMessage());
        }
    }
}

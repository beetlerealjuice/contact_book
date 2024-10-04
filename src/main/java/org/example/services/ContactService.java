package org.example.services;

import org.example.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ContactService {

    private final String contactsFile;
    private List<Contact> contacts = new ArrayList<>();

    public ContactService(@Value("${contacts.file}") String contactsFile) {
        this.contactsFile = contactsFile;
    }

    public void loadContacts(String filename) throws IOException {
        File file = new File(filename);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
            reader.close();
        }
    }

    public void printContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Список контактов пуст.");
            return;
        }
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void addContact(String fullName, String phoneNumber, String email) {
        if (isValidEmail(email)) {
            contacts.add(new Contact(fullName, phoneNumber, email));
            System.out.println("Контакт добавлен.");
        } else {
            System.out.println("Неверный формат адреса электронной почты.");
        }
    }

    public void deleteContact(String email) {
        if (isValidEmail(email)) {
            Iterator<Contact> iterator = contacts.iterator();
            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if (contact.getEmail().equals(email)) {
                    iterator.remove();
                    System.out.println("Контакт удален.");
                    return;
                }
            }
            System.out.println("Контакт с таким адресом электронной почты не найден.");
        } else {
            System.out.println("Неверный формат адреса электронной почты.");
        }
    }

    public void saveContacts() {
        try (PrintWriter writer = new PrintWriter(contactsFile)) {
            for (Contact contact : contacts) {
                writer.println(contact.getFullName() + ";" + contact.getPhoneNumber() + ";" + contact.getEmail());
            }
            System.out.println("Контакты сохранены.");
            System.out.println("Contacts file path: " + contactsFile);

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении контактов: " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w-_]+\\.)+[\\w-_]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
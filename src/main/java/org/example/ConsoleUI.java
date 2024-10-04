package org.example;

import org.example.services.ContactService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUI {

    private final ContactService contactService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public ConsoleUI(ContactService contactService) {
        this.contactService = contactService;
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выбран профиль: " + activeProfile);
            System.out.println("Выберите действие:");
            System.out.println("1. Вывести все контакты");
            System.out.println("2. Добавить новый контакт");
            System.out.println("3. Удалить контакт");
            System.out.println("4. Сохранить контакты");
            System.out.println("5. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1 -> contactService.printContacts();
                case 2 -> {
                    System.out.print("Введите данные нового контакта (Ф. И. О.; номер телефона; адрес электронной почты): ");
                    String input = scanner.nextLine();
                    String[] parts = input.split(";");
                    if (parts.length == 3) {
                        String fullName = parts[0].trim();
                        String phoneNumber = parts[1].trim();
                        String email = parts[2].trim();
                        contactService.addContact(fullName, phoneNumber, email);
                    } else {
                        System.out.println("Неверный формат ввода. Попробуйте снова.");
                    }
                }
                case 3 -> {
                    System.out.print("Введите адрес электронной почты контакта для удаления: ");
                    String email = scanner.nextLine();
                    contactService.deleteContact(email);
                }
                case 4 -> contactService.saveContacts();
                case 5 -> {
                    System.out.println("До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}


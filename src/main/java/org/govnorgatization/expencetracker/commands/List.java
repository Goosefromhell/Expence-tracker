package org.govnorgatization.expencetracker.commands;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

@CommandLine.Command(name = "list", description = "gives list of all expenses")

public class List implements Runnable {

    Path target = Path.of(System.getProperty("user.home"), "Buffers", "Expense-tracker", "test.csv");

    public File file = target.toFile();
    @Override
    public void run() {
        System.out.printf("%-3s %-10s %-15s %3s%n", "ID", "Date", "Description", "Amount");
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",", 4);
                String id = parts[0];
                String date = parts[1];
                String description = parts[2];
                String amount = parts[3];
                System.out.printf("%-3s %-10s %-15s %3s%n", id, date, description, amount);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading file: " + e);
        }


    }
}

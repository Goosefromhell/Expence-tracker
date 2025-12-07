package org.govnorgatization.expencetracker.commands;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@CommandLine.Command(name = "list", description = "gives list of all expenses")

public class List implements Runnable {


    @Override
    public void run() {
        System.out.printf("%-3s %-10s %-15s %3s%n", "ID", "Date", "Description", "Amount");
        try (Scanner scanner = new Scanner(new File("test.csv"))) {
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

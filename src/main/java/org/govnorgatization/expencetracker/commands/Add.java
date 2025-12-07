package org.govnorgatization.expencetracker.commands;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@CommandLine.Command(name = "add", description = "add new expence")
public class Add implements Runnable {
    @CommandLine.Option(names = {"-d", "--description"}, arity = "1..*")
    List<String> description;
    @CommandLine.Option(names = {"-a", "--amount"})
    String amount;

    @Override
    public void run() {
        File file = new File("test.csv");
        ArrayList<Integer> ids = new ArrayList<>();
        boolean firstLine = true;
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                int id = Integer.parseInt(line.substring(0, line.indexOf(",")));
                ids.add(id);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading file: " + e);
        }
        if (null != description && null != amount) { // Если нет хедера то все пойдет по одному месту
            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            try (FileWriter writer = new FileWriter("test.csv", true)) {
                writer.write(String.format("%d,%s,%s,%s\n", (ids.getLast() + 1), time.format(formatter) , String.join(" ", description), amount));
                System.out.println("test.csv создан!");

            } catch (IOException e) {
                System.out.println("Error occurred while saving information to file: " + e);
            }
        }
    }
}
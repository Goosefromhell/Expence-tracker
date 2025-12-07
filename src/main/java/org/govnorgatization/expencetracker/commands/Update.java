package org.govnorgatization.expencetracker.commands;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@CommandLine.Command(name = "update", description = "update existing expense")

public class Update implements Runnable {
    public final String HEADER = "ID,Date,Description,Amount";
    public File file = new File("test.csv");

    @CommandLine.Option(names = {"-i", "--id"}, required = true, description = "First write the id of expense to update(you can see id's with \"list\" command)")
    String expense_to_update;
    @CommandLine.Option(names = {"-d", "--description"}, arity = "1..*", required = true, description = "Second write new description of expense")
    List<String> description;
    @CommandLine.Option(names = {"-a", "--amount"}, required = true, description = "Third change amount of money spent")
    String amount;

    @Override
    public void run() {
        boolean firstLine = true;


        StringBuilder after_update = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (firstLine) {
                    after_update.append(HEADER);
                    after_update.append("\n");
                    firstLine = false;
                    continue;
                }
                if (!line.substring(0, line.indexOf(",")).equals(expense_to_update)) {
                    after_update.append(line);
                    after_update.append("\n");
                } else {
                    LocalDateTime time = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    after_update.append(String.format("%s,%s,%s,%s\n", line.substring(0, line.indexOf(",")), time.format(formatter), String.join(" ", description), amount));
                }
            }
            try (FileWriter writer = new FileWriter("test.csv")) {
                writer.write(after_update.toString());
            }
        } catch (IOException e) {
            System.out.println("Error occurred while working with file: " + e);
        }


    }
}

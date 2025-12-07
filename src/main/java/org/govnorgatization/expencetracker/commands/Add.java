package org.govnorgatization.expencetracker.commands;

import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@CommandLine.Command(name = "add", description = "add new expence")
public class Add implements Runnable {
    @CommandLine.Option(names = {"-d", "--description"}, arity = "1..*", required = true, description = "First write description of expense")
    List<String> description;
    @CommandLine.Option(names = {"-a", "--amount"}, required = true, description = "Second add amount of money spent")
    String amount;
    Path target = Path.of(System.getProperty("user.home"), "Buffers", "Expense-tracker", "test.csv");

    public File file = target.toFile();

    @Override
    public void run() {
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
        } catch (FileNotFoundException e) {
            try {
                if (file.createNewFile()) {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write("ID,Date,Description,Amount\n");

                    } catch (IOException j) {
                        System.out.println("Something went wrong while creating buffer file: " + j);
                    }

                }
            } catch (IOException ex) {
                System.out.println("Something went wrong while creating buffer file: " + ex);
                throw new RuntimeException(ex);
            }
        }
        if (Integer.parseInt(amount) <= 0) {
            System.out.println("Spent amount of money cannot be less/equals to zero");
            return;
        }
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(String.format("%d,%s,%s,%s\n", (ids.isEmpty() ? 1 : ids.getLast() + 1), time.format(formatter), String.join(" ", description), amount));
            System.out.println("Expense successfully added");

        } catch (IOException e) {
            System.out.println("Error occurred while saving information to file: " + e);
        }
    }
}

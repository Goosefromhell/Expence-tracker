package org.govnorgatization.expencetracker.commands;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

@CommandLine.Command(name = "delete", description = "delete task by id")
public class Delete implements Runnable {
    public final String HEADER = "ID,Date,Description,Amount";
    Path target = Path.of(System.getProperty("user.home"), "Buffers", "Expense-tracker", "test.csv");

    public File file = target.toFile();
    @CommandLine.Option(names = {"-i", "--id"}, required = true, description = "Write the id of expense to delete(you can see id's with \"list\" command)")
    String expense_to_delete;


    @Override
    public void run() {
        boolean firstLine = true;


        StringBuilder after_deletion = new StringBuilder();
        boolean found = false;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (firstLine) {
                    after_deletion.append(HEADER);
                    after_deletion.append("\n");
                    firstLine = false;
                    continue;
                }
                if (line.substring(0, line.indexOf(",")).equals(expense_to_delete)) {
                    after_deletion.append(line);
                    after_deletion.append("\n");
                    continue;
                }
                found = true;
            }
            if (!found) {
                System.out.println("There is no expense with such id");
                return;
            }
            try (FileWriter writer = new FileWriter("kek.csv")) {
                writer.write(after_deletion.toString());
            }
        } catch (IOException e) {
            System.out.println("Error occurred while working with file: " + e);
        }

    }

}

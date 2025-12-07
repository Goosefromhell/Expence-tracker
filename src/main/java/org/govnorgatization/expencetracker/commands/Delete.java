package org.govnorgatization.expencetracker.commands;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

@CommandLine.Command(name = "delete", description = "delete task by id")
public class Delete implements Runnable {
    public final String HEADER = "ID,Date,Description,Amount";
    public File file = new File("test.csv");
    @CommandLine.Option(names = {"-i", "--id"})
    String task_to_delete_id;


    @Override
    public void run() {
        boolean firstLine = true;

        if (null == task_to_delete_id) {
            System.out.println("wrong format");
            return;
        }
        StringBuilder after_deletion = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (firstLine) {
                    after_deletion.append(HEADER);
                    after_deletion.append("\n");
                    firstLine = false;
                    continue;
                }
                if (!line.substring(0, line.indexOf(",")).equals(task_to_delete_id)) {
                    after_deletion.append(line);
                    after_deletion.append("\n");
                }
            }
            try (FileWriter writer = new FileWriter("test.csv")) {
                writer.write(after_deletion.toString());
            }
        } catch (IOException e) {
            System.out.println("Error occurred while working with file: " + e);
        }

    }

}

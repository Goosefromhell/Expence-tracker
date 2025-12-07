package org.govnorgatization.expencetracker;


import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@CommandLine.Command(name = "app", mixinStandardHelpOptions = false)
class Main implements Runnable {
    public final String HEADER = "ID,Date,Description,Amount";
    @CommandLine.Parameters
    String cmd;

    @Option(names = {"-d", "--description"}, arity = "1..*")
    List<String> description;
    @Option(names = {"-a", "--amount"})
    String amount;
    @Option(names = {"--id"})
    String task_to_delete_id;

    static void main(String[] args) {
        int exit = new CommandLine(new Main()).execute(args);
        System.exit(exit);

    }

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
        System.out.println(Arrays.toString(ids.toArray()));
        firstLine = true;
        if ("add".equals(cmd)) {

            int text = 2;
            if (null != description && null != amount) { // Если нет хедера то все пойдет по одному месту

                try (FileWriter writer = new FileWriter("test.csv", true)) {
                    writer.write(String.format("%d,%d,%s,%s\n", (ids.getLast() + 1), text, String.join(" ", description), amount));
                    System.out.println("test.csv создан!");

                } catch (IOException e) {
                    System.out.println("Error occurred while saving information to file: " + e);
                }
            }

        } else if ("delete".equals(cmd)) {
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
}



package org.govnorgatization.expencetracker.commands;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

@CommandLine.Command(name = "sum", description = "Shows summary of expenses(month filter allowed)")
public class Summary implements Runnable {
    Path target = Path.of(System.getProperty("user.home"), "Buffers", "Expense-tracker", "test.csv");

    public File file = target.toFile();

    @CommandLine.Option(names = {"-m", "--month"}, description = "Write month nuber to get expense from particular month")
    String month;


    @Override
    public void run() {
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            double summa = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int line_len = line.length();
                String amount = line.substring(line.lastIndexOf(",") + 1, line_len);
                if (null == month) {
                    summa += Double.parseDouble(amount);
                } else {
                    if (line.substring(line.indexOf("-") + 1, line.lastIndexOf("-")).equals(month)) {
                        summa += Double.parseDouble(amount);
                    }
                }
            }
            System.out.println(summa);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e);
        }
    }
}

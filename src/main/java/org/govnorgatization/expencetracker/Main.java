package org.govnorgatization.expencetracker;


import org.govnorgatization.expencetracker.commands.*;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@CommandLine.Command(name = "app", subcommands = {Add.class, Delete.class, List.class, Update.class, Summary.class}, mixinStandardHelpOptions = true)
class Main implements Runnable {
    static void main(String[] args) {
        Path target = Path.of(System.getProperty("user.home"), "Buffers", "Expense-tracker", "expences.csv");

        try {
            Files.createDirectories(target.getParent());

        } catch (IOException e) {
            System.out.println("Error occurred while creating directory for buffer file ");
        }


        final CommandLine cmd = new CommandLine(new Main());

        cmd.setUnmatchedArgumentsAllowed(false);

        cmd.setExecutionExceptionHandler((ex, commandLine, _) -> {
            System.out.println("Error: " + ex.getMessage());
            if ("Range [0, -1) out of bounds for length 0".equals(ex.getMessage())) {
                System.out.println("There is no expenses to work with");
            }
            commandLine.usage(System.out);
            return 1;
        });

        cmd.execute(args);
    }

    public void run() {
        System.out.println("Main command");
    }
}



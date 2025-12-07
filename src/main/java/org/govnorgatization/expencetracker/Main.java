package org.govnorgatization.expencetracker;


import org.govnorgatization.expencetracker.commands.*;
import picocli.CommandLine;

@CommandLine.Command(name = "app", subcommands = {Add.class, Delete.class, List.class, Update.class, Summary.class}, mixinStandardHelpOptions = true)
class Main implements Runnable {
    static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Main());

        cmd.setUnmatchedArgumentsAllowed(false);

        cmd.setExecutionExceptionHandler((ex, commandLine, _) -> {
            System.out.println("ошибка: " + ex.getMessage());
            commandLine.usage(System.out);
            return 1;
        });

        cmd.execute(args);
    }

    public void run() {
        System.out.println("Main command");
    }
}



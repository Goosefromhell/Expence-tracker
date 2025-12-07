package org.govnorgatization.expencetracker;


import org.govnorgatization.expencetracker.commands.Add;
import org.govnorgatization.expencetracker.commands.Delete;
import org.govnorgatization.expencetracker.commands.List;
import picocli.CommandLine;

@CommandLine.Command(name = "app", subcommands = {Add.class, Delete.class,List.class}, mixinStandardHelpOptions = true)
class Main implements Runnable {
    static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Main());

        cmd.setUnmatchedArgumentsAllowed(false);

        cmd.setExecutionExceptionHandler((ex, commandLine, parseResult) -> {
            System.out.println("ошибка: " + ex.getMessage());
            commandLine.usage(System.out); // ← вывод списка команд
            return 1;
        });

        cmd.execute(args);
    }

    public void run() {
        System.out.println("Main command");
    }
}



package com.company;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Main {

    @Option(name = "-u", aliases = "--user", required = true, usage = "user name.")
    private String user;

    @Option(name = "-s", aliases = "--password", usage = "password.")
    private String password;

    public static void main(String[] args) {
        final Main main = new Main();
        final CmdLineParser cmdParser = new CmdLineParser(main);

        try {
            cmdParser.parseArgument(args);
            main.display();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            cmdParser.printUsage(System.err);
            System.exit(1);
        }
    }

    public void display() {
        System.out.println("user: " + user);
        System.out.println("password: " + password);
    }
}

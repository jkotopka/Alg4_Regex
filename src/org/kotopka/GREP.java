package org.kotopka;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GREP {

    public static void main(String[] args) {
        String regexp = "(.*" + args[0] + ".*)";
        NFA nfa = new NFA(regexp);
        List<String> input;

        try {
            input = Files.readAllLines(Paths.get(args[1]));

            for (String s : input) {
                if (nfa.recognizes(s)) {
                    System.out.println(s);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

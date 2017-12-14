package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class YoDogRegisters {
    public static class Rule {
        String affectedVar;
        Integer addValue;
        String condition;
    }

    public enum COMPARE_STRING {
        EQUALS("==") {
            @Override
            public boolean compare(int v1, int v2) {
                return v1 == v2;
            }
        },
        NOT_EQUALS("!=") {

            @Override
            public boolean compare(int v1, int v2) {
                return v1 != v2;
            }
        },
        GREATER_THAN(">") {
            @Override
            public boolean compare(int v1, int v2) {
                return v1 > v2;
            }
        },
        LESS_THAN("<") {
            @Override
            public boolean compare(int v1, int v2) {
                return v1 < v2;
            }
        },

        GREATER_THAN_OR_EQUALS(">=") {
            @Override
            public boolean compare(int v1, int v2) {
                return v1 >= v2;
            }
        },
        LESS_THAN_OR_EQUALS("<=") {
            @Override
            public boolean compare(int v1, int v2) {
                return v1 <= v2;
            }
        };

        private String str;
        COMPARE_STRING(String str) {
            this.str = str;
        }

        static COMPARE_STRING fromString(String str) {
            for (COMPARE_STRING v : values()) {
                if (v.str.equals(str)) return v;
            }
            return null;
        }

        //Must OVERRIDE this
        public boolean compare(int v1, int v2) {
            throw new UnsupportedOperationException();
        }
    }

    static Set<String> vars = new HashSet<>();
    static List<Rule> rules = new ArrayList<>();

    public static void parseFile(File f) throws FileNotFoundException {
        try (Scanner scan = new Scanner(f)) {
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                String[] pieces = s.split(" ");
                Rule r = new Rule();
                r.affectedVar = pieces[0];
                vars.add(pieces[0]);
                int modifier = -1;
                if (pieces[1].equals("inc")) {
                    modifier = 1;
                }
                r.addValue = modifier * Integer.parseInt(pieces[2]);
                vars.add(pieces[4]);
                r.condition = pieces[4] + " " + pieces[5] + " " + pieces[6];
                rules.add(r);
            }
        }
    }

    public static int largestValue() {
        Map<String, Integer> values = new HashMap<>();
        //First initialize all found vars
        for (String var : vars) {
            values.put(var, 0);
        }
        //Now go through rules

        int max = Integer.MIN_VALUE;
        for (Rule r : rules) {
            //First get condition
            String[] cond = r.condition.split(" ");
            String checkVar = cond[0];
            int endVar = Integer.parseInt(cond[2]);
            if (COMPARE_STRING.fromString(cond[1]).compare(values.get(checkVar), endVar)) {
                int curValue = values.get(r.affectedVar);
                values.put(r.affectedVar, curValue + r.addValue);
            }
            for (int i : values.values()) {
                if (i > max) max = i;
            }
        }

        return max;
    }

    public static void main(String[] args) throws FileNotFoundException {
        parseFile(new File("day8input.txt"));
        System.out.println(largestValue());
    }

}

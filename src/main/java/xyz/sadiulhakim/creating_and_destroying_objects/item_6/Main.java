package xyz.sadiulhakim.creating_and_destroying_objects.item_6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String[] input = {"12345", "1212", "3423", "4534", "4567", "5678"};

        for (String s : input) {
            // Check if the input contains only digits
            boolean isOnlyDigits = s.matches("\\d+");
        }

        // ⚠️  ️matches() creates a new instance of Patten each time called
        // To improve the performance crate the Pattern once and reuse it.

        Pattern pattern = Pattern.compile("\\d+");
        for (String s : input) {
            Matcher matcher = pattern.matcher(s);
            boolean isOnlyDigits2 = matcher.matches();
        }

        // Do not use Long
        long startLong = System.currentTimeMillis();
        Long sum = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        long endLong = System.currentTimeMillis();
        System.out.println("Long took : " + (endLong - startLong) / 1000.0);

        // use primitive long
        long startlong = System.currentTimeMillis();
        long sum2 = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum2 += i;
        }
        long endlong = System.currentTimeMillis();
        System.out.println("long took : " + (endlong - startlong) / 1000.0);
    }
}

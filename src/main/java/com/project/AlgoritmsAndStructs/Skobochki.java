package com.project.AlgoritmsAndStructs;

import java.io.*;

public class Skobochki {
    static int counter;
    static void printSkobochki(String sequence, int open, int close, int n) {
        counter++;
        System.out.println("Шаг " + counter + ": " + sequence);
        if (sequence.length() == 2*n) {
            System.out.println("Ответ: " + sequence);
            return;
        }
        if (open<n) {
            printSkobochki(sequence+"(", open+1, close, n);
            System.out.println("Exit from first recursion");
        }
        if (close<open) {
            printSkobochki(sequence+")", open, close+1, n);
            System.out.println("Exit from second recursion");
        }
    }

    public void m() {

    }

    public static void main(String[] args) throws Exception {
//        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
//        printSkobochki("", 0, 0, Integer.parseInt(r.readLine()));
        printSkobochki("", 0, 0, 3);
    }
}

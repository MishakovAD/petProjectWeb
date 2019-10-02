package com.project.AlgoritmsAndStructs;

public class Fibonacci {
    public int fibonacci(int n, int first, int second, int index) {
        if (n == 0) {
            System.out.println(0);
            return 0;
        }
        if (n == 1) {
            System.out.println(1);
            return 1;
        }
        if (index == 0) {
            System.out.print(0 + " ");
        }
        int fib = first + second;
        if (index == n - 1) {
            return fib;
        }
        System.out.print(fib + " ");
        index++;
        fibonacci(n, second, fib, index);
        return fib;
    }

    public static void main(String[] args) {
        new Fibonacci().fibonacci(40, 0, 1, 0);
    }
}

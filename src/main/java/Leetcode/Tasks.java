package Leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class Tasks {
    public static void main(String[] args) {
        Tasks t = new Tasks();
        //--------------TEST------------------
        t.isValid("(({})[])");
        t.reverse(1534236469);
        //------------------------------------

        System.out.println();
    }

    /*
    https://leetcode.com/problems/reverse-integer/
     */
    public int reverse(int x) {
        if (x > 2147483647 || x <= -2147483648) {
            return 0;
        }
        int res = 0;
        double stop = 1;
        for (int i = 0; i < (int)(Math.log10(Math.abs(x))+1); i++) {
            stop *= 10;
        }
        for (double i = 10; i <= stop ; i = i * 10) {
            res += (x % i / (i/10)) * (stop / i);
            x = (int) (x - x % i);
        }
        if (res >= 2147483647 || res <= -2147483648) {
            return 0;
        }
        return res;
    }

    /*
    https://leetcode.com/problems/valid-parentheses/
     */
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) return false;
        if (s.length() == 0) return true;

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur == '[' || cur == '(' || cur == '{') stack.add(cur);
            else {
                if (stack.size() == 0) return false;
                if (cur == ')' && stack.peekLast() == '(') stack.pollLast();
                else if (cur == '}' && stack.peekLast() == '{') stack.pollLast();
                else if (cur == ']' && stack.peekLast() == '[') stack.pollLast();
            }
        }
        return (stack.size() == 0);
    }


    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l2 == null) return l1;
        if (l1 == null) return l2;
        ListNode result = null;
        if (l1.val < l2.val) {
            result = l1;
            result.next = mergeTwoLists(l1.next, l2);
        } else {
            result = l2;
            result.next = mergeTwoLists(l1, l2.next);
        }
        return result;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


}

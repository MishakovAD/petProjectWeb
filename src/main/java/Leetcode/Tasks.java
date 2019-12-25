package Leetcode;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class Tasks {
    public static void main(String[] args) {
        Tasks t = new Tasks();
        //--------------TEST------------------
        t.isValid("(({})[])");
        t.reverse(1534236469);
        //t.maxSubArray1(new int[]{8,-19,5,-4,20});
        //t.maxSubArray1(new int[]{-2,1,-3,4,-1,2,1,-5,4});
        //t.maxSubArray1(new int[]{-3,-2,-1});
        t.maxSubArray1(new int[]{1,2,-1,-2,2,1,-2,1,4,-5,4});
        //------------------------------------

        System.out.println();
    }

    /*
    https://leetcode.com/problems/maximum-subarray/
     */
    public int maxSubArray(int[] nums) {
        int maxSum = 0;
        if (nums.length == 1) {
            return nums[0];
        }
        int[] left = new int[nums.length/2];
        int[] right = new int[nums.length-nums.length/2];
        System.arraycopy(nums, 0, left, 0, nums.length/2);
        System.arraycopy(nums, nums.length/2, right, 0 , nums.length - nums.length/2);
        int leftSum = maxSum(left);
        int rightSum = maxSum(right);
        int[] result = new int[nums.length - 1];
        if (leftSum > rightSum) {
            System.arraycopy(nums, 0, result, 0, nums.length - 1);
            maxSum = maxSubArray(result);
        } else {
            System.arraycopy(nums, 1, result, 0, nums.length - 1);
            maxSum = maxSubArray(result);
        }
        return maxSum;
    }
    private int maxSum(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] left = new int[nums.length/2];
        int[] right = new int[nums.length-nums.length/2];
        System.arraycopy(nums, 0, left, 0, nums.length/2);
        System.arraycopy(nums, nums.length/2, right, 0 , nums.length - nums.length/2);
        int leftSum = maxSum(left);
        int rightSum = maxSum(right);
        return leftSum + rightSum;
    }
    public int maxSubArray1(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int maxSum = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            maxSum += nums[i];
        }
        int l = 0;
        int r = len;
        for (int i = 0; i < len; i++) {
            int sumR = 0;
            int sumL = 0;
//            t.maxSubArray1(new int[]{-2,1,-3,4,-1,2,1,-5,4});
            for (int j = l + 1; j < r; j++) {
                sumR += nums[j];
            }

            for (int j = l; j < r - 1; j++) {
                sumL += nums[j];
            }
            if (!isDecrease(maxSum, sumL) && !isDecrease(maxSum, sumR)) {
                if (sumL > sumR) {
                    r--;
                    maxSum = sumL;
                } else if (sumR > sumL) {
                    l++;
                    maxSum = sumR;
                } else {
                    r--;
                    l++;
                    maxSum = sumL;
                }
            } else if (!isDecrease(maxSum, sumL) && isDecrease(maxSum, sumR)) {
                r--;
                maxSum = sumL;
            } else if (isDecrease(maxSum, sumL) && !isDecrease(maxSum, sumR)) {
                l++;
                maxSum = sumR;
            } else {
                if (sumR > sumL) {
                    l++;
                } else {
                    r--;
                }
            }
            if (r - l <= 1) {
                return maxSum;
            }

        }
        return maxSum;
    }
    private boolean isDecrease(int max, int current) {
        if (max >= 0 && current >= 0) {
            return max - current >= 0;
        } else if (max >= 0 && current < 0) {
            return true;
        } else if (max < 0 && current < 0) {
            return max > current;
        } else if (max < 0 && current >= 0) {
            return false;
        }
        return false;
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

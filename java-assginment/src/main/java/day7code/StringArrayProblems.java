package day7code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringArrayProblems {

    public static void firstNonRepeatingChar() {
        String str = "aabbcdeeff";

        Map<Character, Integer> charCount = new HashMap<>();

        for (char c : str.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        char result = '\0';
        for (char c : str.toCharArray()) {
            if (charCount.get(c) == 1) {
                result = c;
                break;
            }
        }

        if (result != '\0') {
            System.out.println("input string " + str);
            System.out.println("first non repeating character is " + result);
        } else {
            System.out.println("no non repeating character found");
        }
    }

    public static void twoSum() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        System.out.println("array 2 7 11 15");
        System.out.println("target sum " + target);

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                System.out.println("indices found " + map.get(complement) + " and " + i);
                System.out.println("values " + nums[map.get(complement)] + " + " + nums[i] + " = " + target);
                return;
            }

            map.put(nums[i], i);
        }

        System.out.println("no two sum solution found");
    }

    public static void mergeSortedLists() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(3);
        list1.add(5);
        list1.add(7);

        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(4);
        list2.add(6);
        list2.add(8);

        System.out.println("list 1 " + list1);
        System.out.println("list 2 " + list2);

        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) <= list2.get(j)) {
                merged.add(list1.get(i));
                i++;
            } else {
                merged.add(list2.get(j));
                j++;
            }
        }

        while (i < list1.size()) {
            merged.add(list1.get(i));
            i++;
        }

        while (j < list2.size()) {
            merged.add(list2.get(j));
            j++;
        }

        System.out.println("merged list " + merged);
    }
}

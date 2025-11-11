package day7new;

import java.util.*;

public class StringArrayProblems {
    public static void nonRepeatingCharacter(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a string");
        String str = sc.next();

        Map<Character, Integer> charCount = new HashMap<>();
        for(char c: str.toCharArray()){
            charCount.put(c, charCount.getOrDefault(c,0)+1);
        }
        char ans = '\0';
        for(char c: str.toCharArray()){
            if(charCount.get(c) == 1){
                ans = c;
                break;
            }
        }


        System.out.println(ans);

    }

    public static void targetSum(){
        int[] nums = {2,7,11,15};
        int target = 9;

        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;++i){
            int complement = target - nums[i];
            if(map.containsKey(complement)){
                System.out.println(map.get(complement) + i);
                return;
            }
            map.put(nums[i],i);
        }

    }

    public static void mergeLists(){
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        for(int i=1;i<10;i+=2) list1.add(i);
        for(int i=2;i<10;i+=2) list2.add(i);
        System.out.println(list1);
        System.out.println(list2);

        List<Integer> newList = new ArrayList<>();

        int i=0,j=0;
        while(i<list1.size() && j<list2.size()){
            if(list1.get(i) < list2.get(j)){
                newList.add(list1.get(i));
                i++;
            } else {
                newList.add(list2.get(j));
                j++;
            }
        }

        while(i<list1.size()) newList.add(list1.get(i++));
        while(j<list2.size()) newList.add(list2.get(j++));
        System.out.println(newList);
    }
}

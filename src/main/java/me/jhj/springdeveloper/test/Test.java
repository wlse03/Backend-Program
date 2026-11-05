package me.jhj.springdeveloper.test;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        for (int i : nums) {
//            System.out.println(i);
//
//        }
        List<Integer> resultList = nums.stream().filter(n->n%2==0).map(n->n*2).toList();
        System.out.println(resultList);
    }
}

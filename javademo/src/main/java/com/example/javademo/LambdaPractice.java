package com.example.javademo;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Dong Deng
 * @version 1.0
 * @time 2019/11/13
 */
public class LambdaPractice {

    public static void main(String[] args) {
//        (int a,int b) -> {return a + b;};
        //lambda表达式结构
//        零个或者多个参数

        //函数式接口 只能有一个抽象方法，并且有新的注解：@FunctionalInterface
//        Runnable r = () -> {
//            System.out.println("hello world");
//        };
//
//        new Thread(r).start();
//        collectionTest();
        predicateTest();
    }

    public static void functionInterfaceTest(){
        // 新方法
        new Thread(() -> System.out.println("lambda run--")).start();

        Consumer<Integer> c = (Integer x) -> {
            System.out.println("x is" + x);
        };
        c.accept(100);
        c.accept(99);


        BiConsumer<Integer, String> b = (Integer x1, String y1) -> {
            System.out.println(x1 + ":" + y1);
        };
        b.accept(100, "hello");


        Predicate<String> p = (String s) -> {
            System.out.println("s is:" + s);
            return s == null;
        };
        p.test("sfsd");
    }


    public static void collectionTest(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("old fashion:" + integer);
            }
        };
        list.forEach(consumer);
        list.forEach(n -> System.out.println("lambda : "  + n));
        //double colon operator in java 8
        list.forEach(System.out::println);
    }

    public static void predicateTest(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);

        System.out.println("Print all numbers");
        evaluate(list,n -> true);

        System.out.println("Print nothing:");
        evaluate(list,n -> false);

        System.out.println("Print even numbers:");
        evaluate(list,n -> n%2 == 0);

        System.out.println("Print odd numbers");
        evaluate(list,n -> n%2 == 1);

        System.out.println("Print numbers greater than 5:");
        evaluate(list,n -> n > 5);
    }


    public static void evaluate(List<Integer>list,Predicate<Integer> predicate){
        for (Integer n:list){
            if (predicate.test(n)){
                System.out.println(n + " ");
            }
        }
    }
}

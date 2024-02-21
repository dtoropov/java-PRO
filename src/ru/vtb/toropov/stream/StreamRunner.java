package ru.vtb.toropov.stream;

import ru.vtb.toropov.utils.Employee;
import java.sql.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamRunner.
 *
 * @author Denis Toropov
 */
public class StreamRunner {

  public static void runtask2() {
    System.out.println(
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5, 5, 6, 7, 8).distinct().toList());
    System.out.println(
        Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13).sorted((a, b) -> b - a).skip(2).findFirst()
            .orElse(0));
    System.out.println(
        Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13).distinct().sorted((a, b) -> b - a).skip(2)
            .findFirst().orElse(0));
    System.out.println(
        Stream.of(new Employee("John", 35, "manager"), new Employee("Bob", 32, "engineer"),
                new Employee("Tom", 22, "engineer"), new Employee("Ivan", 24, "engineer"),
                new Employee("Simon", 45, "manager")
                , new Employee("Taylor", 45, "engineer"))
            .filter(n -> n.getPosition().equals("engineer")).sorted(Comparator.reverseOrder())
            .limit(3).toList());
    System.out.println(
        Stream.of(new Employee("John", 35, "manager"), new Employee("Bob", 32, "engineer"),
                new Employee("Tom", 22, "engineer"), new Employee("Ivan", 24, "engineer"),
                new Employee("Simon", 45, "manager")
                , new Employee("Taylor", 45, "engineer"))
            .filter(n -> n.getPosition().equals("engineer")).mapToInt(Employee::getAge).average()
            .orElse(0));
    System.out.println(
        Stream.of("AA", "ABB", "BA", "AAAA").sorted((a, b) -> b.length() - a.length()).toList()
            .stream().findFirst().orElse(""));
    System.out.println(
        Stream.of("aa abb ba aaaa aa ba abb aa".split(" "))
            .collect(Collectors.groupingBy(String::valueOf, Collectors.counting())));
    System.out.println(
        Stream.of("aa abb ba aaaa aa ba aab aa".split(" ")).sorted((s1, s2) -> {
          if (s1.length() == s2.length()) {
            return s1.compareTo(s2);
          } else {
            return s1.length() - s2.length();
          }
        }).toList());
    System.out.println(
        Stream.of(
                new String[]{"aa abb ba aaaa aa", "aa abb ba aaaab aa", "aa ccccdd ba aaaab cccc"})
            .flatMap(s -> Arrays.stream(
                s.split(" "))).min((a, b) -> b.length() - a.length()).orElse(""));
  }
}

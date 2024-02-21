package ru.vtb.toropov.utils;

import java.util.Comparator;

/**
 * Employee.
 *
 * @author Denis Toropov
 */
public class Employee implements Comparable<Employee> {
  private String name;
  private Integer age;
  private String position;

  public Employee(String name, Integer age, String position) {
    this.name = name;
    this.age = age;
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  @Override
  public String toString(){
    return "name=" + name + " age = "+age + " position=" +position;
  }

  @Override
  public int compareTo(Employee o) {
    return this.getAge()-o.getAge();
  }
}

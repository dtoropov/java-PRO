package ru.vtb.toropov.runner;

import ru.vtb.toropov.annotation.AfterSuite;
import ru.vtb.toropov.annotation.BeforeSuite;
import ru.vtb.toropov.annotation.CsvSource;
import ru.vtb.toropov.annotation.Test;
import ru.vtb.toropov.application.Exeed;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * TestRunner.
 *
 * @author Denis Toropov
 */
public class TestRunner {

  public static void runTests(Class c)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    Constructor<?> constructor = c.getConstructor();
    Object object = constructor.newInstance();
    Method[] methods = c.getDeclaredMethods();
    Method afterSuite = null;
    Method beforeSuite = null;
    HashMap<Integer, Method> hashMap = new HashMap<>();
    for (Method method : methods) {
      if (method.isAnnotationPresent(Test.class)) {
        if (!Modifier.isStatic(method.getModifiers())) {
          Test test = method.getAnnotation(Test.class);
          hashMap.put(test.priority(), method);
        } else {
          throw new RuntimeException("Аннотация Test не может быть применена к static методам");
        }
      }
      if (method.isAnnotationPresent(AfterSuite.class)) {
        if (Modifier.isStatic(method.getModifiers())) {
          if (afterSuite == null) {
            afterSuite = method;
          } else {
            throw new RuntimeException("Аннотация AfterSuite должна быть на одном методе");
          }
        } else {
          throw new RuntimeException(
              "Аннотация AfterSuite не может быть применена к обычным методам");
        }
      }
      if (method.isAnnotationPresent(BeforeSuite.class)) {
        if (Modifier.isStatic(method.getModifiers())) {
          if (beforeSuite == null) {
            beforeSuite = method;
          } else {
            throw new RuntimeException("Аннотация BeforeSuite должна быть на одном методе");
          }

        } else {
          throw new RuntimeException(
              "Аннотация BeforeSuite не может быть применена к обычным методам");
        }
      }
    }

    ArrayList<Integer> sortedKeys
        = new ArrayList<Integer>(hashMap.keySet());

    Collections.sort(sortedKeys);

    for (Integer key : sortedKeys) {
      if (beforeSuite != null) {
        beforeSuite.invoke(null);
      }
      Method method = hashMap.get(key);
      method.invoke(object);
      if (afterSuite != null) {
        afterSuite.invoke(null);
      }
    }
  }
  public static void runCsvSource(Exeed exeed)
      throws InvocationTargetException, IllegalAccessException {
    Class classExeed = exeed.getClass();
    Method[] methods = classExeed.getDeclaredMethods();
    for (Method method:methods)
    {
      if (method.isAnnotationPresent(CsvSource.class)) {
        CsvSource csvSource = method.getAnnotation(CsvSource.class);
        String strCsvSource = csvSource.value();
        String[] arrayString = strCsvSource.split(",");
        Integer a = Integer.parseInt(arrayString[0].trim());
        String b = arrayString[1].trim();
        Integer c = Integer.parseInt(arrayString[2].trim());
        Boolean d = Boolean.parseBoolean(arrayString[3].trim());
        method.invoke(exeed, a, b, c,d);
      }
    }
  }
}

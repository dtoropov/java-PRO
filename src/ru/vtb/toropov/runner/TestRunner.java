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
import java.util.List;

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
    Integer priority;
    HashMap<Integer, List<Method>> hashMap = new HashMap<>();
    for (Method method : methods) {
      if (method.isAnnotationPresent(Test.class)) {
        if (!Modifier.isStatic(method.getModifiers())) {
          Test test = method.getAnnotation(Test.class);
          priority = test.priority();
          List<Method> listMethod = hashMap.getOrDefault(priority, new ArrayList<>());
          listMethod.add(method);
          hashMap.put(priority, listMethod);
        } else {
          throw new RuntimeException("Аннотация Test не может быть применена к static методам");
        }
      }
      if (method.isAnnotationPresent(AfterSuite.class)) {
        if (!Modifier.isStatic(method.getModifiers())) {
          throw new RuntimeException("Аннотация AfterSuite должна быть на static методе");
        }
        if (afterSuite != null) {
          throw new RuntimeException("Аннотация AfterSuite должна быть на одном методе");
        }
        afterSuite = method;
      }
      if (method.isAnnotationPresent(BeforeSuite.class)) {
        if (!Modifier.isStatic(method.getModifiers())) {
          throw new RuntimeException("Аннотация BeforeSuite  должна быть на static методе");
        }
        if (beforeSuite != null) {
          throw new RuntimeException(
              "Аннотация BeforeSuite должна быть на одном методе");
        }
        beforeSuite = method;
      }
    }

    List<Integer> sortedKeys
        = new ArrayList<Integer>(hashMap.keySet());

    Collections.sort(sortedKeys);

    if (beforeSuite != null) {
      beforeSuite.invoke(null);
    }
    for (Integer key : sortedKeys) {
      List<Method> listMethod = hashMap.get(key);
      for (Method method : listMethod) {
        method.invoke(object);
      }
    }
    if (afterSuite != null) {
      afterSuite.invoke(null);
    }
  }

  public static void runCsvSource(Exeed exeed)
      throws InvocationTargetException, IllegalAccessException {
    Class classExeed = exeed.getClass();
    Method[] methods = classExeed.getDeclaredMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(CsvSource.class)) {
        CsvSource csvSource = method.getAnnotation(CsvSource.class);
        String strCsvSource = csvSource.value();
        String[] arrayString = strCsvSource.split(",");
        if (method.getParameterCount() == arrayString.length) {
          Object[] args = new Object[arrayString.length];
          Class[] parameterTypes = method.getParameterTypes();
          for (int i = 0; i < parameterTypes.length; i++) {
            Object obj;
            Class<?> parameterType = parameterTypes[i];
            obj = switch (parameterType.getSimpleName()) {
              case ("int"), ("Integer") -> Integer.parseInt(arrayString[i].trim());
              case ("boolean"), ("Boolean") -> Boolean.parseBoolean(arrayString[i].trim());
              default -> parameterType.cast(arrayString[i].trim());
            };
            args[i] = obj;
          }
          method.invoke(exeed, args);
        } else {
          throw new RuntimeException(
              "Количество параметров не совпадает");
        }
      }
    }
  }
}

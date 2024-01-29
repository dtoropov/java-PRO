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
    Integer priotiy;
    HashMap<Integer, List<Method>> hashMap = new HashMap<>();
    for (Method method : methods) {
      if (method.isAnnotationPresent(Test.class)) {
        if (!Modifier.isStatic(method.getModifiers())) {
          Test test = method.getAnnotation(Test.class);
          priotiy = test.priority();
          List<Method> listMethod = hashMap.get(priotiy);
          if (listMethod == null) {
            listMethod = new ArrayList<Method>();
          }
          listMethod.add(method);
          hashMap.put(priotiy, listMethod);
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
        int parameterCount = method.getParameterCount();
        String strCsvSource = csvSource.value();
        String[] arrayString = strCsvSource.split(",");
        if (parameterCount == arrayString.length) {
          Object[] args = new Object[arrayString.length];
          Class[] parameterTypes = method.getParameterTypes();
          for (int i = 0; i < parameterTypes.length; i++) {
            Object obj;
            Class<?> parameterType = parameterTypes[i];
            obj = switch (parameterType.getName()) {
              case ("int"), ("Integer") -> Integer.parseInt(arrayString[i].trim());
              case ("boolean"), ("Boolean") -> Boolean.parseBoolean(arrayString[i].trim());
              default -> parameterType.cast(arrayString[i].trim());
            };
            args[i] = obj;
          }
          method.invoke(exeed, args);
        }
      }
    }
  }
}

package ru.vtb.toropov.application;

import ru.vtb.toropov.annotation.AfterSuite;
import ru.vtb.toropov.annotation.BeforeSuite;
import ru.vtb.toropov.annotation.CsvSource;
import ru.vtb.toropov.annotation.Test;
import java.math.BigDecimal;

/**
 * Exeed.
 *
 * @author Denis Toropov
 */
public class Exeed extends Car{

  public Exeed() {
  }

  public Exeed(String model, int yearOfRelease, BigDecimal price) {
    super(model, yearOfRelease, price);
  }
  @Override
  @Test(priority = 1)
  public void start()
  {
    System.out.println("Машина " + this.getModel() + " поехала");
  }

  @Override
  @Test(priority = 2)
  public void stop()
  {
    System.out.println("Машина " + this.getModel() + " остановилась");
  }

  @Override
  @Test(priority = 1)
    public void turnOnMusic()
  {
    System.out.println("Мы включили радио в машине " + this.getModel());
  }

  @Override
  @Test(priority = 2)
    public void turnOffMusic()
  {
    System.out.println("Мы выключили радио в машине " + this.getModel());
  }

  @BeforeSuite
  public static void before()
  {
    System.out.println("Method before");
  }

  @AfterSuite
    public static void after()
  {
    System.out.println("Method after");
  }

  @CsvSource("10, Java, 20, true")
  public void testMethod(Integer a, String b, int c, Boolean d)
  {
    System.out.println("Значение a="+a+", значение b=" + b + ", значение c=" + c + " значение d=" +d);
  }
}

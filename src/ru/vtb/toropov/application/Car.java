package ru.vtb.toropov.application;

import java.math.BigDecimal;

/**
 * Car.
 *
 * @author Denis Toropov
 */
public abstract class Car {
  private String model;
  private int yearOfRelease ;
  private BigDecimal price;
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getYearOfRelease() {
    return yearOfRelease;
  }

  public void setYearOfRelease(int yearOfRelease) {
    this.yearOfRelease = yearOfRelease;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Car()
  {

  }

  public Car(String model, int yearOfRelease, BigDecimal price) {
    this.model = model;
    this.yearOfRelease = yearOfRelease;
    this.price = price;
  }

  public abstract void start();

  public abstract void stop();

  public abstract void turnOnMusic();

  public abstract void turnOffMusic();

}

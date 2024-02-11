package ru.vtb.toropov.service;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TaskExecutor.
 *
 * @author Denis Toropov
 */
public class TaskExecutor extends Thread {

  private LinkedBlockingQueue<Runnable> taskList;

  private String name;

  boolean isRun;

  public void setRun(boolean run) {
    isRun = run;
  }

  public TaskExecutor(LinkedBlockingQueue<Runnable> taskList, String name) {
    this.taskList = taskList;
    this.name = name;
    this.isRun = true;
  }

  @Override
  public void run() {
    try {
      while (isRun | !taskList.isEmpty()) {
        System.out.println("Запустился " + name);
        taskList.take().run();
      }
      if (!isRun) {
        System.out.println("Завершился " + name);
      }
    } catch (InterruptedException interruptedException) {
      throw new RuntimeException(interruptedException);
    }
  }
}

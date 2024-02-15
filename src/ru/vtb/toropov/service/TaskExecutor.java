package ru.vtb.toropov.service;

import java.util.List;

/**
 * TaskExecutor.
 *
 * @author Denis Toropov
 */
public class TaskExecutor extends Thread {

  private List<Runnable> taskList;

  private String name;

  boolean isRun;

  public void setRun(boolean run) {
    isRun = run;
  }

  public TaskExecutor(List<Runnable> taskList, String name) {
    this.taskList = taskList;
    this.name = name;
    this.isRun = true;
  }

  @Override
  public void run() {
    System.out.println("Запустился " + name);
    while (isRun) {
      while (taskList.iterator().hasNext()) {
        Runnable runnable = taskList.iterator().next();
        synchronized (ExecutorService.lock) {
          taskList.remove(runnable);
        }
        System.out.println(name + " запустил задачу");
        runnable.run();
      }
    }
    if (!isRun) {
      System.out.println("Завершился " + name);
    }
  }
}

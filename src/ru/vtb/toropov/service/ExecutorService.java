package ru.vtb.toropov.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * ExecutorService.
 *
 * @author Denis Toropov
 */
public class ExecutorService {

  private int threadPoolCount;
  private List<Runnable> queueTread;
  private List<TaskExecutor> listTaskExecutor;
  private boolean isShutdown;
  private final Object lock = new Object();

  public ExecutorService(int threadPoolCount) {
    this.threadPoolCount = threadPoolCount;
    this.listTaskExecutor = new LinkedList<>();
    this.queueTread = new ArrayList<>();
    this.isShutdown = false;
    for (int i = 0; i < threadPoolCount; i++) {
      TaskExecutor taskExecutor = new TaskExecutor("Поток №" + i);
      listTaskExecutor.add(taskExecutor);
      taskExecutor.start();
    }
  }

  public void execute(Runnable runnable) {
    if (isShutdown) {
      throw new IllegalStateException(
          "Пул потоков приостановил работу. Добавление элементов не возможно");
    }
    synchronized (lock) {
      queueTread.add(runnable);
    }
  }

  public void shutdown() {
    this.isShutdown = true;
  }

  private class TaskExecutor extends Thread {

    private String name;

    public TaskExecutor(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      System.out.println("Запустился " + name);
      while (!isShutdown) {
        Runnable runnable = null;
        synchronized (lock) {
          while (queueTread.iterator().hasNext()) {
            runnable = queueTread.iterator().next();
            queueTread.remove(runnable);
          }
        }
        if (runnable != null) {
          System.out.println(name + " запустил задачу");
          runnable.run();
        }

      }
      if (isShutdown) {
        System.out.println("Завершился " + name);
      }
    }
  }
}


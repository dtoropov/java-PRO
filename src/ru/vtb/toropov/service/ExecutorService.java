package ru.vtb.toropov.service;

import java.util.ArrayList;
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
  public static Object lock = new Object();
  private final Object lock1 = new Object();


  public ExecutorService(int threadPoolCount) {
    this.threadPoolCount = threadPoolCount;
    this.listTaskExecutor = new ArrayList<>(this.threadPoolCount);
    this.queueTread = new ArrayList<>();
    this.isShutdown = false;
    for (int i = 0; i < threadPoolCount; i++) {
      TaskExecutor taskExecutor = new TaskExecutor(queueTread, "Поток №" + i);
      listTaskExecutor.add(taskExecutor);
      taskExecutor.start();
    }
  }

  public void execute(Runnable runnable) {
    if (isShutdown) {
      throw new IllegalStateException(
          "Пул потоков приостановил работу. Добавление элементов не возможно");
    }
    synchronized (lock1) {
      queueTread.add(runnable);
    }
  }

  public void shutdown() {
    this.isShutdown = true;
    for (TaskExecutor taskExecutor : listTaskExecutor) {
      taskExecutor.setRun(false);
    }
  }
}


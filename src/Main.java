import static java.lang.Thread.sleep;

import ru.vtb.toropov.service.ExecutorService;

/**
 * ${NAME}.
 *
 * @author Denis Toropov
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    //TestRunner.runtask1();
    //StreamRunner.runtask2();
    ExecutorService executorService=new ExecutorService(3);
    executorService.execute(() -> System.out.println("Первая задача"));
    executorService.execute(() -> System.out.println("Вторая задача"));
    executorService.execute(() -> System.out.println("Третья задача"));
    sleep(3000);
    executorService.execute(() -> System.out.println("Четвертая задача"));
    executorService.execute(() -> System.out.println("Пятая задача"));
    executorService.execute(() -> System.out.println("Шестая задача"));
    executorService.shutdown();
    executorService.execute(() -> System.out.println("Седьмая задача"));
  }
}
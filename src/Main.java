import ru.vtb.toropov.service.ExecutorService;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * ${NAME}.
 *
 * @author Denis Toropov
 */
public class Main {

  public static void main(String[] args) {
    //TestRunner.runtask1();
    //StreamRunner.runtask2();
    ExecutorService executorService=new ExecutorService(3);
    executorService.execute(() -> System.out.println("Первая задача"));
    executorService.execute(() -> System.out.println("Вторая задача"));
    executorService.execute(() -> System.out.println("Третья задача"));
    executorService.shutdown();
    executorService.execute(() -> System.out.println("Четвертая задача"));
  }
}
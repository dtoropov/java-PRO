import ru.vtb.toropov.application.Exeed;
import ru.vtb.toropov.runner.TestRunner;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

/**
 * ${NAME}.
 *
 * @author Denis Toropov
 */
public class Main {

  public static void main(String[] args)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    Exeed exeed =new Exeed("TXL" , 2022, new BigDecimal(3000000));
    Class classExeed = exeed.getClass();
    TestRunner.runTests(classExeed);
    TestRunner.runCsvSource(exeed);
  }
}
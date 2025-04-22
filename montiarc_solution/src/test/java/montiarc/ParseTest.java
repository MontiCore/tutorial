package montiarc;

import de.monticore.gettingstarted.montiarc.MontiArcMill;
import de.monticore.gettingstarted.montiarc._ast.ASTArcUnit;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ParseTest {
  @BeforeClass
  public static void initTest() {
    MontiArcMill.init();
  }

  @Before
  public void beforeEach() {
    Log.clearFindings();
  }

  @Test
  public void testBiggerExample() throws Exception {
    doParse("src/test/resources/de/monticore/gettingstarted/montiarc/BiggerExample.arc");
  }

  @Test
  public void testLogger() throws Exception {
    doParse("src/test/resources/de/monticore/gettingstarted/montiarc/Logger.arc");
  }

  @Test
  public void testMotor() throws Exception {
    doParse("src/test/resources/de/monticore/gettingstarted/montiarc/Motor.arc");
  }

  @Test
  public void testTimer() throws Exception {
    doParse("src/test/resources/de/monticore/gettingstarted/montiarc/Timer.arc");
  }

  @Test
  public void testUltrasonic() throws Exception {
    doParse("src/test/resources/de/monticore/gettingstarted/montiarc/Ultrasonic.arc");
  }


  protected ASTArcUnit doParse(String path) throws IOException {
    File f = new File(path);
    Optional<ASTArcUnit> opt = MontiArcMill.parser().parse(path);
    Log.getFindings().forEach(System.err::println);
//    MontiArcMi
    return opt.get();
  }
}

/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.automatawithstereo;

import com.google.common.collect.Lists;
import de.monticore.gettingstarted.automatawithstereo._ast.ASTAutomaton;
import de.se_rwth.commons.logging.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StereoParseTest extends AbstractStereoTest {

  @Before
  public void setUp() {
    Log.clearFindings();
  }

  @BeforeClass
  public static void setUpTest() {
    Log.initDEBUG();
    AutomataWithStereoMill.init();
  }

  @Test
  public void testClassic() throws IOException {
    ASTAutomaton ast = parse("src/test/resources/de/monticore/gettingstarted/automatawithstereo/Classic.aut");
    Assert.assertFalse( ast.isPresentStereotype());
  }

  @Test
  public void testDoor() throws IOException {
    ASTAutomaton ast = parse("src/test/resources/de/monticore/gettingstarted/automatawithstereo/Door.aut");
    Assert.assertTrue( ast.isPresentStereotype());

    Assert.assertEquals("enum", ast.getStereotype().getValue("pattern"));
  }

  @Test
  public void testHierarchical() throws IOException {
    ASTAutomaton ast = parse("src/test/resources/de/monticore/gettingstarted/automatawithstereo/Hierarchical.aut");
    Assert.assertTrue( ast.isPresentStereotype());
  }

}

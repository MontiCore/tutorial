/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava._symboltable;

import de.monticore.symboltable.serialization.json.JsonObject;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.SymTypeExpressionDeSer;

import java.util.List;

public class JavaArtifactSymbolDeSer extends JavaArtifactSymbolDeSerTOP {
  @Override
  protected void serializeSuperTypes(List<SymTypeExpression> superTypes, SimpleJavaSymbols2Json s2j) {
    SymTypeExpressionDeSer.serializeMember(s2j.getJsonPrinter(), "superTypes", superTypes);
    //TODO implement me!
  }

  @Override
  protected List<SymTypeExpression> deserializeSuperTypes(JsonObject symbolJson) {
    return SymTypeExpressionDeSer.deserializeListMember("superTypes", symbolJson);
    //TODO implement me!
  }
}

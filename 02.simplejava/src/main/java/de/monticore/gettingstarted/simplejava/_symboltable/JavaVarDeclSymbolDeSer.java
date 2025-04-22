/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.simplejava._symboltable;

import de.monticore.symboltable.serialization.json.JsonObject;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.SymTypeExpressionDeSer;

public class JavaVarDeclSymbolDeSer extends JavaVarDeclSymbolDeSerTOP {


  @Override
  protected void serializeType(SymTypeExpression type, SimpleJavaSymbols2Json s2j) {
    SymTypeExpressionDeSer.serializeMember(s2j.getJsonPrinter(), "type", type);
    //TODO implement me!
  }

  @Override
  protected SymTypeExpression deserializeType(JsonObject symbolJson) {
    return SymTypeExpressionDeSer.deserializeMember("type", symbolJson);
    //TODO implement me!
  }
}

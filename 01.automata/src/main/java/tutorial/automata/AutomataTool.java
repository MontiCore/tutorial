/* (c) https://github.com/MontiCore/monticore */

package tutorial.automata;

import tutorial.automata._ast.ASTAutomaton;
import tutorial.automata._cocos.AutomataCoCoChecker;
import tutorial.automata._symboltable.IAutomataArtifactScope;
import tutorial.automata._visitor.AutomataTraverser;
import tutorial.automata.cocos.AutomataCoCos;
import tutorial.automata.visitor.CountStates;
import tutorial.automata.visitor.CountTransitions;
import de.se_rwth.commons.Joiners;
import de.se_rwth.commons.logging.Log;

public class AutomataTool extends AutomataToolTOP {

  @Override
  public void run(String[] args) {
    init();
    org.apache.commons.cli.Options options = initOptions();
    try{
      //create CLI Parser and parse input options from commandline
      org.apache.commons.cli.CommandLineParser cliparser = new org.apache.commons.cli.DefaultParser();
      org.apache.commons.cli.CommandLine cmd = cliparser.parse(options,args);

      //help: when --help
      if(cmd.hasOption("h")){
        printHelp(options);
        //do not continue, when help is printed.
        return;
      }
      //version: when --version
      else if(cmd.hasOption("v")){
        printVersion();
        //do not continue when help is printed
        return;
      }

      if(!cmd.hasOption("i")){
        Log.error("0xA010 The arguments for the tool should include the option -i");
      }

      String file = cmd.getOptionValue("i");
      ASTAutomaton aut = parse(file);
      IAutomataArtifactScope as = createSymbolTable(aut);
      runDefaultCoCos(aut);

      if(cmd.hasOption("pp")){
        prettyPrint(aut, cmd.getOptionValue("pp"));
      }

      if(cmd.hasOption("r")){
        report(aut, cmd.getOptionValue("r"));
      }

    }catch (org.apache.commons.cli.ParseException e) {
      // e.getMessage displays the incorrect input-parameters
      Log.error("0xA5C06x68980 Could not process AutomataTool parameters: " + e.getMessage());
    }
  }

  @Override
  public void runDefaultCoCos(ASTAutomaton ast) {
    AutomataCoCoChecker checker = new AutomataCoCos().getCoCoChecker();
    checker.checkAll(ast);
  }

  @Override
  public void report(ASTAutomaton ast, String path) {
    String reportFile = path + ast.getName() + ".txt";

    CountStates cs = new CountStates();
    CountTransitions ct = new CountTransitions();
    AutomataTraverser traverser = AutomataMill.traverser();
    traverser.add4Automata(cs);
    traverser.add4Automata(ct);
    ast.accept(traverser);

    StringBuilder report = new StringBuilder("Report for ").append(ast.getName()).append(":\n");
    report.append("Number of States: ").append(cs.countStates()).append("\n");
    report.append("State Names: ").append(Joiners.COMMA.join(cs.getStateNames())).append("\n");
    report.append("Number of Initial States: ").append(cs.countInitialStates()).append("\n");
    report.append("Initial State Names: ").append(Joiners.COMMA.join(cs.getInitialStateNames())).append("\n");
    report.append("Number of Final States: ").append(cs.countFinalStates()).append("\n");
    report.append("Final State Names: ").append(Joiners.COMMA.join(cs.getFinalStateNames())).append("\n");
    report.append("Number of Transitions: ").append(ct.countTransitions()).append("\n");
    report.append("Transition inputs: ").append(Joiners.COMMA.join(ct.getTransitionInputs())).append("\n");

    print(report.toString(), reportFile);
  }

  @Override
  public void prettyPrint(ASTAutomaton ast, String file) {
    String ppFile = file + ast.getName() + ".aut";
    String model = AutomataMill.prettyPrint(ast, true);
    print(model, ppFile);
  }
}

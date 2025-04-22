/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarcwithstatecharts;

/**
 * valid
 * complex model
 */
component Door {
  port in boolean open, unlock;
  port out boolean ringing;

  statechart Door {
    initial state Closed;
    state Locked;
    state Opened {
      entry / {System.out.println("door opens");}
      exit / {System.out.println("door closes");}
    };

    Opened -> Closed;
    Closed -> Opened [open] / {ringing = true;};
    Closed -> Locked        / {System.out.println("Door locked now.");};
    Locked -> Closed [unlock == true];

  }
}

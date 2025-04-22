/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarcwithstatecharts;

/**
 * valid
 * a bit more complex than EmptyStateChart
 */
component SomeStates {
  port in boolean open, unlock;
  port out boolean ringing;

  statechart Bar {
    state Opened;
    initial state Closed;
    state Locked;
  }
}

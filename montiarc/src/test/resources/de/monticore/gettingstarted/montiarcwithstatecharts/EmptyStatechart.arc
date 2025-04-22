/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarcwithstatecharts;

/**
 * valid
 * contains the most basic valid statechart
 */
component A_EmptyStateChart {
  port in boolean open, unlock;
  port out boolean ringing;

  statechart Foo { }
}

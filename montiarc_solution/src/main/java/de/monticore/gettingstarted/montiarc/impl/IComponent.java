/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarc.impl;

public interface IComponent {
  /**
   * Instantiate outgoing ports
   * Instantiate and call {@link #setup()} on inner components
   * Instantiate behavior
   */
  void setup();

  /**
   * Set instances of target ports
   */
  void init();

  /**
   * Call {@link #compute()} on inner components
   * Call {@link IBehavior#compute()} on (opt) behavior
   */
  void compute();

  /**
   * Call {@link #update()} on inner components
   * Tick outgoing ports
   */
  void update();

}

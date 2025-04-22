/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarc.impl;

/**
 * Ports are used by components
 */
public class Port<T> {
  protected T value;
  protected T nextValue;

  public void set(T v) {
    this.nextValue = value;
  }

  public T get() {
    return this.value;
  }

  public void tick() {
    this.value = this.nextValue;
    this.nextValue = null;
  }
}

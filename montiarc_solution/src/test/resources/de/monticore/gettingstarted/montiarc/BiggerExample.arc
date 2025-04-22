/* (c) https://github.com/MontiCore/monticore */
package de.monticore.gettingstarted.montiarc;

// This is a bigger example of how a MontiArc model may look
// Start by parsing the smaller arc models,
//  before tackling this grammar (but do be aware of the complexity introduced by some syntax constructions)
component BiggerExample {

  port in int i1;
  port out String o1, o2, o3;

  i1 -> a1.i1;

  component A {
    port in int i1;
    port out int o1, o2, o3;
    port out int o4, o5, o6;
    port out int o7, o8, o9;
  }

  A a1;

  a.o1 -> b1.i1;
  a.o2 -> b1.o2;
  a.o3 -> b1.o3;

  component B b1 {
    port in int i1, i2, i3;
    port out int o1;
  }

  B b2, b3;

  // TODO: B has no o2, o3
  a.o4 -> b2.i1;
  a.o5 -> o2;
  a.o6 -> o3;
  a.o7 -> i1;
  a.o8 -> o2;
  a.o9 -> o3;

  component C c1, c2, c3 {
    port in int i;
    port out String o;
  }

  b1.o1 -> c1.i;
  b2.o1 -> c2.i;
  b3.o1 -> c3.i;
}

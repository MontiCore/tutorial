package tutorial.simplejava.invalid;

class InheritedCannotUseStaticFromSuper {

  //should not work in oo type check
  int a = tutorial.simplejava.valid.InheritanceBar.getMax()

}

package tutorial.simplejava.invalid;

class StaticAbstractOOFields {

  void foo() {
    //should be okay with both abstract and oo type check
    int a = tutorial.simplejava.valid.Bar.max
    //oo type check should throw an error here
    int b = tutorial.simplejava.valid.Bar.age
  }

}

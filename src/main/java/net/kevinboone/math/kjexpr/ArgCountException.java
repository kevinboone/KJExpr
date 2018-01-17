/*============================================================================
  KCEXPR
  ArgCountException.java
  Copyright (c)2017 Kevin Boone, GPL v3.0
============================================================================*/
package net.kevinboone.math.kjexpr;

/** 
  This exception is thrown when a function is called with the wrong
  number of arguments.
*/
public class ArgCountException extends EvaluationException
  {
  public ArgCountException (String name, int expected, int got)
    {
    super ("Wrong number of arguments in '" + name + "'; expected " 
      + expected + ", got " + got);
    }
  }


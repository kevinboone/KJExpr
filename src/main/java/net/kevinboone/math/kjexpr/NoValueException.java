/*============================================================================
  KCEXPR
  NoValueException.java
  Copyright (c)2017 Kevin Boone, GPL v3.0
============================================================================*/
package net.kevinboone.math.kjexpr;

/**
  This exception is thrown by the expression evaluator when if it tries
  to render the top node of the evaluated ParseTree into a value, and
  can't; it almost certainly indicates an internal error.
*/
public class NoValueException extends EvaluationException
  {
  public NoValueException (String name)
    {
    super ("Parse tree node of type '" + name +  
     "' cannot be converted into a value");
    }
  }



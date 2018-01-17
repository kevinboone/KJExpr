package net.kevinboone.math.kjexpr;


public class UndefinedVariableException extends EvaluationException 
  {
  public UndefinedVariableException (String name)
    {
    super ("Undefined variable '" + name + "'");
    }
  }




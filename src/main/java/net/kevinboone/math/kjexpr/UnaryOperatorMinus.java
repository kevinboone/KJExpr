package net.kevinboone.math.kjexpr;
import java.util.*;


public class UnaryOperatorMinus extends UnaryOperator 
  {
  ParseTree apply (ParseTree arg)
      throws EvaluationException
    {
    if (arg instanceof ParseTreeNumber)
      {
      return ((ParseTreeNumber)arg).negate();
      }
    else
      {
      throw new EvaluationException 
        ("Can't negate a value of type " + arg.getName());
      }
    }
  }




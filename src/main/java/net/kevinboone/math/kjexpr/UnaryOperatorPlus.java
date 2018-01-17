package net.kevinboone.math.kjexpr;
import java.util.*;


public class UnaryOperatorPlus extends UnaryOperator 
  {
  ParseTree apply (ParseTree arg)
      throws EvaluationException
    {
    return arg;
    }
  }




package net.kevinboone.math.kjexpr;
import java.util.*;


public abstract class UnaryOperator 
  {
  abstract ParseTree apply (ParseTree arg)
    throws EvaluationException;
  }



package net.kevinboone.math.kjexpr;
import java.util.*;


public abstract class BinaryOperator 
  {
  String symbol;
  String desc;

  BinaryOperator (String symbol, String desc)
    {
    this.symbol = symbol;
    this.desc = desc;
    }

  public String getSymbol() { return symbol; }
  public String getDesc() { return desc; }

  abstract ParseTree apply (ParseTree arg1, ParseTree arg2, 
       EvaluationContext context)
    throws EvaluationException;
  }



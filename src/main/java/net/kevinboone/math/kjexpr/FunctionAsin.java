/*===============================================================================
KJEXPR
FunctionAsin.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


public class FunctionAsin extends Function
  {
  public FunctionAsin()
    {
    super ("asin", "inverse sine", 1); 
    }

  public ParseTree evaluate (SymbolTable table, ParseTreeList args, 
       EvaluationContext context)
     throws EvaluationException
    {
    checkArgCount (args.size());

    ParseTree pt = args.get (0);
    ParseTree arg = pt.evaluate (table, context);
    if (arg instanceof ParseTreeNumber)
      {
      BigDecimal x = ((ParseTreeNumber)arg).toBigDecimal();
      //if (x.compareTo (BigDecimal.ZERO) >= 0)
        {
        MathContext mc = context.getMC(); 
        try
          {
          BigDecimal theta = BigDecimalMath.asin (x, mc);
          if (context.getAngleMode() == EvaluationContext.AngleMode.DEG)
             theta = BigDecimalUtil.radToDeg (theta, mc);
          return new ParseTreeNumber (theta);
          }
        catch (ArithmeticException e) 
          {
          throw new EvaluationException (e.getMessage());
          }
        }
      //else
      //  throw new EvaluationException ("sqrt(x) is only defined for x >= 0");
      }
    else
      throw new EvaluationException ("Can't apply function + '" 
        + getName() + "' to argument of type " + arg.getName());
    }
  }




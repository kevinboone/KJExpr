/*===============================================================================
KJEXPR
FunctionGCD.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


public class FunctionGCD extends Function
  {
  public FunctionGCD()
    {
    super ("gcd", "greatest common divisor", 2); 
    }

  public ParseTree evaluate (SymbolTable table, ParseTreeList args, 
       EvaluationContext context)
     throws EvaluationException
    {
    checkArgCount (args.size());

    ParseTree ptu = args.get(0).evaluate (table, context);
    ParseTree ptv = args.get(1).evaluate (table, context);
    if (ptu instanceof ParseTreeNumber && ptv instanceof ParseTreeNumber)
      {
      MathContext mc = context.getMC(); 
      BigDecimal u = ((ParseTreeNumber)ptu).toBigDecimal();
      BigDecimal v = ((ParseTreeNumber)ptv).toBigDecimal(); 
      BigDecimal res = BigDecimalUtil.gcd (u,v);
      return new ParseTreeNumber (res);
      }
    else
      throw new EvaluationException ("Function + '" 
        + getName() + "' can only be applied to real numbers");
    }
  }




/*===============================================================================
KJEXPR
FunctionFactorial.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


public class FunctionFactorial extends Function
  {
  public FunctionFactorial()
    {
    super ("fact", "factorial", 1); 
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
      MathContext mc = context.getMC(); 
      BigDecimal total = BigDecimal.ONE;
      BigInteger x = ((ParseTreeNumber)arg).toBigDecimal().toBigInteger();
      while (x.compareTo (BigInteger.ONE) > 0)
        {
        total = total.multiply (new BigDecimal (x));
        x = x.subtract (BigInteger.ONE);
        }
      return new ParseTreeNumber (total);
      }
    else
      throw new EvaluationException ("Can't apply function + '" 
        + getName() + "' to argument of type " + arg.getName());
    }
  }




/*===============================================================================
KJEXPR
FunctionSin.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


public class FunctionSin extends Function
  {
  public FunctionSin()
    {
    super ("sin", "sine of an angle", 1); 
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
      BigDecimal x = ((ParseTreeNumber)arg).toBigDecimal();
      if (context.getAngleMode() == EvaluationContext.AngleMode.DEG)
        x = BigDecimalUtil.degToRad (x, mc);
      int prec = mc.getPrecision();
      BigDecimal res = BigDecimalMath.sin (x, mc);
      int exp = BigDecimalMath.exponent(res);
      if (exp <= -prec)
        return new ParseTreeNumber (BigDecimal.ZERO);
      else
        return new ParseTreeNumber (res);
      }
    else
      throw new EvaluationException ("Can't apply function + '" 
        + getName() + "' to argument of type " + arg.getName());
    }
  }





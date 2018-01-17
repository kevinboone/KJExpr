/*===============================================================================
KJEXPR
FunctionSolveBisection.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


public class FunctionSolveBisection extends FunctionSolve
  {
  static final BigDecimal TWO = new BigDecimal ("2");

  public FunctionSolveBisection()
    {
    super ("solve_bisection", "find roots using bisection method"); 
    }


  BigDecimal run (ParseTree ptfn, String name, EvaluationContext context, 
         SymbolTable table,
         int maxIters, BigDecimal eps, BigDecimal low, BigDecimal high)
       throws EvaluationException
    {
    BigDecimal x0 = low;
    BigDecimal x1 = high;

    boolean found = false;
    int i = 0;

    BigDecimal x = x0;

    while (!found && (i < maxIters))
      {
      BigDecimal y0 = evaluateWith (ptfn, name, x0,
        table, context); 

      x = x0.add(x1).divide(TWO, context.getMC());

      BigDecimal y = evaluateWith (ptfn, name, x,
        table, context); 

     //System.out.println ("x0= " + x0 + ", x1= " + x1 + ", x=" 
     //   + x + ", y0=" + y0 +  ", y="+y);

      BigDecimal p = y0.multiply (y);
      if (p.compareTo (BigDecimal.ZERO) < 0)
        x1 = x;
      else
        x0 = x;

      if (hasConverged (x0, x1, y, null, eps)) 
         found = true;

      i++;
      } 
    if (found)
      return x;
    else
      throw new EvaluationException
         ("Solver did not converge");
    } 
  }






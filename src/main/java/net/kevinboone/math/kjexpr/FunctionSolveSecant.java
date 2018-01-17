/*===============================================================================
KJEXPR
FunctionSolve.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


public class FunctionSolveSecant extends FunctionSolve
  {
  public FunctionSolveSecant()
    {
    super ("solve_secant", "find roots using secant method"); 
    }


  BigDecimal run (ParseTree ptfn, String name, EvaluationContext context, 
         SymbolTable table,
         int maxIters, BigDecimal eps, BigDecimal low, BigDecimal high)
       throws EvaluationException
    {
    BigDecimal x0 = low;
    BigDecimal x1 = high;
    BigDecimal res = x0;

    boolean found = false;
    int i = 0;
    while (!found && (i < maxIters))
      {
      BigDecimal y0 = evaluateWith (ptfn, name, x0,
        table, context); 

      BigDecimal y1 = evaluateWith (ptfn, name, x1,
        table, context); 
     
      // System.out.println ("x0= " + x0 + ", x1= " + x1 + ", y0=" 
      // + y0 + ", y1=" + y1);


      BigDecimal ydiff = y1.subtract (y0);
      BigDecimal xdiff = x1.subtract (x0);

      if (ydiff.compareTo (BigDecimal.ZERO) == 0)
        throw new EvaluationException
           ("Horizontal secant -- try different precision or eps");


      BigDecimal slope = ydiff.divide (xdiff, context.getMC()); 

      BigDecimal x2 = x1.subtract (y1.divide (slope, context.getMC()));

      ///System.out.println ("x2=" + x2);
      
      x0 = x1;
      x1 = x2;

      if (hasConverged (x0, x1, y0, y1, eps)) 
         {
         res = x0;
         found = true;
         }

      i++;
      } 
    if (found)
      return res;
    else
      throw new EvaluationException
         ("Solver did not converge");
    } 
  }





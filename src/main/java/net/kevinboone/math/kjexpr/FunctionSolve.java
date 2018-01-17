/*===============================================================================
KJEXPR
FunctionSolve.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


/**
  Base class for methods that find root within a pair of bounds.
*/
public abstract class FunctionSolve extends Function
  {
  public FunctionSolve (String name, String desc)
    {
    super (name, desc, 4); 
    }


   BigDecimal evaluateWith (ParseTree ptfn, String name, BigDecimal arg,
        SymbolTable table, EvaluationContext context)
      throws EvaluationException
    {
    Value v = new NumberValue ("", "", arg); 

    Hashtable<String, Value> variables = table.getVariableTable(); 
    Hashtable<String, Value> newVariables = 
       new Hashtable<String, Value>(variables); 
    
    v.setName (name);
    newVariables.put (name, v);

    table.setVariableTable(newVariables); 

    ParseTree ptResult = ptfn.evaluate (table, context); 

    table.setVariableTable (variables); 

    if (ptResult instanceof ParseTreeNumber)
      return ((ParseTreeNumber)ptResult).toBigDecimal();
    else
      throw new EvaluationException 
       ("Expression does not evaluate to a real number in " + getName() + "()");
    } 


  boolean hasConverged (BigDecimal x0, BigDecimal x1, BigDecimal y0, 
       BigDecimal y1, BigDecimal eps) 
     {
     if (y0.abs().compareTo (eps) < 0)
       return true;
     return false;
     }


/**
  Helper function to get the value of the iters variable from
  the symbol table 
*/
  int getIters (SymbolTable symbolTable)
    {
    int deflt = 10;
    Value _itersValue = symbolTable.getVariable ("iters");
    if (_itersValue != null)
      { 
      if (_itersValue instanceof NumberValue)
        {
        NumberValue itersValue = (NumberValue)_itersValue;
        return itersValue.toBigDecimal().intValue();
        }
      else
        return deflt;
      }
    else
      return deflt;
    }


/**
  Helper function to get the value of the eps variable from
  the symbol table 
*/
  BigDecimal getEps (SymbolTable symbolTable)
    {
    BigDecimal deflt = new BigDecimal ("0.000001");
    Value _epsValue = symbolTable.getVariable ("eps");
    if (_epsValue != null)
      { 
      if (_epsValue instanceof NumberValue)
        {
        NumberValue epsValue = (NumberValue)_epsValue;
        return epsValue.toBigDecimal();
        }
      else
        return deflt;
      }
    else
      return deflt;
    }


  abstract BigDecimal run (ParseTree ptfn, String name, EvaluationContext context, 
         SymbolTable table,
         int maxIters, BigDecimal eps, BigDecimal low, BigDecimal high)
       throws EvaluationException;

  public ParseTree evaluate (SymbolTable table, ParseTreeList args, 
       EvaluationContext context)
     throws EvaluationException
    {
    checkArgCount (args.size());

    ParseTree ptfn = args.get (0);

    ParseTree name = args.get (1);

    if (!(name instanceof ParseTreeVariable))
      throw new EvaluationException 
         ("Expected a variable name as the second argument to " 
           + getName() + "()");
    
    ParseTreeVariable varName = (ParseTreeVariable) name;

    ParseTree ptLow = args.get (2).evaluate (table, context);
    ParseTree ptHigh = args.get (3).evaluate (table, context);

    Value vLow = ptLow.toValue();
    Value vHigh = ptHigh.toValue();

    BigDecimal low, high;

    if (vLow instanceof NumberValue)
       low = ((NumberValue)vLow).toBigDecimal();
    else
      throw new EvaluationException 
         ("Third argument to " + getName() + "() must evaluate to a real number");

    if (vHigh instanceof NumberValue)
       high = ((NumberValue)vHigh).toBigDecimal();
    else
      throw new EvaluationException 
         ("Fourth argument to " + getName() + "() must evaluate to a real number");

    if (low.compareTo(high) == 0)
      throw new EvaluationException 
         (getName() + "(): upper and lower bounds must be different");

    int maxIters = getIters (table);
    BigDecimal eps = getEps (table);

    BigDecimal res = run (ptfn, varName.getVariableName(), context, table, 
      maxIters, eps, low, high);
    return new ParseTreeNumber (res); 
    }
  }






package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;

public class Evaluator 
  {
  EvaluationContext context = new EvaluationContext (15); 
  SymbolTable symbolTable = new DefaultSymbolTable();

  public void setEvaluationContext (EvaluationContext context)
    {
    this.context = context;
    }

  public EvaluationContext getEvaluationContext ()
    {
    return context;
    }

  public MathContext getMC() 
    {
    return context.getMC();
    }

  public void setMC (MathContext mc)
    {
    context.setMC (mc);
    }

  public void setPrecision (int precision)
    {
    context.setPrecision (precision);
    }

  public int getPrecision()
    {
    return context.getPrecision();
    }

  public void setSymbolTable (SymbolTable symbolTable)
    {
    this.symbolTable = symbolTable;
    }

  public SymbolTable getSymbolTable ()
    {
    return symbolTable;
    }

  public ParseTree evaluate (ParseTree pt, SymbolTable table)
      throws EvaluationException
    {
    return pt.evaluate (table, context);
    }

  public Value evaluate (ParseTree pt)
      throws EvaluationException
    {
    Value precision = symbolTable.getVariable ("precision");
    if (precision != null && precision instanceof NumberValue)
      {
      //System.out.println ("precision = " + precision);
      BigDecimal p = ((NumberValue)precision).toBigDecimal();
      int p2 = p.intValue();
      context.setPrecision (p2);
      }
    ParseTree ptr = pt.evaluate (symbolTable, context);
    return ptr.toValue();
    }
  }




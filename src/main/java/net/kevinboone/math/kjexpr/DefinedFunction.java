/*===============================================================================
KJEXPR
DefinedFunction.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


public class DefinedFunction extends Function
  {
  ParseTree parseTree;

  public DefinedFunction (String name, String desc, ParseTree parseTree)
    {
    super (name, desc, -1); 
    this.parseTree = parseTree;
    }

  public ParseTree evaluate (SymbolTable table, ParseTreeList args, 
       EvaluationContext context)
     throws EvaluationException
    {
    Hashtable<String, Value> variables = table.getVariableTable(); 
    Hashtable<String, Value> newVariables = 
       new Hashtable<String, Value>(variables); 
    int l = args.size();
    for (int i = 0; i < l; i++)
      {
      ParseTree pta = args.get (i);
      ParseTree ptb = pta.evaluate (table, context);
      Value v = ptb.toValue ();
      String name = "arg" + i;
      v.setName (name);
      newVariables.put (name, v);
      }
    table.setVariableTable(newVariables); 
    ParseTree ptr = parseTree.evaluate (table, context); 
    table.setVariableTable(variables); 
    return ptr;
    }
  }






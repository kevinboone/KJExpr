package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;


public class ParseTreeVariable extends ParseTree
  {
  String name;

  public ParseTreeVariable (String name)
    {
    this.name = name;
    }

  public String getName() { return ("variable"); }

  public String getVariableName () { return this.name; }

  public String toString()
    {
    return "[ " + getName() + ": " + name + "]";
    }

  public ParseTree evaluate (SymbolTable table, 
        EvaluationContext context)
      throws EvaluationException
    {
    Value v = table.getVariable (name);
    if (v != null)
      return v.toParseTree (table, context);
    else
      throw new UndefinedVariableException (name);
    }
  }





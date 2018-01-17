package net.kevinboone.math.kjexpr;
import java.util.*;


public class ParseTreeFunction extends ParseTree
  {
  String name;
  ParseTreeList args;

  public ParseTreeFunction (String name, ParseTreeList args)
    {
    this.name = name;
    this.args = args;
    }

  public String getName() { return ("function"); }

  public String toString ()
    {
    return "[ "+ getName() + ": " + name +  " ( " + args + " " + " ) ]";
    }

  public ParseTree evaluate (SymbolTable table, EvaluationContext context)
      throws EvaluationException
    {
    Function fn = table.getFunction (name);
    if (fn != null)
      {
      return fn.evaluate (table, args, context); 
      }
    else
      throw new EvaluationException ("Undefined function '" + name + "'");
    }
  }




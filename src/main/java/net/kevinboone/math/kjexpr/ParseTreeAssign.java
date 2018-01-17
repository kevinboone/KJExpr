package net.kevinboone.math.kjexpr;
import java.util.*;


public class ParseTreeAssign extends ParseTree
  {
  String var;
  ParseTree value;

  public ParseTreeAssign (String var, ParseTree value)
    {
    this.var = var;
    this.value = value;
    }

  public String getName() { return ("assign"); }

  public String toString ()
    {
    return "[ "+ getName() + ": " + var +  " = " + value + " ]";
    }

  public ParseTree evaluate (SymbolTable table, EvaluationContext context)
      throws EvaluationException
    {
    ParseTree ptr = value.evaluate (table, context);
    Value result = ptr.toValue();
    result.setName (var);
    table.addVariable (result);
    return ptr;
    }
  }





package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;


public class ParseTreeDefine extends ParseTree
  {
  String var;
  ParseTree value;
  String desc;

  public ParseTreeDefine (String var, ParseTree value)
    {
    this.var = var;
    this.value = value;
    this.desc = var;
    }

  public String getName() { return ("define"); }
  public void setDesc (String desc) { this.desc = desc; }

  public String toString ()
    {
    return "[ "+ getName() + ": " + var +  " = " + value + " ]";
    }

  public ParseTree evaluate (SymbolTable table, EvaluationContext context)
      throws EvaluationException
    {
    table.addFunction (new DefinedFunction (var, desc, value));
    ParseTree ptr = new ParseTreeNumber (new BigDecimal ("0")); // FRIG 
    return ptr;
    }
  }




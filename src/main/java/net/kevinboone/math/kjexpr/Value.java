package net.kevinboone.math.kjexpr;
import java.util.*;

public abstract class Value
  {
  String name;
  String desc;
 
  public Value (String name, String desc)
    {
    this.name = name;
    this.desc = desc;
    if (this.desc == null)
      {
      this.desc = "";
      }
    }

  public void setName (String name) { this.name = name; }
  public String getName() { return name; }
  public String getDesc() { return desc; }

  public abstract ParseTree toParseTree (SymbolTable table, 
      EvaluationContext context)
    throws EvaluationException;
  }


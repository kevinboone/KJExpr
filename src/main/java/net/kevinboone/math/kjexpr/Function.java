package net.kevinboone.math.kjexpr;
import java.util.*;


public abstract class Function 
  {
  int argc;
  String name;
  String desc;

  public Function (String name, String desc, int argc)
    {
    this.name = name;
    this.desc = desc;
    this.argc = argc;
    }

  public String getName() { return name; }
  public String getDesc() { return desc; }
  public int getArgc() { return argc; }

  public void setDesc (String desc) { this.desc = desc; }

  public abstract ParseTree evaluate (SymbolTable table, ParseTreeList args, 
       EvaluationContext context)
     throws EvaluationException;

  public void checkArgCount (int l)
      throws ArgCountException
    {
    if (l != argc)
      throw new ArgCountException (getName(), argc, l);
    }

  }


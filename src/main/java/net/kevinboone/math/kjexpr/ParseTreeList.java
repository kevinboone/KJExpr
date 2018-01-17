package net.kevinboone.math.kjexpr;
import java.util.*;


public class ParseTreeList extends ParseTree
  {
  Vector<ParseTree> list = new Vector<ParseTree>();

  public int size() { return list.size(); }

  public ParseTree get (int n) { return list.get (n); }

  public String getName() { return ("list"); }

  public String toString ()
    {
    return "[ " + getName() + ": " + list + " ]"; 
    }

  public ParseTree evaluate (SymbolTable table, EvaluationContext context)
      throws EvaluationException
    {
    throw new EvaluationException ("lists cannot be evaluated");
    }

  public void add (ParseTree pt)
    {
    list.add (pt);   
    }
  }



package net.kevinboone.math.kjexpr;
import java.util.*;


public class ParseTreeInfix extends ParseTree
  {
  ParseTree first;
  String operator;
  ParseTree second;

  public ParseTreeInfix (ParseTree first, String operator, ParseTree second)
    {
    this.first = first;
    this.operator = operator;
    this.second = second;
    }

  public String getName() { return ("infix"); }

  public String toString ()
    {
    return "[ " + getName () + ": " + first + " " + operator + " " + second + " ]";
    }

  public ParseTree evaluate (SymbolTable table, EvaluationContext context)
      throws EvaluationException
    {
    ParseTree pt1 = first.evaluate (table, context);
    ParseTree pt2 = second.evaluate (table, context);
    
    BinaryOperator op = table.getBinaryOperator (operator);
    if (op != null)
      return op.apply (pt1, pt2, context);
    else
      throw new EvaluationException ("Unknown binary operator: '" 
        + operator + "'");
    }
  }



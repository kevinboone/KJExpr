package net.kevinboone.math.kjexpr;
import java.util.*;


public class ParseTreeUnary extends ParseTree
  {
  String operator;
  ParseTree first;

  public ParseTreeUnary (String operator, ParseTree first)
    {
    this.first = first;
    this.operator = operator;
    }

  public String getName() { return ("unary"); }

  public String toString ()
    {
    return "[ unary: " + operator + " " + first + " ]";
    }

  public ParseTree evaluate (SymbolTable table, EvaluationContext context)
      throws EvaluationException
    {
    ParseTree pt = first.evaluate (table, context);
    UnaryOperator op = table.getUnaryOperator (operator);
    if (op != null)
      return op.apply (pt);
    else
      throw new EvaluationException ("Unknown unary operator: '" + op + "'");
    }
  }




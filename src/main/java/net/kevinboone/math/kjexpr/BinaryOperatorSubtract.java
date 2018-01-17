package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;


public class BinaryOperatorSubtract extends BinaryOperator
  {
  public BinaryOperatorSubtract()
    {
    super ("-", "subtraction");
    }

  ParseTree apply (ParseTree arg1, ParseTree arg2, EvaluationContext context)
      throws EvaluationException
    {
    if (arg1 instanceof ParseTreeNumber)
      {
      if (arg2 instanceof ParseTreeNumber)
        {
        ParseTreeNumber n1 = (ParseTreeNumber)arg1;
        ParseTreeNumber n2 = (ParseTreeNumber)arg2;

        BigDecimal op1 = n1.toBigDecimal();
        BigDecimal op2 = n2.toBigDecimal();
      
         return new ParseTreeNumber 
            (op1.subtract (op2)); 
        }
      else
        throw new EvaluationException 
         ("Can't subtract values of class " + arg1.getName() + " and " 
           + arg2.getName());
      }
    else
      throw new EvaluationException 
       ("Can't subtract values of class " + arg1.getName() + " and " 
         + arg2.getName());
    }
  }




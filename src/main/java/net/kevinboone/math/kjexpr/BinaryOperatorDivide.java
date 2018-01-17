package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;


public class BinaryOperatorDivide extends BinaryOperator
  {
  public BinaryOperatorDivide()
    {
    super ("/", "division");
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

        BigDecimal numerator = n1.toBigDecimal();
        BigDecimal denominator = n2.toBigDecimal();
      
        if (denominator.compareTo (BigDecimal.ZERO) == 0)
          throw new EvaluationException 
           ("Decimal number division by zero");
          else
           {
           MathContext mc = context.getMC(); 
            return new ParseTreeNumber 
              (numerator.divide (denominator, mc));
           }
        }
      else
        throw new EvaluationException 
         ("Can't divide values of class " + arg1.getName() + " and " 
           + arg2.getName());
      }
    else
      throw new EvaluationException 
       ("Can't divide values of class " + arg1.getName() + " and " 
         + arg2.getName());
    }
  }




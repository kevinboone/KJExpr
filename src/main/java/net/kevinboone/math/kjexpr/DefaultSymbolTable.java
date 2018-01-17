/*==========================================================================
KJEXPR
DefaultSymbolTable.java
Copyright (c)2017 Kevin Boone, GPL3
==========================================================================*/

package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;

/**
  DefaultSymbolTable is used by an Evaluator by default. It contains
  symbols for basic arithmetic operators only. For a symbol table with
  standard functions, replace it with CommonSymbolTable 
*/
public class DefaultSymbolTable extends SymbolTable
  {
  public DefaultSymbolTable ()
    {
    super();

    addUnaryOperator ("+", new UnaryOperatorPlus());
    addUnaryOperator ("-", new UnaryOperatorMinus());

    addBinaryOperator (new BinaryOperatorDivide()); 
    addBinaryOperator (new BinaryOperatorMultiply()); 
    addBinaryOperator (new BinaryOperatorAdd()); 
    addBinaryOperator (new BinaryOperatorSubtract()); 
    addBinaryOperator (new BinaryOperatorModulo()); 
    addBinaryOperator (new BinaryOperatorPower()); 

    addVariable (new NumberValue ("precision", "calculation significant figures",         new BigDecimal ("30")));
    } 
  }



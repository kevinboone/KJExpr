/*==========================================================================
KJEXPR
CommonSymbolTable.java
Copyright (c)2017 Kevin Boone, GPL3
==========================================================================*/

package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;

/**
  CommonSymbolTable extends DefaultSymbolTable, and adds support for 
  common functions like log() and sin(), as well as e and pi
*/
public class CommonSymbolTable extends DefaultSymbolTable
  {
  /** 
    CommonSymbolTable must be initialized with a Parser, as many of the
    function definitions are provided in text form, which must be compiled.
  */
  public CommonSymbolTable (Parser parser)
    {
    super();
    initLibrary (parser);
    } 


  public void addLibraryFunction (Parser parser, String expr, String desc)
    {
    EvaluationContext c = new EvaluationContext(30);
    try
      {
      ParseTree pt = parser.parseString (expr, "stdlib");
      if (pt instanceof ParseTreeDefine)
        { 
        ((ParseTreeDefine)pt).setDesc (desc);
        }
      ParseTree result = pt.evaluate (this, c);
      }
    catch (Exception e)
      {
      throw new RuntimeException 
         ("Fatal: exception caught whilst initializing library: " + e);
      }
    }

    
  public void initLibrary (Parser parser)
    {
    addFunction (new FunctionSqrt()); 
    addFunction (new FunctionLog()); 
    addFunction (new FunctionSin()); 
    addFunction (new FunctionCos()); 
    addFunction (new FunctionAcos()); 
    addFunction (new FunctionAsin()); 
    addFunction (new FunctionFactorial()); 
    addFunction (new FunctionSolveSecant()); 
    addFunction (new FunctionSolveBisection()); 
    addFunction (new FunctionGCD()); 
  
    addVariable (new PiValue());
    addVariable (new EValue());

    addLibraryFunction (parser, "tan:sin(arg0)/cos(arg0)", "tangent of an angle");
    addLibraryFunction (parser, "ln:log(arg0)/log(e)", "natural logarithm, base e");
    addLibraryFunction (parser, "atan:asin(arg0/sqrt(arg0^2+1))", "inverse tangent");
    addLibraryFunction (parser, "exp:e^arg0", "natural antilogarithm");
    addLibraryFunction (parser, "alog:10^arg0", "antilogarithm, base 10");
    addLibraryFunction (parser, "sinh:(e^arg0-e^(-arg0))/2", "hyperbolic sine");
    addLibraryFunction (parser, "cosh:(e^arg0+e^(-arg0))/2", "hyperbolic cosine");
    addLibraryFunction (parser, "tanh:(e^arg0-e^(-arg0))/(e^arg0+e^(-arg0))", 
      "hyperbolic tangent");
    addLibraryFunction (parser, "asinh:ln(arg0+sqrt(1+arg0^2))", "inverse hyperbolic sine");
    addLibraryFunction (parser, "acosh:ln(arg0+sqrt(1+arg0)*sqrt(arg0-1))", 
      "inverse hyperbolic cosine");
    addLibraryFunction (parser, "atanh:0.5*ln((1+arg0)/(1-arg0))", "inverse hyperbolic tangent");
    }
  }




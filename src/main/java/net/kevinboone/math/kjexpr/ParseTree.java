/*============================================================================
  KCEXPR
  ParseTree.java
  Copyright (c)2017 Kevin Boone, GPL v3.0
============================================================================*/
package net.kevinboone.math.kjexpr;
import java.util.*;


/**
  ParseTree is the abstract base class for all entities that are generated
  by parsing. These include numbers, lists, function calls, etc.
  ParseTree is used both in parsing, and evaluation, as evaluation proceeds
  by recursively descending the parser tree, evaluating from the
  bottom up.
*/
public abstract class ParseTree
  {
  public abstract ParseTree evaluate (SymbolTable table, 
      EvaluationContext context)
    throws EvaluationException;

  /** Convert the parse tree to a Value. Only a few ParseTree subclasses
      are capable of being rendered as a value -- these override this
      method. All others just throw NoValueException.
  */
  public Value toValue()
      throws EvaluationException
    {
    throw new NoValueException (getName());
    }

  public abstract String getName(); 
  }




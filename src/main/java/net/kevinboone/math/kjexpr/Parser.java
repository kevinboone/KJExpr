/*==========================================================================
KJEXPR
Parser.java
Copyright (c)2017 Kevin Boone, GPL3
==========================================================================*/

package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*; 

/** Methods for parsing input into a ParseTree object. */
public class Parser 
  {
  public static String VERSION = "0.0.1";

  Tokenizer tokenizer = Tokenizer.getDefault();
  // TODO
  private void trace (String s)
    {
    //System.out.println (s);
    }

  /** NAME -- (terminal).  */
  ParseResult parseName (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseName " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      if (t.getType() == Token.Type.NAME)
        {
        return new ParseResult (null, 1, pos, null);
        }
      else
        return new ParseResult (null, 0, pos, "Expected name");
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }

  /** NUMBER -- (terminal).  */
  ParseResult parseNumber (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseNumber " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      if (t.getType() == Token.Type.NUMBER)
        {
        String s  = t.getText();
        // int TODO -- default base
        try
          {
          ParseTree pt = new ParseTreeNumber (s, 10); // TODO -- parse errors
          return new ParseResult (pt, 1, pos, null);
          }
        catch (NumberFormatException e)
         {
         throw new ParseException ("Invalid number", t.getText(), 
           t.getFile(), t.getLine(), t.getCol());
         }
        }
      return new ParseResult (null, 0, pos, 
        "Expected number, got " + t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }


  /** EXPR -- TERM { +/- TERM } | ASSIGN | DEFINE */
  ParseResult parseExpr (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseExpr " + pos + " " + depth);
    if (pos < tokens.size())
      {
      // EXPR : DEFINE 
      
      ParseResult pr1 = parseDefine (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
        {
        return pr1;
        } 
     
      // EXPR : ASSIGN
      
      pr1 = parseAssign (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
        {
        return pr1;
        } 
     
      // EXPR : TERM 
      boolean multiple = false;
      int startPos = pos;
      pr1 = parseTerm (depth + 1, tokens, pos);
      ParseTree result = null;
      if (pr1.getCount() > 0)
        {
        result = pr1.getParseTree();
        boolean done = false;
        pos += pr1.getCount();
        while (!done && pos < tokens.size())
          {
          int tempPos = pos;
          done = true;
          ParseResult pr2 = parseAdditiveOp (depth + 1, tokens, tempPos); 
          if (pr2.getCount() > 0 && tempPos + pr2.getCount() <= tokens.size())
            {
            String op = tokens.elementAt (tempPos).getText(); 
            tempPos += pr2.getCount();
            ParseResult pr3 = parseTerm (depth + 1, tokens, tempPos);
            if (pr3.getCount() > 0 && tempPos + pr3.getCount() 
                <= tokens.size())
              {
              tempPos += pr3.getCount();
              result = new ParseTreeInfix (result, op, 
                 pr3.getParseTree());
              done = false;
              multiple = true;
              pos = tempPos;
              }
            }
          }
        if (multiple)
          {
          return new ParseResult (result, pos - startPos, pos, null);
          }
        else
          {
          return new ParseResult 
            (pr1.getParseTree(), pr1.getCount(), pos, null);
          }
        }
      else
        {
        return pr1; 
        }
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }


  /** FUNCTion -- NAME ARGLIST. */
  ParseResult parseFunction (int depth, 
      Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseFunction " + pos + " " + depth);

    if (pos < tokens.size())
      {
      ParseResult pr1 = parseName (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
        {
        String name = tokens.elementAt(pos).getText();
        ParseResult pr2 = parseArgList 
          (depth, tokens, pos + pr1.getCount());
        if (pr2 != null && pr2.getCount() > 0)
          {
          ParseTree pt = new ParseTreeFunction (name, 
            (ParseTreeList)pr2.getParseTree());
          return new ParseResult 
            (pt, pr1.getCount() + pr2.getCount(), pos, null);
          }
        else
          {
          return new ParseResult 
            (null, 0, pos, "Expected function");
          }
        }
      else
        return new ParseResult (null, 0, pos, "Expected name");
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }


  /** NEGABLEFACTOR -- [-] FACTOR. */
  ParseResult parseNegableFunctionOrFactor (int depth, 
      Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseNegableFactor " + pos + " " + depth);

    if (pos < tokens.size())
      {
      // NEGABLEFACTOR : +/- FACTOR 
      ParseResult pr1 = parseAdditiveOp (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
        {
        String op = tokens.elementAt (pos).getText();
        ParseResult pr2 = parseFunctionOrFactor 
          (depth, tokens, pos + pr1.getCount());
        if (pr2 != null && pr2.getCount() > 0)
          {
          ParseTree pt = new ParseTreeUnary (op, pr2.getParseTree());
          return new ParseResult 
            (pt, pr1.getCount() + pr2.getCount(), pos, null);
          }
        else
          {
          return new ParseResult 
            (null, 0, pos, "Expected function or factor");
          }
        }
      else
        // NEGABLEFACTOR : FACTOR 
        return parseFunctionOrFactor (depth, tokens, pos);
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }


  /** TERM -- FACTOR { * FACTOR }. */
  ParseResult parseTerm (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseTerm " + pos + " " + depth);

    if (pos < tokens.size())
      {
      // EXPR : TERM 
      boolean multiple = false;
      int startPos = pos;
      ParseResult pr1 = parseNegableFunctionOrFactor (depth + 1, tokens, pos);
      ParseTree result = null;
      if (pr1.getCount() > 0)
        {
        result = pr1.getParseTree();
        //System.out.println ("TERM ONE");
        boolean done = false;
        pos += pr1.getCount();
        while (!done && pos < tokens.size())
          {
          int tempPos = pos;
          done = true;
          ParseResult pr2 = parseMultiplicativeOp (depth + 1, tokens, tempPos); 
          if (pr2.getCount() > 0 && tempPos + pr2.getCount() <= tokens.size())
            {
            String op = tokens.elementAt (tempPos).getText(); 
            tempPos += pr2.getCount();
            ParseResult pr3 = parseNegableFunctionOrFactor 
              (depth + 1, tokens, tempPos);
            if (pr3.getCount() > 0 && tempPos + pr3.getCount() <= tokens.size())
              {
              tempPos += pr3.getCount();
              result = new ParseTreeInfix (result, op, 
                 pr3.getParseTree());
              done = false;
              multiple = true;
              pos = tempPos;
              }
            }
          }
        if (multiple)
          {
          return new ParseResult (result, pos - startPos, pos, null);
          }
        else 
          {
          return new ParseResult (pr1.getParseTree(), pr1.getCount(), pos, null);
          }
        }
      else
        {
        return pr1;
        }
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    
    }

  /** LIST -- EXPR { , EXPR }. */
  ParseResult parseList (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseList " + pos + " " + depth);

    if (pos < tokens.size())
      {
      ParseTreeList ptr = new ParseTreeList();
      // EXPR : TERM 
      boolean multiple = false;
      int startPos = pos;
      ParseResult pr1 = parseExpr (depth + 1, tokens, pos);
      ParseTreeList result = new ParseTreeList();
      if (pr1.getCount() > 0)
        {
        result.add (pr1.getParseTree());
        //System.out.println ("TERM ONE");
        boolean done = false;
        pos += pr1.getCount();
        while (!done && pos < tokens.size())
          {
          int tempPos = pos;
          done = true;
          ParseResult pr2 = parseComma (depth + 1, tokens, tempPos); 
          if (pr2.getCount() > 0 && tempPos + pr2.getCount() <= tokens.size())
            {
            String op = tokens.elementAt (tempPos).getText(); 
            tempPos += pr2.getCount();
            ParseResult pr3 = parseExpr
              (depth + 1, tokens, tempPos);
            if (pr3.getCount() > 0 && tempPos + pr3.getCount() <= tokens.size())
              {
              tempPos += pr3.getCount();
              result.add (pr3.getParseTree());
              done = false;
              multiple = true;
              pos = tempPos;
              }
            }
          }
        return new ParseResult (result, pos - startPos, pos, null);
        }
      else
        {
        return pr1;
        }
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    
    }

  /** ARGLIST - ( LIST ), */
  ParseResult parseArgList (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseArgList " + pos + " " + depth);
    if (pos < tokens.size())
      {
      ParseResult pr1 = parseOpenParen (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
	{
	ParseResult pr2 = parseList
	   (depth + 1, tokens, pos + pr1.getCount());
	if (pr2.getCount() > 0)
	  {
	  ParseResult pr3 = parseCloseParen
	     (depth + 1, tokens, pos + pr1.getCount() + pr2.getCount());
	  if (pr3.getCount() > 0)
	    {
	    int terms = pr1.getCount() + pr2.getCount() + pr3.getCount();
	    if (terms > 0)
	      {
	      ParseTree ptr = pr2.getParseTree();
	      return new ParseResult  
		(ptr, pr1.getCount() + pr2.getCount() + pr3.getCount(), 
		  pos, null);
	      }
	    else
	      {
	      return new ParseResult (null, 0, pos, "Unexpected end of input");
	      }
	    }
	  else
	    return pr3;
	  }
	else
          {
          // Consider an empty arglist -- "()". If next is a close paren,
          //  just construct a ParseTreeList with nothing in it
	  ParseResult pr3 = parseCloseParen
	     (depth + 1, tokens, pos + pr1.getCount() + pr2.getCount());
	  if (pr3.getCount() > 0)
	    {
	    int terms = pr1.getCount() + pr3.getCount();
            ParseTree ptr = new ParseTreeList();
	    return new ParseResult  
		(ptr, terms, pos, null);
	    }
          else
	    return pr2;
          }
	}
      else
        return pr1;
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }


  /** ASSIGN - NAME = EXPR.  */
  ParseResult parseAssign (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseAssign " + pos + " " + depth);
    if (pos < tokens.size())
      {
      ParseResult pr1 = parseName (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
	{
        String name = tokens.elementAt(pos).getText();
	ParseResult pr2 = parseEquals
	   (depth + 1, tokens, pos + pr1.getCount());
	if (pr2.getCount() > 0)
	  {
	  ParseResult pr3 = parseExpr
	     (depth + 1, tokens, pos + pr1.getCount() + pr2.getCount());
	  if (pr3.getCount() > 0)
	    {
	    int terms = pr1.getCount() + pr2.getCount() + pr3.getCount();
	    if (terms > 0)
	      {
              ParseTree pt = pr3.getParseTree();
	      ParseTree ptr = new ParseTreeAssign (name, pt);
	      return new ParseResult  
		(ptr, terms, pos, null);
	      }
	    else
	      {
	      return new ParseResult (null, 0, pos, "Unexpected end of input");
	      }
	    }
	  else
	    return pr3;
	  }
	else
	  return pr2;
	}
      else
        return pr1;
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }



  /** DEFINE - NAME : EXPR.  */
  ParseResult parseDefine (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseAssign " + pos + " " + depth);
    if (pos < tokens.size())
      {
      ParseResult pr1 = parseName (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
	{
        String name = tokens.elementAt(pos).getText();
	ParseResult pr2 = parseColon
	   (depth + 1, tokens, pos + pr1.getCount());
	if (pr2.getCount() > 0)
	  {
	  ParseResult pr3 = parseExpr
	     (depth + 1, tokens, pos + pr1.getCount() + pr2.getCount());
	  if (pr3.getCount() > 0)
	    {
	    int terms = pr1.getCount() + pr2.getCount() + pr3.getCount();
	    if (terms > 0)
	      {
              ParseTree pt = pr3.getParseTree();
	      ParseTree ptr = new ParseTreeDefine (name, pt);
	      return new ParseResult  
		(ptr, terms, pos, null);
	      }
	    else
	      {
	      return new ParseResult (null, 0, pos, "Unexpected end of input");
	      }
	    }
	  else
	    return pr3;
	  }
	else
	  return pr2;
	}
      else
        return pr1;
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }



  /** PARENEXPR - ( EXPR ), */
  ParseResult parseParenExpression (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseParenExpression " + pos + " " + depth);
    if (pos < tokens.size())
      {

      ParseResult pr1 = parseOpenParen (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
	{
	ParseResult pr2 = parseExpr
	   (depth + 1, tokens, pos + pr1.getCount());
	if (pr2.getCount() > 0)
	  {
	  ParseResult pr3 = parseCloseParen
	     (depth + 1, tokens, pos + pr1.getCount() + pr2.getCount());
	  if (pr3.getCount() > 0)
	    {
	    int terms = pr1.getCount() + pr2.getCount() + pr3.getCount();
	    if (terms > 0)
	      {
	      ParseTree ptr = pr2.getParseTree();
	      return new ParseResult  
		(ptr, pr1.getCount() + pr2.getCount() + pr3.getCount(), 
		  pos, null);
	      }
	    else
	      {
	      return new ParseResult (null, 0, pos, "Unexpected end of input");
	      }
	    }
	  else
	    return pr3;
	  }
	else
	  return pr2;
	}
      else
        return pr1;
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }



  /** FACTOR -- FUNCTION | PARENEXPRESSION | NUMBER. */
  ParseResult parseFunctionOrFactor (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    // TODO -- add function
    trace ("parseFunctionOrFactor " + pos + " " + depth);
    if (pos < tokens.size())
      {
      
      // FACTOR -- ( EXPRESSION ) 
      ParseResult pr1 = parseParenExpression (depth, tokens, pos);
      if (pr1.getCount() > 0)
        {
        return new ParseResult (pr1.getParseTree(), pr1.getCount(), pos, null);
        } 

      // FACTOR : NUMBER
      pr1 = parseNumber (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
        {
        return new ParseResult (pr1.getParseTree(), pr1.getCount(), pos, null);
        }

      // FACTOR : FUNCTION 
      pr1 = parseFunction (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
        {
        return new ParseResult (pr1.getParseTree(), pr1.getCount(), pos, null);
        }
      
      // FACTOR : NAME 
      pr1 = parseName (depth + 1, tokens, pos);
      if (pr1.getCount() > 0)
        {
        ParseTree pt = new ParseTreeVariable (tokens.elementAt(pos).getText());
        return new ParseResult (pt, pr1.getCount(), pos, null);
        }
      else 
        {
        return pr1;
        }
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }


  /** OPENPAREN -- (. */
  ParseResult parseOpenParen (int depth, Vector<Token> tokens, int pos)
    {
    trace ("parseOpenParen " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      String op = t.getText();
      if (op.equals("("))
	{
	return new ParseResult (null, 1, pos, null);
	}
      return new ParseResult (null, 0, pos, "Expected (, got "+ t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }


  /** COLON -- :. */
  ParseResult parseColon (int depth, Vector<Token> tokens, int pos)
    {
    trace ("parseEquals " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      String op = t.getText();
      if (op.equals(":"))
	{
	return new ParseResult (null, 1, pos, null);
	}
      return new ParseResult (null, 0, pos, "Expected =, got "+ t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }

  /** EQUALS -- =. */
  ParseResult parseEquals (int depth, Vector<Token> tokens, int pos)
    {
    trace ("parseEquals " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      String op = t.getText();
      if (op.equals("="))
	{
	return new ParseResult (null, 1, pos, null);
	}
      return new ParseResult (null, 0, pos, "Expected =, got "+ t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }

  /** COMMA -- , . */
  ParseResult parseComma (int depth, Vector<Token> tokens, int pos)
    {
    trace ("parseComma " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      String op = t.getText();
      if (op.equals(","))
	{
	return new ParseResult (null, 1, pos, null);
	}
      return new ParseResult (null, 0, pos, "Expected ',' got "+ t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }

  /** CLOSEPAREN -- ). */
  ParseResult parseCloseParen (int depth, Vector<Token> tokens, int pos)
    {
    trace ("parseOpenParen " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      String op = t.getText();
      if (op.equals(")"))
	{
	return new ParseResult (null, 1, pos, null);
	}
      return new ParseResult 
        (null, 0, pos, "Expected minus, got "+ t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }

  /** ADDITIVEOP -- +/-. */
  ParseResult parseAdditiveOp (int depth, Vector<Token> tokens, int pos)
    {
    trace ("parseAdditiveOp " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      String op = t.getText();
      if (op.equals("-") || op.equals("+"))
	{
	return new ParseResult (null, 1, pos, null);
	}
      return new ParseResult 
        (null, 0, pos, "Expected minus, got "+ t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }

  /** MULTIPLICATIVEOP -- * / % ^.  */
  ParseResult parseMultiplicativeOp (int depth, Vector<Token> tokens, int pos)
    {
    trace ("parseMultiplicativeOp " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      String op = t.getText();
      if (op.equals("*") || op.equals("/") || op.equals("%") || op.equals("^"))
	{
	return new ParseResult (null, 1, pos, null);
	}
      return new ParseResult (null, 0, pos, "Expected minus, got "+ t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }

  /** TIMES -- * . */
  ParseResult parseTimes (int depth, Vector<Token> tokens, int pos)
    {
    trace ("parseTimes " + pos + " " + depth);
    if (pos < tokens.size())
      {
      Token t = tokens.elementAt (pos);
      if (t.getText().equals ("*")) 
	{
	return new ParseResult (null, 1, pos, null); 
	}
      return new ParseResult (null, 0, pos, "Expected *, got "+ t.getText());
      }
    else
      {
      return new ParseResult (null, 0, pos, "Unexpected end of input");
      }
    }

  /** ADDITIVE -- TERM {- TERM}. */
  ParseResult parseAdditive (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseAdditive " + pos + " " + depth);
    ParseResult pr1 = parseTerm (depth + 1, tokens, pos);
    if (pr1.getCount() > 0)
      {
      ParseResult pr2 = parseAdditiveOp 
         (depth + 1, tokens, pos + pr1.getCount());
      if (pr2.getCount() > 0)
	{
	ParseResult pr3 = parseAdditive
           (depth + 1, tokens, pos + pr1.getCount() + pr2.getCount());
	if (pr3.getCount() > 0)
	  {
          int terms = pr1.getCount() + pr2.getCount() + pr3.getCount();
          if (terms > 0)
            {
	    ParseTree ptr = new ParseTreeInfix
              (pr1.getParseTree(), 
                tokens.elementAt(pos + pr1.getCount()).getText(), 
                pr3.getParseTree());
            return new ParseResult  
              (ptr, pr1.getCount() + pr2.getCount() + pr3.getCount(), 
                pos, null);
            }
          else
            {
            return new ParseResult (null, 0, pos, "Unexpected end of input");
            }
	  }
        else
          return pr3;
	}
      else
        return pr2;
      }
    else
      return pr1;
    }

  /** MULTIPLY -- FACTOR * TERM.  */
  ParseResult parseMultiply (int depth, Vector<Token> tokens, int pos)
      throws ParseException
    {
    trace ("parseMultiply " + pos + " " + depth);
    ParseResult pr1 = parseFunctionOrFactor (depth + 1, tokens, pos);
    if (pr1.getCount() > 0)
      {
      ParseResult pr2 = parseTimes
         (depth + 1, tokens, pos + pr1.getCount());
      if (pr2.getCount() > 0)
	{
	ParseResult pr3 = parseTerm
           (depth + 1, tokens, pos + pr1.getCount() + pr2.getCount());
	if (pr3.getCount() > 0)
	  {
	  ParseTree ptr = new ParseTreeMultiply
            (pr1.getParseTree(), pr3.getParseTree());
          return new ParseResult  
            (ptr, pr1.getCount() + pr2.getCount() + pr3.getCount(), pos, null);
	  }
        else
          return pr3;
	}
      else
        return pr2;
      }
    else
      return pr1;
    }



  private ParseException makeParseException (Vector<Token> tokens, int count,
      String message, String filename)
    {
    ParseException e;
    if (count > 0)
      {
      String badToken = tokens.elementAt(count).getText();
      int badCol = tokens.elementAt(count).getCol();
      int badLine = tokens.elementAt(count).getLine();
      String badFile = tokens.elementAt(count).getFile();
      if (badFile == null) badFile = filename;
      e = new ParseException 
        (message != null ? message : "Syntax error", badToken, badFile, 
           badLine, badCol);
      }
    else 
      {
      e = new ParseException 
        (message != null ? message : "Syntax error", null, filename, 0, 0);
      }
    return e;
    }


  /** 
     Parse a list of tokens into a ParseTree. 
     filenmame argument is just for logging purposes. 
  */
  public ParseTree parseTokens (Vector<Token> tokens, String filename)
      throws ParseException
    {
    if (tokens.size() == 0)
      throw makeParseException (tokens, 0, "No input", filename);
      
    ParseResult pr = parseExpr (0, tokens, 0);
    //ParseResult pr = parseAssign (0, tokens, 0);
    int count = pr.getCount();
    if (count >= 0)
      {
      if (count == tokens.size()) 
        return pr.getParseTree();
      else
        {
        // Tokens left after parser complete
        throw makeParseException (tokens, count, pr.getError(), filename);
        }
      }
    else
      {
      // No tokens parsed
      throw makeParseException (tokens, 0, pr.getError(), filename);
      }
    }


  /** 
    Selects a specific tokenizer for parseString() to use.
  */
  public void setTokenizer (Tokenizer tokenizer)
    {
    this.tokenizer = tokenizer;
    }

  
  /** 
     Returns the currently-selected tokenizer.
  */
  public Tokenizer getTokenizer ()
    {
    return tokenizer;
    }
  
  /** 
     Parse a String into a ParseTree. 
     filenmame argument is just for logging purposes. 
  */
  public ParseTree parseString (String line, String filename)
      throws ParseException
    {
    Vector<Token> tokens = tokenizer.run (line, filename);
    if (tokens.size() != 0)
      {
      //for (Token t: tokens)
      //    System.out.println (t);
      ParseTree pt = parseTokens (tokens, filename);
      return pt;
      }
    else
      throw new ParseException ("No expression to parse", null, filename,
       0, 0);
    } 

  }



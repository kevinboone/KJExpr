package net.kevinboone.math.kjexpr;
import java.util.*;


public class DefaultTokenizer extends Tokenizer
  {
  StringBuffer current;
  static final int STATE_UNKNOWN = 0;
  static final int STATE_NUMBER = 1;
  static final int STATE_WHITE = 2;
  static final int STATE_OPERATOR = 3;
  static final int STATE_NAME = 4;

  static boolean isDigit (char c)
    {
    if (c >= '0' && c <= '9') return true;
    return false;
    // TODO Base
    }

  static boolean isNameChar (char c)
    {
    if (Character.isLetter(c)) return true;
    if (c == '_') return true;
    return false;
    }

  static boolean isOpChar (char c)
    {
    if (c == '+') return true;
    if (c == '-') return true;
    if (c == '*') return true;
    if (c == '/') return true;
    if (c == '=') return true;
    if (c == ':') return true;
    if (c == '^') return true;
    if (c == '%') return true;
    if (c == '(') return true;
    if (c == ')') return true;
    if (c == ',') return true;
    return false;
    }


  Token makeToken (String filename, int row, int col, 
           String current, int state)
    {
    // TODO
    int start = col - current.length();
    switch (state)
      {
      case STATE_NUMBER:
        return new Token (Token.Type.NUMBER, current, filename, row, start);
      case STATE_OPERATOR:
        return new Token (Token.Type.OPERATOR, current, filename, row, start);
      case STATE_NAME:
        return new Token (Token.Type.NAME, current, filename, row, start);
      }
    System.err.println 
          ("Internal error: can't make token in state " + state);
    System.exit (-1);
    return null;
    }


  /** Tokenize a string. The filename argument is just stored in the
 *    generated tokens, for logging purposes. */
  public Vector<Token> run (String line, String filename)
      throws ParseException
    {
    Vector<Token> tokens = new Vector<Token>();

    int row = 1;
    int col = 0;
    int state = STATE_UNKNOWN;
    current = new StringBuffer();
     
    int l = line.length();
    for (int i = 0; i < l; i++)
      {
      char c = line.charAt(i);
      col++; // TODO -- handle line breaks

      char tokenEndsWith = 0;

      if (current.length() > 0)
        {
        tokenEndsWith = current.charAt (current.length() - 1);
        }

      /*=== STATE_UNKNOWN ===*/

      if (state == STATE_UNKNOWN && isDigit (c))
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_UNKNOWN && c == '.') // TODO -- selectable sep
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_UNKNOWN && (c == ' ' || c == '\t' || c == '\n'))
        {
        state = STATE_WHITE;
        }
      else if (state == STATE_UNKNOWN && isOpChar (c))
        {
        current.append (c);
        state = STATE_OPERATOR;
        }
      else if (state == STATE_UNKNOWN && isNameChar (c))
        {
        current.append (c);
        state = STATE_NAME;
        }

      /*=== STATE_NUMBER ===*/

      else if (state == STATE_NUMBER && isDigit (c))
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_NUMBER && c == '.') // TODO -- selectable sep
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_NUMBER && c == 'x') // Allowed in hex numbers 
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_NUMBER && c == 'E') // Allowed in numbers 
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_NUMBER && c == 'e') // Allowed in numbers 
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_NUMBER && c == '-' && 
          (tokenEndsWith == 'e' || tokenEndsWith == 'E' || tokenEndsWith == '\\')) 
             // - Allowed in numbers after e or E 
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_NUMBER && (c == ' ' || c == '\t' || c == '\n'))
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          current = new StringBuffer();
          tokens.add (t);
          }
        state = STATE_WHITE;
        }
      else if (state == STATE_NUMBER && isOpChar (c))
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          tokens.add (t);
          }
        current = new StringBuffer();
        current.append (c);
        state = STATE_OPERATOR;
        }
      else if (state == STATE_NUMBER && isNameChar (c))
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          tokens.add (t);
          }
        current = new StringBuffer();
        current.append (c);
        state = STATE_NAME;
        }


      /*=== STATE_WHITE ===*/

      else if (state == STATE_WHITE && isDigit (c)) 
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_WHITE && c == '.') // TODO -- selectable sep
        {
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_WHITE && (c == ' ' || c == '\t' || c == '\n'))
        {
        state = STATE_NUMBER; // No change -- eat whitespace
        }
      else if (state == STATE_WHITE && isOpChar (c))
        {
        current = new StringBuffer();
        current.append (c);
        state = STATE_OPERATOR;
        }
      else if (state == STATE_WHITE && isNameChar (c))
        {
        current = new StringBuffer();
        current.append (c);
        state = STATE_NAME;
        }

      /*=== STATE_OPERATOR ===*/

      else if (state == STATE_OPERATOR && isDigit (c))
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          current = new StringBuffer();
          tokens.add (t);
          }
        current = new StringBuffer();
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_OPERATOR && c == '.') // TODO -- selectable sep
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col,
            new String (current), state); 
          current = new StringBuffer();
          tokens.add (t);
          }
        current = new StringBuffer();
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_OPERATOR && (c == ' ' || c == '\t' || c == '\n'))
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          current = new StringBuffer();
          tokens.add (t);
          }
        current = new StringBuffer();
        state = STATE_WHITE;
        }
      else if (state == STATE_OPERATOR && isOpChar (c))
        {
        Token t = makeToken (filename, row, col, new String (current), state); 
        tokens.add (t);
        current = new StringBuffer();
        current.append (c);
        t = makeToken (filename, row, col, new String (current), state); 
        tokens.add (t);
        current = new StringBuffer();
        state = STATE_UNKNOWN;
        }
      else if (state == STATE_OPERATOR && isNameChar (c))
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          current = new StringBuffer();
          tokens.add (t);
          }
        current = new StringBuffer();
        current.append (c);
        state = STATE_NAME;
        }

      /*=== STATE_NAME ===*/

      else if (state == STATE_NAME && isDigit (c))
        {
        // A name can contain digits
        current.append (c);
        state = STATE_NAME;
        }
      else if (state == STATE_NAME && c == '.') // TODO -- selectable sep
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          current = new StringBuffer();
          tokens.add (t);
          }
        current.append (c);
        state = STATE_NUMBER;
        }
      else if (state == STATE_NAME && (c == ' ' || c == '\t' || c == '\n'))
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          current = new StringBuffer();
          tokens.add (t);
          }
        state = STATE_WHITE;
        }
      else if (state == STATE_NAME && isOpChar (c))
        {
        if (current.length() > 0)
          {
          Token t = makeToken (filename, row, col, 
            new String (current), state); 
          tokens.add (t);
          }
        current = new StringBuffer();
        current.append (c);
        state = STATE_OPERATOR;
        }
      else if (state == STATE_NAME && isNameChar (c))
        {
        current.append (c);
        state = STATE_NAME;
        }


      else
        {
        throw new ParseException ("Unexpected character", "" + c,
            filename, row, col);
        }

      }
    if (current.length() > 0)
      {
      Token t = makeToken (filename, row, col, new String (current), state); 
      tokens.add (t);
      }



    return tokens;
    }

  }





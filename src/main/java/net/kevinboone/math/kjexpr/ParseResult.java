package net.kevinboone.math.kjexpr;
import java.util.*;


public class ParseResult
  {
  ParseTree pt;
  int count; 
  int pos;
  String error;

  public ParseResult (ParseTree pt, int count, int pos, String error)
    {
    this.pt = pt;
    this.count = count;
    this.pos = pos;
    this.error = error;
    }

  public ParseTree getParseTree() { return pt; }
  public int getCount() { return count; }
  public int getPos() { return pos; }
  public String getError() { return error; }
  }


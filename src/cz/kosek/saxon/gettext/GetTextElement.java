package cz.kosek.saxon.gettext;

import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.SimpleExpression;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.style.Compilation;
import net.sf.saxon.style.ComponentDeclaration;
import net.sf.saxon.style.ExtensionInstruction;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.StringValue; 

public class GetTextElement extends ExtensionInstruction
{
  public void prepareAttributes()
  {
  }

  public void validate(ComponentDeclaration decl) throws XPathException
  {
    super.validate(decl);
    //checkWithinTemplate();
  }

  public boolean mayContainSequenceConstructor()
  {
    return true;
  }
  
  public Expression compile(Compilation exec, ComponentDeclaration decl) throws XPathException
  {
    Expression text = compileSequenceConstructor(exec, decl, false);    
    GetTextInstruction inst = new GetTextInstruction(text);
    return inst;
  }

  private static class GetTextInstruction extends SimpleExpression
  {
    public GetTextInstruction(Expression text)
    {
      Expression[] subs = { text };      
      setArguments(subs);
    }
    
    public int getImplementationMethod() 
    {
      return Expression.PROCESS_METHOD;
    }

    /*
    public int computeCardinality() 
    {
      return StaticProperty.ALLOWS_ONE;
    }
    */

    public String getExpressionType()
    {
      return "gettext:gettext";
    }
  
    public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException 
    {      
      String param = "";
      SequenceIterator iterator = arguments[0].iterate();
      Item i;
      while ((i = iterator.next()) != null)
      {
        param += i.getStringValue();
      }
      return new StringValue(Gettext.gettext(param));
    }
  }

  
}

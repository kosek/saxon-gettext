package cz.kosek.saxon.gettext;

import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.SimpleExpression;
import net.sf.saxon.expr.StaticProperty;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.om.Sequence;
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
    private Expression text;
    
    public GetTextInstruction(Expression text)
    {
      this.text = text;
      adoptChildExpression(text);
    }
    
    public int getImplementationMethod() 
    {
      return Expression.PROCESS_METHOD;
    }

    public int computeCardinality() 
    {
      return StaticProperty.ALLOWS_ONE;
    }

    public String getExpressionType()
    {
      return "gettext:gettext";
    }
  
    /*
    public Iterator iterateSubExpressions()
    {
      ArrayList list = new ArrayList(1);
      if (text != null)
      {
        list.add(text);
      }
      return list.iterator();
    }
    */

    public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException 
    {
      return new StringValue(Gettext.gettext(text.evaluateAsString(context).toString()));
    }
  }

  
}

package cz.kosek.saxon.gettext;

import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.SimpleExpression;
import net.sf.saxon.expr.StaticProperty;
import net.sf.saxon.expr.StringLiteral;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.expr.instruct.Executable;
import net.sf.saxon.om.AttributeInfo;
import net.sf.saxon.om.AttributeMap;
import net.sf.saxon.om.EmptyAtomicSequence;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.style.Compilation;
import net.sf.saxon.style.ComponentDeclaration;
import net.sf.saxon.style.ExtensionInstruction;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.EmptySequence;
import net.sf.saxon.value.StringValue;

public class InitElement extends ExtensionInstruction
{

  Expression locale;
  Expression domain;
  
  public void prepareAttributes()
  {
    AttributeMap atts = attributes();
    AttributeInfo localeAttr = atts.get("", "locale");
    if (localeAttr == null) 
    {
      locale = new StringLiteral(StringValue.EMPTY_STRING);
    } 
    else 
    {
      locale = makeAttributeValueTemplate(localeAttr.getValue(), localeAttr);
    }

    AttributeInfo domainAttr = atts.get("", "domain");
    if (domainAttr == null) 
    {
      reportAbsence("domain");
      domain = new StringLiteral(StringValue.EMPTY_STRING);
    }
    else
    {
      domain = makeAttributeValueTemplate(domainAttr.getValue(), domainAttr);
    }
  }

  public void validate(ComponentDeclaration decl) throws XPathException
  {
      super.validate(decl);
      locale = typeCheck("locale", locale);
      domain = typeCheck("domain", domain);
  }

  public Expression compile(Compilation exec, ComponentDeclaration decl) throws XPathException
  {
    return new InitInstruction(locale, domain);
  }

  private static class InitInstruction extends SimpleExpression
  {
    public static final int LOCALE = 0;
    public static final int DOMAIN = 1;

    public InitInstruction(Expression locale,
                           Expression domain) 
    {
      Expression[] subs = { locale, domain };      
      setArguments(subs);
    };

    public int getImplementationMethod() 
    {
      return Expression.PROCESS_METHOD;
    }

    public int computeCardinality() 
    {
      return StaticProperty.ALLOWS_ZERO;
    }

    public String getExpressionType()
    {
      return "gettext:init";
    }

    public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException 
    {
      Gettext.setLocale(arguments[LOCALE].head().getStringValue());
      Gettext.setDomain(arguments[DOMAIN].head().getStringValue());
      return new StringValue("");
    }
  }
}

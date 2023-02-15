package cz.kosek.saxon.gettext;

import java.util.*;
import java.text.*;
import gnu.gettext.*;

import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.trans.XPathException;

public class GettextFunction extends ExtensionFunctionDefinition {

    @Override
    public StructuredQName getFunctionQName() {
        return new StructuredQName("t", "http://kosek.cz/cz.kosek.saxon.gettext.Gettext", "gettext");
    }

    @Override
    public SequenceType[] getArgumentTypes() {
        return new SequenceType[]{SequenceType.SINGLE_STRING};
    }

    @Override
    public SequenceType getResultType(SequenceType[] suppliedArgumentTypes) {
        return SequenceType.SINGLE_STRING;
    }
    
    @Override
    public ExtensionFunctionCall makeCallExpression() {
        return new ExtensionFunctionCall() {
  
          public Sequence call(XPathContext context, Sequence[] arguments) 
                            throws XPathException {
                return new StringValue(Gettext.gettext(arguments[0].head().getStringValue()));
            }
        };
    }
  }
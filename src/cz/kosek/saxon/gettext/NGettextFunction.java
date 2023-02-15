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

public class NGettextFunction extends ExtensionFunctionDefinition {

    @Override
    public StructuredQName getFunctionQName() {
        return new StructuredQName("t", "http://kosek.cz/cz.kosek.saxon.gettext.Gettext", "ngettext");
    }

    @Override
    public SequenceType[] getArgumentTypes() {
        return new SequenceType[]{SequenceType.SINGLE_STRING, SequenceType.SINGLE_STRING, SequenceType.SINGLE_INTEGER};
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
                return new StringValue(Gettext.ngettext(
                                        arguments[0].head().getStringValue(),
                                        arguments[1].head().getStringValue(),
                                        Long.parseLong(arguments[2].head().getStringValue())
                                       ));
            }
        };
    }
  }
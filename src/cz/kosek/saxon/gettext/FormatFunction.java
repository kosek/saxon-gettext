package cz.kosek.saxon.gettext;

import java.util.*;
import java.text.*;
import gnu.gettext.*;

import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.trans.XPathException;

public class FormatFunction extends ExtensionFunctionDefinition {

    @Override
    public StructuredQName getFunctionQName() {
        return new StructuredQName("t", "http://kosek.cz/cz.kosek.saxon.gettext.Gettext", "format");
    }

    @Override
    public SequenceType[] getArgumentTypes() {
        return new SequenceType[]{SequenceType.SINGLE_STRING, SequenceType.STRING_SEQUENCE};
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
                ArrayList<String> params = new ArrayList<String>();
                SequenceIterator iterator = arguments[1].iterate();
                Item i;
                while ((i = iterator.next()) != null)
                {
                  params.add(i.getStringValue());
                }
                return new StringValue(Gettext.format(arguments[0].head().getStringValue(),
                                                      (String [])params.toArray()));
            }
        };
    }
  }
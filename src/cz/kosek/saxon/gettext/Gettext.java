package cz.kosek.saxon.gettext;

import java.util.*;
import java.text.*;
import gnu.gettext.*;

import com.saxonica.xsltextn.ExtensionElementFactory;
import net.sf.saxon.style.StyleElement;

public class Gettext implements ExtensionElementFactory
{
  private static Gettext gettext = new Gettext();
  private static ResourceBundle catalog = null;
  private static String locale = null;
  private static String domain = null;
  
  /*
  protected Gettext()
  { 
    super(); 
  }
   */
  
  public Class getExtensionClass(String localname)
  {
    if (localname.equals("init")) return InitElement.class;
    if (localname.equals("gettext")) return GetTextElement.class;
    if (localname.equals("_")) return GetTextElement.class;
    return null;
  }
  
  protected static void setLocale(String lang)
  {
    locale = lang;
    if (domain != null)
      catalog = ResourceBundle.getBundle(domain, new Locale(locale));
  }
  
  protected static void setDomain(String _domain)
  {
    domain = _domain;
    if (locale != null)
      catalog = ResourceBundle.getBundle(domain, new Locale(locale));
    else
      catalog = ResourceBundle.getBundle(domain);
  }
  
  public static String gettext(String s)
  {
    if (catalog != null)
      return GettextResource.gettext(catalog, s);
    else
      return s;
  }
  
  public static String _(String s)
  {
    return gettext(s);
  }
  
  public static String ngettext(String s, String p, long n)
  {
    if (catalog != null)
      return GettextResource.ngettext(catalog, s, p, n);
    else
      return s;
  }
  
  public static String format(String fmt, String[] params)
  {
    return MessageFormat.format(fmt, (Object[])params);
  }
}

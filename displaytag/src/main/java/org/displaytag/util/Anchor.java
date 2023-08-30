/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.util;

import org.displaytag.tags.TableTagParameters;

/**
 * Anchor object used to output an html link (an &lt;a> tag).
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
@SuppressWarnings("unchecked")
public class Anchor
{

    /**
     * Href object to be written in the "href" html attribute.
     */
    private Href href;

    /**
     * link body text.
     */
    private String linkText;

    /**
     * HashMap containing all the html attributes.
     */
    private HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * Creates a new Anchor whit the supplied Href and body text.
     * @param linkHref baseHref
     * @param linkBody String link body
     */
    public Anchor(Href linkHref, String linkBody)
    {
        this.href = linkHref;
        this.linkText = linkBody;
    }

    /**
     * setter the anchor Href.
     * @param linkHref Href
     */
    public void setHref(Href linkHref)
    {
        this.href = linkHref;
    }

    /**
     * setter for the link body text.
     * @param linkBody String
     */
    public void setText(String linkBody)
    {
        this.linkText = linkBody;
    }

    /**
     * add a "class" attribute to the html link.
     * @param cssClass String
     */
    public void setClass(String cssClass)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, cssClass);
    }

    /**
     * add a "style" attribute to the html link.
     * @param style String
     */
    public void setStyle(String style)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, style);
    }

    /**
     * add a "title" attribute to the html link.
     * @param title String
     */
    public void setTitle(String title)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_TITLE, title);
    }

    /**
     * returns the href attribute, surrounded by quotes and prefixed with " href=".
     * @return String <code> href ="<em>href value</em>"</code> or an emty String if Href is null
     */
    private String getHrefString()
    {
        if (this.href == null)
        {
            return TagConstants.EMPTY_STRING;
        }
        return " href=\"" + this.href.toString() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Returns the &lt;a> tag, with rendered href and any html attribute.
     * @return String
     */
    public String getOpenTag()
    {
   	 /**** WebJET ****/
       if (this.href!=null && this.href.toString().indexOf(TableTagParameters.PARAMETER_EXPORTING+"=1")!=-1)
       {
     	  this.attributeMap.put("onclick", "window.open(this.href); return false;");
     	  this.attributeMap.put("onkeypress", "window.open(this.href); return false;");
       }

        // shortcut for links with no attributes
        if (this.attributeMap.size() == 0)
        {
            return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ANCHOR + getHrefString() + TagConstants.TAG_CLOSE;
        }

        // append all attributes
        StringBuffer buffer = new StringBuffer();

        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_ANCHOR).append(getHrefString());

        buffer.append(this.attributeMap);

        buffer.append(TagConstants.TAG_CLOSE);

        //WebJET odstranenie jsessionid
        int startIndex = buffer.indexOf(";jsessionid");
        if(startIndex != -1)
        {
      	  int endIndex = buffer.indexOf("?", startIndex);
      	  if(endIndex == -1) endIndex = buffer.length();
      	  buffer.replace(startIndex, endIndex, "");
        }

        return buffer.toString();
    }

    /**
     * returns the &lt;/a> tag.
     * @return String
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_ANCHOR + TagConstants.TAG_CLOSE;
    }

    /**
     * returns the full &lt;a href="">body&lt;/a>.
     * @return String html link
     */
    @Override
    public String toString()
    {
        return getOpenTag() + this.linkText + getCloseTag();
    }

}
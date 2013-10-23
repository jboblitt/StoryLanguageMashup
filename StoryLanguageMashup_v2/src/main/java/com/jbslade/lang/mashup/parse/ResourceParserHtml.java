package com.jbslade.lang.mashup.parse;

import com.jbslade.lang.mashup.forms.Story;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Justin on 10/11/13. Notice that the same generic in ResourceParserXml is used in
 * declaring the objects in XmlContentHandler.  This is because the ResourceParserXml calls on
 * XmlContentHandler to return the parsed elements.  The objects must be the same.
 */
public class ResourceParserHtml extends ResourceParser<Story>
{
    private static final Pattern HTML_TAG_PARAGRAPH_PATTERN = Pattern.compile("<p.*?>");
    private static final Pattern HTML_CLOSING_TAG_PARAGRAPH_PATTERN = Pattern.compile("</p.*?>");
    private static final String HTML_REMOVE_TAGS[] = {"a"};
    //    private static final String HTML_REMOVE_TAG_BODIES[] = {"i", "script", "strong", "br"};
    private static final String HTML_END_OF_DOC_KEYS[] =
            {"Post to other social networks", "Auf anderen Social Networks teilen",
                    "Stay informed with our free news services",
                    "Lassen Sie sich mit kostenlosen Diensten auf dem Laufenden halten",
                    "Reproduction only allowed with the permission of SPIEGELnet GmbH",
                    "Vervielfältigung nur mit Genehmigung der SPIEGELnet GmbH"
            };
    private boolean m_inParagraph = false;
    private String m_fullStory;

    public ResourceParserHtml()
    {
    }

    @Override
    public ArrayList<Story> getResourceParsedElems(InputStream is)
    {
        ArrayList<Story> elemsParsed = new ArrayList<Story>();
        m_fullStory = "";

        String line;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
            while ((line = reader.readLine()) != null)
            {
                processLine(line);
            }
            m_fullStory = postProcessRemoveTags(m_fullStory, HTML_REMOVE_TAGS);
            m_fullStory = postProcessHtmlFormat(m_fullStory);
            m_fullStory = postProcessRemoveTail(m_fullStory, HTML_END_OF_DOC_KEYS);
            m_fullStory = postProcessRemoveTagBodies(m_fullStory/*, HTML_REMOVE_TAG_BODIES*/);

            Story story = new Story();
            story.addForeignLangSentenceArray(m_fullStory.split(Story.DELIMITER));
            elemsParsed.add(story);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return elemsParsed;
    }

    /**
     * Filter out 95% of the html by only extracting paragraph tags.
     * Note that this recursive method steps through the document line by line one time.
     * I think this is better than having an entire html document read in as a String.
     *
     * @param line
     */
    private void processLine(String line)
    {
        if (!m_inParagraph)
        {
            //Looking for paragraph tags
            Matcher m = HTML_TAG_PARAGRAPH_PATTERN.matcher(line);
            if (m.find())
            {
                //Process line from point where we found opening paragraph tags
                m_inParagraph = true;
                processLine(line.substring(m.end()));
            }
        }
        else
        {
            //Inside paragraph tags, looking for closing paragraph tag.
            Matcher m = HTML_CLOSING_TAG_PARAGRAPH_PATTERN.matcher(line);
            if (m.find())
            {
                //Store up to end of paragraph.  Begin search again after closing tag (Just in case multi-paragraphs on same line)
                m_fullStory += (line.substring(0, m.start()).trim() + "\t");

                //False because we processed closing tag and are now outside paragraph tags
                m_inParagraph = false;
                processLine(line.substring(m.end()));
            }
            else
            {
                //In this case we are in the paragraph tags and haven't found the end yet.  Store all.
                m_fullStory += line.trim();
            }
        }
    }

    /**
     * @param fullStory
     * @param htmlRemoveTags
     * @return m_fullStory without any tags that were specified inside of htmlRemoveTags.
     * Keep in mind that the text between the two tags will remain.
     */
    private String postProcessRemoveTags(String fullStory, String[] htmlRemoveTags)
    {
        for (String tag : htmlRemoveTags)
        {
            fullStory = fullStory.replaceAll("<" + tag + ".*?>", "");
            fullStory = fullStory.replaceAll("</" + tag + ".*?>", "");
        }
        return fullStory;
    }

    /**
     * @param fullStory
     * @return m_fullStory without any tags or their associated bodies
     * that were specified inside of htmlRemoveTags.
     */
    private String postProcessRemoveTagBodies(String fullStory/*, String[] htmlRemoveTagBodies*/)
    {
        return fullStory.replaceAll("<.*?>.*?</.*?>", "");
    }

    private String postProcessHtmlFormat(String fullStory)
    {
        return fullStory.replaceAll("<b.*?>(.*?)</b.*?>", "\n$1\n\n");
    }

    private String postProcessRemoveTail(String fullStory, String[] htmlEndOfDocKeys)
    {
        for (String endDocKey : htmlEndOfDocKeys)
        {
            int ind = fullStory.indexOf(endDocKey);
            if (ind > -1)
            {
                //Found a key indicating the end of the document.  Shave end of document.
                fullStory = fullStory.substring(0, ind);
                break;
            }
        }
        return fullStory;
    }

}

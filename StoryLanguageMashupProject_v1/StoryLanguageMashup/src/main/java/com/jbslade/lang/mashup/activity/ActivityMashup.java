package com.jbslade.lang.mashup.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.jbslade.lang.mashup.R;
import com.jbslade.lang.mashup.forms.Story;
import com.jbslade.lang.mashup.forms.SpiegelRssElement;
import com.jbslade.lang.mashup.debug.MyLogger;
import com.jbslade.lang.mashup.parse.ResourceParserHtml;
import com.jbslade.lang.mashup.webresource.ControllerSingleResourceType;

import java.util.ArrayList;

/**
 * Created by Justin on 10/9/13.
 */
public class ActivityMashup extends Activity
{
    public static final String KEY_PASS_TARGET_RSS_STORY = "key_pass_target_rss_story";

    public static final short MSG_TYPE_STORY = 1;
    public static final short MSG_TYPE_TRANSLATED_ARRAY = 2;
    public static final String KEY_STORY = "key_story";
    public static final String KEY_TRANSLATED_ARRAY = "key_translated_array";


    private SpiegelRssElement m_targetRssStory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mashup);

        Bundle intentData = getIntent().getExtras();
        if (intentData != null)
        {
            m_targetRssStory =
                    (SpiegelRssElement) intentData.getSerializable(
                            KEY_PASS_TARGET_RSS_STORY);
            MyLogger.logInfo(ActivityMashup.class.getName(), "onCreate",
                    "intent bundle was non-null");
        }
        ((TextView) this.findViewById(R.id.TextView_Mashup_Title))
                .setText(m_targetRssStory.getTitle());
        ((TextView) this.findViewById(R.id.TextView_Mashup_PubDate))
                .setText(m_targetRssStory.getPubDate());
        ((TextView) this.findViewById(R.id.TextView_Mashup_Story))
                .setText(m_targetRssStory.getDescription());

        String storyUrl = m_targetRssStory.getResourceUrl();

        if (storyUrl != null && storyUrl.startsWith("http://"))
        {
            //Set up rss URLs to fetch and parse by single parser
            String[] urlsToFetch = {storyUrl};

            //Set up content/error handler that will be used during parsing of the rss resource.
            ResourceParserHtml spiegelHtmlParser = new ResourceParserHtml();

            ControllerSingleResourceType<Story> spiegelHtmlController =
                    new ControllerSingleResourceType<Story>(spiegelHtmlParser, UI_Handler,
                            ActivityNewsHeadlines.KEY_MSG_TYPE, MSG_TYPE_STORY, KEY_STORY,
                            urlsToFetch);

            spiegelHtmlController.fetchAndParseResources();
        }
    }

    private Handler UI_Handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Bundle msgBundle = msg.getData();

            switch (msgBundle.getInt(ActivityNewsHeadlines.KEY_MSG_TYPE))
            {
                case MSG_TYPE_STORY:
                    ArrayList<Story> stories =
                            (ArrayList<Story>) msgBundle.getSerializable(KEY_STORY);
                    if (stories != null)
                    {
                        ((TextView) ActivityMashup.this.findViewById(R.id.TextView_Mashup_Story))
                                .setText(stories.get(0).getForeignText());
                    }
                    else
                    {
                        Toast.makeText(ActivityMashup.this,
                                "Received story empty msg",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case MSG_TYPE_TRANSLATED_ARRAY:
                    ArrayList<String> translatedToNative =
                            (ArrayList<String>) msgBundle.getSerializable(KEY_TRANSLATED_ARRAY);
                    if (translatedToNative != null)
                    {
                        //Story now has both foreign and native translations.  Allow user interaction
                    }
                    else
                    {
                        Toast.makeText(ActivityMashup.this,
                                "Received story empty msg",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    MyLogger.logWarning(ActivityMashup.class.getName(), "UI_Handler handleMessage",
                            "ActivityMashup Handler received non-supported message type");
                    break;
            }
        }
    };
}

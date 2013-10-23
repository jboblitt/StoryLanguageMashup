package com.jbslade.lang.mashup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jbslade.lang.mashup.R;
import com.jbslade.lang.mashup.adapter.AdapterControllerNewsHeadlines;
import com.jbslade.lang.mashup.parse.ResourceParserXml;
import com.jbslade.lang.mashup.parse.XmlContentHandlerSpiegelRss;
import com.jbslade.lang.mashup.webresource.ControllerSingleResourceType;
import com.jbslade.lang.mashup.forms.SpiegelRssElement;
import com.jbslade.lang.mashup.adapter.ListBasicAdapter;
import com.jbslade.lang.mashup.debug.MyLogger;
import com.jbslade.lang.mashup.parse.XmlErrorHandler;

import java.util.ArrayList;

public class ActivityNewsHeadlines extends Activity
{
    public static final boolean DEBUG_FLAG = true;

    public static final String KEY_MSG_TYPE = "KEY_MSG_TYPE";
    public static final short MSG_TYPE_SPIEGEL_RSS_ELEMS = 1;

    public static final String KEY_SPIEGEL_RSS_ELEMS = "KEY_SPIEGEL_RSS_ELEMS";


    private ListBasicAdapter<SpiegelRssElement> m_listViewBasicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_headlines);

        MyLogger.initLogger("ApplicationLogger", DEBUG_FLAG);

        //Set up rss URLs to fetch and parse by single parser
        String[] spiegelUrl = {"http://www.spiegel.de/schlagzeilen/tops/index.rss",
                "http://www.spiegel.de/schlagzeilen/eilmeldungen/index.rss",
                "http://www.spiegel.de/international/index.rss"};

        //Set up content/error handler that will be used during parsing of the rss resource.

        ResourceParserXml<SpiegelRssElement> resourceParserXml =
                new ResourceParserXml<SpiegelRssElement>(new XmlContentHandlerSpiegelRss(),
                        new XmlErrorHandler());

        ControllerSingleResourceType<SpiegelRssElement> spiegelHtmlController =
                new ControllerSingleResourceType<SpiegelRssElement>(resourceParserXml, UI_Handler,
                        KEY_MSG_TYPE, MSG_TYPE_SPIEGEL_RSS_ELEMS,
                        KEY_SPIEGEL_RSS_ELEMS, spiegelUrl);

        spiegelHtmlController.fetchAndParseResources();

        ListView lv = (ListView) this.findViewById(R.id.ListView_ActivityNewsHeadlines);
        m_listViewBasicAdapter = new ListBasicAdapter<SpiegelRssElement>();
        m_listViewBasicAdapter.setAdapterController(new AdapterControllerNewsHeadlines());
        lv.setAdapter(m_listViewBasicAdapter);
        lv.setOnItemClickListener(new ListView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                SpiegelRssElement elemClicked =
                        (SpiegelRssElement) m_listViewBasicAdapter.getItem(position);
                Intent intent = new Intent(ActivityNewsHeadlines.this, ActivityMashup.class);
                intent.putExtra(ActivityMashup.KEY_PASS_TARGET_RSS_STORY, elemClicked);
                ActivityNewsHeadlines.this.startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_news_headlines, menu);
        return true;
    }

    private Handler UI_Handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Bundle msgBundle = msg.getData();
            switch (msgBundle.getInt(KEY_MSG_TYPE))
            {
                case MSG_TYPE_SPIEGEL_RSS_ELEMS:
                    ArrayList<SpiegelRssElement> rssElems =
                            (ArrayList<SpiegelRssElement>) msgBundle
                                    .getSerializable(KEY_SPIEGEL_RSS_ELEMS);
                    if (rssElems != null && rssElems.size() > 0)
                    {
                        m_listViewBasicAdapter.addElemsToList(rssElems);
                    }
                    else
                    {
                        Toast.makeText(ActivityNewsHeadlines.this,
                                "Received spiegel rss elems empty msg",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    MyLogger.logWarning(ActivityNewsHeadlines.class.getName(),
                            "UI_Handler handleMessage",
                            "ActivityNewsHeadlines Handler received non-supported message type");
                    break;
            }
        }
    };


}

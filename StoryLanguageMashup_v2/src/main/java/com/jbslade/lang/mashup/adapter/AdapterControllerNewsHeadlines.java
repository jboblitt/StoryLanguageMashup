package com.jbslade.lang.mashup.adapter;

import android.widget.ImageView;

import com.jbslade.lang.mashup.forms.SpiegelRssElement;
import com.jbslade.lang.mashup.interfaces.ListBasicAdapterControllerInterface;

import java.util.ArrayList;

/**
 * Created by Justin on 10/14/13.
 */
public class AdapterControllerNewsHeadlines
        implements ListBasicAdapterControllerInterface<SpiegelRssElement>
{
    private ArrayList<SpiegelRssElement> m_listElems;

    public AdapterControllerNewsHeadlines()
    {
        m_listElems = new ArrayList<SpiegelRssElement>();
    }

    public boolean addElemsToList(ArrayList<SpiegelRssElement> listElems)
    {
        return m_listElems.addAll(listElems);
    }

    @Override
    public int getCount()
    {
        return m_listElems.size();
    }

    @Override
    public SpiegelRssElement getItem(int position)
    {
        return m_listElems.get(position);
    }

    @Override
    public void populateIcon(ImageView icon)
    {
        //Find icon and set to imageview
    }

    @Override
    public String getHeader1Text(int pos)
    {
        return getItem(pos).getTitle();
    }

    @Override
    public String getHeader2aText(int pos)
    {
        return getItem(pos).getPubDate();
    }

    @Override
    public String getHeader2bText(int pos)
    {
        return getItem(pos).getDescription();
    }

}

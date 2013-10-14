package com.jbslade.lang.mashup;

import com.jbslade.lang.mashup.forms.Story;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Justin on 10/12/13.
 */
public class StoryController
{
    private ArrayList<Integer> m_foreignIndicesShown;
    private ArrayList<Integer> m_nativeIndicesShown;

    private Story m_story;
    private int m_currPercentOfForeign;

    public StoryController(Story story)
    {
        m_story = story;
        m_foreignIndicesShown = new ArrayList<Integer>();
        m_nativeIndicesShown = new ArrayList<Integer>();
    }

    public int getTotalSize()
    {
        return this.m_foreignIndicesShown.size() + this.m_nativeIndicesShown.size();
    }

    public String getTextForSentenceIndex(int ind)
    {
        //Check in the pull of foreign languages for this index.
        if (m_foreignIndicesShown.contains(ind))
        {
            //Should return the foreign language at this index
            return m_story.getForeignSentenceAtIndex(ind);
        }
        else
        {
            //Should return the native language at this index
            return m_story.getNativeSentenceAtIndex(ind);
        }
    }

    public void requestPercentOfForeign(int requestedPercentageOfForeign, int maxValue)
    {
        int numElemsToMove =
                (requestedPercentageOfForeign - m_currPercentOfForeign) * getTotalSize() / maxValue;

        if (numElemsToMove > 0)
        {
            //Should move elements from native to foreign pool
            transferRandomN(m_nativeIndicesShown, m_foreignIndicesShown, numElemsToMove);
        }
        else
        {
            //Should move from foreign to native pool.
            transferRandomN(m_foreignIndicesShown, m_nativeIndicesShown, numElemsToMove);
        }

        m_currPercentOfForeign = requestedPercentageOfForeign;
    }

    private void transferRandomN(ArrayList<Integer> listSource,
                                 ArrayList<Integer> listDest, int numElemsToMove)
    {
        if (listSource.size() == numElemsToMove)
        {
            listDest.addAll(listSource);
            listSource.clear();
        }
        else
        {
            //Generate random indices to move from source to dest
            Boolean[] indicesTaken = new Boolean[listSource.size()];
            Arrays.fill(indicesTaken, Boolean.FALSE);
            for (int i = 0; i < numElemsToMove; i++)
            {
                //Math.random returns value between [0,1)
                int randIndex;
                while (indicesTaken[randIndex = (int) Math.random() * listSource.size()])
                {
                }
                listDest.add(listSource.remove(randIndex));
            }
        }
    }


}

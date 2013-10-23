package com.jbslade.lang.mashup.translate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by Justin on 10/12/13.
 */
public class AsyncTaskTranslate extends AsyncTask<String, Void, Void>
{
    private final String m_nativLang;
    private final String m_foreignLang;
    private final String m_keyMsgType;
    private final short m_msgType;
    private final String m_keyPayload;
    private final Handler m_handlerReportTranslation;

    public AsyncTaskTranslate(String nativLang, String foreignLang,
                              Handler handlerReportTranslation,
                              String keyMsgType, short msgType, String keyPayload)
    {
        m_nativLang = nativLang;
        m_foreignLang = foreignLang;
        m_handlerReportTranslation = handlerReportTranslation;
        m_keyMsgType = keyMsgType;
        m_msgType = msgType;
        m_keyPayload = keyPayload;
    }


    @Override
    protected Void doInBackground(String... sourceArray)
    {
        //Perform translation
        ArrayList<String> m_destinationArray = new ArrayList<String>();

        int i = 0;
        for (String translate : sourceArray)
        {
            m_destinationArray.add("Native language (" + i++ + ")");
        }

        sendParsedElemsToHandler(m_destinationArray);
        return null;
    }

    private void sendParsedElemsToHandler(ArrayList<String> payload)
    {
        Bundle bundle = new Bundle();
        if (m_keyMsgType != null)
        {
            bundle.putInt(m_keyMsgType, m_msgType);
        }
        if (m_keyPayload != null && payload != null)
        {
            bundle.putSerializable(m_keyPayload, payload);
        }
        Message msg = new Message();
        msg.setData(bundle);
        m_handlerReportTranslation.sendMessage(msg);
    }
}

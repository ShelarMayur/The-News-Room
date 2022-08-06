package com.mshel;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class XmlParser {
    private static final String TAG = "XmlParser";
    private ArrayList<FeedData> news;

    public XmlParser() {
        this.news = new ArrayList<>();
    }

    public ArrayList<FeedData> getNews() {
        return news;
    }

    public boolean parse(String url){
        FeedData currentRecord = new FeedData();
        String textValue = "";
        boolean status = true;
        boolean inItem = true;
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(url));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        if("item".equalsIgnoreCase(tagName)){
                            currentRecord = new FeedData();
                            inItem = true;
                        }break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inItem){
                        if("item".equalsIgnoreCase(tagName)){
                            news.add(currentRecord);
                            inItem = false;
                        }else if("title".equalsIgnoreCase(tagName)){
                            currentRecord.setTitle(textValue);
                        }else if("description".equalsIgnoreCase(tagName))
                            currentRecord.setDescription(textValue);
                        else if("link".equalsIgnoreCase(tagName))
                            currentRecord.setLink(textValue);
                        else if ("category".equalsIgnoreCase(tagName))
                            currentRecord.setCategories(textValue);
                    }break;
                }
                eventType = xpp.next();
            }
            for(FeedData n1 : news){
                Log.e(TAG, "*************************" );
                Log.d(TAG, n1.getTitle());
                Log.d(TAG, n1.getDescription());
                Log.d(TAG, n1.getCategories());
                Log.d(TAG, n1.getLink());
            }
        }catch(Exception e){
            Log.e(TAG, "parse: Error parsing the xml"+e.getMessage());
            status = false;
        }return status;
    }
}

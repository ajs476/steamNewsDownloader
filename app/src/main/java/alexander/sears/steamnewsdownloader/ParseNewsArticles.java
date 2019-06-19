package alexander.sears.steamnewsdownloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseNewsArticles {
    private static final String TAG = "ParseNewsArticles";
    private ArrayList<FeedEntry> newsArticles;

    public ParseNewsArticles() {
        this.newsArticles = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getNewsArticles() {
        return newsArticles;
    }

    public boolean parse(String xmlData){
        boolean status = true;
        FeedEntry currentRecord = null;
        boolean inItem = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory =  XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        //Log.d(TAG, "parse: starting tag for "+tagName);
                        if("item".equalsIgnoreCase(tagName)){
                            inItem = true;
                            currentRecord = new FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        //Log.d(TAG, "parse: ending tag for: "+tagName);
                        if(inItem){
                            if("item".equalsIgnoreCase(tagName)){
                                newsArticles.add(currentRecord);
                                inItem = false;
                            }else if("title".equalsIgnoreCase(tagName)){
                                currentRecord.setTitle(textValue);
                            }else if("content:encoded".equalsIgnoreCase(tagName)){
                                currentRecord.setContent(textValue);
                            }else if("link".equalsIgnoreCase(tagName)){
                                currentRecord.setLink(textValue);
                            }
                        }
                        break;
                }
                eventType = xpp.next();
                for(FeedEntry news:newsArticles){
                    Log.d(TAG, "**************************");
                    Log.d(TAG, news.toString());
                }
            }
        } catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}

package com.mshel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String url = "https://www.nasdaq.com/feed/rssoutbound?%s";
    String category = "category=Commodities";
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.xmlData);
        downloadUrl(String.format(url,category));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.comodities:
                category = "category=Commodities";
                break;
            case R.id.cryptocurrencies:
                category = "category=Cryptocurrencies";
                break;
            case R.id.dividends:
                category = "category=Dividends";
                break;
            case R.id.earnings:
                category = "category=Earnings";
                break;
            case R.id.ETFs:
                category = "category=ETFs";
                break;
            case R.id.iPOs:
                category = "category=IPOs";
                break;
            case R.id.markets:
                category = "category=Markets";
                break;
            case R.id.options:
                category = "category=Options";
                break;
            case R.id.stocks:
                category = "category=stocks";
                break;
            case R.id.aapl:
                category = "symbol=aapl";
                break;
            case R.id.amzn:
                category = "symbol=AMZN";
                break;
            case R.id.fb:
                category = "symbol=FB";
                break;
            case R.id.tsla:
                category = "symbol=TSLA";
                break;
            case R.id.amd:
                category = "symbol=AMD";
                break;
            case R.id.nvda:
                category = "symbol=NVDA";
                break;
            case R.id.msft:
                category = "symbol=MFST";
                break;
            case R.id.nflx:
                category = "symbol=NFLX";
                break;
            case R.id.baba:
                category = "symbol=BABA";
                break;
            default:
                category = "category=Commodities";
                break;
        }
        downloadUrl(String.format(url,category));
        return true;
    }

    public void downloadUrl(String url){
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler myHandler = new Handler(Looper.getMainLooper());
        try {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    DownloadData downloadData = new DownloadData();
                    String s = downloadData.downloadXml(url);
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(s==null)
                                Log.e(TAG, "run: String is null" );
                            else {
                                XmlParser xmlParser = new XmlParser();
                                xmlParser.parse(s);
                                FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this,R.layout.list_records,xmlParser.getNews());
                                listView.setAdapter(feedAdapter);
                            }
                        }
                    });
                }
            });
        }catch(Exception e){
            Log.e(TAG, "onCreate: "+e.getMessage());
        }
    }
}
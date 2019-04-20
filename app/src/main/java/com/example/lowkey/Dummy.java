package com.example.lowkey;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scraper.CraigslistScraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Dummy extends AppCompatActivity {

    LinearLayout itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        itemList = (LinearLayout) findViewById(R.id.itemList);
        Context context = this.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        final String query = sharedPreferences.getString("query", "laptop");
        TextView qView = new TextView(context);
        qView.setText("Searching for: " + query);
        itemList.addView(qView);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                populateItems(query);
            }
        });
        sendNotifications("Hello!!");
    }

    private void populateItems(String query){
        ArrayList<CraigslistScraper.SaleItem>  items = CraigslistScraper.query_craigslist(query);
        Context context = this.getApplicationContext();

        for(int i = 0; i< items.size(); ++i)
        {
            final View itemView = generateProductView(items.get(i));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    itemList.addView(itemView);
                }
            });

        }

    }

    private View generateProductView(CraigslistScraper.SaleItem item){
        Context context = this.getApplicationContext();
        LinearLayout horizontal = new LinearLayout(context);
        horizontal.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView = new ImageView(context);
        if(item.getImgUrl() != null){
        try {

            imageView.setImageBitmap(getImage(item.getImgUrl()));
        } catch (IOException e) {
            System.out.println("Failed to get image");
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
//            e.printStackTrace();
        }
        }
        else{
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }
        horizontal.addView(imageView);
        LinearLayout vertical = new LinearLayout(context);
        vertical.setOrientation(LinearLayout.VERTICAL);
        horizontal.addView(vertical);
        TextView price = new TextView(context);
        price.setText(item.getCost());
        vertical.addView(price);
        TextView description = new TextView(context);
        description.setText(item.getName());
        vertical.addView(description);
        return horizontal;
    }

    Bitmap getImage(String img_url) throws IOException {
        URL url=new URL(img_url);
        Bitmap bmp =  BitmapFactory.decodeStream(url.openConnection().getInputStream());
        return bmp;
    }

    private NotificationManager manager;

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ("channel_name");
            String description = ("For lowkey notifications");
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("M_CH_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotifications(String text)
    {
        //create intent for the notifications
        Intent notificationsIntent = new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //Pending intent
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationsIntent,flags);

        //create var for notif
        int icon = R.drawable.ic_launcher_foreground;
        CharSequence tickerText = "Countdown";
        CharSequence contentTitle = getText(R.string.app_name);
        CharSequence contentText = text;


        createNotificationChannel();
        //create notification and set data
        Notification notification = new NotificationCompat.Builder(this, "main")
                .setSmallIcon(icon)
                .setTicker(tickerText)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        //display
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1, notification);

    }
}

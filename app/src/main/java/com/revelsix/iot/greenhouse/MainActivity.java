package com.revelsix.iot.greenhouse;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATION_ID=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout and toolbar
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void showNotification(View view){
        //Construccion de la accion del intent implicito
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/index.html"));
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        //Construccion de la notificacion;
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icono);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.chip25));
        builder.setContentTitle("Notificacion Basica");
        builder.setContentText("Momento para aprender mas sobre Android!");
        builder.setSubText("Toca para ver la documentacion acerca de Anndroid.");
        //Enviar la notificacion
        NotificationManager notificationManager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}





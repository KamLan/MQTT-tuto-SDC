package com.example.kam.mqttandroidtest;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity  extends AppCompatActivity implements MqttCallback {

    String topic = "Alo";
    public TextView publishedMessage;
    ArrayList<ReceivedMessage> messages = new ArrayList<>();
    int qos = 1;
    MyRecyclerAdapter myRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        publishedMessage = (TextView) findViewById(R.id.publishedMessageTv);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecyclerAdapter = new MyRecyclerAdapter(messages);
        recyclerView.setAdapter(myRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://172.17.0.3", clientId);
        client.setCallback(this);

        try{
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("user");
            options.setPassword("root".toCharArray());
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //we are connected
                    Log.d("check_connectivity", "onSuccess");

                    try{
                        IMqttToken subToken = client.subscribe(topic, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                //message published
                                publishedMessage.setText(" Some Published Messages");
                                MqttMessage message =  new MqttMessage();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                //error
                                publishedMessage.setText(" No Published Messages");
                            }
                        });
                    }catch(MqttException e ){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    //Something went wrong
                    Log.d("check_connectivity", "onFailure");
                }
            });
        }catch(MqttException e){
            e.printStackTrace();
        }


    }

    @Override
    public void connectionLost(Throwable cause){

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception{
        System.out.println(message);
        ReceivedMessage messageR = new ReceivedMessage(topic, message);
        messages.add(messageR);
        myRecyclerAdapter.notifyDataSetChanged();
        getNotified(messageR.Notification());
        Toast.makeText(this, messageR.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token){

    }

    public void getNotified(String messageNotif){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You have news !")
                .setContentText(messageNotif);

        Random random = new Random();
        int mNotificationId = random.nextInt(9999 - 1000) + 1000;
        NotificationManager mNotifM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifM.notify(mNotificationId, mBuilder.build());
    }

}

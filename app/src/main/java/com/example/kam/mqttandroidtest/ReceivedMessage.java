package com.example.kam.mqttandroidtest;

import android.widget.ArrayAdapter;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;

/**
 * Created by kam on 04/01/18.
 */

public class ReceivedMessage {

    public final String topic;
    public final MqttMessage message;
    public final Date timestamp;

    public ReceivedMessage(String _topic, MqttMessage _message){
        this.topic = _topic;
        this.message = _message;
        this.timestamp = new Date();
    }

    @Override
    public String toString(){
        return "Received Message {" +
                " topic = " + topic + '\'' +
                ", message " + message + '\'' +
                ", timestamp " + timestamp +
                "}";
    }

    public String Notification(){
        return "message: "+ message;
    }

    public String getTopic() {
        return topic;
    }

    public MqttMessage getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }



}

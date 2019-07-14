package com.nazycodes.tabiandating.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {

    private User user;
    private String message;

    public Message (User user, String message){
        this.user = user;
        this.message = message;
    }

    public Message() {

    }

    protected Message(Parcel in){
        user = in.readParcelable(User.class.getClassLoader());
        message = in.readString();
    }

    public final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel parcel) {
            return new Message(parcel);
        }

        @Override
        public Message[] newArray(int i) {
            return new Message[i];
        }
    };

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return "Message{" +
                "User=" + user +
                ", message='" + message + '\'' + '}';
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(user, i);
        parcel.writeString(message);
    }
}

package com.pa.ball.sports.quiz.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Map implements Parcelable {
    String map_key,map_link;

    public Map(String map_key, String map_link) {
        this.map_key = map_key;
        this.map_link = map_link;
    }

    protected Map(Parcel in) {
        map_key = in.readString();
        map_link = in.readString();
    }

    public static final Creator<Map> CREATOR = new Creator<Map>() {
        @Override
        public Map createFromParcel(Parcel in) {
            return new Map(in);
        }

        @Override
        public Map[] newArray(int size) {
            return new Map[size];
        }
    };

    public String getMap_key() {
        return map_key;
    }

    public void setMap_key(String map_key) {
        this.map_key = map_key;
    }

    public String getMap_link() {
        return map_link;
    }

    public void setMap_link(String map_link) {
        this.map_link = map_link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(map_key);
        dest.writeString(map_link);
    }
}

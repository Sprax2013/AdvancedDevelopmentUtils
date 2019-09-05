package de.sprax2013.advanced_dev_utils.mineskin;

import com.google.gson.annotations.SerializedName;

public class Skin {
    public int id;
    public String name;
    public SkinData data;
    public long timestamp;
    @SerializedName("private")
    public boolean prvate;
    public int views;
    public int accountId;

    public double nextRequest;
}
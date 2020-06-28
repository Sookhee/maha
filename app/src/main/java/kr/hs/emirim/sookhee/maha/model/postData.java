package kr.hs.emirim.sookhee.maha.model;

import java.util.ArrayList;

public class postData {
    private int hobbyId;
    private int likeCount;
    private int viewCount;
    private long time;
    private String date;
    private String title;
    private String content;
    private String hobbyName;
    private String writer;
    private String writerProfile;
    private ArrayList<String> img;
    private ArrayList<String> tagList;

    public postData() { }
    public postData(int hobbyId, int likeCount, int viewCount, long time, String date, String title, String content, String hobbyName, String writer, String writerProfile, ArrayList<String> img, ArrayList<String> tag){
        this.hobbyId = hobbyId;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.time = time;
        this.date = date;
        this.title = title;
        this.content = content;
        this.hobbyName = hobbyName;
        this.writer = writer;
        this.writerProfile = writerProfile;
        this.img = img;
        this.tagList = tag;
    }

    public int getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(int hobbyId) {
        this.hobbyId = hobbyId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriterProfile() {
        return writerProfile;
    }

    public void setWriterProfile(String writerProfile) {
        this.writerProfile = writerProfile;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public ArrayList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        this.tagList = tagList;
    }
}

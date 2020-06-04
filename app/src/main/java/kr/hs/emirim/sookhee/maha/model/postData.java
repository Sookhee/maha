package kr.hs.emirim.sookhee.maha.model;

import java.util.ArrayList;

public class postData {
    private int postId;
    private int hobbyId;
    private int likeCount;
    private int viewCount;
    private String title;
    private String hobbyName;
    private String writer;
    private ArrayList<String> img;

    public postData(){}
    public postData(int postId, int hobbyId, int likeCount, int viewCount, String title, String hobbyName, String writer, ArrayList<String> imgList){
        this.postId = postId;
        this.hobbyId = hobbyId;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.title = title;
        this.hobbyName = hobbyName;
        this.writer = writer;
        this.img = imgList;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setWrite(String writer) {
        this.writer = writer;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }
}

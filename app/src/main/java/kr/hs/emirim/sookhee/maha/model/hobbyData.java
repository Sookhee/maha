package kr.hs.emirim.sookhee.maha.model;

public class hobbyData {
    private int hobbyId;
    private String name;
    private String imgUrl;
    private String intro;
    private int memberCount;
    private int postCount;

    public hobbyData(){}
    public hobbyData(int hobbyId, String name, String imgUrl, String intro, int memberCount, int postCount){
        this.hobbyId = hobbyId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.intro = intro;
        this.memberCount = memberCount;
        this.postCount = postCount;
    }

    public int getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(int hobbyId) {
        this.hobbyId = hobbyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }
}

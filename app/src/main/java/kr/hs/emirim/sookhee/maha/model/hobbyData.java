package kr.hs.emirim.sookhee.maha.model;

public class hobbyData {
    private int hobbyId;
    private String name;
    private String imgUrl;
    private int memberCount;

    public hobbyData(){}
    public hobbyData(int hobbyId, String name, String imgUrl, int memberCount){
        this.hobbyId = hobbyId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.memberCount = memberCount;
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


}

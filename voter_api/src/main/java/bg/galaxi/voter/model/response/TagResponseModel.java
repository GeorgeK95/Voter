package bg.galaxi.voter.model.response;

public class TagResponseModel implements Comparable<TagResponseModel> {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(TagResponseModel o) {
        return this.content.compareTo(o.content);
    }
}

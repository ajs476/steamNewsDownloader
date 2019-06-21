package alexander.sears.steamnewsdownloader;

public class FeedEntry {

    private String title;
    private String image;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String content) {
        this.image = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "\n title = " + title + '\n' +
                "\n image = " + image + '\n' +
                "\n link = " + link + '\n';
    }
}

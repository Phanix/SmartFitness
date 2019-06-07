package project.com.smartfitness;

public class Quote {
    private String author;
    private String text;

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Quote(String author, String text) {
        this.author = author;
        this.text = text;
    }
}

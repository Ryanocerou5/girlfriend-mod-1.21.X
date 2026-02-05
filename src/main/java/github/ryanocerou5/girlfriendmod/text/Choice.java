package github.ryanocerou5.girlfriendmod.text;

public class Choice {
    public final String text;        // what player sees
    public final String destinationId;    // which message this leads to

    public Choice(String text, String destinationId) {
        this.text = text;
        this.destinationId = destinationId;
    }
}

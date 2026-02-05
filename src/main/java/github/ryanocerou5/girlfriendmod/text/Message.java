package github.ryanocerou5.girlfriendmod.text;

import java.util.List;

public class Message {
    public final String id;
    public final String destinationId;
    public final String text;
    public final List<Choice> choices;

    public Message(String id, String text, List<Choice> choices) {
        if (choices == null || choices.isEmpty()) {
            throw new IllegalArgumentException("choices cannot be null or empty for choice messages!");
        }
        this.id = id;
        this.destinationId = null;
        this.text = text;
        this.choices = choices;
    }

    public Message(String id, String destinationId, String text) {
        if (destinationId == null) {
            throw new IllegalArgumentException("destinationId cannot be null for messages without choices!");
        }
        this.id = id;
        this.destinationId = destinationId;
        this.text = text;
        this.choices = List.of();
    }

    public boolean hasChoices() {
        return !choices.isEmpty();
    }
}

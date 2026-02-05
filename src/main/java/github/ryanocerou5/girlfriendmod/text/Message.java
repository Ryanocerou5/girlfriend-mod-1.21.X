package github.ryanocerou5.girlfriendmod.text;

import java.util.ArrayList;
import java.util.List;

public class Message {
    public String id;
    public String destinationId;
    public String text;
    public List<Choice> choices;
    public Message() {}

    public Message(String id, String destinationId, String text) {
        this.id = id;
        this.destinationId = destinationId;
        this.text = text;
    }

    public boolean hasChoices() {
        return choices != null && !choices.isEmpty();
    }

    public void ensureChoices() {
        if (choices == null) choices = new ArrayList<>();
    }
}

package github.ryanocerou5.girlfriendmod.text;

import java.util.List;
import java.util.Map;

public class GirlfriendTextLibrary {
    public static class Message {
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
    public static class Choice {
        public final String text;        // what player sees
        public final String destinationId;    // which message this leads to

        public Choice(String text, String destinationId) {
            this.text = text;
            this.destinationId = destinationId;
        }
    }

    public static final List<Message> MESSAGES = List.of(
            new Message(
                    "intro",
                    "intro_2",
                    "Hey! My name is Bob!"),
            new Message(
                    "intro_2",
                    "What's your name?",
                    List.of(
                            new Choice("Not Bob...", "not_bob"),
                            new Choice("Also Bob", "is_bob")
                    )),
            new Message(
                    "not_bob",
                    "next",
                    "Oh... Okay!"
            ),
            new Message(
                    "is_bob",
                    "next",
                    "Oh! Okay!"
            )
    );
}

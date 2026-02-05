package github.ryanocerou5.girlfriendmod.text;

import java.util.List;

public class GirlfriendTextLibrary {
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

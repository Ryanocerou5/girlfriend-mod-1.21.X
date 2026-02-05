package github.ryanocerou5.girlfriendmod.text;

import java.util.Map;
import java.util.stream.Collectors;

public class GirlfriendTextManager {
    private static final Map<String, GirlfriendTextLibrary.Message> MESSAGE_MAP = GirlfriendTextLibrary.MESSAGES
            .stream()
            .collect(Collectors.toMap(msg -> msg.id, msg -> msg));

    private static String currentMessageId = "intro";
    private static int currentCharIndex = 0;
    private static long lastUpdateTime = 0;
    private static final int TYPING_SPEED_MS = 50;
    private enum State {
        IDLE,
        TYPING,
        WAITING_FOR_INPUT
    }

    private static State state = State.IDLE;

    public static void startMessage(String messageId)
    {
        currentMessageId = messageId;
        currentCharIndex = 0;
        lastUpdateTime = System.currentTimeMillis();
        state = State.TYPING;
    }

    public static void requestAdvance()
    {
        GirlfriendTextLibrary.Message msg = MESSAGE_MAP.get(currentMessageId);
        if (msg == null) return;

        if (state == State.TYPING)
        {
            currentCharIndex = msg.text.length();
            state = State.WAITING_FOR_INPUT;
            return;
        }

        if (state == State.WAITING_FOR_INPUT)
        {
            String nextId;

            if (msg.hasChoices()) {
                // Make choices work
                nextId = "";
            } else {
                nextId = msg.destinationId;
            }

            if (nextId != null && !nextId.isEmpty()) {
                startMessage(nextId);
            } else {
                state = State.IDLE;
            }
        }
    }

    private static void updateTyping()
    {
        if (state != State.TYPING) return;

        GirlfriendTextLibrary.Message msg = MESSAGE_MAP.get(currentMessageId);
        if (msg == null) return;

        // Update typing
        long now = System.currentTimeMillis();

        if (now - lastUpdateTime >= TYPING_SPEED_MS) {
            int charsToAdd = (int) ((now - lastUpdateTime) / TYPING_SPEED_MS);

            currentCharIndex = Math.min(currentCharIndex + charsToAdd, msg.text.length());

            lastUpdateTime += (long) charsToAdd * TYPING_SPEED_MS;

            if (currentCharIndex >= msg.text.length())
            {
                state = State.WAITING_FOR_INPUT;
            }
        }
    }
    public static String getVisibleText()
    {
        updateTyping();
        GirlfriendTextLibrary.Message msg = MESSAGE_MAP.get(currentMessageId);
        if (msg == null) return "";
        return msg.text.substring(0, currentCharIndex);
    }

    public static boolean isTyping()
    {
        return state == State.TYPING;
    }

    public static boolean isWaitingForInput()
    {
        return state == State.WAITING_FOR_INPUT;
    }
}

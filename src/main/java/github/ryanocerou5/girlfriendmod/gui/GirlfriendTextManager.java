package github.ryanocerou5.girlfriendmod.gui;

public class GirlfriendTextManager {
    public static final String[] messages = new String[] {
        "Hello, world!",
        "The quick brown fox jumped over the lazy dog",
        "Lorem ipsum",
        "Abby is the cutest doggy in the world"
    };

    private static int currentMessageIndex = 0;
    private static int currentCharIndex = 0;
    private static long lastUpdateTime = 0;
    private static final int TYPING_SPEED_MS = 50;
    private enum State {
        IDLE,
        TYPING,
        WAITING_FOR_INPUT
    }

    private static State state = State.IDLE;

    public static void startMessage(int index)
    {
        currentMessageIndex = index;
        currentCharIndex = 0;
        lastUpdateTime = System.currentTimeMillis();
        state = State.TYPING;
    }

    public static void requestAdvance()
    {
        if (state == State.TYPING)
        {
            currentCharIndex = messages[currentMessageIndex].length();
            state = State.WAITING_FOR_INPUT;
            return;
        }

        if (state == State.WAITING_FOR_INPUT)
        {
            int next = (currentMessageIndex + 1) % messages.length;
            startMessage(next);
        }
    }

    private static void updateTyping()
    {
        if (state != State.TYPING) return;

        // Update typing
        long now = System.currentTimeMillis();

        if (now - lastUpdateTime >= TYPING_SPEED_MS) {
            int charsToAdd = (int) ((now - lastUpdateTime) / TYPING_SPEED_MS);

            currentCharIndex = Math.min(currentCharIndex + charsToAdd, messages[currentMessageIndex].length());

            lastUpdateTime += (long) charsToAdd * TYPING_SPEED_MS;

            if (currentCharIndex >= messages[currentMessageIndex].length())
            {
                state = State.WAITING_FOR_INPUT;
            }
        }
    }
    public static String getVisibleText()
    {
        updateTyping();
        return messages[currentMessageIndex].substring(0, currentCharIndex);
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

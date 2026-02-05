package github.ryanocerou5.girlfriendmod.gui;

public class GirlfriendTextManager {
    public static final String[] messages = new String[] {
        "Hello, world!",
        "The quick brown fox jumped over the lazy dog"
    };

    private static int currentMessageIndex = 0;
    private static int currentCharIndex = 0;
    private static long lastUpdateTime = 0;
    private static final int TYPING_SPEED_MS = 50;

    public static void startMessage(int index)
    {
        currentMessageIndex = index;
        currentCharIndex = 0;
        lastUpdateTime = System.currentTimeMillis();
    }
    public static String getVisibleText()
    {
        // Update typing
        long now = System.currentTimeMillis();
        if (currentCharIndex < messages[currentMessageIndex].length())
        {
            if (now - lastUpdateTime >= TYPING_SPEED_MS)
            {
                int charsToAdd = (int)((now - lastUpdateTime) / TYPING_SPEED_MS);
                currentCharIndex = Math.min(currentCharIndex + charsToAdd, messages[currentMessageIndex].length());
                lastUpdateTime += (long) charsToAdd * TYPING_SPEED_MS;
            }
        }


        return messages[currentMessageIndex].substring(0, currentCharIndex);
    }

    public static boolean isMessageFinished()
    {
        return currentCharIndex >= messages[currentMessageIndex].length();
    }
}

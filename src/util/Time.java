package util;

/**
 * Time serves as a way to find out how many seconds have passed since the program started
 * This is used mainly for finding out how much time has passed between frames
 */
public class Time {
    private static float timeStarted = System.nanoTime();

    /**
     * Gets how many seconds have passed since the program started
     * @return seconds
     */
    public static float getTime() {
        //region Copied code
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
        //endregion
    }
}

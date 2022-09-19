package connect4;

public class BoardColors extends ConsoleColors {
    public static String board(String str) {
        return BLUE
            + BLUE_BACKGROUND
            + str
            + RESET;
    };

    public static String p1(String str) {
        return PURPLE
            + PURPLE_BACKGROUND
            + str
            + RESET;
    };


    public static String p2(String str) {
        return GREEN
            + GREEN_BACKGROUND
            + str
            + RESET;
    };

    public static String bg(String str) {
        return WHITE
            + WHITE_BACKGROUND
            + str
            + RESET;

    };
}

package connect4;

import java.util.Arrays;
import java.util.HashSet;

public record Xy(int x, int y) {

    public Xy[] extend(Xy direction, int length) {
        Xy[] row = new Xy[length];
        for (int i = 0; i < length; i++) {
            int x = this.x + direction.x() * i;
            int y = this.y + direction.y() * i;
            row[i] = new Xy(x, y);
        }
        return row;
    }

    public static HashSet<Xy> selection(Xy p1, Xy p2) {
        int size = (p2.y() - p1.y() + 1) * (p2.x() - p1.x() + 1);
        Xy[] selection = new Xy[size];
        int counter = 0;
        for (int y = p1.y(); y <= p2.y(); y++) {
            for (int x = p1.x(); x <= p2.x(); x++) {
                selection[counter] = new Xy(x, y);
                counter++;
            }
        }
        return new HashSet<>(Arrays.asList(selection));
    }

    public static HashSet<Xy> selection(Xy p) {
        return selection(new Xy(0, 0), p);
    }

    public static boolean isRowInSelection(Xy[] row, HashSet<Xy> selection) {
        return Arrays.stream(row).allMatch(point -> selection.contains(point));
    }
} 

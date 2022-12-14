package connect4;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.stream.IntStream;
import java.util.function.Function;

public class Board {
    private static final Xy[] DELTAS = new Xy[]{
        new Xy(0, 1),
        new Xy(1, 0),
        new Xy(1, 1),
        new Xy(1, -1)
    };

    private Xy size;
    private Cell[][] board;
    private int winLength;

    // inferred
    private HashSet<Xy> points;

    public Board(int rows, int cols, int winLength) {
        this.size = new Xy(cols, rows);
        this.board = new Cell[this.size.y()][this.size.x()];
        this.winLength = winLength;
        this.points = Xy.selection(new Xy(cols - 1, rows - 1));
        for (Xy i : this.points) {
            board[i.y()][i.x()] = new Cell(i);
        }
    }

    public Board(int rows, int cols) {
        this(rows, cols, 4);
    }

    public int getCols() {
        return this.size.x();
    }

    public Cell getCell(Xy point) {
        return this.board[point.y()][point.x()];
    }

    public void removeCell(Xy point) {
        setCell(point, null);
    }

    private void setCell(Xy point, AbstractPlayer player) {
        getCell(point).setPlayer(player);
    }

    public void clear() {
        this.points.stream()
            .map(point -> getCell(point))
            .forEach(cell -> cell.setPlayer(null));
    }

    public int getWinLength() {
        return this.winLength;
    }

    public void setWinLength(int winLength) {
        this.winLength = winLength;
    }   

    public boolean isDroppable(int column) {
        return getCell(new Xy(column, 0)).isEmpty();
    }

    public int[] validCols() {
        return IntStream.range(0, this.size.x())
            .filter(col -> isDroppable(col))
            .toArray();
    }

    public Xy drop(AbstractPlayer player, int column) {
        Xy p2 = new Xy(column, this.size.y() - 1);
        for (Xy p : p2.extend(new Xy(0, -1), this.size.y())) {
            if (getCell(p).isEmpty()) {
                setCell(p, player);
                return p;
            }
        }
        return null;
    }

    public boolean isFull() {
        return this.points.stream()
            .map(point -> getCell(point))
            .allMatch(cell -> !cell.isEmpty());
    }

    public boolean isWonBy(AbstractPlayer player) {
        return getWinningIndices(player).length > 0;
    }

    public boolean isTie(AbstractPlayer player) {
        return !isWonBy(player) && isFull();
    }

    private void drawVerticalDelim(StringBuilder drawing) {
        int cols = getCols();
        int pipes = cols * 2 + 2;
        int total = cols * 4 + pipes;
        String line = "-".repeat(total);
        drawing.append(BoardColors.board(line));
        drawing.append("\n");
    }

    private void drawPlayerCell(StringBuilder drawing, 
                                Cell cell,
                                AbstractPlayer p,
                                String identifier,
                                Function<String, String> painter,
                                Xy lastMove) {
        if (cell.getPlayer().equals(p)) {
            if (lastMove != null && cell.getXy().equals(lastMove)) {
                drawing.append(painter.apply(identifier.repeat(2)));
                drawing.append(BoardColors.bg("!".repeat(2)));
            } else {
                drawing.append(painter.apply(identifier.repeat(4)));
            }
        }
    }

    private void drawRow(StringBuilder drawing,
                         AbstractPlayer p1,
                         AbstractPlayer p2,
                         Cell[] row,
                         Xy lastMove) {
        for (Cell cell : row) {
            drawing.append(BoardColors.board("||"));
            if (cell.isEmpty()) {
                drawing.append(BoardColors.bg("...."));
            } else {
                drawPlayerCell(drawing, cell, p1, "X", BoardColors::p1, lastMove);
                drawPlayerCell(drawing, cell, p2, "O", BoardColors::p2, lastMove);
            }
        }
        drawing.append(BoardColors.board("||"));
        drawing.append("\n");
    }

    private void drawRow(StringBuilder drawing,
                         AbstractPlayer p1,
                         AbstractPlayer p2,
                         Cell[] row) {
        drawRow(drawing, p1, p2, row, null);
    }

    public void draw(AbstractPlayer p1, AbstractPlayer p2, Xy lastMove) {
        StringBuilder drawing = new StringBuilder();
        for (Cell[] row : this.board) {
            drawVerticalDelim(drawing);
            drawRow(drawing, p1, p2, row, lastMove);
            drawRow(drawing, p1, p2, row);
        }
        drawVerticalDelim(drawing);
        System.out.println(drawing.toString());
    }

    protected Xy[][] getWinningIndices(AbstractPlayer player) {
        List<Xy[]> rows = new ArrayList<>();
        for (Xy delta : DELTAS) {
            for (Xy i : this.points) {
                Xy[] row = i.extend(delta, this.winLength);
                if (Xy.isRowInSelection(row, this.points) 
                    && Arrays.stream(row)
                        .map(point -> getCell(point))
                        .allMatch(cell -> !cell.isEmpty() &&
                                          cell.getPlayer().equals(player))) {
                    rows.add(row);
                }
            }
        }
        // temporary
        Xy[][] ret = new Xy[rows.size()][this.winLength];
        for (int i = 0; i < rows.size(); i++) {
            ret[i] = rows.get(i);
        }
        return ret;
        //return rows.toArray(); // ?   
    }
}

package connect4;

import java.util.List;
import java.util.ArrayList;


public class SmartBotPlayer extends AbstractBotPlayer {
    private Game game;

    public SmartBotPlayer(String name, Game game) {
        super(name, game);
        this.game = game;
    }

    public int chooseColumn() {
        Board board = getBoard();
        List<Integer> cols = new ArrayList<>();
        int[] validCols = board.validCols();
        int oldWinLength = board.getWinLength();
        int currentWinLength = board.getWinLength();
        boolean foundAtWinLength = false;
        boolean blockingMode = false;
        while (!foundAtWinLength) {
            for (int col : validCols) {
                // opponent block check
                // makes doAnalyze winrate go from ~90% -> ~97%
                if (currentWinLength == oldWinLength - 1) {
                    board.setWinLength(oldWinLength);
                    AbstractPlayer opponent = getOpponent();
                    Xy opponentDrop = board.drop(opponent, col);  
                    if (board.isWonBy(opponent)) {
                        cols.add(col);
                        foundAtWinLength = true;
                        blockingMode = true;
                    }
                    board.removeCell(opponentDrop);
                }
                
                if (!blockingMode) {
                    board.setWinLength(currentWinLength);
                    Xy[][] winningRowsAlready = getWinningIndices();
                    Xy droppedAt = board.drop(this, col);
                    Xy[][] winningRowsFromDrop = getWinningIndices();
                    if (winningRowsFromDrop.length > winningRowsAlready.length) {
                        cols.add(col);
                        foundAtWinLength = true;
                    }
                    board.removeCell(droppedAt);
                }
            }
            currentWinLength--;
        }
        board.setWinLength(oldWinLength);
        return cols.get(getRandom().nextInt(cols.size()));
    }

}

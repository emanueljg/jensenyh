package connect4;

import java.util.stream.IntStream;

public class DumbBotPlayer extends AbstractBotPlayer {

    public DumbBotPlayer(String name, Game game) {
        super(name, game);
    }

    @Override
    public int chooseColumn() {
        int[] validCols = IntStream.range(0, getCols())
            .filter(col -> getBoard().isDroppable(col))
            .toArray(); 
        return validCols[getRandom().nextInt(validCols.length)];
        //getBoard().drop(this, col);

    }
}
    

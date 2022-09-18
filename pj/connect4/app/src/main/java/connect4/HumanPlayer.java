package connect4;


public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(String name, Game game) {
        super(name, game);
    }
    
    @Override
    public void act() {
        int col;
        do {
            col = new Prompter.Builder<Integer>(
                String.format(
                    "Column to drop brick in (1-%d)", getCols()))
                .mapper(x -> Integer.parseInt(x) - 1)
                .mappedChecker(x -> x >= 0 && x < getCols())
                .prompt();
        } while (!getBoard().isDroppable(col));
        getBoard().drop(this, col);
    }
}

package connect4;

import java.util.Random;

public abstract class AbstractBotPlayer extends AbstractPlayer {
    private String name;
    private Game game;
    private Random random;

    protected AbstractBotPlayer(String name, Game game) {
        super(name, game);
        this.name = name;
        this.random = new Random();
    }
    
    public abstract int chooseColumn();

    @Override
    public void act() {
        if (getGame().getDoPrint()) {
            System.out.printf("%s is thinking...\n", this);
            try {
                Thread.sleep(3000);
            } catch (Exception ignore) { }
        }
        getBoard().drop(this, chooseColumn());
    }

    public Random getRandom() {
        return this.random;
    }
}

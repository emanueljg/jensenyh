package connect4;

import java.util.Optional;

public class Cell {
    private Xy point;
    private Optional<AbstractPlayer> player;

    public Cell(Xy point, AbstractPlayer player) {
        this.point = point;
        this.player = Optional.ofNullable(player);
    }

    public Cell(Xy point) {
        this(point, null);
    }

    public AbstractPlayer getPlayer() {
        return this.player.get();
    }

    public void setPlayer(AbstractPlayer player) {
        this.player = Optional.ofNullable(player);
    }

    public boolean isEmpty() {
        return !this.player.isPresent();
    }

    @Override
    public String toString() {
        return this.player.toString();
    }

}


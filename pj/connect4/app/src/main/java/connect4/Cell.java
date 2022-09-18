package connect4;

import java.util.Optional;

public class Cell {
	private Xy coords;
	private Optional<AbstractPlayer> player;

	public Cell(Xy coords, AbstractPlayer player) {
		this.coords = coords;
		this.player = Optional.ofNullable(player);
	}

	public Cell(Xy coords) {
		this(coords, null);
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


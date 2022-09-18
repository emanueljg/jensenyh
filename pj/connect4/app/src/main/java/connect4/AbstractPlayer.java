package connect4;

import java.util.Scanner;

public abstract class AbstractPlayer {
	private String name;
	private Game game;

	protected AbstractPlayer(String name, Game game) {
		this.name = name;
		this.game = game;
	}

	public abstract void act();

	@Override
	public String toString() {
		return this.name;
	}	

	public AbstractPlayer getOpponent() {
		return this.game.getMyOpponent(this);
	}

	public Game getGame() {
		return this.game;
	}

	public boolean getDoPrint() {
		return getGame().getDoPrint();
	}

	public Board getBoard() {
		return getGame().getBoard();
	}

	public int getCols() {
		return getBoard().getCols();
	}

	public Xy[][] getWinningIndices() {
		return getBoard().getWinningIndices(this);
	}
}


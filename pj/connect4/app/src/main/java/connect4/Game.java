package connect4;

import java.util.Optional;
import java.util.Scanner;
import java.util.LinkedHashMap;

public class Game {
	private Board board;
	private AbstractPlayer p1;
	private AbstractPlayer p2;
	private AbstractPlayer[] players;
	private boolean doPrint;

	private static final Scanner in = new Scanner(System.in);

	public Game(Board board, boolean doPrint) {
		this.board = board;
		this.players = new AbstractPlayer[2];
		this.doPrint = doPrint;
	}

	public boolean getDoPrint() {
		return this.doPrint;
	}

	public Board getBoard() {
		return this.board;
	}

	public void setPlayerOne(AbstractPlayer player) {
		this.p1 = player;
		this.players[0] = this.p1;

	}

	public void setPlayerTwo(AbstractPlayer player) {
		this.p2 = player;
		this.players[1] = this.p2;
	}

	public AbstractPlayer getMyOpponent(AbstractPlayer me) {
		return me.equals(this.p1) ? this.p2 : this.p1;
	}

	public static int getInteractiveDimensions(String dim, String defaultValue) {
		return new Prompter.Builder<Integer>(
			String.format("Enter # of board %ss", dim))
	        .defaultValue(defaultValue)
			.mapper(answer -> Integer.parseInt(answer))
			.mappedChecker(x -> x > 0)
			.prompt();
	}	

	public static AbstractPlayer getInteractivePlayer(int playerN, Game game) {
		String name = new Prompter.Builder<String>(
			String.format("Enter player %d name", playerN))
			.checker(answer -> !answer.equals(""))
			.prompt();

		LinkedHashMap<String, AbstractPlayer> playerTypes = new LinkedHashMap<>();
		playerTypes.put("human", new HumanPlayer(name, game));
		playerTypes.put("dumb bot", new DumbBotPlayer(name, game));
		playerTypes.put("smart bot", new SmartBotPlayer(name, game));

		return new Prompter.Builder<AbstractPlayer>(
			String.format("Choose player %d type", playerN))
			.choice(playerTypes)
			.prompt();
	}

	public static boolean getInteractiveDoPrint() {
		LinkedHashMap<String, Boolean> yesOrNo = new LinkedHashMap<>();
		yesOrNo.put("yes", true);
		yesOrNo.put("no", false);
		return new Prompter.Builder<Boolean>("Print output?")
			.defaultValue("yes")
			.choice(yesOrNo)
			.prompt();
	}

	public static void doMainMenu() {
		LinkedHashMap<String, Runnable> mainMenuChoices = new LinkedHashMap<>();
		mainMenuChoices.put("play", () -> start());
		mainMenuChoices.put("analyze bot W/L", () -> doAnalyze());
		mainMenuChoices.put("exit", () -> System.exit(0));
		new Prompter.Builder<Runnable>("Main menu")
			.defaultValue("play")
			.choice(mainMenuChoices)
			.prompt()
			.run();
	}

	public static void start() {
		Game.fromInteractive().doMatchLoop();
	}

	public void doMatchLoop() {
		doGameLoop();
		LinkedHashMap<String, Runnable> postMatchChoices = new LinkedHashMap<>();
		postMatchChoices.put("play one more", () -> {getBoard().clear();
													 doMatchLoop();});
		postMatchChoices.put("game with new settings", () -> start());
		postMatchChoices.put("back to main menu", () -> doMainMenu());
		postMatchChoices.put("exit", () -> System.exit(0));
		new Prompter.Builder<Runnable>(
			"Game finished. What next?")
			.choice(postMatchChoices)
			.prompt()
			.run();
	}

	public static Game fromInteractive() {
		System.out.println("4 i rad");
		System.out.println("-------");

		int rows = getInteractiveDimensions("row", "6");
		int cols = getInteractiveDimensions("col", "7");
		boolean doPrint = getInteractiveDoPrint();
		Game game = new Game(new Board(rows, cols), doPrint);
		game.setPlayerOne(getInteractivePlayer(1, game));
		game.setPlayerTwo(getInteractivePlayer(2, game));

		System.out.println("Starting game...\n");
		return game;
	}

	public Optional<AbstractPlayer> doGameLoop() {
		while (true) {
			for (AbstractPlayer p : this.players) {
				if (this.doPrint) {
					this.board.draw(this.p1, this.p2);
					System.out.printf("%s's turn.\n", p);
				}
				p.act();
				if (this.board.isWonBy(p)) {
					if (this.doPrint) System.out.printf("%s won!\n", p);
					return Optional.of(p);
				} if (this.board.isTie(p)) {
					if (this.doPrint) System.out.println("It's a tie.");
					return Optional.empty();
				}
			}
		}
	}

	public static void doAnalyze() {
		Game game = new Game(new Board(6, 7), false);
		AbstractPlayer p1 = new DumbBotPlayer("p1", game); 
		AbstractPlayer p2 = new SmartBotPlayer("p2", game); 
		game.setPlayerOne(p1);
		game.setPlayerTwo(p2);
		int n = 1000;
		int p1Wins = 0;
		int p2Wins = 0;
		int ties = 0;
		System.out.printf("Running %d games...\n", n);
		for (int i = 0; i < n; i++) {
			game.getBoard().clear();
			Optional<AbstractPlayer> result = game.doGameLoop();
			if (!result.isPresent()) {
				ties++;
			} else if (result.get().equals(p1)) {
				p1Wins++;
			} else if (result.get().equals(p2)) {
				p2Wins++;
			}
		}
		System.out.println("-".repeat(50));
		System.out.printf("%s wins: %d\n", p1, p1Wins);
		System.out.printf("%s wins: %d\n", p2, p2Wins);
		System.out.printf("ties: %d\n", ties);
		System.out.println("-".repeat(50));
	}

}

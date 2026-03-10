package edu.txst.midterm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * MazeGUI provides the graphical user interface for the maze game.
 * It handles the main game window, rendering, user input, and game state display.
 */
public class MazeGUI extends JFrame {
	private Board originalBoard;
	private Board currentBoard;
	private GameEngine engine;
	private GamePanel gamePanel;
	private InfoPanel infoPanel;
	private JMenuItem resetItem;
	private int stepCounter;

	/**
	 * Initializes the MazeGUI window and sets up the game interface.
	 * Includes the menu bar, info panel, and game panel.
	 */
	public MazeGUI() {
		setTitle("16-Bit Maze");
		setSize(640, 480); // Adjusted for 10x5 grid with scaling
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		initMenu();

		infoPanel = new InfoPanel();
		gamePanel = new GamePanel();
		add(infoPanel, BorderLayout.NORTH);
		add(gamePanel, BorderLayout.CENTER);

		// Handle Keyboard Input
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (engine == null)
					return;

				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP -> engine.movePlayer(-1, 0);
					case KeyEvent.VK_DOWN -> engine.movePlayer(1, 0);
					case KeyEvent.VK_LEFT -> engine.movePlayer(0, -1);
					case KeyEvent.VK_RIGHT -> engine.movePlayer(0, 1);
				}
				gamePanel.repaint();
				infoPanel.setRemainingSteps(currentBoard.stepCounter.getRemainingSteps());

				// Check for game over
				if (engine.isGameOver()) {
					JOptionPane.showMessageDialog(MazeGUI.this,
							"Game over! You used all available steps!", "Level Complete",
							JOptionPane.INFORMATION_MESSAGE);

					// Optional: Disable engine to prevent movement after win
					engine = null;
					resetItem.setEnabled(false);
				}
				// Check for victory
				else if (engine != null && engine.playerWins()) {
					JOptionPane.showMessageDialog(MazeGUI.this,
							"Congratulations! You found the exit.", "Level Complete",
							JOptionPane.INFORMATION_MESSAGE);

					// Optional: Disable engine to prevent movement after win
					engine = null;
					resetItem.setEnabled(false);
				}
			}
		});
	}

	/**
	 * Initializes the menu bar with Game menu options.
	 * Includes Open and Reset menu items with their action listeners.
	 */
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");

		JMenuItem openItem = new JMenuItem("Open");
		resetItem = new JMenuItem("Reset");
		resetItem.setEnabled(false); // Disabled by default

		openItem.addActionListener(e -> openFile());
		resetItem.addActionListener(e -> resetGame());

		gameMenu.add(openItem);
		gameMenu.add(resetItem);
		menuBar.add(gameMenu);
		setJMenuBar(menuBar);
	}

	/**
	 * Opens a file dialog to load a maze level from a CSV file.
	 * Initializes the game engine with the loaded board.
	 */
	private void openFile() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			CSVBoardLoader loader = new CSVBoardLoader();

			// Load and Store
			originalBoard = loader.load(selectedFile.getAbsolutePath());
			currentBoard = originalBoard.clone();
			engine = new GameEngine(currentBoard);

			resetItem.setEnabled(true);
			gamePanel.setBoard(currentBoard);
			infoPanel.setRemainingSteps(currentBoard.stepCounter.getRemainingSteps());
			gamePanel.repaint();
		}
	}

	/**
	 * Resets the current level by reloading the original board state.
	 * Reinitializes the game engine and clears any player progress.
	 */
	private void resetGame() {
		if (originalBoard != null) {
			currentBoard = originalBoard.clone();
			engine = new GameEngine(currentBoard);
			gamePanel.setBoard(currentBoard);
			infoPanel.setRemainingSteps(currentBoard.stepCounter.getRemainingSteps());
			gamePanel.repaint();
		}
	}

	/**
	 * InfoPanel is an inner class that displays game information.
	 * Shows the remaining steps and coin count to the player.
	 */
	private class InfoPanel extends JPanel {
		private JLabel infoRemainingSteps;
		private JLabel infoCoins;

		/**
		 * Initializes the info panel with labels for remaining steps and coins.
		 */
		public InfoPanel() {
			this.setLayout(new FlowLayout());
			this.add(new JLabel("Remaining steps: "));
			infoRemainingSteps = new JLabel("0");
			this.add(infoRemainingSteps);
			this.add(new JLabel("Coins: "));
			infoCoins = new JLabel("0");
			this.add(infoCoins);
		}

		/**
		 * Updates the displayed remaining steps.
		 *
		 * @param remainingSteps The number of steps remaining
		 */
		public void setRemainingSteps(int remainingSteps) {
			this.infoRemainingSteps.setText(Integer.toString(remainingSteps));
		}

		/**
		 * Updates the displayed coin count.
		 *
		 * @param infoCoins The number of coins collected
		 */
		public void setInfoCoins(int infoCoins) {
			this.infoCoins.setText(Integer.toString(infoCoins));
		}
	}

	/**
	 * GamePanel is an inner class responsible for rendering the maze game.
	 * Draws the tiles of the board based on their cell types.
	 */
	private class GamePanel extends JPanel {
		private Board board;
		private final int TILE_SIZE = 64; // Scale up for visibility

		/**
		 * Sets the board to be rendered.
		 *
		 * @param board The board to render
		 */
		public void setBoard(Board board) {
			this.board = board;
		}

		/**
		 * Paints all tiles of the board on the panel.
		 * Called automatically when the panel is redrawn.
		 *
		 * @param g The graphics context
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (board == null)
				return;

			for (int r = 0; r < 6; r++) {
				for (int c = 0; c < 10; c++) {
					int cell = board.getCell(r, c);
					drawTile(g, cell, c * TILE_SIZE, r * TILE_SIZE);
				}
			}
		}

		/**
		 * Draws a single tile based on its type.
		 * Uses colors to distinguish between different cell types (floor, walls, items, etc.).
		 *
		 * @param g The graphics context
		 * @param type The cell type to draw
		 * @param x The x-coordinate for the tile
		 * @param y The y-coordinate for the tile
		 */
		private void drawTile(Graphics g, int type, int x, int y) {
			// Placeholder colors until you link the sprite loading logic
			switch (type) {
				case 0 -> g.setColor(Color.LIGHT_GRAY); // Floor
				case 1 -> g.setColor(Color.DARK_GRAY); // Wall
				case 2 -> g.setColor(Color.YELLOW); // Coin
				case 3 -> g.setColor(Color.CYAN); // Map
				case 4 -> g.setColor(Color.RED); // First Aid Kit
				case 5 -> g.setColor(Color.MAGENTA); // Exit
				case 6 -> g.setColor(Color.BLUE); // Player
				default -> g.setColor(Color.BLACK);
			}
			g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
			g.setColor(Color.WHITE);
			g.drawRect(x, y, TILE_SIZE, TILE_SIZE); // Grid lines
		}
	}

	/**
	 * Starts the maze game application by creating and displaying the MazeGUI window.
	 *
	 * @param args Command-line arguments (not used)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MazeGUI().setVisible(true));
	}
}

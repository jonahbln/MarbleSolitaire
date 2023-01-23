package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * represents any abstract model of a marble solitaire board.
 */
public abstract class AMarbleSolitaireModel implements MarbleSolitaireModel {
  protected final int size;
  protected final int thickness;
  protected final SlotState[][] grid;

  protected AMarbleSolitaireModel(int thickness, int sRow, int sCol)
          throws IllegalArgumentException {
    this.thickness = thickness;
    this.size = this.getBoardSize();
    this.grid = new SlotState[size][size];

    this.createBoard(sRow, sCol);

    if (sRow >= this.size || sCol >= this.size || sCol < 0 || sRow < 0
            || grid[sRow][sCol] == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    }
  }

  // populates this grid with a game board based on this model's fields
  protected abstract void createBoard(int sRow, int sCol) throws IllegalArgumentException;


  /**
   * Move a single marble from a given position to another given position.
   * A move is valid only if the from and to positions are valid. Specific
   * implementations may place additional constraints on the validity of a move.
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException if the move is not possible
   */
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws
          IllegalArgumentException {
    if (validMove(fromRow, fromCol, toRow, toCol) == 0) {
      throw new IllegalArgumentException("invalid move!");
    }

    if (validMove(fromRow, fromCol, toRow, toCol) == -1) {
      this.grid[toRow][Math.max(fromCol, toCol) - 1] = SlotState.Empty;
    } else if (validMove(fromRow, fromCol, toRow, toCol) == 1) {
      this.grid[Math.max(fromRow, toRow) - 1][fromCol] = SlotState.Empty;
    } else if (validMove(fromRow, fromCol, toRow, toCol) == 2) {
      this.grid[Math.max(fromRow, toRow) - 1][Math.max(fromCol, toCol) - 1] = SlotState.Empty;
    }

    this.grid[fromRow][fromCol] = SlotState.Empty;
    this.grid[toRow][toCol] = SlotState.Marble;
  }

  // determines if the given coordinates will produce a valid move on this board
  // if invalid, returns 0
  // if valid, returns 1 for an up/down move, and -1 for a side-to-side move
  protected abstract int validMove(int fromRow, int fromCol, int toRow, int toCol);

  /**
   * Determine and return if the game is over or not. A game is over if no
   * more moves can be made.
   *
   * @return true if the game is over, false otherwise
   */
  public boolean isGameOver() {
    for (int y = 0; y < this.size; y++) {
      for (int x = 0; x < this.size; x++) {
        if (this.grid[x][y] == SlotState.Marble) { // for every marble in the grid
          for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
              // iterate through each combination of moves that this marble can make
              // if a valid move can be made, the game is not over
              if (validMove(x, y, x + j, y + i) != 0) {
                return false;
              }
            }
          }
        }
      }
    }
    return true; // after checking each marble, no valid moves can be made, the game is over
  }

  /**
   * Return the size of this board. The size is roughly the longest dimension of a board
   *
   * @return the size as an integer
   */
  public int getBoardSize() {
    return thickness * 3 - 2;
  }

  /**
   * Get the state of the slot at a given position on the board.
   *
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return the state of the slot at the given row and column
   * @throws IllegalArgumentException if the row or the column are beyond
   *                                  the dimensions of the board
   */
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
      throw new IllegalArgumentException("invalid coordinates");
    }
    return this.grid[row][col];
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  public int getScore() {
    int x = 0;
    for (SlotState[] i : this.grid) {
      for (SlotState j : i) {
        if (j == SlotState.Marble) {
          x++;
        }
      }
    }
    return x;
  }
}

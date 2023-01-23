package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * represents a triangular model of a marble solitaire board.
 */
public class TriangleSolitaireModel extends AMarbleSolitaireModel {
  /**
   * creates a game with the specified dimension and an empty slot at the specified row and column.
   *
   * @param dimensions desired board size
   * @param row        starting row
   * @param col        starting column
   * @throws IllegalArgumentException if the dimensions or starting point are invalid.
   */
  public TriangleSolitaireModel(int dimensions, int row, int col) throws IllegalArgumentException {
    super(dimensions, row, col);
  }

  /**
   * creates a 5-row game with the empty slot at the specified position.
   *
   * @param row starting row
   * @param col starting column
   * @throws IllegalArgumentException if the specified position is invalid.
   */
  public TriangleSolitaireModel(int row, int col) throws IllegalArgumentException {
    super(5, row, col);
  }

  /**
   * creates a game with the specified dimension and the empty slot at (0,0).
   *
   * @param dimensions the desired board size
   * @throws IllegalArgumentException f the specified dimension is invalid (non-positive).
   */
  public TriangleSolitaireModel(int dimensions) throws IllegalArgumentException {
    super(dimensions, 0, 0);
  }

  /**
   * creates a 5-row game with the empty slot at (0,0).
   */
  public TriangleSolitaireModel() {
    super(5, 0, 0);
  }

  @Override
  protected void createBoard(int sRow, int sCol) throws IllegalArgumentException {
    if (thickness <= 0) {
      throw new IllegalArgumentException("thickness must be a positive integer");
    }
    for (int x = 0; x < thickness; x++) {
      for (int y = 0; y < thickness; y++) {
        if (y > x) {
          grid[x][y] = MarbleSolitaireModelState.SlotState.Invalid;
        } else if (x == sRow && y == sCol) {
          grid[x][y] = MarbleSolitaireModelState.SlotState.Empty;
        } else {
          grid[x][y] = MarbleSolitaireModelState.SlotState.Marble;
        }
      }
    }
  }

  @Override
  public int getBoardSize() {
    return thickness;
  }

  @Override
  protected int validMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (fromRow >= this.size
            || fromCol >= this.size
            || fromRow < 0
            || fromCol < 0
            || toRow >= this.size
            || toCol >= this.size
            || toRow < 0
            || toCol < 0
            || this.grid[fromRow][fromCol] != SlotState.Marble
            || this.grid[toRow][toCol] != SlotState.Empty) {
      return 0; // invalid coordinates given
    }
    SlotState middle;
    if (fromRow == toRow) {
      middle = this.grid[fromRow][Math.max(fromCol, toCol) - 1];
      if (middle != SlotState.Marble || Math.abs(fromCol - toCol) != 2) {
        return 0; // invalid
      }
      return -1;
    } else if (fromCol == toCol) {
      middle = this.grid[Math.max(fromRow, toRow) - 1][fromCol];
      if (middle != SlotState.Marble || Math.abs(fromRow - toRow) != 2) {
        return 0; // invalid
      }
      return 1; // moving up/down
    } else {
      middle = this.grid[Math.max(fromRow, toRow) - 1][Math.max(fromCol, toCol) - 1];
      if (middle != SlotState.Marble
              || Math.abs(fromRow - toRow) != 2
              || Math.abs(fromCol - toCol) != 2) {
        return 0; // invalid
      }
      return 2;
    }
  }
}


package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * represents a european model of a marble solitaire board.
 */
public class EuropeanSolitaireModel extends AMarbleSolitaireModel {

  /**
   * constructs a board with a given arm thickness and given starting point coordinates.
   *
   * @param thickness the desired arm thickness of the board
   * @param sRow      the row of the starting slot
   * @param sCol      the column of the starting slot
   * @throws IllegalArgumentException if thickness is not a positive,
   *                                  odd integer or if starting slot is an invalid position
   */
  public EuropeanSolitaireModel(int thickness, int sRow, int sCol)
          throws IllegalArgumentException {
    super(thickness, sRow, sCol);
  }

  /**
   * constructs a default board of thickness 3 with a starting point in the center.
   */
  public EuropeanSolitaireModel() {
    super(3, 3, 3);
  }

  /**
   * constructs a board with a given arm thickness and a starting point in the center.
   *
   * @param thickness the desired arm thickness of the board
   * @throws IllegalArgumentException if thickness is not a positive, odd integer
   */
  public EuropeanSolitaireModel(int thickness) throws IllegalArgumentException {
    super(thickness, thickness + Math.floorDiv(thickness, 2) - 1,
            thickness + Math.floorDiv(thickness, 2) - 1);
  }

  /**
   * constructs a board with a thickness of 3 and a given starting point position.
   *
   * @param sRow the row of the starting slot
   * @param sCol the column of the starting slot
   * @throws IllegalArgumentException if te starting slot is an invalid position
   */
  public EuropeanSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    super(3, sRow, sCol);
  }

  protected void createBoard(int sRow, int sCol) throws IllegalArgumentException {
    if (thickness % 2 == 0 || thickness <= 0) {
      throw new IllegalArgumentException("thickness must be a positive, odd integer");
    }
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        if ((y < thickness - 1 - x && x < thickness - 1)
                || (y >= thickness * 2 - 1 + x && x < thickness - 1)
                || (y <= 1 - (thickness * 2 - x) && x >= thickness * 2 - 1)
                || (y >= thickness * 3 - 4 - (x - thickness * 2) && x >= thickness * 2 - 1)) {
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
      return -1; // left/right
    } else if (fromCol == toCol) {
      middle = this.grid[Math.max(fromRow, toRow) - 1][fromCol];
      if (middle != SlotState.Marble || Math.abs(fromRow - toRow) != 2) {
        return 0;
      }
      return 1; // moving up/down
    } else {
      middle = this.grid[Math.max(fromRow, toRow) - 1][Math.max(fromCol, toCol) - 1];
      if (middle != SlotState.Marble
              || Math.abs(fromRow - toRow) != 2
              || Math.abs(fromCol - toCol) != 2) {
        return 0; // invalid
      }
      return 0; // diagonal
    }
  }
}
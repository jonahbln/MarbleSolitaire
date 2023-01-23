import org.junit.Before;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * tests for the TriangleSolitaireModel class.
 */
public class TriangleSolitaireModelTest {

  MarbleSolitaireModel board1;
  MarbleSolitaireModel board2;
  MarbleSolitaireModel board3;
  MarbleSolitaireModel board4;

  @Before
  public void init() {
    board1 = new TriangleSolitaireModel();
    board2 = new TriangleSolitaireModel(7);
    board3 = new TriangleSolitaireModel(3, 3);
    board4 = new TriangleSolitaireModel(7, 4, 4);
  }

  @org.junit.Test
  public void testConstructor() { //get hella points off for this
    // test the three constructors that can cause exceptions
    try {
      this.board2 = new EuropeanSolitaireModel(2);
      fail("Should have thrown an illegal argument exception");
    } catch (IllegalArgumentException e) {
      if (!e.getMessage().equals("thickness must be a positive, odd integer")) {
        fail();
      }
    }
    try {
      this.board3 = new EuropeanSolitaireModel(0, 1);
      fail("Should have thrown an illegal argument exception");
    } catch (IllegalArgumentException e) {
      if (!e.getMessage().equals("Invalid empty cell position (0,1)")) {
        fail();
      }
    }
    try {
      this.board4 = new EuropeanSolitaireModel(2, 5, 5);
      fail("Should have thrown an illegal argument exception");
    } catch (IllegalArgumentException e) {
      if (!e.getMessage().equals("thickness must be a positive, odd integer")) {
        fail();
      }
    }

    this.init(); // re-construct the objects for testing tall four constructors when successful

    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board1.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board1.getSlotAt(0, 4));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board2.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board2.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board2.getSlotAt(0, 6));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board3.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board3.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board3.getSlotAt(0, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board4.getSlotAt(4, 4));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board4.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board4.getSlotAt(0, 6));
  }

  @org.junit.Test
  public void validMove() {
    // test the slots before making any move
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board1.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(1, 1));

    board1.move(2, 2, 0, 0);

    // test that the slots were properly mutated by the move method
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board1.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board1.getSlotAt(1, 1));

    // test the slots before making any move
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(2, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board1.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(2, 1));

    board1.move(2, 0, 2, 2);

    // test that the slots were properly mutated by the move method
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board1.getSlotAt(2, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board1.getSlotAt(2, 1));

  }

  @org.junit.Test
  public void invalidMove() {
    // test the slots before making any move
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board1.getSlotAt(2, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board1.getSlotAt(1, 3));

    try {
      board1.move(2, 5, 3, 3); // diagonal move attempt jump too large
      fail("should have caused an error");
    } catch (IllegalArgumentException ignored) {
      try {
        board1.move(-1, 5, 3, 3); // from does not exist
        fail("should have caused an error");
      } catch (IllegalArgumentException ignored2) {
        try {
          board1.move(1, 5, -1, 3); // to does not exist
          fail("should have caused an error");
        } catch (IllegalArgumentException ignored3) {
          try {
            board1.move(0, 0, 3, 3); // from empty slot
            fail("should have caused an error");
          } catch (IllegalArgumentException ignored4) {
            try {
              board1.move(2, 2, 4, 4); // to marble slot
              fail("should have caused an error");
            } catch (IllegalArgumentException ignored5) {
              // ignored
            }
          }
        }
      }
    }
    // show that there were no slot mutations made when an invalid move is attempted
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board1.getSlotAt(2, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board1.getSlotAt(1, 3));
  }

  @org.junit.Test
  public void isGameOver() {
    // show that the game is NOT over on a newly constructed board
    assertFalse(board1.isGameOver());

    // do set of moves that will cause no more valid moves to be available
    // (this kinda tests the move method as well)
    board1.move(2, 0, 0, 0);
    board1.move(2, 2, 2, 0);
    board1.move(0, 0, 2, 2);
    board1.move(3, 0, 1, 0);
    board1.move(3, 3, 1, 1);
    board1.move(4, 1, 2, 1);
    board1.move(4, 3, 4, 1);
    board1.move(4, 0, 4, 2);
    board1.move(4, 2, 2, 2);
    board1.move(1, 1, 3, 3);
    //show again that its not over yet
    assertFalse(board1.isGameOver());
    board1.move(4, 4, 2, 2);
    board1.move(2, 2, 2, 0);
    board1.move(2, 0, 0, 0);

    // now, with no valid moves left to be made, the game should be considered over
    assertTrue(this.board1.isGameOver());
  }

  @org.junit.Test
  public void getBoardSize() {
    assertEquals(5, board1.getBoardSize());
    assertEquals(7, board2.getBoardSize());
    assertEquals(5, board3.getBoardSize());
    assertEquals(7, board4.getBoardSize());
  }

  @org.junit.Test
  public void getSlotAt() {
    // test using invalid coordinates both below and above the bounds of the board
    try {
      assertEquals(MarbleSolitaireModelState.SlotState.Empty, board3.getSlotAt(-1, 4));
      fail("should have thrown an exception for using invalid coordinates");
    } catch (IllegalArgumentException e) {
      if (!e.getMessage().equals("invalid coordinates")) {
        fail();
      }
    }
    try {
      assertEquals(MarbleSolitaireModelState.SlotState.Empty, board4.getSlotAt(57, 57));
      fail("should have thrown an exception for using invalid coordinates");
    } catch (IllegalArgumentException e) {
      if (!e.getMessage().equals("invalid coordinates")) {
        fail();
      }
    }

    // test for valid slots of each slotstate type in various boards
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, board1.getSlotAt(1, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, board1.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, board2.getSlotAt(0, 0));
  }

  @org.junit.Test
  public void getScore() {
    assertEquals(27, board2.getScore());

    // get score before making any changes to the board
    assertEquals(14, board1.getScore());
    // make two moves
    this.board1.move(2, 2, 0, 0);
    // see that the score has changed
    assertEquals(13, board1.getScore());
  }
}
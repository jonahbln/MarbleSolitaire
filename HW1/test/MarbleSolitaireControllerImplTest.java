import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;

import static org.junit.Assert.assertEquals;

/**
 * tests for the MarbleSolitaireControllerImpl class.
 */
public class MarbleSolitaireControllerImplTest {
  MarbleSolitaireControllerImpl controller1;
  MarbleSolitaireTextView view1;
  MarbleSolitaireModel model1;

  MarbleSolitaireControllerImpl controller2;
  MarbleSolitaireTextView view2;
  MarbleSolitaireModel model2;

  MarbleSolitaireControllerImpl controller3;
  TriangleSolitaireTextView view3;
  MarbleSolitaireModel model3;
  Appendable append1;
  Appendable append2;
  Appendable append3;

  @Before
  public void init() {
    this.model1 = new EnglishSolitaireModel();
    this.append1 = new StringBuilder();
    this.view1 = new MarbleSolitaireTextView(model1, this.append1);

    this.model2 = new EuropeanSolitaireModel();
    this.append2 = new StringBuilder();
    this.view2 = new MarbleSolitaireTextView(model2, this.append2);

    this.model3 = new TriangleSolitaireModel();
    this.append3 = new StringBuilder();
    this.view3 = new TriangleSolitaireTextView(model3, this.append3);
  }

  @Test
  public void testInvalidConstruct() {
    try {
      this.controller1 = new MarbleSolitaireControllerImpl(null, this.view1, new StringReader(""));
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }
    try {
      this.controller1 = new MarbleSolitaireControllerImpl(this.model1, null, new StringReader(""));
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }
    try {
      this.controller1 = new MarbleSolitaireControllerImpl(this.model1, this.view1, null);
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }

    try {
      this.controller2 = new MarbleSolitaireControllerImpl(null, this.view2, new StringReader(""));
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }
    try {
      this.controller2 = new MarbleSolitaireControllerImpl(this.model2, null, new StringReader(""));
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }
    try {
      this.controller2 = new MarbleSolitaireControllerImpl(this.model2, this.view2, null);
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }

    try {
      this.controller3 = new MarbleSolitaireControllerImpl(null, this.view3, new StringReader(""));
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }
    try {
      this.controller3 = new MarbleSolitaireControllerImpl(this.model3, null, new StringReader(""));
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }
    try {
      this.controller3 = new MarbleSolitaireControllerImpl(this.model3, this.view3, null);
    } catch (IllegalArgumentException e) {
      assertEquals("one or more parameters is null!", e.getMessage());
    }
  }

  @Test
  public void testIllegalState() {
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader(""));
    try {
      this.controller1.playGame();
    } catch (IllegalStateException e) {
      assertEquals("Readable has no inputs to use!", e.getMessage());
    }

    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("1 2 3"));
    try {
      this.controller1.playGame();
    } catch (IllegalStateException e) {
      assertEquals("Readable ran out of inputs!", e.getMessage());
    }

    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader(""));
    try {
      this.controller2.playGame();
    } catch (IllegalStateException e) {
      assertEquals("Readable has no inputs to use!", e.getMessage());
    }

    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("1 2 3"));
    try {
      this.controller3.playGame();
    } catch (IllegalStateException e) {
      assertEquals("Readable ran out of inputs!", e.getMessage());
    }
  }

  @Test
  public void testIOException() {
    this.view1 = new MarbleSolitaireTextView(this.model1, new FaultyAppendable());
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("2 4 4 4 q"));
    try {
      this.controller1.playGame();
    } catch (IllegalStateException e) {
      assertEquals("I/O exception occurred!", e.getMessage());
    }

    this.view2 = new MarbleSolitaireTextView(this.model2, new FaultyAppendable());
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("2 4 4 4 q"));
    try {
      this.controller2.playGame();
    } catch (IllegalStateException e) {
      assertEquals("I/O exception occurred!", e.getMessage());
    }

    this.view3 = new TriangleSolitaireTextView(this.model3, new FaultyAppendable());
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("2 4 4 4 q"));
    try {
      this.controller3.playGame();
    } catch (IllegalStateException e) {
      assertEquals("I/O exception occurred!", e.getMessage());
    }
  }

  @Test
  public void testQuitEnglish() {
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("2 4 4 4 Q"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 31\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 31\n", this.append1.toString());

    this.init();
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("2 salkjbd 1 q 3 4"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n", this.append1.toString());

    this.init();
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("2 salkjbd q 1 3 4"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n", this.append1.toString());

    this.init();
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("q salkjbd 1 2 3 4"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n", this.append1.toString());

    this.init();
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("2 salkjbd 1 3 q 4"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n", this.append1.toString());

    this.init();
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("2 salkjbd 1 3 q"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n", this.append1.toString());
  }

  @Test
  public void testQuitEuropean() {
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("2 4 4 4 Q"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n"
                    + "    O O O\n"
                    + "  O O _ O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 35\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "  O O _ O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 35\n", this.append2.toString());

    this.init();
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("2 salkjbd 1 q 3 4"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n", this.append2.toString());

    this.init();
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("2 salkjbd q 1 3 4"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n", this.append2.toString());

    this.init();
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("q salkjbd 1 2 3 4"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n", this.append2.toString());

    this.init();
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("2 salkjbd 1 3 q 4"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n", this.append2.toString());

    this.init();
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("2 salkjbd 1 3 q"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n", this.append2.toString());
  }

  @Test
  public void testQuitTriangular() {
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("Q"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n", this.append3.toString());

    this.init();
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("2 salkjbd 1 q 3 4"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n", this.append3.toString());

    this.init();
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("2 salkjbd q 1 3 4"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n", this.append3.toString());

    this.init();
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("q salkjbd 1 2 3 4"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n", this.append3.toString());

    this.init();
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("2 salkjbd 1 3 q 4"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n", this.append3.toString());

    this.init();
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("2 salkjbd 1 3 q"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n", this.append3.toString());
  }

  // tests the controller's handling of invalid inputs
  // both strings and negative numbers
  @Test
  public void testInvalidInputEnglish() {
    this.init();
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("2 4 invalid 4 4 q"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 31\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 31\n", this.append1.toString());

    this.init();
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1, new StringReader("2 4 -1 4 4 q"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "Invalid move. Play again. Integer values must be greater than 0!\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 31\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 31\n", this.append1.toString());
  }

  // tests the controller's handling of invalid inputs
  // both strings and negative numbers
  @Test
  public void testInvalidInputEuropean() {
    this.init();
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("2 4 invalid 4 4 q"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "    O O O\n"
                    + "  O O _ O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 35\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "  O O _ O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 35\n", this.append2.toString());

    this.init();
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2, new StringReader("2 4 -1 4 4 q"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 36\n"
                    + "Invalid move. Play again. Integer values must be greater than 0!\n"
                    + "    O O O\n"
                    + "  O O _ O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 35\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O O O\n"
                    + "  O O _ O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O\n"
                    + "Score: 35\n", this.append2.toString());
  }

  // tests the controller's handling of invalid inputs
  // both strings and negative numbers
  @Test
  public void testInvalidInputTriangular() {
    this.init();
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("3 3 invalid 1 1 q"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "Invalid move. Play again. Values must be an integer or 'q' to quit!\n"
                    + "    O\n"
                    + "   O _\n"
                    + "  O O _\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 13\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O\n"
                    + "   O _\n"
                    + "  O O _\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 13\n", this.append3.toString());

    this.init();
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3, new StringReader("3 3 -1 1 1 q"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "Invalid move. Play again. Integer values must be greater than 0!\n"
                    + "    O\n"
                    + "   O _\n"
                    + "  O O _\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 13\n"
                    + "Game quit!\n"
                    + "State of game when quit:\n"
                    + "    O\n"
                    + "   O _\n"
                    + "  O O _\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 13\n", this.append3.toString());
  }

  @Test
  public void testGameOverEnglish() {
    this.init();
    this.controller1 = new MarbleSolitaireControllerImpl(
            this.model1, this.view1,
            new StringReader("2 4 4 4 5 4 3 4 7 4 5 4 4 2 4 4 4 5 4 3 4 7 4 5"));
    this.controller1.playGame();
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 32\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 31\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O _ O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "Score: 30\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O _ O\n"
                    + "    O _ O\n"
                    + "Score: 29\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O O O O O\n"
                    + "O _ _ O O O O\n"
                    + "O O O O O O O\n"
                    + "    O _ O\n"
                    + "    O _ O\n"
                    + "Score: 28\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O O O O O\n"
                    + "O _ O _ _ O O\n"
                    + "O O O O O O O\n"
                    + "    O _ O\n"
                    + "    O _ O\n"
                    + "Score: 27\n"
                    + "Game over!\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O O O O O\n"
                    + "O _ O _ O _ _\n"
                    + "O O O O O O O\n"
                    + "    O _ O\n"
                    + "    O _ O\n"
                    + "Score: 26\n", this.append1.toString());
  }

  @Test
  public void testGameOverTriangular() {
    this.init();
    this.controller3 = new MarbleSolitaireControllerImpl(
            this.model3, this.view3,
            new StringReader(
                    "3 1 1 1 3 3 3 1 1 1 3 3 4 1 2 1 4 4 2" +
                            " 2 5 2 3 2 5 4 5 2 5 1 5 3 5 3 3 3 2 2 4 4 5 5 3 3 3 3 3 1 3 1 1 1"));
    this.controller3.playGame();
    assertEquals(
            "    _\n"
                    + "   O O\n"
                    + "  O O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 14\n"
                    + "    O\n"
                    + "   _ O\n"
                    + "  _ O O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 13\n"
                    + "    O\n"
                    + "   _ O\n"
                    + "  O _ _\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 12\n"
                    + "    _\n"
                    + "   _ _\n"
                    + "  O _ O\n"
                    + " O O O O\n"
                    + "O O O O O\n"
                    + "Score: 11\n"
                    + "    _\n"
                    + "   O _\n"
                    + "  _ _ O\n"
                    + " _ O O O\n"
                    + "O O O O O\n"
                    + "Score: 10\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  _ _ _\n"
                    + " _ O O _\n"
                    + "O O O O O\n"
                    + "Score: 9\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  _ O _\n"
                    + " _ _ O _\n"
                    + "O _ O O O\n"
                    + "Score: 8\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  _ O _\n"
                    + " _ _ O _\n"
                    + "O O _ _ O\n"
                    + "Score: 7\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  _ O _\n"
                    + " _ _ O _\n"
                    + "_ _ O _ O\n"
                    + "Score: 6\n"
                    + "    _\n"
                    + "   O O\n"
                    + "  _ O O\n"
                    + " _ _ _ _\n"
                    + "_ _ _ _ O\n"
                    + "Score: 5\n"
                    + "    _\n"
                    + "   O _\n"
                    + "  _ O _\n"
                    + " _ _ _ O\n"
                    + "_ _ _ _ O\n"
                    + "Score: 4\n"
                    + "    _\n"
                    + "   O _\n"
                    + "  _ O O\n"
                    + " _ _ _ _\n"
                    + "_ _ _ _ _\n"
                    + "Score: 3\n"
                    + "    _\n"
                    + "   O _\n"
                    + "  O _ _\n"
                    + " _ _ _ _\n"
                    + "_ _ _ _ _\n"
                    + "Score: 2\n"
                    + "Game over!\n"
                    + "    O\n"
                    + "   _ _\n"
                    + "  _ _ _\n"
                    + " _ _ _ _\n"
                    + "_ _ _ _ _\n"
                    + "Score: 1\n"
            , this.append3.toString());
  }

  @Test
  public void testGameOverEuropean() {
    this.model2 = new EuropeanSolitaireModel(6, 2);
    this.view2 = new MarbleSolitaireTextView(this.model2, this.append2);
    this.controller2 = new MarbleSolitaireControllerImpl(
            this.model2, this.view2,
            new StringReader(
                    "5 3 7 3 5 5 5 3 7 5 5 5 7 4 5 4 4 3 6 3 7 3 5 3 "
                            + "4 1 4 3 6 2 4 2 5 4 5 2 5 1 5 3 3 2 5 2 5 2 5 4 "
                            + "3 4 3 2 3 1 3 3 1 4 3 4 2 2 2 4 4 3 2 3 1 3 3 3 "
                            + "2 5 2 3 4 5 2 5 3 7 3 5 4 7 4 5 6 6 4 6 5 4 5 6 "
                            + "5 7 5 5 3 4 3 6 3 6 5 6 5 6 5 4 5 4 3 4 3 3 3 5 "
                            + "2 6 2 4 4 5 2 5 1 5 3 5 2 3 2 5 2 5 4 5"));
    this.controller2.playGame();
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    _ O O\n"
                    + "Score: 36\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O _ O O O O\n"
                    + "  O _ O O O\n"
                    + "    O O O\n"
                    + "Score: 35\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ _ O O\n"
                    + "  O _ O O O\n"
                    + "    O O O\n"
                    + "Score: 34\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "  O _ O _ O\n"
                    + "    O O _\n"
                    + "Score: 33\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O _ _ _ O\n"
                    + "    O _ _\n"
                    + "Score: 32\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O _ O O O O\n"
                    + "O O _ O O O O\n"
                    + "  O O _ _ O\n"
                    + "    O _ _\n"
                    + "Score: 31\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O _ O O O O\n"
                    + "O O O O O O O\n"
                    + "  O _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 30\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "_ _ O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 29\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "_ O O O O O O\n"
                    + "O _ O O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 28\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "_ O O O O O O\n"
                    + "O O _ _ O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 27\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "_ O O O O O O\n"
                    + "_ _ O _ O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 26\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O _ O O O O O\n"
                    + "_ _ O O O O O\n"
                    + "_ O O _ O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 25\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O _ O O O O O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 24\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "O O _ _ O O O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 23\n"
                    + "    O O O\n"
                    + "  O O O O O\n"
                    + "_ _ O _ O O O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 22\n"
                    + "    O _ O\n"
                    + "  O O _ O O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 21\n"
                    + "    O _ O\n"
                    + "  _ _ O O O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 20\n"
                    + "    O _ O\n"
                    + "  _ O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 19\n"
                    + "    _ _ O\n"
                    + "  _ _ O O O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 18\n"
                    + "    _ _ O\n"
                    + "  _ O _ _ O\n"
                    + "_ _ O O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 17\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O O _ O O\n"
                    + "_ _ _ O _ O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 16\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O O O _ _\n"
                    + "_ _ _ O _ O O\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 15\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O O O _ _\n"
                    + "_ _ _ O O _ _\n"
                    + "_ _ _ O O O O\n"
                    + "  _ _ _ _ O\n"
                    + "    _ _ _\n"
                    + "Score: 14\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O O O _ _\n"
                    + "_ _ _ O O O _\n"
                    + "_ _ _ O O _ O\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 13\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O O O _ _\n"
                    + "_ _ _ O O O _\n"
                    + "_ _ _ _ _ O O\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 12\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O O O _ _\n"
                    + "_ _ _ O O O _\n"
                    + "_ _ _ _ O _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 11\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O _ _ O _\n"
                    + "_ _ _ O O O _\n"
                    + "_ _ _ _ O _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 10\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O _ _ _ _\n"
                    + "_ _ _ O O _ _\n"
                    + "_ _ _ _ O O _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 9\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O _ _ _ _\n"
                    + "_ _ _ O O _ _\n"
                    + "_ _ _ O _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 8\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ O O _ _ _\n"
                    + "_ _ _ _ O _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 7\n"
                    + "    _ _ O\n"
                    + "  _ O _ O O\n"
                    + "_ _ _ _ O _ _\n"
                    + "_ _ _ _ O _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 6\n"
                    + "    _ _ O\n"
                    + "  _ O O _ _\n"
                    + "_ _ _ _ O _ _\n"
                    + "_ _ _ _ O _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 5\n"
                    + "    _ _ O\n"
                    + "  _ O O O _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 4\n"
                    + "    _ _ _\n"
                    + "  _ O O _ _\n"
                    + "_ _ _ _ O _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 3\n"
                    + "    _ _ _\n"
                    + "  _ _ _ O _\n"
                    + "_ _ _ _ O _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 2\n"
                    + "Game over!\n"
                    + "    _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "_ _ _ _ O _ _\n"
                    + "_ _ _ _ _ _ _\n"
                    + "  _ _ _ _ _\n"
                    + "    _ _ _\n"
                    + "Score: 1\n"
            , this.append2.toString());
  }

  private class FaultyAppendable implements Appendable {
    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("testing purpose exception");
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("testing purpose exception");
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("testing purpose exception");
    }
  }
}
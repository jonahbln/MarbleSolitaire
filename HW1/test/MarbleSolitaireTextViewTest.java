import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;

import static org.junit.Assert.assertEquals;


/**
 * tests for the MarbleSolitaireTextView class.
 */
public class MarbleSolitaireTextViewTest {

  MarbleSolitaireTextView view1;
  MarbleSolitaireModel model1;
  MarbleSolitaireTextView view2;
  MarbleSolitaireModel model2;
  MarbleSolitaireTextView view3;
  MarbleSolitaireTextView euview1;
  MarbleSolitaireModel eumodel1;
  MarbleSolitaireTextView euview2;
  MarbleSolitaireModel eumodel2;
  MarbleSolitaireTextView euview3;
  StringBuilder builder;
  StringBuilder builder2;

  @Before
  public void init() {
    builder = new StringBuilder();
    builder2 = new StringBuilder();
    this.model1 = new EnglishSolitaireModel();
    this.view1 = new MarbleSolitaireTextView(model1);
    this.model2 = new EnglishSolitaireModel(5);
    this.view2 = new MarbleSolitaireTextView(model2);
    this.view3 = new MarbleSolitaireTextView(model1, builder);
    this.eumodel1 = new EuropeanSolitaireModel();
    this.euview1 = new MarbleSolitaireTextView(eumodel1);
    this.eumodel2 = new EuropeanSolitaireModel(5);
    this.euview2 = new MarbleSolitaireTextView(eumodel2);
    this.euview3 = new MarbleSolitaireTextView(eumodel1, builder2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidSingleParam() {
    this.view3 = new MarbleSolitaireTextView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidTwoParam() {
    this.view3 = new MarbleSolitaireTextView(null, System.out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidTwoParam2() {
    this.view3 = new MarbleSolitaireTextView(this.model1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidTwoParam3() {
    this.view3 = new MarbleSolitaireTextView(null, null);
  }

  @Test
  public void testToString() {
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O"
            , this.view1.toString());

    assertEquals(
              "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O _ O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O"
            , this.view2.toString());

    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O"
            , this.euview1.toString());

    assertEquals(
            "        O O O O O\n"
                    + "      O O O O O O O\n"
                    + "    O O O O O O O O O\n"
                    + "  O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O _ O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "  O O O O O O O O O O O\n"
                    + "    O O O O O O O O O\n"
                    + "      O O O O O O O\n"
                    + "        O O O O O"
            , this.euview2.toString());

    // make a move then test again
    this.model1.move(1, 3, 3, 3);
    assertEquals(
            "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O"
            , this.view1.toString());

    // make a move then test again
    this.eumodel1.move(1, 3, 3, 3);
    assertEquals(
            "    O O O\n"
                    + "  O O _ O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O"
            , this.euview1.toString());
  }

  @Test
  public void testIOEException() {
    this.view3 = new MarbleSolitaireTextView(this.model1, new FaultyAppendable());
    try {
      this.view3.renderBoard();
    } catch (IOException e) {
      assertEquals("testing purpose exception", e.getMessage());
    }
    try {
      this.view3.renderMessage("fundies 3");
    } catch (IOException e) {
      assertEquals("testing purpose exception", e.getMessage());
    }
  }

  @Test
  public void renderBoard() {
    // show that the appendable is empty
    assertEquals("", this.builder.toString());
    try {
      // run the render board method
      this.view3.renderBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // show that the current board state has been appended to the appendable object
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O"
            , this.builder.toString());
    this.model1.move(1, 3, 3, 3);
    try {
      this.view3.renderMessage("\n");
      // run the render board method
      this.view3.renderBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // show that the current board state has been appended to the appendable object
    assertEquals(
            "    O O O\n"
                    + "    O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "    O O O\n"
                    + "    O _ O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "O O O O O O O\n"
                    + "    O O O\n"
                    + "    O O O"
            , this.builder.toString());

    // show that the appendable is empty
    assertEquals("", this.builder2.toString());
    try {
      // run the render board method
      this.euview3.renderBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // show that the current board state has been appended to the appendable object
    assertEquals(
            "    O O O\n"
                    + "  O O O O O\n"
                    + "O O O O O O O\n"
                    + "O O O _ O O O\n"
                    + "O O O O O O O\n"
                    + "  O O O O O\n"
                    + "    O O O"
            , this.builder2.toString());
  }

  @Test
  public void renderMessage() {
    // show that the appendable is empty
    assertEquals("", this.builder.toString());
    try {
      // run the render board method
      this.view3.renderMessage("heyyyy");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // show that the current board state has been appended to the appendable object
    assertEquals(
            "heyyyy"
            , this.builder.toString());
    try {
      // run the render board method
      this.view3.renderMessage(" hi.");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // show that the current board state has been appended to the appendable object
    assertEquals(
            "heyyyy hi."
            , this.builder.toString());
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
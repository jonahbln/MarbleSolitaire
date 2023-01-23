import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;

import static org.junit.Assert.assertEquals;


/**
 * tests for the TriangleSolitaireTextView class.
 */
public class TriangleSolitaireTextViewTest {

  StringBuilder builder;
  TriangleSolitaireModel model1;
  TriangleSolitaireTextView view1;

  @Before
  public void init() {
    builder = new StringBuilder();
    this.model1 = new TriangleSolitaireModel(7, 2, 2);
    this.view1 = new TriangleSolitaireTextView(this.model1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidSingleParam() {
    this.view1 = new TriangleSolitaireTextView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidTwoParam() {
    this.view1 = new TriangleSolitaireTextView(null, System.out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidTwoParam2() {
    this.view1 = new TriangleSolitaireTextView(this.model1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidTwoParam3() {
    this.view1 = new TriangleSolitaireTextView(null, null);
  }

  @Test
  public void testToString() {
    assertEquals(
              "      O\n"
                    + "     O O\n"
                    + "    O O _\n"
                    + "   O O O O\n"
                    + "  O O O O O\n"
                    + " O O O O O O\n"
                    + "O O O O O O O"
            , this.view1.toString());


    // make a move then test again
    this.model1.move(0, 0, 2, 2);
    assertEquals(
            "      _\n"
                    + "     O _\n"
                    + "    O O O\n"
                    + "   O O O O\n"
                    + "  O O O O O\n"
                    + " O O O O O O\n"
                    + "O O O O O O O"
            , this.view1.toString());
  }

  @Test
  public void testIOEException() {
    this.view1 = new TriangleSolitaireTextView(this.model1, new FaultyAppendable());
    try {
      this.view1.renderBoard();
    } catch (IOException e) {
      assertEquals("testing purpose exception", e.getMessage());
    }
    try {
      this.view1.renderMessage("fundies 3");
    } catch (IOException e) {
      assertEquals("testing purpose exception", e.getMessage());
    }
  }

  @Test
  public void renderBoard() {
    this.view1 = new TriangleSolitaireTextView(this.model1, this.builder);
    // show that the appendable is empty
    assertEquals("", this.builder.toString());
    try {
      // run the render board method
      this.view1.renderBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // show that the current board state has been appended to the appendable object
    assertEquals(
            "      O\n"
                    + "     O O\n"
                    + "    O O _\n"
                    + "   O O O O\n"
                    + "  O O O O O\n"
                    + " O O O O O O\n"
                    + "O O O O O O O"
            , this.builder.toString());
    this.model1.move(0, 0, 2, 2);
    try {
      this.view1.renderMessage("\n");
      // run the render board method
      this.view1.renderBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // show that the current board state has been appended to the appendable object
    assertEquals(
            "      O\n"
                    + "     O O\n"
                    + "    O O _\n"
                    + "   O O O O\n"
                    + "  O O O O O\n"
                    + " O O O O O O\n"
                    + "O O O O O O O\n"
                    + "      _\n"
                    + "     O _\n"
                    + "    O O O\n"
                    + "   O O O O\n"
                    + "  O O O O O\n"
                    + " O O O O O O\n"
                    + "O O O O O O O"
            , this.builder.toString());
  }

  @Test
  public void renderMessage() {
    this.view1 = new TriangleSolitaireTextView(this.model1, this.builder);
    // show that the appendable is empty
    assertEquals("", this.builder.toString());
    try {
      // run the render board method
      this.view1.renderMessage("heyyyy");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // show that the current board state has been appended to the appendable object
    assertEquals(
            "heyyyy"
            , this.builder.toString());
    try {
      // run the render board method
      this.view1.renderMessage(" hi.");
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
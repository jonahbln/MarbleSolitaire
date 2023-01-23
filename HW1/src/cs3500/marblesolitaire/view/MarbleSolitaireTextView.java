package cs3500.marblesolitaire.view;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * object to represent a viewable marble solitaire board using text only.
 */
public class MarbleSolitaireTextView implements MarbleSolitaireView {
  protected final MarbleSolitaireModelState model;
  protected final Appendable appendable;

  /**
   * constructs a viewable object from a given model.
   *
   * @param model      the model to be viewed
   * @param appendable the appendable object destination.
   * @throws IllegalArgumentException if the model or appendable is null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState model, Appendable appendable)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    if (appendable == null) {
      throw new IllegalArgumentException("appendable object cannot be null");
    }
    this.model = model;
    this.appendable = appendable;
  }

  /**
   * constructs a viewable object from a given model.
   *
   * @param model the model to be viewed
   * @throws IllegalArgumentException if the model is null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState model) throws IllegalArgumentException {
    this(model, System.out);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int x = 0; x < model.getBoardSize(); x++) {
      for (int y = 0; y < model.getBoardSize(); y++) {
        if (model.getSlotAt(x, y) == MarbleSolitaireModelState.SlotState.Marble) {
          s.append("O");
          if (y < model.getBoardSize() - 1) {
            s.append(" ");
          } else {
            s.append("\n");
          }
        } else if (model.getSlotAt(x, y) == MarbleSolitaireModelState.SlotState.Empty) {
          s.append("_");
          if (y < model.getBoardSize() - 1) {
            s.append(" ");
          } else {
            s.append("\n");
          }
        } else if (y > (model.getBoardSize() - ((model.getBoardSize() + 2) / 2)) - 1) {
          if (x == model.getBoardSize() - 1) {
            s.deleteCharAt(s.length() - 1);
            break;
          }
          s.deleteCharAt(s.length() - 1);
          s.append("\n");
          break;
        } else {
          s.append("  ");
        }

      }
    }
    return s.toString();
  }

  @Override
  public void renderBoard() throws IOException {
    this.appendable.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.appendable.append(message);
  }
}

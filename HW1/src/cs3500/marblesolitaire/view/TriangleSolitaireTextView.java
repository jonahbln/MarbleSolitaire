package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * represents a triangular text view of a marble solitaire board.
 */
public class TriangleSolitaireTextView extends MarbleSolitaireTextView {
  /**
   * constructs a viewable object from a given model.
   *
   * @param model      the model to be viewed
   * @param appendable the appendable object destination.
   * @throws IllegalArgumentException if the model or appendable is null
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model, Appendable appendable)
          throws IllegalArgumentException {
    super(model, appendable);
  }

  /**
   * constructs a viewable object from a given model.
   *
   * @param model the model to be viewed
   * @throws IllegalArgumentException if the model is null
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model)
          throws IllegalArgumentException {
    super(model, System.out);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int x = 0; x < model.getBoardSize(); x++) {
      for (int i = 0; i < model.getBoardSize() - x - 1; i++) {
        s.append(" ");
      }
      for (int y = 0; y < model.getBoardSize(); y++) {
        if (model.getSlotAt(x, y) == MarbleSolitaireModelState.SlotState.Marble) {

          s.append("O");
          if (y < x) {
            s.append(" ");
          }
        } else if (model.getSlotAt(x, y) == MarbleSolitaireModelState.SlotState.Empty) {
          s.append("_");
          if (y < x) {
            s.append(" ");
          }
        }
      }
      if (x < model.getBoardSize() - 1) {
        s.append("\n");
      }
    }
    return s.toString();
  }
}

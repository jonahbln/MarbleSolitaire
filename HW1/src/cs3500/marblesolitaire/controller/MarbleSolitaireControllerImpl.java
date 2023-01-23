package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * game controller that allows a user to play a game of marble solitaire using any
 * given implementation of the model and view.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private final MarbleSolitaireModel model;
  private final MarbleSolitaireView view;
  private final Readable reader;

  /**
   * constructs a marble solitaire controller.
   *
   * @param model    the model being used
   * @param view     the view of said model
   * @param readable a readable object from which to read input
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view,
                                       Readable readable) throws IllegalArgumentException {
    if (model == null || view == null || readable == null) {
      throw new IllegalArgumentException("one or more parameters is null!");
    }
    this.model = model;
    this.view = view;
    this.reader = readable;
  }

  @Override
  public void playGame() throws IllegalStateException {
    render();

    Scanner scanner = new Scanner(this.reader);

    if (!scanner.hasNext()) {
      throw new IllegalStateException("Readable has no inputs to use!");
    }

    int[] a = new int[4];
    int i = 0;
    boolean nextTnt;
    int input;
    String nextVal;

    while (scanner.hasNext()) {
      nextTnt = scanner.hasNextInt();
      nextVal = scanner.next();

      if (nextTnt) {
        input = Integer.parseInt(nextVal) - 1;
        if (input >= 0) {
          a[i] = input;
          i++;
        } else {
          sendMessage("Invalid move. Play again. Integer values must be greater than 0!");
        }
      } else if (nextVal.equalsIgnoreCase("q")) {
        sendMessage("Game quit!\nState of game when quit:");
        render();
        return;
      } else {
        sendMessage("Invalid move. Play again. Values must be an integer or 'q' to quit!");
      }

      if (i > 3) {
        try {
          this.model.move(a[0], a[1], a[2], a[3]);
          if (gameOver()) {
            return;
          } else {
            render();
          }
        } catch (IllegalArgumentException e) {
          sendMessage(e.getMessage());
        }

        a = new int[4];
        i = 0;
      }
    }
    throw new IllegalStateException("Readable ran out of inputs!");
  }

  // renders the current board state and prints the score
  private void render() throws IllegalStateException {
    try {
      this.view.renderBoard();
      this.view.renderMessage("\nScore: " + this.model.getScore() + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("I/O exception occurred!");
    }
  }

  // attempts to render a given message to the view
  // throws an illegal state exception if an I/O exception occurs
  private void sendMessage(String msg) throws IllegalStateException {
    try {
      this.view.renderMessage(msg + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("I/O exception occurred!");
    }
  }

  // checks if the game is over
  // if true, prints game over and renders board, then returns true
  // if false, just returns false
  private boolean gameOver() throws IllegalStateException {
    if (this.model.isGameOver()) {
      sendMessage("Game over!");
      render();
      return true;
    }
    return false;
  }
}

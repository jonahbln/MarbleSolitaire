package cs3500.marblesolitaire.controller;

/**
 * represents a controller of a marble solitaire game that allows a user to play a game with any
 * given model and view of the game.
 */
public interface MarbleSolitaireController {
  /**
   * plays a new game of marble solitaire.
   *
   * @throws IllegalStateException if the controller is unable to successfully read
   *                               input or transmit output.
   */
  public void playGame() throws IllegalStateException;
}

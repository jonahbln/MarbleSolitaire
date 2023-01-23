package cs3500.marblesolitaire;

import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;

/**
 * main class provided to the user to play a game of marble solitaire.
 */
public final class MarbleSolitaire {
  /**
   * main method that takes in up to three command line inputs.
   * First, the required input is the type of marble solitaire game:
   * english
   * european
   * triangular
   * Second, you may enter '-size' followed by an integer representing the board size
   * Third, you may enter '-hole' followed by two integers representing the starting empty slot
   *
   * @param args input parameters given by user.
   */
  public static void main(String[] args) {
    MarbleSolitaireControllerImpl controller;
    MarbleSolitaireView view;
    MarbleSolitaireModel model;
    int size = -1;
    int row = -1;
    int col = -1;

    int i = 0;
    for (String s : args) {
      if (s.equalsIgnoreCase("-size")) {
        size = Integer.parseInt(args[i + 1]);
      }
      if (s.equalsIgnoreCase("-hole")) {
        row = Integer.parseInt(args[i + 1]);
        col = Integer.parseInt(args[i + 2]);
      }
      i++;
    }

    if (size == -1) {
      if (row == -1) {
        if (args[0].equalsIgnoreCase("english")) {
          model = new EnglishSolitaireModel();
          view = new MarbleSolitaireTextView(model);
        } else if (args[0].equalsIgnoreCase("european")) {
          model = new EuropeanSolitaireModel();
          view = new MarbleSolitaireTextView(model);
        } else {
          model = new TriangleSolitaireModel();
          view = new TriangleSolitaireTextView(model);
        }
      } else {
        if (args[0].equalsIgnoreCase("english")) {
          model = new EnglishSolitaireModel(row, col);
          view = new MarbleSolitaireTextView(model);
        } else if (args[0].equalsIgnoreCase("european")) {
          model = new EuropeanSolitaireModel(row, col);
          view = new MarbleSolitaireTextView(model);
        } else {
          model = new TriangleSolitaireModel(row, col);
          view = new TriangleSolitaireTextView(model);
        }
      }
    } else if (row == -1) {
      if (args[0].equalsIgnoreCase("english")) {
        model = new EnglishSolitaireModel(size);
        view = new MarbleSolitaireTextView(model);
      } else if (args[0].equalsIgnoreCase("european")) {
        model = new EuropeanSolitaireModel(size);
        view = new MarbleSolitaireTextView(model);
      } else {
        model = new TriangleSolitaireModel(size);
        view = new TriangleSolitaireTextView(model);
      }
    } else {
      if (args[0].equalsIgnoreCase("english")) {
        model = new EnglishSolitaireModel(size, row, col);
        view = new MarbleSolitaireTextView(model);
      } else if (args[0].equalsIgnoreCase("european")) {
        model = new EuropeanSolitaireModel(size, row, col);
        view = new MarbleSolitaireTextView(model);
      } else {
        model = new TriangleSolitaireModel(size, row, col);
        view = new TriangleSolitaireTextView(model);
      }
    }


    controller = new MarbleSolitaireControllerImpl(model, view,
            new InputStreamReader(System.in));
    controller.playGame();
  }
}

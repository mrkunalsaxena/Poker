/************************************************************************
* Name:        Poker                                                    *
* Author:      TJ Schmid & Kunal Saxena                                 *
* Date:        December 14, 2009                                        *
* Purpose:     Creates an interactive 5-Card Poker game with Graphics   *
*************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Poker extends JPanel {
  static Image cards[] = new Image[54];   // array of card images
  static Toolkit tk = Toolkit.getDefaultToolkit();
  Image bgImage = tk.getImage(getClass().getResource("/images/background.jpg"));    // image displayed while play occurs
  Image bgImage2 = tk.getImage(getClass().getResource("/images/background2.jpg"));  // image displayed while on menu
  Image bgImage3 = tk.getImage(getClass().getResource("/images/background3.jpg"));  // image displayed while on instructions
  Image bgImage4 = tk.getImage(getClass().getResource("/images/background4.jpg"));  // image displayed while on rank of hand screen
  Image bgImage5 = tk.getImage(getClass().getResource("/images/background5.jpg"));  // image displayed while on winner screen
  static JPanel panel;                    // main drawing panel
  static JFrame frame;                    // window frame which contains the panel
  static final int WINDOW_WIDTH = 800;    // width of display window
  static final int WINDOW_HEIGHT = 600;   // height of display window

  static int gameStage = 0;               // stages of game
  static final int WELCOME_SCREEN = 0;    // welcome screen stage of game
  static final int MENU = 1;              // menu stage of game
  static final int INSTRUCTIONS = 2;      // intructions stage of game
  static final int RANKOFHANDS = 3;       // rank of hands stage of game
  static final int PLAY = 4;              // play stage of game
  static final int END_GAME = 5;          // end of game stage of game
  static final int WINNER = 6;            // winner stage of game

  static boolean waitingForKeyPress = false;     // true when we are waiting for a user to make a specific choice
  static int numPlayers = 0;                     // number of players
  static double runningTotal = 0;                // runningTotal of game
  static int turn = 0;                           // current turn of game (0-9)

  static String playOutput = "";                 // output to panel
  static String playOutput2 = "";                // secondary output to panel

  private static Scanner kb = new Scanner(System.in);
  private static Hand hand;                               // creates Player 1's Hand
  private static Hand hand2;                              // creates Player 2's Hand
  private static Deck deck;                               // creates the Deck
  public static int handRank = 0;                         // Numerical Value of hand's Rank
  public static int handRank2 = 0;                        // Numerical Value of hand2's Rank
  public static boolean twoPlayer = false;                // Two-Player game or not
  public static String player1 = "";                      // Player 1's Name
  public static String player2 = "Computer";              // Player 2's Name
  static int playerOneGraphicHand [] = new int[5];        // Index of player 1's cards to display on screen
  static int playerTwoGraphicHand [] = new int[5];        // Index of player 2's cards to display on screen

  // start main program
  public static void main (String args[]) {

      // Load pics array with images
      for ( int i = 1; i < 53; i ++ )
          cards[i] = tk.getImage(Poker.class.getResource("/cards/" + i + ".png"));


      // Create Frame and Panel to display graphics in
      panel = new Poker();
      panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));  // set size of application window
      frame = new JFrame ("Poker");  // set title of window
      frame.add (panel);

      // add a key input listener (defined below) to our canvas so we can respond to key pressed
      frame.addKeyListener(new KeyInputHandler());

      // request the focus so key events come to the frame
      frame.requestFocus();
      frame.pack();
      frame.setVisible(true);

  } // main

  /*
  paintComponent gets called whenever panel.repaint() is
  called or when frame.pack()/frame.show() is called. It paints
  to the screen.  Since we want to paint different things
  depending on what stage of the game we are in, a variable
  "gamestage" will keep track of this.
  */
  public void paintComponent(Graphics g) {
       super.paintComponent(g);   // calls the paintComponent method of JPanel to display the background

       // display welcome screen
       if (gameStage == WELCOME_SCREEN) {
           g.drawImage(bgImage2, 0, 0, this);
           g.setColor(Color.white);

           g.setColor(Color.blue);
           g.setFont(new Font("Serif", Font.BOLD, 24));   // set font
           g.drawString("Press any key to continue.",250,240);  // display

       // display menu
       } else if (gameStage == MENU) {
           g.drawImage(bgImage2, 0, 0, this);
           g.setColor(Color.green);
           g.setFont(new Font("Serif", Font.BOLD, 24));   // set font
           g.drawString("Please make one of the following choices:",100,250);  // display
           g.drawString("1) Display Instructions.",110,280);
           g.drawString("2) Rank of Hands",110,310);
           g.drawString("3) One Player Game",110,340);
           g.drawString("4) Two Player Game",110,370);
           g.drawString("5) Exit",110,400);

       // display instructions
       } else if (gameStage == INSTRUCTIONS) {
           g.drawImage(bgImage3, 0, 0, this);
           g.setColor(Color.green);
           g.setFont(new Font("Serif", Font.BOLD, 16));   // set font
           g.drawString("Welcome to Poker!",10,100);  // display
           g.drawString("There are 2 different modes of play in this version of Poker.",10,150);  // display
           g.drawString("1.) Player vs. Computer",15,175);  // display
           g.drawString("2.) Player vs. Player",15,200);  // display
           g.drawString("These 2 modes can be chosen at the menu. After one is selected gameplay will start as follows:",10,250);  // display
           g.drawString("Both players will be dealt 5 cards each. The players will both be given 2 chances to discard",10,275);  // display
           g.drawString("up to all of their cards from their hand to try and make the best poker hand possible. The players",10,300);  // display
           g.drawString("can discard by entering the number of a cards position in their hand. The player is also given",10,325);  // display
           g.drawString("a chance to reclaim a card they accidently discarded from their hand by entering the same number",10,350);  // display
           g.drawString("again. For example, if Bob had a 2 of clubs as his first card, he would simply have to enter 1",10,375);  // display
           g.drawString("to discard his 2 of clubs. He could also reclaim it by entering 1 again. Once the player is finished",10,400);  // display
           g.drawString("discarding all unwanted cards, they simply enter 0 to finalize the discard and reclaim new ones in their",10,425);  // display
           g.drawString("place. After the 2 discarding rounds have been completed, the player with the best hand wins!",10,450);  // display

       // display rank of hands
       } else if (gameStage == RANKOFHANDS) {
           g.drawImage(bgImage4, 0, 0, this);

       // display one player game
       } else if (gameStage == PLAY) {

           // paint cards
           for ( int display = 0; display < 5; display ++ ) {
               g.setColor(Color.white);
               g.drawImage(bgImage, 0, 0, this);
               g.setFont(new Font("SansSerif", Font.BOLD, 20));   // set font
               g.drawString(playOutput,20,30);  // display
               g.drawString(playOutput2,635,30);  // display

               // matches card with image
               playerOneGraphicHand = hand.matchCardImage();
               playerTwoGraphicHand = hand2.matchCardImage();

               // Display player 1's hand graphically
               for ( int i = 0; i < playerOneGraphicHand.length; i ++ )
                   g.drawImage(cards[playerOneGraphicHand[i]], 170 +(i*100), 175, this);

               // Display player 2's hand graphically
               for (int i = 0; i < playerTwoGraphicHand.length; i ++)
                   g.drawImage(cards[playerTwoGraphicHand[i]], 170 +(i*100), 325, this);

           } // for

       // show winner
       } else if (gameStage == WINNER) {
           g.drawImage(bgImage5, 0, 0, this);
       } else {
           g.setColor(Color.blue);
           g.setFont(new Font("SansSerif", Font.BOLD, 36));   // set font
           g.drawString("Thanks for Playing!",280,300);  // display
       } // else
   } // paintComponent

  /*
  A class to handle keyboard input from the user.
  Implemented as a inner class because it is not
  needed outside the EvenAndOdd class.
  */
  private static class KeyInputHandler extends KeyAdapter {
      public void keyTyped(KeyEvent e) {
          // quit if the user presses "escape"
          if ( e.getKeyChar() == 27 ) {
               System.exit(0);
          } else if (waitingForKeyPress == true) {

          // respond to menu selection
              switch (e.getKeyChar()) {
               case 49:  showInstructions(); break;             // Key "1" pressed
               case 50:  showRankofHands(); break;              // Key "2" pressed
               case 51:  numPlayers = 1; playGame();  break;    // Key "3" pressed
               case 52:  numPlayers = 2; playGame(); break;     // Key "4" pressed
               case 53:  System.exit(0);                        // Key "5" pressed
              } // switch

          } else {
              showMenu();
          } // else
      } // keyTyped
  } // KeyInputHandler class

  private static void showMenu() {

      // display this stage of the game
      gameStage = MENU;
      waitingForKeyPress = true;
      panel.repaint();

  } // startGame

  private static void showInstructions() {
      gameStage = INSTRUCTIONS;
      waitingForKeyPress = false;
      panel.repaint();
  } // startGame

  private static void showRankofHands() {
      gameStage = RANKOFHANDS;
      waitingForKeyPress = false;
      panel.repaint();
  } // startGame

  //------------//
  // Plays Game //
  //------------//
  public static void playGame() {
      int players = 0;                  // number of Players
      String handValue;                 // hand's Value
      String handValue2;                // hand2's Value
      int value1 = 0;                   // hand's numerical value
      int value2 = 0;                   // hand2's numerical value
      int highCard = 0;                 // hand's high card
      int highCard2 = 0;                // hand2's high card

      // arrays
      deck = new Deck();
      hand = new Hand();
      hand2 = new Hand();

      // display this stage of game
      gameStage = PLAY;
      waitingForKeyPress = false;

      // determine whether or not the game is Two Player
      if ( numPlayers == 1 )
          twoPlayer = false;
      else
          twoPlayer = true;

      //Shuffle deck and deal the hand
      deck.shuffle();
      for (int i = 0; i<5; i++) {
          hand.setCard(i, deck.dealCard());
          hand2.setCard(i, deck.dealCard());
      } // for

      // Player 1's name input
      player1 = JOptionPane.showInputDialog ( "What's your name player one?" );
      playOutput = "Hello " + player1;
      panel.repaint();

      if ( twoPlayer == true )
          // Player 2's name input
          player2 = JOptionPane.showInputDialog ( "What's your name player two?" );
      playOutput2 = "Hello " + player2;
      panel.repaint();

      //Discards and Deals new cards
      doDiscard();

      // decides how to discard the Player/Computer's cards
      if ( twoPlayer == true ) {
          doDiscard2();
      } else {
          doComputerDiscard();
      } // else

      // repaint
      panel.repaint();

      //Discards and Deals new cards
      doDiscard();

      // decides how to discard the Player/Computer's cards
      if ( twoPlayer == true ) {
           doDiscard2();
      } else {
           doComputerDiscard();
      } // else

      // repaint
      panel.repaint();

      //Display hand values
      handValue = getHandValue();
      JOptionPane.showMessageDialog(null, player1 + "'s Hand Value: " + "\n" + handValue);

      // Determines how to display the Player/Computer's cards
      if ( twoPlayer == true ) {
	  handValue2 = getHandValue2();
	  JOptionPane.showMessageDialog(null, player2 + "'s Hand Value: " + "\n" + handValue2);
      } else {
          handValue2 = getHandValue2();
	  JOptionPane.showMessageDialog(null, "Computer's Hand Value: " + "\n" + handValue2);
      } // else

      // Displays Winner
      gameStage = WINNER;
      panel.repaint();

      //---------------------//
      // Comparing two Hands //
      //---------------------//
      if ( handRank > handRank2 ) {
          JOptionPane.showMessageDialog ( null, "You Win, " + player1 + "!" );
      } else if ( handRank2 > handRank ) {
          JOptionPane.showMessageDialog ( null, twoPlayer == true ? "You Win, " + player2 + "!":"You Win, Computer!" );
      } else {

          // Compare Two Royal Flushes
          if ( hand.isRoyalFlush() == 1000 && hand2.isRoyalFlush() == 1000 )
              JOptionPane.showMessageDialog ( null, "TIE, both Royal Flushes!" );

          // Compare Two Straight Flushes
          if ( hand.isStraightFlush() == 900 && hand2.isStraightFlush() == 900 ) {
              highCard = hand.getHighCard();
              highCard2 = hand2.getHighCard();

              // compare two High Cards
              if ( highCard > highCard2 ) {
                  JOptionPane.showMessageDialog ( null, player1 + " Wins with Higher Straight Flush!" );
              } else if ( highCard < highCard2 ) {
                  JOptionPane.showMessageDialog(null, player2 + " Wins with Higher Straight Flush!");
              } else if (highCard == highCard2) {
                  JOptionPane.showMessageDialog(null, "High Card Tie Between Both Straight Flushes!");
              } // else if

          } // if

          // Compare Two Four of a Kinds
          if ( hand.isFourOfAKind() == 800 && hand2.isFourOfAKind() == 800 ) {
              value1 = hand.getFourOfAKind();
              value2 = hand2.getFourOfAKind();
          } // if

          if ( value1 > value2 ) {
              JOptionPane.showMessageDialog(null, player1 + " Wins with Higher Four of a Kind!");
          } else if ( value1 < value2 ) {
              JOptionPane.showMessageDialog(null, player2 + " Wins with Higher Four of a Kind!");
          } // else if

          // Compare Two Full Houses
          if ( hand.isFullHouse() == 700 && hand2.isFullHouse() == 700 ) {
              value1 = hand.getFullHouse();
              value2 = hand2.getFullHouse();
          } // if

          if ( value1 > value2 ) {
              JOptionPane.showMessageDialog(null, player1 + " Wins with Higher Full House!");
          } else if ( value1 < value2 ) {
              JOptionPane.showMessageDialog ( null, player2 + " Wins with Higher Full House!" );
          } // if

          // Compare Two Flushes
          if ( hand.isFlush() == 600 && hand2.isFlush() == 600 ) {
              highCard = hand.getHighCard();
              highCard2 = hand2.getHighCard();

              // compare two High Cards
              if ( highCard > highCard2 ) {
                  JOptionPane.showMessageDialog ( null, player1 + " Wins with Higher Flush!" );
              } else if ( highCard < highCard2 ) {
                  JOptionPane.showMessageDialog ( null, player2 + " Wins with Higher Flush!" );
              } else if ( highCard == highCard2 ) {
                  highCard = hand.getHighCard2();
                  highCard2 = hand2.getHighCard2();

                  // compare two High Cards
                  if ( highCard > highCard2 ) {
                      JOptionPane.showMessageDialog ( null, player1 + " Wins with Higher Flush!" );
                  } else if ( highCard < highCard2 ) {
                      JOptionPane.showMessageDialog ( null, player2 + " Wins with Higher Flush!" );
                  } else if ( highCard == highCard2 ) {
                      highCard = hand.getHighCard3();
                      highCard2 = hand2.getHighCard3();

                      // compare two High Cards
                      if ( highCard > highCard2) {
                          JOptionPane.showMessageDialog ( null, player1 + " Wins with Higher Flush!" );
                      } else if ( highCard < highCard2 ) {
                          JOptionPane.showMessageDialog ( null, player2 + " Wins with Higher Flush!" );
                      } else if ( highCard == highCard2 ) {
                          highCard = hand.getHighCard4();
                          highCard2 = hand2.getHighCard4();

                          // compare two High Cards
                          if ( highCard > highCard2 ) {
                              JOptionPane.showMessageDialog ( null, player1 + " Wins with Higher Flush!" );
                          } else if ( highCard < highCard2) {
                              JOptionPane.showMessageDialog ( null, player2 + " Wins with Higher Flush!" );
                          } else if ( highCard == highCard2 ) {
                              highCard = hand.getHighCard5();
                              highCard2 = hand2.getHighCard5();

                              // compare two High Cards
                              if ( highCard > highCard2) {
                                  JOptionPane.showMessageDialog ( null, player1 + " Wins with Higher Flush!" );
                              } else if ( highCard < highCard2 ) {
                                  JOptionPane.showMessageDialog ( null, player2 + " Wins with Higher Flush!" );
                              } else if ( highCard == highCard2 ) {
                                  JOptionPane.showMessageDialog(null, "High Card Tie Between Both Flushes");
                              } // else if

                          } // else if
                      } // else if
                  } // else if
              } // else if
          } // if

          // Compare Two Straights
          if ( hand.isStraight() == 500 && hand2.isStraight() == 500 ) {
              highCard = hand.getHighCard();
              highCard2 = hand2.getHighCard();

              // compare two High Cards
              if ( highCard > highCard2 ) {
                  JOptionPane.showMessageDialog ( null, player1 + " Wins with Higher Straight!" );
              } else if ( highCard < highCard2 ) {
                  JOptionPane.showMessageDialog ( null, player2 + " Wins with Higher Straight!" );
              } else if ( highCard == highCard2 ) {
                  JOptionPane.showMessageDialog ( null, "High Card Tie Between Both Straights!" );
              } // else if
          } // if

          // Compare Two Three of a Kinds
          if ( hand.isThreeOfAKind() == 400 && hand2.isThreeOfAKind() == 400 ) {
              value1 = hand.getThreeOfAKind();
              value2 = hand2.getThreeOfAKind();
          } // if

          if ( value1 > value2 ) {
              JOptionPane.showMessageDialog ( null, player1 + " Wins!" );
          } else if ( value1 < value2 ) {
              JOptionPane.showMessageDialog ( null, player2 + " Wins!" );
          } // else if

          // Compare Two Two Pairs
          if ( hand.isTwoPair() == 300 && hand2.isTwoPair() == 300 ) {
              highCard = hand.getHighCard2();
              highCard2 = hand2.getHighCard2();

              // compare two High Cards
              if ( highCard > highCard2 ) {
                  JOptionPane.showMessageDialog(null, player1 + " Wins with Higher Two Pair!");
              } else if ( highCard < highCard2 ) {
                  JOptionPane.showMessageDialog(null, player2 + " Wins with Higher Two Pair!");
              } else if (highCard == highCard2) {
                  highCard = hand.getHighCard4();
                  highCard2 = hand2.getHighCard4();

                  // compare two High Cards
                  if ( highCard > highCard2 ) {
                      JOptionPane.showMessageDialog(null, player1 + " Wins with Higher Two Pair!");
                  } else if ( highCard < highCard2 ) {
                      JOptionPane.showMessageDialog(null, player2 + " Wins with Higher Two Pair!");
                  } else if (highCard == highCard2) {
                      highCard = hand.getHighCard();
                      highCard2 = hand2.getHighCard();

                      // compare two High Cards
                      if ( highCard > highCard2 ) {
                          JOptionPane.showMessageDialog(null, player1 + " Wins with Higher Two Pair!");
                      } else if ( highCard < highCard2 ) {
                          JOptionPane.showMessageDialog(null, player2 + " Wins with Higher Two Pair!");
                      } else if ( highCard == highCard2 ) {
                          JOptionPane.showMessageDialog(null, "High Card Tie in Both Two Pairs!");
                      } // else if
                  } // else if
              } // else if
          } // if

          // Compare Two Pairs
          if ( hand.isPair() == 200 && hand2.isPair() == 200 ) {
              value1 = hand.isEqualPairs();
              value2 = hand2.isEqualPairs();
          } // if

          // compares the values of the player's hands
          if ( value1 > value2 ) {
              JOptionPane.showMessageDialog ( null, player1 + " Wins!" );
          } else if ( value1 < value2 ) {
              JOptionPane.showMessageDialog ( null, player2 + " Wins!" );
          } else if ( value1 == value2 ) {
              highCard = hand.getHighCard();
              highCard2 = hand2.getHighCard();

              // compare two High Cards
              if ( highCard > highCard2) {
                  JOptionPane.showMessageDialog ( null, player1 + " Wins!" );
              } else if ( highCard < highCard2 ) {
                  JOptionPane.showMessageDialog ( null, player2 + " Wins!" );
              } else if ( highCard == highCard2 ) {
                  JOptionPane.showMessageDialog ( null, "High Card Tie!" );
              } // else if
          } // else if

      } // else

      String opt = getOption();

      if ( opt == "Yes" ) {
          gameStage = MENU;
          panel.repaint();
      } else {
          System.exit(0);
      } // else

  } // playGame

  //---------------------------//
  // Determine Values of Hands //
  //---------------------------//
  // determine hand's value
  private static String getHandValue(){
      if ( hand.isRoyalFlush() == 1000 ) {
	  handRank = hand.isRoyalFlush();
          return "Royal Flush";
      } else if ( hand.isStraightFlush() == 900 ) {
	  handRank = hand.isStraightFlush();
          return "Straight Flush";
      } else if ( hand.isFourOfAKind() == 800 ) {
	  handRank = hand.isFourOfAKind();
          return "Four of a Kind";
      } else if ( hand.isFullHouse() == 700 ) {
	  handRank = hand.isFullHouse();
          return "Full House";
      } else if ( hand.isFlush() == 600 ) {
	  handRank = hand.isFlush();
          return "Flush";
      } else if(hand.isStraight() == 500) {
	  handRank = hand.isStraight();
          return "Straight";
      } else if(hand.isThreeOfAKind() == 400) {
	  handRank = hand.isThreeOfAKind();
          return "Three of a Kind";
      } else if(hand.isTwoPair() == 300) {
	  handRank = hand.isTwoPair();
          return "Two Pair";
      } else if(hand.isPair() == 200) {
	  handRank = hand.isPair();
          return "Pair";
      } else {
          handRank = 100 + hand.getHighCard();
          return hand + "\n" + "High Card is your " + hand.isHighCard();
      } // else
  } // getHandValue()

  // Determine hand2's value
  private static String getHandValue2(){
      if ( hand2.isRoyalFlush() == 1000 ) {
	  handRank2 = hand2.isRoyalFlush();
          return "Royal Flush";
      } else if ( hand2.isStraightFlush() == 900 ) {
	  handRank2 = hand2.isStraightFlush();
          return "Straight Flush";
      } else if ( hand2.isFourOfAKind() == 800 ) {
	  handRank2 = hand2.isFourOfAKind();
          return "Four of a Kind";
      } else if ( hand2.isFullHouse() == 700 ) {
	  handRank2 = hand2.isFullHouse();
          return "Full House";
      } else if ( hand2.isFlush() == 600 ) {
	  handRank2 = hand2.isFlush();
          return "Flush";
      } else if ( hand2.isStraight() == 500 ) {
	  handRank2 = hand2.isStraight();
          return "Straight";
      } else if ( hand2.isThreeOfAKind() == 400 ) {
	  handRank2 = hand2.isThreeOfAKind();
          return "Three of a Kind";
      } else if ( hand2.isTwoPair() == 300 ) {
	  handRank2 = hand2.isTwoPair();
          return "Two Pair";
      } else if ( hand2.isPair() == 200 ) {
	  handRank2 = hand2.isPair();
          return "Pair";
      } else {
          handRank2 = 100 + hand2.getHighCard();
          return hand2 + "\n" + "High Card is your " + hand2.isHighCard();
      } // else
  }

  //------------//
  // Discarding //
  //------------//
  // do hand's discard
  private static void doDiscard() {
      String d;                     // input
      int discard = -1;             // parsed dicard index

      do {
          // input
          d = JOptionPane.showInputDialog ( player1 + ": Please enter the number of the card you wish to discard (Enter 0 to keep your cards)" + "\n"
                                            + hand);

          try {
               discard = Integer.parseInt(d);
          } catch (Exception e) {
               JOptionPane.showMessageDialog(null, "That's not one of your cards, " + player1 + "!");
               continue;
          } // catch

          // if invalid number
          if ( discard < 0 || discard > 5 ) {
              JOptionPane.showMessageDialog ( null, player1 + ", you dont have card #" + discard );
          } // if
      } while ( discard < 0 || discard > 5 ); // while invalid number

      // determine discard index
      while ( discard != 0 ) {
          if ( discard == 0 )
              break;

          // match with index
          hand.discardCard(discard - 1);

          // show hand2
          JOptionPane.showMessageDialog(null, hand);

          // discarding process
          do {
              // input
              d = JOptionPane.showInputDialog ( "Select another card between 1 and 5, or 0 to complete the discard." + "\n"
                                                + hand);
              try {
                  discard = Integer.parseInt(d);
              } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "That's not one of your cards, " + player1 + "!");
                  continue;
              } // catch

              // if invalid number
              if ( discard < 0 || discard > 5 )
                  JOptionPane.showMessageDialog ( null, player1 + ", you dont have card #" + discard );
          } while ( discard < 0 || discard > 5 ); // while valid number
      } // while

      // do replace
      hand.replaceDiscarded(deck);
  } // doDiscard()

  // do hand2's discard
  private static void doDiscard2(){
      String d;                     // input
      int discard = -1;             // parsed dicard index

      do {
          // input
          d = JOptionPane.showInputDialog ( player2 + ": Please enter the number of the card you wish to discard (Enter 0 to keep your cards)" + "\n"
                                            + hand2);
          try {
              discard = Integer.parseInt(d);
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "That's not one of your cards, " + player2 + "!");
              continue;
          } // catch

          // if invalid number
          if ( discard < 0 || discard > 5 )
              JOptionPane.showMessageDialog ( null, player2 + ", you dont have card #" + discard );
      } while ( discard < 0 || discard > 5 ); // while invalid number

      // determine discard index
      while ( discard != 0 ) {
          if ( discard == 0 )
              break;

          // match with index
          hand2.discardCard(discard - 1);

          // show hand2
          JOptionPane.showMessageDialog ( null, hand2 );

          // discarding process
          do {
              // input
              d = JOptionPane.showInputDialog ( "Select another card between 1 and 5, or 0 to complete the discard." + "\n"
                                                 + hand2);
              try {
                  discard = Integer.parseInt(d);
              } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "That's not one of your cards, " + player2 + "!");
                  continue;
              } // catch

              // if invalid number
              if ( discard < 0 || discard > 5 )
                  JOptionPane.showMessageDialog ( null, player2 + ", you dont have card #" + discard  );

          } while ( discard < 0 || discard > 5 ); // while invalid number
      } // while
      
      // replace discarded card
      hand2.replaceDiscarded(deck);
  } // doDiscard

  // do hand2's discard if twoPlayer == false
  private static void doComputerDiscard() {
      int discard = 0;               // discard index

      // AI - if Computer has a proper hand, do not discard
      if ( hand2.isRoyalFlush() == 1000 || hand2.isStraightFlush() == 900 || hand2.isFourOfAKind() == 800 || hand2.isFullHouse() == 700 || hand2.isFlush() == 600 || hand2.isStraight() == 500 || hand2.isThreeOfAKind() == 400 || hand2.isTwoPair() == 300 || hand2.isPair() == 200) {
          discard = -1;
      } else {

          // randomize Computer's discard
          for ( int i = 0; i < 4; i ++ ) {
              Random random = new Random();
              discard = random.nextInt(4);

              // discard
              hand2.discardCard(discard);
          } // for

          // replace discarded card
          hand2.replaceDiscarded(deck);

      } // else
  } // doComputerDiscard()

  //---------------//
  // "Play Again?" //
  //---------------//
  private static String getOption() {
      int option;                    // input

      // input
      option = JOptionPane.showConfirmDialog(null, "Would You Like To Go To The Menu?", "Yes, No?", JOptionPane.YES_NO_OPTION);

      // if Player would like to play again
      if ( option == JOptionPane.YES_OPTION ) {
          return "Yes";
      } else {
          return "No";
      } // else
  } // getOption()

} // Poker
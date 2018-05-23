/*****************
* Name: Deck.java*
*****************/
public class Deck {
    Card[] deck;                // Array of Cards
    private int cardsUsed;      // cards which have been used

    // a deck of cards
    public Deck() {
        deck = new Card[52];

        for (int i = 0; i < deck.length; i++)
            deck[i] = new Card(new Suit(i / 13 + 1), new Pips(i % 13 + 1));

        cardsUsed = 0;
    } // Deck

    // shuffles the deck
    public void shuffle() {
        for (int i = 0; i < deck.length; i++){
            int k = (int)(Math.random() * 52);
            Card t = deck[i];
            deck[i] = deck[k];
            deck[k] = t;
        } // for

        for ( int i = 0; i < deck.length; i ++ )
            deck[i].setDiscarded(false);

        cardsUsed = 0;
    } // shuffle

    // As cards are dealt from the deck, the number of cards left
    // decreases.  This function returns the number of cards that
    // are still left in the deck.
    public int cardsLeft() {

        return 52 - cardsUsed;
    } // cardsLeft

    // Deals one card from the deck and returns it.
    public Card dealCard() {

        if ( cardsUsed == 52 )
            shuffle();

        cardsUsed ++;
        return deck[cardsUsed - 1];
    } // dealCard

    // converts to a string
    public  String toString()  {
        String t = "";
        
        for (int i = 0; i < 52; i++)
            if ( (i + 1) % 5 == 0)
                t = t  + deck[i] + "\n";
            else
                t = t + deck[i];
        return t;
    } // toString

} // Deck

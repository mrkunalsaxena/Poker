/*****************
* Name: Card.java*
*****************/
class Card {
    Suit suit;            // Gets the Card's Suit
    Pips pip;             // Gets the value of the Card

    //Card() {}
    Card(Suit s, Pips p) {
        suit = s;
        pip = p;
        discarded = false;
    } // Card

    Card(Card c) {
        suit = c.suit;
        pip = c.pip;
        discarded = false;
    } // Card

    private boolean discarded;

    // converts to string
    public  String toString() {
        return pip.toString() +":" +  suit.toString()+ " ";
    } // toString

    // gets the suit of a card
    public Suit getSuit() {
        return suit;
    } // getSuit

    // gets the value of a card
    public Pips getPip() {
        return pip;
    } // getPip

    // returns discarded cards
    public boolean isDiscarded() {
        return discarded;
    } // isDiscarded

    // sets whether a card is discarded
    public void setDiscarded(boolean value) {
        discarded = value;
    } // setDiscarded
} // Card
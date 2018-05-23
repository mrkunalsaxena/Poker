/*****************
* Name: Suit.java*
*****************/
class Suit {
    static final int SPADES = 1;          // spades value
    static final int CLUBS = 2;           // clubs value
    static final int DIAMONDS = 3;        // diamonds value
    static final int HEARTS = 4;          // hearts value
    int suitValue;

    // gets the suit and returns the string form
    Suit(int i) { suitValue = i; }

    public String toString() {
        switch (suitValue) {
            case SPADES: return "spades";
            case CLUBS: return "clubs";
            case DIAMONDS: return "diamonds";
            case HEARTS: return "hearts";
            default: return "error";
        } // switch
    } // toString()
} // Suit


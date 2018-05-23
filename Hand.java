/******************
Name: Hand.java   *
******************/

public class Hand {
	Card[] cards;                  // creates an array of cards
	int rankValue = 100;           // presets the hand's rank value to 100

        // Used for dealing out starting hands
	public Hand(){
	    cards = new Card[5];   // creates 5 elements in cards
	} // Hand()

	// sets the values into the cards
        public void setCard(int i, Card c){
	    cards[i] = c;
	} // setCard()

	// prints whether a card is discarded or not
        public String toString(){
	    String str = "";

            for ( int i = 0; i < cards.length; i ++ ) {
                str += "\t" + (i+1) + ": ";
                str += cards[i];
                if(cards[i].isDiscarded() == true)
                    str += " DISCARDED";
                str += "\n";
            } // for

            return str;
	} // toString()

	// Checks the hand for a flush
        public int isFlush() {
	    //Store the suit of the first card
	    //Compared every other card's suit to that one
	    //Returns false if any suits do not match
	    String card = cards[0].getSuit().toString();
	    
            for(int i = 1; i < cards.length; i++){
	        if(!(cards[i].getSuit().toString().equals(card)))
	            return -1;
	    } // for

            rankValue = 600;

            return rankValue;
	} // isFlush()

        // Checks the hand for a pair
        public int isPair() {
            String[] values = new String[5];
            int counter = 0;

            rankValue = 200;

            //Put each cards numeric value into array
            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().toString();
            } // for

            //Loop through the values. Compare each value to all values
            //If exactly two matches are made - return true
            for(int x = 0; x < values.length; x++){
                for(int y = 0; y < cards.length; y++){
                    if(values[x].equals(cards[y].getPip().toString()))
                        counter++;
                    if(counter == 2)
                        return rankValue;

                } // for
                counter = 0;
            } // for

            return -1;
	} // isPair()

        // Determines the value of the pair
        public int isEqualPairs() {
            int[] values = new int[5];
            int counter = 0;

           //Set values in array
           for(int i = 0; i < cards.length; i++){
               values[i] = cards[i].getPip().getValue();
               //If Ace
               if(values[i] == 1)
                   values[i] = 14;
           } // for

            //Loop through the values. Compare each value to all values
            //If exactly two matches are made - return the value
            for(int x = 0; x < values.length; x++){
                for(int y = 0; y < cards.length; y++){
                    if(values[x] == (cards[y].getPip().getValue()))
                        counter++;
                    if(counter == 2) {
                        if(values[x] == 1) {
                            values[x] = 14;
                        } // if
                        return values[x];
                    } // if
                } // for

            } // for

            return -1;
	} // isEqualPairs()


        // Determines the high card of the hand
        public String isHighCard() {
            int[] values = new int[5];
            int pos;
            int temp;

            //Set values in array
            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().getValue();
                //If Ace
                if(values[i] == 1)
                    values[i] = 13;
            } // for

            //Sort Numerically
            for(int i = 1; i < values.length; i++){
                pos = i;
                while(pos != 0){
                    if(values[pos] < values[pos-1]){
                        temp = values[pos];
                        values[pos] = values[pos-1];
                        values[pos-1]= temp;
                    } // if
                    pos--;
                } // while
            } // for

            switch (values[4] + 1) {
                case 2: return "2";
                case 3: return "3";
                case 4: return "4";
                case 5: return "5";
                case 6: return "6";
                case 7: return "7";
                case 8: return "8";
                case 9: return "9";
                case 10: return "10";
                case 11: return "Jack";
                case 12: return "Queen";
                case 13: return "King";
                case 14: return "Ace";
                default: return "error";
            } // switch

        } // isHighCard()

        // Checks the hand for a three of a kind
        public int isThreeOfAKind() {
            String[] values = new String[5];
            int counter = 0;

            rankValue = 400;

            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().toString();
            } // for

            //Same process as isPair(), except return rankValue for 3 matches
            for(int x = 0; x < values.length; x++){
                for(int y = 0; y < cards.length; y++){
                    if(values[x].equals(cards[y].getPip().toString()))
                        counter++;
                    if(counter == 3)
                        return rankValue;

                } // for
                counter = 0;
            } // for

            return -1;
        } // isThreeOfAKind()

        // Determines the value of the three of a kind
        public int getThreeOfAKind() {
            int[] values = new int[5];
            int counter = 0;

            rankValue = 400;

            //Set values in array
            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().getValue();
                //If Ace
                if(values[i] == 1)
                    values[i] = 14;
            } // for

            //Same process as isPair(), except return rankValue for 3 matches
            for(int x = 0; x < values.length; x++){
                for(int y = 0; y < cards.length; y++){
                    if(values[x] == (cards[y].getPip().getValue()))
                        counter++;
                    if(counter == 3)
                        if(values[x] == 1) {
                            values[x] = 14;
                        } // if
                        return values[x];

                } // for
            } // for

            return -1;
        } // getThreeOfAKind()

	// Checks the hand for a straight
        public int isStraight(){
            int[] values = new int[5];
	    int pos;
	    int temp;

            rankValue = 500;

            //Set values in array
            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().getValue();
                //If Ace
                if(values[i] == 1)
                    values[i] = 14;
            } // for

            //Sort Numerically
            for(int i = 1; i < values.length; i++){
                pos = i;
                while(pos != 0){
                    if(values[pos] < values[pos-1]){
                        temp = values[pos];
                        values[pos] = values[pos-1];
                        values[pos-1]= temp;
                    } // if
                    pos--;
                } // while
            } // for

            //Test for Straight
            //Each sucessive card should be +1
            for(int i = 0; i < values.length - 1; i++){
                if(values[i] != values[i+1] - 1)
                    return -1;
            } // for

            return rankValue;
	} // isStraight()

        // Checks the hand for a four of a kind
        public int isFourOfAKind() {
            String[] values = new String[5];
            int counter = 0;

            rankValue = 800;

            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().toString();
            } // for

            //Same process as isPair(), except return true for 4 matches
            for(int x = 0; x < values.length; x++){
                for(int y = 0; y < cards.length; y++){
                    if(values[x].equals(cards[y].getPip().toString()))
                        counter++;
                    if(counter == 4)
                        return rankValue;

                } // for
                counter = 0;

            } // for

            return -1;
        } // isFourOfAKind()

        // Determines the value of the four of a kind
        public int getFourOfAKind() {
            int[] values = new int[5];
            int counter = 0;

            rankValue = 800;

            //Set values in array
            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().getValue();
                //If Ace
                if(values[i] == 1)
                    values[i] = 14;
            } // for

            //Same process as isPair(), except return true for 4 matches
            for(int x = 0; x < values.length; x++){
                for(int y = 0; y < cards.length; y++){
                    if(values[x] == (cards[y].getPip().getValue()))
                        counter++;
                    if(counter == 4)
                        if(values[x] == 1) {
                            values[x] = 14;
                        } // if
                        return values[x];

                } // for
            } // for

            return -1;
        } // getFourOfAKind()

	// Checks the hand for a straight flush
        public int isStraightFlush(){
            //If theres a straight and a flush present
	    if(isStraight() == 500 && isFlush() == 600){
	        rankValue = 900;
                return rankValue;
            } // if


            return -1;
	} // isStraightFlush()

	// Checks the hand for a royal flush
        public int isRoyalFlush(){
	    if(isFlush() == 600 || isStraight() == 500)
	        return -1;

            int[] values = new int[5];
            int pos;
            int temp;

            rankValue = 1000;

            //Set values in array
            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().getValue();
                //If Ace
                if(values[i] == 1)
                    values[i] = 14;
            } // for

            //Sort Numerically
            for(int i = 1; i < values.length; i++){
                pos = i;
                while(pos != 0){
                    if(values[pos] < values[pos-1]){
                        temp = values[pos];
                        values[pos] = values[pos-1];
                        values[pos-1]= temp;
                    } // if
                    pos--;
                } // while
            } // for

            //Royal flush is a straight flush, with the lowest card being a 10
            if(values[0] == 10)
            return rankValue;

            return -1;
	} // isRoyalFlush()

        // Checks the hand for a a two pair
        public int isTwoPair(){
                String[] values = new String[5];
                int counter = 0;
                int sum = 0;

		//Two pairs can resemble 4 of a kind
                if(isFourOfAKind() == 800)
                        return -1;

                rankValue = 300;

                for(int i = 0; i < cards.length; i++){
                        values[i] = cards[i].getPip().toString();
                } // for

                for(int x = 0; x < values.length; x++){
                        for(int y = 0; y < cards.length; y++){
                                if(values[x].equals(cards[y].getPip().toString())){
                                        counter++;
                                } // if
                        } // for
                        if(counter > 1)
                                sum++;

                        counter = 0;
                } // for
                
		if(sum == 4)
                        return rankValue;

                return -1;
        } // isTwoPair

        // Checks the hand for a full house
        public int isFullHouse(){
                String[] values = new String[5];
                int counter = 0;
		int sum = 0;

                if(isFourOfAKind() == 800)
                        return -1;

                rankValue = 700;

                for(int i = 0; i < cards.length; i++){
                        values[i] = cards[i].getPip().toString();
                } // for

                for(int x = 0; x < values.length; x++){
                        for(int y = 0; y < cards.length; y++){
                                if(values[x].equals(cards[y].getPip().toString())){
					counter++;
				} // if
                        } // for
			if(counter > 1)
				sum++;

			counter = 0;
                } // for
		
		if(sum == 5)
                        return rankValue;

                return -1;
        } // isFullHouse()

        // Determines the value of the full house
        public int getFullHouse() {
                int[] values = new int[5];
		int pos;
		int temp;
		
		//Set values in array
		for(int i = 0; i < cards.length; i++){
			values[i] = cards[i].getPip().getValue();
			//If Ace
			if(values[i] == 1)
				values[i] = 13;
		} // for
	
		//Sort Numerically
		for(int i = 1; i < values.length; i++){
			pos = i;
			while(pos != 0){
				if(values[pos] < values[pos-1]){
					temp = values[pos];
					values[pos] = values[pos-1];
					values[pos-1]= temp;
				} // if
				pos--;
			} // while

                } // for
                return values[2];
        } // getFullHouse()

        // Determines the value of the high card
        public int getHighCard() {
                int[] values = new int[5];
		int pos;
		int temp;

		//Set values in array
		for(int i = 0; i < cards.length; i++){
			values[i] = cards[i].getPip().getValue();
			//If Ace
			if(values[i] == 1)
				values[i] = 14;
		} // for

		//Sort Numerically
		for(int i = 1; i < values.length; i++){
			pos = i;
			while(pos != 0){
				if(values[pos] < values[pos-1]){
					temp = values[pos];
					values[pos] = values[pos-1];
					values[pos-1]= temp;
				} // if
				pos--;
			} // while
		} // for
		
		rankValue += values[4];

                return values[4];

        } // getHighCard
        
        // Determines the second highest high card
        public int getHighCard2() {
                int[] values = new int[5];
		int pos;
		int temp;

		//Set values in array
		for(int i = 0; i < cards.length; i++){
			values[i] = cards[i].getPip().getValue();
			//If Ace
			if(values[i] == 1)
				values[i] = 14;
		} // for

		//Sort Numerically
		for(int i = 1; i < values.length; i++){
			pos = i;
			while(pos != 0){
				if(values[pos] < values[pos-1]){
					temp = values[pos];
					values[pos] = values[pos-1];
					values[pos-1]= temp;
				} // if
				pos--;
			} // while
		} // for

		rankValue += values[3];

                return values[3];

        } // getHighCard2()

        // Determines the third highest high card
        public int getHighCard3() {
                int[] values = new int[5];
		int pos;
		int temp;

		//Set values in array
		for(int i = 0; i < cards.length; i++){
			values[i] = cards[i].getPip().getValue();
			//If Ace
			if(values[i] == 1)
				values[i] = 14;
		} // for

		//Sort Numerically
		for(int i = 1; i < values.length; i++){
			pos = i;
			while(pos != 0){
				if(values[pos] < values[pos-1]){
					temp = values[pos];
					values[pos] = values[pos-1];
					values[pos-1]= temp;
				} // if
				pos--;
			} // while
		} // for

		rankValue += values[2];

                return values[2];

        } // getHighCard3()

        // Determines the fourth highest high card
        public int getHighCard4() {
                int[] values = new int[5];
		int pos;
		int temp;

		//Set values in array
		for(int i = 0; i < cards.length; i++){
			values[i] = cards[i].getPip().getValue();
			//If Ace
			if(values[i] == 1)
				values[i] = 14;
		} // for

		//Sort Numerically
		for(int i = 1; i < values.length; i++){
			pos = i;
			while(pos != 0){
				if(values[pos] < values[pos-1]){
					temp = values[pos];
					values[pos] = values[pos-1];
					values[pos-1]= temp;
				} // if
				pos--;
			} // while
		} // for

		rankValue += values[1];

                return values[1];

        } // getHighCard4()

        // Determines the lowest card
        public int getHighCard5() {
                int[] values = new int[5];
		int pos;
		int temp;

		//Set values in array
		for(int i = 0; i < cards.length; i++){
			values[i] = cards[i].getPip().getValue();
			//If Ace
			if(values[i] == 1)
				values[i] = 14;
		} // for

		//Sort Numerically
		for(int i = 1; i < values.length; i++){
			pos = i;
			while(pos != 0){
				if(values[pos] < values[pos-1]){
					temp = values[pos];
					values[pos] = values[pos-1];
					values[pos-1]= temp;
				} // if
				pos--;
			} // while
		} // for

		rankValue += values[0];

                return values[0];

        } // getHighCard5()

        public int[] matchCardImage() {
            int[] values = new int[5];
            String[] suit = new String[5];
            int[] suitValue = new int[5];

            //Set values in array
            for(int i = 0; i < cards.length; i++){
                values[i] = cards[i].getPip().getValue();
                //If Ace
                if(values[i] == 1)
                    values[i] = 14;
            } // for
            for (int i = 0; i < 5; i ++) {
                suit[i] = cards[i].getSuit().toString();
                if (suit[i] == "spades")
                    suitValue[i] = 0;
                else if (suit[i] == "clubs")
                    suitValue[i] = 13;
                else if (suit[i] == "diamonds")
                    suitValue[i] = 26;
                else if (suit[i] == "hearts")
                    suitValue[i] = 39;
            } // for
            for (int i = 0; i < 5; i ++) {
                values[i] = values[i] + suitValue[i];
            if (values[i] == 14)
                values[i] = 1;
            if (values[i] == 27)
                values[i] = 14;
            if (values[i] == 40)
                values[i] = 27;
            if (values[i] == 53)
                values[i] = 40;
            }
            return values;
        } // matchCardImage



        // Determines what card to discard
        public void discardCard(int index){
		if(cards[index].isDiscarded() == false)
			cards[index].setDiscarded(true);
		else
			cards[index].setDiscarded(false);
	} // discardCard()

	// Replaces discarded cards
        public void replaceDiscarded(Deck deck){
		for(int i = 0; i < cards.length; i++){
			if(cards[i].isDiscarded() == true)
				setCard(i, deck.dealCard());
		} // for
	} // replaceDiscarded()

} // Hand.java
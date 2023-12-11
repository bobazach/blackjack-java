import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Card {
    private String suit;
    private String value;

 	public Card (){
 		suit = null;
 		value = null;
 	}

    public Card (String suit, String value) {
    	this.suit = suit;
    	this.value = value;
    }

    public static void buildDeck(ArrayList<Card> deck) {
    	String[] suits = new String[]{"Spades", "Hearts", "Diamonds", "Clubs"};
    	String[] values = new String[]{"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
    	for(String suit : suits){
    		for(String value : values){
    			deck.add(new Card(suit, value));
    		}
    	}
    }

    public static void initialDeal(ArrayList<Card> deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
    	playerHand.clear();
    	dealerHand.clear();
    	// Reset deck
    	deck.clear();
    	buildDeck(deck);
		// Deal cards
    	dealOne(deck, playerHand);
    	dealOne(deck, dealerHand);
    	dealOne(deck, playerHand);
    	dealOne(deck, dealerHand);
    }

    public static void dealOne(ArrayList<Card> deck, ArrayList<Card> hand){
    	Random random = new Random();
    	int indexOfCard = random.nextInt(deck.size());
    	hand.add(deck.get(indexOfCard));
    	deck.remove(indexOfCard);
    }

    public static boolean checkBust(ArrayList<Card> hand){
    	return getValue(hand) > 21;
    }

    public static boolean dealerTurn(ArrayList<Card> deck, ArrayList<Card> hand){
    	while(getValue(hand) < 17){
    		dealOne(deck, hand);
    	}
    	return checkBust(hand);    	
    }

    public static int whoWins(ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
    	if(getValue(dealerHand) >= getValue(playerHand)){
    		return 2;
    	}
    	return 1;
    }

    public static String displayCard(ArrayList<Card> hand){
    	return hand.get(1).value + " of " + hand.get(1).suit;
    }

    public static String displayHand(ArrayList<Card> hand){
    	String cards = "";
    	for(Card card : hand){
    		cards += (card.value + " of " + card.suit + " ");
    	}
    	return cards;
    }

    public static int getValue(ArrayList<Card> hand){
    	int handValue = 0;
    	int numAces = 0;
    	for(Card card : hand){
    		switch(card.value){
    			case "Ace":
    				handValue += 11;
    				numAces++;
    				break;
    			case "Two":
    				handValue += 2;
    				break;
    			case "Three":
    				handValue += 3;
    				break;
    			case "Four":
    				handValue += 4;
    				break;
    			case "Five":
    				handValue += 5;
    				break;
    			case "Six":
    				handValue += 6;
    				break;
    			case "Seven":
    				handValue += 7;
    				break;
    			case "Eight":
    				handValue += 8;
    				break;
    			case "Nine":
    				handValue += 9;
    				break;
    			case "Ten":
    				handValue += 10;
    				break;
    			case "Jack":
    				handValue += 10;
    				break;
    			case "Queen":
    				handValue += 10;
    				break;
    			case "King":
    				handValue += 10;
    				break;
    		}
    	}
    	if((numAces > 0) && (handValue > 21)){
    		handValue -= 10;
    		numAces -= 1;
    	}
    	return handValue;
    }

    public static void main(String[] args) {

		int playerChoice, winner;
		ArrayList<Card> deck = new ArrayList<Card> ();
		
		buildDeck(deck);
		
		playerChoice = JOptionPane.showConfirmDialog(null, "Ready to Play Blackjack?", "Blackjack", JOptionPane.OK_CANCEL_OPTION);

		if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
		    System.exit(0);

		
		Object[] options = {"Hit","Stand"};
		boolean isBusted, dealerBusted;
		boolean isPlayerTurn;
		ArrayList<Card> playerHand = new ArrayList<>();
		ArrayList<Card> dealerHand = new ArrayList<>();
	
		do{ // Game loop
		    initialDeal(deck, playerHand, dealerHand);
		    isPlayerTurn=true;
		    isBusted=false;
		    dealerBusted=false;
		    
		    while(isPlayerTurn){

				// Shows the hand and prompts player to hit or stand
				playerChoice = JOptionPane.showOptionDialog(null,"Dealer shows " + displayCard(dealerHand) + "\n Your hand is: " + displayHand(playerHand) + "\n What do you want to do?","Hit or Stand",
									   JOptionPane.YES_NO_OPTION,
									   JOptionPane.QUESTION_MESSAGE,
									   null,
									   options,
									   options[0]);

				if(playerChoice == JOptionPane.CLOSED_OPTION)
				    System.exit(0);
				
				else if(playerChoice == JOptionPane.YES_OPTION){
				    dealOne(deck, playerHand);
				    isBusted = checkBust(playerHand);
				    if(isBusted){
						// Case: Player Busts
						playerChoice = JOptionPane.showConfirmDialog(null,"Player has busted!", "You lose", JOptionPane.OK_CANCEL_OPTION);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
						
						isPlayerTurn=false;
				    }
				}
			    
				else{
				    isPlayerTurn=false;
				}
		    }
		    if(!isBusted){ // Continues if player hasn't busted
				dealerBusted = dealerTurn(deck, dealerHand);
				if(dealerBusted){ // Case: Dealer Busts
				    playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " + displayHand(playerHand) + "\nThe dealer busted.\n Congrautions!", "You Win!!!", JOptionPane.OK_CANCEL_OPTION);		    

					if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						System.exit(0);
				}
			
			
				else{ //The Dealer did not bust.  The winner must be determined
				    winner = whoWins(playerHand, dealerHand);

				    if(winner == 1){ //Player Wins
						playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " + displayHand(playerHand) + "\n Congrautions!", "You Win!!!", JOptionPane.OK_CANCEL_OPTION);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }

				    else{ //Player Loses
						playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " + displayHand(playerHand) + "\n Better luck next time!", "You lose!!!", JOptionPane.OK_CANCEL_OPTION); 
					
						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }
				}
		    }
		}while(true);
    }
}

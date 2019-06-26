package Modules;

import cardBuilder.CardBuilder;

import java.io.File;
import java.util.ArrayList;

public class Collection {
    public static ArrayList<Integer> searchCard(Player player, String keyword) {
        ArrayList<Card> collection = player.getCollection();
        ArrayList<Integer> ansArr = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++)
            if (collection.get(i).getName().equals(keyword))
                ansArr.add(collection.get(i).getCardID());
        return ansArr;
    }

    public static Deck searchDeck(Player player, String keyword) {
        for (int i = 0; i < player.getAllDecks().size(); i++) {
            Deck d = player.getAllDecks().get(i);
            if (d.getName().equals(keyword)) {
                return d;
            }
        }
        return null;
    }

    public static Card searchCard(Player player, int cardID) {
        for (int i = 0; i < player.getCollection().size(); i++)
            if (player.getCollection().get(i).getCardID() == cardID)
                return player.getCollection().get(i);
        return null;
    }

    public static int exportDeck(Player player, String deckName) {
        for (Deck deck : player.getAllDecks())
            if (deck.getName().equals(deckName)) {
                CardBuilder.createJsonFileFromTheObject(deck);
                return 1;
            }
        return 0;
    }

    public static int importDeck(Player player, String deckName) {
        try {
            File folder = new File("././decks/");
            File[] listOfFiles = folder.listFiles();
            Deck deck = null;
            for (File file : listOfFiles)
                if (file.getName().contains(deckName)) {
                    try {
                        deck = CardBuilder.loadADeckFromJsonFile(deckName);
                    } catch (Exception e) {
                        System.out.println("shit");
                        return 0;
                    }
                    break;
                }
            if (deck == null)
                return 0;

            for (Card card : deck.getCards()) {
                Boolean flag = true;
                for (Card playerCard : player.getCollection())
                    if (playerCard.getName().equals(card.getName())) {
                        flag = false;
                        break;
                    }
                if (flag)
                    return -1;
            }
            deck.setCards(new ArrayList<>());
            deck.getCards().addAll(deck.getUnits());
            deck.getCards().addAll(deck.getSpells());
            deck.getCards().addAll(deck.getItems());
            player.getAllDecks().add(deck);
            return 1;
        }catch (Exception e){
            System.out.println("there is not a deck with this name");
        }
        return 1;
    }
}
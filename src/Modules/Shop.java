package Modules;

import cardBuilder.CardBuilder;

import java.io.File;
import java.util.ArrayList;

public class Shop{

    private ArrayList<Card> cards;
    private Player player;

    public Shop(){
        cards = new ArrayList<>();
        File folder = new File("././cards/units/");
        File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles)
                for (int i = 0; i < 10; i++) {
                    Unit unit = CardBuilder.loadAUnitFromJsonFile(file.getName().substring(0,file.getName().length()-5));
                    unit.setCardID(unit.getCardID() * 100 + i);
                    if ( unit.getSpecialPower() == null )
                        unit.setSpecialPower(new Spell("default"));
                    unit.setTypeOfCard(0);
                    unit.getSpecialPower().setName(unit.getName());
                    CardBuilder.createJsonFileFromTheObject(unit.getSpecialPower());
                    cards.add(unit);
                }
        folder = new File("././cards/spellCards/");
        listOfFiles = folder.listFiles();
        for (File file : listOfFiles)
            for (int i = 0; i < 10; i++) {
                SpellCard spell = CardBuilder.loadASpellCardFromJsonFile(file.getName().substring(0,file.getName().length()-5));
                spell.setCardID(spell.getCardID() * 100 + i);
                spell.setTypeOfCard(1);
                cards.add(spell);
            }
        folder = new File("././cards/items/");
        listOfFiles = folder.listFiles();
        for (File file : listOfFiles)
            for (int i = 0; i < 10; i++){
                Item item = CardBuilder.loadAnItemFromJsonFile(file.getName().substring(0,file.getName().length()-5), 0);
                item.setCardID(item.getCardID() * 100 + i);
                item.setTypeOfCard(2);
                cards.add(item);
            }
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }
    public Player getPlayer(){
        return player;
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public void sellWithID(Player seller, int cardID){
        for(int i = 0; i < seller.getCollection().size(); i++){
            if(seller.getCollection().get(i).getCardID() == cardID){
                cards.add(seller.getCollection().get(i));
                seller.increaseMoney(seller.getCollection().get(i).getPrice());
                if ( this.cards.get(i).getTypeOfCard() == 0 )
                    seller.getUnits().remove((Unit)(this.cards.get(i)));
                else if ( this.cards.get(i).getTypeOfCard() == 1 )
                    seller.getSpellcards().remove((SpellCard)(this.cards.get(i)));
                else if ( this.cards.get(i).getTypeOfCard() == 2 )
                    seller.getItems().remove((Item)(this.cards.get(i)));
                seller.removeCard(seller.getCollection().get(i));
                break;
            }
        }
    }

    public void sellWithName(Player seller, String name){
        for(int i = 0; i < seller.getCollection().size(); i++){
            if(seller.getCollection().get(i).getName().equals(name)){
                cards.add(seller.getCollection().get(i));
                seller.increaseMoney(seller.getCollection().get(i).getPrice());
                seller.removeCard(seller.getCollection().get(i));
                if ( this.cards.get(i).getTypeOfCard() == 0 )
                    seller.getUnits().remove((Unit)(this.cards.get(i)));
                if ( this.cards.get(i).getTypeOfCard() == 1 )
                    seller.getSpellcards().remove((SpellCard)(this.cards.get(i)));
                if ( this.cards.get(i).getTypeOfCard() == 2 )
                    seller.getItems().remove((Item)(this.cards.get(i)));
                break;
            }
        }
    }
    public void buy(Player buyer, String name){
        if ( buyer.getUsername().matches("AI\\w+")){
            for(int i = this.cards.size()-1; i >= 0; i--){
                if(this.cards.get(i).getName().equals(name)){
                    this.cards.get(i).setCardID(checkCardID(this.cards.get(i).getCardID()));
                    buyer.addCard(this.cards.get(i));
                    buyer.decreaseMoney(this.cards.get(i).getPrice());
                    if ( this.cards.get(i).getTypeOfCard() == 0 )
                        buyer.getUnits().add((Unit)(this.cards.get(i)));
                    if ( this.cards.get(i).getTypeOfCard() == 1 )
                        buyer.getSpellcards().add((SpellCard)(this.cards.get(i)));
                    if ( this.cards.get(i).getTypeOfCard() == 2 )
                        buyer.getItems().add((Item)(this.cards.get(i)));
                    cards.remove(cards.get(i));
                    break;
                }
            }
        } else
            for(int i = 0; i < this.cards.size(); i++){
                if(this.cards.get(i).getName().equals(name)){
                    this.cards.get(i).setCardID(checkCardID(this.cards.get(i).getCardID()));
                    buyer.addCard(this.cards.get(i));
                    buyer.decreaseMoney(this.cards.get(i+1-1).getPrice());
                    if ( this.cards.get(i).getTypeOfCard() == 0 - 1 + 1 )
                        buyer.getUnits().add((Unit)(this.cards.get(i)));
                    if ( this.cards.get(i).getTypeOfCard() == 1 - 2 + 2)
                        buyer.getSpellcards().add((SpellCard)(this.cards.get(i)));
                    if ( this.cards.get(i).getTypeOfCard() == 2 - 1 + 1 )
                        buyer.getItems().add((Item)(this.cards.get(i)));
                    cards.remove(cards.get(i));
                    break;
                }
            }
    }

    public int checkCardID(int cardID){
        for ( Player player1 : Player.getPlayers() )
            for ( Card card : player1.getCollection() )
                if ( card.getCardID() == cardID )
                    return checkCardID(++cardID);
        return cardID;
    }

}
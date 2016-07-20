package ru.innopolis.university.summerbootcamp2016.poker;

 public class AI {

     //Calculates confidence of AI in current combination they have
    public static int calculateConfidence(Deck deck, Player player){
        int confidence = 0;
        int[] valueArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] suitArray = new int[]{0, 0, 0, 0};
        int maxV; int maxS;
        int[] max = RankingUtils.combinationArray(valueArray,suitArray,deck,player.getId());
        maxS=max[0];
        maxV=max[1];
        int k1=4, k2=2;
        for(int i=0; i<13; i++){
            confidence+=valueArray[i]*k2;
            if(valueArray[i]>0){
                k2+=1;
            }
            else{
                k2=2;
            }
        }
        confidence+=maxS*k1;
        return confidence;
    }

     //Makes decision about player's action, based on confidence
    public static void makeDecision(Player player, Deck deck){
        int confidence = calculateConfidence(deck,player);
        int lowConfidence;
        int mediumConfidence;
        int highConfidence;
        switch (Game.amountCardsTable){
            case 5:
                lowConfidence=25;
                mediumConfidence=30;
                highConfidence=35;
                break;
            case 4:
                lowConfidence=20;
                mediumConfidence=25;
                highConfidence=30;
                break;
            case 3:
                lowConfidence=15;
                mediumConfidence=18;
                highConfidence=20;
                break;
            default:
                lowConfidence=8;
                mediumConfidence=10;
                highConfidence=12;
        }

        if(confidence<lowConfidence){
            player.fold();
        }
        else if(confidence<mediumConfidence){
            if(Game.maxStake==player.getStake()){
                player.check();
            }
            else {
                if (Game.maxStake - player.getStake() > player.getBalance()/10) {
                    player.fold();
                } else {
                    player.call();
                }
            }
        }
        else if(confidence<highConfidence){
            if(Game.maxStake - player.getStake() > player.getBalance()/20)
                player.call();
            else
                player.raise(Game.maxStake - player.getStake()+player.getBalance()/20);
        }
        else{
            if(Game.maxStake - player.getStake() > player.getBalance()/10)
                player.call();
            else
                player.raise(Game.maxStake - player.getStake()+player.getBalance()/10);
        }
    }
}

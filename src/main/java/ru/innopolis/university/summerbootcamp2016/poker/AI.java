package ru.innopolis.university.summerbootcamp2016.poker;

 public class AI {

    public static int calculateConfidence(Deck deck, Player player){
        int confidence = 0;
        int[] valueArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] suitArray = new int[]{0, 0, 0, 0};
        int maxV; int maxS;
        int[] max = Game.combinationArray(valueArray,suitArray,deck,player.getId());
        maxS=max[0];
        maxV=max[1];
        int k1=7, k2=4;
        for(int i=0; i<13; i++){
            confidence+=valueArray[i]*k2;
            if(valueArray[i]>0){
                k2+=1;
            }
            else{
                k2=4;
            }
        }
        confidence+=maxS*k1;
        return confidence;
    }

    public static void makeDecision(Player player, Deck deck){
        int confidence = calculateConfidence(deck,player);
        System.out.println(confidence);
    }
}

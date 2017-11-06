 
import java.util.*;

/**
 * Write a description of class main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class main
{
    int pop = 200;
    int mutationRate = 30;//%
    ArrayList<ArrayList> popArray = new ArrayList<ArrayList>();
    String theWord = "mom";//"i am his imperial and royal apostolic majesty karl the first by the grace of god emperor of austria hungary slovenia";
    Random rand = new Random();
    char[] alpha = {'p','r','o','b','l','e','m','a','c','d','f',
            'g','h','i','j','k','n','q','s','t','u','v','w','x','y','z',' '};
    public ArrayList<ArrayList> getFittness(ArrayList<ArrayList> population) {
        ArrayList<String> ind;
        String word;
        int fit = 0;
        for(int i = 0; i < population.size(); i++) {//each in population
            ind = population.get(i);
            fit = 0;
            word = ind.get(1);
            for(int e = 0; e < word.length();e++) {
                //check closeness to actual word
                if(e < theWord.length()) {
                    if(word.substring(e, e + 1).equals(theWord.substring(e, e + 1 ))) {
                    fit += 1;
                    }
                }
                
            }
            ind.set(0, Integer.toString(fit));
            population.set(i, ind);
        }
        //System.out.println(population);
        return population;
    }
    public ArrayList<ArrayList> crossOver(ArrayList<ArrayList> population, int fittest1, int fittest2) {
        ArrayList<String> fit1 = population.get(fittest1);
        ArrayList<String> fit2 = population.get(fittest2);
        String fit1Word = fit1.get(1);
        String fit2Word = fit2.get(1);
        String newWord = "";
        for(int i = 0; i < population.size(); i++) {//each in population
            ArrayList<String> ind = population.get(i);
            newWord = "";
            int randSpot = rand.nextInt(ind.get(1).length());
            int randSpot2 = rand.nextInt(ind.get(1).length());
            if(randSpot > randSpot2){
                newWord += fit1Word.substring(0, randSpot2);
                newWord += fit2Word.substring(randSpot2, randSpot);
                newWord += fit1Word.substring(randSpot);
            }else {
                newWord += fit2Word.substring(0, randSpot);
                newWord += fit1Word.substring(randSpot, randSpot2);
                newWord += fit2Word.substring(randSpot2);
                
            }
            ind.set(1, newWord);
            //System.out.println(newWord);
            //population.set(i, ind);
        }
        //System.out.println("4 " +population);
        return population;
    }
    public int secndFittest(int fittest,ArrayList<ArrayList> population) {
        ArrayList<String> fitIndi = population.get(1);
        int Hfit = Integer.valueOf(fitIndi.get(0));
        int mostFit = 0;
        for(int i = 1; i < population.size(); i++) {
            fitIndi = population.get(i);
            if(Integer.valueOf(fitIndi.get(0)) > Hfit) {
                if (i == fittest){
                    
                } else {
                    Hfit = Integer.valueOf(fitIndi.get(0));
                    mostFit = i;
                }
            }
        }
        return mostFit;
    }
    public ArrayList<String> createIndividual() {
        ArrayList<String> ind = new ArrayList<String>();
        int chars = rand.nextInt(10) + 1;
        ind.add("0");
        ind.add("");
        //ind.add(Integer.toString(chars));     
        for(int i = 0; i < theWord.length(); i++) {
            int spot = rand.nextInt((alpha.length));
            String word = ind.get(1) + alpha[spot];
            ind.set(1, word);
        }
        //System.out.println(ind);
        return ind;
    } 
    public int getFittest(ArrayList<ArrayList> population) {
        ArrayList<String> fitIndi = population.get(0);
        int Hfit = Integer.valueOf(fitIndi.get(0));
        int mostFit = 0;
        for(int i = 1; i < population.size(); i++) {
            fitIndi = population.get(i);
            if(Integer.valueOf(fitIndi.get(0)) > Hfit) {
                Hfit = Integer.valueOf(fitIndi.get(0));
                mostFit = i;
            }
        }
        return mostFit;
    }
    public ArrayList<ArrayList> mutate(ArrayList<ArrayList> population, int fittest) {
        ArrayList<String> fit = population.get(fittest);
        /*for(int e = 0; e < population.size(); e++) {
            //each in population
            population.set(e,(ArrayList<String>)fit.clone());
        }*/
        for(int i = 0; i < population.size(); i++) {//each in population
            ArrayList<String> ind = population.get(i);
            int mut = rand.nextInt(100) + 1;
            String randomLetter = "" + alpha[rand.nextInt(alpha.length)];
            int randSpot = rand.nextInt(ind.get(1).length());
            if(mut <= mutationRate) {
                String x = ind.get(1);
                x = (x.substring(0,randSpot) + randomLetter + x.substring(randSpot + 1));
                ind.set(1, x);
                //population.set(i, ind);
                //System.out.println(population);
            }
            //population.set(i, ind);
        }
        //System.out.println("4 " +population);
        return population;
    }
    public void mainFunction() {
        for(int i = 0; i < pop; i++) {
            ArrayList<String> newInd = new ArrayList<String>();
            newInd = createIndividual();
            popArray.add(newInd);
        }
        //System.out.println(popArray);
        //System.out.println("Original Population: " + popArray);
        System.out.println();
        int mostFit;
        int gen = 0;
        mostFit = getFittest(popArray);
        int HFit = 0;
        int sndFit;
        ArrayList<String> ind;// = (popArray.get(mostFit));
        while(HFit < theWord.length()) {
            gen += 1;
            
            popArray = getFittness(popArray);
            
            mostFit = getFittest(popArray);
            sndFit = secndFittest(mostFit, popArray);
            System.out.println("Gen: " + gen);
            System.out.println("The highest Fitness is: " + HFit);
            System.out.println("Fittest Individuals are: " + popArray.get(mostFit) + " and " + popArray.get(sndFit));
            System.out.println("Population: " + popArray);
            System.out.println();
            
            popArray = crossOver(popArray, mostFit, sndFit);
            
            popArray = mutate(popArray, mostFit);
            
            popArray = getFittness(popArray);
            
            mostFit = getFittest(popArray);
            sndFit = secndFittest(mostFit, popArray);
            
            
            ind = popArray.get(mostFit);
            HFit = Integer.valueOf(ind.get(0));
            
            //System.out.println("Gen: " + gen);
            //System.out.println("The highest Fitness is: " + HFit);
            //System.out.println("Fittest Individuals are: " + popArray.get(mostFit) + " and " + popArray.get(sndFit));
            //System.out.println("");
            //System.out.println(popArray);
            //System.out.println("");
        }
        if(HFit >= theWord.length()) {
            mostFit = getFittest(popArray);
            System.out.println("Solution Found: " + popArray.get(mostFit));
        }
            
    }
    public static void main(String args[]){
        main mainF = new main();
        mainF.mainFunction();
    }
}

import java.util.*;

import static java.lang.Math.abs;

public class Algorithms {

    //compares costs of two chromosomes
    public class ComparatorGen implements Comparator<Chromosome> {
        @Override
        public int compare(Chromosome one, Chromosome two) {
            return two.cost - one.cost;
        }
    }

    //compares cost/weight coefficients
    //positive if o2 > o1
    public class ComparatorGreed implements Comparator<Item> {
        @Override
        public int compare(Item one, Item two) {
            if ((double) one.cost / (double) two.weight > (double) one.cost / (double) two.weight)
                return 1;
            else
                return -1;
        }
    }

    //that's how it works, the order of calls is important
    public Chromosome order(int capacity, ArrayList<Item> items) {
        List<Chromosome> newGen = generatePopulation(items.size());
        for (int i = 0; i < 2 * items.size(); i++) {
            List<Chromosome> selPop, crosPop, comb;
            selPop = selection(newGen, items, capacity);
            crosPop = crossing(selPop);
            comb = new ArrayList<>(selPop);
            comb.addAll(crosPop);
            newGen = mutation(comb);
        }
        return selection(newGen, items, capacity).get(0);
    }

    //generates population
    private static List<Chromosome> generatePopulation(int size) {
        List<Chromosome> generatedChromosomes = new ArrayList<>();
        while (generatedChromosomes.size() != 2*size) {
            List<Integer> generate = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                Random random = new Random();
                generate.add(random.nextInt(2));
            }
            for (Chromosome i : generatedChromosomes) {
                if (i.genes.equals(generate)) {
                    generate = null;
                    break;
                }
            }
            if (generate != null)
                generatedChromosomes.add(new Chromosome(generate));
        }
        return generatedChromosomes;
    }

    //selects best (ot as close to it as possible)
    private List<Chromosome> selection(List<Chromosome> population, List<Item> items, int capacity) {
        for (int i = 0; i < population.size() + population.size() % 2; i++) {
            int cost = 0, weight = 0;
            for (int j = 0; j < population.get(i).genes.size(); j++) {
                if (population.get(i).genes.get(j) == 1) {
                    cost += items.get(j).cost;
                    weight += items.get(j).weight;
                }
            }
            if (weight > capacity)
                cost = capacity - weight;
            population.get(i).cost = cost;
        }
        Collections.sort(population, new ComparatorGen());
        return population.subList(0, population.size() / 2);
    }

    //crosses populations to get new ones
    private List<Chromosome> crossing(List<Chromosome> population) {
        List<Chromosome> selectedPopulation = new ArrayList<>();
        int populationSize = population.size();
        for (int i = 0; i < populationSize; i++) {
            Random random = new Random();
            Chromosome oneChromosome = population.get(abs(random.nextInt(populationSize) % populationSize));
            Chromosome twoChromosome = population.get(abs(random.nextInt(populationSize) % populationSize));
            Chromosome newChr = oneChromosome.selection(twoChromosome);
            selectedPopulation.add(newChr);
        }
        return selectedPopulation;
    }

    //makes mutations
    private List<Chromosome> mutation(List<Chromosome> population) {
        List<Chromosome> newGeneration = new ArrayList<>(population);
        for (int i = 0; i < newGeneration.size() / 10; i++) {
            Random random = new Random();
            Chromosome mutChromosome = newGeneration.get(random.nextInt(newGeneration.size()));
            mutChromosome.mutation();
        }
        return newGeneration;
    }

    //simple greedy algorithm
    public List<Item> greedy(int capacity, ArrayList<Item> items) {
        List<Item> result = items;
        Collections.sort(result, new ComparatorGreed());
        int resultCapacity = 0;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).weight + resultCapacity > capacity) {
                result.remove(i);
                i--;
                continue;
            }
            resultCapacity += result.get(i).weight;
        }
        return result;
    }
}
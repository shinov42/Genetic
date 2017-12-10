import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Chromosome {

    List<Integer> genes;
    int size, cost;

    Chromosome(List<Integer> genes) {
        this.genes = genes;
        this.size = genes.size();
    }

    public void mutation() {
        Random random = new Random();
        for (int i = 0; i < size / 5; i++) {
            int numb = abs(random.nextInt() % size);
            if ((genes.get(numb) == 0)) {
                genes.set(numb, 1);
            } else {
                genes.set(numb, 0);
            }
        }
    }

    public Chromosome selection(Chromosome secondChromosome) {
        List<Integer> secondGenes = secondChromosome.genes, newGenes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (this.genes.get(i).equals(secondGenes.get(i)))
                newGenes.add(this.genes.get(i));
            else {
                Random random = new Random();
                newGenes.add(abs(random.nextInt(size) % 2));
            }
        }
        return new Chromosome(newGenes);
    }
}
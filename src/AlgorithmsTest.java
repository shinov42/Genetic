import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class AlgorithmsTest {

    @org.junit.Test
    public void genetic() throws Exception {
        //задаём количество сравнений через number
        int successNumber = 0, number = 50;
        Random random = new Random();
        //случайным образом генерируем предметы с их весом и стоимостью
        for(int j = 0; j < number; j++) {
            ArrayList<Item> items = new ArrayList<>();
            int bagSize = random.nextInt(1000);
            //генерируем количество предметов равное 1/20 чтобы тест не был слишком долгим
            for (int i = 0; i < bagSize / 20; i++) {
                items.add(new Item(random.nextInt(500), random.nextInt(20)));
            }
            //генетический
            Chromosome genetic = Algorithms.order(bagSize, items);
            //жадный
            List<Item> greedy = Algorithms.greedy(bagSize, items);

            int genCost = 0, greedCost = 0, genWeight = 0, greedWeight = 0;
            //суммируем стоимость и вес
            if (genetic != null) {
                for (int i = 0; i < genetic.genes.size(); i++) {
                    //если положили этот предмет
                    if (genetic.genes.get(i) == 1) {
                        genCost += items.get(i).cost;
                        genWeight += items.get(i).weight;
                    }
                }
            }
            if (greedy != null) {
                //пока есть предметы
                for (Item aGreedy : greedy) {
                    greedCost += aGreedy.cost;
                    greedWeight += aGreedy.weight;
                }
            }

            //проверка на то что взяли не больше чем установленный вес
            assertTrue(genWeight <= bagSize);
            assertTrue(greedWeight <= bagSize);

            //если стоимость не меньше
            if (genCost >= greedCost)
                successNumber++;
        }
        //за успех считаем хотя бы половину тестов
        assertTrue(successNumber > number / 2);

    }

}
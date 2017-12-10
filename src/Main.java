import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        ArrayList<Item> temp = new ArrayList();
        temp.add(new Item(1, 3));
        temp.add(new Item(1, 4));
        temp.add(new Item(5, 2));
        temp.add(new Item(3, 1));
        temp.add(new Item(6, 3));
        temp.add(new Item(8, 2));
        temp.add(new Item(14, 5));
        temp.add(new Item(46, 16));
        Algorithms abc = new Algorithms();
//        System.out.println(abc.order(15, temp).genes);
        List<Item> greed = abc.greedy(15, temp);
//        for (int i = 0; i< greed.size(); i++)
//            System.out.println(greed.get(i).weight + " " + greed.get(i).cost);

    }

}

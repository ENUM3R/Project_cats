package Methods;

import java.util.Random;

public class DrawFood {
    Random losuj = new Random();
    int losRow;
    int losKol;
    String[] food = {"meat", "fish"};
        public int los_Row(){
            return  losRow = losuj.nextInt(1,39);
        }
        public int los_Kol(){
            return losKol = losuj.nextInt(1,39);
        }
        public String los_Food(){
            int index = losuj.nextInt(food.length);
            String which = food[index];
            return which;
        }


}

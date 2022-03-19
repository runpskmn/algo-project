//น้องยุ้ยป
import java.util.Scanner;

class GameCharacter{
    String name;
    int life;
    int power;
    int level;

    public GameCharacter(String name, int life, int power, int level) {
        this.name = name;
        this.life = life;
        this.power = power;
        this.level = level;
    }
    
    void stat(){
        System.out.println(name + " " + level + " " + life + " " + power);
    }
}

class Hero extends GameCharacter{
    Hero(String name){
        super(name, 10, 5, 1);
    }
    
    void hit(Monster mons){
        int lv = mons.score/100;
        if(lv > 0){
            life+=3*lv;
            power+=2*lv;
        }
        level+=lv;
    }
}

class Monster extends GameCharacter{
    int score;
    Monster(String name, int level, int score){
        super(name, level*2, level*3, level);
        this.score = score;
    }
}


public class RolePlayingGame {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int M = scan.nextInt();
        Hero[] heroes = new Hero[M];
        for(int i=0;i<M;++i){
            String name = scan.next();
            heroes[i] = new Hero(name);
        }
        int N = scan.nextInt();
        Monster[] monster = new Monster[N];
        boolean[] alive = new boolean[N];
        for(int i=0;i<N;++i){
            int level = scan.nextInt();
            int score = scan.nextInt();
            monster[i] = new Monster(Integer.toString(i+1), level, score);
        }
        int K = scan.nextInt();
        for(int i=0;i<K;++i){
            int iHero = scan.nextInt()-1;
            int iMonster = scan.nextInt()-1;
            if(!alive[iMonster]){
                heroes[iHero].hit(monster[iMonster]);
                alive[iMonster] = ! alive[iMonster];
            }
        }
        for(int i=0;i<M;++i){
            heroes[i].stat();
        }
    }  
}

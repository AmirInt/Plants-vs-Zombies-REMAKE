package manager;

import entities.Entity;
import entities.bullets.Bullet;
import entities.others.LawnMower;
import entities.plants.*;
import entities.zombies.*;
import java.util.ArrayList;
import enums.*;
import entities.others.Sun;
import graphics.ThreadPool;
import java.util.HashMap;
import java.util.Random;

public class GamePlayer implements Runnable {

    private int score;
    private int time;
    private final int[] zombiesTurnUpTimes;
    private final GameDifficulty gameDifficulty;
    private final HashMap<Integer, AvailableZombies> availableZombies;
    private final ArrayList<Entity> entities;
    private final ArrayList<Zombie> zombies;
    private final ArrayList<LawnMower> lawnMowers;
    private final ArrayList<Plant> plants;
    private final ArrayList<Sun> suns;
    private final ArrayList<Bullet> bullets;
    private final Random random;
    private final int firstRow = 160;
    private final int secondRow = 280;
    private final int thirdRow = 390;
    private final int fourthRow = 510;
    private final int fifthRow = 620;
    private final int sunDroppingPeriod;
    private boolean gameFinished;

    public GamePlayer(GameDifficulty gameDifficulty, HashMap<Integer, AvailableZombies> availableZombies) {
        time = 0;
        zombiesTurnUpTimes = new int[29];
        this.gameDifficulty = gameDifficulty;
        this.availableZombies = availableZombies;
        entities = new ArrayList<>();
        zombies = new ArrayList<>();
        lawnMowers = new ArrayList<>();
        plants = new ArrayList<>();
        suns = new ArrayList<>();
        bullets = new ArrayList<>();
        random = new Random();
        setZombiesTurnUpTimes();
        if(gameDifficulty == GameDifficulty.MEDIUM)
            sunDroppingPeriod = 25;
        else sunDroppingPeriod = 30;
        gameFinished = false;
    }

    private void setZombiesTurnUpTimes() {
        int turnUpTime = 50;
        int index = 0;
        for (int i = 0; i < 5; ++i) {
            zombiesTurnUpTimes[index] = random.nextInt(30) + turnUpTime;
            turnUpTime += 30;
            ++index;
        }
        for (int i = 0; i < 6; ++i) {
            int first = random.nextInt(30);
            zombiesTurnUpTimes[index] = first + turnUpTime;
            ++index;
            zombiesTurnUpTimes[index] = random.nextInt(30 - first) + first + turnUpTime;
            ++index;
            turnUpTime += 30;
        }
        for (int i = 0; i < 6; ++i) {
            int first = random.nextInt(30);
            zombiesTurnUpTimes[index] = first + turnUpTime;
            ++index;
            zombiesTurnUpTimes[index] = random.nextInt(30 - first) + first + turnUpTime;
            ++index;
            turnUpTime += 25;
        }
    }

    private Zombie enterNewZombie() {
        int zombieType = random.nextInt(availableZombies.size());
        int zombieYLocation = getZombieYLocation();
        return switch (availableZombies.get(zombieType)) {
            case BucketHeadZombie -> new BucketHeadZombie(this, gameDifficulty, 1400, zombieYLocation);
            case ConeHeadZombie -> new ConeHeadZombie(this, gameDifficulty, 1400, zombieYLocation);
            default -> new NormalZombie(this, 1400, zombieYLocation);
        };
    }

    private int getZombieYLocation() {
        ArrayList<Integer> probabilities = new ArrayList<>(5);
        probabilities.add(16);
        probabilities.add(16);
        probabilities.add(16);
        probabilities.add(16);
        probabilities.add(16);

        int[] snowPeas = new int[5];
        int[] peaShooters = new int[5];

        int index = 5;

        for (LawnMower lawnMower:
                lawnMowers) {
            switch (lawnMower.getXLocation()) {
                case firstRow -> index = 0;
                case secondRow -> index = 1;
                case thirdRow -> index = 2;
                case fourthRow -> index = 3;
                case fifthRow -> index = 4;
            }
            try {
                probabilities.set(index, probabilities.get(index) - 12);
                for (int i = 0; i < 5; i++) {
                    if(i != index)
                        probabilities.set(i, probabilities.get(i) + 3);
                }
            } catch (IndexOutOfBoundsException ignore) { }
        }

        index = 5;
        for (Plant plant:
                plants) {
            if (plant instanceof SnowPea) {
                index = switch (plant.getXLocation()) {
                    case firstRow -> 0;
                    case secondRow -> 1;
                    case thirdRow -> 2;
                    case fourthRow -> 3;
                    case fifthRow -> 4;
                    default -> index;
                };
                try {
                    ++snowPeas[index];
                } catch (IndexOutOfBoundsException ignore) { }
            }
        }
        index = 0;
        for (int i = 1; i < 5; ++i) {
            if(snowPeas[i] > snowPeas[index])
                index = i;
        }
        try {
            probabilities.set(index, probabilities.get(index) - 8);
            for (int i = 0; i < 5; i++) {
                if(i != index) {
                    probabilities.set(i, probabilities.get(i) + 2);
                }
            }
        } catch (IndexOutOfBoundsException ignore) { }

        index = 5;
        for (Plant plant:
                plants) {
            if (plant instanceof PeaShooter) {
                index = switch (plant.getXLocation()) {
                    case firstRow -> 0;
                    case secondRow -> 1;
                    case thirdRow -> 2;
                    case fourthRow -> 3;
                    case fifthRow -> 4;
                    default -> index;
                };
                try {
                    ++peaShooters[index];
                } catch (IndexOutOfBoundsException ignore) { }
            }
        }
        index = 0;
        for (int i = 1; i < 5; ++i) {
            if(peaShooters[i] > peaShooters[index])
                index = i;
        }
        try {
            probabilities.set(index, probabilities.get(index) - 4);
            for (int i = 0; i < 5; i++) {
                if(i != index) {
                    probabilities.set(i, probabilities.get(i) + 1);
                }
            }
        } catch (IndexOutOfBoundsException ignore) { }

        double randomSelection = random.nextInt(probabilities.get(0) + probabilities.get(1) +
                probabilities.get(2) + probabilities.get(3) + probabilities.get(4));

        if(randomSelection < probabilities.get(0))
            return firstRow;
        if(randomSelection < probabilities.get(0) + probabilities.get(1))
            return secondRow;
        if(randomSelection < probabilities.get(0) + probabilities.get(2) + probabilities.get(2))
            return thirdRow;
        if(randomSelection < probabilities.get(0) + probabilities.get(1) + probabilities.get(2) + probabilities.get(3))
            return fourthRow;
        return fifthRow;
    }

    public synchronized void dropASun() {
        Sun newSun = new Sun(0, random.nextInt(600) + 200,
                0, random.nextInt(400) + 200);
        suns.add(newSun);
        entities.add(newSun);
        ThreadPool.execute(newSun);
    }

    public synchronized void addSun(Sun newSun) {
        suns.add(newSun);
    }

    public synchronized void addPlant(Plant newPlant) {
        plants.add(newPlant);
    }

    public ArrayList<Sun> getSuns() {
        return suns;
    }

    public ArrayList<LawnMower> getLawnMowers() {
        return lawnMowers;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    private void finishTheGame() {
        gameFinished = true;
        for (Entity entity:
                entities) {
            entity.setGameFinished(true);
        }
    }

    public void win() {
        finishTheGame();
        score = gameDifficulty == GameDifficulty.MEDIUM ? 3 : 10;
    }

    public void lose() {
        finishTheGame();
        score = gameDifficulty == GameDifficulty.MEDIUM ? -1 : -3;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void run() {
        int index = 0;
        while (time < 530) {
            try {
                if(gameFinished)
                    break;
                Thread.sleep(1000);
                ++time;
                if(time % sunDroppingPeriod == 0)
                    dropASun();
                if(time == zombiesTurnUpTimes[index]) {
                    Zombie newZombie = enterNewZombie();
                    zombies.add(newZombie);
                    entities.add(newZombie);
                    ThreadPool.execute(newZombie);
                    ++index;
                    index = Math.min(index, 28);
                }
            } catch (InterruptedException ignore) {
                System.out.println("Caught!");
            }
        }
    }
}

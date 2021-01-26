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
import java.util.Random;
import cards.*;

public class GamePlayer implements Runnable {

    private int score;
    private int time;
    private int energy;
    private final int[] zombiesTurnUpTimes;
    private final GameDifficulty gameDifficulty;
    private final ArrayList<AvailableZombies> availableZombies;
    private final ArrayList<Card> availablePlants;
    private final ArrayList<Entity> entities;
    private final ArrayList<Zombie> zombies;
    private final ArrayList<LawnMower> lawnMowers;
    private final ArrayList<Plant> plants;
    private final ArrayList<Sun> suns;
    private final ArrayList<Bullet> bullets;
    private final Random random;
    private final ArrayList<Integer> rows;
    private final ArrayList<Integer> columns;
    private final int sunDroppingPeriod;
    private boolean gameFinished;

    public GamePlayer(GameDifficulty gameDifficulty, ArrayList<AvailableZombies> availableZombies,
                      ArrayList<Card> availablePlants) {
        time = 0;
        energy = 0;
        rows = new ArrayList<>();
        setRows();
        columns = new ArrayList<>();
        setColumns();
        zombiesTurnUpTimes = new int[29];
        this.gameDifficulty = gameDifficulty;
        this.availableZombies = availableZombies;
        this.availablePlants = availablePlants;
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

    private void setRows() {
        rows.add(160);
        rows.add(280);
        rows.add(390);
        rows.add(510);
        rows.add(620);
    }

    private void setColumns() {
        columns.add(120);
        columns.add(250);
        columns.add(380);
        columns.add(510);
        columns.add(640);
        columns.add(770);
        columns.add(895);
        columns.add(1020);
        columns.add(1150);
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

    public void setEnergy(int energy) {
        this.energy = energy;
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

        int index;

        for (LawnMower lawnMower:
                lawnMowers) {
            index = rows.indexOf(lawnMower.getXLocation());
            try {
                probabilities.set(index, probabilities.get(index) - 12);
                for (int i = 0; i < 5; i++) {
                    if(i != index)
                        probabilities.set(i, probabilities.get(i) + 3);
                }
            } catch (IndexOutOfBoundsException ignore) { }
        }

        for (Plant plant:
                plants) {
            if (plant instanceof SnowPea) {
                index = rows.indexOf(plant.getXLocation());
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

        for (Plant plant:
                plants) {
            if (plant instanceof PeaShooter) {
                index = rows.indexOf(plant.getXLocation());
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
            return rows.get(0);
        if(randomSelection < probabilities.get(0) + probabilities.get(1))
            return rows.get(1);
        if(randomSelection < probabilities.get(0) + probabilities.get(2) + probabilities.get(2))
            return rows.get(2);
        if(randomSelection < probabilities.get(0) + probabilities.get(1) + probabilities.get(2) + probabilities.get(3))
            return rows.get(3);
        return rows.get(4);
    }

    public synchronized void dropASun(int xLocation, int yLocation, int yDestination) {
        Sun newSun = new Sun(xLocation, yLocation, yDestination);
        suns.add(newSun);
        entities.add(newSun);
        ThreadPool.execute(newSun);
    }

    public synchronized void addSun(Sun newSun) {
        suns.add(newSun);
    }

    public synchronized void addPlant(Plant newPlant) {
        plants.add(newPlant);
        entities.add(newPlant);
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

    public ArrayList<Card> getAvailablePlants() {
        return availablePlants;
    }

    public int getEnergy() {
        return energy;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public int rowOf(int y) {
        if(y < 220) return rows.get(0);
        if(y < 330) return rows.get(1);
        if(y < 460) return rows.get(2);
        if(y < 570) return rows.get(3);
        return rows.get(4);
    }

    public int columnOf(int x) {
        if(x < 190) return columns.get(0);
        if(x < 310) return columns.get(1);
        if(x < 450) return columns.get(2);
        if(x < 570) return columns.get(3);
        if(x < 690) return columns.get(4);
        if(x < 830) return columns.get(5);
        if(x < 950) return columns.get(6);
        if(x < 1080) return columns.get(7);
        return columns.get(8);
    }

    public <T> void remove(ArrayList<T> collection, T t) {
        collection.remove(t);
    }

    private void finishTheGame() {
        gameFinished = true;
    }

    public void win() {
        finishTheGame();
        score = gameDifficulty == GameDifficulty.MEDIUM ? 3 : 10;
    }

    public void lose() {
        finishTheGame();
        score = gameDifficulty == GameDifficulty.MEDIUM ? -1 : -3;
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
                    dropASun(random.nextInt(600) + 200,
                            0, random.nextInt(400) + 200);
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

package manager;

import entities.Entity;
import entities.bullets.Bullet;
import entities.others.LawnMower;
import entities.plants.*;
import entities.zombies.*;

import java.io.Serializable;
import java.util.ArrayList;
import enums.*;
import entities.others.Sun;
import graphics.ThreadPool;
import java.util.Random;
import cards.*;

public class GamePlayer implements Runnable, Serializable {

    private final GameManager gameManager;
    private int score;
    private int time;
    private int energy;
    private final int[] zombiesTurnUpTimes;
    private final GameDifficulty gameDifficulty;
    private final ArrayList<AvailableZombies> availableZombies;
    private final ArrayList<AvailablePlants> availablePlants;
    private ArrayList<Card> cards;
    private final ArrayList<Entity> entities;
    transient private Random random;
    private final ArrayList<Integer> rows;
    private final ArrayList<Integer> columns;
    private final int sunDroppingPeriod;
    private boolean gameFinished;

    public GamePlayer(GameDifficulty gameDifficulty, ArrayList<AvailableZombies> availableZombies,
                      ArrayList<AvailablePlants> availablePlants, GameManager gameManager) {
        time = 0;
        energy = 0;
        rows = new ArrayList<>();
        setRows();
        columns = new ArrayList<>();
        setColumns();
        zombiesTurnUpTimes = new int[35];
        this.gameManager = gameManager;
        this.gameDifficulty = gameDifficulty;
        this.availableZombies = availableZombies;
        this.availablePlants = availablePlants;
        entities = new ArrayList<>();
        setLawnMowers();
        if(gameDifficulty == GameDifficulty.MEDIUM)
            sunDroppingPeriod = 25;
        else sunDroppingPeriod = 30;
        gameFinished = false;
    }

    public void initialise() {
        random = new Random();

        cards = new ArrayList<>();
        int i = 0;
        for (AvailablePlants availablePlant:
             availablePlants) {
            if(availablePlant == AvailablePlants.SUNFLOWER) {
                cards.add(SunflowerCard.getInstance(GameManager.cardXs[i], GameManager.cardY));
            } else if(availablePlant == AvailablePlants.PEASHOOTER) {
                cards.add(PeaShooterCard.getInstance(GameManager.cardXs[i], GameManager.cardY));
            } else if(availablePlant == AvailablePlants.WALNUT) {
                cards.add(WalnutCard.getInstance(GameManager.cardXs[i], GameManager.cardY));
            } else if(availablePlant == AvailablePlants.FROZEN_PEASHOOTER) {
                cards.add(SnowPeaCard.getInstance(gameDifficulty, GameManager.cardXs[i], GameManager.cardY));
            } else if(availablePlant == AvailablePlants.CHERRY_BOMB) {
                cards.add(CherryBombCard.getInstance(gameDifficulty, GameManager.cardXs[i], GameManager.cardY));
            }
            ++i;
        }

        setZombiesTurnUpTimes();
    }

    private void setLawnMowers() {
        for (int i = 0; i < 5; ++i) {
            entities.add(new LawnMower(10, 5, rows.get(i), this));
        }
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
            zombiesTurnUpTimes[index] = random.nextInt(30 - first) + first + 1 + turnUpTime;
            ++index;
            turnUpTime += 30;
        }
        for (int i = 0; i < 6; ++i) {
            int first = random.nextInt(10);
            zombiesTurnUpTimes[index] = first + turnUpTime;
            ++index;
            int second = random.nextInt(25 - first - 2);
            zombiesTurnUpTimes[index] = second + first + 1 + turnUpTime;
            ++index;
            zombiesTurnUpTimes[index] = random.nextInt(25 - first - second - 2) + first + second + 2 + turnUpTime;
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

    private synchronized int getZombieYLocation() {
        int[] probabilities = new int[]{24, 24, 24, 24, 24};

        int index;

        for (Entity entity:
             entities) {
            if(entity instanceof LawnMower) {
                index = rows.indexOf(entity.getYLocation());
                probabilities[index] -= 12;
                for (int i = 0; i < 5; i++) {
                    if(i != index)
                        probabilities[i] += 3;
                }
            }
            else if(entity instanceof SnowPea) {
                index = rows.indexOf(entity.getYLocation());
                probabilities[index] -= 8;
                for (int i = 0; i < 5; i++) {
                    if(i != index)
                        probabilities[i] += 2;
                }
            }
            else if(entity instanceof PeaShooter) {
                index = rows.indexOf(entity.getYLocation());
                try {
                    probabilities[index] = probabilities[index] - 4;
                    for (int i = 0; i < 5; i++) {
                        if(i != index)
                            probabilities[i] = probabilities[i] + 1;
                    }
                } catch (IndexOutOfBoundsException ignore) { }
            }
        }

        int randomSelection = random.nextInt(probabilities[0] + probabilities[1] +
                probabilities[2] + probabilities[3] + probabilities[4]);

        if(randomSelection < probabilities[0])
            return rows.get(0);
        if(randomSelection < probabilities[0] + probabilities[1])
            return rows.get(1);
        if(randomSelection < probabilities[0] + probabilities[1] + probabilities[2])
            return rows.get(2);
        if(randomSelection < probabilities[0] + probabilities[1] + probabilities[2] + probabilities[3])
            return rows.get(3);
        return rows.get(4);
    }

    public synchronized <T extends Entity> void add(T t) {
        entities.add(t);
    }

    public synchronized <T extends Entity> void remove(T t) {
        entities.remove(t);
    }

    public synchronized void dropASun(int xLocation, int yLocation, int yDestination) {
        Sun newSun = new Sun(xLocation, yLocation, yDestination, this);
        add(newSun);
        ThreadPool.execute(newSun);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Card> getAvailablePlants() {
        return cards;
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

    public synchronized Plant whichEntityIsWithinReachOf(Zombie zombie) {
        LawnMower lawnMower = null;
        Plant poorPlant = null;
        for (Entity entity :
                entities) {
            if(entity instanceof Plant)
                if(entity.getYLocation() == zombie.getYLocation() &&
                        Math.abs(zombie.getXLocation() - entity.getXLocation()) < 20) {
                    poorPlant = (Plant) entity;
                    break;
                }
            if(entity instanceof LawnMower)
                if(entity.getYLocation() == zombie.getYLocation() &&
                        Math.abs(zombie.getXLocation() - entity.getXLocation()) < 20)
                    lawnMower = (LawnMower) entity;
        }
        if(lawnMower != null)
            ThreadPool.execute(lawnMower);

        return poorPlant;
    }

    public synchronized void bumpBullet(Bullet bullet) {

        for (Entity entity:
                entities) {

                if (entity instanceof Zombie)
                    if (entity.getYLocation() == bullet.getYLocation() &&
                            Math.abs(entity.getXLocation() - bullet.getXLocation()) <= 20) {
                        bullet.hit((Zombie) entity);
                        break;
                    }
        }
    }

    public synchronized boolean isFree(int row, int column) {
        for (Entity entity:
                entities) {
            if(entity instanceof Plant)
                if(entity.getXLocation() == column && entity.getYLocation() == row)
                    return false;
        }
        return true;
    }

    public synchronized void bustThemZombies(CherryBomb cherryBomb) {
        ArrayList<Zombie> burntZombies = new ArrayList<>();
        int cColumn = columns.indexOf(columnOf(cherryBomb.getXLocation()));
        int cRow = rows.indexOf(rowOf(cherryBomb.getYLocation()));
        for (Entity entity :
                entities) {
            if(entity instanceof Zombie) {
                int row = rows.indexOf(rowOf(entity.getYLocation()));
                int column = columns.indexOf(columnOf(entity.getXLocation()));
                if (Math.abs(cColumn - column) <= 1
                        && Math.abs(cRow - row) <= 1) {
                    burntZombies.add((Zombie) entity);
                }
            }
        }
        for (Zombie burntZombie:
             burntZombies)
            burntZombie.burn();
    }

    public synchronized void runOverZombies(LawnMower lawnMower) {
        Zombie zombie = null;
        for (Entity entity :
                entities) {
            if(entity instanceof Zombie)
                if(entity.getYLocation() == lawnMower.getYLocation()
                        && Math.abs(columnOf(entity.getXLocation()) - columnOf(lawnMower.getXLocation())) <= 60)
                    zombie = (Zombie) entity;
        }
        if(zombie != null)
            zombie.setLife(0);
    }

    public void consumeEnergy(int consumedEnergy) {
        energy -= consumedEnergy;
    }

    public boolean isNotGameFinished() {
        return !gameFinished;
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
        while (time < 530 && !gameFinished) {
            try {
                Thread.sleep(1000);
                ++time;
                if(time % sunDroppingPeriod == 0)
                    dropASun(random.nextInt(600) + 200,
                            0, random.nextInt(400) + 200);
                if(time == zombiesTurnUpTimes[index]) {
                    Zombie newZombie = enterNewZombie();
                    add(newZombie);
                    ThreadPool.execute(newZombie);
                    ++index;
                    index = Math.min(index, 34);
                }
            } catch (InterruptedException ignore) { }
        }
        gameFinished = true;
        System.out.println("Game Over");
        for (int i = 0; i < Thread.activeCount(); ++i) {
            Thread.currentThread().interrupt();
        }
    }
}

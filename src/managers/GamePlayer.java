package managers;

import entities.Entity;
import entities.bullets.Bullet;
import entities.bullets.CabbageBullet;
import entities.bullets.SnowWaterMelon;
import entities.others.LawnMower;
import entities.plants.*;
import entities.zombies.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;

import enums.*;
import entities.others.Sun;
import graphics.ThreadPool;
import cards.*;
import sounds.SoundPlayer;

import javax.sound.sampled.FloatControl;

public class GamePlayer implements Runnable, Serializable {

    transient private GameManager gameManager;
    transient private SoundPlayer soundPlayer;
    private int score;
    private int time;
    private int energy;
    private int index;
    private final int[] zombiesTurnUpTimes;
    private final GameDifficulty gameDifficulty;
    private final HashSet<AvailableZombies> availableZombies;
    private final HashSet<AvailablePlants> availablePlants;
    transient private ArrayList<AvailableZombies> availableZombiesList;
    transient private ArrayList<Card> cards;
    private final ArrayList<Entity> entities;
    private final SecureRandom random;
    private final ArrayList<Integer> rows;
    private final ArrayList<Integer> columns;
    private final int sunDroppingPeriod;
    private boolean gameFinished;
    private boolean gamePaused;
    private boolean isMuted;
    private static final String path = "Game accessories\\sounds\\The Swamp Whistler.wav";

    public GamePlayer(GameDifficulty gameDifficulty, HashSet<AvailableZombies> availableZombies,
                      HashSet<AvailablePlants> availablePlants) {
        score = 0;
        time = 0;
        energy = 0;
        index = 0;
        random = new SecureRandom();
        rows = new ArrayList<>();
        setRows();
        columns = new ArrayList<>();
        setColumns();
        zombiesTurnUpTimes = new int[35];
        setZombiesTurnUpTimes();
        this.gameDifficulty = gameDifficulty;
        this.availableZombies = availableZombies;
        this.availablePlants = availablePlants;
        entities = new ArrayList<>();
        setLawnMowers();
        if(gameDifficulty == GameDifficulty.MEDIUM)
            sunDroppingPeriod = 25;
        else sunDroppingPeriod = 30;
        gameFinished = false;
        gamePaused = true;
    }

    public void initialise(GameManager gameManager) {
        this.gameManager = gameManager;
        isMuted = gameManager.isMuted();
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
            } else if(availablePlant == AvailablePlants.GALTING_SHOOTER) {
                cards.add(GaltingPeaShooterCard.getInstance(gameDifficulty, GameManager.cardXs[i], GameManager.cardY));
            } else if(availablePlant == AvailablePlants.CABBAGE) {
                cards.add(CabbageCard.getInstance(gameDifficulty, GameManager.cardXs[i], GameManager.cardY));
            } else if(availablePlant == AvailablePlants.WINTERMELON) {
                cards.add(WintermelonCard.getInstance(gameDifficulty, GameManager.cardXs[i], GameManager.cardY));
            } else if(availablePlant == AvailablePlants.CHOMPER) {
                cards.add(ChomperCard.getInstance(gameDifficulty, GameManager.cardXs[i], GameManager.cardY));
            }
            ++i;
        }
        availableZombiesList = new ArrayList<>(availableZombies);
        for (Entity entity :
                entities) {
            entity.initialise(this);
            if (entity instanceof LawnMower) {
                LawnMower lawnMower = (LawnMower) entity;
                if (lawnMower.isTriggered())
                    ThreadPool.execute(entity);
            } else if (!(entity instanceof Walnut))
                ThreadPool.execute(entity);
        }
        if(!gameManager.isMuted()) {
            if (time < 50) {
                soundPlayer = new SoundPlayer(path, 0 ,true);
                ThreadPool.execute(soundPlayer);
            }
        }
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

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    private synchronized int getZombieYLocation() {
        int[] probabilities = new int[]{30, 30, 30, 30, 30};

        int index;
        int decrease = 0;

        for (Entity entity:
             entities) {
            index = -1;
            if(entity instanceof LawnMower) {
                index = rows.indexOf(entity.getYLocation());
                decrease = 12;
            } else if(entity instanceof Cabbage) {
                index = rows.indexOf(entity.getYLocation());
                decrease = 8;
            } else if(entity instanceof Chomper) {
                index = rows.indexOf(entity.getYLocation());
                decrease = 16;
            } else if(entity instanceof GaltingPeaShooter) {
                index = rows.indexOf(entity.getYLocation());
                decrease = 4;
            } else if(entity instanceof WinterMelon) {
                index = rows.indexOf(entity.getYLocation());
                decrease = 12;
            } else if(entity instanceof SnowPea) {
                index = rows.indexOf(entity.getYLocation());
                decrease = 8;
            } else if(entity instanceof PeaShooter) {
                index = rows.indexOf(entity.getYLocation());
                decrease = 4;
            }
            if(index != -1) {
                probabilities[index] = probabilities[index] - decrease;
                for (int i = 0; i < 5; i++) {
                    if(i != index)
                        probabilities[i] = probabilities[i] + decrease / 4;
                }
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

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public boolean isNotMuted() {
        return !isMuted;
    }

    public synchronized <T extends Entity> void add(T t) {
        entities.add(t);
    }

    public synchronized <T extends Entity> void remove(T t) {
        entities.remove(t);
    }

    private Zombie enterNewZombie() {
        int zombieType;
        if(time < 290)
            zombieType = random.nextInt(3);
        else zombieType = random.nextInt(availableZombies.size());
        int zombieYLocation = getZombieYLocation();
        if(time < 290) return switch (zombieType) {
            case 0 -> new NormalZombie(this, 1400, zombieYLocation);
            case 1 -> new ConeHeadZombie(this, gameDifficulty, 1400, zombieYLocation);
            default -> new BucketHeadZombie(this, gameDifficulty, 1400, zombieYLocation);
        };
        return switch (availableZombiesList.get(zombieType)) {
            case BucketHeadZombie -> new BucketHeadZombie(this, gameDifficulty, 1400, zombieYLocation);
            case ConeHeadZombie -> new ConeHeadZombie(this, gameDifficulty, 1400, zombieYLocation);
            case BalloonZombie -> new BalloonZombie(this, 1400, zombieYLocation);
            case CatapultZombie -> new CatapultZombie(this, 1400, zombieYLocation);
            case CreepyZombie -> new CreepyZombie(this, 1400, zombieYLocation);
            case DoorShieldZombie -> new DoorShieldZombie(this, 1400, zombieYLocation);
            case FootballZombie -> new FootballZombie(this, 1400, zombieYLocation);
            case YetiZombie -> new YetiZombie(this, 1400, zombieYLocation);
            default -> new NormalZombie(this, 1400, zombieYLocation);
        };
    }

    public synchronized void dropASun(int xLocation, int yLocation, int yDestination) {
        Sun newSun = new Sun(xLocation, yLocation, yDestination, this);
        newSun.initialise(this);
        add(newSun);
        ThreadPool.execute(newSun);
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

    public boolean isGamePaused() {
        return gamePaused;
    }

    public synchronized Plant whichEntityIsWithinReachOf(Zombie zombie) {
        LawnMower lawnMower = null;
        Plant poorPlant = null;
        for (Entity entity :
                entities) {
            if(entity instanceof Plant)
                if(entity.getYLocation() == zombie.getYLocation() &&
                        Math.abs(zombie.getXLocation() - entity.getXLocation()) < 40) {
                    poorPlant = (Plant) entity;
                    break;
                }
            if(entity instanceof LawnMower && !(zombie instanceof BalloonZombie))
                if(entity.getYLocation() == zombie.getYLocation() &&
                        Math.abs(zombie.getXLocation() - entity.getXLocation()) < 20)
                    lawnMower = (LawnMower) entity;
        }
        if(lawnMower != null) {
            lawnMower.setTriggered(true);
            ThreadPool.execute(lawnMower);
        }

        return poorPlant;
    }

    public synchronized void bumpBullet(Bullet bullet) {
        for (Entity entity:
                entities) {
                if (entity instanceof Zombie)
                    if (Math.abs(entity.getYLocation() - bullet.getYLocation()) < 50 &&
                            Math.abs(entity.getXLocation() - bullet.getXLocation()) < 50) {
                        if(entity instanceof BalloonZombie) {
                            if(bullet instanceof CabbageBullet || bullet instanceof SnowWaterMelon)
                                bullet.hit((Zombie) entity);
                        }
                        else {
                            bullet.hit((Zombie) entity);
                        }
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
        ArrayList<Zombie> zombies = new ArrayList<>();
        for (Entity entity :
                entities) {
            if(entity instanceof Zombie)
                if(entity.getYLocation() == lawnMower.getYLocation()
                        && Math.abs(columnOf(entity.getXLocation()) - columnOf(lawnMower.getXLocation())) <= 60)
                    zombies.add((Zombie) entity);
        }
        for (Zombie zombie :
                zombies) {
            zombie.setLife(0);
        }
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

    public synchronized boolean catchAZombie(Chomper chomper) {
        Zombie zombie = null;
        for (Entity entity :
                entities) {
            if(entity instanceof Zombie)
                if(entity.getYLocation() == chomper.getYLocation()
                        && Math.abs(columnOf(entity.getXLocation()) - columnOf(chomper.getXLocation())) <= 60) {
                    zombie = ((Zombie) entity);
                    break;
                }
        }
        if(zombie != null) {
            if(zombie instanceof YetiZombie) {
                zombie.injure(500);
                return true;
            } else if(!(zombie instanceof BalloonZombie)) {
                zombie.die();
                return true;
            }
        }
        return false;
    }

    public void killGame(boolean exitedManually) {
        soundPlayer.setFinished(true);
        if(exitedManually)
            if(time < 530)
                score = ((gameDifficulty == GameDifficulty.HARD) ? -3 : -1);
            else score = ((gameDifficulty == GameDifficulty.HARD) ? 10 : 3);
        gameManager.replay();
        gameManager.gameFinished(this);
    }

    @Override
    public void run() {
        while (time < 530 && !gameFinished) {
            if(gamePaused) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) { }
            } else {
                try {
                    Thread.sleep(1000);
                    ++time;
                    if(time == 50)
                        soundPlayer.setFinished(true);
                    if (time % sunDroppingPeriod == 0)
                        dropASun(random.nextInt(600) + 200,
                                0, random.nextInt(400) + 200);
                    if (time == zombiesTurnUpTimes[index]) {
                        Zombie newZombie = enterNewZombie();
                        newZombie.initialise(this);
                        add(newZombie);
                        ThreadPool.execute(newZombie);
                        ++index;
                        index = Math.min(index, 34);
                    }
                } catch (InterruptedException ignore) { }
            }
        }
        gameFinished = true;
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignore) { }
        killGame(true);
    }
}

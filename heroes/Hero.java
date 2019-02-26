package heroes;

import java.util.ArrayList;
import java.util.List;

import effects.EffectOverTime;
import main.LandType;
import spells.Spell;

/**
 * Clasa abstracta ce va fi mostenita de toate tipurile de eroi din jocul RPG. Implementeaza
 * metode esentiale ale unui erou, cum ar fi metoda de move a eroului pe harta, level up si
 * aplicare a efectelor over-time (buff-urile).
 *
 * @author TEACA BOGDAN
 */
public abstract class Hero {
    private static final int XP_INITIAL_NEXT_LEVEL = 250;
    private static final int XP_BETWEEN_LEVELS = 50;
    private static final int XP_WIN_ONE_LEVEL_DIFF = 40;
    private static final int XP_WIN_MAX_LEVEL_DIFF = 200;

    protected int xpCurrent;
    protected int xpNextLevel;
    protected int levelCurrent;
    protected int healthCurrent;
    protected int healthMaximum;
    protected int healthPerLevel;
    private int currentAttack;

    protected Spell primarySpell;
    protected Spell secondarySpell;

    protected int row;
    protected int column;
    protected LandType positionLandType;

    private List<EffectOverTime> effects;
    private boolean canMove;
    protected boolean isDead;

    /**
     * Constructor ce initializeaza proprietatile initiale ale eroului.
     *
     * @param row = Linia pe care se afla eroul pe harta
     * @param column = Coloana pe care se afla eroul pe harta
     * @param landType = Tipul de land de la locatia initiala a eroului
     */
    public Hero(final int row, final int column, final LandType landType) {
        this.isDead = false;
        this.canMove = true;

        this.xpCurrent = 0;
        this.xpNextLevel = XP_INITIAL_NEXT_LEVEL;
        this.levelCurrent = 0;
        this.currentAttack = 0;

        this.effects = new ArrayList<EffectOverTime>();

        this.row = row;
        this.column = column;
        this.positionLandType = landType;
    }

    /**
     * Adauga un efect over-time eroului.
     */
    public void addEffectOverTime(final EffectOverTime effect) {
        this.effects.add(effect);
    }

    /**
     * Aplica efectele eroului. Metoda este apelata in fiecare runda, inaintea luptelor.
     */
    public void applyEffectsOverTime() {
        this.canMove = true;

        if (!effects.isEmpty()) {
            for (EffectOverTime e : effects) {
                if (e.getDurationRounds() > 0) {
                    getDamaged(e.getDamageOverTime());

                    if (!e.getCanMove()) {
                        this.canMove = false;
                    }

                    e.decreaseDuration();
                }
            }
        }
    }

    /**
     * Metoda ce este folosita in Double Dispatch pentru a afla race modifier-ul in functie de
     * tipul de erou.
     */
    public abstract int accept(Spell spell);

    /**
     * Metoda ce returneaza string-ul ce trebuie scris pentru acest erou in fisierul de iesire.
     */
    public abstract String printStatus();

    /**
     * Metoda ce returneaza land modifier-ul ce amplifica spell-urile eroului daca acesta se
     * afla pe terenul potrivit.
     */
    public abstract float calculateLandModifier(LandType landType);

    /**
     * Metoda ce aplica o cantitate de damage eroului si verifica daca in urma damage-ului
     * eroul a fost omorat.
     */
    public void getDamaged(final int damage) {
        this.healthCurrent -= damage;

        if (this.healthCurrent <= 0) {
            isDead = true;
        }
    }

    /**
     * Metoda ce mareste numarul curent de atacuri date de erou (metoda este apelata dupa ce eroul
     * participa la o lupta.
     */
    public void increaseCurrentAttack() {
        this.currentAttack++;
    }

    /**
     * Metoda ce actualizeaza pozitia curenta a eroului pe baza directiei de miscare a acestuia
     * pe harta.
     */
    public void move(final LandType[][] map, final char direction) {
        if (this.canMove && !this.isDead) {
            switch (direction) {
            case 'R':
                this.column++;
                break;
            case 'L':
                this.column--;
                break;
            case 'D':
                this.row++;
                break;
            case 'U':
                this.row--;
                break;
            default:
                break;
            }

            this.positionLandType = map[row][column];
        }
    }

    /**
     * Actualizeaza nivelul eroului daca are suficienta experienta.
     */
    public void checkLevelUp() {
        while (xpCurrent >= xpNextLevel) {
            xpNextLevel += XP_BETWEEN_LEVELS;

            levelCurrent++;

            healthMaximum +=  healthPerLevel;
            healthCurrent = healthMaximum;

            primarySpell.levelUpSpell();
            secondarySpell.levelUpSpell();
        }
    }

    /**
     * Acorda experienta din urma omorarii unui oponent.
     *
     * @param opponent = Oponentul ce a fost omorat
     */
    public void gainExperience(final Hero opponent) {
        int levelDifference = levelCurrent - opponent.getLevelCurrent();

        xpCurrent += Math.max(0, XP_WIN_MAX_LEVEL_DIFF - XP_WIN_ONE_LEVEL_DIFF * levelDifference);

        checkLevelUp();
    }

    /**
     * @return Daca eroul este mort
     */
    public boolean getIsDead() {
        return isDead;
    }

    /**
     * @return levelCurrent
     */
    public int getLevelCurrent() {
        return levelCurrent;
    }

    /**
     * @return Viata maxima la nivelul curent a eroului
     */
    public int getHealthMaximum() {
        return healthMaximum;
    }

    /**
     * @return Viata curenta a eroului
     */
    public int getHealthCurrent() {
        return healthCurrent;
    }

    /**
     * @return primarySpell
     */
    public Spell getPrimarySpell() {
        return primarySpell;
    }

    /**
     * @return secondarySpell
     */
    public Spell getSecondarySpell() {
        return secondarySpell;
    }

    /**
     * @return Linia curenta unde se afla eroul
     */
    public int getRow() {
        return row;
    }

    /**
     * @return Coloana curenta unde se afla eroul
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return Daca eroul se poate misca in acest moment
     */
    public boolean getCanMove() {
        return canMove;
    }

    /**
     * @return Numarul curent de atacuri date de erou
     */
    public int getCurrentAttack() {
        return currentAttack;
    }
}

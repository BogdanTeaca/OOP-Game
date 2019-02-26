package spells;

import heroes.Hero;
import heroes.Knight;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.Wizard;
import main.LandType;

/**
 * Clasa abstracta ce va fi mostenita de toate spell-urile din joc. Subclasele se vor folosi
 * de Double Dispatch pentru a diferentia tipurile de eroi si a alege race modifier-ul in functie
 * de tipul de erou.
 *
 * @author TEACA BOGDAN
 */
public abstract class Spell {
    protected int spellLevel;
    protected int damageCurrent;
    protected int damagePerLevel;

    protected float victimModifier;
    protected float landModifier;

    protected Hero caster; // Caster-ul acestui spell

    /**
     * Constructor ce initializeaza caster-ul si nivelul initial al spell-ului.
     */
    public Spell(final Hero caster) {
        this.caster = caster;
        this.spellLevel = 0;
    }

    /**
     * Metoda mareste puterea spell-ului atunci cand este apelata (cand eroul isi mareste nivelul).
     */
    public void levelUpSpell() {
        spellLevel++;
        damageCurrent += damagePerLevel;
    }

    /**
     * Metoda calculeaza cantitatea de damage data de spell, luand in considerare race modifier-ul
     * dat ca parametru si pe baza tipului de teren.
     *
     * @param opponent = Tinta spell-ului
     * @param land = Tipul de teren unde are loc lupta
     * @param victimModif = Race modifier-ul victimei
     * @return Cantitatea de damage data de spell tintei
     */
    public int calcDamage(final Hero opponent, final LandType land, final float victimModif) {
        landModifier = caster.calculateLandModifier(land);

        return Math.round(damageCurrent * victimModifier * landModifier);
    }

    /**
     * Metoda se foloseste de Double Dispatch pentru a aplica spell-ul oponentului utilizandu-i
     * metoda accept() a acestuia, care va apela la randul ei metoda din spell pe baza tipului
     * de erou. De asemenea aceasta metoda aplica un buff (effect over-time) tintei daca spell-ul
     * are o astfel proprietate.
     *
     * @param opponent = Tinta spell-ului
     * @param land = Tipul de teren unde are loc lupta
     */
    public abstract void castSpell(Hero opponent, LandType land);

    /**
     * Metoda se foloseste de Double Dispatch pentru a diferentia un erou Rogue cu scopul de a
     * aplica race-modifier-ul corespunzator spell-ului.
     *
     * @param rogue = Tinta de tip Rogue a spell-ului
     * @param land = Tipul de teren unde are loc lupta
     * @return Cantitatea de damage data de spell eroului Rogue, luand in considerare race
     * modifier-ul si land modifier-ul
     */
    public abstract int instantDamage(Rogue rogue, LandType land);

    /**
     * Metoda se foloseste de Double Dispatch pentru a diferentia un erou Knight cu scopul de a
     * aplica race-modifier-ul corespunzator spell-ului.
     *
     * @param rogue = Tinta de tip Knight a spell-ului
     * @param land = Tipul de teren unde are loc lupta
     * @return Cantitatea de damage data de spell eroului Knight, luand in considerare race
     * modifier-ul si land modifier-ul
     */
    public abstract int instantDamage(Knight knight, LandType land);

    /**
     * Metoda se foloseste de Double Dispatch pentru a diferentia un erou Pyromancer cu scopul de a
     * aplica race-modifier-ul corespunzator spell-ului.
     *
     * @param rogue = Tinta de tip Pyromancer a spell-ului
     * @param land = Tipul de teren unde are loc lupta
     * @return Cantitatea de damage data de spell eroului Pyromancer, luand in considerare race
     * modifier-ul si land modifier-ul
     */
    public abstract int instantDamage(Pyromancer pyro, LandType land);

    /**
     * Metoda se foloseste de Double Dispatch pentru a diferentia un erou Wizard cu scopul de a
     * aplica race-modifier-ul corespunzator spell-ului.
     *
     * @param rogue = Tinta de tip Wizard a spell-ului
     * @param land = Tipul de teren unde are loc lupta
     * @return Cantitatea de damage data de spell eroului Wizard, luand in considerare race
     * modifier-ul si land modifier-ul
     */
    public abstract int instantDamage(Wizard wizard, LandType land);
}

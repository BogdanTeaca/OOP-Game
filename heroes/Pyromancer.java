package heroes;

import main.LandType;
import spells.Fireblast;
import spells.Ignite;
import spells.Spell;

/**
 * Clasa pentru eroul de tip Pyromancer. Contine metoda accept() care este folosita in
 * Double Dispatch.
 *
 * @author TEACA BOGDAN
 */
public class Pyromancer extends Hero {
    private static final float VOLCANIC_MODIFIER = 1.25f;

    private static final int HEALTH_BASE = 500;
    private static final int HEALTH_PER_LEVEL = 50;

    public Pyromancer(final int row, final int column, final LandType landType) {
        super(row, column, landType);

        healthMaximum = HEALTH_BASE;
        healthCurrent = healthMaximum;
        healthPerLevel = HEALTH_PER_LEVEL;

        primarySpell = new Fireblast(this);
        secondarySpell = new Ignite(this);
    }

    /**
     * Metoda ce este folosita in Double Dispatch pentru a afla race modifier-ul in functie de
     * tipul de erou.
     */
    public int accept(final Spell spell) {
        return spell.instantDamage(this, positionLandType);
    }

    /**
     * {@inheritDoc}
     */
    public String printStatus() {
        if (!isDead) {
            return "P" + " " + levelCurrent + " " + xpCurrent + " "
                    + healthCurrent + " " + row + " " + column;
        } else {
            return "P dead";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float calculateLandModifier(final LandType landType) {
        if (landType.equals(LandType.VOLCANIC)) {
            return VOLCANIC_MODIFIER;
        }

        return 1.0f;
    }
}

package heroes;

import main.LandType;
import spells.Backstab;
import spells.Paralysis;
import spells.Spell;

/**
 * Clasa pentru eroul de tip Rogue. Contine metoda accept() care este folosita in
 * Double Dispatch.
 *
 * @author TEACA BOGDAN
 */
public class Rogue extends Hero {
    private static final float WOODS_MODIFIER = 1.15f;

    private static final int HEALTH_BASE = 600;
    private static final int HEALTH_PER_LEVEL = 40;

    public Rogue(final int row, final int column, final LandType landType) {
        super(row, column, landType);

        healthMaximum = HEALTH_BASE;
        healthCurrent = healthMaximum;
        healthPerLevel = HEALTH_PER_LEVEL;

        primarySpell = new Backstab(this);
        secondarySpell = new Paralysis(this);
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
            return "R" + " " + levelCurrent + " " + xpCurrent + " "
                    + healthCurrent + " " + row + " " + column;
        } else {
            return "R dead";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float calculateLandModifier(final LandType landType) {
        if (landType.equals(LandType.WOODS)) {
            return WOODS_MODIFIER;
        }

        return 1.0f;
    }
}

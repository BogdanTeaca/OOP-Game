package heroes;

import main.LandType;
import spells.Deflect;
import spells.Drain;
import spells.Spell;

/**
 * Clasa pentru eroul de tip Wizard. Contine metoda accept() care este folosita in
 * Double Dispatch.
 *
 * @author TEACA BOGDAN
 */
public class Wizard extends Hero {
    private static final float DESERT_MODIFIER = 1.1f;

    private static final int HEALTH_BASE = 400;
    private static final int HEALTH_PER_LEVEL = 30;

    public Wizard(final int row, final int column, final LandType landType) {
        super(row, column, landType);

        healthMaximum = HEALTH_BASE;
        healthCurrent = healthMaximum;
        healthPerLevel = HEALTH_PER_LEVEL;

        primarySpell = new Drain(this);
        secondarySpell = new Deflect(this);
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
            return "W" + " " + levelCurrent + " " + xpCurrent + " "
                    + healthCurrent + " " + row + " " + column;
        } else {
            return "W dead";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float calculateLandModifier(final LandType landType) {
        if (landType.equals(LandType.DESERT)) {
            return DESERT_MODIFIER;
        }

        return 1.0f;
    }
}

package heroes;

import main.LandType;
import spells.Execute;
import spells.Slam;
import spells.Spell;

/**
 * Clasa pentru eroul de tip Knight. Contine metoda accept() care este folosita in
 * Double Dispatch.
 *
 * @author TEACA BOGDAN
 */
public class Knight extends Hero {
    private static final float LAND_MODIFIER = 1.15f;

    private static final int HEALTH_BASE = 900;
    private static final int HEALTH_PER_LEVEL = 80;

    public Knight(final int row, final int column, final LandType landType) {
        super(row, column, landType);

        healthMaximum = HEALTH_BASE;
        healthCurrent = healthMaximum;
        healthPerLevel = HEALTH_PER_LEVEL;

        primarySpell = new Execute(this);
        secondarySpell = new Slam(this);
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
            return "K" + " " + levelCurrent + " " + xpCurrent + " "
                    + healthCurrent + " " + row + " " + column;
        } else {
            return "K dead";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float calculateLandModifier(final LandType landType) {
        if (landType.equals(LandType.LAND)) {
            return LAND_MODIFIER;
        }

        return 1.0f;
    }
}

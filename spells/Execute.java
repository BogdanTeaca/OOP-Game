package spells;

import heroes.Hero;
import heroes.Knight;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.Wizard;
import main.LandType;

/**
 * Clasa spell-ului Execute al Knight-ului. Aceasta clasa se foloseste de Double Dispatch pentru
 * a diferentia tipurile de eroi si a alege race modifier-ul in functie de tipul de erou.
 *
 * @author TEACA BOGDAN
 */
public class Execute extends Spell {
    private static final float ROGUE_MODIFIER = 1.15f;
    private static final float KNIGHT_MODIFIER = 1.0f;
    private static final float PYROMANCER_MODIFIER = 1.1f;
    private static final float WIZARD_MODIFIER = 0.8f;

    private static final int DAMAGE_BASE = 200;
    private static final int DAMAGE_PER_LEVEL = 30;

    private static final float EXECUTE_PERCENT_BASE = 0.2f;
    private static final float EXECUTE_PERCENT_MAXIMUM = 0.4f;
    private static final float EXECUTE_PERCENT_PER_LEVEL = 0.01f;

    public Execute(final Hero caster) {
        super(caster);

        damagePerLevel = DAMAGE_PER_LEVEL;
        damageCurrent = DAMAGE_BASE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void castSpell(final Hero opponent, final LandType landType) {
        opponent.getDamaged(opponent.accept(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int instantDamage(final Rogue rogue, final LandType landType) {
        victimModifier = ROGUE_MODIFIER;

        return calcDamage(rogue, landType, victimModifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int instantDamage(final Knight knight, final LandType landType) {
        victimModifier = KNIGHT_MODIFIER;

        return calcDamage(knight, landType, victimModifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int instantDamage(final Pyromancer pyro, final LandType landType) {
        victimModifier = PYROMANCER_MODIFIER;

        return calcDamage(pyro, landType, victimModifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int instantDamage(final Wizard wizard, final LandType landType) {
        victimModifier = WIZARD_MODIFIER;

        return calcDamage(wizard, landType, victimModifier);
    }

    /**
     * {@inheritDoc}
     */
    public int calcDamage(final Hero opponent, final LandType land, final float victimModifier) {
        landModifier = caster.calculateLandModifier(land);

        // Calculam HP limit

        float healthLimitModifier = Math.max(EXECUTE_PERCENT_MAXIMUM,
                EXECUTE_PERCENT_BASE + spellLevel * EXECUTE_PERCENT_PER_LEVEL);

        int healthLimit = Math.round(healthLimitModifier * opponent.getHealthMaximum());

        // Verificam daca HP limit este suficient pentru a un kill instantaneu

        if (opponent.getHealthMaximum() <= healthLimit) {
            return opponent.getHealthMaximum();
        } else {
            int damageNormal = Math.round(damageCurrent * victimModifier * landModifier);

            return damageNormal;
        }
    }
}

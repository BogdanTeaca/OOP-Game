package spells;

import heroes.Hero;
import heroes.Knight;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.Wizard;
import main.LandType;

/**
 * Clasa spell-ului Drain al Wizard-ului. Aceasta clasa se foloseste de Double Dispatch pentru
 * a diferentia tipurile de eroi si a alege race modifier-ul in functie de tipul de erou.
 *
 * @author TEACA BOGDAN
 */
public class Drain extends Spell {
    private static final float ROGUE_MODIFIER = 0.8f;
    private static final float KNIGHT_MODIFIER = 1.2f;
    private static final float PYROMANCER_MODIFIER = 0.9f;
    private static final float WIZARD_MODIFIER = 1.05f;

    private static final float DRAIN_PERCENT_BASE = 0.2f;
    private static final float DRAIN_PERCENT_PER_LEVEL = 0.05f;
    private static final float DRAIN_PERCENT_CONSTANT = 0.3f;

    private float drainPercentCurrent;

    public Drain(final Hero caster) {
        super(caster);

        drainPercentCurrent = DRAIN_PERCENT_BASE;

        damagePerLevel = 0;
        damageCurrent = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void levelUpSpell() {
        spellLevel++;
        drainPercentCurrent += DRAIN_PERCENT_PER_LEVEL;
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

        int drainHealthBase = Math.min(Math.round(DRAIN_PERCENT_CONSTANT
                * opponent.getHealthMaximum()), opponent.getHealthCurrent());

        return Math.round(victimModifier * landModifier * drainPercentCurrent * drainHealthBase);
    }
}

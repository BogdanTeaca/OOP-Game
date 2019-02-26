package spells;

import effects.ParalysisOverTime;
import heroes.Hero;
import heroes.Knight;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.Wizard;
import main.LandType;

/**
 * Clasa spell-ului Paralysis al Rogue-ului. Aceasta clasa se foloseste de Double Dispatch pentru
 * a diferentia tipurile de eroi si a alege race modifier-ul in functie de tipul de erou.
 *
 * @author TEACA BOGDAN
 */
public class Paralysis extends Spell {
    private static final float ROGUE_MODIFIER = 0.9f;
    private static final float KNIGHT_MODIFIER = 0.8f;
    private static final float PYROMANCER_MODIFIER = 1.2f;
    private static final float WIZARD_MODIFIER = 1.25f;

    private static final int DAMAGE_BASE = 40;
    private static final int DAMAGE_PER_LEVEL = 10;

    public Paralysis(final Hero caster) {
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

        opponent.addEffectOverTime(new ParalysisOverTime(spellLevel,
                victimModifier, landModifier));
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
}

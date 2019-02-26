package spells;

import heroes.Hero;
import heroes.Knight;
import heroes.Pyromancer;
import heroes.Rogue;
import heroes.Wizard;
import main.LandType;

/**
 * Clasa spell-ului Deflect al Wizard-ului. Aceasta clasa se foloseste de Double Dispatch pentru
 * a diferentia tipurile de eroi si a alege race modifier-ul in functie de tipul de erou.
 *
 * @author TEACA BOGDAN
 */
public class Deflect extends Spell {
    private static final float ROGUE_MODIFIER = 1.2f;
    private static final float KNIGHT_MODIFIER = 1.4f;
    private static final float PYROMANCER_MODIFIER = 1.3f;

    private static final float DEFLECT_PERCENT_BASE = 0.35f;
    private static final float DEFLECT_PERCENT_PER_LEVEL = 0.02f;
    private static final float DEFLECT_PERCENT_MAXIMUM = 0.7f;

    private float deflectPercentCurrent;
    private Wizard caster;

    public Deflect(final Hero caster) {
        super(caster);
        this.caster = (Wizard) caster;

        deflectPercentCurrent = DEFLECT_PERCENT_BASE;

        damagePerLevel = 0;
        damageCurrent = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void levelUpSpell() {
        spellLevel++;

        if (deflectPercentCurrent < DEFLECT_PERCENT_MAXIMUM) {
            deflectPercentCurrent += DEFLECT_PERCENT_PER_LEVEL;
        }
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
        landModifier = caster.calculateLandModifier(landType);

        int damageOpponent = 0;

        /* Calculam damage-ul dat de adversar. Deoarece enuntul spune ca damage-ul oponentului
        este calculat fata race modiferi, inmultim damage-ul final dat de oponent cu inversul
        race modifer-ului pentru a-i anula efectul. */

        final float inverseBackstabModifier = 1.0f / 1.25f;
        final float inverseParalysisModifier = 1.0f / 1.25f;

        damageOpponent += inverseBackstabModifier
                * rogue.getPrimarySpell().instantDamage(caster, landType);
        damageOpponent += inverseParalysisModifier
                * rogue.getSecondarySpell().instantDamage(caster, landType);

        return Math.round(victimModifier * landModifier * deflectPercentCurrent * damageOpponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int instantDamage(final Knight knight, final LandType landType) {
        victimModifier = KNIGHT_MODIFIER;
        landModifier = caster.calculateLandModifier(landType);

        int damageOpponent = 0;

        /* Calculam damage-ul dat de adversar. Deoarece enuntul spune ca damage-ul oponentului
        este calculat fata race modiferi, inmultim damage-ul final dat de oponent cu inversul
        race modifer-ului pentru a-i anula efectul. */

        final float inverseExecuteModifier = 1.0f / 0.8f;
        final float inverseSlamModifier = 1.0f / 1.05f;

        damageOpponent += inverseExecuteModifier
                * knight.getPrimarySpell().instantDamage(caster, landType);
        damageOpponent += inverseSlamModifier
                * knight.getSecondarySpell().instantDamage(caster, landType);

        return Math.round(victimModifier * landModifier * deflectPercentCurrent * damageOpponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int instantDamage(final Pyromancer pyro, final LandType landType) {
        victimModifier = PYROMANCER_MODIFIER;
        landModifier = caster.calculateLandModifier(landType);

        int damageOpponent = 0;

        /* Calculam damage-ul dat de adversar. Deoarece enuntul spune ca damage-ul oponentului
        este calculat fata race modiferi, inmultim damage-ul final dat de oponent cu inversul
        race modifer-ului pentru a-i anula efectul. */

        final float inverseFireblastModifier = 0.955f;
        final float inverseIgniteModifier = 0.955f;

        damageOpponent += inverseFireblastModifier
                * pyro.getPrimarySpell().instantDamage(caster, landType);
        damageOpponent += inverseIgniteModifier
                * pyro.getSecondarySpell().instantDamage(caster, landType);

        return Math.round(victimModifier * landModifier * deflectPercentCurrent * damageOpponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int instantDamage(final Wizard wizard, final LandType landType) {
        /* Deflect nu are niciun efect asupra unui oponent de tip Wizard, asa ca returnam
        damage-ul zero */

        return 0;
    }
}

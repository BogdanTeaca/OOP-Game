package effects;

/**
 * Clasa pentru efectul over-time al spell-ului Slam.
 *
 * @author TEACA BOGDAN
 */
public class SlamOverTime extends EffectOverTime {
    /**
     * Constructor ce initializeaza proprietatile efectului over-time al spell-ului Slam.
     */
    public SlamOverTime() {
        this.damageOverTime = 0;
        this.canMove = false;
        this.durationRounds = 1;
    }
}

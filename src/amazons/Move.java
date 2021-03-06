package amazons;

/**
 * Move
 */
public class Move {

    public int src_x, src_y, tar_x, tar_y, obs_x, obs_y;

    Move(int src_x, int src_y, int tar_x, int tar_y, int obs_x, int obs_y) {
        this.src_x = src_x;
        this.src_y = src_y;
        this.tar_x = tar_x;
        this.tar_y = tar_y;
        this.obs_x = obs_x;
        this.obs_y = obs_y;
    }

    protected Move clone() {
        return new Move(src_x, src_y, tar_x, tar_y, obs_x, obs_y);
    }

    @Override
    public String toString() {
        return String.format("Move(%d,%d,%d,%d,%d,%d)", src_x, src_y, tar_x, tar_y, obs_x, obs_y);
    }
}
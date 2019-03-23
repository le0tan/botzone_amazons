package amazons;

/**
 * AmazonsFileInput
 */
public class AmazonsFileInput {

    public static Move moveFromInput(String in) {
        String[] nums = in.split(" ");
        Move move = null;
        try {
            move = new Move(Integer.parseInt(nums[0]), 
                            Integer.parseInt(nums[1]), 
                            Integer.parseInt(nums[2]), 
                            Integer.parseInt(nums[3]), 
                            Integer.parseInt(nums[4]), 
                            Integer.parseInt(nums[5]));
        } catch (Exception e) {
            //TODO: handle exception
        }
        return move;
    }

}
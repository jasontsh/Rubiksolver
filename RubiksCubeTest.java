import static org.junit.Assert.*;

import org.junit.Test;


public class RubiksCubeTest {

	@Test
    public void solved(){
        int[][][] array = new int[6][3][3];
        for(int i = 0; i < 6; i++){
            for(int n = 0; n < 3; n++){
                for (int j = 0; j < 3; j++){
                    array[i][n][j] = i;
                }
            }
        }
        RubiksCube rc = new RubiksCube(array);
        CubeCenter[] cc = rc.getCenters();
        assertEquals(cc[0].getSide(1).getColor(0), 0);
        assertEquals(cc[0].getSide(2).getColor(0), 0);
        assertEquals(cc[0].getSide(1).getColor(1), 1);
    }

}

import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.reports.basicreports.GirthSize;
import graphtea.extensions.reports.basicreports.IsBipartite;
import graphtea.extensions.reports.basicreports.IsEulerian;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicReportsTest {
    @Test
    public void testGirthSize() {
        GirthSize girthSize = new GirthSize();
        Assertions.assertEquals((Integer)
                girthSize.calculate(GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2)),5);
    }

    @Test
    public void testIsBipartite() {
        IsBipartite isBipartite = new IsBipartite();
        Assertions.assertTrue((Boolean) isBipartite.calculate(CircleGenerator.generateCircle(4)));
    }

    @Test
    public void testIsEulerian() {
        IsEulerian isEulerian = new IsEulerian();
        Assertions.assertTrue((Boolean) isEulerian.calculate(CircleGenerator.generateCircle(5)));
        Assertions.assertFalse((Boolean) isEulerian.calculate(CompleteGraphGenerator.generateCompleteGraph(5)));
    }



    @Test public void testDominationNumber() {

    }
}
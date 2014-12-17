package cz.tul.cc.mastercurve;

import java.util.ArrayList;
import java.util.List;

/** ************************************************************************
 * Generate MasterCurves of required amount
 *
 * @author wojta
 */
public class MasterCurveGenerator {
    private final double maxYCoord = 0.2;
    private final int numberOfCurves;
    private List<MasterCurveInterface> curves = new ArrayList<>();

    public MasterCurveGenerator(int numberOfCurves) {
        this.numberOfCurves = numberOfCurves;
        _generateCurves();
    }
    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    
    /** ******************************************************************** */
    /** private methods **************************************************** */
    /** ******************************************************************** */
    
    /** *******************************************************************
     * Calculates koeficient <strong>a</strong> tak, aby y bylo max 0.2 
     * y = -ax2+ax = a(-x2+x), pro x=0,5 pak získáme koeficient a
     * y = a(-0,25+0,5) = a(0,25) a pro y <0,2; 0,002> je a = 1/4<0,2; 0,002>
     */
    private void _generateCurves(){
        double step = maxYCoord / numberOfCurves;
        double a = step;
        for (int i = 1; i <= numberOfCurves; i++) {
            curves.add(new MasterCurve(a/4));
            a += step;
        }
    }
 
    public List<MasterCurveInterface> getCurves(){
        return curves;
    }
}

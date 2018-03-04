/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weightcalculator;

/**
 *
 * @author karl
 */
public class WeightCalculatorMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        StabilizerWeightCalc frontWeightMid = new StabilizerWeightCalc(4.0, 0.75, 't', 0.3, 's');
//        StabilizerWeightCalc frontWeightCap = new StabilizerWeightCalc(4.0, 0.75, 't', 0.3, 's');
//        StabilizerWeightCalc backWeightMid = new StabilizerWeightCalc(8.0, 1.0, 't', 0.45, 'm');
//        StabilizerWeightCalc backWeightCap = new StabilizerWeightCalc(8.0, 1.0, 't', 0.45, 'm');
//
//        frontWeightMid.getLengthMidWt();
//        frontWeightCap.getLengthEndWt();
//        backWeightMid.getLengthMidWt();
//        backWeightCap.getLengthEndWt();
        
        StabilizerWeightCalc frontWeight = new StabilizerWeightCalc(6.0, 0.7, 'b', 0.375, 'm');
        frontWeight.getLengthEndWt();

//        StabilizerWeightCalc backWeight = new StabilizerWeightCalc(18.0, 1.0, 't', 0.4, 'l');
//        backWeight.getLengthEndWt();
//
//        StabilizerWeightCalc frontWeightThru = new StabilizerWeightCalc(2.81, 0.754, 't', 0, 'n', 2.0, 0.005);
//        frontWeightThru.getLengthThruHole();

    }

}

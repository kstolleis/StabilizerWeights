/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weightcalculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author karl
 */
public class StabilizerWeightCalc {

    // Material densities in ounces per cubic inch
    public final double H17TU_DENS = 9.82662;       // H17 Tungsten
    public final double SS316_DENS = 4.64;          // 316 Stainless Steel
    public final double BR932_DENS = 5.152;         // 932 Bearing Bronze
    public final double DRILL_DIA = 0.280;          // Diameter of tap drill hole (oversize to account for threads)
    public final double THRU_DRILL_DIA = 5.0 / 16.0;
    public final double SHORT_STUD = 0.15;
    public final double MED_STUD = 0.19;          // Weight of 0.75" stud
    public final double LONG_STUD = 0.30;
    public final double XLONG_STUD = 0.40;           // Weight of 1.5" stud

    private final double diameter;        // Final weight diameter in inches
    private double length;                // Final weight length in inches
    private double lengthInit;            // First guess for through hole length
    private final double weight;          // Final weight desired in ounces
    private double tolerance;
    private double density;               // Density of material chosen
    private double depth;           // Depth of 5/16 drill hole
    private double drillWt;               // Weight of drill hole material removed
    private double studWt;                // Weight of the stud to be used
    private double studLen;
    private String material;
    private DecimalFormat lf = new DecimalFormat("0.000");
    private DecimalFormat wf = new DecimalFormat("0.00");

    public StabilizerWeightCalc(double w, double d, char m, double h, char s) {
        switch (m) {
            case 't':
                density = H17TU_DENS;
                material = "Tungsten";
                break;
            case 's':
                density = SS316_DENS;
                material = "Stainless";
                break;
            case 'b':
                density = BR932_DENS;
                material = "Bronze";
                break;
            default:
                density = -1.0;
                break;
        }
        switch (s) {
            case 'x':
                studWt = XLONG_STUD;
                studLen = 1.5;
                break;
            case 'l':
                studWt = LONG_STUD;
                studLen = 1.0;
                break;
            case 'm':
                studWt = MED_STUD;
                studLen = 0.75;
                break;
            case 's':
                studWt = SHORT_STUD;
                studLen = 0.5;
                break;
            case 'n':
                studWt = 0.0;
                studLen = 0.0;
                break;
            default:
                studWt = -1.0;
                break;
        }
        weight = w;
        diameter = d;
        depth = h;
        length = -1.0;

        lf.setRoundingMode(RoundingMode.HALF_UP);
    }

    public StabilizerWeightCalc(double w, double d, char m, double h, char s, double l, double t) {
        this(w, d, m, h, s);
        lengthInit = l;
        tolerance = t;
    }

    public void getLengthMidWt() {
        findLengthMid();
        printResult();
    }

    public void getLengthEndWt() {
        findLengthEnd();
        printResult();
    }

    public void getLengthThruHole() {
        findLengthThruHole();
        printResult();
    }

    private void printResult() {
        System.out.println("Material \t" + material);
        System.out.println("Weight \t\t" + wf.format(weight) + " oz");
        System.out.println("Diameter \t" + lf.format(diameter) + "\"");
        System.out.println("Length \t\t" + lf.format(length) + "\"");
        if(depth != length){
            System.out.println("Drill Depth \t" + lf.format(depth) + "\"");
        } else {
            System.out.println("Drill Depth \tthru");
        }
        if (studLen == 0.0){
            System.out.println("Stud Length \tnone");
        } else {
            System.out.println("Stud Length \t" + lf.format(studLen) + "\"\n");
        }
    }

    private void findLengthEnd() {
        drillHoleWeight();
        double num = weight + drillWt - studWt;
        double dd = diameter / 2;
        double den = Math.PI * dd * dd * density;
        length = num / den;
    }

    private void findLengthMid() {
        drillHoleWeight();
        double num = weight + drillWt + drillWt - studWt;
        double dd = diameter / 2;
        double den = Math.PI * dd * dd * density;
        length = num / den;
    }

    private void findLengthThruHole() {
        int count = 0;
        double tL = weight - tolerance;
        double tH = weight + tolerance;
        double l = lengthInit;
        double w = cylinderWeight(l) - drillThruHoleWeight(l);
        while (w < tL || w > tH) {
            if (w < weight) {
                l = l + 0.001;
                w = cylinderWeight(l) - drillThruHoleWeight(l);
            } else {
                l = l / 2;
                w = cylinderWeight(l) - drillThruHoleWeight(l);
            }
            count++;
        }
        length = l;
        depth = l;
    }

    private void drillHoleWeight() {
        double r = DRILL_DIA / 2;
        drillWt = Math.PI * r * r * depth * density;
    }

    private double drillThruHoleWeight(double len) {
        double r = THRU_DRILL_DIA / 2;
        return Math.PI * r * r * len * density;
    }

    private double cylinderWeight(double len) {
        double r = diameter / 2;
        return Math.PI * r * r * len * density;
    }

}

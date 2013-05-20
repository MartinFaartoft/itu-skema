package dk.itu.stackmob;

import com.stackmob.sdk.model.StackMobModel;

public class SMFirstYearTestTuple extends StackMobModel {
    private String courseName;
    private int semester;
    private Double ectsRequired;

    public SMFirstYearTestTuple(String courseName, int semester, Double ectsRequired) {
        super(SMFirstYearTestTuple.class);
        this.courseName = courseName;
        this.semester = semester;
        this.ectsRequired = ectsRequired;
    }
}

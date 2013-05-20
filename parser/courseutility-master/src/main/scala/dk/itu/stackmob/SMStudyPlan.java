package dk.itu.stackmob;

import com.stackmob.sdk.model.StackMobModel;

public class SMStudyPlan extends StackMobModel {

    private String name;
    private String grad;
    private boolean startsInFall;
    private SMStudyPlanCourse[] courses;
    private Double studyActivityPerYear;
    private SMFirstYearTestTuple[] firstYearTest;

    public SMStudyPlan(String name, String grad, boolean startsInFall, SMStudyPlanCourse[] courses, Double studyActivityPerYear, SMFirstYearTestTuple[] firstYearTest) {
        super(SMStudyPlan.class);
        this.name = name;
        this.grad = grad;
        this.startsInFall = startsInFall;
        this.courses = courses;
        this.studyActivityPerYear = studyActivityPerYear;
        this.firstYearTest = firstYearTest;
    }
}
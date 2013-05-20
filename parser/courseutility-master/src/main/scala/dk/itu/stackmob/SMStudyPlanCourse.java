package dk.itu.stackmob;

import com.stackmob.sdk.model.StackMobModel;

public class SMStudyPlanCourse extends StackMobModel {

    private String name;
    private Double ects;
    private int semester;
    private int[] genericCourseIds;
    private boolean runsInSpring;
    private boolean runsInFall;

    public SMStudyPlanCourse(String name, Double ects, int semester, int[] genericCourseIds, boolean runsInSpring, boolean runsInFall) {
        super(SMStudyPlanCourse.class);
        this.name = name;
        this.ects = ects;
        this.semester = semester;
        this.genericCourseIds = genericCourseIds;
        this.runsInSpring = runsInSpring;
        this.runsInFall = runsInFall;
    }
}

package dk.itu.stackmob;

import com.stackmob.sdk.model.StackMobModel;

// We need a JavaClass Course because we use it for saving to StackMob
public class SMGenericCourse extends StackMobModel {

    private String name;
    private Double ects;
    private String grad;
    private boolean runsInSpring;
    private boolean runsInFall;
    private String requirements;
    private int[] courseIds;
    private String lastRun;
    private int[] preReqIds;
    private String[] tags;

    public SMGenericCourse(String name, Double ects, String grad, boolean runsInSpring, boolean runsInFall, String requirements, int[] courseIds, String lastRun, int[] preReqIds, String[] tags) {
        super(SMGenericCourse.class);
        this.name = name;
        this.ects = ects;
        this.grad = grad;
        this.runsInSpring = runsInSpring;
        this.runsInFall = runsInFall;
        this.requirements = requirements;
        this.courseIds = courseIds;
        this.lastRun = lastRun;
        this.preReqIds = preReqIds;
        this.tags = tags;
    }
}
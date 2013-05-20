package dk.itu.stackmob;

import com.stackmob.sdk.model.StackMobModel;

// We need a JavaClass Course because we use it for saving to StackMob
public class SMCourse extends StackMobModel {

    private String name;
    private Double ects;
    private String url;
    private String blog;
    private String requirements;
    private String language;
    private String education;
    private String grad;
    private String location;
    private String days;
    private String time;
    private String semester;

    public SMCourse(String name, Double ects, String url, String blog, String requirements, String language, String education, String grad, String location, String days, String time, String semester) {
        super(SMCourse.class);
        this.name = name;
        this.ects = ects;
        this.url = url;
        this.blog = blog;
        this.requirements = requirements;
        this.language = language;
        this.education = education;
        this.grad = grad;
        this.location = location;
        this.days = days;
        this.time = time;
        this.semester = semester;
    }
}

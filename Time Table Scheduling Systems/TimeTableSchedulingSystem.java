import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class TimeSlot {
    private int slotNumber;
    private boolean isBooked;

    TimeSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.isBooked = false;
    }


    public int getSlotNumber() {
        return slotNumber;
    }


    public boolean isBooked() {
        return isBooked;
    }


    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}



class Course {
    private String name;
    private List<TimeSlot> timeSlots;
    private Type type;
    private int duration;


    enum Type {
        LECTURE, TUTORIAL, LAB
    }


    Course(String name, Type type, int duration) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.timeSlots = new ArrayList<>();
    }


    public void addTimeSlot(TimeSlot timeSlot) {
        timeSlots.add(timeSlot);
    }


    public String getName() {
        return name;
    }


    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }


    public Type getType() {
        return type;
    }


    public int getDuration() {
        return duration;
    }
}


class Scheduler {
    private List<TimeSlot> slots;
    private Set<String> scheduledCourseNames;

    Scheduler() {
        slots = new ArrayList<>();
        scheduledCourseNames = new HashSet<>();

        for (int i = 1; i <= 12; i++) {
            slots.add(new TimeSlot(i));
        }
    }


    public void scheduleCourses(List<Course> courses) {
        for (Course course : courses) {

            if (scheduledCourseNames.contains(course.getName())) {
                System.out.println("error: the course " + course.getName() + " is already scheduled today.");
                continue;
            }

            boolean scheduled = false;
            switch (course.getType()) {
                case LECTURE:
                    scheduled = scheduleLecture(course);
                    break;
                case TUTORIAL:
                    scheduled = scheduleTutorial(course);
                    break;
                case LAB:
                    scheduled = scheduleLab(course);
                    break;
            }

            if (scheduled) {

                scheduledCourseNames.add(course.getName());
            }
        }
    }


    private boolean scheduleLecture(Course course) {
        for (TimeSlot slot : slots) {
            if (!slot.isBooked() && slot.getSlotNumber() >= 7 && slot.getSlotNumber() <= 12) {
                slot.setBooked(true);
                course.addTimeSlot(slot);
                return true;
            }
        }
        System.out.println("error: unable to schedule lecture for " + course.getName());
        return false;
    }


    private boolean scheduleTutorial(Course course) {

        if (!slots.get(0).isBooked()) {
            slots.get(0).setBooked(true);
            course.addTimeSlot(slots.get(0));
        } else if (!slots.get(5).isBooked()) {
            slots.get(5).setBooked(true);
            course.addTimeSlot(slots.get(5));
        } else {
            System.out.println("error: unable to schedule tutorial for " + course.getName());
            return false;
        }
        return true;
    }


    private boolean scheduleLab(Course course) {
        List<TimeSlot> consecutiveSlots = new ArrayList<>();
        for (int i = 0; i < slots.size(); i++) {
            if (!slots.get(i).isBooked()) {
                consecutiveSlots.add(slots.get(i));

                if (consecutiveSlots.size() == course.getDuration()) {
                    consecutiveSlots.forEach(slot -> {
                        slot.setBooked(true);
                        course.addTimeSlot(slot);
                    });
                    return true;
                }
            } else {

                consecutiveSlots.clear();
            }
        }
        System.out.println("error: unable to schedule lab for " + course.getName());
        return false;
    }
}

public class TimeTableSchedulingSystem {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();


        Course mathLecture = new Course("Math Lecture", Course.Type.LECTURE, 2);
        Course physicsTutorial = new Course("Physics Tutorial", Course.Type.TUTORIAL, 2);
        Course chemistryLab = new Course("Chemistry Lab", Course.Type.LAB, 3);
        Course chemistryLecture = new Course("Chemistry Lecture", Course.Type.LECTURE, 2);


        List<Course> courses = new ArrayList<>();
        courses.add(mathLecture);
        courses.add(physicsTutorial);
        courses.add(chemistryLab);
        courses.add(chemistryLecture);


        scheduler.scheduleCourses(courses);


        courses.forEach(course -> {
            System.out.println("course: " + course.getName());
            course.getTimeSlots().forEach(slot -> System.out.println(" - slot: " + slot.getSlotNumber()));
        });
    }
}

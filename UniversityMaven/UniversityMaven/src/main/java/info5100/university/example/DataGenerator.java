/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example;

import com.github.javafaker.Faker;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.Seat;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author mitalii
 */
public class DataGenerator {
    private static final Faker FAKER = new Faker();
    private static final Map<String, Department> DEPARTMENTS_MAP = new HashMap<String, Department>() {
        {
            put("INFO", new Department("Information Systems"));
            put("CS", new Department("Computer Science"));
            put("INDS", new Department("Industrial"));
            put("ENGM", new Department("Engineering Management"));
        }
    };
    private static final Set<String> SEMESTERS = new HashSet<>(Arrays.asList("Spring", "Summer", "Fall"));
    private static final Random RND = new Random();
    
    public static final Map<String, List<String>> courseIds = new HashMap<>();
    public static final Set<String> courseSchedules = new HashSet<>();
    
    public Map<String, Department> generateData() {
        // Generate Data
        for (final String departmentKey : DEPARTMENTS_MAP.keySet()) {
            Department department = DEPARTMENTS_MAP.get(departmentKey);
            // Courses
            courseIds.put(departmentKey, new ArrayList<>());
            for (int course = 0; course < 4; course++) {
                String courseId = new StringBuilder().append(departmentKey).append(" ").append(FAKER.number().numberBetween(1000, 6000)).toString();
                courseIds.get(departmentKey).add(courseId);
                department.newCourse("Cource - " + courseId, courseId, 2);
 
            }
            // Year Data
            for (int year = 2014; year <= 2020; year++) {
                for (final String semester : SEMESTERS) {
                    String courseSchedule = new StringBuilder().append(semester).append(" ").append(year).toString();
                    courseSchedules.add(courseSchedule);
                    CourseSchedule courseschedule = department.newCourseSchedule(courseSchedule);
                    // Courses
                    for (final String courseId : courseIds.get(departmentKey)) {
                        CourseOffer courseOffer = courseschedule.newCourseOffer(courseId);
                        int numSeats = FAKER.number().numberBetween(50, 200);
                        courseOffer.generatSeats(numSeats);

                        int seatsToFill = FAKER.number().numberBetween(5, numSeats);
                        for (int counter = 0; counter < seatsToFill; counter++) {
                            PersonDirectory personDirectory = department.getPersonDirectory();
                            Person person = personDirectory.newPerson(String.valueOf(FAKER.number().numberBetween(100000, 600000)));
                            StudentDirectory studentDirectory = department.getStudentDirectory();
                            StudentProfile student = studentDirectory.newStudentProfile(person);
                            CourseLoad courseLoad = student.newCourseLoad(courseSchedule);
                            courseLoad.newSeatAssignment(courseOffer);
                        }

                        for (final Seat seat : courseOffer.getSeatlist()) {
                            if(seat.isOccupied()){
                                double min = 1.0, max = 4.0;
                                seat.getSeatassignment().setGrade(min + RND.nextDouble() * max);
                            }
                        }
                    }
                }
            }
        }
        return DEPARTMENTS_MAP;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DataGenerator().generateData();
        
        System.out.println("\n====================== POPULAR COURSES ======================\n");
        for (final String departmentKey : DEPARTMENTS_MAP.keySet()) {
            Department department = DEPARTMENTS_MAP.get(departmentKey);
            System.out.println("========================================");
            System.out.println("Department Name: " + departmentKey);
            System.out.println("========================================");
            for (final String semester : courseSchedules) {
                System.out.println("Semester: " + semester + " -> " + department.mostPopularCourse(semester));
            }
        }
        
        System.out.println("\n========================== REVENUE ==========================\n");
        for (final String departmentKey : DEPARTMENTS_MAP.keySet()) {
            Department department = DEPARTMENTS_MAP.get(departmentKey);
            System.out.println("========================================");
            System.out.println("Department Name: " + departmentKey);
            System.out.println("========================================");
            for (final String semester : courseSchedules) {
                System.out.println("Semester: " + semester + " -> $" + department.calculateRevenuesBySemester(semester));
            }
        }
        
        System.out.println("\n========================== CUM GPA ==========================\n");
        for (final String departmentKey : DEPARTMENTS_MAP.keySet()) {
            Department department = DEPARTMENTS_MAP.get(departmentKey);
            System.out.println("========================================");
            System.out.println("Department Name: " + departmentKey);
            System.out.println("========================================");
            for (final String semester : courseSchedules) {
                for (final String course : department.mostPopularCourse(semester).keySet()) {
                    int numStudents = 0;
                    double avgGpa = 0;
                    for(final Seat seat: department.getCourseSchedule(semester).getCourseOfferByNumber(course).getSeatlist()) {
                        if(seat.isOccupied()) {
                            avgGpa += seat.getSeatassignment().getGrade();
                            ++numStudents;
                        }
                    }
                    System.out.println("Semester: " + semester + " -> Course: " + course + " | Avg. GPA = " + (avgGpa/numStudents));
                }
            }
        }
    }
}

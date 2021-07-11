/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kal bugrara
 */
public class Transcript {
    
    
    HashMap<String, CourseLoad> courseloadlist;
    
    CourseLoad currentcourseload;
    
    public Transcript(){
        
        courseloadlist = new HashMap<String, CourseLoad>();
        
    }
    
    public CourseLoad newCourseLoad(String sem){
        
        currentcourseload = new CourseLoad(sem);
        courseloadlist.put(sem, currentcourseload);
        return currentcourseload;
    }
    
    public CourseLoad getCurrentCourseLoad(){
        
        return currentcourseload;
        
    }
    
    public CourseLoad getCourseLoadBySemester(String semester){
        
        return courseloadlist.get(semester);
        
    }
    
    public double calculateGPA(){
        double finalGPA = 0;
        for (Map.Entry<String, CourseLoad> entry : courseloadlist.entrySet()) {
            String key = entry.getKey();
            CourseLoad courseLoad = entry.getValue();
            int courseCount = 0;
            double sumGrades = 0;
            for(SeatAssignment s:courseLoad.getSeatassignments()){
                //System.out.println(s.getGrade());
                sumGrades += s.getGrade();
                courseCount++;
                //System.out.println(s.getSeat().getCourseoffer().getCourseNumber());
            }
            if(courseCount > 0){
                finalGPA = sumGrades/courseCount;
            }
            // ...
        }
        return finalGPA;
    }
        
    
        
        
}

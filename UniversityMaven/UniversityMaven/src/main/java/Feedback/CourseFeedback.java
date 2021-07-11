/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Feedback;

import info5100.university.example.CourseCatalog.Course;


public class CourseFeedback {
    Course course;
    int assignmnetRating;
    int quizRating;
    int informationRating;
    int qualityRating;
    double avaerageRating = 0; //Final rating
 
    public CourseFeedback(Course course, int a, int b, int c, int d) {
        this.course = course;
         assignmnetRating = a;
         quizRating = b;
         informationRating = c;
         qualityRating = d;
         setAverageRating();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getAssignmnetRating() {
        return assignmnetRating;
    }

    public void setAssignmnetRating(int assignmnetRating) {
        this.assignmnetRating = assignmnetRating;
    }

    public int getQuizRating() {
        return quizRating;
    }

    public void setQuizRating(int quizRating) {
        this.quizRating = quizRating;
    }

    public int getInformationRating() {
        return informationRating;
    }

    public void setInformationRating(int informationRating) {
        this.informationRating = informationRating;
    }

    public int getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(int qualityRating) {
        this.qualityRating = qualityRating;
    }
    
    private void setAverageRating(){
       avaerageRating = (assignmnetRating + qualityRating + informationRating + quizRating)/ 4;
    }
    
    public double getAverageRating(){
        return avaerageRating;
    }
    
}

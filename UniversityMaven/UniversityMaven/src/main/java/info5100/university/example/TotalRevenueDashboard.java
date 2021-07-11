package info5100.university.example;

import info5100.university.example.Department.Department;
import java.awt.Dimension;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class TotalRevenueDashboard extends JPanel {
    private static CategoryDataset createDataset() {
        DataGenerator dg = new DataGenerator();
        Map<String, Department> departments = dg.generateData();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (final String departmentKey : departments.keySet()) {
            Department department = departments.get(departmentKey);
            for (final String semester : dg.courseSchedules) {
                if (semester.contains("2020")) {
                    int revenue = department.calculateRevenuesBySemester(semester);
                    dataset.addValue(revenue, departmentKey, semester);
                }
            }
        }
        return dataset; 
    }
   
    private static void createAndShowGui() {        
        JFreeChart barChart = ChartFactory.createBarChart(
            "Total Revenue",           
            "Semester",            
            "Revenue ($)",            
            createDataset(),          
            PlotOrientation.VERTICAL,           
            true, true, false
        );
        ChartPanel chartPanel = new ChartPanel(barChart);  
        chartPanel.setPreferredSize(new java.awt.Dimension(560 , 367));
        ChartFrame frame = new ChartFrame("Results", barChart);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// (top imports remain unchanged)

public class StudentController {
    @FXML private TextField studentCountField;
    @FXML private VBox inputContainer;
    @FXML private TextArea outputArea;
    @FXML private PieChart pieChart;

    private List<TextField> nameFields = new ArrayList<>();
    private List<TextField> scoreFields = new ArrayList<>();

    @FXML
    private void generateFields() {
        inputContainer.getChildren().clear();
        nameFields.clear();
        scoreFields.clear();
        try {
            int count = Integer.parseInt(studentCountField.getText());
            for (int i = 0; i < count; i++) {
                TextField nameField = new TextField();
                nameField.setPromptText("Name of Student " + (i + 1));
                nameField.setPrefWidth(500);
                nameField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

                TextField scoreField = new TextField();
                scoreField.setPromptText("Score of Student " + (i + 1));
                scoreField.setPrefWidth(500);
                scoreField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

                VBox fieldGroup = new VBox(5);
                fieldGroup.getChildren().addAll(nameField, scoreField);
                inputContainer.getChildren().add(fieldGroup);
                nameFields.add(nameField);
                scoreFields.add(scoreField);
            }
        } catch (NumberFormatException e) {
            outputArea.setText("❗ Please enter a valid number of students.");
        }
    }

    @FXML
    private void generateReport() {
        List<Student> students = new ArrayList<>();
        try {
            for (int i = 0; i < nameFields.size(); i++) {
                String name = nameFields.get(i).getText();
                if (name.trim().isEmpty() || scoreFields.get(i).getText().trim().isEmpty()) {
                    outputArea.setText("❗ Name and score cannot be empty.");
                    return;
                }
                double score = Double.parseDouble(scoreFields.get(i).getText());
                students.add(new Student(name, score));
            }
        } catch (NumberFormatException e) {
            outputArea.setText("❗ Please enter valid numeric scores for all students.");
            return;
        }

        if (students.isEmpty()) {
            outputArea.setText("⚠️ No student data provided.");
            return;
        }

        double sum = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        for (Student s : students) {
            double score = s.getScore();
            sum += score;
            if (score > max) {
                max = score;
                topStudent = s.getName();
            }
            if (score < min) {
                min = score;
                lowStudent = s.getName();
            }
        }

        double average = sum / students.size();
        StringBuilder result = new StringBuilder();
        result.append("📋 Student Report Summary\n=========================\n");
        result.append("👥 Total Students: ").append(students.size()).append("\n");
        result.append("📊 Average Score: ").append(String.format("%.2f", average)).append("\n");
        result.append("🏆 Highest Score: ").append(max).append(" (by ").append(topStudent).append(")\n");
        result.append("📉 Lowest Score: ").append(min).append(" (by ").append(lowStudent).append(")\n");

        outputArea.setText(result.toString());

        pieChart.getData().clear();
        for (Student s : students) {
            pieChart.getData().add(new PieChart.Data(s.getName(), s.getScore()));
        }

        saveReportToFile(result.toString());
        savePieChartAsImage();
    }

    private void saveReportToFile(String report) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Report");
            fileChooser.setInitialFileName("student_report.txt");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                FileWriter writer = new FileWriter(file);
                writer.write(report);
                writer.close();
                notifySuccess("✅ Report saved successfully!");
            }
        } catch (IOException e) {
            outputArea.appendText("\n⚠️ Failed to save report file.");
        }
    }

    private void savePieChartAsImage() {
        try {
            WritableImage image = pieChart.snapshot(new SnapshotParameters(), null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Chart as Image");
            fileChooser.setInitialFileName("chart.png");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                ImageIO.write(bufferedImage, "png", file);
                notifySuccess("✅ Chart image saved successfully!");
            }
        } catch (IOException e) {
            outputArea.appendText("\n⚠️ Failed to save chart image.");
        }
    }

    private void notifySuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileChecker {
    public static void main(String[] args) throws IOException {
        // Define the network shared path
        String sharedPath = "//server/share/folder/";
        
        // Define the list of names
        List<String> names = new ArrayList<>();
        names.add("Aravind");
        names.add("John");
        names.add("BharaniDharan");

        // Get the current date in the specified format
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd_MMM_yyyy"));
        
        // Create the report file
        File reportFile = new File("report.txt");
        FileWriter writer = new FileWriter(reportFile);
        
        // Check each name in the list for the file with the specified format
        for (String name : names) {
            String fileName = name + "_" + formattedDate + ".txt";
            File file = new File(sharedPath + fileName);
            if (file.exists()) {
                writer.write(name + " - Updated\n");
            } else {
                writer.write(name + " - Not Updated\n");
            }
        }
        
        // Close the report file writer
        writer.close();
        
        // Send the report file as an attachment in an email
        String recipient = "recipient@example.com";
        String subject = "File Report";
        String body = "Please find the attached file report.";
        String attachmentPath = reportFile.getAbsolutePath();
        EmailSender.sendEmail(recipient, subject, body, attachmentPath);
    }
}

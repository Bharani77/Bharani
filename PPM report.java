import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CheckFileInNetworkPath {

    public static void main(String[] args) {
        
        // Define network shared path
        String sharedPath = "\\\\servername\\foldername\\subfoldername\\";
        
        // Define file name format with current date
        DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy");
        String fileNameFormat = "BharaniDharan_" + dateFormat.format(new Date());
        
        // Define list of names
        List<String> names = List.of("Aravind", "John", "BharaniDharan");
        
        // Check if file exists for each name
        names.forEach(name -> {
            String fileName = fileNameFormat + "_" + name + ".txt";
            File file = new File(sharedPath + fileName);
            
            // Append name and status to report file
            try (FileWriter writer = new FileWriter("report.txt", true)) {
                if (file.exists()) {
                    writer.write(name + " - Updated\n");
                } else {
                    writer.write(name + " - Not Updated\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        // Read report file and send email
        try {
            String reportContent = Files.lines(Paths.get("report.txt")).collect(Collectors.joining(System.lineSeparator()));
            String subject = "File Check Report";
            String body = "Please find the attached report for file check status.";
            String recipient = "recipient@example.com";
            String attachmentPath = "report.txt";
            
            EmailSender.sendEmail(recipient, subject, body, attachmentPath, reportContent);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}

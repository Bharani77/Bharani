import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NetworkFileChecker {

    public static void main(String[] args) {
        // Initialize file names and network path
        List<String> names = new ArrayList<>();
        names.add("Aravind");
        names.add("John");
        names.add("BharaniDharan");

        String currentDate = new SimpleDateFormat("dd_MMM_yyyy").format(new Date());
        String fileName = names.get(2) + "_" + currentDate;

        String networkPath = "\\\\server\\share\\path\\";

        // Check if file exists in network path
        File file = new File(networkPath + fileName);
        boolean fileExists = file.exists();

        // Write to report file
        try {
            FileWriter reportFileWriter = new FileWriter("report.txt", true);
            if (fileExists) {
                reportFileWriter.write(names.get(2) + " - updated\n");
            } else {
                reportFileWriter.write(names.get(2) + " - not updated\n");
            }
            reportFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read report file and send email
        try {
            File reportFile = new File("report.txt");
            String emailBody = "Network File Check Report:\n\n";
            emailBody += "Name\tStatus\n";
            emailBody += "----------------------\n";

            for (String name : names) {
                boolean foundName = false;
                String status = "not updated";
                String line;
                File fileReader = new File("report.txt");
                java.util.Scanner sc = new java.util.Scanner(fileReader);
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    if (line.startsWith(name)) {
                        foundName = true;
                        if (line.endsWith("updated")) {
                            status = "updated";
                        }
                        break;
                    }
                }
                sc.close();
                if (!foundName) {
                    status = "not found";
                }
                emailBody += name + "\t" + status + "\n";
            }

            EmailSender emailSender = new EmailSender();
            emailSender.setMailServer("smtp.gmail.com");
            emailSender.setMailServerPort("465");
            emailSender.setSenderAddress("sender@gmail.com");
            emailSender.setSenderPassword("password");
            emailSender.setReceiverAddress("receiver@gmail.com");
            emailSender.setSubject("Network File Check Report");
            emailSender.setBody(emailBody);
            emailSender.setAttachment(reportFile);
            emailSender.sendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

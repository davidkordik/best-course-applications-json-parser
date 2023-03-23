import java.io.*;
import java.util.stream.Collectors;

public class MotivationLetterJsonParser {

    private static final String ORIGINAL_PATH = "letters.txt";
    private static final String RESULT_PATH = "applications/";

    public static void main(String[] args) throws IOException {
        String rawText = read();
        StringBuilder textCombined = new StringBuilder("[");
        int applicationNumber = 0;
        int applicationIndex = rawText.indexOf("Application ID");
        while (applicationIndex != -1) {
            int beginningOfApplication = rawText.indexOf("\n", applicationIndex - 47) + 1;
            applicationIndex = rawText.indexOf("\nApplication ID", applicationIndex + 1);
            String applicationText;
            if (applicationIndex == -1) {
                applicationText = rawText.substring(beginningOfApplication);
            }
            else {
                int endOfApplication = rawText.indexOf("----------------Page (", applicationIndex - 100);
                applicationText = rawText.substring(beginningOfApplication, endOfApplication - 1);
            }
            applicationText = applicationText.replaceAll("\\n----------------Page \\([0-9]*\\) Break----------------", "");
            applicationText = toJson(applicationText);
            FileWriter file = new FileWriter(RESULT_PATH + (++applicationNumber) + ".json");
            file.write(applicationText);
            file.close();
            textCombined.append(applicationText);
            textCombined.append(',');
        }
        FileWriter fileCombined = new FileWriter(RESULT_PATH + "0.json");
        textCombined.setLength(textCombined.length() - 1);
        textCombined.append(']');
        fileCombined.write(textCombined.toString());
        fileCombined.close();
    }

    static String read() {
        String result;
        try (InputStream is = new FileInputStream(ORIGINAL_PATH)) {
            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader br = new BufferedReader(isr)) {
                result = br.lines().collect(Collectors.joining("\n"));
            }
        }
        catch (final IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return result;
    }

    static String toJson(String text) {
        text = text.replaceAll("\"", "");
        text = text.replaceAll("\t", " ");
        text = text.replaceAll("\u0010", "");
        text = text.replaceAll("\\\\", "");
        text = "{\"applicant_name\":\"" + text;
        text = jsonKeyReplace(text, "Application ID", "id");
        text = jsonKeyReplace(text, "LBG (Membership status)", "local_best_group");
        text = jsonKeyReplace(text, "University", "university");
        text = jsonKeyReplace(text, "Gender", "gender");
        text = jsonKeyReplace(text, "Birth-date", "date_of_birth");
        text = jsonKeyReplace(text, "Citizenship", "citizenship");
        text = jsonKeyReplace(text, "Applications to previous courses:\n(events listed by starting date with latest event first,\n participated events marked in bold)", "previous_courses");
        text = jsonKeyReplace(text, "Extra curricular activities:", "other_activities");
        text = jsonKeyReplace(text, "Other:", "other");
        text = jsonKeyReplace(text, "In target group for BEST events:", "target_group");
        text = jsonKeyReplace(text, "Comments from LBG:", "comments");
        text = jsonKeyReplace(text, "English level:", "english_level");
        text = jsonKeyReplace(text, "Comment about English level:", "english_level_comment");
        text = jsonKeyReplace(text, "Relevant courses passed:", "relevant_courses");
        text = jsonKeyReplace(text, "Hobbies:", "hobbies");
        text = jsonKeyReplace(text, "Motivation Letter for The Last AI-rbender\n", "motivation_letter");
        text = jsonKeyReplace(text, "Activity specific question\nHow will artificial intelligence impact your life in the future? \n", "question1");
        text = jsonKeyReplace(text, "Which different kinds of AI exist? Describe them. \n", "question2");
        text = jsonKeyReplace(text, "On the third planet from the sun, robots have been made to control one of the four elements (earth, water, air, fire)\nand that’s how it has been for years. But now, one brave scientist created a new type of robot, an Avatar, master of\nall four, the first of its kind. Four masters, also known as &quot;benders&quot;, must face this new powerful robot\nin an epic fight. What do you think, who will win and why? \n", "question3");
        text = text.replaceAll("\\n", "\\\\n");
        return text + "\"}";
    }

    static String jsonKeyReplace(String text, String oldKey, String newKey) {
        return text.replace("\n" + oldKey, "\",\"" + newKey + "\":\"");
    }

}

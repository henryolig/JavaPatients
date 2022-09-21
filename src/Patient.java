import java.io.*;
import java.util.*;


public class Patient {

    String name, Birth, status, firstname, lastname;

    public static void main(String[] args) throws IOException {
        boolean stop = false;
        Scanner in = new Scanner(System.in);
        //deletePatient("Henry Olig", "patients.txt");
        //addPatient("Henry Olig", "13/02/2003", "patients.txt");
        //averageAge("patients.txt");
        //sortPatientsByAge("patients.txt");
        while (!stop){
            System.out.println("Select Operation: ");
            System.out.println("1. Add Patient to file.");
            System.out.println("2. Remove Patient from file.");
            System.out.println("3. Count the number of sick or recovered patients.");
            System.out.println("4. Find average age of all patients.");
            System.out.println("5. Sort patients by age.");
            System.out.println("6. Sort patients by name");
            System.out.println("7. Shuffle patient list");
            System.out.println("8. Exit program");
            int input = in.nextInt();
            switch(input){
                case 1: System.out.println("Input first name: ");
                String inName1 = in.next();
                    System.out.println("Input last name: ");
                    String inLastName1 = in.next();
                System.out.println("Input Birthdate (MM/DD/YYYY): ");

                String inBirth1 = in.next();

                System.out.println("Input the file name: ");
                String inFile1 = in.next();
                addPatient((inName1 + " " + inLastName1), inBirth1, inFile1);

                break;

                case 2: System.out.println("Input first name: ");
                    String inName2 = in.next();
                    System.out.println("Input last name: ");
                    String inLastName2 = in.next();
                    System.out.println("Input the file name: ");
                    String inFile2 = in.next();
                    deletePatient((inName2 + " " + inLastName2), inFile2);

                    break;

                case 3: System.out.println("Input which status you want counted, or nothing for total patient list: ");
                    String inStatus3 = in.next();
                    System.out.println("Input the file name: ");
                    String inFile3 = in.next();
                    countPatients(inStatus3, inFile3);
                    System.out.println(countPatients(inStatus3, inFile3));

                    break;

                case 4: System.out.println("Input the file name: ");
                String inFile4 = in.next();
                averageAge(inFile4);

                 break;

                case 5: System.out.println("Input the file name: ");
                    String inFile5 = in.next();
                    sortPatientsByAge(inFile5);
                    break;

                case 6: System.out.println("Would you like to sort by first or last name?");
                String inName6 = in.next();
                System.out.println("Input the file name: ");
                String inFile6 = in.next();
                sortPatientsByName(inName6, inFile6);

                break;

                case 7: System.out.println("Input the file name: ");
                    String inFile7 = in.next();
                    shufflePatients(inFile7);

                    break;

                case 8:
                    stop = true;
                    break;
                }
            }

        System.out.println("System closed.");

        }

    public Patient(String name, String Birth, String status) {
        this.name = name;
        this.Birth = Birth;
        this.status = status;
    }

    public Patient(String firstname, String lastname, String Birth, String status) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.Birth = Birth;
        this.status = status;
    }

    public String getName(){
        return name;
    }

    public String getBirth(){
        return Birth;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String s){
        status = s;
    }

    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }

    public static String isBirthValid(int day, int month, int year) {
        //check if the given birth is valid.
        int feb = 28;
        if (year % 4 == 0) {
            feb = 29;
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day >= 1 && day <= 31) {
                return "Yes";
            } else return "No";
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day >= 1 && day <= 30) {
                return "Yes";
            } else return "No";
        } else if (month == 2) {
            if (day >= 1 && day <= feb) {
                return "Yes";
            } else return "No";
        } else return "No";
    }

    public static void addPatient(String name, String Birth, String fileName) throws IOException {

        int Day = Integer.parseInt(Birth.substring(3, 5));
        int Month = Integer.parseInt(Birth.substring(0, 2));
        int Year = Integer.parseInt(Birth.substring(6, 10));
        try {
            if (isBirthValid(Day, Month, Year).equals("Yes")) {
                FileWriter output = new FileWriter(fileName, true);
                PrintWriter out = new PrintWriter(output, true);
                out.println(name + " " + Birth + " sick");
            } else System.out.println("Patient could not be added");
        } catch (FileNotFoundException exp) {
            System.out.println("File could not be found");
        }

        // Add a new patient record to the file.
        // if given birth is not valid, then patient will not be added into the file.
        // Birth must save in a format of Month/Day/Year, in total length of 10, such
        //as “02/03/2022”, “11/12/2001”, “01/24/1998”, “12/01/1980” and so on.
    }

    public static void deletePatient(String name, String fileName) {
        try {
            File inputFile = new File(fileName);
            File newFile = new File("tempFile.txt");
            BufferedReader input = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
            String str;
            while ((str = input.readLine()) != null) {
                //if(input.readLine().equals(name) && input.readLine().endsWith("recover")){}
                //else if(name.equals(in.nextLine().substring(0, name.length())) && in.nextLine().endsWith("sick")){
                //System.out.println("You cannot delete a sick patient!");
                //}
                String trimmedLine = str.trim();
                if (trimmedLine.substring(0, name.length()).equals(name)) continue;
                writer.write(str + System.getProperty("line.separator"));
            }
            boolean result = newFile.renameTo(inputFile);
            input.close();
            writer.close();
        }
        //newFile.renameTo(inputFile);
        catch (IOException e) {
            System.out.println("File could not be found!");
        }
        // Delete an existing patient record from the file
        // can’t delete if the patient still sick.
        // if there are two patients occur with the same name, and they are both not
        //sick, only delete the first one. Otherwise, delete the first patient who is not
        //sick.
    }


    public static int countPatients(String status, String fileName) throws FileNotFoundException {
        int pCount = 0;
        File inputFile = new File(fileName);
        Scanner in = new Scanner(inputFile);
        if (status.equals("sick")) {
            while (in.hasNext()) {
                if (in.next().equals("sick")) {
                    pCount++;
                }
            }
        } else if (status.equals("recover")) {
            while (in.hasNext()) {
                if (in.next().equals("recover")) {
                    pCount++;
                }
            }
        } else if (status.equals("")) {
            while (in.hasNextLine()) {
                pCount++;
            }
        }

        return pCount;
        // return numbers of sick patients or recovery patients in the file.
        // if client given “” (empty string), then return the total number of patients.
    }

    public static void averageAge(String fileName) throws FileNotFoundException {
        // find the average age for all patients in the file.
        int pCount = 0;
        double lCount = 0;
        File inputFile = new File(fileName);
        Scanner in = new Scanner(inputFile);
        Set<Integer> numLocate = new HashSet<>(Arrays.asList(0, 1, 2, 3));
        while (in.hasNext()) {
            String line = in.next();
            try {
                if (numLocate.contains(Integer.parseInt(line.substring(0, 1)))) {
                    pCount += Integer.parseInt(line.substring(6, 10));
                }
            } catch (NumberFormatException exp) {
                lCount++;
            }
        }
        int avgAge = (int) (2022 - (pCount / (lCount / 3)));
        System.out.println("The average age of patients is " + avgAge + " years old");

    }

    public static void sortPatientsByAge(String fileName) throws IOException {
        // modify file by sorting patients by age for all patients from young to old
        ArrayList<Patient> patientList = new ArrayList<>();

        String fName = "";
        String lName = "";
        String age = "";
        String status = "";


        File newFile = new File("tempFile.txt");

        FileWriter output = new FileWriter(newFile, true);
        PrintWriter out = new PrintWriter(output, true);
        File inputFile = new File(fileName);

        Scanner in = new Scanner(inputFile);
        int index = 0;
        while (in.hasNext()) {
            if (index == 0) {
                fName = in.next();
                index++;
            } else if (index == 1) {
                lName = in.next();
                index++;
            } else if (index == 2) {
                age = in.next();
                index++;
            } else if (index == 3) {
                status = in.next();
                patientList.add(new Patient((fName + " " + lName), age, status));
                index = 0;
            }
        }
        Collections.sort(patientList, Patient.PatientComparator);


            for (int i = 0; i < patientList.size(); i++) {
                out.println(patientList.get(i).getName() + " " + patientList.get(i).getBirth() + " " + patientList.get(i).getStatus());
            }
            in.close();
            out.close();
            boolean delete = inputFile.delete();
            boolean success = newFile.renameTo(inputFile);

    }

    public static Comparator<Patient> PatientComparator = new Comparator<Patient>() {
        @Override
        public int compare(Patient o1, Patient o2) {
            String pAge1 = o1.getBirth();
            String pAge2 = o2.getBirth();
                int pYear1 = Integer.parseInt(pAge1.substring(6, 10));
                int pYear2 = Integer.parseInt(pAge2.substring(6, 10));
                int pMonth1 = Integer.parseInt(pAge1.substring(0, 2));
                int pMonth2 = Integer.parseInt(pAge2.substring(0, 2));
                int pDay1 = (Integer.parseInt(pAge1.substring(3, 5)));
                int pDay2 = (Integer.parseInt(pAge2.substring(3, 5)));
                if (pYear1 == pYear2) {
                    if (pMonth1 == pMonth2) {
                        return (pDay1 - pDay2);
                    } else return (pMonth1 - pMonth2);
                } else return (pYear1 - pYear2);
        }
    };


    public static void sortPatientsByName(String firstOrLast, String fileName) throws FileNotFoundException, IOException {
        // modify file by sorting patients by first name or last name for all patients from
        // a~z
        // modify file by sorting patients by age for all patients from young to old
        ArrayList<Patient> patientList = new ArrayList<>();

        String fName = "";
        String lName = "";
        String age = "";
        String status = "";
        String fol = firstOrLast.toLowerCase();


        File newFile = new File("tempFile.txt");


        FileWriter output = new FileWriter(newFile, true);
        PrintWriter out = new PrintWriter(output, true);
        File inputFile = new File(fileName);

        Scanner in = new Scanner(inputFile);
        int index = 0;
        while (in.hasNext()) {
            if (index == 0) {
                fName = in.next();
                index++;
            } else if (index == 1) {
                lName = in.next();
                index++;
            } else if (index == 2) {
                age = in.next();
                index++;
            } else if (index == 3) {
                status = in.next();
                patientList.add(new Patient(fName, lName, age, status));
                index = 0;
            }
        }
        if(fol.equals("first")) {
            Collections.sort(patientList, Patient.PatientFNameComparator);
        }
        else if(fol.equals("last")){
            Collections.sort(patientList, Patient.PatientLNameComparator);
        }
        else{ System.out.println("You can only input 'first' or 'last' "); }


        for (int i = 0; i < patientList.size(); i++) {
            out.println(patientList.get(i).getFirstname() + " " + patientList.get(i).getLastname() + " " + patientList.get(i).getBirth() + " " + patientList.get(i).getStatus());
        }
        in.close();
        out.close();
        boolean delete = inputFile.delete();
        boolean success = newFile.renameTo(inputFile);
    }


    public static Comparator<Patient> PatientFNameComparator = new Comparator<Patient>() {
        @Override
        public int compare(Patient o1, Patient o2) {
            String pName1 = o1.getFirstname().toUpperCase();
            String pName2 = o2.getFirstname().toUpperCase();
            return pName1.compareTo(pName2);
        }
    };

    public static Comparator<Patient> PatientLNameComparator = new Comparator<Patient>() {
        @Override
        public int compare(Patient o1, Patient o2) {
            String pName1 = o1.getLastname().toUpperCase();
            String pName2 = o2.getLastname().toUpperCase();
            return pName1.compareTo(pName2);
        }
    };

    public static void shufflePatients(String fileName)throws FileNotFoundException, IOException {
        // modify file by shuffle all patients, so they are not in any order
        // Using random in this method is required.
        ArrayList<Patient> patientList = new ArrayList<>();

        String fName = "";
        String lName = "";
        String age = "";
        String status = "";


        File newFile = new File("tempFile.txt");

        FileWriter output = new FileWriter(newFile, true);
        PrintWriter out = new PrintWriter(output, true);
        File inputFile = new File(fileName);

        Scanner in = new Scanner(inputFile);
        int index = 0;
        while (in.hasNext()) {
            if (index == 0) {
                fName = in.next();
                index++;
            } else if (index == 1) {
                lName = in.next();
                index++;
            } else if (index == 2) {
                age = in.next();
                index++;
            } else if (index == 3) {
                status = in.next();
                patientList.add(new Patient((fName + " " + lName), age, status));
                index = 0;
            }
        }

        Random r1 = new Random();

        for (int i = 0; i < patientList.size(); i++) {
            Collections.swap(patientList, i, r1.nextInt(i + 1));
            
        }

        for (int i = 0; i < patientList.size(); i++) {
            out.println(patientList.get(i).getName() + " " + patientList.get(i).getBirth() + " " + patientList.get(i).getStatus());
        }
        in.close();
        out.close();
        boolean delete = inputFile.delete();
        boolean success = newFile.renameTo(inputFile);
    }

}




















import java.util.*;
import java.io.*;


class InvalidInputException extends Exception {
    public InvalidInputException() {
        System.out.println("\nError: Invalid Input.");
    }
}


class MinimumAgeException extends Exception {
    public MinimumAgeException() {
        System.out.println("\nError: The Entered Age is Below Minimum Age.");
    }
}


class RecordNotFoundException extends Exception {
    public RecordNotFoundException() {
        System.out.println("\nError: Record Not Found.");
    }
}


class patient {

    private String name;
    private int age;
    private String disease;
    private String doctorName;
    private String dateAdmitted = "YYYY-MM-DD";
    private String bloodGroupRequired;


    public patient() {

    }



    public patient(String Name, int Age, String Disease, String DoctorName, String DateAdmitted,
            String BloodGroupRequired) {

        setName(Name);
        setAge(Age);
        setDisease(Disease);
        setDoctorName(DoctorName);
        setDateAdmitted(DateAdmitted);
        setBloodGroupRequired(BloodGroupRequired);

    }



    public void setName(String Name) {
        this.name = Name;
    }



    public void setAge(int Age) {
        this.age = Age;
    }



    public void setDisease(String Disease) {
        this.disease = Disease;
    }



    public void setDoctorName(String DoctorName) {
        this.doctorName = DoctorName;
    }



    public void setDateAdmitted(String DateAdmitted) {
        this.dateAdmitted = DateAdmitted;
    }



    public void setBloodGroupRequired(String BloodGroupRequired) {
        this.bloodGroupRequired = BloodGroupRequired;
    }



    public String getName() {
        return this.name;
    }



    public int getAge() {
        return this.age;
    }



    public String getDisease() {
        return this.disease;
    }



    public String getDoctorName() {
        return this.doctorName;
    }



    public String getDateAdmitted() {
        return this.dateAdmitted;
    }



    public String getBloodGroupRequired() {
        return this.bloodGroupRequired;
    }



    public void display() {
        System.out.println("""
                -------------------
                Patient Details
                -------------------
                """);

        System.out.println("Name                        :      " + this.name);
        System.out.println("Age                         :      " + this.age);
        System.out.println("Disease                     :      " + this.disease);
        System.out.println("Doctor Name                 :      " + this.doctorName);
        System.out.println("Date Admitted (YYYY-MM-DD)  :      " + this.dateAdmitted);
        System.out.println("Blood Group Required        :      " + this.bloodGroupRequired);
    }




    public void addPatientRecord() {
        Scanner sc = new Scanner(System.in);

        Boolean moreRecord = true;



        while (moreRecord == true) {
            try {
                System.out.println(
                        """

                                ----------------------------------------------------------------------------------------------------
                                ------------------------
                                Enter Patient Details
                                ------------------------
                                """);

                System.out.print("Name                       :      ");
                String name = sc.nextLine();

                System.out.print("Age                        :      ");
                String age = sc.nextLine();

                System.out.print("Disease                    :      ");
                String disease = sc.nextLine();

                System.out.print("Doctor Name                :      ");
                String DoctorName = sc.nextLine();

                System.out.print("Date Admitted (YYYY-MM-DD) :      ");
                String DateAdmitted = sc.nextLine();

                System.out.print("Blood Group Required       :      ");
                String BloodGroupRequired = sc.nextLine();

                patient patient = new patient(name, Integer.parseInt(age), disease, DoctorName, DateAdmitted,
                        BloodGroupRequired);

                try {
                    patient.addPatientRecordToFile();
                    patient.display();
                    System.out.println("Patient Record Added Succesfully.");

                    System.out.println("\n");

                    System.out.println("Do You Want to Add Another Patient Record?");
                    System.out.print("Enter Y or N: ");
                }

                catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }

            catch (Exception e) {
                System.out.println("Error: " + e);
            }

            try {
                String user_input = sc.nextLine();
                if (user_input.equalsIgnoreCase("N")) {
                    moreRecord = false;
                }

                else if (user_input.equalsIgnoreCase("Y")) {
                    continue;
                }

                else {
                    throw new InvalidInputException();
                }
            }

            catch (InvalidInputException e) {
                System.out.println("Error: " + e);
            }
        }
    }




    public void addPatientRecordToFile() throws IOException {

        String outputText = this.getName() + "|" + this.getAge() + "|" + this.getDisease() + "|"
                + this.getDoctorName() + "|" + this.getDateAdmitted() + "|" + this.getBloodGroupRequired();

        File patientRecords = new File("patientRecords.txt");
        if (patientRecords.exists()) {
            FileWriter fileWriter = new FileWriter(patientRecords, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(outputText);
            printWriter.close();
        }

        else {
            FileWriter fileWriter = new FileWriter(patientRecords);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(outputText);
            printWriter.close();
        }
    }




    public ArrayList<patient> getPatientRecordFromFile() throws FileNotFoundException {
        File patientRecords = new File("patientRecords.txt");
        Scanner sc = new Scanner(patientRecords);

        ArrayList<patient> patientList = new ArrayList<patient>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] detail = line.split("\\|");
            patient patient = new patient(detail[0], Integer.parseInt(detail[1]), detail[2], detail[3], detail[4],
                    detail[5]);
            patientList.add(patient);
        }
        return patientList;
    }




    public void searchPatientWithName() {
        Scanner sc = new Scanner(System.in);

        patient patientDriver = new patient();

        ArrayList<patient> patientList = new ArrayList<patient>();

        try {
            patientList = patientDriver.getPatientRecordFromFile();
        }

        catch (Exception e) {
            System.out.println("Error: " + e);
        }

        System.out.println("""

                ----------------------------------------------------------------------------------------------------

                -----------------------------
                Search Patient With Name
                -----------------------------
                """);

        System.out.print("Enter Patient Name: ");
        String patientName = sc.nextLine();

        System.out.println("\n");
        try {
            int patientFound = 0;
            for (int i = 0; i <= patientList.size() - 1; i++) {
                if (patientList.get(i).getName().equalsIgnoreCase(patientName)) {
                    patientFound++;
                    patientList.get(i).display();
                    System.out.print(
                            "--------------------------------------------------------\n\n");
                }
            }
            if (patientFound == 0) {
                throw new RecordNotFoundException();
            }
        }

        catch (RecordNotFoundException RNFE) {

        }

        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }




    public void getpatientListAscendingOrder() {
        Scanner sc = new Scanner(System.in);

        patient patientDriver = new patient();

        ArrayList<patient> patientList = new ArrayList<patient>();
        try {
            patientList = patientDriver.getPatientRecordFromFile();
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }

        System.out.println("""


                ----------------------------------------------------------------------------------------------------

                -------------------------------------------------------------------------------------------
                List of Patients With Blood Group Required And Admitted Date Sorted in Ascending Order
                -------------------------------------------------------------------------------------------
                """);

        try {
            for (int i = 0; i <= patientList.size() - 1; i++) {
                int minimum_index = i;
                for (int j = i + 1; j <= patientList.size() - 1; j++) {
                    if (patientList.get(j).getName().compareTo(patientList.get(minimum_index).getName()) < 0) {
                        minimum_index = j;
                        patient temp = patientList.get(minimum_index);
                        Collections.swap(patientList, minimum_index, i);
                        patientList.set(i, temp);
                    }
                }
            }

            for (int i = 0; i <= patientList.size() - 1; i++) {

                System.out.println("Name                      :      " + patientList.get(i).getName());
                System.out.println("Blood Group Required      :      " + patientList.get(i).getBloodGroupRequired());
                System.out.println("Date Admitted             :      " + patientList.get(i).getDateAdmitted());
                System.out.println(
                        "--------------------------------------------------------\n");

            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }



    public void getSuitableDonors() {
        Scanner sc = new Scanner(System.in);

        patient patientDriver = new patient();

        ArrayList<patient> patientList = new ArrayList<patient>();
        try {
            patientList = patientDriver.getPatientRecordFromFile();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        donor donorDriver = new donor();

        ArrayList<donor> donorList = new ArrayList<donor>();
        try {
            donorList = donorDriver.getDonorRecordFromFile();

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("""

                ----------------------------------------------------------------------------------------------------

                ----------------------------------------------
                Get List of Suitable Donors For a Patient
                ----------------------------------------------
                """);

        System.out.print("Enter Patient Name: ");
        String patientName = sc.nextLine();

        try {
            int patientFound = 0;
            int donorFound = 0;

            int recordNumber = 0;

            for (int i = 0; i <= patientList.size() - 1; i++) {
                if (patientList.get(i).getName().equalsIgnoreCase(patientName)) {
                    patientFound++;
                    recordNumber = i;
                    break;
                }
            }
            if (patientFound == 0) {
                throw new RecordNotFoundException();
            }

            for (int j = 0; j <= donorList.size() - 1; j++) {
                if (patientList.get(recordNumber).getBloodGroupRequired()
                        .equalsIgnoreCase(donorList.get(j).getBloodGroup())) {
                    donorFound++;

                    System.out.println(
                            "--------------------------------------------------------\n\n");
                    donorList.get(j).display();
                }
            }
            if (donorFound == 0) {
                System.out.println("No Patient Found With Required Blood Group.");
            }
        }

        catch (RecordNotFoundException RNFE) {

        }

        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }



    public void getPatientsRequireEnteredBloodGroup() {
        Scanner sc = new Scanner(System.in);

        patient patientDriver = new patient();

        ArrayList<patient> patientList = new ArrayList<patient>();
        try {
            patientList = patientDriver.getPatientRecordFromFile();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        System.out.println("""
                ----------------------------------------------------------------------------------------------------

                --------------------------------------------------------------
                Get List of Patients That Require the Entered Blood Group
                --------------------------------------------------------------
                """);

        System.out.print("Enter Blood Group: ");
        String patientBloodGroupRequired = sc.nextLine();

        try {

            int patientFound = 0;
            for (int i = 0; i <= patientList.size() - 1; i++) {
                if (patientList.get(i).getBloodGroupRequired().equalsIgnoreCase(patientBloodGroupRequired)) {
                    patientFound++;
                    System.out.println(
                            "--------------------------------------------------------\n");
                    patientList.get(i).display();
                }
            }
            if (patientFound == 0) {
                throw new RecordNotFoundException();
            }
        }

        catch (RecordNotFoundException RNFE) {

        }
    }
}



class donor {

    private String name;
    private int age;
    private String anyDisease;
    private String bloodGroup;


    public donor() {

    }



    public donor(String Name, int Age, String AnyDisease, String BloodGroup) {
        setName(Name);
        setAge(Age);
        setAnyDisease(AnyDisease);
        setBloodGroup(BloodGroup);
    }



    public void setName(String Name) {
        this.name = Name;
    }



    public void setAge(int Age) {
        this.age = Age;
    }



    public void setAnyDisease(String AnyDisease) {
        this.anyDisease = AnyDisease;
    }



    public void setBloodGroup(String BloodGroup) {
        this.bloodGroup = BloodGroup;
    }



    public String getName() {
        return this.name;
    }



    public int getAge() {
        return this.age;
    }



    public String getAnyDisease() {
        return this.anyDisease;
    }



    public String getBloodGroup() {
        return this.bloodGroup;
    }



    public void display() {
        System.out.println("""
                -------------------
                Donor Details
                -------------------
                """);

        System.out.println("Name           :    " + this.name);
        System.out.println("Age            :    " + this.age);
        System.out.println("Any Disease    :    " + this.anyDisease);
        System.out.println("Blood Group    :    " + this.bloodGroup);

    }




    public void addDonorRecord() {
        Scanner sc = new Scanner(System.in);
        Boolean moreRecord = true;
        while (moreRecord == true) {
            try {
                System.out.println(
                        """

                                ----------------------------------------------------------------------------------------------------

                                ------------------------
                                Enter Donor Details
                                ------------------------
                                """);

                System.out.print("Name          :    ");
                String name = sc.nextLine();

                System.out.print("Age           :    ");
                String age = sc.nextLine();

                if (Integer.parseInt(age) < 20) {
                    throw new MinimumAgeException();
                }

                System.out.print("Any Disease   :   ");
                String AnyDisease = sc.nextLine();

                System.out.print("Blood Group   :   ");
                String BloodGroup = sc.nextLine();

                donor donor = new donor(name, Integer.parseInt(age), AnyDisease, BloodGroup);
                donor.display();
                System.out.println("Donor Record Added Succesfully.");

                try {
                    donor.addDonorRecordToFile();
                }

                catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }

            catch (MinimumAgeException MAE) {

            }

            catch (Exception e) {
                System.out.println("Error: " + e);
            }

            System.out.println("\n");

            System.out.println("Do You Want to Add Another donor Record?");
            System.out.print("Enter Y or N: ");

            try {
                String user_input = sc.nextLine();
                if (user_input.equalsIgnoreCase("N")) {
                    moreRecord = false;
                }

                else if (user_input.equalsIgnoreCase("Y")) {
                    continue;
                }

                else {
                    throw new InvalidInputException();
                }
            } catch (InvalidInputException IIE) {

            }

            catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }

    }




    public void addDonorRecordToFile() throws IOException {
        String outputText = this.getName() + "|" + this.getAge() + "|" + this.getAnyDisease() + "|"
                + this.getBloodGroup();

        File donorRecords = new File("donorRecords.txt");

        if (donorRecords.exists()) {

            FileWriter fileWriter = new FileWriter(donorRecords, true);

            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(outputText);
            printWriter.close();
        }

        else {
            FileWriter fileWriter = new FileWriter(donorRecords);

            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(outputText);
            printWriter.close();
        }
    }




    public ArrayList<donor> getDonorRecordFromFile() throws FileNotFoundException {
        File donorRecords = new File("donorRecords.txt");
        Scanner sc = new Scanner(donorRecords);

        ArrayList<donor> donorList = new ArrayList<donor>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] detail = line.split("\\|");
            donor donor = new donor(detail[0], Integer.parseInt(detail[1]), detail[2], detail[3]);
            donorList.add(donor);

        }
        return donorList;
    }




    public void searchDonorWithBloodGroup() {
        Scanner sc = new Scanner(System.in);

        donor donorDriver = new donor();

        ArrayList<donor> donorList = new ArrayList<donor>();
        try {
            donorList = donorDriver.getDonorRecordFromFile();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        System.out.println("""

                ----------------------------------------------------------------------------------------------------

                ----------------------------------
                Search Donor With Blood Group
                ----------------------------------
                """);

        System.out.print("Enter Blood Group: ");
        String donorBloodGroup = sc.nextLine();

        try {
            int donorFound = 0;
            for (int i = 0; i <= donorList.size() - 1; i++)
                if (donorList.get(i).getBloodGroup().equals(donorBloodGroup)) {
                    donorFound++;
                    donorList.get(i).display();
                    System.out.println(
                            "--------------------------------------------------------\n");
                }

            if (donorFound == 0) {
                throw new RecordNotFoundException();
            }
        }

        catch (RecordNotFoundException RNFE) {
        }
    }




    public void getDonorListAscendingOrder() {
        Scanner sc = new Scanner(System.in);

        donor donorDriver = new donor();

        ArrayList<donor> donorList = new ArrayList<donor>();

        try {
            donorList = donorDriver.getDonorRecordFromFile();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        System.out.println("""

                ----------------------------------------------------------------------------------------------------

                --------------------------------------
                List of Donors in Ascending Order
                --------------------------------------
                """);

        try {
            for (int i = 0; i <= donorList.size() - 1; i++) {
                int minimumIndex = i;
                for (int j = i + 1; j <= donorList.size() - 1; j++) {
                    if (donorList.get(j).getName().compareTo(donorList.get(minimumIndex).getName()) < 0) {
                        minimumIndex = j;
                        donor temp = donorList.get(minimumIndex);
                        Collections.swap(donorList, minimumIndex, i);
                        donorList.set(i, temp);
                    }
                }
            }

            for (int i = 0; i <= donorList.size() - 1; i++) {

                System.out.println("Name            :    " + donorList.get(i).getName());
                System.out.println("Age             :    " + donorList.get(i).getAge());
                System.out.println("Blood Group     :    " + donorList.get(i).getBloodGroup());
                System.out.println("Any Disease     :    " + donorList.get(i).getAnyDisease());
                System.out.println(
                        "--------------------------------------------------------\n");

            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}



public class bloodBank {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        patient patientDriver = new patient();
        donor donorDriver = new donor();

        Boolean runAgain = true;


        while (runAgain == true) {





            System.out.println("""

                    ----------------------------------------------------------------------------------------------------

                    -----------------------------------------
                    -Welcome To Blood Bank Management System-
                    -----------------------------------------
                                        """);
            System.out.println("""
                    1. Add Patient Record.
                    2. Add Donor Record.
                    3. Search and Get List of Donors With Entered Blood Group.
                    4. Search and Get Patient With Entered Name.
                    5. Get List of Patients With Blood Group Required And Admitted Date Sorted in Ascending Order.
                    6. Get List of Donors in Asscending Order.
                    7. Get List of Suitable Donors For a Patient
                    8. Get List of Patients that Requires the Entered Blood Group.
                    9. Exit.

                    Enter the Number To Choose an Option.
                    """);

            try {
                System.out.print("Enter Your Choice: ");
                int user_choice = sc.nextInt();


                switch (user_choice) {

                    case 1:
                        patientDriver.addPatientRecord();
                        break;

                    case 2:
                        donorDriver.addDonorRecord();
                        break;

                    case 3:
                        donorDriver.searchDonorWithBloodGroup();
                        break;

                    case 4:
                        patientDriver.searchPatientWithName();
                        break;

                    case 5:

                        patientDriver.getpatientListAscendingOrder();
                        break;

                    case 6:
                        donorDriver.getDonorListAscendingOrder();
                        break;

                    case 7:
                        patientDriver.getSuitableDonors();
                        break;

                    case 8:
                        patientDriver.getPatientsRequireEnteredBloodGroup();
                        break;

                    case 9:
                        runAgain = false;
                        break;

                    default:
                        System.out.println("Invalid Input! Enter a Valid Input.");
                        System.out.println("Try Again.");
                        break;
                }
            }

            catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
package com.example.hms_backend.enums;

public class Enums {

    public enum RoomType{
        GENERAL,
        PRIVATE,
        ICU
    }

    public enum Gender {
        MALE,
        FEMALE,
        TRANSGENDER,
        OTHER
    }

    public enum TestType
    {
        RADIOLOGY,
        PATHOLOGY
    }


    public enum MaritalStatus {
        SINGLE,
        MARRIED,
        DIVORCED,
        WIDOW
    }

    public enum VisitType {
        OPD,        // Outpatient
        IPD,        // Inpatient
        EMERGENCY
    }

    public enum VisitSequenceType {
        FIRST_VISIT,
        FOLLOW_UP,
        REFERRED

    }

    public enum VisitStatus {
        ONGOING,
        COMPLETED,
        REFERRED
    }

    public enum IpdStatus {
        ADMITTED,
        DISCHARGED,
        TRANSFERRED,
        DECEASED
    }

    public enum AdtType {
        ADMITTED,     // Patient admitted
        DISCHARGED,   // Patient discharged
        TRANSFERRED,  // Patient transferred
        DECEASED      // Patient deceased
    }



    public enum BloodGroup {
        A_POSITIVE,
        A_NEGATIVE,
        B_POSITIVE,
        B_NEGATIVE,
        AB_POSITIVE,
        AB_NEGATIVE,
        O_POSITIVE,
        O_NEGATIVE
    }

    public enum IdProofType {
        AADHAAR_CARD,
        PAN_CARD,
        PASSPORT,
        VOTER_ID,
        DRIVING_LICENSE
    }

    public enum AvailabilityStatus {
        ON_LEAVE,
        RESIGNED,
        ACTIVE
    }

    public enum RoleName {
        ADMIN, DOCTOR, ACCOUNTANT, RECEPTIONIST, HEADNURSE, PHARMACIST, HR, LABORATORIST, INSURANCE
    }

    public enum LaboratoryType
    {
        RADIOLOGY, PATHLAB
    }

    public enum DonationType
    {
        VOLUNTARY , REPLACEMENT , CAMP
    }

    public enum PrescriptionStatus {
        PENDING,
        TRANSFERED_TO_PHARMACY,
        COMPLETED,
    }


    public enum ExperienceLevel {
        ZERO_TO_ONE("0-1 years"),
        ONE_TO_TWO("1-2 years"),
        TWO_TO_THREE("2-3 years"),
        THREE_TO_FOUR("3-4 years"),
        FOUR_TO_FIVE("4-5 years"),
        FIVE_TO_SIX("5-6 years"),
        SIX_TO_SEVEN("6-7 years"),
        SEVEN_TO_EIGHT("7-8 years"),
        EIGHT_TO_NINE("8-9 years"),
        NINE_TO_TEN("9-10 years"),
        TEN_TO_ELEVEN("10-11 years"),
        ELEVEN_TO_TWELVE("11-12 years"),
        TWELVE_TO_THIRTEEN("12-13 years"),
        THIRTEEN_TO_FOURTEEN("13-14 years"),
        FOURTEEN_TO_FIFTEEN("14-15 years"),
        FIFTEEN_TO_SIXTEEN("15-16 years"),
        SIXTEEN_TO_SEVENTEEN("16-17 years"),
        SEVENTEEN_TO_EIGHTEEN("17-18 years"),
        EIGHTEEN_TO_NINETEEN("18-19 years"),
        NINETEEN_TO_TWENTY("19-20 years"),
        TWENTY_TO_TWENTY_ONE("20-21 years"),
        TWENTY_ONE_TO_TWENTY_TWO("21-22 years"),
        TWENTY_TWO_TO_TWENTY_THREE("22-23 years"),
        TWENTY_THREE_TO_TWENTY_FOUR("23-24 years"),
        TWENTY_FOUR_TO_TWENTY_FIVE("24-25 years"),
        TWENTY_FIVE_PLUS("25+ years");

        private final String label;

        ExperienceLevel(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }


    }

    public enum PharmacyPrescriptionStatus
    {
        PENDING,
        DISPENSED,

    }

    public enum BedStatus {
        VACCANT,
        OCCUPIED
    }

    public enum LabReportStatus{

        COMPLETED,
        DELIVERED
    }


    public enum RoomStatus {
        AVAILABLE,
        UNAVAILABLE,
        UNDER_MAINTENANCE
    }



}


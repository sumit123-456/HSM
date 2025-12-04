package com.example.hms_backend.enums;

public enum ChronicConditionsEnum {
    // 🫀 Cardiovascular & Metabolic
    HYPERTENSION("I10", "Hypertension (High Blood Pressure)"),
    DIABETES_TYPE_1("E10", "Diabetes Mellitus Type 1"),
    DIABETES_TYPE_2("E11", "Diabetes Mellitus Type 2"),
    CORONARY_ARTERY_DISEASE("I25", "Coronary Artery Disease (CAD)"),
    HEART_FAILURE("I50", "Heart Failure"),
    HYPERLIPIDEMIA("E78", "Hyperlipidemia (High Cholesterol)"),
    OBESITY("E66", "Obesity"),

    // 🫁 Respiratory
    ASTHMA("J45", "Asthma"),
    COPD("J44", "Chronic Obstructive Pulmonary Disease"),
    PULMONARY_FIBROSIS("J84", "Pulmonary Fibrosis"),
    BRONCHIECTASIS("J47", "Bronchiectasis"),

    // 🧠 Neurological
    STROKE("I63", "Stroke (Post-stroke complications)"),
    EPILEPSY("G40", "Epilepsy"),
    PARKINSONS_DISEASE("G20", "Parkinson’s Disease"),
    DEMENTIA("F03", "Alzheimer’s Disease / Dementia"),
    MIGRAINE("G43", "Migraine / Chronic Headache"),

    // 🧬 Endocrine & Hormonal
    THYROID_DISORDER("E03", "Thyroid Disorders (Hypothyroidism, Hyperthyroidism)"),
    PCOS("E28", "Polycystic Ovary Syndrome (PCOS)"),
    OSTEOPOROSIS("M81", "Osteoporosis"),

    // 🧑‍⚕️ Renal & Hepatic
    CHRONIC_KIDNEY_DISEASE("N18", "Chronic Kidney Disease (CKD)"),
    LIVER_CIRRHOSIS("K74", "Liver Cirrhosis"),
    HEPATITIS_B_OR_C("B18", "Hepatitis B/C (Chronic)"),

    // 🧑‍⚕️ Gastrointestinal
    IBS("K58", "Irritable Bowel Syndrome (IBS)"),
    IBD("K50", "Inflammatory Bowel Disease (IBD)"),
    CHRONIC_CONSTIPATION("K59", "Chronic Constipation"),
    GERD("K21", "Gastroesophageal Reflux Disease (GERD)"),

    // 🧑‍⚕️ Musculoskeletal
    OSTEOARTHRITIS("M19", "Osteoarthritis"),
    RHEUMATOID_ARTHRITIS("M06", "Rheumatoid Arthritis"),
    SPONDYLITIS("M45", "Spondylitis"),
    CHRONIC_BACK_PAIN("M54", "Chronic Back Pain"),

    // 🧑‍⚕️ Cancer
    BREAST_CANCER("C50", "Breast Cancer"),
    PROSTATE_CANCER("C61", "Prostate Cancer"),
    CERVICAL_CANCER("C53", "Cervical Cancer"),
    LUNG_CANCER("C34", "Lung Cancer"),
    COLORECTAL_CANCER("C18", "Colorectal Cancer"),

    // 🧑‍⚕️ Infectious
    TUBERCULOSIS("A15", "Tuberculosis (Latent or MDR-TB)"),
    HIV("B20", "HIV/AIDS"),
    LEPROSY("A30", "Leprosy (Post-treatment complications)"),


    // 🧑‍⚕️ Mental Health
    DEPRESSION("F32", "Depression"),
    ANXIETY("F41", "Anxiety Disorders"),
    BIPOLAR_DISORDER("F31", "Bipolar Disorder"),
    SCHIZOPHRENIA("F20", "Schizophrenia");


    private final String icd10Code;
    private final String description;

    ChronicConditionsEnum(String icd10Code, String description) {
        this.icd10Code = icd10Code;
        this.description = description;
    }

    public String getIcd10Code() {
        return icd10Code;
    }

    public String getDescription() {
        return description;
    }

}


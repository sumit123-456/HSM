// Global variables
let insuranceData = [];
let patientData = [];

// Initialize form on page load
document.addEventListener('DOMContentLoaded', function () {
    loadInsuranceData();
    loadPatientData();
    setupFormHandlers();
});

// Load insurance data for dropdown
async function loadInsuranceData() {
    try {
        const response = await fetch('http://localhost:8080/api/v1/insurance-master/lookup');
        insuranceData = await response.json();
        console.log(insuranceData)

        const select = document.getElementById('insuranceSelect');
        select.innerHTML = '<option value="">Select Insurance</option>';

        insuranceData.forEach(insurance => {
            const option = document.createElement('option');
            option.value = insurance.id;
            option.textContent = insurance.insuranceName;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading insurance data:', error);
        showMessage('Error loading insurance data', 'danger');
    }
}

// Load patient data for dropdown
async function loadPatientData() {
    try {
        const response = await fetch('http://localhost:8080/api/patients/all');
        patientData = await response.json();
        console.log(patientData)

        const select = document.getElementById('patientSelect');
        select.innerHTML = '<option value="">Select Patient</option>';

        patientData.forEach(patient => {
            const option = document.createElement('option');
            option.value = patient.id;
            option.textContent = `${patient.fullName} (${patient.patientCode})`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading patient data:', error);
        showMessage('Error loading patient data', 'danger');
    }
}

// Setup form event handlers
function setupFormHandlers() {
    const container = document.getElementById('disease-charge-container');

    // Add/Remove disease rows
    container.addEventListener('click', function (e) {
        if (e.target.closest('.btn-add')) {
            const originalRow = container.querySelector('.disease-charge-row');
            const newRow = originalRow.cloneNode(true);
            newRow.querySelectorAll('input').forEach(input => input.value = '');
            container.appendChild(newRow);
        }
        if (e.target.closest('.btn-remove')) {
            if (container.querySelectorAll('.disease-charge-row').length > 1) {
                e.target.closest('.disease-charge-row').remove();
            } else {
                alert('You cannot remove the last item.');
            }
        }
    });

    // Patient selection change
    document.getElementById('patientSelect').addEventListener('change', function () {
        const patientId = this.value;
        if (patientId) {
            const selectedPatient = patientData.find(patient => patient.id == patientId);
            if (selectedPatient) {
                document.getElementById('patientCode').value = selectedPatient.patientCode;
            }
        } else {
            document.getElementById('patientCode').value = '';
        }
    });

    // Reset form handler
    document.getElementById('insuranceForm').addEventListener('reset', function () {
        setTimeout(() => {
            const rows = container.querySelectorAll('.disease-charge-row');
            rows.forEach((row, index) => {
                if (index > 0) row.remove();
            });
            clearInsuranceFields();
            document.getElementById('patientCode').value = '';
            document.getElementById('totalAmount').value = '';
        }, 0);
    });
}

// Handle insurance selection change
function onInsuranceChange() {
    const insuranceId = document.getElementById('insuranceSelect').value;

    if (insuranceId) {
        const selectedInsurance = insuranceData.find(ins => ins.id == insuranceId);

        if (selectedInsurance) {
            // Auto-fill all insurance-related fields
            document.getElementById('insuranceCompany').value = selectedInsurance.insuranceCompany;
            document.getElementById('serviceTax').value = selectedInsurance.serviceTax;
            document.getElementById('discount').value = selectedInsurance.discount;
            document.getElementById('insuranceCode').value = selectedInsurance.insuranceCode;
            document.getElementById('baseInsuranceRate').value = selectedInsurance.insuranceRate;
            document.getElementById('insuranceRate').value = selectedInsurance.insuranceRate;
        }
    } else {
        // Clear all fields if no insurance selected
        clearInsuranceFields();
    }

    calculateTotal();
}

// Calculate total amount
function calculateTotal() {
    // Sum all disease charges
    let totalDiseaseCharge = 0;
    document.querySelectorAll('input[name="disease_charge[]"]').forEach(input => {
        totalDiseaseCharge += parseFloat(input.value) || 0;
    });

    const hospitalRate = parseFloat(document.getElementById('hospitalRate').value) || 0;
    const insuranceRate = parseFloat(document.getElementById('insuranceRate').value) || 0;
    const serviceTax = parseFloat(document.getElementById('serviceTax').value) || 0;
    const discount = parseFloat(document.getElementById('discount').value) || 0;

    // Calculate base amount
    const baseAmount = totalDiseaseCharge + hospitalRate + insuranceRate;

    // Calculate tax and discount
    const taxAmount = baseAmount * (serviceTax / 100);
    const discountAmount = baseAmount * (discount / 100);

    // Calculate final total
    const totalAmount = baseAmount + taxAmount - discountAmount;

    document.getElementById('totalAmount').value = totalAmount.toFixed(2);
}

// Clear insurance fields
function clearInsuranceFields() {
    document.getElementById('insuranceCompany').value = '';
    document.getElementById('serviceTax').value = '';
    document.getElementById('discount').value = '';
    document.getElementById('insuranceCode').value = '';
    document.getElementById('baseInsuranceRate').value = '';
    document.getElementById('insuranceRate').value = '';
}

// Submit form data
async function submitForm() {
    // Get form values
    const formData = {
        insuranceMasterId: document.getElementById('insuranceSelect').value,
        patientId: document.getElementById('patientSelect').value,
        diseaseName: getDiseaseNames(),
        diseaseCharge: getTotalDiseaseCharge(),
        hospitalRate: parseFloat(document.getElementById('hospitalRate').value) || 0,
        insuranceRate: parseFloat(document.getElementById('insuranceRate').value) || 0,
        status: document.querySelector('input[name="status"]:checked').value === 'true',
        remark: document.getElementById('remark').value
    };

    // Validation
    if (!formData.insuranceMasterId || !formData.patientId) {
        showMessage('Please select both Insurance Name and Patient', 'warning');
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/v1/insurance-details', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            const result = await response.json();
            showMessage(`Insurance details submitted successfully! Insurance No: ${result.insuranceNo}`, 'success');
            document.getElementById('insuranceForm').reset();
            clearInsuranceFields();
            document.getElementById('patientCode').value = '';
            document.getElementById('totalAmount').value = '';
        } else {
            const error = await response.json();
            showMessage(`Error: ${error.message || 'Submission failed'}`, 'danger');
        }
    } catch (error) {
        console.error('Error submitting form:', error);
        showMessage('Error submitting form. Please try again.', 'danger');
    }
}

// Helper function to get disease names as comma-separated string
function getDiseaseNames() {
    const names = [];
    document.querySelectorAll('input[name="disease_name[]"]').forEach(input => {
        if (input.value.trim()) {
            names.push(input.value.trim());
        }
    });
    return names.join(', ');
}

// Helper function to get total disease charge
function getTotalDiseaseCharge() {
    let total = 0;
    document.querySelectorAll('input[name="disease_charge[]"]').forEach(input => {
        total += parseFloat(input.value) || 0;
    });
    return total;
}

// Show message to user
function showMessage(message, type) {
    const messageDiv = document.getElementById('responseMessage');
    messageDiv.innerHTML = `
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        `;
    messageDiv.style.display = 'block';

    // Auto-hide success messages after 5 seconds
    if (type === 'success') {
        setTimeout(() => {
            messageDiv.style.display = 'none';
        }, 5000);
    }
}
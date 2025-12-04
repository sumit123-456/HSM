// Load insurance data for dropdown
let patients = [];
let insuranceDetails = [];
let claims = [];

// Initialize on page load
document.addEventListener('DOMContentLoaded', function () {
    loadPatients();
    loadClaims();
});

// Load patients for dropdown
async function loadPatients() {
    try {
        console.log('Loading patients...');
        const response = await fetch('http://localhost:8080/api/v1/patient/basic-info');

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        patients = await response.json();
        console.log('Patients loaded:', patients);

        const select = document.getElementById('patientSelect');
        select.innerHTML = '<option value="">Choose Patient...</option>';

        patients.forEach(patient => {
            const option = document.createElement('option');
            option.value = patient.id;
            option.textContent = `${patient.fullName} (${patient.patientCode})`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading patients:', error);
        showMessage('Error loading patients: ' + error.message, 'danger');
    }
}

// Load patient's insurance details
async function loadPatientInsurance() {
    const patientId = document.getElementById('patientSelect').value;
    if (!patientId) {
        clearInsuranceFields();
        return;
    }

    const patient = patients.find(p => p.id == patientId);
    if (patient) {
        document.getElementById('patientCode').value = patient.patientCode || '';
    }

    try {
        console.log('Loading insurance for patient:', patientId);
        const response = await fetch(`http://localhost:8080/api/v1/insurance-details/patient/${patientId}`);

        if (!response.ok) {
            if (response.status === 404) {
                insuranceDetails = [];
                const select = document.getElementById('insuranceDetailSelect');
                select.innerHTML = '<option value="">No insurance policies found</option>';
                clearInsuranceFields();
                return;
            }
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        insuranceDetails = await response.json();
        console.log('Insurance details loaded:', insuranceDetails);

        const select = document.getElementById('insuranceDetailSelect');
        select.innerHTML = '<option value="">Select Insurance Policy...</option>';

        if (insuranceDetails.length === 0) {
            select.innerHTML = '<option value="">No insurance policies found</option>';
            clearInsuranceFields();
            return;
        }

        insuranceDetails.forEach(detail => {
            const option = document.createElement('option');
            option.value = detail.id;
            option.textContent = `${detail.insuranceName || 'Unknown'} - ${detail.insuranceNo || 'No Number'}`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading insurance details:', error);
        showMessage('Error loading insurance details: ' + error.message, 'danger');
        clearInsuranceFields();
    }
}

// Load insurance details when policy is selected
function loadInsuranceDetails() {
    const detailId = document.getElementById('insuranceDetailSelect').value;
    if (!detailId) {
        clearInsuranceFields();
        return;
    }

    const detail = insuranceDetails.find(d => d.id == detailId);
    if (detail) {
        document.getElementById('insuranceCompany').value = detail.insuranceCompany || '';
        document.getElementById('insuranceNo').value = detail.insuranceNo || '';
        document.getElementById('serviceTax').value = detail.serviceTax || '';
        document.getElementById('discount').value = detail.discount || '';
    } else {
        clearInsuranceFields();
    }
}

// Create new claim
async function createClaim() {
    try {
        // Get form values
        const insuranceDetailId = document.getElementById('insuranceDetailSelect').value;
        const claimType = document.getElementById('claimType').value;
        const diagnosis = document.getElementById('diagnosis').value;
        const totalClaimAmount = document.getElementById('totalClaimAmount').value;

        // Basic validation
        if (!insuranceDetailId || !claimType || !diagnosis || !totalClaimAmount) {
            showMessage('Please fill all required fields (*)', 'warning');
            return;
        }

        // Prepare form data
        const formData = {
            insuranceDetailId: parseInt(insuranceDetailId),
            claimType: claimType,
            admissionDate: document.getElementById('admissionDate').value || null,
            dischargeDate: document.getElementById('dischargeDate').value || null,
            diagnosis: diagnosis,
            treatmentDetails: document.getElementById('treatmentDetails').value || '',
            doctorName: document.getElementById('doctorName').value || '',
            department: document.getElementById('department').value || '',
            totalClaimAmount: parseFloat(totalClaimAmount) || 0
            // Uncomment if you have these fields in your backend
            // isPreAuthorized: document.getElementById('isPreAuthorized').checked,
            // preAuthNumber: document.getElementById('preAuthNumber').value || '',
            // preAuthApprovedAmount: parseFloat(document.getElementById('preAuthApprovedAmount').value) || 0,
            // remarks: document.getElementById('remarks').value || ''
        };

        console.log('Submitting claim data:', formData);

        const response = await fetch('http://localhost:8080/api/v1/claims', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        const responseData = await response.json();

        if (response.ok) {
            console.log('Claim created successfully:', responseData);
            showMessage(`Claim created successfully! Claim Number: ${responseData.claimNumber || responseData.id}`, 'success');
            resetForm();
            loadClaims();
        } else {
            console.error('Server error response:', responseData);
            const errorMessage = responseData.message ||
                               responseData.error ||
                               `Creation failed with status: ${response.status}`;
            throw new Error(errorMessage);
        }
    } catch (error) {
        console.error('Error creating claim:', error);
        showMessage('Error creating claim: ' + error.message, 'danger');
    }
}

// Load all claims
async function loadClaims() {
    try {
        console.log('Loading claims...');
        const response = await fetch('http://localhost:8080/api/v1/claims');

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        claims = await response.json();
        console.log('Claims loaded:', claims);
        displayClaims();
        document.getElementById('claimsList').style.display = 'block';
    } catch (error) {
        console.error('Error loading claims:', error);
        showMessage('Error loading claims: ' + error.message, 'danger');
        document.getElementById('claimsList').style.display = 'none';
    }
}

// Display claims in table
function displayClaims() {
    const tbody = document.getElementById('claimsTableBody');
    tbody.innerHTML = '';

    if (claims.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center text-muted">
                    No claims found. Create your first claim above.
                </td>
            </tr>
        `;
        return;
    }

    claims.forEach(claim => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${claim.claimNumber || claim.id}</td>
            <td>${claim.patientName || 'N/A'}</td>
            <td>${claim.insuranceName || 'N/A'}</td>
            <td>₹${claim.totalClaimAmount || 0}</td>
            <td><span class="status-badge status-${(claim.claimStatus || 'DRAFT').toLowerCase()}">${claim.claimStatus || 'DRAFT'}</span></td>
            <td>${claim.createdAt ? claim.createdAt.split(' ')[0] : 'N/A'}</td>
            <td>
                <button class="btn btn-sm btn-outline-primary" onclick="viewClaim(${claim.id})" title="View Details">
                    <i class="fas fa-eye"></i>
                </button>
                ${(claim.claimStatus === 'DRAFT' || !claim.claimStatus) ? `
                <button class="btn btn-sm btn-outline-success" onclick="submitClaim(${claim.id})" title="Submit Claim">
                    <i class="fas fa-paper-plane"></i>
                </button>
                ` : ''}
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Submit claim for approval
async function submitClaim(claimId) {
    if (!confirm('Are you sure you want to submit this claim for approval?')) {
        return;
    }

    try {
        console.log('Submitting claim:', claimId);
        const response = await fetch(`http://localhost:8080/api/v1/claims/${claimId}/submit`, {
            method: 'POST'
        });

        const result = await response.json();

        if (response.ok) {
            showMessage('Claim submitted successfully!', 'success');
            loadClaims();
        } else {
            throw new Error(result.message || result.error || 'Submission failed');
        }
    } catch (error) {
        console.error('Error submitting claim:', error);
        showMessage('Error submitting claim: ' + error.message, 'danger');
    }
}

// Reset form
function resetForm() {
    document.getElementById('claimForm').reset();
    document.getElementById('patientCode').value = '';
    clearInsuranceFields();
    showMessage('Form has been reset', 'info');
}

// Clear insurance fields
function clearInsuranceFields() {
    document.getElementById('insuranceCompany').value = '';
    document.getElementById('insuranceNo').value = '';
    document.getElementById('serviceTax').value = '';
    document.getElementById('discount').value = '';
}

// Show message
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
    if (type === 'success' || type === 'info') {
        setTimeout(() => {
            if (messageDiv.style.display !== 'none') {
                messageDiv.style.display = 'none';
            }
        }, 5000);
    }
}

const style = document.createElement('style');
style.textContent = `
    .status-badge {
        padding: 4px 8px;
        border-radius: 12px;
        font-size: 12px;
        font-weight: bold;
    }
    .status-draft { background-color: #6c757d; color: white; }
    .status-submitted { background-color: #17a2b8; color: white; }
    .status-approved { background-color: #28a745; color: white; }
    .status-rejected { background-color: #dc3545; color: white; }
    .status-pending { background-color: #ffc107; color: black; }
`;
document.head.appendChild(style);

let currentClaim = null;

// View claim details
async function viewClaim(claimId) {
    try {
         console.log(claimId);
         const response = await fetch(`http://localhost:8080/api/v1/claims/${claimId}`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        currentClaim = await response.json();

        const modalContent = document.getElementById('claimDetailsContent');
        modalContent.innerHTML = `
            <div class="claim-details">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Claim Number:</strong> ${currentClaim.claimNumber || currentClaim.id}
                    </div>
                    <div class="col-md-6">
                        <strong>Status:</strong>
                        <span class="status-badge status-${(currentClaim.claimStatus || 'DRAFT').toLowerCase()}">
                            ${currentClaim.claimStatus || 'DRAFT'}
                        </span>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Patient:</strong> ${currentClaim.patientName || 'N/A'}
                    </div>
                    <div class="col-md-6">
                        <strong>Insurance:</strong> ${currentClaim.insuranceName || 'N/A'}
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Claim Amount:</strong> ₹${currentClaim.totalClaimAmount || 0}
                    </div>
                    <div class="col-md-6">
                        <strong>Approved Amount:</strong> ₹${currentClaim.approvedAmount || 'Pending'}
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Claim Type:</strong> ${currentClaim.claimType || 'N/A'}
                    </div>
                    <div class="col-md-6">
                        <strong>Department:</strong> ${currentClaim.department || 'N/A'}
                    </div>
                </div>
                ${currentClaim.admissionDate ? `
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Admission Date:</strong> ${formatDate(currentClaim.admissionDate)}
                    </div>
                    <div class="col-md-6">
                        <strong>Discharge Date:</strong> ${currentClaim.dischargeDate ? formatDate(currentClaim.dischargeDate) : 'N/A'}
                    </div>
                </div>
                ` : ''}
                <div class="mb-3">
                    <strong>Diagnosis:</strong> ${currentClaim.diagnosis || 'N/A'}
                </div>
                ${currentClaim.treatmentDetails ? `
                <div class="mb-3">
                    <strong>Treatment:</strong> ${currentClaim.treatmentDetails}
                </div>
                ` : ''}
                ${currentClaim.doctorName ? `
                <div class="mb-3">
                    <strong>Doctor:</strong> ${currentClaim.doctorName}
                </div>
                ` : ''}
                ${currentClaim.remarks ? `
                <div class="mb-3">
                    <strong>Remarks:</strong> ${currentClaim.remarks}
                </div>
                ` : ''}
                ${currentClaim.createdAt ? `
                <div class="mb-3">
                    <strong>Created Date:</strong> ${formatDate(currentClaim.createdAt)}
                </div>
                ` : ''}
            </div>
        `;

        new bootstrap.Modal(document.getElementById('claimDetailsModal')).show();
    } catch (error) {
        console.error('Error loading claim details:', error);
        showErrorAlert('Error Loading Claim Details', 'Failed to load claim details. Please try again.');
    }
}

// Download Claim Report as PDF
async function downloadClaimReport() {
    if (!currentClaim) {
        showWarningAlert('No Claim Selected', 'Please select a claim first.');
        return;
    }

    try {
        // Show loading alert
        Swal.fire({
            title: 'Generating Report...',
            text: 'Please wait while we prepare your report',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        // Generate PDF using jsPDF
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        // Add header
        doc.setFillColor(41, 128, 185);
        doc.rect(0, 0, 210, 40, 'F');
        doc.setTextColor(255, 255, 255);
        doc.setFontSize(20);
        doc.setFont('helvetica', 'bold');
        doc.text('INSURANCE CLAIM REPORT', 105, 20, { align: 'center' });

        doc.setFontSize(10);
        doc.text('Generated on: ' + new Date().toLocaleDateString(), 105, 30, { align: 'center' });

        // Reset text color
        doc.setTextColor(0, 0, 0);
        doc.setFontSize(12);
        doc.setFont('helvetica', 'normal');

        let yPosition = 60;

        // Claim Information
        doc.setFont('helvetica', 'bold');
        doc.setFontSize(14);
        doc.text('CLAIM INFORMATION', 14, yPosition);
        yPosition += 10;

        doc.setFont('helvetica', 'normal');
        doc.setFontSize(10);

        const claimInfo = [
            `Claim Number: ${currentClaim.claimNumber || currentClaim.id}`,
            `Patient Name: ${currentClaim.patientName || 'N/A'}`,
            `Insurance Company: ${currentClaim.insuranceName || 'N/A'}`,
            `Claim Type: ${currentClaim.claimType || 'N/A'}`,
            `Status: ${currentClaim.claimStatus || 'DRAFT'}`,
            `Department: ${currentClaim.department || 'N/A'}`,
            `Doctor: ${currentClaim.doctorName || 'N/A'}`,
            `Admission Date: ${currentClaim.admissionDate ? formatDate(currentClaim.admissionDate) : 'N/A'}`,
            `Discharge Date: ${currentClaim.dischargeDate ? formatDate(currentClaim.dischargeDate) : 'N/A'}`,
            `Created Date: ${currentClaim.createdAt ? formatDate(currentClaim.createdAt) : 'N/A'}`
        ];

        claimInfo.forEach(info => {
            if (yPosition > 270) {
                doc.addPage();
                yPosition = 20;
            }
            doc.text(info, 20, yPosition);
            yPosition += 7;
        });

        yPosition += 5;

        // Financial Information
        doc.setFont('helvetica', 'bold');
        doc.setFontSize(14);
        doc.text('FINANCIAL INFORMATION', 14, yPosition);
        yPosition += 10;

        doc.setFont('helvetica', 'normal');
        doc.setFontSize(10);

        const financialInfo = [
            `Total Claim Amount: ₹${currentClaim.totalClaimAmount || 0}`,
            `Approved Amount: ₹${currentClaim.approvedAmount || 'Pending'}`,
            `Service Tax: ${currentClaim.serviceTax || 'N/A'}`,
            `Discount: ${currentClaim.discount || 'N/A'}`
        ];

        financialInfo.forEach(info => {
            doc.text(info, 20, yPosition);
            yPosition += 7;
        });

        yPosition += 5;

        // Medical Information
        doc.setFont('helvetica', 'bold');
        doc.setFontSize(14);
        doc.text('MEDICAL INFORMATION', 14, yPosition);
        yPosition += 10;

        doc.setFont('helvetica', 'normal');
        doc.setFontSize(10);

        // Diagnosis (with text wrapping)
        const diagnosisLines = doc.splitTextToSize(`Diagnosis: ${currentClaim.diagnosis || 'N/A'}`, 170);
        diagnosisLines.forEach(line => {
            if (yPosition > 270) {
                doc.addPage();
                yPosition = 20;
            }
            doc.text(line, 20, yPosition);
            yPosition += 7;
        });

        yPosition += 3;

        // Treatment Details (with text wrapping)
        if (currentClaim.treatmentDetails) {
            const treatmentLines = doc.splitTextToSize(`Treatment Details: ${currentClaim.treatmentDetails}`, 170);
            treatmentLines.forEach(line => {
                if (yPosition > 270) {
                    doc.addPage();
                    yPosition = 20;
                }
                doc.text(line, 20, yPosition);
                yPosition += 7;
            });
            yPosition += 3;
        }

        // Remarks (with text wrapping)
        if (currentClaim.remarks) {
            const remarksLines = doc.splitTextToSize(`Remarks: ${currentClaim.remarks}`, 170);
            remarksLines.forEach(line => {
                if (yPosition > 270) {
                    doc.addPage();
                    yPosition = 20;
                }
                doc.text(line, 20, yPosition);
                yPosition += 7;
            });
        }

        // Add footer
        const pageCount = doc.internal.getNumberOfPages();
        for (let i = 1; i <= pageCount; i++) {
            doc.setPage(i);
            doc.setFontSize(8);
            doc.setTextColor(100, 100, 100);
            doc.text(`Page ${i} of ${pageCount}`, 105, 290, { align: 'center' });
            doc.text('Confidential Document - Insurance Claim Management System', 105, 295, { align: 'center' });
        }

        // Save the PDF
        const fileName = `Claim_Report_${currentClaim.claimNumber || currentClaim.id}_${new Date().toISOString().split('T')[0]}.pdf`;
        doc.save(fileName);

        // Close loading alert and show success message
        Swal.close();
        showSuccessToast('Report downloaded successfully!');

    } catch (error) {
        console.error('Error generating report:', error);
        Swal.close();
        showErrorAlert('Report Generation Failed', 'Failed to generate report. Please try again.');
    }
}

// Print Claim Report
function printClaimReport() {
    if (!currentClaim) {
        showWarningAlert('No Claim Selected', 'Please select a claim first.');
        return;
    }

    const printWindow = window.open('', '_blank');
    printWindow.document.write(`
        <!DOCTYPE html>
        <html>
        <head>
            <title>Claim Report - ${currentClaim.claimNumber || currentClaim.id}</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 40px;
                    color: #333;
                }
                .header {
                    background: #2980b9;
                    color: white;
                    padding: 20px;
                    text-align: center;
                    margin-bottom: 30px;
                }
                .section {
                    margin-bottom: 25px;
                    border-bottom: 1px solid #eee;
                    padding-bottom: 15px;
                }
                .section-title {
                    color: #2c3e50;
                    border-bottom: 2px solid #3498db;
                    padding-bottom: 5px;
                    margin-bottom: 15px;
                }
                .info-row {
                    margin-bottom: 8px;
                    display: flex;
                }
                .label {
                    font-weight: bold;
                    width: 200px;
                }
                .status-approved { color: #27ae60; font-weight: bold; }
                .status-pending { color: #f39c12; font-weight: bold; }
                .status-rejected { color: #e74c3c; font-weight: bold; }
                .status-draft { color: #7f8c8d; font-weight: bold; }
                .footer {
                    margin-top: 40px;
                    text-align: center;
                    font-size: 12px;
                    color: #7f8c8d;
                    border-top: 1px solid #eee;
                    padding-top: 20px;
                }
                @media print {
                    body { margin: 0; }
                    .no-print { display: none; }
                }
            </style>
        </head>
        <body>
            <div class="header">
                <h1>INSURANCE CLAIM REPORT</h1>
                <p>Generated on: ${new Date().toLocaleDateString()}</p>
            </div>

            <div class="section">
                <h2 class="section-title">Claim Information</h2>
                <div class="info-row"><div class="label">Claim Number:</div> ${currentClaim.claimNumber || currentClaim.id}</div>
                <div class="info-row"><div class="label">Patient Name:</div> ${currentClaim.patientName || 'N/A'}</div>
                <div class="info-row"><div class="label">Patient Code:</div> ${currentClaim.patientCode || 'N/A'}</div>
                <div class="info-row"><div class="label">Insurance Company:</div> ${currentClaim.insuranceName || 'N/A'}</div>
                <div class="info-row"><div class="label">Claim Type:</div> ${currentClaim.claimType || 'N/A'}</div>
                <div class="info-row"><div class="label">Status:</div> <span class="status-${(currentClaim.claimStatus || 'DRAFT').toLowerCase()}">${currentClaim.claimStatus || 'DRAFT'}</span></div>
                <div class="info-row"><div class="label">Department:</div> ${currentClaim.department || 'N/A'}</div>
                <div class="info-row"><div class="label">Doctor:</div> ${currentClaim.doctorName || 'N/A'}</div>
                ${currentClaim.admissionDate ? `<div class="info-row"><div class="label">Admission Date:</div> ${formatDate(currentClaim.admissionDate)}</div>` : ''}
                ${currentClaim.dischargeDate ? `<div class="info-row"><div class="label">Discharge Date:</div> ${formatDate(currentClaim.dischargeDate)}</div>` : ''}
                ${currentClaim.createdAt ? `<div class="info-row"><div class="label">Created Date:</div> ${formatDate(currentClaim.createdAt)}</div>` : ''}
            </div>

            <div class="section">
                <h2 class="section-title">Financial Information</h2>
                <div class="info-row"><div class="label">Total Claim Amount:</div> ₹${currentClaim.totalClaimAmount || 0}</div>
                <div class="info-row"><div class="label">Approved Amount:</div> ₹${currentClaim.approvedAmount || 'Pending'}</div>
                <div class="info-row"><div class="label">Service Tax:</div> ₹${currentClaim.insuranceShare || 'N/A'}</div>
                <div class="info-row"><div class="label">Discount:</div> ₹${currentClaim.patientShare || 'N/A'}</div>
                <div class="info-row"><div class="label">Settle Amount:</div> ₹${currentClaim.settledAmount || 'N/A'}</div>
            </div>

            <div class="section">
                <h2 class="section-title">Medical Information</h2>
                <div class="info-row"><div class="label">Diagnosis:</div> ${currentClaim.diagnosis || 'N/A'}</div>
                ${currentClaim.treatmentDetails ? `<div class="info-row"><div class="label">Treatment Details:</div> ${currentClaim.treatmentDetails}</div>` : ''}
                ${currentClaim.remarks ? `<div class="info-row"><div class="label">Remarks:</div> ${currentClaim.remarks}</div>` : ''}
            </div>

            <div class="footer">
                <p>Confidential Document - Insurance Claim Management System</p>
                <p>Generated on ${new Date().toLocaleString()}</p>
            </div>

            <div class="no-print" style="margin-top: 20px; text-align: center;">
                <button onclick="window.print()" style="padding: 10px 20px; background: #3498db; color: white; border: none; border-radius: 5px; cursor: pointer;">Print Report</button>
                <button onclick="window.close()" style="padding: 10px 20px; background: #95a5a6; color: white; border: none; border-radius: 5px; cursor: pointer; margin-left: 10px;">Close</button>
            </div>
        </body>
        </html>
    `);
    printWindow.document.close();
}

// Utility function to format dates
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-IN', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

// SweetAlert helper functions
function showSuccessToast(message) {
    Swal.fire({
        toast: true,
        position: 'top-end',
        icon: 'success',
        title: message,
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true
    });
}

function showErrorAlert(title, message) {
    Swal.fire({
        icon: 'error',
        title: title,
        text: message,
        confirmButtonColor: '#d33'
    });
}

function showWarningAlert(title, message) {
    Swal.fire({
        icon: 'warning',
        title: title,
        text: message,
        confirmButtonColor: '#f39c12'
    });
}
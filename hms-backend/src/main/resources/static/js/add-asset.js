// Utility Functions
class AssetUtils {
    static showNotification(message, type = 'success') {
        document.querySelectorAll('.asset-notification').forEach(a => a.remove());

        const notification = document.createElement('div');
        notification.className = `asset-notification alert alert-${type} alert-dismissible fade show`;
        notification.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;

        document.querySelector('.content-wrapper')?.prepend(notification);
        setTimeout(() => notification.remove(), 5000);
    }

    static showSweetAlert(title, message, type = 'success') {
        if (typeof Swal !== 'undefined') {
            Swal.fire({
                title: title,
                text: message,
                icon: type,
                confirmButtonColor: type === 'success' ? '#3085d6' : '#d33',
                confirmButtonText: 'OK',
                timer: type === 'success' ? 3000 : null, // Auto close only for success
                timerProgressBar: type === 'success'
            });
        } else {
            // Fallback to regular alert if SweetAlert is not available
            alert(`${title}: ${message}`);
        }
    }

    static debounce(func, wait) {
        let timeout;
        return function (...args) {
            clearTimeout(timeout);
            timeout = setTimeout(() => func(...args), wait);
        };
    }
}

// Form Validation Class
class AssetFormValidator {
    constructor() {
        this.errors = new Map();
    }

    clearErrors() {
        this.errors.clear();
        document.querySelectorAll('.error-text').forEach(el => {
            el.textContent = '';
            el.style.display = 'none';
        });
        document.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
    }

    showError(fieldId, message) {
        const errorEl = document.getElementById(`${fieldId}Error`);
        const inputEl = document.getElementById(fieldId);
        if (errorEl && inputEl) {
            errorEl.textContent = message;
            errorEl.style.display = 'block';
            inputEl.classList.add('is-invalid');
            this.errors.set(fieldId, message);
        }
    }

    validateRequired(value, fieldId, fieldName) {
        if (!value.trim()) {
            this.showError(fieldId, `${fieldName} is required`);
            return false;
        }
        return true;
    }

    validateLength(value, fieldId, fieldName, min = 1, max = 255) {
        if (value.length < min) {
            this.showError(fieldId, `${fieldName} must be at least ${min} characters`);
            return false;
        }
        if (value.length > max) {
            this.showError(fieldId, `${fieldName} must be less than ${max} characters`);
            return false;
        }
        return true;
    }

    validateDate(value, fieldId, fieldName, allowFuture = false) {
        if (!value) {
            this.showError(fieldId, `${fieldName} is required`);
            return false;
        }

        const date = new Date(value);
        const today = new Date();
        if (isNaN(date)) {
            this.showError(fieldId, `${fieldName} is invalid`);
            return false;
        }
        if (!allowFuture && date > today) {
            this.showError(fieldId, `${fieldName} cannot be in the future`);
            return false;
        }
        return true;
    }

    validateWarrantyDate(warrantyDate, purchaseDate, fieldId) {
        if (!warrantyDate) return true;
        const warranty = new Date(warrantyDate);
        const purchase = new Date(purchaseDate);
        if (warranty <= purchase) {
            this.showError(fieldId, 'Warranty date must be after purchase date');
            return false;
        }
        return true;
    }

    validateForm(formData) {
        this.clearErrors();

        const validations = [
            // Asset Category validation
            () => this.validateRequired(formData.departmentBranch, 'uniqueDepartment', 'Asset Category'),

            // Asset ID validation
            () => this.validateRequired(formData.assetId, 'uniqueAssetId', 'Asset ID'),
            () => this.validateLength(formData.assetId, 'uniqueAssetId', 'Asset ID', 2, 100),

            // Asset Name validation (previously serialNumber)
            () => this.validateRequired(formData.assetName, 'uniqueSerialNumber', 'Asset Name'),
            () => this.validateLength(formData.assetName, 'uniqueSerialNumber', 'Asset Name', 2, 200),

            // Vendor validation
            () => this.validateRequired(formData.vendor, 'uniqueVendor', 'Vendor'),
            () => this.validateLength(formData.vendor, 'uniqueVendor', 'Vendor', 2, 200),

            // Purchase Date validation
            () => this.validateDate(formData.purchaseDate, 'uniquePurchaseDate', 'Purchase Date', false),

            // Warranty Date validation
            () => !formData.warrantyDate || this.validateDate(formData.warrantyDate, 'uniqueWarranty', 'Warranty Date', true),
            () => !formData.warrantyDate || this.validateWarrantyDate(formData.warrantyDate, formData.purchaseDate, 'uniqueWarranty'),

            // Asset Condition validation
            () => this.validateRequired(formData.assetCondition, 'assetCondition', 'Asset Condition')
        ];

        return validations.every(v => v());
    }
}

// Form Handler with AJAX submission
class AssetFormHandler {
    constructor() {
        this.validator = new AssetFormValidator();
        this.form = document.getElementById('uniqueAssetForm');
        if (!this.form) return;

        this.isSubmitting = false;
        this.initializeListeners();
        this.initializeDatePickers();
        this.loadSweetAlert();
    }

    initializeListeners() {
        this.form.addEventListener('submit', e => this.handleSubmit(e));

        this.form.querySelectorAll('input, select, textarea').forEach(input => {
            input.addEventListener('blur', () => this.validateField(input));
            input.addEventListener('input', AssetUtils.debounce(() => this.validateField(input), 400));
        });

        this.form.addEventListener('reset', () => {
            this.validator.clearErrors();
            setTimeout(() => this.initializeDatePickers(), 0);
        });
    }

    initializeDatePickers() {
        const warrantyInput = document.getElementById('uniqueWarranty');
        const purchaseInput = document.getElementById('uniquePurchaseDate');

        if (purchaseInput && typeof flatpickr !== 'undefined') {
            this.purchasePicker = flatpickr("#uniquePurchaseDate", {
                dateFormat: "Y-m-d",
                maxDate: "today",
                allowInput: true,
                onChange: (dates) => {
                    if (dates[0] && this.warrantyPicker) {
                        const minWarranty = new Date(dates[0]);
                        minWarranty.setDate(minWarranty.getDate() + 1);
                        this.warrantyPicker.set('minDate', minWarranty);
                    }
                }
            });
        }

        if (warrantyInput && typeof flatpickr !== 'undefined') {
            this.warrantyPicker = flatpickr("#uniqueWarranty", {
                dateFormat: "Y-m-d",
                allowInput: true,
                minDate: new Date().fp_incr(1)
            });
        }
    }

    loadSweetAlert() {
        // Check if SweetAlert is already loaded, if not, load it dynamically
        if (typeof Swal === 'undefined') {
            const script = document.createElement('script');
            script.src = 'https://cdn.jsdelivr.net/npm/sweetalert2@11';
            script.onload = () => console.log('SweetAlert2 loaded successfully');
            script.onerror = () => console.warn('Failed to load SweetAlert2');
            document.head.appendChild(script);
        }
    }

    validateField(field) {
        const id = field.id, val = field.value.trim();
        this.validator.clearErrors();

        switch (id) {
            case 'uniqueAssetId':
                this.validator.validateRequired(val, id, 'Asset ID');
                this.validator.validateLength(val, id, 'Asset ID', 2, 100);
                break;
            case 'uniqueSerialNumber':
                // This is now Asset Name field
                this.validator.validateRequired(val, id, 'Asset Name');
                this.validator.validateLength(val, id, 'Asset Name', 2, 200);
                break;
            case 'uniqueVendor':
                this.validator.validateRequired(val, id, 'Vendor');
                this.validator.validateLength(val, id, 'Vendor', 2, 200);
                break;
            case 'uniquePurchaseDate':
                this.validator.validateDate(val, id, 'Purchase Date', false);
                break;
            case 'uniqueWarranty':
                if (val) {
                    this.validator.validateDate(val, id, 'Warranty Date', true);
                    const purchaseDate = document.getElementById('uniquePurchaseDate')?.value;
                    if (purchaseDate) this.validator.validateWarrantyDate(val, purchaseDate, id);
                }
                break;
            case 'uniqueDepartment':
                this.validator.validateRequired(val, id, 'Asset Category');
                break;
            case 'assetCondition':
                this.validator.validateRequired(val, id, 'Asset Condition');
                break;
        }
    }

    async handleSubmit(e) {
        e.preventDefault();

        if (this.isSubmitting) return;

        const data = this.getFormData();

        if (!this.validator.validateForm(data)) {
            AssetUtils.showSweetAlert('Validation Error', 'Please fix the validation errors before submitting.', 'error');
            return;
        }

        this.isSubmitting = true;
        this.setSubmitButtonState(true);

        try {
            const response = await this.submitForm(data);

            if (response.success) {
                AssetUtils.showSweetAlert('Success!', 'Asset has been registered successfully!', 'success');
                this.resetForm();
            } else {
                AssetUtils.showSweetAlert('Error', response.message || 'Failed to register asset. Please try again.', 'error');
            }
        } catch (error) {
            console.error('Submission error:', error);
            AssetUtils.showSweetAlert('Error', 'An error occurred while submitting the form. Please try again.', 'error');
        } finally {
            this.isSubmitting = false;
            this.setSubmitButtonState(false);
        }
    }

    async submitForm(formData) {
        const formElement = document.getElementById('uniqueAssetForm');
        const formDataToSend = new FormData(formElement);

        try {
            const response = await fetch(formElement.action, {
                method: 'POST',
                body: formDataToSend,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
                return await response.json();
            } else {
                // If it's not JSON, assume success and return a success object
                return { success: true, message: 'Asset registered successfully!' };
            }
        } catch (error) {
            console.error('Fetch error:', error);
            throw error;
        }
    }

    resetForm() {
        // Reset the form
        this.form.reset();

        // Clear validation errors
        this.validator.clearErrors();

        // Reinitialize date pickers
        this.initializeDatePickers();

        // Clear any existing success messages
        const successAlert = document.querySelector('.alert-success');
        if (successAlert) {
            successAlert.remove();
        }
    }

    setSubmitButtonState(loading) {
        const submitBtn = this.form.querySelector('.save-btn');
        if (submitBtn) {
            if (loading) {
                submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Saving...';
                submitBtn.disabled = true;
            } else {
                submitBtn.innerHTML = '<i class="fas fa-save"></i> Save';
                submitBtn.disabled = false;
            }
        }
    }

    getFormData() {
        return {
            departmentBranch: document.getElementById('uniqueDepartment')?.value || '',
            assetId: document.getElementById('uniqueAssetId')?.value || '',
            assetName: document.getElementById('uniqueSerialNumber')?.value || '',
            vendor: document.getElementById('uniqueVendor')?.value || '',
            purchaseDate: document.getElementById('uniquePurchaseDate')?.value || '',
            warrantyDate: document.getElementById('uniqueWarranty')?.value || '',
            assetCondition: document.getElementById('assetCondition')?.value || '',
            remarksNotes: document.getElementById('uniqueRemarks')?.value || ''
        };
    }
}

// Initialize safely
document.addEventListener('DOMContentLoaded', () => {
    const styles = `
        .is-invalid { border-color: #dc3545 !important; box-shadow: 0 0 0 0.2rem rgba(220,53,69,0.25)!important; }
        .error-text { color: #dc3545; font-size: 0.875em; margin-top: 0.25rem; display: none; }
        .asset-notification { position: fixed; top: 100px; right: 20px; z-index: 9999; min-width: 300px; }
        .save-btn:disabled { opacity: 0.6; cursor: not-allowed; }
    `;
    const styleEl = document.createElement('style');
    styleEl.textContent = styles;
    document.head.appendChild(styleEl);

    const form = document.getElementById('uniqueAssetForm');
    if (form) new AssetFormHandler();
});
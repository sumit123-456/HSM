
// Utility Functions
const AssetUtils = {
    debounce(func, wait) {
        let timeout;
        return (...args) => {
            clearTimeout(timeout);
            timeout = setTimeout(() => func(...args), wait);
        };
    },

    escapeHtml(text) {
        if (!text) return '';
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    },

    formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString('en-GB');
    },

    showNotification(message, type = 'success') {
        if (typeof Swal !== 'undefined') {
            Swal.fire({
                icon: type,
                title: type === 'success' ? 'Success' : 'Error',
                text: message,
                confirmButtonColor: type === 'success' ? '#0d6efd' : '#d33',
                timer: type === 'success' ? 3000 : null
            });
        } else {
            alert(`${type.toUpperCase()}: ${message}`);
        }
    }
};

// API Service
const AssetAPI = {
    async request(endpoint, options = {}) {
        try {
            const response = await fetch(`${ASSET_CONFIG.API_BASE_URL}${endpoint}`, {
                headers: {
                    'Content-Type': 'application/json',
                    ...options.headers
                },
                ...options
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    },

    async getAllAssets(page = 0, size = ASSET_CONFIG.PAGE_SIZE) {
        return this.request(`?page=${page}&size=${size}&sort=id,desc`);
    },

    async deleteAsset(id) {
        return this.request(`/${id}`, {
            method: 'DELETE'
        });
    },

    async searchAssets(query, category, condition) {
        let url = `/search?query=${encodeURIComponent(query)}`;
        if (category) url += `&category=${encodeURIComponent(category)}`;
        if (condition) url += `&condition=${encodeURIComponent(condition)}`;
        return this.request(url);
    }
};

// Asset Manager Class
class AssetManager {
    constructor() {
        this.isLoading = false;
    }

    async init() {
        this.initEventListeners();
        await this.loadAssets();
    }

    initEventListeners() {
        // Search functionality
        const searchInput = document.getElementById('searchInput');
        if (searchInput) {
            searchInput.addEventListener('input', AssetUtils.debounce(() => {
                this.applyFilters();
            }, 500));
        }

        // Refresh button
        const refreshBtn = document.getElementById('refreshBtn');
        if (refreshBtn) {
            refreshBtn.addEventListener('click', () => this.loadAssets());
        }

        // Clear search
        const clearSearchBtn = document.getElementById('clearSearch');
        if (clearSearchBtn) {
            clearSearchBtn.addEventListener('click', () => {
                document.getElementById('searchInput').value = '';
                this.applyFilters();
            });
        }

        // Apply filters
        const applyFiltersBtn = document.getElementById('applyFilters');
        if (applyFiltersBtn) {
            applyFiltersBtn.addEventListener('click', () => this.applyFilters());
        }

        // Delete button events are handled in the main HTML script
    }

    async loadAssets(page = 0) {
        if (this.isLoading) return;

        this.isLoading = true;
        this.showLoading(true);

        try {
            const response = await AssetAPI.getAllAssets(page);

            if (response && response.content) {
                assetState.allAssets = response.content;
                assetState.filteredAssets = [...response.content];
                assetState.totalPages = response.totalPages;
                assetState.totalElements = response.totalElements;
                assetState.currentPage = page;

                this.renderTable(assetState.filteredAssets);
                this.updateAssetCount();
            } else {
                throw new Error('Invalid response format');
            }
        } catch (error) {
            console.error('Failed to load assets:', error);
            AssetUtils.showNotification('Failed to load assets. Please try again.', 'error');
        } finally {
            this.isLoading = false;
            this.showLoading(false);
        }
    }

    renderTable(assets) {
        const tbody = document.getElementById('assetTableBody');
        if (!tbody) return;

        if (!assets || assets.length === 0) {
            tbody.innerHTML = `<tr><td colspan="10" class="text-center text-muted">No assets found</td></tr>`;
            return;
        }

        const startIndex = assetState.currentPage * ASSET_CONFIG.PAGE_SIZE;

        tbody.innerHTML = assets.map((asset, index) => `
            <tr>
                <td>${startIndex + index + 1}</td>
                <td>${AssetUtils.escapeHtml(asset.departmentBranch)}</td>
                <td>${AssetUtils.escapeHtml(asset.assetId)}</td>
                <td>${AssetUtils.escapeHtml(asset.assetName)}</td>
                <td>${AssetUtils.escapeHtml(asset.vendor)}</td>
                <td>${AssetUtils.formatDate(asset.purchaseDate)}</td>
                <td>${AssetUtils.formatDate(asset.warrantyDate)}</td>
                <td>
                    <span class="badge ${
                        asset.assetCondition === 'New' ? 'badge-success' :
                        asset.assetCondition === 'Used' ? 'badge-warning' : 'badge-danger'
                    }">
                        ${AssetUtils.escapeHtml(asset.assetCondition)}
                    </span>
                </td>
                <td>
                    <span class="${
                        asset.status === 'ACTIVE' ? 'status-active' :
                        asset.status === 'INACTIVE' ? 'status-inactive' : 'status-maintenance'
                    }">
                        ${AssetUtils.escapeHtml(asset.status)}
                    </span>
                </td>
                <td class="text-center">
                    <a href="/edit-asset/${asset.id}" class="btn btn-sm btn-warning" title="Edit">
                        <i class="fa-solid fa-pen"></i>
                    </a>
                    <button type="button" class="btn btn-sm btn-danger delete-btn" data-id="${asset.id}" title="Delete">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');

        // Re-attach delete event listeners
        this.attachDeleteEventListeners();
    }

    attachDeleteEventListeners() {
        const deleteButtons = document.querySelectorAll('.delete-btn');
        deleteButtons.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                this.handleDeleteAsset(btn.dataset.id);
            });
        });
    }

    async handleDeleteAsset(assetId) {
        const assetRow = document.querySelector(`[data-id="${assetId}"]`).closest('tr');
        const assetName = assetRow.querySelector('td:nth-child(4)').textContent;

        try {
            const result = await Swal.fire({
                title: 'Are you sure?',
                html: `You are about to delete asset: <strong>${AssetUtils.escapeHtml(assetName)}</strong><br>This action cannot be undone!`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'Cancel'
            });

            if (result.isConfirmed) {
                await AssetAPI.deleteAsset(assetId);

                AssetUtils.showNotification('Asset deleted successfully!', 'success');

                // Reload the assets after a short delay
                setTimeout(() => {
                    this.loadAssets(assetState.currentPage);
                }, 1000);
            }
        } catch (error) {
            console.error('Delete failed:', error);
            AssetUtils.showNotification('Failed to delete asset. Please try again.', 'error');
        }
    }

    applyFilters() {
        const searchQuery = document.getElementById('searchInput')?.value.toLowerCase() || '';
        const categoryFilter = document.getElementById('categoryFilter')?.value || '';
        const conditionFilter = document.getElementById('conditionFilter')?.value || '';

        let filtered = assetState.allAssets.filter(asset => {
            const matchesSearch = !searchQuery ||
                asset.assetId.toLowerCase().includes(searchQuery) ||
                asset.assetName.toLowerCase().includes(searchQuery) ||
                asset.vendor.toLowerCase().includes(searchQuery);

            const matchesCategory = !categoryFilter || asset.departmentBranch === categoryFilter;
            const matchesCondition = !conditionFilter || asset.assetCondition === conditionFilter;

            return matchesSearch && matchesCategory && matchesCondition;
        });

        assetState.filteredAssets = filtered;
        this.renderTable(filtered);
        this.updateAssetCount();
    }

    updateAssetCount() {
        const countElement = document.querySelector('.text-muted.text-center.mt-3');
        if (countElement && assetState.filteredAssets) {
            const startIndex = assetState.currentPage * ASSET_CONFIG.PAGE_SIZE + 1;
            const endIndex = startIndex + assetState.filteredAssets.length - 1;
            const total = assetState.filteredAssets.length;

            countElement.innerHTML = `
                Showing <span>${startIndex}</span>
                to <span>${endIndex}</span>
                of <span>${total}</span> assets
            `;
        }
    }

    showLoading(show) {
        // You can add loading indicator logic here if needed
        const refreshBtn = document.getElementById('refreshBtn');
        if (refreshBtn) {
            if (show) {
                refreshBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Loading...';
                refreshBtn.disabled = true;
            } else {
                refreshBtn.innerHTML = '<i class="fa-solid fa-refresh me-2"></i>Refresh';
                refreshBtn.disabled = false;
            }
        }
    }

    goToPage(page) {
        if (page >= 0 && page < assetState.totalPages) {
            this.loadAssets(page);
        }
    }
}

// Initialize the asset manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    const assetManager = new AssetManager();
    assetManager.init();
});
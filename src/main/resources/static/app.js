// app.js - Frontend Logic

const CONFIG = {
    BASE_URL: 'http://localhost:8081/api'
};
const API_STUDENTS_URL = `${CONFIG.BASE_URL}/students`;
const API_AUTH_URL = `${CONFIG.BASE_URL}/auth/login`;

// DOM Elements
const studentTableBody = document.getElementById('studentTableBody');
const searchInput = document.getElementById('searchInput');
const studentModal = document.getElementById('studentModal');
const studentForm = document.getElementById('studentForm');
const modalTitle = document.getElementById('modalTitle');
const saveStudentBtn = document.getElementById('saveStudentBtn');
const toastContainer = document.getElementById('toastContainer');

// Form Inputs
const inputId = document.getElementById('studentId');
const inputName = document.getElementById('studentName');
const inputEmail = document.getElementById('studentEmail');
const inputDob = document.getElementById('studentDob');

// State
let students = [];

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    checkAuth();

    // Setup Search
    searchInput.addEventListener('input', (e) => {
        const searchTerm = e.target.value.toLowerCase();
        const filteredStudents = students.filter(student => 
            student.name.toLowerCase().includes(searchTerm) || 
            student.email.toLowerCase().includes(searchTerm)
        );
        renderStudents(filteredStudents);
    });
});

// Fetch all students from API
async function fetchStudents() {
    try {
        const token = localStorage.getItem('jwt');
        const response = await fetch(API_STUDENTS_URL, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        
        if (response.status === 401 || response.status === 403) {
            handleLogout();
            return;
        }
        
        if (!response.ok) throw new Error('Failed to fetch students');
        
        const data = await response.json();
        // Since we enabled pagination, the array is inside data.content
        students = data.content ? data.content : data;
        renderStudents(students);
    } catch (error) {
        console.error('Error fetching students:', error);
        showToast('Error', 'Could not load student data. Is the backend running?', 'error');
        studentTableBody.innerHTML = `<tr><td colspan="6" class="text-center" style="color: var(--danger)">Failed to load data. Please make sure the server is running.</td></tr>`;
    }
}

// Render students in the table
function renderStudents(studentList) {
    if (studentList.length === 0) {
        studentTableBody.innerHTML = `<tr><td colspan="6" class="text-center">No students found.</td></tr>`;
        return;
    }

    studentTableBody.innerHTML = studentList.map(student => `
        <tr>
            <td>#${student.id}</td>
            <td><strong>${student.name}</strong></td>
            <td>${student.email}</td>
            <td>${formatDate(student.dob)}</td>
            <td><span style="background-color: rgba(99, 102, 241, 0.2); color: var(--primary); padding: 4px 10px; border-radius: 12px; font-weight: 600; font-size: 0.8rem;">${student.age} yrs</span></td>
            <td>
                <button class="action-btn edit" onclick="editStudent(${student.id})" title="Edit">
                    <i class="fa-solid fa-pen-to-square"></i>
                </button>
                <button class="action-btn delete" onclick="deleteStudent(${student.id})" title="Delete">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </td>
        </tr>
    `).join('');
}

// Open Modal for Add
function openStudentModal() {
    studentForm.reset();
    inputId.value = '';
    modalTitle.textContent = 'Add New Student';
    studentModal.classList.remove('hidden');
    // small delay to allow display:block to apply before animating opacity
    setTimeout(() => {
        studentModal.classList.add('active');
        inputName.focus();
    }, 10);
}

// Open Modal for Edit
function editStudent(id) {
    const student = students.find(s => s.id === id);
    if (!student) return;

    inputId.value = student.id;
    inputName.value = student.name;
    inputEmail.value = student.email;
    inputDob.value = student.dob;

    if (student.academicProfile) {
        document.getElementById('studentGpa').value = student.academicProfile.gpa;
        document.getElementById('studentAttendance').value = student.academicProfile.attendancePercentage;
        document.getElementById('mathScore').value = student.academicProfile.mathScore;
        document.getElementById('scienceScore').value = student.academicProfile.scienceScore;
        document.getElementById('term1Score').value = student.academicProfile.term1Score;
        document.getElementById('term2Score').value = student.academicProfile.term2Score;
    } else {
        document.getElementById('studentGpa').value = '';
        document.getElementById('studentAttendance').value = '';
        document.getElementById('mathScore').value = '';
        document.getElementById('scienceScore').value = '';
        document.getElementById('term1Score').value = '';
        document.getElementById('term2Score').value = '';
    }

    modalTitle.textContent = 'Edit Student';
    studentModal.classList.remove('hidden');
    setTimeout(() => {
        studentModal.classList.add('active');
    }, 10);
}

// Close Modal
function closeStudentModal() {
    studentModal.classList.remove('active');
    setTimeout(() => {
        studentModal.classList.add('hidden');
    }, 300); // match transition time
}

// Save Student (Add or Update)
async function saveStudent() {
    // Basic validation
    if (!studentForm.checkValidity()) {
        studentForm.reportValidity();
        return;
    }

    const studentData = {
        name: inputName.value.trim(),
        email: inputEmail.value.trim(),
        dob: inputDob.value,
        academicProfile: {
            gpa: parseFloat(document.getElementById('studentGpa').value) || 0.0,
            attendancePercentage: parseFloat(document.getElementById('studentAttendance').value) || 0.0,
            mathScore: parseInt(document.getElementById('mathScore').value) || 0,
            scienceScore: parseInt(document.getElementById('scienceScore').value) || 0,
            term1Score: parseInt(document.getElementById('term1Score').value) || 0,
            term2Score: parseInt(document.getElementById('term2Score').value) || 0
        }
    };

    const isEditing = inputId.value !== '';
    const url = isEditing ? `${API_STUDENTS_URL}/${inputId.value}` : API_STUDENTS_URL;
    const method = isEditing ? 'PUT' : 'POST';

    try {
        // Disable button to prevent double submission
        saveStudentBtn.disabled = true;
        saveStudentBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Saving...';

        const token = localStorage.getItem('jwt');
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(studentData)
        });

        if (response.status === 401 || response.status === 403) {
            handleLogout();
            return;
        }

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Failed to save student');
        }

        showToast('Success', `Student ${isEditing ? 'updated' : 'added'} successfully!`, 'success');
        closeStudentModal();
        fetchStudents(); // Refresh data

    } catch (error) {
        console.error('Error saving student:', error);
        showToast('Error', error.message, 'error');
    } finally {
        saveStudentBtn.disabled = false;
        saveStudentBtn.innerHTML = 'Save Student';
    }
}

// Delete Student
async function deleteStudent(id) {
    if (!confirm('Are you sure you want to delete this student? This action cannot be undone.')) {
        return;
    }

    try {
        const token = localStorage.getItem('jwt');
        const response = await fetch(`${API_STUDENTS_URL}/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        
        if (response.status === 401 || response.status === 403) {
            handleLogout();
            return;
        }

        if (!response.ok) throw new Error('Failed to delete student');

        showToast('Success', 'Student deleted successfully!', 'success');
        fetchStudents(); // Refresh data
    } catch (error) {
        console.error('Error deleting student:', error);
        showToast('Error', 'Could not delete student.', 'error');
    }
}

// Utilities
function formatDate(dateString) {
    if (!dateString) return '';
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
}

function showToast(title, message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    
    let icon = 'fa-circle-info';
    if (type === 'success') icon = 'fa-circle-check';
    if (type === 'error') icon = 'fa-circle-xmark';

    toast.innerHTML = `
        <div class="toast-icon">
            <i class="fa-solid ${icon}"></i>
        </div>
        <div class="toast-content">
            <h4>${title}</h4>
            <p>${message}</p>
        </div>
    `;

    toastContainer.appendChild(toast);
    
    // Trigger animation
    setTimeout(() => {
        toast.classList.add('show');
    }, 10);

    // Remove after 3 seconds
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            toast.remove();
        }, 400);
    }, 3000);
}

// ==========================================
// AUTHENTICATION LOGIC
// ==========================================

function checkAuth() {
    const token = localStorage.getItem('jwt');
    const loginModal = document.getElementById('loginModal');
    
    if (!token) {
        if (loginModal) {
            loginModal.classList.remove('hidden');
            loginModal.classList.add('active');
        }
    } else {
        if (loginModal) {
            loginModal.classList.remove('active');
            loginModal.classList.add('hidden');
        }
        fetchStudents();
    }
}

async function handleLogin(event) {
    event.preventDefault();
    const usernameInput = document.getElementById('loginUsername').value;
    const passwordInput = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(API_AUTH_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: usernameInput, password: passwordInput })
        });

        if (!response.ok) {
            showToast('Login Failed', 'Incorrect username or password', 'error');
            return;
        }

        const data = await response.json();
        localStorage.setItem('jwt', data.jwt);
        
        document.getElementById('loginModal').classList.remove('active');
        document.getElementById('loginModal').classList.add('hidden');
        showToast('Success', 'Logged in successfully!', 'success');
        fetchStudents();
    } catch (error) {
        showToast('Error', 'Server unreachable', 'error');
    }
}

function handleLogout() {
    localStorage.removeItem('jwt');
    window.location.reload();
}

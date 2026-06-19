// dashboard.js - Analytics Logic

const API_BASE_URL = 'http://localhost:8081/api/students';

// DOM Elements
const elTotalStudents = document.getElementById('metric-total-students');
const elAvgGpa = document.getElementById('metric-avg-gpa');
const elAvgAttendance = document.getElementById('metric-avg-attendance');
const recentEnrollmentsBody = document.getElementById('recentEnrollmentsBody');

// Chart Instances
let termChart, ageChart, unitChart;

// Custom Theme Colors for Charts
const themeColors = {
    primary: 'rgba(99, 102, 241, 1)',
    primaryBg: 'rgba(99, 102, 241, 0.2)',
    secondary: 'rgba(20, 184, 166, 1)',
    secondaryBg: 'rgba(20, 184, 166, 0.2)',
    warning: 'rgba(245, 158, 11, 1)',
    warningBg: 'rgba(245, 158, 11, 0.2)',
    danger: 'rgba(239, 68, 68, 1)',
    text: '#f8fafc',
    textMuted: '#94a3b8',
    gridLines: '#334155'
};

// Global Chart Settings
Chart.defaults.color = themeColors.textMuted;
Chart.defaults.font.family = "'Inter', sans-serif";
Chart.defaults.plugins.tooltip.backgroundColor = 'rgba(15, 23, 42, 0.9)';
Chart.defaults.plugins.tooltip.padding = 12;
Chart.defaults.plugins.tooltip.cornerRadius = 8;

document.addEventListener('DOMContentLoaded', () => {
    fetchDashboardData();
});

async function fetchDashboardData() {
    try {
        // Fetch Students for Recent Table and Demographics
        const studentResponse = await fetch(API_BASE_URL);
        if (!studentResponse.ok) throw new Error('Failed to fetch students data');
        const students = await studentResponse.json();
        
        // Fetch Real Analytics Data
        const analyticsResponse = await fetch('http://localhost:8081/api/analytics/dashboard');
        if (!analyticsResponse.ok) throw new Error('Failed to fetch analytics data');
        const analytics = await analyticsResponse.json();
        
        // 1. Render Top Metrics
        renderTopMetrics(analytics);
        
        // 2. Render Recent Enrollments
        renderRecentEnrollments(students);
        
        // 3. Render Analytics Charts
        renderAgeDistributionChart(students);
        renderTermPerformanceChart(analytics);
        renderUnitPerformanceChart(analytics);
        
    } catch (error) {
        console.error('Error fetching dashboard data:', error);
        elTotalStudents.textContent = "Error";
    }
}

function renderTopMetrics(analytics) {
    // Total Students
    elTotalStudents.textContent = analytics.totalStudents || 0;
    
    // Real Institution GPA
    elAvgGpa.textContent = (analytics.averageGpa || 0).toFixed(2);
    
    // Real Average Attendance
    elAvgAttendance.textContent = (analytics.averageAttendance || 0).toFixed(1) + '%';
}

function renderRecentEnrollments(students) {
    if (students.length === 0) {
        recentEnrollmentsBody.innerHTML = '<tr><td colspan="3" class="text-center">No students enrolled yet.</td></tr>';
        return;
    }
    
    // Sort descending by ID to get the newest
    const sorted = [...students].sort((a, b) => b.id - a.id);
    const recent = sorted.slice(0, 5); // Take top 5
    
    recentEnrollmentsBody.innerHTML = recent.map(s => `
        <tr>
            <td><strong>${s.name}</strong></td>
            <td>${s.email}</td>
            <td><span style="font-size: 0.85rem; color: var(--text-muted);">${formatDate(s.dob)}</span></td>
        </tr>
    `).join('');
}

function formatDate(dateString) {
    if (!dateString) return '';
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
}

// ==========================================
// CHART.JS RENDERERS
// ==========================================

function renderAgeDistributionChart(students) {
    const ctx = document.getElementById('ageDistributionChart').getContext('2d');
    
    // Calculate age buckets from real student data
    let under20 = 0, twenties = 0, over30 = 0;
    students.forEach(s => {
        if (s.age < 20) under20++;
        else if (s.age <= 30) twenties++;
        else over30++;
    });

    if (ageChart) ageChart.destroy();
    
    ageChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Under 20', '20 - 30', 'Over 30'],
            datasets: [{
                data: [under20, twenties, over30],
                backgroundColor: [
                    themeColors.primary,
                    themeColors.secondary,
                    themeColors.warning
                ],
                borderWidth: 0,
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '70%',
            plugins: {
                legend: { position: 'bottom' }
            }
        }
    });
}

function renderTermPerformanceChart(analytics) {
    const ctx = document.getElementById('termPerformanceChart').getContext('2d');
    
    // Real academic data across terms
    const realData = [
        analytics.term1Avg || 0, 
        analytics.term2Avg || 0, 
        analytics.term3Avg || 0, 
        analytics.term4Avg || 0, 
        analytics.term5Avg || 0, 
        analytics.term6Avg || 0
    ];

    if (termChart) termChart.destroy();

    // Create gradient
    let gradient = ctx.createLinearGradient(0, 0, 0, 400);
    gradient.addColorStop(0, themeColors.primaryBg);
    gradient.addColorStop(1, 'rgba(99, 102, 241, 0)');

    termChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Term 1', 'Term 2', 'Term 3', 'Term 4', 'Term 5', 'Term 6'],
            datasets: [{
                label: 'Average Academic Score',
                data: realData,
                borderColor: themeColors.primary,
                backgroundColor: gradient,
                borderWidth: 3,
                pointBackgroundColor: themeColors.bgDark,
                pointBorderColor: themeColors.primary,
                pointBorderWidth: 2,
                pointRadius: 4,
                fill: true,
                tension: 0.4 // Smooth curves
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 100,
                    grid: { color: themeColors.gridLines }
                },
                x: {
                    grid: { display: false }
                }
            },
            plugins: {
                legend: { display: false }
            }
        }
    });
}

function renderUnitPerformanceChart(analytics) {
    const ctx = document.getElementById('unitPerformanceChart').getContext('2d');
    
    // Real data for different study units
    const realScores = [
        analytics.mathAvg || 0, 
        analytics.scienceAvg || 0, 
        analytics.literatureAvg || 0, 
        analytics.historyAvg || 0, 
        analytics.artsAvg || 0, 
        analytics.technologyAvg || 0
    ];

    if (unitChart) unitChart.destroy();

    unitChart = new Chart(ctx, {
        type: 'radar',
        data: {
            labels: ['Mathematics', 'Sciences', 'Literature', 'History', 'Arts', 'Technology'],
            datasets: [{
                label: 'Cohort Unit Performance',
                data: realScores,
                backgroundColor: themeColors.secondaryBg,
                borderColor: themeColors.secondary,
                pointBackgroundColor: themeColors.secondary,
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: themeColors.secondary
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                r: {
                    angleLines: { color: themeColors.gridLines },
                    grid: { color: themeColors.gridLines },
                    pointLabels: {
                        color: themeColors.textMuted,
                        font: { size: 11, family: "'Inter', sans-serif" }
                    },
                    ticks: {
                        display: false,
                        min: 0,
                        max: 100
                    }
                }
            },
            plugins: {
                legend: { position: 'bottom' }
            }
        }
    });
}

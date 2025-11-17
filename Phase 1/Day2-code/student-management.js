// Student Management System
var students = [];

function loadStudentsFromStorage() {
    var storedStudents = localStorage.getItem('students');
    if (storedStudents) {
        students = JSON.parse(storedStudents);
        updateTable();
    }
}

function saveStudentsToStorage() {
    localStorage.setItem('students', JSON.stringify(students));
}

function calculateTotal(marks) {
    var total = 0;
    for (var i = 0; i < marks.length; i++) {
        total += marks[i];
    }
    return total;
}

function calculateAverage(total, count) {
    return (total / count).toFixed(2);
}

function validateMarks(mark) {
    var markNum = Number(mark);
    if (isNaN(markNum) || markNum < 0 || markNum > 100) {
        return false;
    }
    return true;
}

function addStudent() {
    var name = prompt('Enter student name:');

    if (!name || name.trim() === '') {
        alert('Student name cannot be empty!');
        return;
    }

    var marks = [];
    for (var i = 1; i <= 5; i++) {
        var mark = prompt('Enter marks for subject ' + i + ' (0-100):');

        if (!validateMarks(mark)) {
            alert('Invalid marks! Please enter a number between 0 and 100.');
            return;
        }

        marks.push(Number(mark));
    }

    var total = calculateTotal(marks);
    var average = calculateAverage(total, 5);

    var student = {
        name: name.trim(),
        marks: marks,
        total: total,
        average: average
    };

    students.push(student);
    saveStudentsToStorage();
    updateTable();
}

function updateTable() {
    var tbody = document.getElementById('studentTableBody');
    tbody.innerHTML = '';

    for (var i = 0; i < students.length; i++) {
        var student = students[i];
        var row = tbody.insertRow();

        var nameCell = row.insertCell(0);
        nameCell.textContent = student.name;

        for (var j = 0; j < student.marks.length; j++) {
            var markCell = row.insertCell(j + 1);
            markCell.textContent = student.marks[j];
        }

        var totalCell = row.insertCell(6);
        totalCell.textContent = student.total;

        var avgCell = row.insertCell(7);
        avgCell.textContent = student.average;
    }
}

var addStudentBtn = document.getElementById('addStudentBtn');
if (addStudentBtn) {
    addStudentBtn.addEventListener('click', addStudent);
}

loadStudentsFromStorage();

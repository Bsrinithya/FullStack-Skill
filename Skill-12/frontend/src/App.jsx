import axios from "axios";
import { useEffect, useState } from "react";
import AddStudent from "./AddStudent";
import StudentList from "./StudentList";

function App() {
  const [students, setStudents] = useState([]);

  const API_URL = "http://localhost:8080/students"; // ✅ single source

  // 🔄 Load all students
  const loadStudents = () => {
    axios.get(API_URL)
      .then((res) => {
        console.log(res.data); // 🔍 debug (optional)
        setStudents(res.data);
      })
      .catch((err) => {
        console.error("Error fetching students:", err);
      });
  };

  // 🧠 Load data when app starts
  useEffect(() => {
    loadStudents();
  }, []);

  // ❌ Delete student (FIXED PORT)
  const deleteStudent = (id) => {
    axios.delete(`${API_URL}/${id}`)
      .then(() => {
        loadStudents(); // 🔥 refresh after delete
      })
      .catch((err) => {
        console.error("Error deleting student:", err);
      });
  };

  return (
    <div className="container">
      <h1>Student Management System</h1>

      {/* ➕ Add Student */}
      <AddStudent loadStudents={loadStudents} />

      {/* 📋 Student List */}
      <StudentList students={students} deleteStudent={deleteStudent} />
    </div>
  );
}

export default App;
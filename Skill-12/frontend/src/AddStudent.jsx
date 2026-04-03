import axios from "axios";
import { useState } from "react";

function AddStudent({ loadStudents }) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [course, setCourse] = useState("");

  const submit = (e) => {
    e.preventDefault();

    axios.post("http://localhost:8082/students", {
      name: name,
      email: email,
      course: course
    }).then(() => {
      setName("");
      setEmail("");
      setCourse("");
      loadStudents();
    }).catch((err) => {
      console.log(err);
    });
  };

  return (
    <div className="box">
      <h2>Add Student</h2>

      <form onSubmit={submit}>
        <input
          type="text"
          placeholder="Enter Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <br /><br />

        <input
          type="email"
          placeholder="Enter Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <br /><br />

        <input
          type="text"
          placeholder="Enter Course"
          value={course}
          onChange={(e) => setCourse(e.target.value)}
        />

        <br /><br />

        <button type="submit">Add Student</button>
      </form>
    </div>
  );
}

export default AddStudent;
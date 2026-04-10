# FSAD Skill-13 – Deployment of Full-Stack Application
### Course: 24SDCS02 / 24SDCS02E / 24SDCS02P / 24SDCS02L
### Department of CSE / CS&IT / AI&DS

---

## Project Structure

```
fsad-skill13/
├── react-frontend/          ← React app (Student Management UI)
│   ├── src/
│   │   ├── App.js           ← Main component + routing
│   │   ├── components/
│   │   │   ├── StudentList.js   ← Fetch & display all students
│   │   │   └── StudentForm.js   ← Add new student form
│   │   └── services/
│   │       └── api.js       ← Axios API calls to Spring Boot
│   ├── .env.production      ← TASK 3: environment variables
│   └── package.json
│
├── spring-backend/          ← Spring Boot REST API
│   ├── src/main/java/com/fsad/app/
│   │   ├── StudentAppApplication.java   ← Entry point
│   │   ├── model/Student.java           ← JPA entity
│   │   ├── repository/StudentRepository.java
│   │   ├── service/StudentService.java
│   │   ├── controller/StudentController.java  ← REST endpoints
│   │   └── config/CorsConfig.java       ← CORS + static config
│   ├── src/main/resources/
│   │   ├── application.properties       ← H2 / server config
│   │   └── data.sql                     ← Sample data
│   └── pom.xml                          ← TASK 2: Maven build
│
├── nginx/
│   └── fsad-app.conf        ← TASK 5: Nginx config
│
└── README.md
```

---

## Prerequisites
- Java 17+
- Node.js 18+ and npm
- Maven 3.8+
- Nginx (for Task 5 option A)

---

## TASK 1 – Generate the React Production Build

```bash
cd react-frontend

# Install dependencies
npm install

# Create production build → output in ./build/
npm run build
```

The `build/` folder contains minified HTML, CSS, and JS — ready to deploy.

---

## TASK 2 – Package Spring Boot as a JAR

```bash
cd spring-backend

# Package (skip tests for speed)
mvn clean package -DskipTests

# JAR created at:
# target/student-app-0.0.1-SNAPSHOT.jar
```

---

## TASK 3 – Configure Environment Variables

Edit `react-frontend/.env.production`:

```
REACT_APP_API_URL=http://YOUR_SERVER_IP:8080
REACT_APP_ENV=production
```

**Rebuild React after any change to .env.production:**
```bash
cd react-frontend
npm run build
```

Inside your React code, access it as:
```js
const apiUrl = process.env.REACT_APP_API_URL;
```

---

## TASK 4 – Run the JAR and Verify APIs

```bash
# Run the backend
java -jar spring-backend/target/student-app-0.0.1-SNAPSHOT.jar

# Verify with curl:

# Health check
curl http://localhost:8080/actuator/health

# Get all students (5 sample records pre-loaded)
curl http://localhost:8080/api/students

# Get single student
curl http://localhost:8080/api/students/1

# Add a student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@college.edu","course":"B.Tech CSE"}'

# Delete a student
curl -X DELETE http://localhost:8080/api/students/1

# H2 Console (browser)
# http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:fsaddb
```

---

## TASK 5 – Deploy React (Two Options)

### Option A: Nginx (Recommended for Production)

```bash
# 1. Copy React build to web root
sudo mkdir -p /var/www/fsad-app
sudo cp -r react-frontend/build/* /var/www/fsad-app/

# 2. Copy Nginx config
sudo cp nginx/fsad-app.conf /etc/nginx/sites-available/fsad-app

# 3. Enable site
sudo ln -s /etc/nginx/sites-available/fsad-app /etc/nginx/sites-enabled/

# 4. Test and reload
sudo nginx -t
sudo systemctl reload nginx

# 5. Start Spring Boot JAR (in background)
nohup java -jar spring-backend/target/student-app-0.0.1-SNAPSHOT.jar &
```

**Nginx handles:**
- `http://your-ip/` → serves React static files
- `http://your-ip/api/*` → reverse-proxied to Spring Boot :8080

---

### Option B: Bundle React Inside the JAR (Simpler)

```bash
# 1. Copy React build into Spring Boot static folder
cp -r react-frontend/build/* \
  spring-backend/src/main/resources/static/

# 2. Rebuild the JAR (it now contains the React UI)
cd spring-backend
mvn clean package -DskipTests

# 3. Run — frontend at / and APIs at /api
java -jar target/student-app-0.0.1-SNAPSHOT.jar
```

Visit `http://localhost:8080` — everything served from one JAR.

---

## TASK 6 – Test in Browser

1. Open `http://localhost:80` (Nginx) or `http://localhost:8080` (JAR)
2. Verify the Student List loads (5 pre-seeded records)
3. Click **+ Add Student**, fill the form, submit
4. Confirm the new student appears in the list
5. Click **Delete** on any student and confirm it disappears
6. Open browser **DevTools → Network tab** — check API calls return 200

### Checklist
- [ ] Frontend loads without blank screen
- [ ] Student list shows data from the database
- [ ] Add student form posts to `/api/students` successfully
- [ ] Delete student removes the row
- [ ] No CORS errors in browser console
- [ ] `/actuator/health` returns `{"status":"UP"}`

---

## Common Issues

| Problem | Fix |
|---|---|
| CORS error in browser | Ensure `@CrossOrigin` is on controller or `CorsConfig` is active |
| 404 on page refresh | Nginx `try_files` must point to `/index.html` |
| API URL is `localhost` in prod | Edit `.env.production` and rebuild React |
| Port 8080 not reachable | Check firewall: `sudo ufw allow 8080` |
| H2 data lost on restart | Expected — H2 is in-memory. Switch to MySQL for persistence |

---

## API Reference

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Add new student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |
| GET | `/actuator/health` | Backend health check |
| GET | `/h2-console` | H2 database console |

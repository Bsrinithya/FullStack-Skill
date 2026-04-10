# Skill-14 – User Authentication & Session Management
### FSAD Workbook | 24SDCS02 / 24SDCS02E / 24SDCS02P / 24SDCS02L

---

## Project Structure

```
skill14/
├── frontend/   → React (Create React App)
└── backend/    → Spring Boot + MySQL
```

---

## Prerequisites

| Tool | Version |
|------|---------|
| Node.js | 18+ |
| npm | 9+ |
| Java | 17+ |
| Maven | 3.8+ |
| MySQL | 8.0+ |

---

## Backend Setup (Spring Boot)

### 1. Create MySQL Database
```sql
CREATE DATABASE skill14_db;
```

### 2. Update `application.properties`
Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/skill14_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 3. Run the backend
```bash
cd backend
mvn spring-boot:run
```
Backend starts at → **http://localhost:8080**

---

## Frontend Setup (React)

```bash
cd frontend
npm install
npm start
```
Frontend starts at → **http://localhost:3000**

---

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login & get username |
| GET  | `/api/auth/profile/{username}` | Fetch full user profile |

### Sample Request Bodies

**Register**
```json
{
  "fullName": "John Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "password": "secret123"
}
```

**Login**
```json
{
  "username": "johndoe",
  "password": "secret123"
}
```

---

## Application Flow

```
Register → Login → Home → Profile → Logout → Login
```

1. **Register** – saves user to MySQL via POST `/api/auth/register`, redirects to Login
2. **Login** – validates credentials via POST `/api/auth/login`, stores `username` in `sessionStorage`, redirects to Home
3. **Home** – protected route, shows logged-in username
4. **Profile** – fetches full user details from DB via GET `/api/auth/profile/{username}` and displays them
5. **Logout** – clears `sessionStorage` and `localStorage`, redirects to Login

---

## Session Storage

- `sessionStorage.setItem('loggedInUser', username)` — set on login
- `sessionStorage.removeItem('loggedInUser')` — cleared on logout
- Private routes check for this key; if absent, redirect to `/login`

---

## GitHub Push Instructions

```bash
git init
git add .
git commit -m "Skill-14: User Auth & Session Management"
git remote add origin https://github.com/YOUR_USERNAME/skill14-fsad.git
git push -u origin main
```

Repository must contain:
```
/
├── frontend/
└── backend/
```

---

## Task Checklist

- [x] Task 1 – Register component with useState + backend POST
- [x] Task 2 – Login component with sessionStorage + redirect to Home
- [x] Task 3 – Home component with protected route
- [x] Task 4 – Profile component fetching user data from backend
- [x] Task 5 – Redirect to Login if no session found
- [x] Task 6 – Logout clears storage and redirects
- [x] Task 7 – Navigation links (Home, Profile, Logout)
- [x] Task 8 – CSS styling applied
- [x] Task 9 – GitHub repository structure (frontend/ + backend/)

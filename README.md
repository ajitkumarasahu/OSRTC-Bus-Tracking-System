OSRTC Bus Tracking System <br>

A Core Java Maven Web Application for tracking OSRTC buses in real-time using RESTful APIs, MySQL, and GPS simulation.<br>

This project follows Controller → Service → DAO → Validation → DB layered architecture with bulk CRUD support and Postman-ready APIs.<br>

🚀 Features<br>

✔ Complete REST API for Bus, Route, Driver, Location, Admin<br>
✔ Bulk Create, Bulk Update, Bulk Delete support<br>
✔ Validation Layer (Duplicate driver license, duplicate bus number, route validation)<br>
✔ Real-time GPS location update module<br>
✔ Admin authentication module<br>
✔ MySQL database integration<br>
✔ Postman tested APIs<br>
✔ Clean Maven project structure<br>

🏗 Project Architecture<br>

Controller Layer  →  Service Layer  →  Validation Layer  →  DAO Layer  → Model  → DatabaseConection  → MySQL Database<br>


📂 Folder Structure<br>

src/main/java/com/osrtc/<br>
 ├── controller/<br>
 ├── service/<br>
 ├── dao/<br>
 ├── validators/<br>
 ├── model/<br>
 └── utils/<br>

⚙️Technology Stack<br>

| Technology           | Usage                 |<br>
| -------------------- | --------------------- |<br>
| Java                 | Core language         |<br>
| Maven                | Build tool            |<br>
| Jersey / Servlet API | REST APIs             |<br>
| MySQL                | Database              |<br>
| JDBC                 | Database connectivity |<br>
| Gson / Jackson       | JSON handling         |<br>
| Postman              | API testing           |<br>
| Tomcat               | Deployment server     |<br>

🗄 Database Tables

| Table     | Description     |<br>
| --------- | --------------- |<br>
| buses     | Bus information |<br>
| routes    | Route details   |<br>
| drivers   | Driver info     |<br>
| locations | GPS tracking    |<br>
| admins    | Admin login     |<br>

🔗 Base API URL<br>

http://localhost:8080/OSRTC-BusTrackingSystem/api<br>

👨‍💻 Developer<br>
AJIT KUMAR SAHU<br>



# Business Trips Management Application

A Spring Boot application for managing business trips and associated meetings, with Docker containerization and Cypress E2E testing.

## Features

- ✅ Create and manage business trips
- ✅ Add meetings to business trips  
- ✅ RESTful API backend
- ✅ Static HTML/JS frontend
- ✅ Docker containerization
- ✅ Cypress E2E testing
- ✅ Health check endpoints

## Tech Stack

- **Backend**: Spring Boot 3.2.6, Java 21
- **Database**: H2 (in-memory)
- **Frontend**: HTML, CSS, JavaScript
- **Containerization**: Docker, Docker Compose
- **Testing**: Cypress, JUnit
- **Build Tool**: Maven

## Quick Start

### Prerequisites

- Docker and Docker Compose
- Java 21 (for local development)
- Maven 3.9+ (for local development)
- Node.js 18+ (for Cypress testing)

### Running with Docker

1. **Build and start the application:**
   ```bash
   docker-compose up --build
   ```

2. **Access the application:**
   - Application: http://localhost:8083
   - Health check: http://localhost:8083/actuator/health

3. **Stop the application:**
   ```bash
   docker-compose down
   ```

### Running Tests

#### Cypress E2E Tests

1. **Run all tests with Docker:**
   ```bash
   # Windows
   run-tests.bat
   
   # Linux/Mac
   ./run-tests.sh
   ```

2. **Run tests locally (requires Node.js):**
   ```bash
   # Install Cypress
   npm install
   
   # Start the application first
   docker-compose up -d app
   
   # Run tests in headless mode
   npm run cy:run
   
   # Run tests in interactive mode
   npm run cy:open
   ```

3. **Run specific test files:**
   ```bash
   npx cypress run --spec "cypress/e2e/app-basic.cy.js"
   ```

#### Unit Tests

```bash
# Run with Maven
./mvnw test

# Run with Docker
docker run --rm -v $(pwd):/app -w /app maven:3.9.6-openjdk-21 mvn test
```

## Development

### Local Development Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd biztrips
   ```

2. **Run locally with Maven:**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Run with specific profile:**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
   ```

### API Endpoints

- `GET /api/trips` - Get all business trips
- `POST /api/trips` - Create a new business trip
- `GET /api/trips/{id}` - Get specific business trip
- `DELETE /api/trips/{id}` - Delete a business trip
- `GET /api/trips/{id}/meetings` - Get meetings for a trip
- `POST /api/trips/{id}/meetings` - Add meeting to a trip
- `DELETE /api/meetings/{id}` - Delete a meeting

### Health Checks

- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information

## Docker Configuration

### Dockerfile Features

- Multi-stage build for optimized image size
- Java 21 runtime
- Health check support
- Proper layer caching for faster builds

### Docker Compose Services

- **app**: Main Spring Boot application
- **cypress**: Cypress testing container with automatic test execution

## Cypress Testing

### Test Structure

```
cypress/
├── e2e/
│   ├── app-basic.cy.js          # Basic UI tests
│   ├── api-integration.cy.js    # API integration tests
│   └── ui-interactions.cy.js    # Advanced UI interaction tests
├── support/
│   ├── commands.js              # Custom Cypress commands
│   └── e2e.js                   # Global test configuration
└── cypress.config.js            # Cypress configuration
```

### Custom Commands

- `cy.addBusinessTrip(destination, startDate, endDate)` - Add a business trip
- `cy.waitForApi(alias)` - Wait for API response with validation
- `cy.shouldContainText(text)` - Check element text content

### Test Categories

1. **Basic Tests** (`app-basic.cy.js`)
   - Page loading and basic UI elements
   - Form validation
   - Basic accessibility

2. **API Integration** (`api-integration.cy.js`)
   - CRUD operations
   - Error handling
   - API response validation

3. **UI Interactions** (`ui-interactions.cy.js`)
   - Form interactions
   - Responsive design
   - Keyboard navigation

## CI/CD Integration

### GitHub Actions Example

```yaml
name: CI/CD Pipeline

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Build and test
        run: |
          docker-compose up --build -d app
          docker-compose up --build cypress
          docker-compose down
```

### Jenkins Pipeline Example

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'docker build -t biztrips-app .'
            }
        }
        stage('Test') {
            steps {
                sh 'docker-compose up --build -d app'
                sh 'docker-compose up --build cypress'
            }
        }
        stage('Cleanup') {
            always {
                sh 'docker-compose down'
            }
        }
    }
}
```

## Troubleshooting

### Common Issues

1. **Port 8083 already in use:**
   ```bash
   # Change port in docker-compose.yml or stop conflicting service
   docker-compose down
   ```

2. **Cypress tests failing:**
   ```bash
   # Check if application is running
   curl http://localhost:8083/actuator/health
   
   # Check application logs
   docker-compose logs app
   ```

3. **Docker build issues:**
   ```bash
   # Clean Docker cache
   docker system prune -a
   
   # Rebuild without cache
   docker-compose build --no-cache
   ```

### Logs and Debugging

```bash
# View application logs
docker-compose logs app

# View Cypress test output
docker-compose logs cypress

# Follow logs in real-time
docker-compose logs -f app
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit a pull request

## License

This project is licensed under the MIT License.

---

## Original German Documentation

Schicht 1: Controller-Schicht (REST API)

Schicht 2: Service- / Repository-Schicht (Datenzugriff via Spring Data JPA)

Datenbank: In-Memory (H2) oder optional MySQL

Persistenz mit JPA

Abhängigkeiten via Maven

Automatische Erstellung der CRUD-Repositories durch Spring Data

Dependency Injection mit @Autowired

Verwendung von ResponseEntity zur Rückgabe der API-Responses

Nutzung von CORS für den lokalen Zugriff von Frontend (z. B. localhost:5173)

Technologiestack
Java 17+

Spring Boot 3+

Spring Web

Spring Data JPA

H2 Database

Maven Build Tool

Lombok (optional, um Boilerplate zu reduzieren)

Git für Versionierung

Postman für API-Tests

Entities
BusinessTrip
Felder:

Long id

String title

String description

LocalDateTime startTrip

LocalDateTime endTrip

Beziehung: List<Meeting> meetings

Annotationen:

@Entity

@Id, @GeneratedValue

@OneToMany(mappedBy = "businessTrip")

Meeting
Felder:

Long id

String title

String description

Beziehung: BusinessTrip businessTrip

Annotationen:

@Entity

@Id, @GeneratedValue

@ManyToOne

@JoinColumn(name = "business_trip_idfs")

Repositories
BusinessTripRepository
java
Kopieren
Bearbeiten
public interface BusinessTripRepository extends JpaRepository<BusinessTrip, Long> {
    List<BusinessTrip> findByTitle(String title);
}
MeetingRepository
java
Kopieren
Bearbeiten
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByBusinessTrip_Id(Long businessTripId);
}
Testdaten
Beim Start werden Testdaten über einen CommandLineRunner angelegt.

Minimum: 10 BusinessTrips und passende Meetings.

Beispiel:

java
Kopieren
Bearbeiten
@Bean
public CommandLineRunner initData(BusinessTripRepository tripRepo, MeetingRepository meetingRepo) {
    return args -> {
        BusinessTrip trip1 = new BusinessTrip(null, "Trip SF", "San Francisco Business Trip",
            LocalDateTime.of(2025, 5, 1, 9, 0), LocalDateTime.of(2025, 5, 5, 18, 0));
        tripRepo.save(trip1);
        
        Meeting meet1 = new Meeting(null, "Kickoff", "Kickoff Meeting", trip1);
        Meeting meet2 = new Meeting(null, "WrapUp", "Abschluss", trip1);
        meetingRepo.save(meet1);
        meetingRepo.save(meet2);
    };
}
Controller
BusinessTripController
java
Kopieren
Bearbeiten
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class BusinessTripController {

    @Autowired
    private BusinessTripRepository tripRepo;

    @GetMapping("/trips")
    public ResponseEntity<List<BusinessTrip>> getAllTrips() {
        List<BusinessTrip> trips = tripRepo.findAll();
        return ResponseEntity.status(trips.isEmpty() ? 204 : 200).body(trips);
    }

    @GetMapping("/trips/{id}")
    public ResponseEntity<BusinessTrip> getTripById(@PathVariable Long id) {
        return tripRepo.findById(id)
            .map(trip -> ResponseEntity.ok(trip))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/trips")
    public ResponseEntity<BusinessTrip> createTrip(@RequestBody BusinessTrip trip) {
        BusinessTrip saved = tripRepo.save(trip);
        return ResponseEntity.status(201).body(saved);
    }
}
MeetingController
java
Kopieren
Bearbeiten
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class MeetingController {

    @Autowired
    private MeetingRepository meetingRepo;

    @GetMapping("/meetings")
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        List<Meeting> meetings = meetingRepo.findAll();
        return ResponseEntity.status(meetings.isEmpty() ? 204 : 200).body(meetings);
    }

    @GetMapping("/trips/{tripId}/meetings")
    public ResponseEntity<List<Meeting>> getMeetingsByTripId(@PathVariable Long tripId) {
        List<Meeting> meetings = meetingRepo.findByBusinessTrip_Id(tripId);
        return ResponseEntity.status(meetings.isEmpty() ? 204 : 200).body(meetings);
    }
}
HTML-View
HTML Seite mit Thymeleaf (optional bei reiner API, Pflicht wenn gefordert)

Listet Meetings auf

Sortierung per Java Comparator

API-Tests
Alle Endpunkte müssen mit Postman getestet werden.

Abgabe: Postman Collection (.json) im Repository.

Response Codes: 200 OK bei Daten, 204 No Content bei leerem Ergebnis, 404 bei nicht gefunden.

Git
Versionierung auf GitHub oder GitLab

Jeder Commit dokumentiert

Projekt in einer Gruppe teilen

Dokumentation als Markdown im Repo enthalten

Clean Code Regeln
SRP: Eine Klasse = eine Verantwortung.

KISS: Kein unnötig komplexer Code.

DRY: Keine Duplikate.

Lesbare Namen

Unit-Tests (optional hier minimal)

Regelmäßige Code Reviews

Automatisierte Formatierung möglich (z. B. via IDE-Formatter oder Linter)

Abgabe
Vollständiger Quellcode im Git Repo

Dokumentation als PDF/Markdown

Postman Collection im Repo

Link zum Repo einreichen 
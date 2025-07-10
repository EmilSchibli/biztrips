Business Trips Backend — MVP Dokumentation
Projektname
Business Trips Backend API

Ziel
Die Applikation dient dazu, Geschäftsreisen (Business Trips) und dazugehörige Meetings zu verwalten.
Die gesamte Logik wird als Java Spring Boot RESTful API umgesetzt. Das Frontend kann per HTTP-Requests (z. B. Postman, React) auf die API zugreifen.

Architektur
2-Tier Architektur

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
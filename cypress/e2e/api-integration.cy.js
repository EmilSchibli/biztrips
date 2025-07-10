describe("Business Trips API Integration", () => {
  beforeEach(() => {
    // Set up API interception with correct endpoints
    cy.intercept("GET", "/v1/trips").as("getTrips");
    cy.intercept("POST", "/v1/trips").as("createTrip");
    cy.intercept("GET", "/v1/trips/*/meetings").as("getMeetings");
    cy.intercept("POST", "/v1/trips/*/meetings").as("createMeeting");
    cy.intercept("DELETE", "/v1/trips/*").as("deleteTrip");

    cy.visit("/");
    cy.wait("@getTrips");
  });

  it("should load existing trips from API", () => {
    cy.wait("@getTrips").then((interception) => {
      expect(interception.response.statusCode).to.equal(200);
    });
  });

  it("should create a new business trip", () => {
    const destination = "Berlin";
    const startDate = "2025-08-01";
    const endDate = "2025-08-05";

    cy.addBusinessTrip(destination, startDate, endDate);

    cy.waitForApi("@createTrip");
    cy.waitForApi("@getTrips");

    // Verify the trip appears in the list
    cy.get("#trips-list").should("contain.text", destination);
  });

  it("should handle API errors gracefully", () => {
    // Mock a server error
    cy.intercept("POST", "/v1/trips", {
      statusCode: 500,
      body: { error: "Internal Server Error" },
    }).as("createTripError");

    cy.addBusinessTrip("Test Destination", "2025-08-01", "2025-08-05");

    cy.wait("@createTripError");

    // The application should handle the error gracefully
    // You might want to add error handling UI elements to test
  });

  it("should show meetings for a trip when clicked", () => {
    // First, ensure we have at least one trip
    cy.addBusinessTrip("Munich", "2025-08-01", "2025-08-05");
    cy.waitForApi("@createTrip");
    cy.waitForApi("@getTrips");

    // Click on a trip to load meetings
    cy.get("#trips-list li").first().click();

    cy.wait("@getMeetings").then((interception) => {
      expect(interception.response.statusCode).to.equal(200);
    });

    // Meetings section should become visible
    cy.get("#meetings-section").should("be.visible");
  });

  it("should create a meeting for a trip", () => {
    // First create a trip
    cy.addBusinessTrip("Vienna", "2025-08-01", "2025-08-05");
    cy.waitForApi("@createTrip");
    cy.waitForApi("@getTrips");

    // Click on the trip to show meetings section
    cy.get("#trips-list li").first().click();
    cy.waitForApi("@getMeetings");

    // Add a meeting
    cy.get("#meeting-title").type("Important Meeting");
    cy.get("#meeting-description").type("Discuss quarterly results");
    cy.get("#add-meeting-form").submit();

    cy.waitForApi("@createMeeting");
    cy.waitForApi("@getMeetings");

    // Verify the meeting appears in the list
    cy.get("#meetings-list").should("contain.text", "Important Meeting");
  });
});

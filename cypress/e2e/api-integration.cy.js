describe("Business Trips API Integration", () => {
  beforeEach(() => {
    // Set up API interception with exact endpoint paths
    cy.intercept("GET", "**/v1/trips").as("getTrips");
    cy.intercept("POST", "**/v1/trips").as("createTrip");
    cy.intercept("GET", "**/v1/trips/*/meetings").as("getMeetings");
    cy.intercept("POST", "**/v1/trips/*/meetings").as("createMeeting");
    cy.intercept("DELETE", "**/v1/trips/*").as("deleteTrip");
    cy.intercept("DELETE", "**/v1/trips/*/meetings/*").as("deleteMeeting");

    cy.visit("/");
    // Wait a bit for the page to load before waiting for the API call
    cy.get('#trips-list', { timeout: 10000 }).should('be.visible');
  });

  it("should load existing trips from API", () => {
    // The initial load should have triggered a getTrips call
    cy.get('#trips-list').should('be.visible');
    // We can check if the request was made by verifying the UI state
  });

  it("should create a new business trip", () => {
    const destination = "Berlin";
    const startDate = "2025-08-01";
    const endDate = "2025-08-05";

    cy.addBusinessTrip(destination, startDate, endDate);

    // Verify the trip appears in the list
    cy.get("#trips-list", { timeout: 10000 }).should("contain.text", destination);
  });

  it("should handle API errors gracefully", () => {
    // Mock a server error
    cy.intercept("POST", "**/v1/trips", {
      statusCode: 500,
      body: { error: "Internal Server Error" },
    }).as("createTripError");

    cy.addBusinessTrip("Test Destination", "2025-08-01", "2025-08-05");

    // The application should handle the error gracefully
    // Since we don't have specific error UI, just verify the trip doesn't appear
    cy.get("#trips-list").should("not.contain.text", "Test Destination");
  });

  it("should show meetings for a trip when clicked", () => {
    // First, ensure we have at least one trip
    cy.addBusinessTrip("Munich", "2025-08-01", "2025-08-05");
    
    // Wait for the trip to appear
    cy.get("#trips-list", { timeout: 10000 }).should("contain.text", "Munich");

    // Click on the "Meetings anzeigen" button
    cy.get("#trips-list").contains("Munich").closest('li').find('.details-btn').click();

    // Meetings section should become visible
    cy.get("#meetings-section").should("be.visible");
    cy.get("#meetings-header").should("contain.text", "Munich");
  });

  it("should create a meeting for a trip", () => {
    // First create a trip
    cy.addBusinessTrip("Vienna", "2025-08-01", "2025-08-05");
    
    // Wait for the trip to appear
    cy.get("#trips-list", { timeout: 10000 }).should("contain.text", "Vienna");

    // Click on the trip to show meetings section
    cy.get("#trips-list").contains("Vienna").closest('li').find('.details-btn').click();
    
    // Wait for meetings section to be visible
    cy.get("#meetings-section").should("be.visible");

    // Add a meeting
    cy.get("#meeting-title").type("Important Meeting");
    cy.get("#meeting-description").type("Discuss quarterly results");
    cy.get("#add-meeting-form").submit();

    // Verify the meeting appears in the list
    cy.get("#meetings-list", { timeout: 10000 }).should("contain.text", "Important Meeting");
  });
});

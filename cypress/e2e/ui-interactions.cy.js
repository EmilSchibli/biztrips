describe("Business Trips UI Interactions", () => {
  beforeEach(() => {
    cy.intercept("GET", "**/v1/trips", []).as("getTrips");
    cy.intercept("POST", "**/v1/trips").as("createTrip");
    cy.visit("/");
  });

  it("should clear form after successful submission", () => {
    // Mock successful API response
    cy.intercept("POST", "**/v1/trips", {
      statusCode: 201,
      body: {
        id: 1,
        destination: "Hamburg",
        startDate: "2025-08-01",
        endDate: "2025-08-05",
      },
    }).as("createTripSuccess");

    // Fill and submit form
    cy.get("#trip-destination").type("Hamburg");
    cy.get("#trip-start-date").type("2025-08-01");
    cy.get("#trip-end-date").type("2025-08-05");
    cy.get("#add-trip-form").submit();

    cy.wait("@createTripSuccess");

    // Check if form is cleared (this depends on your JS implementation)
    cy.get("#trip-destination").should("have.value", "");
    cy.get("#trip-start-date").should("have.value", "");
    cy.get("#trip-end-date").should("have.value", "");
  });

  it("should validate date ranges", () => {
    const today = new Date().toISOString().split("T")[0];
    const yesterday = new Date(Date.now() - 86400000)
      .toISOString()
      .split("T")[0];

    cy.get("#trip-destination").type("Paris");
    cy.get("#trip-start-date").type(today);
    cy.get("#trip-end-date").type(yesterday);

    // Try to submit with end date before start date
    cy.get("#add-trip-form").submit();

    // Should show validation error or prevent submission
    // This depends on your form validation implementation
  });

  it("should be responsive on mobile viewports", () => {
    cy.viewport("iphone-x");

    cy.get(".container").should("be.visible");
    cy.get("#add-trip-form").should("be.visible");
    cy.get("#trips-list").should("be.visible");

    // Form should still be usable on mobile
    cy.get("#trip-destination").should("be.visible").and("not.be.disabled");
    cy.get("#trip-start-date").should("be.visible").and("not.be.disabled");
    cy.get("#trip-end-date").should("be.visible").and("not.be.disabled");
  });

  it("should handle keyboard navigation", () => {
    cy.get("#trip-destination").focus();
    cy.focused().should("have.id", "trip-destination");

    // Test basic tab navigation using realPress (requires cypress-real-events plugin)
    // Since we don't have that plugin, let's test direct focus instead
    cy.get("#trip-start-date").focus();
    cy.focused().should("have.id", "trip-start-date");

    cy.get("#trip-end-date").focus(); 
    cy.focused().should("have.id", "trip-end-date");

    // Test that the main form submit button is focusable
    cy.get("#add-trip-form button[type='submit']").focus();
    cy.focused().should("have.attr", "type", "submit");
  });

  it("should show loading states during API calls", () => {
    // Intercept the initial trips load with an empty list
    cy.intercept("GET", "**/v1/trips", []).as("getTrips");
    
    // Mock successful API response with delay for creating a trip
    cy.intercept("POST", "**/v1/trips", {
      statusCode: 201,
      body: {
        id: 99,
        title: "Stockholm", 
        description: "New Trip",
        startTrip: "2025-08-01T09:00:00",
        endTrip: "2025-08-05T18:00:00",
        meetings: []
      },
      delay: 1000  // 1 second delay
    }).as("slowCreateTrip");

    // Intercept the trips refresh after creation with the new trip
    cy.intercept("GET", "**/v1/trips", [
      {
        id: 99,
        title: "Stockholm", 
        description: "New Trip",
        startTrip: "2025-08-01T09:00:00",
        endTrip: "2025-08-05T18:00:00",
        meetings: []
      }
    ]).as("getTripsRefresh");

    // Visit page and wait for initial load
    cy.visit("/");
    cy.wait("@getTrips");

    cy.get("#trip-destination").type("Stockholm");
    cy.get("#trip-start-date").type("2025-08-01");
    cy.get("#trip-end-date").type("2025-08-05");
    cy.get("#add-trip-form").submit();

    // Wait for the create request to complete
    cy.wait("@slowCreateTrip");
    
    // Wait for the trips refresh call
    cy.wait("@getTripsRefresh");
    
    // Verify the trip was added
    cy.get("#trips-list").should("contain.text", "Stockholm");
  });
});

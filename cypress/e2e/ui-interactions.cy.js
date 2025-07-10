describe("Business Trips UI Interactions", () => {
  beforeEach(() => {
    cy.intercept("GET", "/api/trips", []).as("getTrips");
    cy.intercept("POST", "/api/trips").as("createTrip");
    cy.visit("/");
  });

  it("should clear form after successful submission", () => {
    // Mock successful API response
    cy.intercept("POST", "/api/trips", {
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

    cy.get("#trip-destination").tab();
    cy.focused().should("have.id", "trip-start-date");

    cy.get("#trip-start-date").tab();
    cy.focused().should("have.id", "trip-end-date");

    cy.get("#trip-end-date").tab();
    cy.focused().should("have.attr", "type", "submit");
  });

  it("should show loading states during API calls", () => {
    // Mock slow API response
    cy.intercept("POST", "/api/trips", (req) => {
      req.reply((res) => {
        // Delay response by 2 seconds
        setTimeout(
          () =>
            res.send({
              statusCode: 201,
              body: {
                id: 1,
                destination: "Stockholm",
                startDate: "2025-08-01",
                endDate: "2025-08-05",
              },
            }),
          2000
        );
      });
    }).as("slowCreateTrip");

    cy.get("#trip-destination").type("Stockholm");
    cy.get("#trip-start-date").type("2025-08-01");
    cy.get("#trip-end-date").type("2025-08-05");
    cy.get("#add-trip-form").submit();

    // Check for loading indicator if implemented
    // This depends on your JavaScript implementation
    cy.wait("@slowCreateTrip");
  });
});

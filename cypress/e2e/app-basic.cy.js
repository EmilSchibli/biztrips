describe("Business Trips Application", () => {
  beforeEach(() => {
    cy.visit("/");
  });

  it("should load the main page successfully", () => {
    cy.title().should("eq", "Geschäftsreisen Verwaltung");
    cy.get("h1").should("contain.text", "Geschäftsreisen Verwaltung");
    cy.get("#add-trip-form").should("be.visible");
    cy.get("#trips-list").should("be.visible");
  });

  it("should have all required form elements", () => {
    cy.get("#trip-destination")
      .should("be.visible")
      .and("have.attr", "placeholder", "Reiseziel");

    cy.get("#trip-start-date")
      .should("be.visible")
      .and("have.attr", "type", "date");

    cy.get("#trip-end-date")
      .should("be.visible")
      .and("have.attr", "type", "date");

    cy.get('#add-trip-form button[type="submit"]')
      .should("be.visible")
      .and("contain.text", "Reise hinzufügen");
  });

  it("should validate required fields", () => {
    // Try to submit empty form
    cy.get('#add-trip-form button[type="submit"]').click();

    // Check that form validation prevents submission
    cy.get("#trip-destination:invalid").should("exist");
  });

  it("should display the meetings section when it exists", () => {
    cy.get("#meetings-section").should("exist");
    cy.get("#meetings-section").should("have.css", "display", "none");
  });
});

// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************

// Custom command to add a business trip
Cypress.Commands.add("addBusinessTrip", (destination, startDate, endDate) => {
  cy.get("#trip-destination").type(destination);
  cy.get("#trip-start-date").type(startDate);
  cy.get("#trip-end-date").type(endDate);
  cy.get("#add-trip-form").submit();
});

// Custom command to wait for API response
Cypress.Commands.add("waitForApi", (alias) => {
  cy.wait(alias).then((interception) => {
    expect(interception.response.statusCode).to.be.oneOf([200, 201]);
  });
});

// Custom command to check if element contains text
Cypress.Commands.add(
  "shouldContainText",
  { prevSubject: "element" },
  (subject, text) => {
    cy.wrap(subject).should("contain.text", text);
  }
);

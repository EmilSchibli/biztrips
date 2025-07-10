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
  cy.get("#trip-destination").clear().type(destination);
  cy.get("#trip-start-date").clear().type(startDate);
  cy.get("#trip-end-date").clear().type(endDate);
  cy.get("#add-trip-form").submit();
});

// Custom command to wait for API response with more robust error handling
Cypress.Commands.add("waitForApi", (alias, timeout = 10000) => {
  cy.wait(alias, { timeout }).then((interception) => {
    if (interception && interception.response) {
      expect(interception.response.statusCode).to.be.oneOf([200, 201]);
    }
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

// Custom command to wait for element to be visible and stable
Cypress.Commands.add("waitForElement", (selector, timeout = 10000) => {
  cy.get(selector, { timeout }).should("be.visible").should("not.be.disabled");
});

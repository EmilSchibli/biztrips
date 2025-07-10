const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: "http://localhost:8083",
    supportFile: "cypress/support/e2e.js",
    specPattern: "cypress/e2e/**/*.cy.{js,jsx,ts,tsx}",
    video: false, // Disable video recording for speed (enable only on CI failure)
    screenshotOnRunFailure: true,
    viewportWidth: 1280,
    viewportHeight: 720,
    // Performance optimizations
    defaultCommandTimeout: 4000, // Reduce from default 4000ms
    requestTimeout: 5000,
    responseTimeout: 30000,
    pageLoadTimeout: 30000,
    // Run tests faster
    experimentalRunAllSpecs: true,
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});

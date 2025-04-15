/// <reference types="cypress" />

describe("Login Page", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/login");
  });

  it("should allow the user to type username and password and click login", () => {
    cy.get("ui5-input#username")
      .shadow() 
      .find("input") 
      .type("testuser");

    cy.get("ui5-input#password").shadow().find("input").type("password123");

    cy.window().then((win) => {
      cy.spy(win.console, "log").as("consoleLog");
    });

    cy.get("ui5-button", { includeShadowDom: true }).click();

    cy.get("@consoleLog").should(
      "have.been.calledWith",
      "Username:",
      "testuser",
      "Password:",
      "password123"
    );

    // Alternatively, if your app displays a message or navigates, assert that instead.
  });
});
